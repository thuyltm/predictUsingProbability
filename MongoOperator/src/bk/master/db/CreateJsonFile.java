package bk.master.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import bk.master.input.model.Location;


public class CreateJsonFile {
    public static void createJsonFileChunkLineString(List<Location> uniqueList,
            String outputFile, int chunkSize) {
        try {

            File file = new File(outputFile);

            // if file doesnt exists, then create it
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("{\n \"type\": \"FeatureCollection\",\"features\": [\n");

            int loop = uniqueList.size()/chunkSize;
            if (uniqueList.size()%chunkSize!=0) {
                loop = loop+1;
            }
            for (int j = 0; j < loop; j++) {
                JSONArray ja = new JSONArray();
                int i = chunkSize*j;
                int maxValue = i+chunkSize;
                //Location startLoc = uniqueList.get(i);
                while (i<maxValue && i < uniqueList.size()){
                    JSONArray arr = new JSONArray();
                    Location location = uniqueList.get(i);
                    arr.put(location.getLongtitude());
                    arr.put(location.getLatitude());
                    ja.put(arr);
                    i++;
                }
                JSONObject geometry = new JSONObject();
                geometry.put("type", "LineString");
                JSONArray Locations = new JSONArray();
                Locations.put(ja);
                geometry.put("coordinates", ja);
                JSONObject data = new JSONObject();
                data.put("type", "Feature");
                data.put("geometry", geometry);
                bw.write(data.toString());
                if (j < loop-1) {
                    bw.write(",\n");
                }
            }
            bw.write("]}");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createJsonFileLineString(List<Location> uniqueList,
                                                String outputFile) {
        try {

            File file = new File(outputFile);

            // if file doesnt exists, then create it
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("{\n \"type\": \"FeatureCollection\",\"features\": [\n");
            JSONArray ja = new JSONArray();
            for (int i = 0; i < uniqueList.size(); i++) {
                JSONArray arr = new JSONArray();
                Location location = uniqueList.get(i);
                arr.put(location.getLongtitude());
                arr.put(location.getLatitude());
                ja.put(arr);
            }
            JSONObject geometry = new JSONObject();
            geometry.put("type", "LineString");
            geometry.put("coordinates", ja);
            JSONObject data = new JSONObject();
            data.put("type", "Feature");
            data.put("geometry", geometry);
            bw.write(data.toString());

            bw.write("]}");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createJsonFilePoint(List<Location> uniqueList, String outputFile) {
        try {

            File file = new File(outputFile);

            // if file doesnt exists, then create it
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("{\n \"type\": \"FeatureCollection\",\n\"features\": [\n");
            for (int i = 0; i < uniqueList.size(); i++) {
                JSONArray arr = new JSONArray();
                Location location = uniqueList.get(i);
                arr.put(location.getLongtitude());
                arr.put(location.getLatitude());
                JSONObject geometry = new JSONObject();
                geometry.put("type", "Point");
                geometry.put("coordinates", arr);
                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                feature.put("id", i);
                feature.put("geometry", geometry);
                JSONObject properties = new JSONObject();
                properties.put("icon", "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld="+i+"|FF0000|000000");
                properties.put("date", location.getReadableDate());
                feature.put("properties", properties);
                bw.write(feature.toString());
                if (i < uniqueList.size()-1) {
                    bw.write(",\n");
                }
            }
            bw.write("\n]}");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
