package bk.master.input.model;


public class Route {
    int index;
    String departure;
    String destination;
    String departTime;
    String destTime;
    Long finishTime;
    String finishTimeText;

    public Route() {
        super();
    }
    public Route(int index, String departure, String destination,
            String departTime, String destTime, Long finishTime,
            String finishTimeText) {
        super();
        this.index = index;
        this.departure = departure;
        this.destination = destination;
        this.departTime = departTime;
        this.destTime = destTime;
        this.finishTime = finishTime;
        this.finishTimeText = finishTimeText;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
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
        return departTime;
    }
    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }
    public String getDestTime() {
        return destTime;
    }
    public void setDestTime(String destTime) {
        this.destTime = destTime;
    }
    public Long getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }
    public String getFinishTimeText() {
        return finishTimeText;
    }
    public void setFinishTimeText(String finishTimeText) {
        this.finishTimeText = finishTimeText;
    }
}
