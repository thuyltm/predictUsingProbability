package bk.master.classify;


public class Test {

    public static void main(String[] args) {
        System.out.println(EvaluateBayes.calculateAccuracy("CC-AS"));
        System.out.println(EvaluateBayes.calculateErrorRate("CC-AS"));
        System.out.println(EvaluateBayes.calculateAccuracy("AS-CC"));
        System.out.println(EvaluateBayes.calculateErrorRate("AS-CC"));
        /*
        System.out.println(EvaluateBayes.calculatePreciseOnTime("CC-AS"))
        System.out.println(EvaluateBayes.calculatePreciseLateTime("CC-AS"));
        System.out.println(EvaluateBayes.calculateErrorRate("CC-AS"));
        System.out.println(EvaluateBayes.calculatePreciseOnTime("AS-CC"));
        System.out.println(EvaluateBayes.calculatePreciseLateTime("AS-CC"));
        System.out.println(EvaluateBayes.calculateErrorRate("AS-CC"));*/
    }
}
