package MyTest;

public class MyClassConstructorTest {


    public MyClassConstructorTest(String[] strings, int[] ints){

        for(String s: strings
            ) {
            System.out.println (s);
        }

        for(int i: ints
        ) {
            System.out.println (i);
        }

    }

    public MyClassConstructorTest(){

    }

}
