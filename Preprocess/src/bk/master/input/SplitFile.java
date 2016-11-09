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
    public static void splitFile(File f) {
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
                File newFile = new File(f.getParent(), name + "." + String.format("%03d", partCounter++));
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

    public static void main(String[] args) throws IOException {
        splitFile(new File("/home/thuy/Documents/data/2016-09-19/2016-09-19.txt"));
    }
}
