package bk.master.input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SplitFile {
    private static String DATA_FOLDER = "/home/thuy1/gpsdata/2016-09-17.txt";
    private static String SPLIT_DATA_FOLDER = "/home/thuy1/split/17/";
    private static String JSON_DATA_FOLDER = "/home/thuy1/json/17/";
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
    public static void exportToJson() {
        RawCsvTranslator translator = new RawCsvTranslatorImpl();
        try {
            File folderPath = new File(SPLIT_DATA_FOLDER);
            File[] fileList = folderPath.listFiles();
            for (File file : fileList) {
                translator.translateCsvToJson(file.getAbsolutePath(),
                        JSON_DATA_FOLDER+file.getName()+".json");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static void main(String[] args) throws IOException {
        splitFile(new File(DATA_FOLDER), SPLIT_DATA_FOLDER);
        exportToJson();
    }
}
