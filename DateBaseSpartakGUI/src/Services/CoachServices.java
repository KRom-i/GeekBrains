package Services;

import Cash.CashBook;
import Cash.Transaction;
import GUIMain.CustomStage.DialogSelectPaymentMethod;
import GUIMain.CustomStage.ErrorStage;
import Logger.LOG;
import MySQLDB.ServerMySQL;
import WorkDataBase.ActionClient;
import WorkDataBase.ActionUser;
import WorkDataBase.UserSpartak;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CoachServices extends Service{

    private int Type;
    private int numberGroup;
    private int id;
    private String name;
    private double cost;
//    private int balance;
    private int numberVisits;
    private HBox hBoxInfo;
    private HBox hBoxEdit;
    private int NumberClients;
    private Service  service;


    public CoachServices(String name, double cost, int numberVisits, int numberGroup) {
        this.name = name;
        this.cost = cost;
        this.numberGroup = numberGroup;
        this.numberVisits = numberVisits;
        this.service = this;
    }

    public int getNumberClients() {
        return NumberClients;
    }

    public void setNumberClients(int numberClients) {
        NumberClients = numberClients;
    }

    public int getNumberGroup() {
        return numberGroup;
    }

    public void setNumberGroup(int numberGroup) {
        this.numberGroup = numberGroup;
    }

    public int getNumberVisits() {
        return numberVisits;
    }

    public void setNumberVisits(int numberVisits) {
        this.numberVisits = numberVisits;
    }

//    public int getBalance() {
//        return balance;
//    }
//
//    public void setBalance(int balance) {
//        this.balance = balance;
//    }

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
//        Label labelBalance = new Label("Остаток:  " + getBalance());
//        labelBalance.setMinWidth(100);
        Label labelNumberVisits = new Label("Посещений:  " + getNumberVisits());
        labelNumberVisits.setMinWidth(150);
        hBoxInfo.getChildren().add(labelName);
        hBoxInfo.getChildren().add(labelCost);
//        hBoxInfo.getChildren().add(labelBalance);
        hBoxInfo.getChildren().add(labelNumberVisits);
        hBoxInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2){
                    new DialogSelectPaymentMethod (hBoxInfo , service, ActionUser.getUser (),ActionClient.getClient ());
                }
            }
        });
//        if (getBalance() <= 0){
//
//        }
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

        Label labelNumberVisits = new Label("Посещений:  ");
        TextField textFieldNumberVisits = new TextField("" +  getNumberVisits());
        textFieldNumberVisits.setMaxWidth(50);
        textFieldNumberVisits.setEditable(false);

        Button buttonEdit = new Button("Редактировать");
        buttonEdit.setMinWidth(100);

        Button buttonSave = new Button("Сохранить");
buttonSave.getStyleClass().add("save-button");
        buttonSave.setMinWidth(100);
        buttonSave.setVisible(false);
        buttonSave.setManaged(false);

        Button buttonDel = new Button("Удалить");
buttonDel.getStyleClass().add("del-button");
        buttonDel.setMinWidth(100);
        buttonDel.setVisible(false);
        buttonDel.setManaged(false);

        Label labelValNumberGroup = new Label(getNumberGroup() + "");
        Label labelValName = new Label(getName()+ "");
        Label labelValCost = new Label(getCost() + "");
        Label labelValNUmberVisits = new Label(getNumberVisits() + "");
//        Label labelValBalance = new Label(getBalance() + "");

        hBoxEdit.getChildren().add(labelNumberGroup);
        hBoxEdit.getChildren().add(textNumberGroup);
        hBoxEdit.getChildren().add(labelValNumberGroup);
        textNumberGroup.setVisible(false);
        textNumberGroup.setManaged(false);
        hBoxEdit.getChildren().add(labelName);
        hBoxEdit.getChildren().add(textFieldName);
        hBoxEdit.getChildren().add(labelValName);
        textFieldName.setVisible(false);
        textFieldName.setManaged(false);
        hBoxEdit.getChildren().add(labelCost);
        hBoxEdit.getChildren().add(textFieldCost);
        hBoxEdit.getChildren().add(labelValCost);
        textFieldCost.setVisible(false);
        textFieldCost.setManaged(false);
        hBoxEdit.getChildren().add(labelNumberVisits);
        hBoxEdit.getChildren().add(textFieldNumberVisits);
        hBoxEdit.getChildren().add(labelValNUmberVisits);
        textFieldNumberVisits.setVisible(false);
        textFieldNumberVisits.setManaged(false);
