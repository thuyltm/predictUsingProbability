package bk.master.classify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import bk.master.util.ExportUtil;

public class Check {
    public static void getReplicate(String inputFile, String outputFile) {
        Map<String, Set<String>> resultList = new HashMap<String, Set<String>>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));;
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                String key = nextLine.substring(0, nextLine.lastIndexOf(","));
                String value = nextLine.substring(nextLine.lastIndexOf(",")+1);
                Set<String> lineList = new TreeSet<String>();
                if (resultList.get(key) != null) {
                    lineList = resultList.get(key);
                }
                lineList.add(value);
                resultList.put(key, lineList);
            }
            reader.close();
            ExportUtil.exportToFile(resultList, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        getReplicate("classifyRoute.csv", "classifyRouteRep_Org.csv");
        getReplicate("classifyRouteV2.csv", "classifyRouteRep.csv");
    }
}
