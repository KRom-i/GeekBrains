package Services.Accounting;

import Format.DateTime;
import Logger.LOG;
import MySQLDB.ServerMySQL;
import Services.Product;
import Services.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListServices {



    public static boolean addNewService(Service s){

        PreparedStatement statement = null;

        try {

            String query = "INSERT INTO SERVICE_GENERAL_VALUES (" +
                    " ID_SERVICE," +
                    " ID_TYPE_SERVICE," +
                    " ID_GROUP_SERVICE," +
                    " NAME_SERVICE," +
                    " COST_SERVICE," +
                    " NUMBER_VISITS_INT," +
                    " TERM_DAYS," +
                    " NUMBER_CLIENT_Traning, " +
                    " BALANCE," +
                    " DELETE_BOOLEAN" +
                    ") VALUES (" +
                    "?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?);";



            statement = ServerMySQL.getConnection ().prepareStatement(query);

            if (s.getId () != 0){
                statement.setInt(1, s.getId ());
            } else {
                statement.setInt(1, 0);
            }

            if (s.getType () != 0){
                statement.setInt(2, s.getType ());
            } else {
                statement.setInt(2, 0);
            }

            if (s.getNumberGroup () != 0){
                statement.setInt(3, s.getNumberGroup ());
            } else {
                statement.setInt(3, 0);
            }

            if (s.getName () != null){
                statement.setString(4, s.getName ());
            } else {
                statement.setString(4,null);
            }

            if (s.getCost () != 0){
                statement.setDouble (5, s.getCost ());
            } else {
                statement.setDouble (5, 0);
            }
            if (s.getNumberVisits () != 0){
                statement.setInt(6, s.getNumberVisits ());
            } else {
                statement.setInt(6, 0);
            }

            if (s.getTermDays () != 0){
                statement.setInt(7, s.getTermDays ());
            } else {
                statement.setInt(7, 0);
            }

            if (s.getNumberClient () != 0){
                statement.setInt(8, s.getNumberClient ());
            } else {
                statement.setInt(8, 0);
            }

            if (s.getBalance () != 0){
                statement.setInt(9, s.getBalance ());
            } else {
                statement.setInt(9, 0);
            }

            statement.setBoolean (10, false);

            statement.executeUpdate();

            LOG.info (String.format("Добавление новой услуги id[%s]имя[%s] баланс [%s] \n",s.getId (), s.getName (), s.getBalance () + ""));

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
        }
        return false;
    }


//    Метод возвращяет новый Id услуги так как услуги распределенны по разным таблицам
    private static int newId(){
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {

            String query = "SELECT * FROM newIdService;";
            statement = ServerMySQL.getConnection ().prepareStatement(query);

            rs = statement.executeQuery();

            int iVal = 0;
            int newID = 0;
            while (rs.next()) {
                iVal = rs.getInt (2);
                newID= iVal + 1;
                LOG.info (String.format("Новой id услуги [%s]\n", newID));
                editId(newID);
                return newID;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
        }
        return 0;
    }

    private static void editId(int i){

        PreparedStatement statement = null;
        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                "UPDATE newIdService SET ipVal = ? WHERE nameVal = ?;"
        );

//                Новый IP
        statement.setInt(1, i);

        statement.setString (2, "service");

        statement.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
        }
    }


    private static void delService(int idService){
        PreparedStatement statement = null;
        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE SERVICE_GENERAL_VALUES SET DELETE_BOOLEAN = ? WHERE ID_SERVICE = ?;"
            );

            statement.setBoolean (1, true);
            statement.setInt (2, idService);

            statement.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
        }
    }

    public static void main(String[] args) {


        ServerMySQL.getConnection ();

//        Service s = new Product ("Test DB", 300.30, 100, 1);
//        s.setId (newId ());
//        addNewService (s);

//        newId ();

        delService (4);
        delService (111);
        ServerMySQL.disconnect ();


    }

}
