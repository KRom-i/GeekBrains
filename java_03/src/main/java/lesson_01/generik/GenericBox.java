package lesson_01.generik;

public class GenericBox<T> {

    private T object;

    public void put(T o){
        System.out.println("Open box");
        System.out.println(o + " add box");
        this.object = o;
        System.out.println("Close box \n");
    }

    public T get(){
        System.out.println("Open box");
        if (object != null){
            System.out.println("Предмет " + object + "доступен");
            return object;
        } else {
            System.out.println("Ящик пуст");
        }
        return null;
    }
}
