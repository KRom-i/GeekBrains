package MySQLDB;

import Logger.LOG;

import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

public final class ServerMySQL {

    public static Connection connection;
    private static String LOCAL;
    private static String PORT;

    private ServerMySQL() {
    }

    private static void readConfig(){
        try (FileReader fileReader = new FileReader(
                new File("config.txt")
        )){
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()){
                String[] strings = scanner.nextLine().split(":");
                LOCAL = strings[0];
                PORT = strings[1];
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){

        if (LOCAL == null || PORT == null){
            readConfig();
        }

        if (connection == null){
            try {
                connection = DriverManager.
                        getConnection("jdbc:mysql://"+LOCAL+":"+PORT+"/DateBaseSpartak", "root", "limit2301");
            } catch (SQLException e) {
                LOG.error("Connection mysql", e);
                e.printStackTrace();
            }
        }

        return connection;
    }

    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("Connection.close mysql", e);
            e.printStackTrace();
        }
    }



}