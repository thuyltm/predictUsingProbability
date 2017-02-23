package bk.master.classify;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bk.master.input.model.Leg;
import bk.master.util.ExportUtil;
import bk.master.util.InputUtil;
import bk.master.util.TimeTranslatorUtil;

public class BatchJob {

    public static void getDistance() {
        String month = "10";
        String locationFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/"+month+"/location/";
        String distanceFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/"+month+"/distance/";
        for (int i = 10; i <= 13; i++) {
            String numberFolder = String.valueOf(i);
            if (i<10) {
                numberFolder = "0"+i;
            }
            File folder = new File(locationFolder + numberFolder);
            File[] dataFile = folder.listFiles();
            if (dataFile != null) {
                for (File file : dataFile) {
                    Calculate.calculateDistance(file, distanceFolder+numberFolder+"/");
                }
            }
        }
    }
    public static List<String> getDetail(List<String> fileList, Integer percentFTime, String distanceFolder) {
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
            File file = new File(distanceFolder + numberFolder + "/distance_" + fileName +".csv");
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
            GroupDuration.groupToStandardDuration(legList,detailList);
            GroupDuration.groupToStandardDuration(percentLegList,detailList);
        }
        return detailList;
    }
    public static void getClassify(String pattern, String locationFolder, String distanceFolder) {
        HashMap<String,List<String>> classifyFile = Calculate.classify(Integer.valueOf(45),
                            new File(locationFolder), "\\d+:\\d+_"+pattern+"$");
        //onTime
        List<String> onTimeFileList = classifyFile.get("onTime");
        List<String> onTimeDetailList = getDetail(onTimeFileList, 80, distanceFolder);
        ExportUtil.exportAccumulateFile(onTimeDetailList, "onTimeDetail_"+pattern+"_80_5.csv");

        //lateTime
        List<String> lateTimeFileList = classifyFile.get("lateTime");
        List<String> lateTimeDetailList = getDetail(lateTimeFileList, 80, distanceFolder);
        ExportUtil.exportAccumulateFile(lateTimeDetailList, "lateTimeDetail_"+pattern+"_80_5.csv");
    }
    public static void getClassifyAllMonth() {
        String pattern = "CC-AS";
        File file = new File("onTimeDetail_"+pattern+"_80_5.csv");
        if (file.exists()) {
            file.delete();
        }
        file = new File("lateTimeDetail_"+pattern+"_80_5.csv");
        if (file.exists()) {
            file.delete();
        }
        String[] monthList = {"09","10","11"};
        for (String month : monthList) {
            String locationFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/"+month+"/location/";
            String distanceFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/"+month+"/distance/";
            getClassify(pattern,locationFolder,distanceFolder);
        }
    }

    public static void main(String[] args) {
        /*List<String> fileList = new ArrayList<String>();
        String distanceFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/09/distance/";
        fileList.add("53N5174_19_6_38:28_CC-AS");
        List<String> result = BatchJob.getDetail(fileList, 80, distanceFolder);
        for (String data : result) {
            System.out.println(data);
        }*/
        getClassifyAllMonth();
    }
}
