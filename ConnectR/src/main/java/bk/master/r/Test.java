package bk.master.r;

import org.rosuda.JRI.Rengine;

public class Test {
    static {
       // System.out.println(System.getProperty("java.library.path"));
        //System.loadLibrary("libjri.so");
    }
    public static void main(String[] args) {
        // Create an R vector in the form of a string.
        String javaVector = "c(141,192,341,177,143,41,99,137,210,307,320,76,34,3,240,2106,1550,262,399,226,313,29,184,323,2421,351,2350,2353,195,409,441,2559,334,212,377,216,405,423,475,539,466,403,410,338,107,279,309,182,428,469,451,59,163,347,206,401,193,348,393,132,384,480,425,1358,2107,137,252,52,313,354,121,328,448,209,1952,1942,1200)";
        System.out.println(System.getProperty("java.library.path"));
        // Start Rengine.
        Rengine engine = new Rengine(new String[] { "--no-save" }, false, null);

        // The vector that was created in JAVA context is stored in 'rVector' which is a variable in R context.
        engine.eval("d = " + javaVector);

        //Calculate MEAN of vector using R syntax.
        engine.eval("x=length(d)");

        engine.eval("png(filename='test.png')");

        engine.eval("temp <- plot(1:x, d, type='b', axes=FALSE,xlab = 'bước', ylab = 'giá trị (m)')");
        engine.eval("temp <- axis(side=1, at=c(1:x))");
        engine.eval("temp <- axis(side=2, at=seq(min(d), max(d), by=100))");
        engine.eval("temp <- box()");
        engine.eval("dev.off()");
        System.exit(0);
        //Retrieve MEAN value
       // int len = engine.eval("x").asInt();

        //Print output values
        //System.out.println("len of given vector is=" + len);
    }

    public static void test1() {
       // Create an R vector in the form of a string.
        String javaVector = "c(1,2,3,4,5)";
       // System.out.println(System.getProperty("java.library.path"));
        // Start Rengine.
        Rengine engine = new Rengine(new String[] { "--no-save" }, false, null);

        // The vector that was created in JAVA context is stored in 'rVector' which is a variable in R context.
        engine.eval("rVector=" + javaVector);

        //Calculate MEAN of vector using R syntax.
        engine.eval("meanVal=mean(rVector)");

        //Retrieve MEAN value
        double mean = engine.eval("meanVal").asDouble();

        //Print output values
        System.out.println("Mean of given vector is=" + mean);
    }
}
