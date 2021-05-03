package HomeWork_1_Java_3.Box;

import java.util.ArrayList;

public class BoxDemoApp {

    public static void main(String[] args) {

        BoxFruit<Apple> appleBoxFruit = new BoxFruit<>();
        appleBoxFruit.add(new Apple(1.1f));
        appleBoxFruit.add(new Apple(1.1f));
        appleBoxFruit.add(new Apple(1.1f));
        appleBoxFruit.add(new Apple(1.1f));
        appleBoxFruit.add(new Apple(1.1f));


        BoxFruit<Orange> orangeBoxFruit = new BoxFruit<>();
        orangeBoxFruit.add(new Orange(1.5f));
        orangeBoxFruit.add(new Orange(1.5f));
        orangeBoxFruit.add(new Orange(1.5f));
        orangeBoxFruit.add(new Orange(1.5f));
        orangeBoxFruit.add(new Orange(1.5f));
        orangeBoxFruit.add(new Orange(1.5f));


        System.out.println(orangeBoxFruit.compare(orangeBoxFruit));

        BoxFruit<Apple> appleBoxFruit1 = new BoxFruit<>();
        appleBoxFruit.moveFruitToBox(appleBoxFruit1);

    }

}
