package bk.master.input;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

public class FileUtil {
    public static void renameFile(String folderPath, String oldNumbber, String newNumber) {
        try {
            File folder = new File(folderPath);
            File[] fileList = folder.listFiles();
            for (File file : fileList) {
                Path source = Paths.get(file.getAbsolutePath());
                String fileName = FilenameUtils.getBaseName(file.getName());
                Pattern p = Pattern.compile("_\\d+_\\d+:\\d+:\\d+");
                Matcher m = p.matcher(file.getName());
                String endName = "NO_FOUND";
                while (m.find()) {
                    endName = m.group();
                }
                if ("NO_FOUND".equals(endName)) {
                    p = Pattern.compile("_\\d+_\\d+:\\d+");
                    m = p.matcher(file.getName());
                    while (m.find()) {
                        endName = m.group();
                    }
                }
                String baseName = fileName.substring(0, fileName.indexOf(oldNumbber+endName, 0));
                Files.move(source, source.resolveSibling(baseName+newNumber+endName+".csv"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteFile() {

    }
    public static void deleteFolder() {

    }
}
