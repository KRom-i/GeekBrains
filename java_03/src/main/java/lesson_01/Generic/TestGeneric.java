package lesson_01.Generic;

public class TestGeneric<T>{
//    generic (общий, обобщенный)

    private T obj;

    public TestGeneric(T obj) {
        this.obj = obj;
    }

    public T getObj() {
//        return (возвращать)
        return obj;
    }

    public void showType(){
//        show (показать) type (тип)
        System.out.println("Type T: " + obj.getClass().getName());
    }
}
