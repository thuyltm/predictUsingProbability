package bk.master.classify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import bk.master.input.model.Leg;

public class GroupDuration {
    private static Long DURATION_STEP_SHORT = 20L;
    public static void groupToStandardDuration(List<Leg> legList, List<String> outputList) {
        List<Integer> result = new ArrayList<Integer>();
        HashMap<Long, List<Integer>> mapDurationDistance = new HashMap<Long, List<Integer>>();
        List<Long> redundantDuration = new ArrayList<Long>();
        for (Leg leg : legList) {
            Long duration = leg.getDuration()/1000;
            Integer distance = leg.getDistance();
            if (duration % DURATION_STEP_SHORT == 0) {
               divideBigDuration(result, duration, distance);
            } else {
                List<Integer> valueList = new ArrayList<Integer>();
                if (mapDurationDistance.get(duration)!=null) {
                    valueList = mapDurationDistance.get(duration);
                }
                valueList.add(distance);
                mapDurationDistance.put(duration, valueList);
                redundantDuration.add(duration);
            }
        }
        Collections.sort(redundantDuration, new Comparator<Long>() {
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        });
        accumulateDoubleElmt(redundantDuration,mapDurationDistance,result,outputList);
        outputList.add(result.toString());
    }

    public static void accumulateDoubleElmt(List<Long> sortedDuration,
            HashMap<Long, List<Integer>> mapDurationDistance, List<Integer> result,
            List<String> outputList) {
        List<Integer> blockList = new ArrayList<Integer>();
        int maxIndex = sortedDuration.size();
        for (int i = 0; i <= maxIndex-2; i++) {
            for (int j = i+1; j <= maxIndex-1; j++) {
                long duration1 = sortedDuration.get(i);
                long duration2 = sortedDuration.get(j);
                long sumDuration = duration1+duration2;
                if (sumDuration % DURATION_STEP_SHORT == 0 && !blockList.contains(i) && !blockList.contains(j)) {
                    blockList.add(i);
                    blockList.add(j);
                    Integer distance1 = mapDurationDistance.get(duration1).remove(0);
                    if (mapDurationDistance.get(duration1).size()==0) {
                        mapDurationDistance.remove(duration1);
                    }
                    Integer distance2 = mapDurationDistance.get(duration2).remove(0);
                    if (mapDurationDistance.get(duration2).size()==0) {
                        mapDurationDistance.remove(duration2);
                    }
                    Integer sumDistance = distance1 + distance2;
                    divideBigDuration(result, sumDuration, sumDistance);
                    break;
                }
            }
        }
        List<Long> redundantList = new ArrayList<Long>();
        for (int i = 0; i < maxIndex; i++) {
            if (!blockList.contains(i)) {
                redundantList.add(sortedDuration.get(i));
            }
        }
        if (redundantList.size() >= 3) {
            accumulateTripleElmt(redundantList,mapDurationDistance,result,outputList);
        } else {
            accumulateInfiniteSequenceElmt(redundantList,mapDurationDistance,result,outputList);
        }
    }

