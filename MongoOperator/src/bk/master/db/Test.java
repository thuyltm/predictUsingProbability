package bk.master.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.mongodb.BasicDBObject;

public class Test {
    public static void main(String[] args) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date now = formatter.parse("2016-09-18 05:00:00");

            BasicDBObject timeNow = new BasicDBObject("date", now);
            System.out.println(timeNow.toString());

        } catch (Exception e) {
            // TODO: handle exception
        }
   }
}
