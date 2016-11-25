package bk.master.input;

import java.io.File;
import java.util.List;


public class Test {

    public static void main(String[] args) {
        /*List<String> candidateList = InputUtil.loadInput("deviceList.txt");
        System.out.println(candidateList.contains("53N5112"));*/
        for (int i = 11; i <= 17; i++) {
            FileUtil.renameFile("/home/thuy1/git/predictUsingProbability/MongoOperator/location/"+i,
                    "02", ""+i);
        }
    }
}
