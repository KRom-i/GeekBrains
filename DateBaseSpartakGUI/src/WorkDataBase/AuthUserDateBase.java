package WorkDataBase;

import Cash.Transaction;
import GUIMain.CustomStage.SystemErrorStage;
import Logger.LOG;
import MySQLDB.ServerMySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthUserDateBase {


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


//    Метод добаляет нового пользователя

    public void addNewUsr(String nickname, String login, int password){

        PreparedStatement statement = null;

        try {

            String query = "INSERT INTO users (" +
                    "idUser, nameUser, loginUser, passwordUser, authUser)" +
                    " VALUES (?, ?, ?, ?, ?);";


            statement = ServerMySQL.getConnection ().prepareStatement(query);

            statement.setInt (1, getNewIdUser () + 1);
            statement.setString (2, nickname);
            statement.setString(3, login);
            statement.setInt(4, password);
            statement.setBoolean (5, false);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statementClose(statement);
        }

    }


//    Новый id опльзователя

    private int getNewIdUser(){


        PreparedStatement statement = null;
        ResultSet rs = null;
        Transaction transaction = null;
        int id = 0;
        try {

//            statement = ServerMySQL.getConnection ().prepareStatement(
//                    "SELECT * FROM CashBook;"
//            );


            statement = ServerMySQL.getConnection ().prepareStatement (
                    "SELECT * FROM users ORDER BY idUser DESC LIMIT 0, 1;"
            );


            rs = statement.executeQuery ();



            while (rs.next ()) {
                id =  (rs.getInt (1));
                LOG.info (String.format  ("Новый id пользователя: [%s] \n", id + ""));
            }


        } catch (SQLException e) {
            e.printStackTrace ();
new SystemErrorStage (e);
        } finally {
            ServerMySQL.statementClose (statement);
            ServerMySQL.resultSetClose (rs);
        }

        return id;
    }



    //    Авторизация пользователя через БД.
    public static UserSpartak newUserAuth(String login, int password){

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM users WHERE loginUser = ?;"
            );

            statement.setString(1,login);

            rs = statement.executeQuery();

            if (rs.next()){

                int dbHash = rs.getInt("passwordUser");

                if (password == dbHash){

                    return new UserSpartak(
                            rs.getInt(1), rs.getString(2),
                            rs.getString(3), dbHash,
                            rs.getBoolean(5)
                    );

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statementClose(statement);
            resultSetClose(rs);
        }

        return null;
    }

//    Изменение записи пользовательской сессии после авторизации

   public static void editUserAuth(String login, boolean auth){

       PreparedStatement statement = null;

       try {

           statement = ServerMySQL.getConnection ().prepareStatement(
                   "UPDATE users SET authUser = ? WHERE loginUser = ?;"
           );

//           Новое значение
           statement.setBoolean(1, auth);

           statement.setString(2,login);
           statement.executeUpdate();

       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           statementClose(statement);

       }
   }


    public void setSavePassword(int id, String pass){

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE users SET savePassword = ? WHERE idUser = ?;"
            );

//           Новое значение
            statement.setString (1, pass);

            statement.setInt (2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statementClose(statement);

        }
    }

    public String getSavePassword(int id){

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM users WHERE idUser = ?;"
            );

            statement.setInt (1, id);

            rs = statement.executeQuery();

            if (rs.next()){
                return rs.getString ("savePassword");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statementClose(statement);
            resultSetClose(rs);
        }

        return null;
    }


    //    Изменение данных пользователя

    public static void updateUser(UserSpartak userUpdate){

        PreparedStatement statement = null;

        try {

            UserSpartak userAuth = AuthUserDateBase.checkUserAuthStart ();


            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE users SET nameUser = ?, loginUser = ?, passwordUser = ? WHERE idUser = ?;"
            );

//           Новое значение
            statement.setString (1, userUpdate.getName ());
            statement.setString(2, userUpdate.getLogin ());
            statement.setInt (3, userUpdate.getPassword ());

            statement.setInt (4, userAuth.getId ());
            statement.executeUpdate();

            LOG.info (String.format (
                    "Изменение данных пользователя \n Старые данные[%s]\n Новые данные[%s]\n",
                    userAuth.toString (), userUpdate.toString ()));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statementClose(statement);

        }
    }
//    Проверка открытой пользовательской сессии

public static UserSpartak checkUserAuthStart(){

    PreparedStatement statement = null;
    ResultSet rs = null;

    try {

        statement = ServerMySQL.getConnection ().prepareStatement(
                "SELECT * FROM Users WHERE authUser = ?;"
        );

        statement.setBoolean(1,true);

        rs = statement.executeQuery();

        if (rs.next()){
                return new UserSpartak(
                        rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getBoolean(5));

        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        statementClose(statement);
        resultSetClose(rs);
    }

    return null;
}



}