    public static void accumulateTripleElmt(List<Long> sortedDuration,
            HashMap<Long, List<Integer>> mapDurationDistance, List<Integer> result,
            List<String> outputList) {
        List<Integer> blockList = new ArrayList<Integer>();
        int maxIndex = sortedDuration.size();
        for (int i = 0; i <= maxIndex-3; i++) {
            for (int j = i+1; j <= maxIndex-2; j++) {
                for (int k = j+1; k <= maxIndex-1; k++) {
                    long duration1 = sortedDuration.get(i);
                    long duration2 = sortedDuration.get(j);
                    long duration3 = sortedDuration.get(k);
                    long sumDuration = duration1+duration2+duration3;
                    if (sumDuration % DURATION_STEP_SHORT == 0 && !blockList.contains(i)
                        && !blockList.contains(j) && !blockList.contains(k)) {
                        blockList.add(i);
                        blockList.add(j);
                        blockList.add(k);
                        Integer distance1 = mapDurationDistance.get(duration1).remove(0);
                        if (mapDurationDistance.get(duration1).size()==0) {
                            mapDurationDistance.remove(duration1);
                        }
                        Integer distance2 = mapDurationDistance.get(duration2).remove(0);
                        if (mapDurationDistance.get(duration2).size()==0) {
                            mapDurationDistance.remove(duration2);
                        }
                        Integer distance3 = mapDurationDistance.get(duration3).remove(0);
                        if (mapDurationDistance.get(duration3).size()==0) {
                            mapDurationDistance.remove(duration3);
                        }
                        Integer sumDistance = distance1 + distance2 + distance3;
                        divideBigDuration(result, sumDuration, sumDistance);
                        break;
                    }
                }
            }
        }
        List<Long> redundantList = new ArrayList<Long>();
        for (int i = 0; i < maxIndex; i++) {
            if (!blockList.contains(i)) {
                redundantList.add(sortedDuration.get(i));
            }
        }
        if (redundantList.size() >= 4) {
            accumulateFourElmt(redundantList,mapDurationDistance,result,outputList);
        } else {
            accumulateInfiniteSequenceElmt(redundantList,mapDurationDistance,result,outputList);
        }
    }
    public static void accumulateFourElmt(List<Long> sortedDuration,
            HashMap<Long, List<Integer>> mapDurationDistance, List<Integer> result,
            List<String> outputList) {
        List<Integer> blockList = new ArrayList<Integer>();
        int maxIndex = sortedDuration.size();
        for (int i = 0; i <= maxIndex-4; i++) {
            for (int j = i+1; j <= maxIndex-3; j++) {
                for (int k = j+1; k <= maxIndex-2; k++) {
                    for (int m = k+1; m <= maxIndex-1; m++) {
                        long duration1 = sortedDuration.get(i);
                        long duration2 = sortedDuration.get(j);
                        long duration3 = sortedDuration.get(k);
                        long duration4 = sortedDuration.get(m);
                        long sumDuration = duration1+duration2+duration3+duration4;
                        if (sumDuration % DURATION_STEP_SHORT == 0 && !blockList.contains(i)
                            && !blockList.contains(j) && !blockList.contains(k) && !blockList.contains(m)) {
                            blockList.add(i);
                            blockList.add(j);
                            blockList.add(k);
                            blockList.add(m);
                            Integer distance1 = mapDurationDistance.get(duration1).remove(0);
                            if (mapDurationDistance.get(duration1).size()==0) {
                                mapDurationDistance.remove(duration1);
                            }
                            Integer distance2 = mapDurationDistance.get(duration2).remove(0);
                            if (mapDurationDistance.get(duration2).size()==0) {
                                mapDurationDistance.remove(duration2);
                            }
                            Integer distance3 = mapDurationDistance.get(duration3).remove(0);
                            if (mapDurationDistance.get(duration3).size()==0) {
                                mapDurationDistance.remove(duration3);
                            }
                            Integer distance4 = mapDurationDistance.get(duration4).remove(0);
                            if (mapDurationDistance.get(duration4).size()==0) {
                                mapDurationDistance.remove(duration4);
                            }
                            Integer sumDistance = distance1 + distance2 + distance3 + distance4;
                            divideBigDuration(result, sumDuration, sumDistance);
                            break;
                        }
                    }
                }
            }
        }
        List<Long> redundantList = new ArrayList<Long>();
        for (int i = 0; i < maxIndex; i++) {
            if (!blockList.contains(i)) {
                redundantList.add(sortedDuration.get(i));
            }
        }
        accumulateInfiniteSequenceElmt(redundantList,mapDurationDistance,result,outputList);
    }

