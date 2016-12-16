package bk.master.input;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bk.master.input.model.Leg;

public class BatchJob {
    private static String MONTH = "09";
    private static String LOCATION_FOLDER = "/home/thuy1/git/predictUsingProbability/MongoOperator/"+MONTH+"/location/";
    private static String DISTANCE_FOLDER = "/home/thuy1/git/predictUsingProbability/MongoOperator/"+MONTH+"/distance/";
    public static void getDistance() {
        //int i = 3;
        for (int i = 12; i <= 19; i++) {
            String numberFolder = String.valueOf(i);
            if (i<10) {
                numberFolder = "0"+i;
            }
            File folder = new File(LOCATION_FOLDER + numberFolder);
            File[] dataFile = folder.listFiles();
            if (dataFile != null) {
                for (File file : dataFile) {
                    Calculate.calculateDistance(file, DISTANCE_FOLDER+numberFolder+"/");
                }
            }
        }
    }
    public static void groupTime() {
        List<Leg> legList = InputUtil.loadLegNoPlaceList(DISTANCE_FOLDER+"02/distance_53N4663_02_0_44:49_AS-CC.csv");
        List<Integer> distanceList = new ArrayList<Integer>();
        List<Long> redundantDuration = new ArrayList<Long>();
        List<Integer> redundantDistance = new ArrayList<Integer>();
        for (Leg leg : legList) {
            Long duration = leg.getDuration();
            Integer distance = leg.getDistance();
            if (duration == 20000) {
                distanceList.add(distance);
            } else {
                redundantDuration.add(duration);
                redundantDistance.add(distance);
            }
        }
        System.out.println(redundantDistance.toString());
        System.out.println(redundantDuration.toString());
    }

    public static List<String> getDetail(List<String> onTimeList, Integer... para) {
        List<String> detailList = new ArrayList<String>();
        Pattern folderPattern = Pattern.compile("\\d+_\\d+_\\d+:\\d+", Pattern.CASE_INSENSITIVE);
        long thresholdDuration = -1L;
        for (String fileName: onTimeList) {
            Matcher folderMatcher = folderPattern.matcher(fileName);
            String numberFolder = "0";
            while (folderMatcher.find()) {
                String data = folderMatcher.group();
                numberFolder = data.substring(0, data.indexOf("_"));
                if (para.length > 0) {
                    Pattern timePattern = Pattern.compile("\\d+:\\d+", Pattern.CASE_INSENSITIVE);
                    Matcher timeMatcher = timePattern.matcher(fileName);
                    while(timeMatcher.find()) {
                        Long finishTime = TimeTranslatorUtil.convertToMillionSeconds(timeMatcher.group());
                        thresholdDuration = (para[0] * finishTime)/100;
                    }
                }
            }
            File file = new File(DISTANCE_FOLDER + numberFolder + "/distance_" + fileName +".csv");
            List<Leg> legList = InputUtil.loadLegNoPlaceList(file.getAbsolutePath());
            StringBuilder distanceList = new StringBuilder();
            StringBuilder durationList = new StringBuilder();
            StringBuilder durationTextList = new StringBuilder();
            int maxSize = legList.size()-1;
            int i = 0;
            Long totalDuration = 0L;
            Integer totalDistance = 0;
            for (Leg leg : legList) {
                if (leg.getDuration() != 0) {
                    durationTextList.append(leg.getDurationText());
                    durationList.append(leg.getDuration());
                    distanceList.append(leg.getDistance());
                    totalDuration += leg.getDuration();
                    totalDistance += leg.getDistance();
                }
                if (i++ < maxSize) {
                    durationList.append(",");
                    durationTextList.append(",");
                    distanceList.append(",");
                }
                if (thresholdDuration > 0 && totalDuration > thresholdDuration) {
                    break;
                }
            }
            detailList.add(fileName+"|"+TimeTranslatorUtil.covertToReadableFormat(totalDuration)
                           +"|"+totalDistance);
            detailList.add(durationTextList.toString());
            detailList.add(durationList.toString());
            detailList.add(distanceList.toString());
        }
        return detailList;
    }
    public static void getClassify(String pattern) {
        HashMap<String,List<String>> data = Calculate.classify(Integer.valueOf(45),
                            new File(LOCATION_FOLDER), "\\d+:\\d+_"+pattern+"$");
        List<String> onTimeList = data.get("onTime");
       // List<String> lateTimeList = data.get("lateTime");
        List<String> onTimeDetailList = getDetail(onTimeList, 80);
        ExportUtil.exportToFile(onTimeDetailList, "onTimeDetail_"+pattern+"_80.csv");
        //ExportUtil.exportToFile(lateTimeList, "lateTime_"+pattern+".csv");
    }
    public static void main(String[] args) {
        groupTime();
    }
}
