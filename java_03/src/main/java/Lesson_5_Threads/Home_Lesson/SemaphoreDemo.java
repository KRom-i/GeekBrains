package Lesson_5_Threads.Home_Lesson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

//    Семафор является счетчиком. Интовое значение при инициализации семафора
    // колическо потоков имеющик доступ к ресурсу, после заверщения логики
    // поток освобождвет ресурс и заходит следующий (если таковой имеется)

    public static void main (String[] args) throws InterruptedException {

        Semaphore smp = new Semaphore(2);

        int threads = 5;


        for (int i = 0; i < threads; i++) {

            final int I = i;

            Thread.sleep(10);

            new Thread(()->{

                try{
                    System.out.printf("Поток %s перед семафором\n", I);

                    smp.acquire();

                    System.out.printf("Поток %s получил доступ к ресурсу\n", I);

                    Thread.sleep(1000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                } finally {

                    System.out.printf("Поток %s освободил ресурс \n", I);

                    smp.release();
                }

            }).start();


        }

    }
}
