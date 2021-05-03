package Lesson_5_Threads.Home_Lesson;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class DemoCyclicBarrier {

//    Цыкли ожидания создаются при помощи метода
    // awain(), т.е. ожидает интовое значение потоков

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        final int THREADS_COUNT = 5;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREADS_COUNT);

        CountDownLatch countDownLatch = new CountDownLatch(THREADS_COUNT);

        for (int i = 0; i < THREADS_COUNT; i++) {

            final int I = i;

            new Thread(()->{

                try {

                    System.out.printf("Поток %s готовиться \n", I);
                    Thread.sleep(500 + (int) (500 * Math.random()));
                    System.out.printf("Поток %s готов \n", I);
                    cyclicBarrier.await();

                    System.out.printf("Поток %s начал первый этап \n", I);
                    Thread.sleep(500 + (int) (500 * Math.random()));
                    System.out.printf("Поток %s прошел первый этап \n", I);
                    cyclicBarrier.await();

                    System.out.printf("Поток %s начал второй этап \n", I);
                    Thread.sleep(500 + (int) (500 * Math.random()));
                    System.out.printf("Поток %s прошел второй этап \n", I);
                    cyclicBarrier.await();

                    countDownLatch.countDown();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();

        }

        countDownLatch.await();
        System.out.println("Гонка потоков завершена");

    }
}
