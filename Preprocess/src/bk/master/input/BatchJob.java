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
    public static List<String> getDetail(List<String> fileList, Integer percentFTime) {
        List<String> detailList = new ArrayList<String>();
        Pattern folderPattern = Pattern.compile("\\d+_\\d+_\\d+:\\d+", Pattern.CASE_INSENSITIVE);
        long thresholdDuration = -1L;
        for (String fileName: fileList) {
            Matcher folderMatcher = folderPattern.matcher(fileName);
            String numberFolder = "0";
            while (folderMatcher.find()) {
                String data = folderMatcher.group();
                numberFolder = data.substring(0, data.indexOf("_"));
                Pattern timePattern = Pattern.compile("\\d+:\\d+", Pattern.CASE_INSENSITIVE);
                Matcher timeMatcher = timePattern.matcher(fileName);
                while(timeMatcher.find()) {
                    Long finishTime = TimeTranslatorUtil.convertToMillionSeconds(timeMatcher.group());
                    thresholdDuration = (percentFTime * finishTime)/100;
                }
            }
            File file = new File(DISTANCE_FOLDER + numberFolder + "/distance_" + fileName +".csv");
            List<Leg> legList = InputUtil.loadLegNoPlaceList(file.getAbsolutePath());
            List<Leg> percentLegList = new ArrayList<Leg>();
            StringBuilder percentDistanceList = new StringBuilder();
            StringBuilder percentDurationList = new StringBuilder();
            StringBuilder precentDurationTextList = new StringBuilder();
            int maxSize = legList.size()-1;
            int i = 0;
            Long percentDuration = 0L;
            Integer percentDistance = 0;
            for (Leg leg : legList) {
                if (leg.getDuration() != 0) {
                    precentDurationTextList.append(leg.getDurationText());
                    percentDurationList.append(leg.getDuration()/1000);
                    percentDistanceList.append(leg.getDistance());
                    percentDuration += leg.getDuration();
                    percentDistance += leg.getDistance();
                    percentLegList.add(leg);
                }
                if (thresholdDuration > 0 && percentDuration > thresholdDuration) {
                    break;
                }
                if (i++ < maxSize) {
                    percentDurationList.append(",");
                    precentDurationTextList.append(",");
                    percentDistanceList.append(",");
                }
            }
            Long totalDuration = 0L;
            Integer totalDistance = 0;
            for (Leg leg : legList) {
                if (leg.getDuration() != 0) {
                    totalDuration += leg.getDuration();
                    totalDistance += leg.getDistance();
                }
            }
            detailList.add(fileName+"|"+TimeTranslatorUtil.covertToReadableFormat(totalDuration)
                           +"|"+totalDistance);
            detailList.add(TimeTranslatorUtil.covertToReadableFormat(percentDuration)
                           +"|"+percentDistance);
            detailList.add(precentDurationTextList.toString());
            detailList.add(percentDurationList.toString());
            detailList.add(percentDistanceList.toString());
            GroupDuration.groupToStandardDuration(percentLegList,detailList);
        }
        return detailList;
    }
    public static void getClassify(String pattern) {
        HashMap<String,List<String>> classifyFile = Calculate.classify(Integer.valueOf(45),
                            new File(LOCATION_FOLDER), "\\d+:\\d+_"+pattern+"$");
       // List<String> onTimeFileList = classifyFile.get("onTime");
        List<String> lateTimeList = classifyFile.get("lateTime");

        List<String> lateTimeDetailList = getDetail(lateTimeList, 80);
       // List<String> onTimeDetailList = getDetail(onTimeFileList, 80);
       // ExportUtil.exportToFile(onTimeDetailList, "onTimeDetail_"+pattern+"_80_1.csv");
        ExportUtil.exportToFile(lateTimeDetailList, "lateTime_"+pattern+"_80.csv");
    }

    public static void main(String[] args) {
        getClassify("CC-AS");
    }
}
