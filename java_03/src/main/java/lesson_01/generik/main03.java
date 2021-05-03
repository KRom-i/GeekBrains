package lesson_01.generik;

public class main03 {

    public static void main(String[] args) {
        GenericBox<lemon> box1 = new GenericBox<>();
        GenericBox<Aplle> box2 = new GenericBox<>();
        GenericBox<String> box3 = new GenericBox<>();

        box1.put(new lemon());
        box2.put(new Aplle());
        box3.put("test string");

        openBox(box1);
        openBox(box2);
        openBox(box3);

        openBox2(box1);
        openBox2(box2);

        openBox3(box2);

//        GenericBox<Aplle>[] boxes = new GenericBox[10];
//        Невозможно создать массив

//        GenericBox[] boxes = new GenericBox[10]
//          Можно но не нужно

//        GenericBox<?>[] boxes = new GenericBox<?>[10];
//        boxes[0] = new GenericBox<Aplle>();
//        boxes[1] = new GenericBox<lemon>();
//          Возможно формирование массива



    }

    public static void openBox(GenericBox<?> box){
        box.get();
    }

    public static void openBox2(GenericBox<? extends Product> box){
        box.get();
    }

    public static void openBox3(GenericBox<? super Aplle> box){
        box.get();
    }
}
