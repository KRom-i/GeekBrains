package WorkDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDateBaseSQLite {

    protected static Connection connection;

    public static void connect(){

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:DataBase/data-base-spartak.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
