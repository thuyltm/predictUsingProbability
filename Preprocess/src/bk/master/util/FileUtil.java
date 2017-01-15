package bk.master.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import bk.master.input.model.Route;

public class FileUtil {
    public static void addDepartDestFileName(String dataPath, String changeFolder, String day) {
        List<Route> routeList = InputUtil.loadFinishTime(dataPath);
        File file = new File(dataPath);
        String deviceId = FilenameUtils.getBaseName(file.getName());
        for (Route route : routeList) {
            int index = route.getIndex() - 2;
            String depart = route.getDeparture();
            if ("benXeAnSuong".equalsIgnoreCase(depart)) {
                depart = "AS";
            }
            if ("benXeCuChi".equalsIgnoreCase(depart)) {
                depart = "CC";
            }
            String destination = route.getDestination();
            if ("benXeAnSuong".equalsIgnoreCase(destination)) {
                destination = "AS";
            }
            if ("benXeCuChi".equalsIgnoreCase(destination)) {
                destination = "CC";
            }
            String duration = route.getFinishTimeText();
            String oldName = "distance_"+deviceId+"_"+day+"_"+ index +"_" +duration;
            String newName = oldName + "_"+depart+"-"+destination;
            Path source = Paths.get(changeFolder+oldName+".csv");
            try {
                Files.move(source, source.resolveSibling(newName+".csv"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
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
