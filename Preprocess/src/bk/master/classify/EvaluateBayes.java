package bk.master.classify;

import java.io.FileReader;

import au.com.bytecode.opencsv.CSVReader;

public class EvaluateBayes {
    public static int calculateTruePositive(String direction, String clazz) {
        int count = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader("correctResultR_"+ direction +".csv"), ',');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[5].equals(clazz)) {
                    count++;
                }
            }
            reader.close();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int calculateFalsePositive(String direction, String clazz) {
        int count = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader("wrongResultR_"+ direction +".csv"), ',');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[5].equals(clazz)) {
                    count++;
                }
            }
            reader.close();
            return count;
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int calculateTrueNegative(String direction, String clazz) {
        int count = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader("correctResultR_"+ direction +".csv"), ',');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[5].equals(clazz)) {
                    count++;
                }
            }
            reader.close();
            return count;
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int calculateFalseNegative(String direction, String clazz) {
        int count = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader("wrongResultR_"+ direction +".csv"), ',');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[5].equals(clazz)) {
                    count++;
                }
            }
            reader.close();
            return count;
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static float calculateAccuracy(String direction) {
        int tp = calculateTruePositive(direction, "onTime");
        int fp = calculateFalsePositive(direction, "lateTime");
        int tn = calculateTruePositive(direction, "lateTime");
        int fn = calculateFalsePositive(direction, "onTime");
        return (float)(tp+tn)/(tp+fp+tn+fn);
    }

    public static float calculateErrorRate(String direction) {
        int tp = calculateTruePositive(direction, "onTime");
        int fp = calculateFalsePositive(direction, "lateTime");
        int tn = calculateTrueNegative(direction, "lateTime");
        int fn = calculateFalseNegative(direction, "onTime");
        return (float)(fp+fn)/(tp+fp+tn+fn);
    }
}
