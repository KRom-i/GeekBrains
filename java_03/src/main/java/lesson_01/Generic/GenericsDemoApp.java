package lesson_01.Generic;

public class GenericsDemoApp {

    public static void main(String[] args) {

        TestGeneric<String> genericString = new TestGeneric<>("Hello");
        genericString.showType();
        System.out.println("genericString.getObj(): " + genericString.getObj());

        TestGeneric<Integer> genericInteger = new TestGeneric<>(100);
        genericInteger.showType();
        System.out.println("genericInteger.getObj(): " + genericInteger.getObj());

        int valueFromGetInt = genericInteger.getObj();
        String valueFromGetString = genericString.getObj();
//        value (значение) from (из)

        genericInteger = new TestGeneric<>(100);
//        genericInteger = new TestGeneric<>("String java"); error compilation (ошибка компилации)

//    genericInteger = genericString   (error compilation)


    }
}
