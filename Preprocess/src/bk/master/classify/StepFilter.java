package bk.master.classify;

import java.io.BufferedReader;
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
        VERY_SMALL_LOW(4,33) {
            @Override
            public String toString() {
                return "low";
            }
        },
        VERY_SMALL_MEDIUM(33,64) {
            @Override
            public String toString() {
                return "medium";
            }
        },
        VERY_SMALL_HIGH(64,133) {
            @Override
            public String toString() {
                return "high";
            }
        },
        SMALL_LOW(12,39) {
            @Override
            public String toString() {
                return "low";
            }
        },
        SMALL_MEDIUM(39,62) {
            @Override
            public String toString() {
                return "medium";
            }
        },
        SMALL_HIGH(62,89) {
            @Override
            public String toString() {
                return "high";
            }
        },
        MEDIUM_LOW(29,59) {
            @Override
            public String toString() {
                return "low";
            }
        },
        MEDIUM_MEDIUM(59,86) {
            @Override
            public String toString() {
                return "medium";
            }
        },
        MEDIUM_HIGH(86,129) {
            @Override
            public String toString() {
                return "high";
            }
        },
        HIGH_LOW(0,24) {
            @Override
            public String toString() {
                return "low";
            }
        },
        HIGH_MEDIUM(24,45) {
            @Override
            public String toString() {
                return "medium";
            }
        },
        HIGH_HIGH(45,80) {
            @Override
            public String toString() {
                return "high";
            }
        },
        VERY_HIGH_LOW(0,6) {
            @Override
            public String toString() {
                return "low";
            }
        },
        VERY_HIGH_MEDIUM(6,14) {
            @Override
            public String toString() {
                return "medium";
            }
        },
        VERY_HIGH_HIGH(14,22) {
            @Override
            public String toString() {
                return "high";
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
        }, VERY_HIGH(343,479) {
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
    public static void getFreqStep(String inputFile, String outputFile) {
        HashMap<Integer, Integer> resultList = new HashMap<Integer, Integer>();
        try {
            CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (int i = 0; i < nextLine.length; i++) {
                    String key = nextLine[i].trim();
                    if (!"".equals(key)) {
                        Integer distance = Integer.valueOf(key);
                        Integer repeat = 0;
                        if (resultList.get(distance) != null) {
                            repeat = resultList.get(distance);
                        }
                        resultList.put(distance, repeat+1);
                    }
                }
            }
            reader.close();
            ExportUtil.exportToFile(resultList, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getFreqStep(String inputFile1, String inputFile2, String outputFile) {
        HashMap<Integer, Integer> resultList = new HashMap<Integer, Integer>();
        try {
            CSVReader reader = new CSVReader(new FileReader(inputFile1), '|');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Integer distance = Integer.valueOf(nextLine[0]);
                Integer repeat = Integer.valueOf(nextLine[1]);
                resultList.put(distance, repeat);
            }
            reader.close();
            reader = new CSVReader(new FileReader(inputFile2), '|');
            nextLine = null;
            while ((nextLine = reader.readNext()) != null) {
                Integer distance = Integer.valueOf(nextLine[0]);
                Integer repeat = Integer.valueOf(nextLine[1]);
                if (resultList.get(distance) != null) {
                    repeat = repeat + resultList.get(distance);
                }
                resultList.put(distance, repeat);
            }
            reader.close();
            Map<Integer, List<Integer>> repeatList = new TreeMap<Integer, List<Integer>>();
            for (Integer distance: resultList.keySet()) {
                Integer repeat = resultList.get(distance);
                List<Integer> distanceList = new ArrayList<Integer>();
                if (repeatList.get(repeat) != null) {
                    distanceList = repeatList.get(repeat);
                }
                distanceList.add(distance);
                repeatList.put(repeat, distanceList);
            }
            ExportUtil.exportToFile(repeatList, outputFile);
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
         rank1.add(FreqClazz.VERY_SMALL_LOW);
         rank1.add(FreqClazz.VERY_SMALL_MEDIUM);
         rank1.add(FreqClazz.VERY_SMALL_HIGH);
         List<FreqClazz> rank2 = new ArrayList<FreqClazz>();
         rank2.add(FreqClazz.SMALL_LOW);
         rank2.add(FreqClazz.SMALL_MEDIUM);
         rank2.add(FreqClazz.SMALL_HIGH);
         List<FreqClazz> rank3 = new ArrayList<FreqClazz>();
         rank3.add(FreqClazz.MEDIUM_LOW);
         rank3.add(FreqClazz.MEDIUM_MEDIUM);
         rank3.add(FreqClazz.MEDIUM_HIGH);
         List<FreqClazz> rank4 = new ArrayList<FreqClazz>();
         rank4.add(FreqClazz.HIGH_LOW);
         rank4.add(FreqClazz.HIGH_MEDIUM);
         rank4.add(FreqClazz.HIGH_HIGH);
         List<FreqClazz> rank5 = new ArrayList<FreqClazz>();
         rank5.add(FreqClazz.VERY_HIGH_LOW);
         rank5.add(FreqClazz.VERY_HIGH_MEDIUM);
         rank5.add(FreqClazz.VERY_HIGH_HIGH);
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
    public static void getDistanceStep(String inputFile, String outputFile) {
        SortedSet<Integer> sortedDistanceList = new TreeSet<Integer>();
        BufferedReader br;
        try {
             br = new BufferedReader(new FileReader(inputFile));
             String line = br.readLine();
             while (line != null) {
                 String[] orgDistanceList = line.substring(line.indexOf("[")+1, line.indexOf("]")).trim().split(",");
                 for (String distance: orgDistanceList) {
                     sortedDistanceList.add(Integer.valueOf(distance.trim()));
                 }
                 //continue read new line
                 line = br.readLine();
             }
             br.close();
             List<Integer> distanceList = new ArrayList<Integer>();
             for (Integer value : sortedDistanceList) {
                 distanceList.add(value);
             }
             ExportUtil.exportToFile(distanceList, outputFile);
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
                    }                 }
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
    public static void main(String[] args) {
        /*getStep("onTimeDetail_CC-AS_80.csv", "onTimeDetail_CC-AS_80_3.csv");
        getStep("lateTimeDetail_CC-AS_80.csv", "lateTimeDetail_CC-AS_80_3.csv");*/
        /*getFreqStep("onTimeDetail_CC-AS_80_1.csv", "onTimeDetail_CC-AS_80_2.csv");
        getFreqStep("lateTimeDetail_CC-AS_80_1.csv", "lateTimeDetail_CC-AS_80_2.csv");*/
        //getFreqStep("onTimeDetail_CC-AS_80_2.csv","lateTimeDetail_CC-AS_80_2.csv","CC-AS_80_Freq+Distance.csv");
        //getDistanceStep("CC-AS_80_Freq+Distance.csv", "CC-AS_80_Distance.csv");
        /*classifiedStepInRoute("onTimeDetail_CC-AS_80_1.csv","onTimeClassify_CC-AS_80.csv");
        classifiedStepInRoute("lateTimeDetail_CC-AS_80_1.csv","lateTimeClassify_CC-AS_80.csv");
        getClassifiedStepInRoute("onTimeClassify_CC-AS_80.csv","onTime","classifyRoute.csv");
        getClassifiedStepInRoute("lateTimeClassify_CC-AS_80.csv","lateTime","classifyRoute.csv");*/
       /* getCountStep("classifyRoute.csv", 0, "classifyRoute_VerySmallDistance.csv");
        getCountStep("classifyRoute.csv", 1, "classifyRoute_SmallDistance.csv");
        getCountStep("classifyRoute.csv", 2, "classifyRoute_MediumDistance.csv");
        getCountStep("classifyRoute.csv", 3, "classifyRoute_HighDistance.csv");
        getCountStep("classifyRoute.csv", 4, "classifyRoute_VeryHighDistance.csv");*/
        //classifyFreqStep("classifyRoute.csv","classifyRoute_Full.csv");
        getClassifyFreqStep("classifyRoute_Full.csv", "classifyRoute_V2.csv");
    }
}
