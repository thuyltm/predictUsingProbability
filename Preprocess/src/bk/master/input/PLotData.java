package bk.master.input;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bk.master.input.model.Leg;

public class PLotData {
    public static void export() {
        File folder = new File("distanceFolder");
        List<Integer> distanceList = new ArrayList<Integer>();
        long accTime = 0;
        int accDistance = 0;
        for (File fileEntry : folder.listFiles()) {
            List<Leg> legList = InputUtil.loadLegList(fileEntry.getAbsolutePath());
            distanceList = new ArrayList<Integer>();
            for (Leg leg: legList) {
                if (leg.getDuration()>=30000) {
                    distanceList.add(leg.getDistance());
                } else if (leg.getDuration() < 30000) {
                    accTime = accTime + leg.getDuration();
                    accDistance = accDistance + leg.getDistance();
                    if (accTime >= 30000) {
                        distanceList.add(accDistance);
                        accTime = 0;
                        accDistance = 0;
                    }
                }
            }
            ExportUtil.exportPlotDataFile(distanceList, "data.txt");
        }
    }
}
