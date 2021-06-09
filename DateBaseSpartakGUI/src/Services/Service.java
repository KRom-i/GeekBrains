package Services;

import MySQLDB.ServerMySQL;
import Services.ImroptExcel.ServiceImport;
import javafx.scene.layout.HBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Service {

    private int Type;
    private int numberGroup;
    private int id;
    private String name;
    private double cost;
    private int numberVisits;
    private String typeTren;
    private int numberClient;
    private Service  service;
    private int termDays;
    private int balance;
    private boolean delete;
    private HBox hBoxInfo;
    private double sumTren;

    private boolean timeControl;
    private double addSumTimeControl;

    private int idTre;

    public int getIdTre () {
        return idTre;
    }

    public void setIdTre (int idTre) {
        this.idTre = idTre;
    }

    public boolean isTimeControl () {
        return timeControl;
    }

    public void setTimeControl (boolean timeControl) {
        this.timeControl = timeControl;
    }

    public double getAddSumTimeControl () {
        return addSumTimeControl;
    }

    public void setAddSumTimeControl (double addSumTimeControl) {
        this.addSumTimeControl = addSumTimeControl;
    }

    public double getSumTren () {
        return sumTren;
    }

    public void setSumTren (double sumTren) {
        this.sumTren = sumTren;
    }


    public HBox getHBoxInfo () {
        return hBoxInfo;
    }

    public void setHBoxInfo (HBox hBoxInfo) {
        this.hBoxInfo = hBoxInfo;
    }


    public boolean isDelete () {
        return delete;
    }

    public void setDelete (boolean delete) {
        this.delete = delete;
    }

    public int getType () {
        return Type;
    }

    public void setType (int type) {
        Type = type;
    }

    public int getNumberGroup () {
        return numberGroup;
    }

    public void setNumberGroup (int numberGroup) {
        this.numberGroup = numberGroup;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public double getCost () {
        return cost;
    }

    public void setCost (double cost) {
        this.cost = cost;
    }

    public int getNumberVisits () {
        return numberVisits;
    }

    public void setNumberVisits (int numberVisits) {
        this.numberVisits = numberVisits;
    }

    public String getTypeTren () {
        return typeTren;
    }

    public void setTypeTren (String typeTren) {
        this.typeTren = typeTren;
    }

    public int getNumberClient () {
        return numberClient;
    }

    public void setNumberClient (int numberClient) {
        this.numberClient = numberClient;
    }

    public Service getService () {
        return service;
    }

    public void setService (Service service) {
        this.service = service;
    }

    public int getTermDays () {
        return termDays;
    }

    public void setTermDays (int termDays) {
        this.termDays = termDays;
    }

    public int getBalance () {
        return balance;
    }

    public void setBalance (int balance) {
        this.balance = balance;
    }


    public Service getServiceDataBase(int idService){


        PreparedStatement statement = null;
        ResultSet rs = null;

        ServerMySQL.getConnection();

        Service service = new Service();

        try {

            statement = ServerMySQL.getConnection().prepareStatement(
                    "select * from servicePrice Where ID_SERVICE = ?;"
            );

            statement.setInt (1,idService);

            rs = statement.executeQuery();


            while (rs.next()){

                int id = rs.getInt (1);
                int type = rs.getInt (2);
                int group = rs.getInt (3);
                String name = rs.getString (4);
                double cost = rs.getDouble (5);
                int balance = rs.getInt (7);

//                int visit = rs.getInt (8);
//                int days = rs.getInt (9);
//                int clients = rs.getInt (10);
//                String typeTre =  rs.getString (11);

                service.setId(id);
                service.setType(type);
                service.setNumberGroup(group);
                service.setName(name);
                service.setCost(cost);
                service.setBalance(balance);
//                setNumberVisits(visit);
//                setTermDays(days);
//                setNumberClient(clients);
//

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return service;

    }

    public void serviceDelete (Service s){

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement("UPDATE servicePrice SET DELETE_CHECK = ? WHERE ID_SERVICE = ?;");

//           Новое значение
            statement.setBoolean (1, true);

            statement.setInt (2, s.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }

        ServiceImport.readDataBase(false);
        ServiceImport.updateVbox(s.getType() - 1, 0, 0);

    }

    public void updateServiceBalance (Service s, int value){

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement("UPDATE servicePrice SET BALANCE = ? WHERE ID_SERVICE = ?;");

//           Новое значение
            int newBalance =  s.getBalance() + value;
            if (newBalance <= 0){
                newBalance = 0;
            }
            statement.setInt (1, newBalance);

            statement.setInt (2, s.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }

        ServiceImport.readDataBase(false);
        ServiceImport.updateVbox(s.getType() - 1, s.getId(),2);

    }

    public void updateNumberGroup(Service s){

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement("UPDATE servicePrice SET ID_GROUP = ? WHERE ID_SERVICE = ?;");

//           Новое значение
            statement.setInt (1, s.getNumberGroup());

            statement.setInt (2, s.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }

        ServiceImport.readDataBase(false);
        ServiceImport.updateVbox(s.getType() - 1, s.getId(), 2);

    }

    public void updateService(Service c){

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE servicePrice SET" +
                    " ID_GROUP = ?, NAME = ?, DELETE_CHECK = ?, BALANCE = ?, COST = ?," +
                    "NUMBER_VISITS = ?, NUMBER_CLIENTS = ?, Type_tren = ?, sumTren = ?, TERM_DAYS = ?, timeControl = ?, addSumTimeControl = ? WHERE ID_SERVICE = ?;"
            );

//           Новое значение
            statement.setInt (1, c.getNumberGroup());
            statement.setString (2, c.getName());
            statement.setBoolean(3, false);
            statement.setInt (4, c.getBalance());
            statement.setDouble (5, c.getCost());
            statement.setInt (6, c.getNumberVisits());
            statement.setInt (7, c.getNumberClient());
            statement.setString (8, c.getTypeTren());
            statement.setDouble (9, c.getSumTren());
            statement.setInt (10, c.getTermDays());
            statement.setBoolean (11, c.isTimeControl());
            statement.setDouble (12, c.getAddSumTimeControl());


            statement.setInt (13, c.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }


    }


    public int checkBalance(int idService) {

        PreparedStatement statement = null;
        ResultSet rs = null;

        int balance = 999_999;

        try {

            statement = ServerMySQL.getConnection().prepareStatement(
                    "select BALANCE from servicePrice Where ID_SERVICE = ?;"
            );

            statement.setInt(1, idService);

            rs = statement.executeQuery();


            if (rs.next()) {
                balance = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }
        return balance;

    }

    public void updateBalance(int idService, int newBalance) {

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE servicePrice SET BALANCE = ? WHERE ID_SERVICE = ?;"
            );

//           Новое значение
            statement.setInt (1, newBalance);

            statement.setInt (2, idService);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }


    }
}
