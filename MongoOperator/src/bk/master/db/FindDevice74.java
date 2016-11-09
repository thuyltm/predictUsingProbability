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
    private static String SCHEDULE_FOLDER = "/home/thuy/workspace/MongoOperator/schedule/";
    private static String SWITCH_SCHEDULE_FOLDER = "/home/thuy/workspace/MongoOperator/merge_schedule/";
    private static String ROUTE_FOLDER = "/home/thuy/workspace/MongoOperator/route/";
    private static String LOCATION_CSV_FOLDER = "/home/thuy/workspace/MongoOperator/location/";
    private static String LOCATION_JSON_FOLDER = "/home/thuy/workspace/MongoOperator/location-json/";
    public static void main(String[] args) {
        getLocationData();
    }
    public static void getLocationData() {
        List<String> deviceList = InputUtil.loadInput("result.txt");
        for (String device : deviceList) {
            List<Move> moveList = InputUtil.loadCsvDate(SWITCH_SCHEDULE_FOLDER + device + "_18.csv");
            int length = moveList.size();
            for (int i = 0; i < length; i++) {
                Move mv = moveList.get(i);
                String startTime = mv.getDepartTime();
                String endTime = mv.getDestTime();
                long duration = TimeTranslatorUtil.substractDate(mv.getOrginalDestTime(), mv.getOrginalDepartTime());
                String durationText = TimeTranslatorUtil.covertToReadableFormat(duration);
                String newFileName = device + "_18_"+ i + "_" + durationText;
                DBUtil.findLocationDeviceByTime(TimeTranslatorUtil.convertDateToISODate(startTime),
                        TimeTranslatorUtil.convertDateToISODate(endTime), device,
                        //LOCATION_JSON_FOLDER + newFileName + ".json",
                        null,
                        LOCATION_CSV_FOLDER + newFileName + ".csv");
            }
        }
    }
    public static void mergeSchedule(String deviceFile, String date,
            String departName, String destName) {
        List<String> deviceList = InputUtil.loadInput(deviceFile);
        for (String device : deviceList) {
            List<Move> result = TimeTranslatorUtil.mergeSchedule(
                    SCHEDULE_FOLDER + device + date + departName + ".csv",
                    SCHEDULE_FOLDER + device + date + destName + ".csv",
                    departName, destName);
            ExportUtil.exportToScheduleFile(result, SWITCH_SCHEDULE_FOLDER + device + date + ".csv");
        }
    }
    public static void getTimeInStation(String stationLatLng, String deviceFile, String date) {
        String startTime = TimeTranslatorUtil.convertDateToISODate(date + " 00:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(date + " 04:00:00");
        List<Location> cs2LngLat = InputUtil.loadInputLocation(stationLatLng+".txt");
        List<String> deviceList = InputUtil.loadInput(deviceFile);
        int length = deviceList.size();
        for (int i = 0; i < length; i++) {
            String device = deviceList.get(i);
            DBUtil.findTimeDeviceWithinLocation(cs2LngLat, device, startTime, endTime, SCHEDULE_FOLDER + device +
                                date + stationLatLng +".csv");
        }
    }
    public static void checkCandidateByMap(String date) {
        String startTime = TimeTranslatorUtil.convertDateToISODate(date + " 04:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(date + " 22:00:00");
        List<String> deviceList = InputUtil.loadInput("result.txt");
        int length = deviceList.size();
        for (int i = 0; i < length; i++) {
            String device = deviceList.get(i);
            DBUtil.findLocationDeviceByTime(startTime, endTime, device, ROUTE_FOLDER + device+".json", null);
        }
    }
    public static void getCandidate(String date, String departStationLatLng, String destStationLatLng) {
        getCandidateStopInStation(date, departStationLatLng, destStationLatLng);
        getCandidateRunToStation(date, departStationLatLng, destStationLatLng);
        getCandidateDevice("device_" + departStationLatLng + "00:04.txt",
                  "device_" + destStationLatLng + "04:23.txt", "result1.txt");
        getCandidateDevice("device_" + destStationLatLng + "00:04.txt",
                "device_" + departStationLatLng + "04:23.txt", "result2.txt");
    }
    private static void getCandidateStopInStation(String date, String departStationLatLng, String destStationLatLng) {
        String startTime = TimeTranslatorUtil.convertDateToISODate(date + " 00:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(date + " 04:00:00");

        List<Location> bKLngLat = InputUtil.loadInputLocation(departStationLatLng + ".txt");
        DBUtil.findDistinctDeviceWithinLocation(bKLngLat, startTime, endTime, "device_" + departStationLatLng + "00:04.txt");

        List<Location> cs2LngLat = InputUtil.loadInputLocation(destStationLatLng + ".txt");
        DBUtil.findDistinctDeviceWithinLocation(cs2LngLat, startTime, endTime, "device" + destStationLatLng + "00:04.txt");
    }
    private static void getCandidateRunToStation(String date, String departStationLatLng, String destStationLatLng) {
        String startTime = TimeTranslatorUtil.convertDateToISODate(date + " 04:00:00");
        String endTime = TimeTranslatorUtil.convertDateToISODate(date + " 23:00:00");

        List<Location> bKLngLat = InputUtil.loadInputLocation(departStationLatLng + ".txt");
        DBUtil.findDistinctDeviceWithinLocation(bKLngLat, startTime, endTime, "device_" + departStationLatLng + "04:23.txt");

        List<Location> cs2LngLat = InputUtil.loadInputLocation(destStationLatLng  + ".txt");
        DBUtil.findDistinctDeviceWithinLocation(cs2LngLat, startTime, endTime, "device_" + destStationLatLng + "04:23.txt");
    }
    private static void getCandidateDevice(String departStation, String destStation, String candidateFile) {
        HashMap<String, String> deviceDepartList = createHashMap(departStation);
        HashMap<String, String> deviceRunningList = createHashMap(destStation);

        Iterator<String> iterator = deviceDepartList.keySet().iterator();
        List<String> result = new ArrayList<String>();
        while(iterator.hasNext()) {
            String candidate = iterator.next();
            if (deviceRunningList.containsKey(candidate)) {
                result.add(candidate);
            }
        }
        ExportUtil.exportToFile(result, candidateFile);
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
