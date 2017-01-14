package bk.master.input;

import java.io.File;

public class Test {

    public static void main(String[] args) {
        File folder = new File("/home/thuy1/git/predictUsingProbability/MongoOperator/09/location/02");
        String outputFolder = "/home/thuy1/git/predictUsingProbability/MongoOperator/09/distance/02";
        File[] dataFile = folder.listFiles();
        for (File file : dataFile) {
            Calculate.calculateDistance(file, outputFolder);
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
