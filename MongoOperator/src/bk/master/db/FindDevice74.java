package bk.master.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import bk.master.input.ExportUtil;
import bk.master.input.InputUtil;
import bk.master.input.TimeTranslatorUtil;
import bk.master.input.model.Location;
import bk.master.input.model.Move;

public class FindDevice74 {
    private static String DEPART_STATION = "benXeAnSuong";
    private static String DEST_STATION = "benXeCuChi";
    private static String DEVICE_LIST = "result.txt";
    public static void main(String[] args) {
        //getCandidate();
        //checkCandidateByMap();
        int month = 10;
        for (int i = 14; i <=14; i++) {
            String day = String.valueOf(i);
            //String day ="31";
            String scheduleFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/"
                    +month+"/schedule/"+day+"/";
            String switchScheduleFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/"
                     +month+"/merge_schedule/"+day+"/";
            String locationCsvFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/"
                     +month+"/location/"+day+"/";
            String locationJSonFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/"
                     +month+"/location-json/"+day+"/";
            String invokeDate = "2016-10-"+day;
            getSchedule(invokeDate, scheduleFolder, switchScheduleFolder);
            getLocationData(switchScheduleFolder, day, locationJSonFolder, locationCsvFolder);
        }
    }
    public static void getSchedule(String invokeDate, String scheduleFolder, String switchScheduleFolder) {
        getTimeInStation(DEPART_STATION, invokeDate, scheduleFolder);
        getTimeInStation(DEST_STATION, invokeDate, scheduleFolder);
        mergeSchedule(scheduleFolder, switchScheduleFolder);
    }
    public static void getLocationData(String switchScheduleFolder, String day,
                    String locationJSonFolder, String locationCsvFolder) {
        List<String> deviceList = InputUtil.loadInput(DEVICE_LIST);
        for (String device : deviceList) {
            List<Move> moveList = InputUtil.loadCsvDate(switchScheduleFolder + device + ".csv");
            int length = moveList.size();
            for (int i = 0; i < length; i++) {
                Move mv = moveList.get(i);
                String startTime = mv.getDepartTime();
                String endTime = mv.getDestTime();
                long duration = TimeTranslatorUtil.substractDate(mv.getOrginalDestTime(), mv.getOrginalDepartTime());
                String durationText = TimeTranslatorUtil.covertToReadableFormat(duration);
                String newFileName = device + "_"+day+"_"+ i + "_" + durationText;
                DBUtil.findLocationDeviceByTime(TimeTranslatorUtil.convertDateToISODate(startTime),
                        TimeTranslatorUtil.convertDateToISODate(endTime), device,
                        locationJSonFolder + newFileName + ".json",
                        //null,
                        locationCsvFolder + newFileName + ".csv");
            }
        }
    }
    public static void mergeSchedule(String scheduleFolder, String switchScheduleFolder) {
        List<String> deviceList = InputUtil.loadInput(DEVICE_LIST);
        for (String device : deviceList) {
            try {
                List<Move> result = TimeTranslatorUtil.mergeSchedule(
                        scheduleFolder + device + DEPART_STATION + ".csv",
                        scheduleFolder + device + DEST_STATION + ".csv",
                        DEPART_STATION, DEST_STATION);
                ExportUtil.exportToScheduleFile(result, switchScheduleFolder + device + ".csv");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void getTimeInStation(String stationFile, String invokeDate, String scheduleFolder) {
        String startTime = TimeTranslatorUtil.convertDateToISODate(invokeDate + " 04:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(invokeDate + " 22:00:00");
        List<Location> stationLngLat = InputUtil.loadInputLocation(stationFile+".txt");
        List<String> deviceList = InputUtil.loadInput(DEVICE_LIST);
        int length = deviceList.size();
        for (int i = 0; i < length; i++) {
            String device = deviceList.get(i);
            DBUtil.findTimeDeviceWithinLocation(stationLngLat, device, startTime, endTime,
                    scheduleFolder + device + stationFile +".csv");
        }
    }
    public static void checkCandidateByMap(String invokeDate, String routeFolder) {
        String startTime = TimeTranslatorUtil.convertDateToISODate(invokeDate + " 04:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(invokeDate + " 22:00:00");
        List<String> deviceList = InputUtil.loadInput(DEVICE_LIST);
        int length = deviceList.size();
        for (int i = 0; i < length; i++) {
            String device = deviceList.get(i);
            DBUtil.findLocationDeviceByTime(startTime, endTime, device,
                    routeFolder + device+".json", null);
        }
    }
    public static void getCandidate(String invokeDate) {
       // getCandidateStopInStation();
        getCandidateRunToStation(invokeDate);
        getCandidateDevice("device" + DEPART_STATION + "04:23.txt",
                  "device" + DEST_STATION + "04:23.txt", "result1.txt");
        getCandidateDevice("device" + DEST_STATION + "04:23.txt",
                "device" + DEPART_STATION + "04:23.txt", "result2.txt");
        getCandidateDevice("result2.txt", "result1.txt", DEVICE_LIST);
    }
    private static void getCandidateStopInStation(String invokeDate) {
        String startTime = TimeTranslatorUtil.convertDateToISODate(invokeDate + " 00:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(invokeDate + " 04:00:00");

        List<Location> departLngLat = InputUtil.loadInputLocation(DEPART_STATION + ".txt");
        DBUtil.findDistinctDeviceWithinLocation(departLngLat, startTime, endTime, "device" + DEPART_STATION + "00:04.txt");

        List<Location> destLngLat = InputUtil.loadInputLocation(DEST_STATION + ".txt");
        DBUtil.findDistinctDeviceWithinLocation(destLngLat, startTime, endTime, "device" + DEST_STATION + "00:04.txt");
    }
    private static void getCandidateRunToStation(String invokeDate) {
        String startTime = TimeTranslatorUtil.convertDateToISODate(invokeDate + " 04:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(invokeDate + " 23:00:00");

        List<Location> departLatLng = InputUtil.loadInputLocation(DEPART_STATION + ".txt");
        DBUtil.findDistinctDeviceWithinLocation(departLatLng, startTime, endTime, "device" + DEPART_STATION + "04:23.txt");

        List<Location> destLngLat = InputUtil.loadInputLocation(DEST_STATION  + ".txt");
        DBUtil.findDistinctDeviceWithinLocation(destLngLat, startTime, endTime, "device" + DEST_STATION + "04:23.txt");
    }
    private static void getCandidateDevice(String compareFile1, String compareFile2, String resultFile) {
        HashMap<String, String> deviceDepartList = createHashMap(compareFile1);
        HashMap<String, String> deviceRunningList = createHashMap(compareFile2);

        Iterator<String> iterator = deviceDepartList.keySet().iterator();
        List<String> result = new ArrayList<String>();
        while(iterator.hasNext()) {
            String candidate = iterator.next();
            if (deviceRunningList.containsKey(candidate)) {
                result.add(candidate);
            }
        }
        ExportUtil.exportToFile(result, resultFile);
    }
    private static HashMap<String, String> createHashMap(String inputFile) {
        List<String> result = new ArrayList<String>();
        HashMap<String, String> deviceList = new HashMap<String, String>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();
            while (line != null) {
                deviceList.put(line.trim(), line.trim());
                line = br.readLine();
            }
            br.close();
            Iterator<String> iterator = deviceList.keySet().iterator();
            while(iterator.hasNext()) {
                result.add(iterator.next());
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return deviceList;
    }
}
