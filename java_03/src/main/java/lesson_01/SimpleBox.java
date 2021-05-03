package lesson_01;

public class SimpleBox {

    private Object object;

    public void put(Object o){
        System.out.println("Open box");
        System.out.println(o + " add box");
        this.object = o;
        System.out.println("Close box \n");
    }

    public Object get(){
        System.out.println("Open box");
        if (object != null){
            System.out.println("Предмет " + object + "доступен");
        } else {
            System.out.println("Ящик пуст");
        }
        return object;
    }
}
