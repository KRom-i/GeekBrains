package com.roman;

import org.apache.log4j.Logger;

public class OwnMath {

    private static final Logger LOGGER = Logger.getLogger(OwnMath.class);

    private OwnMath(){}

    public static int min(int a, int b){
        int result = a > b ? b : a;
        LOGGER.info("Min value is: " + result);
        return result;
    }

    public static int max(int a, int b){
        int result = a < b ? b : a;
        LOGGER.debug("Min value is: " + result);
        return result;
    }

    public static double aDouble(double a, double b){
        if (b == 0){
            IllegalArgumentException e = new IllegalArgumentException("b == 0");
            LOGGER.error("aDouble error", e);
            throw e;
        }
        return a /b;
    }

    public static void someMethod(){
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
