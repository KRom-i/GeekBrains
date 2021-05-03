package lesson_02.datebase;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionService {

    private static Connection connection;

    private ConnectionService() {
    }

    public static Connection getConnection(){

        if (connection == null){
            try {
                connection = DriverManager.
                        getConnection("jdbc:mysql://localhost:3306/warehouse", "root", "limit2301");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
