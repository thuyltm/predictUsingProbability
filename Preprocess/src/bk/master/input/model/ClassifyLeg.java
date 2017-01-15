package bk.master.input.model;

import java.util.List;

public class ClassifyLeg {
    public enum Clazz {
        VERY_SMALL, SMALL, MEDIUM, HIGH, VERY_HIGH
    }
    private Clazz clazz;
    private Integer realValue;
    private List<Integer> distanceList;
    public ClassifyLeg(Clazz clazz, Integer realValue,
            List<Integer> distanceList) {
        this.clazz = clazz;
        this.realValue = realValue;
        this.distanceList = distanceList;
    }
    public Clazz getClazz() {
        return clazz;
    }
    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
    public Integer getRealValue() {
        return realValue;
    }
    public void setRealValue(Integer realValue) {
        this.realValue = realValue;
    }
    public List<Integer> getDistanceList() {
        return distanceList;
    }
    public void setDistanceList(List<Integer> distanceList) {
        this.distanceList = distanceList;
    }
}
