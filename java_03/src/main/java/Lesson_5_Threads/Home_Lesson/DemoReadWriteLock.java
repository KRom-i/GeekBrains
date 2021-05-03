package Lesson_5_Threads.Home_Lesson;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DemoReadWriteLock {

    public static void main(String[] args) {

        final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        for (int i = 0; i < 3; i++) {

            final int I = i;

            new Thread(()->{

                try {
                readWriteLock.readLock().lock();
                System.out.printf("Поток %s начал чтение \n", I);

                Thread.sleep(1000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.printf("Поток %s завершил чтение \n", I);
                    readWriteLock.readLock().unlock();
                }

            }).start();
        }

        for (int i = 0; i < 3; i++) {

            final int I = i;

            new Thread(()->{

                try {
                    readWriteLock.writeLock().lock();
                    System.out.printf("Поток %s начал запись \n", I);

                    Thread.sleep(1000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.printf("Поток %s завершил запись \n", I);
                    readWriteLock.writeLock().unlock();
                }

            }).start();
        }
    }
}
