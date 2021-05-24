package Services.Accounting;

import Cash.Transaction;
import Format.DateTime;
import Logger.LOG;
import MySQLDB.ServerMySQL;
import Services.Product;
import Services.Service;
import WorkDataBase.UserSpartak;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValueAccountingBook {

    String thisNameRus = "Книга учета ценностей";
    String thisNameEng = "ValueAccountingBook";

    String[][] fieldsName = {{
            "Номер операции",
            "Дата операции",
            "Время операции",
            "Идентификатор пользователя",
            "ФИО пользователя",
            "Идентификатор МТЦ",
            "Наименование МТЦ",
            "Приход (шт.)",
            "Расход (шт.)",
            "Остаток на балансе"
    }, {
            "operationNumber",
            "operationDate",
            "operationTime",
            "idUser",
            "nameUser",
            "idService",
            "nameService",
            "Coming",
            "Consumption",
            "Balanse"
    }};



    public static boolean addOperationToDataBase(UserSpartak u, Service s, int value){
        PreparedStatement statement = null;

        try {

            String query = "INSERT INTO ValueAccountingBook (" +
                    " operationNumber," +
                    " operationDate, operationTime," +
                    " idUser, nameUser," +
                    " idService, nameService," +
                    " Coming, Consumption," +
                    " Balance" +
                    ") VALUES (" +
                    "?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?);";


            statement = ServerMySQL.getConnection ().prepareStatement(query);

            statement.setInt(1, getEndNumber () + 1);

            DateTime dateTime = new DateTime ();
            statement.setString(2, dateTime.currentDate ());
            statement.setString(3, dateTime.currentTime ());

            statement.setInt(4, u.getId ());
            statement.setString(5, u.getName ());

            statement.setInt(6, s.getId ());
            statement.setString(7, s.getName ());

            int coming = 0;
            int consumption = 0;

            if (value > 0){
                coming = value;
            } else if (value < 0){
                consumption = value;
            }


            statement.setInt(8, coming);
            statement.setInt(9, consumption);

            int newBalance = (s.getBalance () + coming) - consumption;

            statement.setInt(10, newBalance);

            statement.executeUpdate();

            LOG.info (String.format("Изменение баланса ТМЦ \n id[%s]имя[%s] \n на [%s] \n",s.getId (), s.getName (), value + ""));

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
        }
        return false;
    }


    private static void addEndStrNull(){
        PreparedStatement statement = null;

        try {

            String query = "INSERT INTO ValueAccountingBook (" +
                    " operationNumber," +
                    " operationDate, operationTime," +
                    " idUser, nameUser," +
                    " idService, nameService," +
                    " Coming, Consumption," +
                    " Balance" +
                    ") VALUES (" +
                    "?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?);";


            statement = ServerMySQL.getConnection ().prepareStatement(query);

            statement.setInt(1, 0);

            DateTime dateTime = new DateTime ();
            statement.setString(2, dateTime.currentDate ());
            statement.setString(3, dateTime.currentTime ());

            statement.setInt(4, 0);
            statement.setString(5, null);

            statement.setInt(6, 0);
            statement.setString(7, null);

            statement.setInt(8, 0);
            statement.setInt(9, 0);

            statement.setInt(10, 0);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
        }
    }

    private static int getEndNumber(){

        PreparedStatement statement = null;
        ResultSet rs = null;
        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM ValueAccountingBook ORDER BY operationNumber DESC LIMIT 0, 1;"
            );

            rs = statement.executeQuery();

            while (rs.next()) {
                int val = rs.getInt (1);
                LOG.info (String.format("Номер последнй операции учета ТМЦ: [%s] \n",val ));
                return val;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }
        return 0;
    }


    public static void main(String[] args) {

        ServerMySQL.getConnection ();

        UserSpartak user = new UserSpartak (1, "Roman", "log", 12345, false);
        Service service = new Product ("Product Name Test", 200, 0, 1);


        ValueAccountingBook.addOperationToDataBase (user, service, 10);
        ValueAccountingBook.addOperationToDataBase (user, service, 0);
        ValueAccountingBook.addOperationToDataBase (user, service, -5);

//        addEndStrNull ();
        ServerMySQL.disconnect ();
    }
}