//        hBoxEdit.getChildren().add(labelBalance);
//        hBoxEdit.getChildren().add(textFieldBalance);
//        textFieldBalance.setVisible(false);
//        textFieldBalance.setManaged(false);
//        hBoxEdit.getChildren().add(labelValBalance);
//        labelValBalance.setMinWidth(50);
//        hBoxEdit.getChildren().add(labelAddBalance);
//        hBoxEdit.getChildren().add(spinnerCost);

        //    hBoxEdit.setOpacity(0.7);

        HBox hBoxMain = new HBox(hBoxEdit);
        hBoxMain.setSpacing(5);
        hBoxMain.getChildren().add(buttonEdit);
        hBoxMain.getChildren().add(buttonSave);
        hBoxMain.getChildren().add(buttonDel);

        buttonEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //    hBoxEdit.setOpacity(1);
                textNumberGroup.setEditable(true);
                textFieldCost.setEditable(true);
                textFieldName.setEditable(true);
                textFieldNumberVisits.setEditable(true);
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
                labelValNumberGroup.setVisible(false);
                labelValName.setVisible(false);
                labelValCost.setVisible(false);
                labelValNUmberVisits.setVisible(false);
//                labelValBalance.setVisible(false);
                labelValNumberGroup.setManaged(false);
                labelValName.setManaged(false);
                labelValCost.setManaged(false);
                labelValNUmberVisits.setManaged(false);
//                labelValBalance.setManaged(false);
//                textFieldBalance.setManaged(true);
//                textFieldBalance.setVisible(true);
                textFieldCost.setManaged(true);
                textFieldCost.setVisible(true);
                textFieldName.setManaged(true);
                textFieldName.setVisible(true);
                textNumberGroup.setManaged(true);
                textNumberGroup.setVisible(true);
                textFieldNumberVisits.setManaged(true);
                textFieldNumberVisits.setVisible(true);

            }
        });

        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //    hBoxEdit.setOpacity(0.7);
                textNumberGroup.setEditable(false);
                textFieldCost.setEditable(false);
                textFieldName.setEditable(false);
                textFieldNumberVisits.setEditable(false);
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

                labelValNumberGroup.setVisible(true);
                labelValName.setVisible(true);
                labelValCost.setVisible(true);
                labelValNUmberVisits.setVisible(true);
//                labelValBalance.setVisible(true);
                labelValNumberGroup.setManaged(true);
                labelValName.setManaged(true);
                labelValCost.setManaged(true);
                labelValNUmberVisits.setManaged(true);
//                labelValBalance.setManaged(true);

//                textFieldBalance.setManaged(false);
//                textFieldBalance.setVisible(false);
                textFieldCost.setManaged(false);
                textFieldCost.setVisible(false);
                textFieldName.setManaged(false);
                textFieldName.setVisible(false);
                textNumberGroup.setManaged(false);
                textNumberGroup.setVisible(false);
                textFieldNumberVisits.setManaged(false);
                textFieldNumberVisits.setVisible(false);
            }
        });

//        if (getBalance() <= 0){
//            textFieldBalance.setStyle("-fx-background-color: #FF6347");
//            labelValBalance.setStyle("-fx-text-fill: #FF6347");
//        }
        return hBoxMain;
    }

    public static class GUIMainStage extends Application {

        @Override
        public void start(Stage primaryStage){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("GUIMain.fxml"));
            Scene scene = new Scene(root);
            String stylesheet = getClass().getResource("Styles/style.css").toExternalForm();
            scene.getStylesheets().add(stylesheet);
            primaryStage.setTitle("DBS V 1.0");
    //        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //        double width = screenSize.getWidth();
    //        double height = screenSize.getHeight();
    //        primaryStage.setY(height / 6);
    //        primaryStage.setX(width / 6);
    //        primaryStage.initStyle(StageStyle.UNDECORATED);
    //        String css = this.getClass().getResource("/styles/style.css").toExternalForm();
    //        scene.getStylesheets().add(css);
    //        scene.setFill(Color.TRANSPARENT);
    //        primaryStage.setWidth(width - (width / 3));
    //        primaryStage.setHeight(height - (height / 3));
            primaryStage.setHeight (1000);
            primaryStage.setWidth (1600);
            primaryStage.setScene(scene);
            primaryStage.show();
            LOG.info ("Успешный старт приложения.");
            } catch (Exception e) {
                LOG.error ("Ошибка при запуске приложения.", e);
            }

        }


        public static void main(String[] args) {
                launch(args);
        }

        @Override
        public void stop(){
            ServerMySQL.disconnect();
            LOG.info ("Приложение закрыто без ошибок.");
        }
    }
}
