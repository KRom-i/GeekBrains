package Services;

import WorkDataBase.ConnectDateBase;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClubCard extends Service{

    private int Type;
    private int numberGroup;
    private int id;
    private String name;
    private double cost;
    private int balance;
    private int termDays;
    private HBox hBoxInfo;

    public ClubCard() {

    }

    public ClubCard(String name,double cost,int balance,int termDays,int numberGroup) {
        this.name = name;
        this.cost = cost;
        this.balance = balance;
        this.termDays = termDays;
        this.numberGroup = numberGroup;
    }

    public int getNumberGroup() {
        return numberGroup;
    }

    public void setNumberGroup(int numberGroup) {
        this.numberGroup = numberGroup;
    }

    @Override
    public String toString() {
        return "ClubCard{" +
                "Type=" + Type +
                ", numberGroup=" + numberGroup +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", balance=" + balance +
                ", termDays=" + termDays +
                ", hBoxInfo=" + hBoxInfo +
                '}';
    }

    public int getTermDays() {
        return termDays;
    }

    public void setTermDays(int numberVisits) {
        this.termDays = numberVisits;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }



    public HBox getHBoxInfo() {
        if (this.hBoxInfo == null){
            hBoxInfo = new HBox();
        }
        Label labelName = new Label(getName());
        labelName.setMinWidth(200);
        Label labelCost = new Label("Цена (руб):  " + getCost());
        labelCost.setMinWidth(150);
        Label labelBalance = new Label("Остаток:  " + getBalance());
        labelBalance.setMinWidth(150);
        Label labelTermDays= new Label();
        labelTermDays.setMinWidth(150);
        if (getTermDays() >= 31){
            double doubleTermMonth = getTermDays() / 30.33333;
            int intDoubleTermMonth = (int) doubleTermMonth;
            labelTermDays.setText("Срок (мес):  " + intDoubleTermMonth);
        } else {
            labelTermDays.setText("Срок (дн):  " + getTermDays());
        }

        hBoxInfo.getChildren().add(labelName);
        hBoxInfo.getChildren().add(labelCost);
        hBoxInfo.getChildren().add(labelTermDays);
        hBoxInfo.getChildren().add(labelBalance);
        hBoxInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (getBalance() <= 0){
                    labelBalance.setStyle("-fx-background-color: red");
                }
            }
        });
        if (getBalance() <= 0){
            labelBalance.setStyle("-fx-text-fill: red");
        }
        return hBoxInfo;
    }


    public boolean addToDataBase(){

        ConnectDateBase.connect ();

        PreparedStatement statement = null;

        try {

            String query = "INSERT INTO Service_Price (" +
                    "ID_TYPE, ID_GROUP, NAME, COST, DELETE_CHECK, BALANCE, NUMBER_VISITS," +
                    "TERM_DAYS, NUMBER_CLIENTS, Type_tren" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

//            String query = "INSERT INTO Service_Price ("+
//                    "ID_TYPE, ID_GROUP, NAME, COST, DELETE_CHECK, BALANCE" +
//                    ") VALUES (?, ?, ?, ?, ?, ?);";



            statement = ConnectDateBase.connection.prepareStatement (query);

            statement.setInt(1, this.getType ());
            statement.setInt(2, this.getNumberGroup ());
            statement.setString(3, this.getName ());
            statement.setDouble (4, this.getCost ());
            statement.setBoolean (5,false);
            statement.setInt(6, this.getBalance ());

//            statement.setInt (7, 0);
            statement.setInt(8, this.getTermDays ());
//            statement.setInt(9, 0);
//            statement.setString (10, " ");
//


            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDateBase.statementClose(statement);
        }
        return false;
    }

}
