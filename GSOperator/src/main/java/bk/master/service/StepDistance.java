package bk.master.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import bk.master.input.ExportUtil;
import bk.master.input.InputUtil;
import bk.master.input.TimeTranslatorUtil;
import bk.master.input.TransformUtil;
import bk.master.input.model.Leg;
import bk.master.input.model.Location;

public class StepDistance {
    private static final String DISTANCE_API_KEY = "AIzaSyDtt2dNdvWREWfvLuntTma-FDAU8g431oE";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String NUMBER = "03";
    private static final String INPUT_FOLDER = "/home/thuy1/git/predictUsingProbability/MongoOperator/location/"+NUMBER+"/limit/";
    private static final String OUTPUT_FOLDER = "/home/thuy1/git/predictUsingProbability/GSOperator/newLeg/"+NUMBER+"/";

    public static void main(String[] args) {
       // Pattern p = Pattern.compile("_\\d+_\\d+:\\d+:\\d+");
        File dir = new File(INPUT_FOLDER);
        File[] directoryList = dir.listFiles();
        try {
            for (File file : directoryList) {
                /*Matcher m = p.matcher(file.getName());
                String endName = "NO_FOUND";
                while (m.find()) {
                    endName = m.group();
                }*/
                List<Location> travelPointList = InputUtil.loadInputDateLocation(file.getAbsolutePath());
                sendGet(travelPointList, OUTPUT_FOLDER + "distance_" + file.getName() + ".csv");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sendGet(List<Location> travelPointList, String outputFile) throws ClientProtocolException, IOException {
          List<Leg> legList = new ArrayList<Leg>();
          Location firstLoc, nextLoc;
          int size = travelPointList.size();
          for (int i=0;i<=size-2;i++) {
            firstLoc = travelPointList.get(i);
            nextLoc = travelPointList.get(i+1);
            String query = "origins="+firstLoc.getLatitude()+","+firstLoc.getLongtitude()+
                    "&destinations="+nextLoc.getLatitude()+","+nextLoc.getLongtitude();

            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?"+query+"&key="+DISTANCE_API_KEY;

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            // add request header
            request.addHeader("User-Agent", USER_AGENT);

            HttpResponse response = client.execute(request);

            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " +
                           response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

             StringBuffer result = new StringBuffer();
             String line = "";
             while ((line = rd.readLine()) != null) {
                 result.append(line);
             }
             System.out.println(result);
             JSONObject info = new JSONObject(result.toString());
             String originAddress = (String)((JSONArray)info.get("origin_addresses")).get(0);
             String destinationAddress = (String)((JSONArray)info.get("destination_addresses")).get(0);
             JSONArray rows = (JSONArray)info.get("rows");
             JSONArray element = (JSONArray)((JSONObject)rows.get(0)).get("elements");
             JSONObject distance = (JSONObject)((JSONObject)element.get(0)).get("distance");
             long duration = TimeTranslatorUtil.substractDate(firstLoc.getOrginDate(), nextLoc.getOrginDate());
             legList.add(new Leg(i,i+1,(Double)firstLoc.getLatitude(),(Double)firstLoc.getLongtitude(),
                         (Double)nextLoc.getLatitude(),(Double)nextLoc.getLongtitude(),
                         originAddress,destinationAddress,
                         (Integer)distance.get("value"),(String)distance.get("text"),duration,
                         TimeTranslatorUtil.covertToReadableFormat(duration)));
          }
          ExportUtil.exportToLegFile(legList, outputFile, true);
    }
}
