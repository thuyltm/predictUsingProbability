package bk.master.classify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import bk.master.util.ExportUtil;

public class StuffCode {
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
         List<String> result = new ArrayList<String>();
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
             int size = classifyR.size();
             for (int i = 0; i < size; i++) {
                 String predict = classifyR.get(i);
                 String real = classifyRouteSuffle.get(i);
                 if (!predict.equalsIgnoreCase(real)) {
                     result.add(predict);
                 }
             }
             br.close();
             ExportUtil.exportAccumulateFile(result, "compare.txt");
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
}
