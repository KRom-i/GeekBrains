package Services;

import Cash.CashBook;
import Cash.Transaction;
import WorkDataBase.ClientClass;
import WorkDataBase.ConnectDateBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Product extends Service{

    private int Type;
    private int numberGroup;
    private int id;
    private String name;
    private double cost;
    private int balance;
    private HBox hBoxInfo;
    private HBox hBoxEdit;
    private Service service;

    public Product(){}

    public Product(String name,double cost, int balance, int numberGroup) {
        this.name = name;
        this.cost = cost;
        this.balance = balance;
        this.numberGroup = numberGroup;
        this.service = this;
    }

    public int getNumberGroup() {
        return numberGroup;
    }

    public void setNumberGroup(int numberGroup) {
        this.numberGroup = numberGroup;
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
        hBoxInfo.getChildren().add(labelName);
        hBoxInfo.getChildren().add(labelCost);
        hBoxInfo.getChildren().add(labelBalance);
        hBoxInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (getBalance() > 0 && mouseEvent.getClickCount() == 2){

                    CashBook cashBook = new CashBook();

                    Transaction transaction_1 = new Transaction(1, service, 11, 11, 1);
                    transaction_1.setSumCashBalanceEnd(1000);
                    transaction_1.setSumNonCashBalanceEnd(10000);
                    transaction_1.setSumAllBalanceEnd(100000);
                    System.out.println(transaction_1);
                    cashBook.writeFileTran(transaction_1);
                    Transaction transaction_2 = new Transaction(1, service, 22, 22, 2);
                    transaction_2.balanceCalculation(transaction_1);
                    System.out.println(transaction_2);
                    cashBook.writeFileTran(transaction_2);
                    Transaction transaction_3 = new Transaction(1, service, 22, 22, 1);
                    transaction_3.balanceCalculation(transaction_2);
                    System.out.println(transaction_3);
                    cashBook.writeFileTran(transaction_3);
                    Transaction transaction_4 = new Transaction(1, service, 22, 22, 2);
                    transaction_4.balanceCalculation(transaction_3);
                    System.out.println(transaction_4);
                    cashBook.writeFileTran(transaction_4);
                }
            }
        });
        if (getBalance() <= 0){
            labelBalance.setStyle("-fx-text-fill: red");
        }
        return hBoxInfo;
    }


    public HBox getHBoxEdit() {
        if (this.hBoxEdit == null){
            hBoxEdit = new HBox();
        }
        hBoxEdit.setSpacing(5);
        hBoxEdit.setAlignment(Pos.CENTER_LEFT);

        Label labelNumberGroup = new Label("Гр:  ");

        TextField textNumberGroup = new TextField(String.valueOf(getNumberGroup()));
        textNumberGroup.setMaxWidth(35);
        textNumberGroup.setEditable(false);

        Label labelName = new Label("Наименование:  ");

        TextField textFieldName = new TextField(getName());
        textFieldName.setMaxWidth(200);
        textFieldName.setEditable(false);

        Label labelCost = new Label("Цена (руб):  " );


        TextField textFieldCost = new TextField(getCost() + "");
        textFieldCost.setMaxWidth(100);
        textFieldCost.setEditable(false);

        Label labelBalance = new Label("Остаток:  ");


        TextField textFieldBalance = new TextField("" + getBalance());
        textFieldBalance.setMaxWidth(50);
        textFieldBalance.setEditable(false);

        Label labelAddBalance = new Label("Добавить:  ");
        labelAddBalance.setVisible(false);
        labelAddBalance.setManaged(false);

        Spinner<Double> spinnerCost = new Spinner<>();
        spinnerCost.setMaxWidth(100);
        spinnerCost.setVisible(false);
        spinnerCost.setManaged(false);
        spinnerCost.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 999999, 0));

        Button buttonEdit = new Button("Редактировать");
        buttonEdit.setMinWidth(100);

        Button buttonSave = new Button("Сохранить");
        buttonSave.setMinWidth(100);
        buttonSave.setVisible(false);
        buttonSave.setManaged(false);

        Button buttonDel = new Button("Удалить");
        buttonDel.setMinWidth(100);
        buttonDel.setVisible(false);
        buttonDel.setManaged(false);

        hBoxEdit.getChildren().add(labelNumberGroup);
        hBoxEdit.getChildren().add(textNumberGroup);
        hBoxEdit.getChildren().add(labelName);
        hBoxEdit.getChildren().add(textFieldName);
        hBoxEdit.getChildren().add(labelCost);
        hBoxEdit.getChildren().add(textFieldCost);
        hBoxEdit.getChildren().add(labelBalance);
        hBoxEdit.getChildren().add(textFieldBalance);
        hBoxEdit.getChildren().add(labelAddBalance);
        hBoxEdit.getChildren().add(spinnerCost);
        hBoxEdit.setOpacity(0.7);

        HBox hBoxMain = new HBox(hBoxEdit);
        hBoxMain.setSpacing(5);
        hBoxMain.getChildren().add(buttonEdit);
        hBoxMain.getChildren().add(buttonSave);
        hBoxMain.getChildren().add(buttonDel);


        buttonEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                hBoxEdit.setOpacity(1);
                textNumberGroup.setEditable(true);
                textFieldCost.setEditable(true);
                textFieldName.setEditable(true);
                labelAddBalance.setVisible(true);
                labelAddBalance.setManaged(true);
                spinnerCost.setEditable(true);
                spinnerCost.setVisible(true);
                spinnerCost.setManaged(true);
                buttonEdit.setVisible(false);
                buttonEdit.setManaged(false);
                buttonSave.setVisible(true);
                buttonSave.setManaged(true);
                buttonDel.setVisible(true);
                buttonDel.setManaged(true);
            }
        });

        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                hBoxEdit.setOpacity(0.7);
                textNumberGroup.setEditable(false);
                textFieldCost.setEditable(false);
                textFieldName.setEditable(false);
                labelAddBalance.setVisible(false);
                labelAddBalance.setManaged(false);
                spinnerCost.setEditable(false);
                spinnerCost.setVisible(false);
                spinnerCost.setManaged(false);
                buttonEdit.setVisible(true);
                buttonEdit.setManaged(true);
                buttonSave.setVisible(false);
                buttonSave.setManaged(false);
                buttonDel.setVisible(false);
                buttonDel.setManaged(false);
            }
        });

        if (getBalance() <= 0){
            textFieldBalance.setStyle("-fx-background-color: red");
        }
        return hBoxMain;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Type=" + Type +
                ", numberGroup=" + numberGroup +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", balance=" + balance +
                ", hBoxInfo=" + hBoxInfo +
                ", hBoxEdit=" + hBoxEdit +
                ", service=" + service +
                '}';
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
//            statement.setInt(8, 0);
//            statement.setInt(9, 0);
//            statement.setString (10, " ");

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
