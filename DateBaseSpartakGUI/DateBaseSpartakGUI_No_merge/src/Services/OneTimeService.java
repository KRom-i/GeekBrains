package Services;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class OneTimeService extends Service {

    private int Type;
    private int numberGroup;
    private int id;
    private String Name;
    private double cost;
    private HBox hBoxInfo;
    private HBox hBoxEdit;

    public OneTimeService(String name,double cost, int numberGroup) {
        Name = name;
        this.cost = cost;
        this.numberGroup = numberGroup;

    }

    public int getNumberGroup() {
        return numberGroup;
    }

    public void setNumberGroup(int numberGroup) {
        this.numberGroup = numberGroup;
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
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
        Label labelCost = new Label("Цена (руб):  "+ getCost());
        labelCost.setMinWidth(150);
        hBoxInfo.getChildren().add(labelName);
        hBoxInfo.getChildren().add(labelCost);
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

//        Label labelBalance = new Label("Остаток:  ");
//
//
//        TextField textFieldBalance = new TextField("" + getBalance());
//        textFieldBalance.setMaxWidth(50);
//        textFieldBalance.setEditable(false);
//
//        Label labelAddBalance = new Label("Добавить:  ");
//        labelAddBalance.setVisible(false);
//        labelAddBalance.setManaged(false);
//
//        Spinner<Double> spinnerCost = new Spinner<>();
//        spinnerCost.setMaxWidth(100);
//        spinnerCost.setVisible(false);
//        spinnerCost.setManaged(false);
//        spinnerCost.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 999999, 0));

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
//        hBoxInfo.getChildren().add(labelBalance);
//        hBoxInfo.getChildren().add(textFieldBalance);
//        hBoxInfo.getChildren().add(labelAddBalance);
//        hBoxInfo.getChildren().add(spinnerCost);
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
//                labelAddBalance.setVisible(true);
//                labelAddBalance.setManaged(true);
//                spinnerCost.setEditable(true);
//                spinnerCost.setVisible(true);
//                spinnerCost.setManaged(true);
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
//                labelAddBalance.setVisible(false);
//                labelAddBalance.setManaged(false);
//                spinnerCost.setEditable(false);
//                spinnerCost.setVisible(false);
//                spinnerCost.setManaged(false);
                buttonEdit.setVisible(true);
                buttonEdit.setManaged(true);
                buttonSave.setVisible(false);
                buttonSave.setManaged(false);
                buttonDel.setVisible(false);
                buttonDel.setManaged(false);
            }
        });

//        if (getBalance() <= 0){
//            textFieldBalance.setStyle("-fx-background-color: red");
//        }
        return hBoxMain;
    }
}
