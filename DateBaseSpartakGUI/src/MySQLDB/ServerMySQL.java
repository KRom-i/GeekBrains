package MySQLDB;

import java.sql.*;

public final class ServerMySQL {

    public static Connection connection;

    private ServerMySQL() {
    }

    public static Connection getConnection(){

        if (connection == null){
            try {
                connection = DriverManager.
                        getConnection("jdbc:mysql://localhost:3306/DateBaseSpartak", "root", "limit2301");
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