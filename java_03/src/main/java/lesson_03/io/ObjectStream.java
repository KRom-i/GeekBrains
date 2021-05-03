package lesson_03.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectStream {

    public static void main(String[] args) {

        File file = new File("src/main/java/lesson_03/io/Developers.txt");



        writeDeveloper(file);

        System.out.println(readDeveloperFile(file));


    }

    private static void writeDeveloper(File file) {

        Developer developer1 = new Developer("Mike", "JAVA", 4300);
        Developer developer2 = new Developer("John", "Python", 4300);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))){

            objectOutputStream.writeObject(developer1);
            objectOutputStream.writeObject(developer2);

        } catch (IOException e){
            e.printStackTrace();
        }


    }

    private static List<Developer> readDeveloperFile(File file){

        List<Developer> developerList = new ArrayList<>();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))){

            try {

                developerList.add((Developer) objectInputStream.readObject());
                developerList.add((Developer) objectInputStream.readObject());

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return developerList;
    }
}
