package bk.master.input.model;


public class Leg {
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
            double endLat, double endLng, Integer distance, Long duration,
            String durationText) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
        this.distance = distance;
        this.duration = duration;
        this.durationText = durationText;
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
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(startIndex + "|" + endIndex + "|" + startLat + "|" +
                startLng + "|" + endLat + "|" + endLng + "|");
        if (startPlace != null) {
            result.append(startPlace + "|" + endPlace + "|");
        }
        result.append(distance + "|");
        if (distanceText != null) {
            result.append(distanceText + "|");
        }
        result.append(duration + "|" + durationText);
        return result.toString();
    }
}
