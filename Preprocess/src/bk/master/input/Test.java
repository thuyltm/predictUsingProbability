package bk.master.input;

import java.util.List;

import bk.master.input.model.Move;


public class Test {

    public static void main(String[] args) {
       /* List<Move> result = TimeTranslatorUtil.mergeSchedule("/home/thuy/workspace/MongoOperator/schedule/53N4663_18_AS.csv",
                "/home/thuy/workspace/MongoOperator/schedule/53N4663_18_AS.csv",
                "An Suong", "Cu Chi");
        ExportUtil.exportToScheduleFile(result, "53N4663_18.csv");*/

        System.out.println(TimeTranslatorUtil.covertToReadableFormat(30000));
    }
}
