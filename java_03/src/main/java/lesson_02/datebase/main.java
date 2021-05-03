package lesson_02.datebase;

import HomeWork_1_Java_3.Box.Apple;
import HomeWork_1_Java_3.Box.BoxFruit;
import HomeWork_1_Java_3.Box.Orange;

import java.sql.Connection;

public class main {

    public static void main(String[] args) {

        FruitsRepository fruitsRepository = new FruitsRepository();

        BoxFruit<Apple> appleBoxFruit = new BoxFruit<>();
        appleBoxFruit.add(new Apple(1.6f));
        appleBoxFruit.add(new Apple(1.6f));
        appleBoxFruit.add(new Apple(1.6f));
        appleBoxFruit.add(new Apple(1.6f));

        int row = fruitsRepository.create(appleBoxFruit);
        System.out.println(row);

        int row1 = fruitsRepository.create(new BoxFruit<Orange>());
        System.out.println(row1);

        System.out.println(fruitsRepository.readAll());

        ConnectionService.disconnect();

    }
}
