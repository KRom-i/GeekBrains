package lesson_01.Generic_02;

import javax.swing.*;

public class SimpleGenericApp {

    public static void main(String[] args) {

        TwoGeneric<Integer, String, Double, JFrame> twoGeneric
                = new TwoGeneric<>(500, "java String", 100.99, new MyJFrame());
        twoGeneric.showTypes();

        int valueFromGetInt = twoGeneric.getObj1();
        String valueFromGetString = twoGeneric.getObj2();
        Double valueFromGetDouble = twoGeneric.getObj3();

        System.out.println(valueFromGetInt);
        System.out.println(valueFromGetString);
        System.out.println(valueFromGetDouble);

        twoGeneric.getObj4();

    }
}
