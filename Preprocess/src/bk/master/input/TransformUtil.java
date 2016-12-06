package bk.master.input;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import bk.master.input.model.Leg;
import bk.master.input.model.Route;

public class TransformUtil {
    public static void getFinishTimeFromCCToAS(String departure, String destination,
                                           File inputFolder, String outputFile){
        File[] fileList = inputFolder.listFiles();
        for (File inputFile : fileList) {
            List<Route> routeList = InputUtil.loadFinishTime(inputFile.getAbsolutePath());
            ExportUtil.exportFinishTime(departure, destination, routeList, outputFile);
        }
    }
    public static void mergeToMoveCSV(String[] sourceFileList, String destinateFile,
                                    String source, String destination) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(destinateFile, true),
                    CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
            writer.writeNext(new String[]{"id","source name","destination name",
                            "departure time","arrival time","seconds","duration"});
            CSVReader reader = null;
            int count = 1;
            for (int i=0;i<sourceFileList.length;i++) {
                reader = new CSVReader(new FileReader(sourceFileList[i]), ',');
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                   if (nextLine != null && nextLine[1].trim().equals(source)
                       && nextLine[2].trim().equals(destination)) {
                      nextLine[0]=String.valueOf(count);
                      writer.writeNext(nextLine);
                      count++;
                   }
                }
            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<Leg> aggregate(List<Leg> legList, int minDistance, long totalDuration) {
        DecimalFormat df2 = new DecimalFormat("#.####");
        List<Leg> newLegList = new ArrayList<Leg>();
        int max = legList.size();
        int i = 0;
        int j = 1;
        Leg firstLeg, lastLeg;
        int distance = 0;
        long duration = 0;
        while (i<max && j<max) {
            firstLeg = legList.get(i);
            lastLeg = legList.get(j);
            distance = firstLeg.getDistance()+lastLeg.getDistance();
            duration = firstLeg.getDuration()+lastLeg.getDuration();
            while (distance < minDistance) {
                j++;
                if (j==max) {
                    break;
                }
                lastLeg = legList.get(j);
                distance = distance + lastLeg.getDistance();
                duration = duration + lastLeg.getDuration();
            }
            newLegList.add(new Leg(firstLeg.getStartIndex(), lastLeg.getEndIndex(),
                    firstLeg.getStartLat(), firstLeg.getStartLng(),
                       lastLeg.getEndLat(), lastLeg.getEndLng(), firstLeg.getStartPlace(),
                       lastLeg.getEndPlace(), distance, distance+"m", duration,
                       TimeTranslatorUtil.covertToReadableFormat(duration)));
            i = j+1;
            j = i+1;
            distance = 0;
        }
        return newLegList;
    }
    public static void accumulate(String distanceFolder, String newLegFolder, int part) {
        Pattern p = Pattern.compile("_\\d+_\\d+:\\d+:\\d+");
        File dir = new File(distanceFolder);
        File[] directoryList = dir.listFiles();
        for (File file : directoryList) {
            Matcher m = p.matcher(file.getName());
            String endName = "NO_FOUND";
            while (m.find()) {
                endName = m.group();
            }
            TransformUtil.accumulateOneRoute(file.getAbsolutePath(), newLegFolder+"newLeg" + endName,part);
        }
    }
    public static void accumulateOneRoute(String distanceFile, String newLegFile, int part) {
        List<Leg> oldLegList = InputUtil.loadLegList(distanceFile);
        long totalDuration = 0;
        int totalDistance = 0;
        for (Leg leg: oldLegList) {
            totalDistance = totalDistance + leg.getDistance();
            totalDuration = totalDuration + leg.getDuration();
        }
        int orginalPart = totalDistance/part;
        List<Leg> newLegList = aggregate(oldLegList, orginalPart, totalDuration);
        if (newLegList.size()==part) {
            ExportUtil.exportToLegFile(newLegList, newLegFile+"_"+totalDistance+".csv", false);
            return;
        }
        int denominator = part;
        int maxChange = 1;
        int updatePart = orginalPart - orginalPart/denominator;
        newLegList = aggregate(oldLegList, updatePart, totalDuration);
        while (newLegList.size()!=part&&maxChange<part) {
            if (newLegList.size()>part) {
                denominator = denominator+1;
            } else {
                denominator = denominator-1;
            }
            updatePart = orginalPart - orginalPart/denominator;
            newLegList = aggregate(oldLegList, updatePart, totalDuration);
            maxChange++;
        }
        if (newLegList.size()==part) {
            ExportUtil.exportToLegFile(newLegList, newLegFile+"_"+totalDistance+".csv", false);
            return;
        }
    }
}
