package Format;

import GUIMain.CustomStage.DateControlService;
import GUIMain.CustomStage.SystemErrorStage;
import Logger.LOG;
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

//    На день вперед
    public void upDay(){
        long time = 86400000;
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("dd.MM.yyyy");
            Date docDate= format.parse(new DateTime ().currentDate ());
            docDate.setTime (docDate.getTime () + time);
            String upDay = new SimpleDateFormat("dd.MM.yyyy").format(docDate);
            LOG.info (String.format ( "Новая дата [%s]", upDay));
            setDateConfig (upDay);
        } catch (ParseException e) {
            e.printStackTrace ();
        }
    }

    private String upTime(){
        long time = Long.valueOf (getTime ()) * 60000;
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("HH:mm:ss");
            Date docDate = format.parse(new DateTime ().currentTime2());
            docDate.setTime (docDate.getTime () + time);
            return new SimpleDateFormat("HH:mm:ss").format(docDate);
        } catch (ParseException e) {
            e.printStackTrace ();
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
        }
        return null;
    }


    public int getDaysDatePay(String date){

        int days = 0;

        try {

            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("dd.MM.yyyy");

            Date docDate= format.parse(new DateTime ().currentDate ());

            Date docDate2 = format.parse(date);

            long l = (docDate.getTime () - docDate2.getTime ()) / 86400000;
            days = (int) l;

        } catch (ParseException e) {
            e.printStackTrace ();
        }

        return days;
    }

    public String getDateLast(String datePay, int days){

        long time = (long) days * 86400000;

        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("dd.MM.yyyy");
            Date docDate= format.parse(datePay);
            docDate.setTime (docDate.getTime () + time);

            return new SimpleDateFormat("dd.MM.yyyy").format(docDate);

        } catch (ParseException e) {
            e.printStackTrace ();
        }

        return "";
    }

    public static void main (String[] args) {

        System.out.println("Текущая дата " + new DateTime().currentDate());

        String datePay = "09.05.2021";
        int days = 31;

        System.out.println("Дата покупки " + datePay + "  срок в днях " + days);

        int getIntDaysDatePay = new DateTime().getDaysDatePay(datePay);
        System.out.println("Прошло дней c момента покупки: " + getIntDaysDatePay);

        String getStringDateLast = new DateTime().getDateLast(datePay ,days);
        System.out.println("Дествителен до : " + getStringDateLast);

        if (days <= getIntDaysDatePay){
            System.out.println("Осталось дней : " + (days - getIntDaysDatePay));
        } else {
            System.out.println("Срок действия истек");
        }

    }

    public String dateFormatMMyyyy(){
        //        return new SimpleDateFormat("MM.yyyy").format(new Date());
        return getDate ().substring (3,10);
    }

    public String dateFormatYyyy(){
        //        return new SimpleDateFormat("MM.yyyy").format(new Date());
        return getDate ().substring (6,10);
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

    public boolean checkTime(){

        try {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("HH:mm");
        Date timeControl = format.parse(DateControlService.getTimeControl());
        LOG.info(String.format("Ограничение по времени [%s]  long value [%s]", DateControlService.getTimeControl(), timeControl.getTime()));

        SimpleDateFormat format1 = new SimpleDateFormat();
        format1.applyPattern("HH:mm:ss");
        Date time = format1.parse(currentTime());
        LOG.info(String.format("Текущее время [%s]  long value [%s]",currentTime(), time.getTime()));

        if (time.getTime() < timeControl.getTime()){
            return true;
        } else {

            SimpleDateFormat format3 = new SimpleDateFormat();
            format3.applyPattern("dd.MM.yyyy");
            Date date = format3.parse(new DateTime ().currentDate ());

            String numberDay = new SimpleDateFormat("u").format(date);
            int day = Integer.valueOf(numberDay);

            LOG.info(String.format("Текущая дата [%s]  номер дня недели [%s]", new DateTime ().currentDate (), day));

            if (day < 6){
                return  DateControlService.getDayControl(new DateTime ().currentDate (), "Выходной день");
            } else {
                if (!DateControlService.getDayControl(new DateTime ().currentDate (), "Рабочий день")){
                    return  true;
                }
            }
        }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;
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
