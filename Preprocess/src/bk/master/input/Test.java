package bk.master.input;

import java.util.HashMap;
import java.util.List;



public class Test {
    public static void main(String[] args) {
        HashMap<Integer, List<String>> result =
                InputUtil.mapFinishTime("/home/thuy1/git/predictUsingProbability/Preprocess/AS_CC.txt");
        ExportUtil.exportFrequenceFinishTime(result, "AS_CC_Freq.txt");
        result = InputUtil.mapFinishTime("/home/thuy1/git/predictUsingProbability/Preprocess/CC_AS.txt");
        ExportUtil.exportFrequenceFinishTime(result, "CC_AS_Freq.txt");
    }
}
