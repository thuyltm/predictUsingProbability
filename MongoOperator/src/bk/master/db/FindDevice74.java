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
    private static String MONTH = "10";
    private static String DAY = "05";
    private static String SCHEDULE_FOLDER = "/home/thuy1/git/predictUsingProbability/MongoOperator/"
                            +MONTH+"/schedule/"+DAY+"/";
    private static String SWITCH_SCHEDULE_FOLDER = "/home/thuy1/git/predictUsingProbability/MongoOperator/"
                            +MONTH+"merge_schedule/"+DAY+"/";
    private static String ROUTE_FOLDER = "/home/thuy1/git/predictUsingProbability/MongoOperator/"
                            +MONTH+"route/"+DAY+"/";
    private static String LOCATION_CSV_FOLDER = "/home/thuy1/git/predictUsingProbability/MongoOperator/"
                            +MONTH+"location/"+DAY+"/";
    private static String LOCATION_JSON_FOLDER = "/home/thuy1/git/predictUsingProbability/MongoOperator/"
                            +MONTH+"location-json/"+DAY+"/";
    private static String DEPART_STATION = "benXeAnSuong";
    private static String DEST_STATION = "benXeCuChi";
    private static String INVOKE_DATE = "2016-10-"+DAY;
    private static String DEVICE_LIST = "result.txt";
    public static void main(String[] args) {
        //getCandidate();
        //checkCandidateByMap();
        getSchedule();
    }
    public static void getSchedule() {
        getTimeInStation(DEPART_STATION,INVOKE_DATE);
        getTimeInStation(DEST_STATION,INVOKE_DATE);
        mergeSchedule();
        getLocationData();
    }
    public static void getLocationData() {
        List<String> deviceList = InputUtil.loadInput(DEVICE_LIST);
        for (String device : deviceList) {
            List<Move> moveList = InputUtil.loadCsvDate(SWITCH_SCHEDULE_FOLDER + device + ".csv");
            int length = moveList.size();
            for (int i = 0; i < length; i++) {
                Move mv = moveList.get(i);
                String startTime = mv.getDepartTime();
                String endTime = mv.getDestTime();
                long duration = TimeTranslatorUtil.substractDate(mv.getOrginalDestTime(), mv.getOrginalDepartTime());
                String durationText = TimeTranslatorUtil.covertToReadableFormat(duration);
                String newFileName = device + "_02_"+ i + "_" + durationText;
                DBUtil.findLocationDeviceByTime(TimeTranslatorUtil.convertDateToISODate(startTime),
                        TimeTranslatorUtil.convertDateToISODate(endTime), device,
                        LOCATION_JSON_FOLDER + newFileName + ".json",
                        //null,
                        LOCATION_CSV_FOLDER + newFileName + ".csv");
            }
        }
    }
    public static void mergeSchedule() {
        List<String> deviceList = InputUtil.loadInput(DEVICE_LIST);
        for (String device : deviceList) {
            try {
                List<Move> result = TimeTranslatorUtil.mergeSchedule(
                        SCHEDULE_FOLDER + device + DEPART_STATION + ".csv",
                        SCHEDULE_FOLDER + device + DEST_STATION + ".csv",
                        DEPART_STATION, DEST_STATION);
                ExportUtil.exportToScheduleFile(result, SWITCH_SCHEDULE_FOLDER + device + ".csv");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void getTimeInStation(String stationFile, String INVOKE_DATE) {
        String startTime = TimeTranslatorUtil.convertDateToISODate(INVOKE_DATE + " 04:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(INVOKE_DATE + " 22:00:00");
        List<Location> stationLngLat = InputUtil.loadInputLocation(stationFile+".txt");
        List<String> deviceList = InputUtil.loadInput(DEVICE_LIST);
        int length = deviceList.size();
        for (int i = 0; i < length; i++) {
            String device = deviceList.get(i);
            DBUtil.findTimeDeviceWithinLocation(stationLngLat, device, startTime, endTime, SCHEDULE_FOLDER + device + stationFile +".csv");
        }
    }
    public static void checkCandidateByMap() {
        String startTime = TimeTranslatorUtil.convertDateToISODate(INVOKE_DATE + " 04:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(INVOKE_DATE + " 22:00:00");
        List<String> deviceList = InputUtil.loadInput(DEVICE_LIST);
        int length = deviceList.size();
        for (int i = 0; i < length; i++) {
            String device = deviceList.get(i);
            DBUtil.findLocationDeviceByTime(startTime, endTime, device,
                    ROUTE_FOLDER + device+".json", null);
        }
    }
    public static void getCandidate() {
       // getCandidateStopInStation();
        getCandidateRunToStation();
        getCandidateDevice("device" + DEPART_STATION + "04:23.txt",
                  "device" + DEST_STATION + "04:23.txt", "result1.txt");
        getCandidateDevice("device" + DEST_STATION + "04:23.txt",
                "device" + DEPART_STATION + "04:23.txt", "result2.txt");
        getCandidateDevice("result2.txt", "result1.txt", DEVICE_LIST);
    }
    private static void getCandidateStopInStation() {
        String startTime = TimeTranslatorUtil.convertDateToISODate(INVOKE_DATE + " 00:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(INVOKE_DATE + " 04:00:00");

        List<Location> departLngLat = InputUtil.loadInputLocation(DEPART_STATION + ".txt");
        DBUtil.findDistinctDeviceWithinLocation(departLngLat, startTime, endTime, "device" + DEPART_STATION + "00:04.txt");

        List<Location> destLngLat = InputUtil.loadInputLocation(DEST_STATION + ".txt");
        DBUtil.findDistinctDeviceWithinLocation(destLngLat, startTime, endTime, "device" + DEST_STATION + "00:04.txt");
    }
    private static void getCandidateRunToStation() {
        String startTime = TimeTranslatorUtil.convertDateToISODate(INVOKE_DATE + " 04:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(INVOKE_DATE + " 23:00:00");

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
