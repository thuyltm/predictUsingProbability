package bk.master.classify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import bk.master.util.ExportUtil;

public class StuffCode {
    public static void getTestData() {
        String pattern = "AS-CC";
        File file = new File("onTimeDetail_"+pattern+"_80_5.csv");
        if (file.exists()) {
            file.delete();
        }
        file = new File("lateTimeDetail_"+pattern+"_80_5.csv");
        if (file.exists()) {
            file.delete();
        }
        String[] monthList = {"09"};
        for (String month : monthList) {
            String locationFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/"+month+"/location/";
            String distanceFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/"+month+"/distance/";
            BatchJob.getClassify(pattern,locationFolder,distanceFolder);
        }
        StepFilter.getStep("onTimeDetail_AS-CC_80.csv", "onTimeDetail_AS-CC_100.csv");
        StepFilter.getStep("lateTimeDetail_AS-CC_80.csv", "lateTimeDetail_AS-CC_100.csv");
        StepFilter.getStep80("onTimeDetail_AS-CC_80.csv", "onTimeDetail_AS-CC_80_1.csv");
        StepFilter.getStep80("lateTimeDetail_AS-CC_80.csv", "lateTimeDetail_AS-CC_80_1.csv");
        StepFilter.classifiedStepInRoute("onTimeDetail_AS-CC_80_1.csv","onTimeClassify_AS-CC_80.csv");
        StepFilter.classifiedStepInRoute("lateTimeDetail_AS-CC_80_1.csv","lateTimeClassify_AS-CC_80.csv");
        File exportFile = new File("classifyRoute_AS-CC.csv");
        if (exportFile.exists()) {
            exportFile.delete();
        }
        StepFilter.getClassifiedStepInRoute("onTimeClassify_AS-CC_80.csv","onTime","classifyRoute_AS-CC.csv");
        StepFilter.getClassifiedStepInRoute("lateTimeClassify_AS-CC_80.csv","lateTime","classifyRoute_AS-CC.csv");
        Check.shuffle("classifyRoute_AS-CC_Test.csv", "classifyRoute_AS-CC_Suffle.csv");
    }
    public static void cutLastString(){
        List<String> result = new ArrayList<String>();
        try {
            CSVReader reader = new CSVReader(new FileReader("classifyRouteSuffle.csv"), ',');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                StringBuilder dataList = new StringBuilder();
                dataList.append(nextLine[0]+",");
                dataList.append(nextLine[1]+",");
                dataList.append(nextLine[2]+",");
                dataList.append(nextLine[3]+",");
                dataList.append(nextLine[4]);
                result.add(dataList.toString());
            }
            reader.close();
            ExportUtil.exportAccumulateFile(result, "testData.txt");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void compareResult() {
        List<String> classifyR = new ArrayList<String>();
        List<String> classifyRouteSuffle = new ArrayList<String>();
        Map<String, String> calculateOnTime = new HashMap<String, String>();
        List<String> wrongResult = new ArrayList<String>();
        List<String> correctResult = new ArrayList<String>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("classifyR.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                classifyR.add(line);
            }
            br.close();
            br = new BufferedReader(new FileReader("classifyRouteSuffle.csv"));
            while ((line = br.readLine()) != null) {
                classifyRouteSuffle.add(line);
            }
            br.close();
            br = new BufferedReader(new FileReader("calculateOnTime.csv"));
            while ((line = br.readLine()) != null) {
                String pattern = line.substring(0, line.lastIndexOf(","));
                String prob = line.substring(line.lastIndexOf(","));
                calculateOnTime.put(pattern, prob);
            }
            br.close();
            int size = classifyR.size();
            for (int i = 0; i < size; i++) {
                String predict = classifyR.get(i);
                String real = classifyRouteSuffle.get(i);
                String pattern = predict.substring(0, predict.lastIndexOf(","));
                String prob = calculateOnTime.get(pattern);
                if (!predict.equalsIgnoreCase(real)) {
                    wrongResult.add(real+prob);
                } else {
                    correctResult.add(predict+prob);
                }
            }
            ExportUtil.exportAccumulateFile(wrongResult, "wrongResult.txt");
            ExportUtil.exportAccumulateFile(correctResult, "correctResult.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