    public static void accumulateInfiniteSequenceElmt(List<Long> sortedDuration,
            HashMap<Long, List<Integer>> mapDurationDistance, List<Integer> result,
            List<String> outputList) {
         int i = 0;
         int j = 1;
         int maxIndex = sortedDuration.size();
         while (i<maxIndex) {
             long sumDuration = sortedDuration.get(i);
             while (j < maxIndex) {
                 sumDuration += sortedDuration.get(j);
                 if (sumDuration%DURATION_STEP_SHORT==0) {
                     int sumDistance=0;
                     for (int k = i; k <= j; k++) {
                         long duration = sortedDuration.get(k);
                         List<Integer> distanceList = mapDurationDistance.get(duration);
                         sumDistance += distanceList.remove(0);
                         if (distanceList.size()==0) {
                             mapDurationDistance.remove(duration);
                         }
                     }
                     divideBigDuration(result, sumDuration, sumDistance);
                     i=j+1;
                     j=i+1;
                     break;
                 } else {
                     j++;
                 }
             }
             if (j == maxIndex) {
                 break;
             }
         }
         List<Long> redundantList = new ArrayList<Long>();
         while (i<maxIndex) {
             redundantList.add(sortedDuration.get(i));
             i++;
         }
         getMinRedundantSequenceElmt(redundantList,mapDurationDistance,result,outputList);
    }
    public static void getMinRedundantSequenceElmt(List<Long> sortedDuration,
            HashMap<Long, List<Integer>> mapDurationDistance, List<Integer> result,
            List<String> outputList) {
        List<Long> outputReduntDurationList = new ArrayList<>();
        int i = 0;
        int j = 1;
        int stopIndex = 0;
        int maxIndex = sortedDuration.size();
        while (i<maxIndex) {
            long sumDuration = sortedDuration.get(i);
            stopIndex = i;
            long compareNumber = DURATION_STEP_SHORT;
            if (sumDuration/DURATION_STEP_SHORT>0) {
                compareNumber = DURATION_STEP_SHORT*(sumDuration/DURATION_STEP_SHORT);
            }
            long min = Math.abs(sumDuration - compareNumber);
            while (j < maxIndex) {
                sumDuration += sortedDuration.get(j);
                long currentCompareNumber = DURATION_STEP_SHORT;
                if (sumDuration/DURATION_STEP_SHORT>0) {
                    currentCompareNumber = DURATION_STEP_SHORT*(sumDuration/DURATION_STEP_SHORT);
                }
                long currentMin = Math.abs(sumDuration - currentCompareNumber);
                if (currentMin <= min) {
                    stopIndex = j;
                    min = currentMin;
                }
                j++;
            }
            if (stopIndex != i) {
                sumDuration = 0;
                int sumDistance=0;
                for (int k = i; k <= stopIndex; k++) {
                    sumDuration += sortedDuration.get(k);
                    long duration = sortedDuration.get(k);
                    List<Integer> distanceList = mapDurationDistance.get(duration);
                    sumDistance += distanceList.remove(0);
                    if (distanceList.size()==0) {
                        mapDurationDistance.remove(duration);
                    }
                }
                divideBigDuration(result, sumDuration, sumDistance);
                outputReduntDurationList.add(sumDuration);
            } else {
                long duration = sortedDuration.get(i);
                List<Integer> distanceList = mapDurationDistance.get(duration);
                int distance = distanceList.remove(0);
                if (distanceList.size()==0) {
                    mapDurationDistance.remove(duration);
                }
                divideBigDuration(result, duration, distance);
                outputReduntDurationList.add(sortedDuration.get(i));
            }
            i = stopIndex + 1;
            j = i + 1;
        }
        outputList.add(outputReduntDurationList.toString());
    }
    private static void divideBigDuration(List<Integer> result,
            long sumDuration, int sumDistance) {
        if (sumDuration/DURATION_STEP_SHORT > 0) {
            int loop = (int)(sumDuration/DURATION_STEP_SHORT);
            int distance = (int)(sumDistance*DURATION_STEP_SHORT/sumDuration);
            for (int rep = 1; rep <= loop; rep++) {
                result.add(distance);
            }
        } else if (sumDuration%DURATION_STEP_SHORT>=15) {
            result.add(sumDistance);
        }
    }

}
