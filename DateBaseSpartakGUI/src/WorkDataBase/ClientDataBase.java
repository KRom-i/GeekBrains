package WorkDataBase;

import Logger.LOG;
import MySQLDB.ServerMySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDataBase {

    private static void statementClose(Statement statement){
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void resultSetClose(ResultSet resultSet){
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    Метод редактирует данные клиента
public static boolean editClientDateBase(ClientClass client){

    PreparedStatement statement = null;

    try {

        statement = ServerMySQL.getConnection ().prepareStatement(
                "UPDATE client_list SET " +
                        "FirstName = ?," +
                        " LastName = ?," +
                        " PatName = ?," +
                        " Telephone = ?," +
                        " DataBirth = ?," +
                        " Email = ?," +
                        " InfoClient = ?" +
                        "  WHERE id = ?;"
        );

        statement.setString(1, client.getFirstName());
        statement.setString(2, client.getLastName());
        statement.setString(3, client.getPatronymicName());
        statement.setString(4, client.getTelephone());
        statement.setString(5, client.getDateBirth());
        statement.setString(6, client.getEmail());
        statement.setString(7, client.getInfoClient());
        statement.setInt(8, client.getId());
        statement.executeUpdate();

        return true;
    } catch (SQLException e) {
        e.printStackTrace();

    } finally {
        statementClose(statement);
    }
    return false;
}

//    Метод добавляет нового клиента в базу данных

    public static boolean addNewClientDateBase(ClientClass client){

        PreparedStatement statement = null;

        try {

            String query = "INSERT INTO client_list (" +
                    "id, FirstName, LastName, PatName, Telephone, DataBirth, Email, InfoClient, BOOL_DEL" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            statement = ServerMySQL.getConnection ().prepareStatement(query);

            statement.setInt(1, client.getId());
            statement.setString(2, client.getFirstName());
            statement.setString(3, client.getLastName());
            statement.setString(4, client.getPatronymicName());
            statement.setString(5, client.getTelephone());
            statement.setString(6, client.getDateBirth());
            statement.setString(7, client.getEmail());
            statement.setString(8, client.getInfoClient());
            statement.setBoolean(9, false);

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statementClose(statement);
        }
        return false;
    }

//    Редактор ID в MySQL
public static void startNewID(String nameClass, int numberIP){

    PreparedStatement statement = null;

    try {

        String query = "INSERT INTO UP_ID (Class, Value_IP) VALUES (?, ?);";
        statement = ServerMySQL.getConnection ().prepareStatement(query);
        statement.setString(1, nameClass);
        statement.setInt(2, numberIP);
        statement.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        statementClose(statement);
    }

}

    // Метод добавляет новый id

    public static int idUpDateBase(String nameClass){

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM UP_ID WHERE Class = ?;"
            );

            statement.setString(1,nameClass);

            rs = statement.executeQuery();


            if (rs.next()){

                int Value_IP = rs.getInt("Value_IP");

                resultSetClose(rs);
                statementClose(statement);

                statement = ServerMySQL.getConnection ().prepareStatement(
                        "UPDATE UP_ID SET Value_IP = ? WHERE class = ?;"
                );

//                Новый IP
                statement.setInt(1, Value_IP + 1);

                statement.setString(2,nameClass);
                statement.executeUpdate();

                return Value_IP;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statementClose(statement);

        }
        return 0;
    }


//    Тест сортировки

    public static List<ClientClass> newListClient() {

        PreparedStatement statement = null;
        ResultSet rs = null;

        List<ClientClass> clientList = new ArrayList<>();

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM client_list ORDER BY LastName ASC;"
            );
//            statement.executeQuery();


//            statement = ServerMySQL.getConnection ().prepareStatement(
//                    "SELECT * FROM client_list WHERE BOOL_DEL = ?;"
//            );
//
//            statement.setBoolean(1, false);

            rs = statement.executeQuery();


            while (rs.next()) {

                if (!rs.getBoolean("BOOL_DEL")) {

                    ClientClass clientNew = new ClientClass();
                    clientNew.setId(rs.getInt("id"));
                    clientNew.setFirstName(rs.getString("FirstName"));
                    clientNew.setLastName(rs.getString("LastName"));
                    clientNew.setPatronymicName(rs.getString("PatName"));
                    clientNew.setTelephone(rs.getString("Telephone"));
                    clientNew.setDateBirth(rs.getString("DataBirth"));
                    clientNew.setEmail(rs.getString("Email"));
                    clientNew.setInfoClient(rs.getString("InfoClient"));
                    clientList.add(clientNew);
                }
            }

            return clientList;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statementClose(statement);
            resultSetClose(rs);
        }

        return null;
    }

//     Метод обновляет ClientList для работы
/*
    public static List<ClientClass> newListClient() {

        PreparedStatement statement = null;
        ResultSet rs = null;

        List<ClientClass> clientList = new ArrayList<>();

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM client_list ORDER BY LastName ASC;"
            );
            statement.executeQuery();


            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM client_list WHERE BOOL_DEL = ?;"
            );

            statement.setBoolean(1, false);

            rs = statement.executeQuery();


              while (rs.next()) {

                  ClientClass clientNew = new ClientClass();

                  clientNew.setId(rs.getInt("id"));
                  clientNew.setFirstName(rs.getString("FirstName"));
                  clientNew.setLastName(rs.getString("LastName"));
                  clientNew.setPatronymicName(rs.getString("PatName"));
                  clientNew.setTelephone(rs.getString("Telephone"));
                  clientNew.setDateBirth(rs.getString("DataBirth"));
                  clientNew.setEmail(rs.getString("Email"));
                  clientNew.setInfoClient(rs.getString("InfoClient"));

                  clientList.add(clientNew);

            }

            return clientList;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statementClose(statement);
            resultSetClose(rs);
        }

        return null;
    }
*/

    public static void delClient(ClientClass clientClass){

        PreparedStatement statement = null;

        try {


                statement = ServerMySQL.getConnection ().prepareStatement(
                        "UPDATE client_list SET BOOL_DEL = ? WHERE id = ?;"
                );

//                Новый IP


                statement.setBoolean(1, true);
                statement.setInt(2, clientClass.getId());
                statement.executeUpdate();

            LOG.info("Клиент удален из базы данных \n" + clientClass.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("Ошибка при удалении \n " + clientClass.toString(), e);
        } finally {
            statementClose(statement);
        }

    }
}
