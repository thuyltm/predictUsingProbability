package bk.master.input;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;



public class Test {
    public static void main(String[] args) {
        File folder = new File("/home/thuy1/git/predictUsingProbability/MongoOperator/09/location/18");
        File[] dataFile = folder.listFiles();
        for (File file : dataFile) {
            String fileName = FilenameUtils.getBaseName(file.getAbsolutePath());
            String remainName = fileName.substring(0, fileName.lastIndexOf("_")+1);
            String newName = remainName+"CC-AS";
            if (fileName.contains("An Suong-Cu Chi")) {
                newName = remainName+"AS-CC";
            }
            //System.out.println(newName);
            Path source = Paths.get(file.getAbsolutePath());
            try {
                Files.move(source, source.resolveSibling(newName+".csv"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        /*File dataFolder = new File("/home/thuy1/git/predictUsingProbability/GSOperator/newLeg/09/02/");
        File[] dataList = dataFolder.listFiles();
        for (File file : dataList) {
            Path source = Paths.get(file.getAbsolutePath());
            String fileName = FilenameUtils.getBaseName(file.getName());
            String newFileName = fileName.substring(0, fileName.lastIndexOf("."));
            try {
                Files.move(source, source.resolveSibling(newFileName+".csv"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/

    }
}
