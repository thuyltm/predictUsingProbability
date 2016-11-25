package bk.master.input.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import bk.master.input.TimeTranslatorUtil;

public class Move {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String departure;
    String destination;
    Date departTime;
    Date destTime;
    public Move(String departure, String destination, Date departTime,
            Date destTime) {
        this.departure = departure;
        this.destination = destination;
        this.departTime = departTime;
        this.destTime = destTime;
    }
    public Move(Date departTime, Date destTime) {
        this.departTime = departTime;
        this.destTime = destTime;
    }
    public String getDeparture() {
        return departure;
    }
    public void setDeparture(String departure) {
        this.departure = departure;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public String getDepartTime() {
        try {
            return formatter.format(departTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Date getOrginalDepartTime() {
        return departTime;
    }
    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }
    public String getDestTime() {
        try {
            return formatter.format(destTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Date getOrginalDestTime() {
        return destTime;
    }
    public void setDestTime(Date destTime) {
        this.destTime = destTime;
    }
    public String toString() {
        long duration = destTime.getTime() - departTime.getTime();
        return departure + "," + destination + "," + getDepartTime()
                + "," + getDestTime() + "," + duration/1000 + "," +
                TimeTranslatorUtil.covertToReadableFormat(duration);
    }

}
