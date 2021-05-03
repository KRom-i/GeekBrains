package lesson_01.generik;

import lesson_01.Melon;

public class main2 {

    public static void main(String[] args) {

        GenericBox<lemon> box = new GenericBox<>();
        lemon lemon = new lemon();
        box.put(lemon);
        openBox(box);

//        lemon newlemon = box.get();
//        System.out.println("Достали из ящика " + newlemon);

        GenericBox<Aplle> box2 = new GenericBox<>();
        box2.put(new Aplle());
//        openBox(box2);



    }

    public static void openBox(GenericBox<lemon> box){
        box.get();
    }
}
