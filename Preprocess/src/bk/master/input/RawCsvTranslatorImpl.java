package bk.master.input;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import bk.master.input.model.Location;

public class RawCsvTranslatorImpl implements RawCsvTranslator{

    @Override
    public void translateDBToJson(String filePath, String outputPath) {
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(filePath));
            List<String[]> allRows = reader.readAll();
            List<Location> data = new ArrayList<Location>();
            for(String[] row : allRows){
              Double lng = Double.valueOf(row[2]);
              Double lat = Double.valueOf(row[1]);
              Location loc = new Location(row[0],lat,lng,row[3]);
              data.add(loc);
            }
            writeToJsonFile(data, outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void translateCsvToJson(String filePath, String outputPath) {
        CSVReader reader;
        int i = 1;
        try {
            reader = new CSVReader(new FileReader(filePath));
            List<String[]> allRows = reader.readAll();
            List<Location> data = new ArrayList<Location>();
            for(String[] row : allRows){
              Double lng = Double.valueOf(row[5]);
              Double lat = Double.valueOf(row[6]);
              long miliseconds = Double.valueOf(row[4]).longValue()*1000;
              Location loc = new Location(row[0],lat,lng,miliseconds);
              data.add(loc);
              i++;
            }
            writeToJsonFile(data, outputPath);
        } catch (Exception e) {
            System.out.println("error in " + filePath+"line="+i);
            e.printStackTrace();
        }
    }

    @Override
    public void translateFolderCsvToJson(String folderPath, String outputPath) {
        File folder = new File(folderPath);
        File[] fileList = folder.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            translateCsvToJson(fileList[i].getAbsolutePath(),
                    outputPath+"/"+fileList[i].getName()+".json");
        }
    }

    private void writeToJsonFile(List<Location> data, String outputPath) {
        File file = new File(outputPath);
        try {
            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter writer = new FileWriter(file, true);
            for (Location obj : data) {
               // System.out.println(obj.getDate());
                writer.write(obj.toString()+"\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
