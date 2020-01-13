import java.math.BigDecimal;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.*;

public class EulerBricks {

    public static BigDecimal a = new BigDecimal("0");
    public static BigDecimal b = new BigDecimal("0");
    public static BigDecimal c = new BigDecimal("0");

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        boolean win = false;
        boolean totalWin = false;
        getValues();

        BigDecimal add1 = new BigDecimal("1");

        do {
            
            a = a.add(add1);
            saveForNext(a, b, c);
            b = new BigDecimal("500000000001");
            /*
             * old version of the counter for (int i = 0; i < 1000; i++) { win = check(a, b,
             * c); a = a.add(add1); String string = "a: " + a.toString() + " b: " +
             * b.toString() + " c: " + c.toString(); System.out.println(string); if (win) {
             * 
             * saveResoult(a, b, c); totalWin = true; } }
             */


            int x = -1;
            do {
                BigDecimal l = a.subtract(new BigDecimal("1"));
                win = check(a, b, c);
                
                String string = "a: " + a.toString() + " b: " + b.toString() + " c: " + c.toString();
                System.out.println(string);
                if (win) {
                    saveResoult(a, b, c);
                    totalWin = true;
                }
                x = b.compareTo(l);

                saveForNext(a, b, c);
                
                BigDecimal h = b.subtract(new BigDecimal("1"));                
                int j = -1;
                int f = 0;
                do {
                    win = check(a, b, c);
                    
                    string = "a: " + a.toString() + " b: " + b.toString() + " c: " + c.toString();
                    System.out.println(string);
                    if (win) {
                        saveResoult(a, b, c);
                        totalWin = true;
                    }
                    j = c.compareTo(h);
                    c = c.add(add1);

                    f++;
                    if(f>=100000)
                    {
                        saveResoult(a, b, c);
                        f=0;
                    }


                } while (j == -1);  

                c = new BigDecimal("500000000000");
                b = b.add(add1);
                saveForNext(a, b, c);
            } while (x == -1);            
        } while (win == false || totalWin == false);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    saveForNext(a, b, c);
                } catch (FileNotFoundException e) {

                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }
            }
        });
    }

    private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

    private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
        BigDecimal fx = xn.pow(2).add(c.negate());
        BigDecimal fpx = xn.multiply(new BigDecimal(2));
        BigDecimal xn1 = fx.divide(fpx, 2 * SQRT_DIG.intValue(), RoundingMode.HALF_DOWN);
        xn1 = xn.add(xn1.negate());
        BigDecimal currentSquare = xn1.pow(2);
        BigDecimal currentPrecision = currentSquare.subtract(c);
        currentPrecision = currentPrecision.abs();
        if (currentPrecision.compareTo(precision) <= -1) {
            return xn1;
        }
        return sqrtNewtonRaphson(c, xn1, precision);
    }

    public static BigDecimal bigSqrt(BigDecimal c) {
        return sqrtNewtonRaphson(c, new BigDecimal(1), new BigDecimal(1).divide(SQRT_PRE));
    }

    public static Boolean ac(BigDecimal a, BigDecimal c) {

        BigDecimal aa = a.multiply(a);
        BigDecimal cc = c.multiply(c);
        BigDecimal sumac = aa.add(cc);
        BigDecimal sqrac = bigSqrt(sumac);

        if (Math.round(sqrac.doubleValue()) == sqrac.doubleValue())
            return true;

        return false;
    }

    public static Boolean ab(BigDecimal a, BigDecimal b) {

        BigDecimal aa = a.multiply(a);
        BigDecimal bb = b.multiply(b);
        BigDecimal sumac = aa.add(bb);
        BigDecimal sqrac = bigSqrt(sumac);

        if (Math.round(sqrac.doubleValue()) == sqrac.doubleValue())
            return true;

        return false;
    }

    public static Boolean bc(BigDecimal b, BigDecimal c) {

        BigDecimal bb = b.multiply(b);
        BigDecimal cc = c.multiply(c);
        BigDecimal sumac = bb.add(cc);
        BigDecimal sqrac = bigSqrt(sumac);

        if (Math.round(sqrac.doubleValue()) == sqrac.doubleValue())
            return true;

        return false;
    }

    public static Boolean abc(BigDecimal a, BigDecimal c, BigDecimal b) {

        BigDecimal aa = a.multiply(a);
        BigDecimal cc = c.multiply(c);
        BigDecimal bb = b.multiply(b);
        BigDecimal sumac = aa.add(cc).add(bb);
        BigDecimal sqrac = bigSqrt(sumac);

        if (Math.round(sqrac.doubleValue()) == sqrac.doubleValue())
            return true;

        return false;
    }

    public static Boolean check(BigDecimal a, BigDecimal c, BigDecimal b)
            throws FileNotFoundException, UnsupportedEncodingException {
        if (!ac(a, c)) {
            return false;
        }
        if (!ab(a, b)) {
            return false;
        }
        if (!bc(b, c)) {
            return false;
        }

        partialright(a, b, c);

        if (!abc(a, c, b)) {
            return false;
        }

        return true;
    }

    public static void saveResoult(BigDecimal a, BigDecimal b, BigDecimal c)
            throws FileNotFoundException, UnsupportedEncodingException {
        String win = "" + a.toString() + "/" + b.toString() + "/" + c.toString();
        PrintWriter writer = new PrintWriter("win.txt", "UTF-8");
        writer.println(win);
        writer.close();

    }

    public static void partialright(BigDecimal a, BigDecimal b, BigDecimal c)
            throws FileNotFoundException, UnsupportedEncodingException {
        String win = "" + a.toString() + "/" + b.toString() + "/" + c.toString();
        PrintWriter writer = new PrintWriter("3right.txt", "UTF-8");
        writer.println(win);
        writer.close();

    }

    public static void saveForNext(BigDecimal a, BigDecimal b, BigDecimal c)
            throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("lastTime.txt", "UTF-8");
        writer.println(a.toString());
        writer.println(b.toString());
        writer.println(c.toString());
        writer.close();

    }

    public static void getValues() throws FileNotFoundException {
        File file = new File("lastTime.txt");
        Scanner sc = new Scanner(file);

        String[] s = new String[4];
        int i = 0;
        while (sc.hasNextLine()) {
            s[i] = sc.nextLine();
            i++;
        }

        a = new BigDecimal(s[0]);
        b = new BigDecimal(s[1]);
        c = new BigDecimal(s[2]);

        sc.close();
    }

}