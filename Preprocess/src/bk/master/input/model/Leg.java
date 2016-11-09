package bk.master.input.model;

import java.text.DecimalFormat;

public class Leg {
    private DecimalFormat df2 = new DecimalFormat("##.##");
    int startIndex;
    int endIndex;
    double startLat;
    double startLng;
    double endLat;
    double endLng;
    String startPlace;
    String endPlace;
    Integer distance;
    String distanceText;
    Long duration;
    String durationText;
    float percentTime;
    public Leg() {
        super();
    }

    public Leg(int startIndex, int endIndex, double startLat, double startLng, double endLat, double endLng,
            String startPlace, String endPlace, Integer distance,
            String distanceText, Long duration, String durationText) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.distance = distance;
        this.distanceText = distanceText;
        this.duration = duration;
        this.durationText = durationText;
    }

    public Leg(int startIndex, int endIndex, double startLat, double startLng,
            double endLat, double endLng, String startPlace, String endPlace,
            Integer distance, String distanceText, Long duration,
            String durationText, float percentTime) {
        super();
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.distance = distance;
        this.distanceText = distanceText;
        this.duration = duration;
        this.durationText = durationText;
        this.percentTime = Float.valueOf(df2.format(percentTime*100));
    }

    public double getStartLat() {
        return startLat;
    }
    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }
    public double getStartLng() {
        return startLng;
    }
    public void setStartLng(double startLng) {
        this.startLng = startLng;
    }
    public double getEndLat() {
        return endLat;
    }
    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }
    public double getEndLng() {
        return endLng;
    }
    public void setEndLng(double endLng) {
        this.endLng = endLng;
    }
    public String getStartPlace() {
        return startPlace;
    }
    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }
    public String getEndPlace() {
        return endPlace;
    }
    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }
    public Integer getDistance() {
        return distance;
    }
    public void setDistance(Integer distance) {
        this.distance = distance;
    }
    public String getDistanceText() {
        return distanceText;
    }
    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }
    public Long getDuration() {
        return duration;
    }
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    public String getDurationText() {
        return durationText;
    }
    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }
    public int getStartIndex() {
        return startIndex;
    }
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    public int getEndIndex() {
        return endIndex;
    }
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
    public float getPercentTime() {
        return percentTime;
    }
    public void setPercentTime(float percentTime) {
        this.percentTime = percentTime;
    }
    @Override
    public String toString() {
        return startIndex + "|" + endIndex + "|" + startLat + "|" +
               startLng + "|" + endLat + "|" + endLng + "|" + startPlace +
               "|" + endPlace + "|" + distance + "|" + distanceText + "|" +
               duration + "|" + durationText + "|" + percentTime;
    }
}
