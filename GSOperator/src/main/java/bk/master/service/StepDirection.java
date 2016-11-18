package bk.master.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import bk.master.input.ExportUtil;
import bk.master.input.InputUtil;
import bk.master.input.model.Leg;
import bk.master.input.model.Location;

public class StepDirection {
    private static final String SERVER_API = "AIzaSyA_Zm07U2PRT9p8kQ3VlNfmXVTnhTinriw";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final int MAX_WAYPOINTS = 23;
    public static void main(String[] args) {
        List<Location> travelPointList = InputUtil.loadInputDateLocation("route_0.txt");
        sendGet(travelPointList,"leg.csv");
    }

    private static List<String> getQuery(List<Location> travelPointList) throws Exception{
        List<String> result = new ArrayList<String>();
        int loop = travelPointList.size()/MAX_WAYPOINTS;
        for (int i = 1; i <= loop; i++) {
            result.add(getBatchQuery(travelPointList, MAX_WAYPOINTS*(i-1), MAX_WAYPOINTS*i));
        }
        if (travelPointList.size()%MAX_WAYPOINTS>0) {
            result.add(getBatchQuery(travelPointList, MAX_WAYPOINTS*loop, travelPointList.size()));
        }
        return result;
    }
    private static String getBatchQuery(List<Location> travelPointList, int start, int end) throws Exception{
        StringBuilder result = new StringBuilder();
        for (int j = start;j<end;j++) {
            Location loc = travelPointList.get(j);
            if (j==start) {
                result.append("origin="+loc.getLatitude()+","+loc.getLongtitude());
            } else if (j==end-1) {
                result.append("&destination="+loc.getLatitude()+","+loc.getLongtitude());
            } else if (j==start+1) {
                result.append("&waypoints="+loc.getLatitude()+","+loc.getLongtitude()+URLEncoder.encode("|", "UTF-8"));
            } else if (j==end-2) {
                result.append(loc.getLatitude()+","+loc.getLongtitude());
            } else {
                result.append(loc.getLatitude()+","+loc.getLongtitude()+URLEncoder.encode("|", "UTF-8"));
            }
        }
        return result.toString();
    }
    // HTTP GET request
    public static void sendGet(List<Location> travelPointList, String outputFile) {
        try {
          List<String> queryList = getQuery(travelPointList);
          for (String query: queryList) {
            List<Leg> legList = new ArrayList<Leg>();
            String url = "https://maps.googleapis.com/maps/api/directions/json?"+query+"&key="+SERVER_API;

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

             System.out.println(result.toString());
             JSONObject checkResult = new JSONObject(result.toString());
             if ("NOT_FOUND".equals(checkResult.get("status"))) {
                 System.out.println("Not found from "+query);
                 return;
             }
             JSONObject move = new JSONObject(result.toString());
             JSONArray routes = (JSONArray) move.get("routes");
             JSONArray legs = (JSONArray) ((JSONObject)routes.get(0)).get("legs");
             for (int i = 0; i < legs.length(); i++) {
                 JSONObject info = (JSONObject)legs.get(i);
                 JSONObject distance = (JSONObject)info.get("distance");
                 JSONObject startLocation = (JSONObject)info.get("start_location");
                 JSONObject endLocation = (JSONObject)info.get("end_location");
             }
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
