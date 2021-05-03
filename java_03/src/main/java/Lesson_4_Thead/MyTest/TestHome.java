package Lesson_4_Thead.MyTest;

public class TestHome {

    public static void main(String[] args) {

        MyObject myObject = new MyObject();


        Thread[] threads = new Thread[1_000_000];
        for (int i = 0; i < threads.length; i++) {
            final int X = i;
            threads[i] = new Thread(() -> {
                myObject.demoMyObject(X);
            });
            threads[i].start();
        }
    }
}
