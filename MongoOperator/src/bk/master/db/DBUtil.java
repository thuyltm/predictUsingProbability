package bk.master.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import bk.master.input.ExportUtil;
import bk.master.input.TimeTranslatorUtil;
import bk.master.input.model.Location;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DBUtil {
    public static final String HOST = "192.168.1.104";
    public static void findLocationDeviceByTime(String fromISODate, String toISODate,
                            String deviceId, String exportJsonFile, String exportCsvFile) {
        try {
            MongoClient mongoClient = new MongoClient( HOST , 27017 );
            DB db = mongoClient.getDB("bus");
            DBCollection table = db.getCollection("bus");
            BasicDBObject query = new BasicDBObject("date", new BasicDBObject("$gte",fromISODate)
            .append("$lte", toISODate));
            query.put("deviceId", deviceId);

            DBCursor cursor = table.find(query);
            cursor.sort(new BasicDBObject("date",1));
            List<Location> coorList = new ArrayList<Location>();
            while (cursor.hasNext()) {
                BasicDBObject result = (BasicDBObject)cursor.next();
                BasicDBObject location = (BasicDBObject)result.get("location");
                BasicDBList coordinates = (BasicDBList)location.get("coordinates");
                Double longtitude = (Double)coordinates.get(0);
                Double latitude = (Double)coordinates.get(1);
                coorList.add(new Location(deviceId,latitude,longtitude,
                        TimeTranslatorUtil.convertISODateToDate((String)result.getString("date"))));
            }
            if (exportJsonFile!=null&&coorList.size()>0) {
                CreateJsonFile.createJsonFilePoint(coorList,exportJsonFile);
            }
            if (exportCsvFile!=null&&coorList.size()>0) {
                ExportUtil.exportToStepFile(coorList, exportCsvFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<String> findDistinctDeviceWithinLocation(List<Location> data,
            String fromISODate, String toISODate,
            String exportFile) {
        List<String> result = new ArrayList<String>();
        try {
            MongoClient mongoClient = new MongoClient( HOST , 27017 );
            DB db = mongoClient.getDB("bus");
            DBCollection table = db.getCollection("bus");

            BasicDBList pointList = new BasicDBList();
            for (Location lngLat: data) {
                BasicDBList point = new BasicDBList();
                point.add(Double.valueOf(lngLat.getLongtitude()));
                point.add(Double.valueOf(lngLat.getLatitude()));
                pointList.add(point);
            }
            BasicDBList points = new BasicDBList();
            points.add(pointList);

            DBObject geoIntersectQuery = new BasicDBObject("location",
                    new BasicDBObject("$geoWithin",
                            new BasicDBObject("$geometry", new BasicDBObject("type","Polygon")
                                    .append("coordinates", points))));

            final BasicDBObject dateQuery = new BasicDBObject("date", new BasicDBObject("$gte",fromISODate)
            .append("$lte", toISODate));

            BasicDBList and = new BasicDBList();
            and.add(geoIntersectQuery);
            and.add(dateQuery);

            DBObject query = new BasicDBObject("$and", and);

            Object[] deviceList = (Object[])table.distinct("deviceId", query).toArray();
            for (Object device : deviceList) {
                 result.add((String)device);
            }
            ExportUtil.exportToFile(result, exportFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static boolean findTimeDeviceWithinLocation(List<Location> data, String deviceId,
            String fromISODate, String toISODate, String outputScheduleFile) {
        try {
            MongoClient mongoClient = new MongoClient( HOST , 27017 );
            DB db = mongoClient.getDB("bus");
            DBCollection table = db.getCollection("bus");

            BasicDBList pointList = new BasicDBList();
            for (Location lngLat: data) {
                BasicDBList point = new BasicDBList();
                point.add(Double.valueOf(lngLat.getLongtitude()));
                point.add(Double.valueOf(lngLat.getLatitude()));
                pointList.add(point);
            }
            BasicDBList points = new BasicDBList();
            points.add(pointList);

            DBObject geoIntersectQuery = new BasicDBObject("location",
                    new BasicDBObject("$geoWithin",
                            new BasicDBObject("$geometry", new BasicDBObject("type","Polygon")
                                    .append("coordinates", points))));

            final BasicDBObject dateQuery = new BasicDBObject("date", new BasicDBObject("$gte",fromISODate)
            .append("$lte", toISODate));
            final BasicDBObject deviceQuery = new BasicDBObject("deviceId", deviceId);

            BasicDBList and = new BasicDBList();
            and.add(geoIntersectQuery);
            and.add(dateQuery);
            and.add(deviceQuery);

            DBObject query = new BasicDBObject("$and", and);
            DBCursor cursor = table.find(query);
            cursor.sort(new BasicDBObject("date",1));
            List<String> dateList = new ArrayList<String>();
            while (cursor.hasNext()) {
                BasicDBObject result = (BasicDBObject)cursor.next();
                dateList.add((String)result.getString("date"));
            }
            List<Date> diffDateList = TimeTranslatorUtil.filterDiffHour(dateList);
            ExportUtil.exportToDateFile(diffDateList,outputScheduleFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
