package bk.master.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import bk.master.input.model.Leg;
import bk.master.input.model.Location;
import bk.master.input.model.Move;
import bk.master.input.model.Route;

public class InputUtil {
    static private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static List<Leg> loadLegList(String inputFile) {
        try {
            ColumnPositionMappingStrategy<Leg> strat = new ColumnPositionMappingStrategy<Leg>();
            strat.setType(Leg.class);
            String[] columns = new String[] {"startIndex","endIndex","startLat", "startLng", "endLat", "endLng",
                    "startPlace", "endPlace", "distance", "distanceText", "duration", "durationText"};
            strat.setColumnMapping(columns);

            CsvToBean<Leg> csv = new CsvToBean<Leg>();

            CSVReader csvReader = new CSVReader(new FileReader(inputFile), '|');
            List<Leg> legList = csv.parse(strat, csvReader);
            csvReader.close();
            return legList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Route> loadFinishTime(String inputFile) {
        try {
            ColumnPositionMappingStrategy<Route> strat = new ColumnPositionMappingStrategy<Route>();
            strat.setType(Route.class);
            String[] columns = new String[] {"index","departure","destination", "departTime",
                    "destTime", "finishTime", "finishTimeText"};
            strat.setColumnMapping(columns);

            CsvToBean<Route> csv = new CsvToBean<Route>();

            CSVReader csvReader = new CSVReader(new FileReader(inputFile), ',');
            List<Route> routeList = csv.parse(strat, csvReader);
            csvReader.close();
            return routeList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Location> loadInputDateLocation(String inputFile) {
        List<Location> locationList = new ArrayList<Location>();
        try {
            CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
            String[] nextLine;
            nextLine = reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                locationList.add(new Location(Double.valueOf(nextLine[0]), Double.valueOf(nextLine[1]),
                        formatter.parse(nextLine[2])));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationList;
    }
    public static List<Move> loadCsvDate(String inputFile) {
        List<Move> moveList = new ArrayList<Move>();
        try {
            CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
            String[] nextLine;
            nextLine = reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                Date departTime = formatter.parse(nextLine[3].trim());
                Date destTime = formatter.parse(nextLine[4].trim());
                moveList.add(new Move(departTime, destTime));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moveList;
    }
    public static List<Location> loadInputLocation(String inputFile) {
        List<Location> result = new ArrayList<Location>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();

            while (line != null) {
                String[] data = line.split(",");
                result.add(new Location(Double.valueOf(data[0]), Double.valueOf(data[1])));
                line = br.readLine();
            }
            br.close();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Date> loadInputDate(String inputFile) {
        List<Date> result = new ArrayList<Date>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();
            while (line != null) {
                result.add(formatter.parse(line.trim()));
                line = br.readLine();
            }
            br.close();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> loadInput(String inputFile) {
        List<String> result = new ArrayList<String>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();
            while (line != null) {
                result.add(line.trim());
                line = br.readLine();
            }
            br.close();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static HashMap<Double,List<String>> loadPlotData(String inputFile) {
        DecimalFormat numberFormat = new DecimalFormat("#.##");
        HashMap<Double,List<String>> result = new HashMap<Double,List<String>>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();
            while (line != null) {
                String[] valueList = line.trim().split(",");
                double destTime = Double.valueOf(numberFormat.format(valueList.length/2.0));
                List<String> data = result.get(destTime);
                if (data == null) {
                    data = new ArrayList<String>();
                }
                data.add(line.trim());
                result.put(destTime, data);
                line = br.readLine();
            }
            br.close();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
