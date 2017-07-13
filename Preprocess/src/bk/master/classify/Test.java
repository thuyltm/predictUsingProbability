package bk.master.classify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import bk.master.util.ExportUtil;

public class Test {

    public static void main(String[] args) {
        List<String> classifyR = new ArrayList<String>();
        List<String> classifyRouteSuffle = new ArrayList<String>();
        Map<String, String> calculateOnTime = new HashMap<String, String>();
        List<String> wrongResult = new ArrayList<String>();
        List<String> correctResult = new ArrayList<String>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("classify.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                classifyR.add(line);
            }
            br.close();
            br = new BufferedReader(new FileReader("classifyRoute_AS-CC_Suffle.csv"));
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
