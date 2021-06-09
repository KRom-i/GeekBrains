package Services;

import Cash.CashBook;
import Cash.Transaction;
import GUIMain.CustomStage.DialogSelectPaymentMethod;
import GUIMain.CustomStage.ErrorStage;
import GUIMain.CustomStage.WarningStage;
import MySQLDB.ServerMySQL;
import Services.ActionServices.ActionService;
import Services.StageService.StageEditService;
import WorkDataBase.ActionClient;
import WorkDataBase.ActionUser;
import WorkDataBase.Trainers.Trainer;
import WorkDataBase.UserSpartak;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClubCard extends Service {

    private int Type;
    private int numberGroup;
    private int id;
    private String name;
    private double cost;
    private int balance;

    private HBox hBoxInfo;
    private HBox hBoxEdit;
    private Service  service;

    private boolean timeControl;
    private double addSumTimeControl;

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

    public ClubCard (int type, int numberGroup, String name, double cost, int balance, int termDays, boolean timeControl, double addSumTimeControl) {
        this.Type = type;
        this.numberGroup = numberGroup;
        this.name = name;
        this.cost = cost;
        this.service = this;
        this.termDays = termDays;
        this.balance = balance;
        this.timeControl = timeControl;
        this.addSumTimeControl = addSumTimeControl;
    }

    public ClubCard (int id, int type, int numberGroup, String name, double cost, int balance, int termDays, boolean timeControl, double addSumTimeControl) {
        this.Type = type;
        this.numberGroup = numberGroup;
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.service = this;
        this.termDays = termDays;
        this.balance = balance;
        this.timeControl = timeControl;
        this.addSumTimeControl = addSumTimeControl;
    }


    public ClubCard(){}

    public ClubCard(String name,double cost, int balance, int termDays, int numberGroup) {
        this.name = name;
        this.cost = cost;
        this.balance = balance;
        this.termDays = termDays;
        this.numberGroup = numberGroup;
        this.service = this;
    }



    public int getNumberGroup() {
        return numberGroup;
    }

    public void setNumberGroup(int numberGroup) {
        this.numberGroup = numberGroup;
    }

    private int termDays;
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
        this.hBoxInfo = new HBox();
        hBoxInfo.getStyleClass().add("my-dox-class-false");
//        Button buttonUp = new Button ("Оформить");
//        buttonUp.setOnAction (new EventHandler<ActionEvent> () {
//            @Override
//            public void handle(ActionEvent event) {
//                Transaction t1 = new Transaction (1, service, ActionClient.getClient (),
//                        new UserSpartak (1, "Roman", "log", 1234, true), 1);
//                CashBook.addTransactionToCashBook (t1);
//            }
//        });
//        hBoxInfo.getChildren().add(buttonUp);

        Label labelGroup = new Label("Гр. " + getNumberGroup());
        labelGroup.setMinWidth(70);
        hBoxInfo.getChildren().add(labelGroup);

        Label labelName = new Label(" " + getName());
        labelName.setMinWidth(300);
        hBoxInfo.getChildren().add(labelName);

        Label labelCost = new Label("Цена (руб):  " + getCost());
        labelCost.setMinWidth(200);
        hBoxInfo.getChildren().add(labelCost);


        if (getTermDays() > 0) {
            Label labelTermDays = new Label();
            labelTermDays.setMinWidth(150);
            if (getTermDays() > 30) {
                double doubleTermMonth = getTermDays() / 30.33333;
                int intDoubleTermMonth = (int) doubleTermMonth;
                labelTermDays.setText("Срок (мес):  " + intDoubleTermMonth);
            } else {
                labelTermDays.setText("Срок (дн):  " + getTermDays());
            }
            hBoxInfo.getChildren().add(labelTermDays);
        }



        Label labelBalance = new Label("Остаток:  " + getBalance());
        if (getBalance() != 999_999) {
            labelBalance.setMinWidth(150);
            hBoxInfo.getChildren().add(labelBalance);
        }



        if (getNumberClient() > 1) {
            Label labelNumClients = new Label();
            labelNumClients.setMinWidth(150);
            labelNumClients.setText("Clients:  " + getNumberClient());
            hBoxInfo.getChildren().add(labelNumClients);
        }




        hBoxInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    if (ActionClient.getClient().getId() > 0) {
                        if (getBalance() > 0) {

                            new DialogSelectPaymentMethod(hBoxInfo, service, ActionUser.getUser(), ActionClient.getClient());
                        } else {
                            new ErrorStage(String.format("Отстаток %s.", getBalance() + ""));
                        }
                    } else {
                        new ErrorStage("Необходимо выбрать клиента");
                    }
                }
            }
        });
        if (getBalance() <= 0){
            labelBalance.getStyleClass().add("label-warning");
        }
        return hBoxInfo;
    }

    public HBox getHBoxEdit() {
        this.hBoxEdit = new HBox();
        hBoxEdit.getStyleClass().add("my-dox-class-false");
//        Button buttonUp = new Button ("Оформить");
//        buttonUp.setOnAction (new EventHandler<ActionEvent> () {
//            @Override
//            public void handle(ActionEvent event) {
//                Transaction t1 = new Transaction (1, service, ActionClient.getClient (),
//                        new UserSpartak (1, "Roman", "log", 1234, true), 1);
//                CashBook.addTransactionToCashBook (t1);
//            }
//        });
//        hBoxEdit.getChildren().add(buttonUp);

        Label labelGroup = new Label("Гр. " + getNumberGroup());
        labelGroup.setMinWidth(40);
        Spinner<Integer> spinnerNumberGroup = new Spinner<>();
        spinnerNumberGroup.setMaxWidth(30);
        spinnerNumberGroup.setValueFactory (new SpinnerValueFactory.IntegerSpinnerValueFactory (-100,-1,getNumberGroup() * -1));
        spinnerNumberGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                service.setNumberGroup(spinnerNumberGroup.getValue() * -1);
                new Service().updateNumberGroup(service);
            }
        });
        hBoxEdit.getChildren().addAll(labelGroup, spinnerNumberGroup);

        Label labelName = new Label(" " + getName());
        labelName.setMinWidth(300);
        hBoxEdit.getChildren().add(labelName);

        int i = getNumberGroup () % 2;
        if (i == 0){
            labelGroup.getStyleClass ().add ("label-service");
            labelName.getStyleClass ().add ("label-service");
        }

        Label labelCost = new Label("Цена (руб):  " + getCost());
        labelCost.setMinWidth(200);
        hBoxEdit.getChildren().add(labelCost);


        if (getTermDays() > 0) {
            Label labelTermDays = new Label();
            labelTermDays.setMinWidth(150);
            if (getTermDays() > 30) {
                double doubleTermMonth = getTermDays() / 30.33333;
                int intDoubleTermMonth = (int) doubleTermMonth;
                labelTermDays.setText("Срок (мес):  " + intDoubleTermMonth);
            } else {
                labelTermDays.setText("Срок (дн):  " + getTermDays());
            }
            hBoxEdit.getChildren().add(labelTermDays);
        }



        Label labelBalance = new Label("Остаток:  " + getBalance());
        if (getBalance() != 999_999) {

            labelBalance.setMinWidth(150);
//            Label labelBalanceAdd = new Label("Добавить");
//            labelBalanceAdd.setMinWidth(100);
//            Button buttonValue1= new Button("+ 1");
//            buttonValue1.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle (ActionEvent event) {
//                    new Service().updateServiceBalance(service, 1);
//                }
//            });
//            Button buttonValue10= new Button("+ 10");
//            buttonValue10.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle (ActionEvent event) {
//                    new Service().updateServiceBalance(service, 10);
//                }
//            });
//            Button buttonValue50 = new Button("+ 50");
//            buttonValue50.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle (ActionEvent event) {
//                    new Service().updateServiceBalance(service, 50);
//                }
//            });
//            Button buttonValue100= new Button("+ 100");
//            buttonValue100.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle (ActionEvent event) {
//                    new Service().updateServiceBalance(service, 100);
//                }
//            });
//            Button buttonValueDown= new Button("- 1");
//
//            buttonValueDown.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle (ActionEvent event) {
//                    if (service.getBalance() > 0){
//                        new Service().updateServiceBalance(service, -1);
//                    } else {
//                        new ErrorStage("Остаток 0");
//                    }
//
//                }
//            });
//            hBoxEdit.getChildren().addAll(labelBalance, labelBalanceAdd, buttonValueDown ,buttonValue1, buttonValue10, buttonValue50, buttonValue100);
           int[] valueBalance = {1,5,10,25,50,75,100,150,200, 500, 1000, 5000};

            Button buttonDown = new Button("-");
            buttonDown.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle (MouseEvent event) {
                    Platform.runLater(() -> {
                        ContextMenu contextMenu = new ContextMenu();
                        contextMenu.getItems().clear();
                        buttonDown.setContextMenu(contextMenu);
                        contextMenu.setStyle("-fx-max-width: 300;");
                        contextMenu.setStyle("-fx-max-height: 300;");

                        MenuItem[] items = new MenuItem[valueBalance.length];

                        for(int j = 0; j < valueBalance.length; j++) {


                            int I = valueBalance[j];

                            items[j] = new MenuItem(String.valueOf(" - " + I));

                            items[j].setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle (ActionEvent event) {
                                    if (service.getBalance() <= 0){
                                        new ErrorStage("Отстаток 0");
                                    } else {
                                        new Service().updateServiceBalance(service, -I);
                                    }

                                }
                            });
                            contextMenu.getItems().add(items[j]);
                        }


                        double x = MouseInfo.getPointerInfo().getLocation().getX();
                        double y = MouseInfo.getPointerInfo().getLocation().getY();

                        contextMenu.show(buttonDown, x, y);

                    });
                }
            });
            Button buttonUp = new Button("+");
            buttonUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle (MouseEvent event) {
                    Platform.runLater(() -> {
                        ContextMenu contextMenu = new ContextMenu();
                        contextMenu.getItems().clear();
                        buttonUp.setContextMenu(contextMenu);
                        contextMenu.setStyle("-fx-max-width: 300;");
                        contextMenu.setStyle("-fx-max-height: 300;");

                        MenuItem[] items = new MenuItem[valueBalance.length];

                        for(int j = 0; j < valueBalance.length; j++) {


                            int I = valueBalance[j];

                            items[j] = new MenuItem(String.valueOf(" + " + I));

                            items[j].setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle (ActionEvent event) {
                                    if (service.getBalance() + I >= 999_999){
                                        new WarningStage("Недопустимый остаток.");
                                    } else {
                                        new Service().updateServiceBalance(service, I);
                                    }

                                }
                            });
                            contextMenu.getItems().add(items[j]);
                        }


                        double x = MouseInfo.getPointerInfo().getLocation().getX();
                        double y = MouseInfo.getPointerInfo().getLocation().getY();

                        contextMenu.show(buttonUp, x, y);

                    });
                }
            });
            if (service.getBalance() <= 0){
                buttonDown.setVisible(false);
                buttonDown.setManaged(false);
            }
            hBoxEdit.getChildren().addAll(labelBalance, buttonDown, buttonUp);
        }

        hBoxEdit.setSpacing(3);


        if (getNumberClient() > 1) {
            Label labelNumClients = new Label();
            labelNumClients.setMinWidth(150);
            labelNumClients.setText("Clients:  " + getNumberClient());
            hBoxEdit.getChildren().add(labelNumClients);
        }


        hBoxEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    new StageEditService(hBoxEdit, service);
                }
            }
        });
        if (getBalance() <= 0){
            labelBalance.getStyleClass().add("label-warning");
        }
        return hBoxEdit;
    }

    public boolean addToDataBase(){

        PreparedStatement statement = null;

        try {

            String query = "INSERT INTO ServicePrice (" +
                           "ID_TYPE, ID_GROUP, NAME, COST, DELETE_CHECK, BALANCE, TERM_DAYS, timeControl, addSumTimeControl " +
                           ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

//            String query = "INSERT INTO Service_Price ("+
//                    "ID_TYPE, ID_GROUP, NAME, COST, DELETE_CHECK, BALANCE" +
//                    ") VALUES (?, ?, ?, ?, ?, ?);";


            statement = ServerMySQL.getConnection().prepareStatement (query);

            statement.setInt(1, this.getType ());
            statement.setInt(2, this.getNumberGroup ());
            statement.setString(3, this.getName ());
            statement.setDouble (4, this.getCost ());
            statement.setBoolean (5,false);
            statement.setInt(6, this.getBalance ());
            statement.setInt(7, this.getTermDays ());

            statement.setBoolean(8, this.isTimeControl ());
            statement.setDouble(9, this.getAddSumTimeControl ());

//


            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
        }
        return false;
    }




}
