package bk.master.input;

import java.io.File;


public class Test {
    public static void main(String[] args) {
        File parentFolder = new File("/home/thuy1/git/predictUsingProbability/MongoOperator/11/merge_schedule/");
        File[] folderList = parentFolder.listFiles();
        for (File inputFolder : folderList) {
            TransformUtil.getFinishTimeFromCCToAS("benXeAnSuong","benXeCuChi", inputFolder, "AS_CC.txt");
        }
    }
}
