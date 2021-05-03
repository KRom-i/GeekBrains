package Lesson_5_Threads.Home_Lesson;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class DemoCountDownLatch {

//    Обрадынй счетчик, после набора интового значения логика программы продолжается.

    public static void main (String[] args) throws InterruptedException{

        final int THREADS_COUNT = 6;

        final CountDownLatch cdl = new CountDownLatch(THREADS_COUNT);

        System.out.println("Готовимся");

        for (int i = 0; i < THREADS_COUNT; i++) {

            final int I = i;

                new Thread(()->{

                    try {

                        Thread.sleep(500 + (int) (500 * Math.random()));
                        cdl.countDown();
                        System.out.printf("Поток %s готов \n", I);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }).start();

        }

        cdl.await();

        System.out.println("Все готовы");
    }
}
