package WorkDataBase;

import MySQLDB.ServerMySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


    //    Авторизация пользователя через БД.
    public static UserSpartak newUserAuth(String login, int password){

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            statement = ServerMySQL.connection.prepareStatement(
                    "SELECT * FROM users WHERE login = ?;"
            );

            statement.setString(1,login);

            rs = statement.executeQuery();

            if (rs.next()){

                int dbHash = rs.getInt("password");

                if (password == dbHash){

                    return new UserSpartak(
                            rs.getInt("id"), rs.getString("name"),
                            rs.getString("login"), dbHash,
                            rs.getBoolean("authstart")
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

   public static void editUserDateBase(String login, boolean autostart){

       PreparedStatement statement = null;

       try {

           statement = ServerMySQL.connection.prepareStatement(
                   "UPDATE users SET authstart = ? WHERE login = ?;"
           );

//           Новое значение
           statement.setBoolean(1, autostart);

           statement.setString(2,login);
           statement.executeUpdate();

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

        statement = ServerMySQL.connection.prepareStatement(
                "SELECT * FROM users WHERE authstart = ?;"
        );

        statement.setBoolean(1,true);

        rs = statement.executeQuery();

        if (rs.next()){
                return new UserSpartak(
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("login"), rs.getInt("password"),
                        rs.getBoolean("authstart"));

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
