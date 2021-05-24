package Services;

import Cash.CashBook;
import Cash.Transaction;
import GUIMain.CustomStage.DialogSelectPaymentMethod;
import GUIMain.CustomStage.ErrorStage;
import WorkDataBase.ActionClient;
import WorkDataBase.ActionUser;
import WorkDataBase.UserSpartak;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ClubCard extends Service {

    private int Type;
    private int numberGroup;
    private int id;
    private String name;
    private double cost;
    private int balance;
    private int termDays;
    private HBox hBoxInfo;
    private Service  service;

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
        name = name;
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
        Label labelName = new Label(" " + getName());
        labelName.setMinWidth(200);
        Label labelCost = new Label("Цена (руб):  " + getCost());
        labelCost.setMinWidth(150);
        Label labelBalance = new Label("Остаток:  " + getBalance());
        labelBalance.setMinWidth(150);
        Label labelTermDays= new Label();
        labelTermDays.setMinWidth(150);
        if (getTermDays() > 31){
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
                if (mouseEvent.getClickCount() == 2) {
                    if (getBalance () > 0) {
                        new DialogSelectPaymentMethod (hBoxInfo, service, ActionUser.getUser (), ActionClient.getClient ());
                    } else {
                        new ErrorStage (String.format ("%s.\nОтстаток %s.", service.getName (), getBalance () + ""));
                    }
                }
            }
        });
        if (getBalance() <= 0){
            labelBalance.setStyle("-fx-text-fill: #FF6347");
        }
        return hBoxInfo;
    }
}
