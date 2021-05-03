package Lesson_5_Threads.Home_Lesson;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DemoLock {

//    Lock синфронизирует потоки

    public static void main(String[] args) throws InterruptedException {
        final Lock lock = new ReentrantLock();


        for (int i = 0; i < 5; i++) {
            final int I = i;


            new Thread(()->{
                try {

                    lock.lock();
                    System.out.printf("Поток %s заблокировал критическую секцию \n", I);
                    Thread.sleep(500);

                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.printf("Поток %s разблокировал критическую секцию \n", I);
                }

            }).start();

        }

    }
}
