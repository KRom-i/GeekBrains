package lesson_05.HomeWork_05_Threads;

import java.util.Arrays;

public class ArrayThreads {

    static final int SIZE = 10_000_000;
    static final int NUM_T = 4;

    public static void main(String[] args) {

        method1();
        method2();

    }

    private static void method1(){

        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1f);

        long timeMethod = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }

        timeMethod = System.currentTimeMillis() - timeMethod;

        System.out.println("Method 1 time: " + timeMethod + " ms.");


    }


    private static void method2() {

        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1f);

        long timeMethod = System.currentTimeMillis();

        long timeDivide = System.currentTimeMillis();

        int nD = SIZE/NUM_T;
        float[][] arrCopy = new float[NUM_T][nD];

        for (int i = 0, j = 0; i < arrCopy.length; i++, j+=nD) {
            System.arraycopy(arr, j, arrCopy[i], 0, nD);
        }
        timeDivide = System.currentTimeMillis() - timeDivide;


        long[] timeRec = new long[NUM_T];
        Thread[] t = new Thread[NUM_T];

        for (int i = 0, x = 0; i < arrCopy.length; i++, x+=nD) {
            int finalI = i;
            int finalX = x;
            t[i] = new Thread(()-> {
                timeRec[finalI] = System.currentTimeMillis();
                for (int j = 0; j < arrCopy[finalI].length; j++) {

                    arrCopy[finalI][j] = (float)(arrCopy[finalI][j] * Math.sin(0.2f + (j + finalX) / 5)
                            * Math.cos(0.2f + (j + finalX) / 5) *
                            Math.cos(0.4f + (j + finalX) / 2));}
                timeRec[finalI] = System.currentTimeMillis() - timeRec[finalI];
            });
            t[i].start();
        }



        for (Thread tJ: t) {
            try { tJ.join();} catch (InterruptedException e) { e.printStackTrace();}}

        long timeGlue  = System.currentTimeMillis();
        for (int i = 0, j = 0; i < arrCopy.length; i++, j+=nD) {
            System.arraycopy(arrCopy[i], 0,arr, j, nD);
        }
        timeGlue = System.currentTimeMillis() - timeGlue;

        timeMethod = System.currentTimeMillis() - timeMethod;

        System.out.println("Division time: " + timeDivide + " ms.");
        for (int i = 0; i < timeRec.length; i++) {
            System.out.println("Recalculation time. Thread - "+ (i + 1) + ": " + timeRec[i] + " ms.");
        }
        System.out.println("Gluing time: " + timeGlue + " ms.");
        System.out.println("Method 2 time: " + timeMethod + " ms.");


    }

}

