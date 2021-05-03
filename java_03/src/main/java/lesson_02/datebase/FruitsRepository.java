package lesson_02.datebase;

import HomeWork_1_Java_3.Box.BoxFruit;
import HomeWork_1_Java_3.Box.Fruit;

import java.sql.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class FruitsRepository<T extends BoxFruit<? extends Fruit>>{


    public int create(T box){
        Connection connection = ConnectionService.getConnection();

        int result = -1;

        try {

            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO boxes (name, type, weight) VALUES (?,?,?)"
            );


            float weight = 0;

            String nameBox = "box";

            if (box.size() > 0) {
                weight = box.getWeight();
                nameBox = box.get(0).getClass().getName();
            }

            statement.setString(1,nameBox);
            statement.setInt(2,2);
            statement.setFloat(3, weight);

            result = statement.executeUpdate();

            connection.commit();

            /*
            Statement statement = connection.createStatement();

            float weight = 0;

            if(box.size() > 0){
                weight = box.getWeight();
            }

            result = statement.executeUpdate(
                    String.format(
                            "INSERT INTO boxes (name, type, weight) VALUES ('%s',%s,%s);",
                            "box1","2", "1.2"
                    )
            );
             */

            statement.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Fruit> readAll(){

        ArrayList<Fruit> fruits = new ArrayList<>();
        Connection connection = ConnectionService.getConnection();

//        Все объекты из базы по наименованию столбца
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM fruits"
            );

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                fruits.add(
                        new Fruit(
                                rs.getString("name"),
                                rs.getInt("id")

                        )
                );
            }

            statement.close();
            rs.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

//        Выборка по условию

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM fruits WHERE id = 1"
            );

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                fruits.add(
                        new Fruit(
                                rs.getString("name"),
                                rs.getInt("id")

                        )
                );
            }

            statement.close();
            rs.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

//        Все объекты из базы по наименованию столбца по номеру столбца

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM fruits "
            );

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                fruits.add(
                        new Fruit(
                                rs.getString(2),
                                rs.getInt(1)

                        )
                );
            }

            statement.close();
            rs.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

//        Выборка по условию с форматом строки

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM fruits WHERE name LIKE ?"
            );

            statement.setString(1, "app%");

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                fruits.add(
                        new Fruit(
                                rs.getString(2),
                                rs.getInt(1)

                        )
                );
            }

            statement.close();
            rs.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        
        return fruits;
    }

}
