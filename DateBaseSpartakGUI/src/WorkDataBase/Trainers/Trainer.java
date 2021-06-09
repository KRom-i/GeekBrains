package WorkDataBase.Trainers;

import Cash.CashBook;
import Cash.Transaction;
import GUIMain.CustomStage.ErrorStage;
import GUIMain.CustomStage.WarningStage;
import MySQLDB.ServerMySQL;
import WorkDataBase.ActionUser;
import WorkDataBase.ClientClass;
import WorkDataBase.ClientDataBase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Trainer {

    private int id;
    private ClientClass client;
    private double balance;

    public Trainer () {
    }

    public Trainer (ClientClass client, double balance) {
        this.client = client;
        this.balance = balance;
    }

    public Trainer (int id, ClientClass client, double balance) {
        this.id = id;
        this.client = client;
        this.balance = balance;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public ClientClass getClient () {
        return client;
    }

    public void setClient (ClientClass client) {
        this.client = client;
    }

    public double getBalance () {
        return balance;
    }

    public void setBalance (double balance) {
        this.balance = balance;
    }



    public void addDataBase(){

        PreparedStatement statement = null;

        try {

            String query = "INSERT INTO trainers (idClientThisTrainer, balance) VALUES (?, ?);";

            statement = ServerMySQL.getConnection ().prepareStatement(query);
            statement.setInt(1, this.client.getId());
            statement.setDouble(2, this.getBalance());
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
        }

    }

    public  Trainer getOneTrainer(int idTrainer){

        PreparedStatement statement = null;
        ResultSet rs = null;

        Trainer trainer = null;
        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM trainers Where idTrainer = ?;"
            );


            statement.setInt(1, idTrainer);
            rs = statement.executeQuery();


            if (rs.next()) {
                int id = rs.getInt(1);
                ClientClass client = ClientDataBase.getClientDateBase(rs.getInt(2));
                double balance = rs.getDouble(4);
                trainer = new Trainer(id, client, balance);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return trainer;
    }

    public  Trainer getOneTrainerIdClient(int idClinet){

        PreparedStatement statement = null;
        ResultSet rs = null;

        Trainer trainer = null;
        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM trainers Where idClientThisTrainer = ?;"
            );


            statement.setInt(1, idClinet);
            rs = statement.executeQuery();


            if (rs.next()) {
                int id = rs.getInt(1);
                ClientClass client = ClientDataBase.getClientDateBase(rs.getInt(2));
                double balance = rs.getDouble(4);
                trainer = new Trainer(id, client, balance);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return trainer;
    }

    public  void setBalanceDateBase( double value){

        PreparedStatement statement = null;


        value = getBalance() - value;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE trainers SET balance = ? Where idTrainer = ?;"
            );


            statement.setDouble(1, value);

            statement.setInt(2, this.getId());

            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }


    }


    public HBox getHBoxInfo(){
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER_LEFT);

        Label labelName = new Label("ФИО:");
        labelName.setMinWidth(50);

        TextField textFieldName = new TextField(client.toStringIteam());
        textFieldName.setEditable(false);
        textFieldName.setMinWidth(350);

        Label labelBalance = new Label("Баланс:");
        labelBalance.setMinWidth(70);

        TextField textFieldBalance = new TextField(String.valueOf(getBalance()));
        textFieldBalance.setEditable(false);
        textFieldBalance.setMinWidth(70);

        hBox.getChildren().addAll(labelName, textFieldName, labelBalance, textFieldBalance);

        if (getBalance() > 0){
            Label labelSumDownBalance = new Label("Сумма списания");
            TextField textFieldSumDownBalance = new TextField();
            textFieldSumDownBalance.setPromptText("Сумма");
            textFieldSumDownBalance.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle (MouseEvent event) {

                    double[] costUp = {5,10,50,100,500,1000,5000};

                    Platform.runLater (()->{
                        ContextMenu contextMenu = new ContextMenu();
                        contextMenu.getItems ().clear ();
                        textFieldSumDownBalance.setContextMenu (contextMenu);
                        contextMenu.setStyle("-fx-max-width: 300;");
                        contextMenu.setStyle("-fx-max-height: 300;");

                        MenuItem[] items =  new MenuItem[costUp.length];

                        for(int j = 0; j < costUp.length; j++) {

                            items[j] = new MenuItem (" + " + costUp[j]);
                            double D = costUp[j];
                            items[j].setOnAction (new EventHandler<ActionEvent> () {
                                @Override
                                public void handle (ActionEvent event){

                                    if (textFieldSumDownBalance.getText().length() > 0){
                                        try {
                                            double cost = Double.valueOf(textFieldSumDownBalance.getText());
                                            cost = cost + D;
                                            textFieldSumDownBalance.setText(cost + "");
                                        } catch (NumberFormatException numberFormatException){
                                            new ErrorStage("Ошибка формата суммы");
                                            new Thread(()->{
                                                textFieldSumDownBalance.getStyleClass().add("label-warning");
                                                try {
                                                    Thread.sleep(1500);
                                                } catch (InterruptedException e) {

                                                } finally {
                                                    textFieldSumDownBalance.getStyleClass().add("label-null");
                                                }
                                            }).start();
                                        }

                                    } else {
                                        double cost = D;
                                        textFieldSumDownBalance.setText(cost + "");
                                    }

                                }
                            });
                            contextMenu.getItems ().add (items[j]);
                        }


                        double x = MouseInfo.getPointerInfo().getLocation().getX();
                        double y = MouseInfo.getPointerInfo().getLocation().getY();

                        contextMenu.show (textFieldName, x,y);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
                    });

                }
            });
            Button buttonSumDownBalance = new Button("Списать");
            buttonSumDownBalance.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle (ActionEvent event) {
                    try {
                        double valueDown = Double.valueOf(textFieldSumDownBalance.getText());
                        if (valueDown > 0) {
                            if (valueDown <= getBalance()) {

                                if (valueDown < new CashBook().getBalanceCash()) {

                                    setBalanceDateBase(valueDown);
                                    Transaction t = new Transaction(4, getClient(), ActionUser.getUser(), 1, valueDown);
                                    CashBook.addTransactionToCashBook(t);
                                    TrainerList.updateListView();

                                } else {
                                    new ErrorStage("Недостаточно средств в кассе");
                                }

                            } else {
                                new ErrorStage("Недостаточно средств на балансе тренера");
                            }
                        } else {
                            new WarningStage("Необходимо указать сумму списания");
                        }
                    } catch (NumberFormatException numberFormatException){
                        new ErrorStage("Ошибка формата суммы");
                        new Thread(()->{
                            textFieldSumDownBalance.getStyleClass().add("label-warning");
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {

                            } finally {
                                textFieldSumDownBalance.getStyleClass().add("label-null");
                            }
                        }).start();
                    }
                }
            });
            hBox.getChildren().addAll(labelSumDownBalance, textFieldSumDownBalance, buttonSumDownBalance);
        }


        return hBox;
    }


    @Override
    public String toString () {
        return "Trainer{" +
               "id=" + id +
               ", client=" + client +
               ", balance=" + balance +
               '}';
    }
}
