package bk.master.input;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bk.master.input.model.Leg;
import bk.master.input.model.Location;
import bk.master.input.model.Move;

public class ExportUtil {
    static private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static private SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static void exportToDateFile(List<Date> data, String outputFile) {
        try
        {
            BufferedWriter bw = createBufferWriter(outputFile);
            for (Date date: data) {
                bw.write(formatter.format(date));
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
    public static void exportToStepFile(List<Location> data, String outputFile) {
        try {
            BufferedWriter bw = createAccumulateBuffer(outputFile);
            for (Location mv: data) {
                bw.write(mv.getLatitude()+","+mv.getLongtitude()+","+mv.getReadableDate());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void exportToScheduleFile(List<Move> moveList, String outputFile){
        try
        {
            BufferedWriter bw = createBufferWriter(outputFile);
            int i = 1;
            for (Move mv : moveList) {
                bw.write(i + "," +mv.toString());
                bw.newLine();
                i++;
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
    public static void exportToLegFile(List<Leg> legList, String outputFile, boolean append){
        try
        {
            File file = new File(outputFile);

            // if file doesnt exists, then create it
            if (file.exists()&&!append) {
                file.delete();
            }
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
            for (Leg leg : legList) {
                bw.write(leg.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
    public static void exportToFile(List<String> data, String outputFile){
        try
        {
            BufferedWriter bw = createBufferWriter(outputFile);
            for (String sentence : data) {
                bw.write(sentence);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
    public static void exportAccumulateFile(List<? extends Object> data, String outputFile){
        try
        {
            BufferedWriter bw = createAccumulateBuffer(outputFile);
            for (Object sentence : data) {
                bw.write(sentence.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
    public static void exportPlotDataFile(List<? extends Object> data, String outputFile){
        try
        {
            BufferedWriter bw = createAccumulateBuffer(outputFile);
            int length = data.size();
            int i = 0;
            while (i<length-1) {
                bw.write(data.get(i).toString()+",");
                i++;
            }
            bw.write(data.get(i).toString()+"\n");
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
    private static BufferedWriter createAccumulateBuffer(String outputFile)
            throws IOException, UnsupportedEncodingException,
            FileNotFoundException {
        File file = new File(outputFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), "UTF-8"));
        return bw;
    }
    public static void exportTime(String newLegFolder, String outputFile) {
        try {
            BufferedWriter bw = createBufferWriter(outputFile);

            File dir = new File(newLegFolder);
            File[] directoryList = dir.listFiles();
            for (File legFile : directoryList) {
                List<Leg> legList = InputUtil.loadLegList(legFile.getAbsolutePath());
                StringBuilder valueList = new StringBuilder();
                long totalDuration = 0;
                for (Leg leg: legList) {
                    totalDuration = totalDuration+leg.getDuration();
                    valueList.append(leg.getDuration()/1000+",");
                }
                valueList.append(totalDuration/1000);
                bw.write(valueList.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void exportDistance(String newLegFolder, String outputFile) {
        try {
            BufferedWriter bw = createBufferWriter(outputFile);

            File dir = new File(newLegFolder);
            File[] directoryList = dir.listFiles();
            for (File legFile : directoryList) {
                List<Leg> legList = InputUtil.loadLegList(legFile.getAbsolutePath());
                StringBuilder valueList = new StringBuilder();
                long totalDistance = 0;
                for (Leg leg: legList) {
                    totalDistance = totalDistance+leg.getDistance();
                    valueList.append(leg.getDistance()+",");
                }
                valueList.append(totalDistance);
                bw.write(valueList.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void exportTimeText(String newLegFolder, String outputFile) {
        try {
            BufferedWriter bw = createBufferWriter(outputFile);

            File dir = new File(newLegFolder);
            File[] directoryList = dir.listFiles();
            int i = 1;
            for (File legFile : directoryList) {
                List<Leg> legList = InputUtil.loadLegList(legFile.getAbsolutePath());
                StringBuilder valueList = new StringBuilder(i+",");
                long totalDuration = 0;
                for (Leg leg: legList) {
                    totalDuration = totalDuration+leg.getDuration();
                    valueList.append(leg.getDurationText()+",");
                }
                valueList.append(TimeTranslatorUtil.covertToReadableFormat(totalDuration));
                bw.write(valueList.toString());
                bw.newLine();
                i++;
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void exportAllInfo(String newLegFolder, String outputFile) {
        try {
            BufferedWriter bw = createBufferWriter(outputFile);
            File dir = new File(newLegFolder);
            File[] directoryList = dir.listFiles();
            StringBuilder valueList = new StringBuilder();
            int i = 1;
            for (File legFile : directoryList) {
                List<Leg> legList = InputUtil.loadLegList(legFile.getAbsolutePath());
                valueList = new StringBuilder(i+",");
                long totalDuration = 0;
                for (Leg leg: legList) {
                    totalDuration = totalDuration+leg.getDuration();
                    valueList.append(leg.getDuration()/1000+",");
                }
                valueList.append(totalDuration/1000);
                bw.write(valueList.toString());
                bw.newLine();
                valueList = new StringBuilder("   ");
                for (Leg leg: legList) {
                    valueList.append(leg.getDurationText()+",");
                }
                valueList.append(TimeTranslatorUtil.covertToReadableFormat(totalDuration));
                bw.write(valueList.toString());
                bw.newLine();
                valueList = new StringBuilder("   ");

                valueList.append("100");
                bw.write(valueList.toString());
                bw.newLine();
                valueList = new StringBuilder("   ");
                long totalDistance = 0;
                for (Leg leg: legList) {
                    totalDistance = totalDistance+leg.getDistance();
                    valueList.append(leg.getDistance()+",");
                }
                valueList.append(totalDistance);
                bw.write(valueList.toString());
                bw.newLine();
                i++;
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void exportPairCompare(String newLegFolder, String outputFile) {
        try {
            BufferedWriter bw = createBufferWriter(outputFile);
            File dir = new File(newLegFolder);
            File[] directoryList = dir.listFiles();
            StringBuilder valueList = new StringBuilder();
            for (File legFile : directoryList) {
                List<Leg> legList = InputUtil.loadLegList(legFile.getAbsolutePath());
                valueList = new StringBuilder();
                int i = 1;
                for (Leg leg: legList) {
                    valueList.append(leg.getDistance()+","+leg.getDuration()/1000+","
                                        +",V"+i+"\n");
                    i++;
                }
                bw.write(valueList.toString());
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static BufferedWriter createBufferWriter(String outputFile)
            throws IOException, UnsupportedEncodingException,
            FileNotFoundException {
        File file = new File(outputFile);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        return bw;
    }
}
