package bk.master.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import au.com.bytecode.opencsv.CSVReader;

public class StepUtil {
    public static void getStep(String inputFile, String outputFile) {
        List<String> result = new ArrayList<String>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();
            int lineNumber = 0;
            while (line != null) {
                lineNumber++;
                if (lineNumber%7==0) {
                    result.add(line.substring(1, line.length()-2));
                }
                line = br.readLine();
            }
            br.close();
            ExportUtil.exportToFile(result, outputFile);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getFreqStep(String inputFile, String outputFile) {
        HashMap<Integer, Integer> resultList = new HashMap<Integer, Integer>();
        try {
            CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (int i = 0; i < nextLine.length; i++) {
                    String key = nextLine[i].trim();
                    if (!"".equals(key)) {
                        Integer distance = Integer.valueOf(key);
                        Integer repeat = 0;
                        if (resultList.get(distance) != null) {
                            repeat = resultList.get(distance);
                        }
                        resultList.put(distance, repeat+1);
                    }
                }
            }
            reader.close();
            ExportUtil.exportToFile(resultList, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getFreqStep(String inputFile1, String inputFile2, String outputFile) {
        HashMap<Integer, Integer> resultList = new HashMap<Integer, Integer>();
        try {
            CSVReader reader = new CSVReader(new FileReader(inputFile1), '|');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Integer distance = Integer.valueOf(nextLine[0]);
                Integer repeat = Integer.valueOf(nextLine[1]);
                resultList.put(distance, repeat);
            }
            reader.close();
            reader = new CSVReader(new FileReader(inputFile2), '|');
            nextLine = null;
            while ((nextLine = reader.readNext()) != null) {
                Integer distance = Integer.valueOf(nextLine[0]);
                Integer repeat = Integer.valueOf(nextLine[1]);
                if (resultList.get(distance) != null) {
                    repeat = repeat + resultList.get(distance);
                }
                resultList.put(distance, repeat);
            }
            reader.close();
            Map<Integer, List<Integer>> repeatList = new TreeMap<Integer, List<Integer>>();
            for (Integer distance: resultList.keySet()) {
                Integer repeat = resultList.get(distance);
                List<Integer> distanceList = new ArrayList<Integer>();
                if (repeatList.get(repeat) != null) {
                    distanceList = repeatList.get(repeat);
                }
                distanceList.add(distance);
                repeatList.put(repeat, distanceList);
            }
            //ExportUtil.exportToFile(repeatList, outputFile);
            List<Integer> keyList = new ArrayList<Integer>();
            for (Integer key : repeatList.keySet()) {
                keyList.add(key);
            }
            ExportUtil.exportToFile(keyList, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //getStep("lateTime_CC-AS_80.csv", "lateTime_CC-AS_80_1.csv");
        //getFreqStep("lateTime_CC-AS_80_1.csv", "lateTime_CC-AS_80_2.csv");
       getFreqStep("onTimeDetail_CC-AS_80_2.csv","lateTimeDetail_CC-AS_80_2.csv","Freq_CC-AS_80.csv");
    }
}
