package Lesson_07;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Main {

    public static void main (String[] args) throws Exception{

//        doDemoReflectionClass();
//        doDemoReflectionField();
//        doDemoReflectionFieldAccess();
//        doDemoReflectionMethod();
    }

    private static void doDemoReflectionMethodAccess() throws Exception{


    }

    private static void doDemoReflectionMethod() throws Exception{

        Object human = new Human();
        Class<?> object = human.getClass();
        Method[] methods = object.getDeclaredMethods();

        for(Method m: methods
        ) {
            System.out.println(m.getModifiers());
            System.out.println(m.getName());
            System.out.println("_____________");
        }

    }


    private static void doDemoReflectionFieldAccess() throws Exception{

        Object human = new Human();
        Class<?> object = human.getClass();

        Field fieldName = object.getDeclaredField("privateField");
        fieldName.setAccessible(true);
        fieldName.set(human, "test private field");
        fieldName.setAccessible(false);
        System.out.println(human);

    }

    private static void doDemoReflectionField(){

        Object human = new Human();
        Class<?> object = human.getClass();
        Field[] fields = object.getFields();

        for(Field f: fields
            ) {
            System.out.println(f);
        }
        System.out.println("_____________________________________");

        Field[] declaredFields = object.getDeclaredFields();

        for(Field f: declaredFields
        ) {
            System.out.println(f.getModifiers());
            System.out.println(f.getName());
            System.out.println(f.getType());
            System.out.println("_____________");
        }

    }


    private static void doDemoReflectionClass(){

        Object human = new Human();

        System.out.println(human.getClass());
        System.out.println(human.getClass().getName());
        System.out.println(human.getClass().getSimpleName());

        Class<?> object = human.getClass();
        int modifiers = object.getModifiers();
        System.out.println(modifiers);
        System.out.println("public: " + Modifier.isPublic(modifiers));
        System.out.println("abstract: " + Modifier.isAbstract(modifiers));

    }
}
