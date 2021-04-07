package Server;

import java.sql.*;

public class AuthSetvice {

    private static Connection connection;
    private static Statement statement;

    public static void connect(){

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chat-db.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static String getNicknameByLoginAndPassword(String login, String password){
        String query = String.format("select nickname from users where login='%s' and password='%s'"
        ,login ,password);

        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()){
                return rs.getString("nickname");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void dicconnect(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
