package bk.master.input;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import au.com.bytecode.opencsv.CSVReader;
import bk.master.input.model.Leg;
import bk.master.input.model.Location;

public class Calculate {
    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
    public static void calculateDistance(File file, String outputPath) {
        List<Location> travelPointList = InputUtil.loadInputDateLocation(file.getAbsolutePath());
        List<Leg> legList = new ArrayList<Leg>();
        Location firstLoc, nextLoc;
        int size = travelPointList.size();
        for (int i=0;i<=size-2;i++) {
          firstLoc = travelPointList.get(i);
          nextLoc = travelPointList.get(i+1);
          long duration = TimeTranslatorUtil.substractDate(firstLoc.getOrginDate(), nextLoc.getOrginDate());
          double lat1 = (Double)firstLoc.getLatitude();
          double lng1 = (Double)firstLoc.getLongtitude();
          double lat2 = (Double)nextLoc.getLatitude();
          double lng2 = (Double)nextLoc.getLongtitude();
          int distance = Math.round(distFrom(lat1,lng1,lat2,lng2));
          legList.add(new Leg(i,i+1,lat1,lng1,lat2,lng2, distance, duration,
                      TimeTranslatorUtil.covertToReadableFormat(duration)));
        }
        ExportUtil.exportToLegFile(legList, outputPath + "/distance_"
                        + FilenameUtils.getBaseName(file.getName())+".csv", true);
    }
    public static HashMap<String,List<String>> classify(Integer standardFinishTime, File mainFolder, String regex) {
        List<String> onTimeFileList = new ArrayList<String>();
        List<String> lateTimeFileList = new ArrayList<String>();
        //String regex = "\\d+:\\d+_CC-AS$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        File[] folderList = mainFolder.listFiles();
        for (File folder: folderList) {
            File[] fileList = folder.listFiles();
            for (File file: fileList) {
                String fileName = FilenameUtils.getBaseName(file.getName());
                Matcher matcher = pattern.matcher(fileName);
                while (matcher.find()) {
                     String data = matcher.group();
                     Integer finishTime = Integer.valueOf(data.substring(0, data.indexOf(":")));
                     if (finishTime<=standardFinishTime) {
                         onTimeFileList.add(fileName);
                     } else {
                         lateTimeFileList.add(fileName);
                     }
                }
            }
        }
        HashMap<String,List<String>> data = new HashMap<String,List<String>>();
        data.put("onTime", onTimeFileList);
        data.put("lateTime", lateTimeFileList);
        return data;
    }
    public static void sort(String inputFile, String outputFile) {
        try {
            HashMap<Integer, List<String>> data = new HashMap<Integer, List<String>>();
            CSVReader reader = new CSVReader(new FileReader(inputFile), '|');
            String[] nextLine;
            int totalFreq = 0;
            while ((nextLine = reader.readNext()) != null) {
                int frequencies = Integer.valueOf(nextLine[1]);
                if (data.get(frequencies) == null) {
                    List<String> finishTimeList = new ArrayList<String>();
                    finishTimeList.add(nextLine[0]);
                    data.put(frequencies, finishTimeList);
                } else {
                    List<String> finishTimeList = data.get(frequencies);
                    finishTimeList.add(nextLine[0]);
                }
                totalFreq += frequencies;
            }
            Set<Integer> freqList = data.keySet();
            int length = freqList.size();
            int[] sortedFreqList = new int[length];
            int index = 0;
            for( Integer i : freqList ) {
                sortedFreqList[index++] = i;
            }
            Arrays.sort(sortedFreqList);
            BufferedWriter bw = ExportUtil.createBufferWriter(outputFile);
            float accValue = 0L;
            for(int i = length-1; i >= 0; i--) {
                int frequencies = sortedFreqList[i];
                String[] finishTimeArr = new String[data.get(frequencies).size()];
                finishTimeArr = data.get(frequencies).toArray(finishTimeArr);
                Arrays.sort(finishTimeArr);
                for (int j = 0; j < finishTimeArr.length; j++) {
                    accValue += frequencies;
                    bw.write(finishTimeArr[j]+"|"+frequencies);
                    bw.write("|"+(float)accValue/totalFreq);
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void replicate(String inputFile, String outputFile) {
        try {
            HashMap<String, Integer> data = new HashMap<String, Integer>();
            CSVReader reader = new CSVReader(new FileReader(inputFile), '|');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                data.put(nextLine[0], Integer.valueOf(nextLine[1]));
            }
            BufferedWriter bw = ExportUtil.createBufferWriter(outputFile);
            Set<String> keyList = data.keySet();
            Iterator<String> keyIterator = keyList.iterator();
            while(keyIterator.hasNext()){
                String key = keyIterator.next();
                Integer rep = data.get(key);
                for (int i = 0; i < rep; i++) {
                    bw.write(key);
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
