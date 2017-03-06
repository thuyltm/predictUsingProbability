package bk.master.classify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

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
    public static void shuffle(String inputFile, String outputFile) {
        List<String> data = new ArrayList<String>();
        List<String> suffleData = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                data.add(nextLine);
            }
            reader.close();
            Set<Integer> randomIndexList = generateRandomNotRepeat(190);
            for (Integer randomIndex : randomIndexList) {
                suffleData.add(data.get(randomIndex));
            }
            ExportUtil.exportToFile(suffleData, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Set<Integer> generateRandomNotRepeat(int size) {
        // Note: use LinkedHashSet to maintain insertion order
        Set<Integer> randomNumberList = new LinkedHashSet<Integer>();
        ArrayList<Integer> list = new ArrayList<Integer>(size);
        for(int i = 0; i < size; i++) {
            list.add(i);
        }
        while(list.size() > 1) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, list.size()-1);
            randomNumberList.add(list.get(randomNum));
            list.remove(randomNum);
        }
        randomNumberList.add(list.get(0));
        list.remove(0);
        return randomNumberList;
    }
    public static void main(String[] args) {
        shuffle("classifyRouteShort.csv", "classifyRouteSuffle.csv");
    }
}
