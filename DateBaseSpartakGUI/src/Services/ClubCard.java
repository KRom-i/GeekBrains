package Services;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ClubCard {

    private int Type;
    private int numberGroup;
    private int id;
    private String name;
    private double cost;
    private int balance;
    private int termDays;
    private HBox hBoxInfo;

    public ClubCard(String name,double cost, int balance, int termDays, int numberGroup) {
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
        Label labelName = new Label(getName());
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
                if (getBalance() <= 0){
                    labelBalance.setStyle("-fx-background-color: #FF6347");
                }
            }
        });
        if (getBalance() <= 0){
            labelBalance.setStyle("-fx-text-fill: #FF6347");
        }
        return hBoxInfo;
    }
}
