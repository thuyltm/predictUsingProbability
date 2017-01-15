package bk.master.input.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import bk.master.util.TimeTranslatorUtil;

public class Location {
    static private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static private SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    String deviceId;
    Double latitude;
    Double longtitude;
    String iSODate;
    Date date;
    public Location(String deviceId, Double latitude, Double longtitude,
            String dateInString) {
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longtitude = longtitude;
        try {
            this.date = formatter.parse(dateInString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Location(String deviceId, Double latitude, Double longtitude, Long miliseconds) {
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.date = new Date(miliseconds);
    }

    public Location(Double latitude, Double longtitude, String ISODate) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.iSODate = ISODate;
    }

    public Location(Double latitude, Double longtitude, Date date) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.date = date;
    }

    public Location(Double latitude, Double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getDeviceId() {
        return deviceId;
    }
    public Double getLatitude() {
        return latitude;
    }
    public Double getLongtitude() {
        return longtitude;
    }
    public String getDate() {
        if (date!=null) {
            return isoFormatter.format(date.getTime());
        }
        if (iSODate!=null) return iSODate;
        return null;
    }
    public String getReadableDate() {
        if (date!=null) return formatter.format(date);
        if (iSODate!=null) return TimeTranslatorUtil.convertISODateToDate(iSODate);
        return null;
    }
    public Date getOrginDate() {
        return date;
    }
    @Override
    public String toString() {
        return "{\"deviceId\": \"" + deviceId + "\", \"location\": {\"type\": \"Point\", \"coordinates\": [" + longtitude
                + ", " + latitude + "]}" + ", \"date\": \"" + getDate() + "\"}";
    }

}
