package bk.master.translator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SplitFile {
    private static String DATA_FOLDER = "/mnt/thuy/data/2016-10-16.txt";
    private static int NUMBER = 11;
    private static String SPLIT_DATA_FOLDER = "/mnt/thuy/split/10/"+NUMBER+"/";
    private static String JSON_DATA_FOLDER = "/mnt/thuy/json/10/"+NUMBER+"/";
    public static void splitFile(File f, String outputPath) {
        String name = f.getName();
        int partCounter = 1;
        String encoding = "UTF-8";
        int maxlines = 25630;
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), encoding));
            int count = 1;
            while (reader.readLine() != null) {
                File newFile = new File(outputPath, name + "." + String.format("%03d", partCounter++));
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile), encoding));
                count = 1;
                for (String line; (line = reader.readLine()) != null;) {
                    if (count++ > maxlines) {
                        break;
                    }
                    writer.write(line);
                    writer.newLine();
                }
                writer.flush();
            }
            writer.flush();
            writer.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void exportToJson(String splitDataFolder, String jsonDataFolder) {
        RawCsvTranslator translator = new RawCsvTranslatorImpl();
        try {
            File folderPath = new File(splitDataFolder);
            File[] fileList = folderPath.listFiles();
            for (File file : fileList) {
                translator.translateCsvToJson(file.getAbsolutePath(),
                        jsonDataFolder+file.getName()+".json");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static void main(String[] args) throws IOException {
        int j = 16;
        for (int i = 18; i <= 19; i++) {
            String dataFolder = "/mnt/thuy/data/2016-09-"+i+".txt";
            String splitDataFolder = "/mnt/thuy/split/09/"+j+"/";
            String jsonDataFolder = "/mnt/thuy/json/09/"+j+"/";
            splitFile(new File(dataFolder), splitDataFolder);
            exportToJson(splitDataFolder,jsonDataFolder);
            j++;
        }
    }
}
