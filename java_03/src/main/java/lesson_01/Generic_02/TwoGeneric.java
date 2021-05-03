package lesson_01.Generic_02;

public class TwoGeneric<T, V, D, F> {
//    Two (два)

    private T obj1;
    private V obj2;
    private D obj3;
    private F obj4;

    public TwoGeneric(T obj1, V obj2, D obj3, F odj4) {
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.obj3 = obj3;
        this.obj4 = obj4;
    }

    public void showTypes(){
        System.out.println("Тип T: " + obj1.getClass().getName());
        System.out.println("Тип V: " + obj2.getClass().getName());
        System.out.println("Тип D: " + obj3.getClass().getName());
    }

    public T getObj1() {
        return obj1;
    }

    public V getObj2() {
        return obj2;
    }

    public D getObj3() {
        return obj3;
    }

    public F getObj4() {
        return obj4;
    }
}
