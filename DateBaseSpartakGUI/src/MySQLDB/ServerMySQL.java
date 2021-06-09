package MySQLDB;

import Logger.LOG;

import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

public final class ServerMySQL {

    private static Connection connection;
    private static String LOCAL;
    private static String PORT;
    private static String USER_LOG;
    private static String PASS;
    private static String URL;

    private ServerMySQL() {
    }

    private static void readConfig(){
        try (FileReader fileReader = new FileReader(
                new File("config.txt")
        )){
            Scanner s = new Scanner(fileReader);
            while (s.hasNextLine()){

                String str = s.nextLine();

                if (str.startsWith ("LOCAL")){
                    LOCAL = str.split("=")[1];
                }

                if (str.startsWith ("PORT")){
                    PORT = str.split("=")[1];
                }

                if (str.startsWith ("USER_LOG")){
                    USER_LOG = str.split("=")[1];
                }

                if (str.startsWith ("PASS")){
                    PASS = str.split("=")[1];
                }


            }

            LOG.info (String.format("Config connect DateBase: LOCAL[%s] PORT[%s] USER_LOG[%s] PASS[%s]" +
                    " \n", LOCAL,PORT,USER_LOG,PASS));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){

        if (connection == null){
            readConfig();
            try {
                URL = String.format ("jdbc:mysql://%s:%s/DateBaseSpartak", LOCAL,PORT );
                LOG.info (String.format ("Connect Date Base URL [%s]", URL));
                connection = DriverManager.
                        getConnection(URL, USER_LOG, PASS);

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

    public static void statementClose(Statement statement){
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resultSetClose(ResultSet resultSet){
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static Connection connectionNameDateBase;

    public static Connection getConnectionNameDateBase(String nameDateBase){
        if (connectionNameDateBase == null){
            readConfig();
            try {
                URL = String.format ("jdbc:mysql://%s:%s/%s", LOCAL,PORT, nameDateBase);
                LOG.info (String.format ("Connect Date Base URL [%s]", URL));
                connectionNameDateBase = DriverManager.
                        getConnection(URL, USER_LOG, PASS);
            } catch (SQLException e) {
                LOG.error("Connection mysql", e);
                e.printStackTrace();
            }
        }

        return connectionNameDateBase;
    }

    public static void disconnectNameDateBase(){
        try {
            connectionNameDateBase.close();
        } catch (SQLException e) {
            LOG.error("Connection.close mysql", e);
            e.printStackTrace();
        }
    }


}