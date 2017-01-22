package bk.master.classify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import au.com.bytecode.opencsv.CSVReader;
import bk.master.util.ExportUtil;

public class StepFilter {
    public enum FreqClazz {
        VERY_SMALL_VERY_LOW(2,24) {
            @Override
            public String toString() {
                return "very low";
            }
        },
        VERY_SMALL_LOW(24,45) {
            @Override
            public String toString() {
                return "low";
            }
        },
        VERY_SMALL_MEDIUM(45,67) {
            @Override
            public String toString() {
                return "medium";
            }
        },
        VERY_SMALL_HIGH(67,89) {
            @Override
            public String toString() {
                return "high";
            }
        },
        VERY_SMALL_VERY_HIGH(89,133) {
            @Override
            public String toString() {
                return "very high";
            }
        },
        SMALL_VERY_LOW(0,25) {
            @Override
            public String toString() {
                return "very low";
            }
        },
        SMALL_LOW(25,43) {
            @Override
            public String toString() {
                return "low";
            }
        },
        SMALL_MEDIUM(43,61) {
            @Override
            public String toString() {
                return "medium";
            }
        },
        SMALL_HIGH(61,79) {
            @Override
            public String toString() {
                return "high";
            }
        },
        SMALL_VERY_HIGH(79,105) {
            @Override
            public String toString() {
                return "very high";
            }
        },
        MEDIUM_VERY_LOW(0,43) {
            @Override
            public String toString() {
                return "very low";
            }
        },
        MEDIUM_LOW(43,62) {
            @Override
            public String toString() {
                return "low";
            }
        },
        MEDIUM_MEDIUM(62,82) {
            @Override
            public String toString() {
                return "medium";
            }
        },
        MEDIUM_HIGH(82,102) {
            @Override
            public String toString() {
                return "high";
            }
        },
        MEDIUM_VERY_HIGH(102,129) {
            @Override
            public String toString() {
                return "very high";
            }
        },
        HIGH_VERY_LOW(0,16) {
            @Override
            public String toString() {
                return "very low";
            }
        },
        HIGH_LOW(16,31) {
            @Override
            public String toString() {
                return "low";
            }
        },
        HIGH_MEDIUM(31,45) {
            @Override
            public String toString() {
                return "medium";
            }
        },
        HIGH_HIGH(45,60) {
            @Override
            public String toString() {
                return "high";
            }
        },
        HIGH_VERY_HIGH(60,80) {
            @Override
            public String toString() {
                return "very high";
            }
        },
        VERY_HIGH_VERY_LOW(0,2) {
            @Override
            public String toString() {
                return "very low";
            }
        },
        VERY_HIGH_LOW(2,6) {
            @Override
            public String toString() {
                return "low";
            }
        },
        VERY_HIGH_MEDIUM(6,12) {
            @Override
            public String toString() {
                return "medium";
            }
        },
        VERY_HIGH_HIGH(12,16) {
            @Override
            public String toString() {
                return "high";
            }
        },
        VERY_HIGH_VERY_HIGH(16,22) {
            @Override
            public String toString() {
                return "very high";
            }
        };
        private Integer lowerValue;
        private Integer upperValue;
        private FreqClazz(Integer lowerValue, Integer upperValue) {
            this.lowerValue = lowerValue;
            this.upperValue = upperValue;
        }
        public Integer getLowerValue() {
            return lowerValue;
        }
        public void setLowerValue(Integer lowerValue) {
            this.lowerValue = lowerValue;
        }
        public Integer getUpperValue() {
            return upperValue;
        }
        public void setUpperValue(Integer upperValue) {
            this.upperValue = upperValue;
        }
        public abstract String toString();
    }
    public enum StepClazz {
        VERY_SMALL(0,85) {
            @Override
            public String toString() {
                return "very small";
            }
        }, SMALL(85,171) {
            @Override
            public String toString() {
                return "small";
            }
        }, MEDIUM(171,257) {
            @Override
            public String toString() {
                return "medium";
            }
        }, HIGH(257,343) {
            @Override
            public String toString() {
                return "high";
            }
        }, VERY_HIGH(343,965) {
            @Override
            public String toString() {
                return "very high";
            }
        };
        private Integer lowerValue;
        private Integer upperValue;
        private StepClazz(Integer lowerValue, Integer upperValue) {
            this.lowerValue = lowerValue;
            this.upperValue = upperValue;
        }
        public Integer getLowerValue() {
            return lowerValue;
        }
        public void setLowerValue(Integer lowerValue) {
            this.lowerValue = lowerValue;
        }
        public Integer getUpperValue() {
            return upperValue;
        }
        public void setUpperValue(Integer upperValue) {
            this.upperValue = upperValue;
        }
        public abstract String toString();
    }
    private static FreqClazz getFreqClazz(List<FreqClazz> comparedList, Integer value) {
        FreqClazz freqClazz = comparedList.get(0);
        if (freqClazz.getLowerValue() <= value && value <= freqClazz.getUpperValue()) {
           return freqClazz;
        }
        for (int i = 1; i < comparedList.size(); i++) {
            freqClazz = comparedList.get(i);
            if (freqClazz.getLowerValue() <= value && value <= freqClazz.getUpperValue()) {
               return freqClazz;
            }
        }
        return null;
    }
    private static StepClazz getStepClazz(Integer value) {
        if (StepClazz.VERY_SMALL.getLowerValue() <= value
            && value <= StepClazz.VERY_SMALL.getUpperValue()) {
            return StepClazz.VERY_SMALL;
        }
        if (StepClazz.SMALL.getLowerValue() < value
            && value <= StepClazz.SMALL.getUpperValue()) {
            return StepClazz.SMALL;
        }
        if (StepClazz.MEDIUM.getLowerValue() < value
            && value <= StepClazz.MEDIUM.getUpperValue()) {
            return StepClazz.MEDIUM;
        }
        if (StepClazz.HIGH.getLowerValue() < value
            && value <= StepClazz.HIGH.getUpperValue()) {
            return StepClazz.HIGH;
        }
        if (StepClazz.VERY_HIGH.getLowerValue() < value
            && value <= StepClazz.VERY_HIGH.getUpperValue()) {
            return StepClazz.VERY_HIGH;
        }
        return StepClazz.VERY_HIGH;
    }
    public static void getStep(String inputFile, String outputFile) {
        List<String> result = new ArrayList<String>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();
            int lineNumber = 0;
            int dataLine = 7;
            while (line != null) {
                lineNumber++;
                if (lineNumber==dataLine) {
                    result.add(line.substring(1, line.length()-1));
                    dataLine = dataLine+9;
                }
                line = br.readLine();
            }
            br.close();
            ExportUtil.exportToFile(result, outputFile);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getStep80(String inputFile, String outputFile) {
        List<String> result = new ArrayList<String>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();
            int lineNumber = 0;
            while (line != null) {
                lineNumber++;
                if (lineNumber%9==0) {
                    result.add(line.substring(1, line.length()-1));
                }
                line = br.readLine();
            }
            br.close();
            ExportUtil.exportToFile(result, outputFile);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getFreqStep(String inputFile, String outputFile, int column, String clazz) {
        List<Integer> resultList = new ArrayList<Integer>();
        try {
            CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (clazz.equalsIgnoreCase(nextLine[nextLine.length-1])) {
                    resultList.add(Integer.valueOf(nextLine[column].trim()));
                }
            }
            reader.close();
            ExportUtil.exportToFile(resultList, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getCountStep(String inputFile, int column, String outputFile) {
         SortedSet<Integer> sortedCountStep = new TreeSet<Integer>();
         BufferedReader br;
         try {
              br = new BufferedReader(new FileReader(inputFile));
              String line = br.readLine();
              while (line != null) {
                  String[] countList = line.split(",");
                  sortedCountStep.add(Integer.valueOf(countList[column]));
                  //continue read new line
                  line = br.readLine();
              }
              br.close();
              List<Integer> countList = new ArrayList<Integer>();
              for (Integer value : sortedCountStep) {
                  countList.add(value);
              }
              ExportUtil.exportToFile(countList, outputFile);
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
    public static void getClassifyFreqStep(String inputFile, String outputFile) {
        List<String> resultList = new ArrayList<String>();
        BufferedReader br;
        try {
             br = new BufferedReader(new FileReader(inputFile));
             String line = br.readLine();
             int lineNumber = 0;
             while (line != null) {
                 lineNumber++;
                 if (lineNumber%2==0) {
                     resultList.add(line);
                 }
                 //continue read new line
                 line = br.readLine();
             }
             br.close();
             ExportUtil.exportToFile(resultList, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
    public static void classifyFreqStep(String inputFile, String outputFile) {
         List<FreqClazz> rank1 = new ArrayList<FreqClazz>();
         rank1.add(FreqClazz.VERY_SMALL_VERY_LOW);
         rank1.add(FreqClazz.VERY_SMALL_LOW);
         rank1.add(FreqClazz.VERY_SMALL_MEDIUM);
         rank1.add(FreqClazz.VERY_SMALL_HIGH);
         rank1.add(FreqClazz.VERY_SMALL_VERY_HIGH);
         List<FreqClazz> rank2 = new ArrayList<FreqClazz>();
         rank2.add(FreqClazz.SMALL_VERY_LOW);
         rank2.add(FreqClazz.SMALL_LOW);
         rank2.add(FreqClazz.SMALL_MEDIUM);
         rank2.add(FreqClazz.SMALL_HIGH);
         rank2.add(FreqClazz.SMALL_VERY_HIGH);
         List<FreqClazz> rank3 = new ArrayList<FreqClazz>();
         rank3.add(FreqClazz.MEDIUM_VERY_LOW);
         rank3.add(FreqClazz.MEDIUM_LOW);
         rank3.add(FreqClazz.MEDIUM_MEDIUM);
         rank3.add(FreqClazz.MEDIUM_HIGH);
         rank3.add(FreqClazz.MEDIUM_VERY_HIGH);
         List<FreqClazz> rank4 = new ArrayList<FreqClazz>();
         rank4.add(FreqClazz.HIGH_VERY_LOW);
         rank4.add(FreqClazz.HIGH_LOW);
         rank4.add(FreqClazz.HIGH_MEDIUM);
         rank4.add(FreqClazz.HIGH_HIGH);
         rank4.add(FreqClazz.HIGH_VERY_HIGH);
         List<FreqClazz> rank5 = new ArrayList<FreqClazz>();
         rank5.add(FreqClazz.VERY_HIGH_VERY_LOW);
         rank5.add(FreqClazz.VERY_HIGH_LOW);
         rank5.add(FreqClazz.VERY_HIGH_MEDIUM);
         rank5.add(FreqClazz.VERY_HIGH_HIGH);
         rank5.add(FreqClazz.VERY_HIGH_VERY_HIGH);
         List<String> resultList = new ArrayList<String>();
         BufferedReader br;
         try {
              br = new BufferedReader(new FileReader(inputFile));
              String line = br.readLine();
              while (line != null) {
                  resultList.add(line);
                  String[] countList = line.split(",");
                  FreqClazz clazz1 = getFreqClazz(rank1, Integer.valueOf(countList[0]));
                  FreqClazz clazz2 = getFreqClazz(rank2, Integer.valueOf(countList[1]));
                  FreqClazz clazz3 = getFreqClazz(rank3, Integer.valueOf(countList[2]));
                  FreqClazz clazz4 = getFreqClazz(rank4, Integer.valueOf(countList[3]));
                  FreqClazz clazz5 = getFreqClazz(rank5, Integer.valueOf(countList[4]));
                  resultList.add(clazz1.toString()+","+clazz2.toString()+","+clazz3.toString()+","+
                                 clazz4.toString()+","+clazz5.toString()+","+countList[5]);
                  //continue read new line
                  line = br.readLine();
              }
              br.close();
              ExportUtil.exportToFile(resultList, outputFile);
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
    public static void getDistanceStep(String inputFile1, String inputFile2, String outputFile) {
        SortedSet<Integer> sortedDistanceList = new TreeSet<Integer>();
        BufferedReader br;
        try {
            CSVReader reader = new CSVReader(new FileReader(inputFile1), ',');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
               for (String distance : nextLine) {
                   sortedDistanceList.add(Integer.valueOf(distance.trim()));
               }
            }
            reader.close();
            reader = new CSVReader(new FileReader(inputFile2), ',');
            while ((nextLine = reader.readNext()) != null) {
               for (String distance : nextLine) {
                   sortedDistanceList.add(Integer.valueOf(distance.trim()));
               }
            }
            reader.close();
            List<Integer> resultList = new ArrayList<Integer>();
            for (Integer distance : sortedDistanceList) {
                resultList.add(distance);
            }
            ExportUtil.exportToFile(resultList, outputFile);
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
    public static void getClassifiedStepInRoute(String inputFile, String status, String outputFile) {
         List<String> resultList = new ArrayList<String>();
         BufferedReader br;
         try {
              br = new BufferedReader(new FileReader(inputFile));
              String line = br.readLine();
              int i = 0;
              while (line != null) {
                  i++;
                  if (i%2==0) {
                      String[] classifiedList = line.split(",");
                      StringBuilder dataBuilder = new StringBuilder();
                      for (String data: classifiedList) {
                          dataBuilder.append(data.substring(data.indexOf(":")+1));
                          dataBuilder.append(",");
                      }
                      dataBuilder.append(status);
                      resultList.add(dataBuilder.toString());
                  }
                  line = br.readLine();
              }
              br.close();
              ExportUtil.exportAccumulateFile(resultList, outputFile);
         } catch (Exception e) {
              e.printStackTrace();
         }
    }
    public static void classifiedStepInRoute(String inputFile, String outputFile) {
        List<String> resultList = new ArrayList<String>();
        BufferedReader br;
        try {
             br = new BufferedReader(new FileReader(inputFile));
             String line = br.readLine();
             int i = 0;
             while (line != null) {
                 i++;
                 HashMap<StepClazz,Integer> classified = new HashMap<StepClazz,Integer>();
                 classified.put(StepClazz.VERY_SMALL, 0);
                 classified.put(StepClazz.SMALL, 0);
                 classified.put(StepClazz.MEDIUM, 0);
                 classified.put(StepClazz.HIGH, 0);
                 classified.put(StepClazz.VERY_HIGH, 0);
                 String[] orgDistanceList = line.split(",");
                 for (String distance: orgDistanceList) {
                    try {
                         StepClazz stepClazz = getStepClazz(Integer.valueOf(distance.trim()));
                         classified.put(stepClazz,classified.get(stepClazz)+1);
                    } catch (Exception e) {
                        System.out.println("The line has the end comma:"+i);
                    }
                 }
                 resultList.add(line);
                 StringBuilder clazzBuilder = new StringBuilder();
                 clazzBuilder.append(StepClazz.VERY_SMALL.toString()+":"+classified.get(StepClazz.VERY_SMALL));
                 clazzBuilder.append(",");
                 clazzBuilder.append(StepClazz.SMALL.toString()+":"+classified.get(StepClazz.SMALL));
                 clazzBuilder.append(",");
                 clazzBuilder.append(StepClazz.MEDIUM.toString()+":"+classified.get(StepClazz.MEDIUM));
                 clazzBuilder.append(",");
                 clazzBuilder.append(StepClazz.HIGH.toString()+":"+classified.get(StepClazz.HIGH));
                 clazzBuilder.append(",");
                 clazzBuilder.append(StepClazz.VERY_HIGH.toString()+":"+classified.get(StepClazz.VERY_HIGH));
                 resultList.add(clazzBuilder.toString());
                 //continue read new line
                 line = br.readLine();
             }
             br.close();
             ExportUtil.exportToFile(resultList, outputFile);
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
    public static void discreteFreqStep() {
        getCountStep("classifyRoute.csv", 0, "freqVerySmall.csv");
        getCountStep("classifyRoute.csv", 1, "freqSmall.csv");
        getCountStep("classifyRoute.csv", 2, "freqMedium.csv");
        getCountStep("classifyRoute.csv", 3, "freqHigh.csv");
        getCountStep("classifyRoute.csv", 4, "freqVeryHigh.csv");
        classifyFreqStep("classifyRoute.csv","classifyRoute_Full.csv");
        getClassifyFreqStep("classifyRoute_Full.csv", "classifyRouteV2.csv");
    }
    public static void kde() {
        getFreqStep("classifyRoute.csv", "freqVerySmall_OnTime.csv", 0, "onTime");
        getFreqStep("classifyRoute.csv", "freqVerySmall_LateTime.csv", 0, "lateTime");
        getFreqStep("classifyRoute.csv", "freqSmall_OnTime.csv", 1, "onTime");
        getFreqStep("classifyRoute.csv", "freqSmall_LateTime.csv", 1, "lateTime");
        getFreqStep("classifyRoute.csv", "freqMedium_OnTime.csv", 2, "onTime");
        getFreqStep("classifyRoute.csv", "freqMedium_LateTime.csv", 2, "lateTime");
        getFreqStep("classifyRoute.csv", "freqHigh_OnTime.csv", 3, "onTime");
        getFreqStep("classifyRoute.csv", "freqHigh_LateTime.csv", 3, "lateTime");
        getFreqStep("classifyRoute.csv", "freqVeryhigh_OnTime.csv", 4, "onTime");
        getFreqStep("classifyRoute.csv", "freqVeryhigh_LateTime.csv", 4, "lateTime");
    }
    public static void discreteStep() {
        getStep("onTimeDetail_CC-AS_80.csv", "onTimeDetail_CC-AS_100.csv");
        getStep("lateTimeDetail_CC-AS_80.csv", "lateTimeDetail_CC-AS_100.csv");
        getStep80("onTimeDetail_CC-AS_80.csv", "onTimeDetail_CC-AS_80_1.csv");
        getStep80("lateTimeDetail_CC-AS_80.csv", "lateTimeDetail_CC-AS_80_1.csv");
        getDistanceStep("onTimeDetail_CC-AS_100.csv", "lateTimeDetail_CC-AS_100.csv", "CC-AS_Distance.csv");
        classifiedStepInRoute("onTimeDetail_CC-AS_80_1.csv","onTimeClassify_CC-AS_80.csv");
        classifiedStepInRoute("lateTimeDetail_CC-AS_80_1.csv","lateTimeClassify_CC-AS_80.csv");
        File file = new File("classifyRoute.csv");
        if (file.exists()) {
            file.delete();
        }
        getClassifiedStepInRoute("onTimeClassify_CC-AS_80.csv","onTime","classifyRoute.csv");
        getClassifiedStepInRoute("lateTimeClassify_CC-AS_80.csv","lateTime","classifyRoute.csv");
    }
    public static void main(String[] args) {
        discreteStep();
        discreteFreqStep();
        kde();
    }
}
