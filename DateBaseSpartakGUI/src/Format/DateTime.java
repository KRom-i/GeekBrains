package Format;

import GUIMain.CustomStage.SystemErrorStage;
import MySQLDB.ServerMySQL;
import WorkDataBase.UserSpartak;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

    public DateTime (){

    }

//    Для теста возвращает из заданного значения
    public String currentDate(){
//        return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        return getDate ();
    }


//    Добавляет влемя заданное пользователем
    public String currentTime(){
//        return new SimpleDateFormat("HH:mm:ss").format(new Date());
        return upTime();
    }

    //    Текущее время
    public String currentTime2(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

//   Добавляет время заданное пользователем в минутах

    private String upTime(){
        long time = Long.valueOf (getTime ()) * 60000;
        System.out.println (time);
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("HH:mm:ss");
            Date docDate= format.parse(new DateTime ().currentTime2());
            docDate.setTime (docDate.getTime () + time);
            return new SimpleDateFormat("HH:mm:ss").format(docDate);

        } catch (ParseException e) {
            e.printStackTrace ();
        new SystemErrorStage (e);
        }
        return null;
    }

    public Date getConfigDate(){
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("dd.MM.yyyy");
            return format.parse(new DateTime ().getDate ());
        } catch (ParseException e) {
            e.printStackTrace ();
        new SystemErrorStage (e);
        }
        return null;
    }




    public String dateFormatMMyyyy(){
        //        return new SimpleDateFormat("MM.yyyy").format(new Date());
        return getDate ().substring (3,10);
    }



    public void setDateConfig(String date){
        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE Config SET valConfig = ? WHERE idConfig = ?;"
            );

//           Новое значение
            statement.setString (1, date);

            statement.setInt (2,1);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }
    }


    private String getDate(){

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM Config WHERE idConfig = ?;"
            );

            statement.setInt (1,1);

            rs = statement.executeQuery();

            if (rs.next()){
                return rs.getString (3);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return null;
    }

    public void setTimeConfig (String text) {

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE Config SET valConfig = ? WHERE idConfig = ?;"
            );

//           Новое значение
            statement.setString (1, text);

            statement.setInt (2,2);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }

    }


    public String getTime () {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM Config WHERE idConfig = ?;"
            );

            statement.setInt (1,2);

            rs = statement.executeQuery();

            if (rs.next()){
                return rs.getString (3);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return null;
    }
}
