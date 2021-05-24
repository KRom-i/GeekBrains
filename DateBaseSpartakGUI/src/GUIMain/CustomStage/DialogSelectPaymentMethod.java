package GUIMain.CustomStage;

import Cash.CashBook;
import Cash.Transaction;
import Cash.TypePaymentArray;
import Logger.LOG;
import Services.Service;
import WorkDataBase.ActionClient;
import WorkDataBase.ClientClass;
import WorkDataBase.UserSpartak;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import СustomTitlePanel.ChoiceTitlePanel;
import СustomTitlePanel.EditServicesTitlePanel;

public class DialogSelectPaymentMethod {

    private Service service;
    private UserSpartak user;
    private ClientClass client;

    public DialogSelectPaymentMethod(Node node, Service service, UserSpartak user, ClientClass client) {

        this.service = service;
        this.user = user;
        this.client = client;

        Stage mainStage = (Stage) node.getScene().getWindow();

        StackPane stackPane = new StackPane();
//        stackPane.setStyle("-fx-background-radius: 5; " +
//                " -fx-background-color: #A9A9A9; " +
//                " -fx-text-fill: black;");
//        stackPane.getChildren().add(new Label (textInfo));

        Stage newWindow = new Stage();

        newWindow.setTitle("Выбор способа оплаты");

        Scene scene = new Scene(stackPane ,400, 400);
//        scene.setOnMouseClicked(new EventHandler<MouseEvent> () {
//            @Override
//            public void handle (MouseEvent event) {
//                newWindow.close();
//            }
//        });




        TypePaymentArray typePaymentArray = new TypePaymentArray ();
        Button b1 = new Button (typePaymentArray.getName (1));
        b1.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                newTran(1);
                newWindow.close();
            }
        });
        Button b2 = new Button (typePaymentArray.getName (2));
        b2.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                newTran(2);
                newWindow.close();
            }
        });
        Button b3 = new Button (typePaymentArray.getName (3));
        b3.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                newTran(3);
                newWindow.close();
            }
        });

        HBox hBox = new HBox ();
        hBox.getChildren ().addAll (b1, b2, b3);
        hBox.setSpacing (5);
        hBox.setAlignment (Pos.CENTER);

        TextField tFNameClient = new TextField (client.toStringIteam ());
        TextField tFNameService = new TextField (service.getName ());
        TextField tFCostService = new TextField (service.getCost () + "");

        tFNameClient.setEditable (false);
        tFNameService.setEditable (false);
        tFCostService.setEditable (false);

        tFNameClient.setMaxSize (350,60);
        tFNameClient.setMinHeight (40);
        tFNameService.setMaxSize (350,60);
        tFNameService.setMinHeight (40);
        tFCostService.setMaxSize (350,60);
        tFCostService.setMinHeight (40);
        b1.setMinSize (110,40);
        b2.setMinSize (110,40);
        b3.setMinSize (110,40);

        Label label1 = new Label ("Имя клиента");
        Label label2 = new Label ("Наименование услуги/товара:");
        Label label3 = new Label ("Сумма:");
        Label label4 = new Label ("Способ оплаты: ");


        VBox vBox = new VBox ();
        vBox.setMaxWidth (350);
        vBox.setAlignment (Pos.TOP_LEFT);
        vBox.setSpacing (5);

        VBox vBoxReg = new VBox ();
        vBoxReg.setMaxWidth (350);
        vBoxReg.setAlignment (Pos.TOP_LEFT);
        vBoxReg.setSpacing (5);

        HBox hBox2 = new HBox ();
        hBox2.setSpacing (5);
        Label label5 = new Label ("Регистрация в зале");
        CheckBox checkBox = new CheckBox ();
        checkBox.setSelected (true);


        Label labelKeyNum = new Label ("Номер ключа");

        HBox hBoxKey = new HBox ();
        hBoxKey.setSpacing (10);
        hBoxKey.setAlignment (Pos.CENTER_LEFT);

        HBox hBoxMen = new HBox ();
        hBoxMen.setAlignment (Pos.CENTER);
        hBoxMen.setSpacing (5);
        Label labelMen = new Label ("М");
        CheckBox checkBoxMenKey = new CheckBox ();
        checkBoxMenKey.setSelected (false);
        Spinner<Double> spinnerCostKyeMen = new Spinner<>();
        spinnerCostKyeMen.setMaxWidth(100);
        spinnerCostKyeMen.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 40, 1));
        hBoxMen.getChildren ().addAll (labelMen,checkBoxMenKey, spinnerCostKyeMen);


        HBox hBoxWomen = new HBox ();
        hBoxWomen.setAlignment (Pos.CENTER);
        hBoxWomen.setSpacing (5);
        Label labelWoman = new Label ("Ж");
        CheckBox checkBoxWomenKey = new CheckBox ();
        checkBoxWomenKey.setSelected (false);
        Spinner<Double> spinnerCostKyeWoman = new Spinner<>();
        spinnerCostKyeWoman.setMaxWidth(100);
        spinnerCostKyeWoman.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 40, 1));
        hBoxWomen.getChildren ().addAll (labelWoman, checkBoxWomenKey,spinnerCostKyeWoman);

        hBoxKey.getChildren ().addAll (hBoxMen, hBoxWomen);

        hBox2.setSpacing (5);
        hBox2.getChildren ().addAll (label5, checkBox);

        vBoxReg.getChildren ().addAll (hBox2, labelKeyNum,hBoxKey);


        Label labelRegTrue = new Label ("Клиент зарегистрирован в зале");
        labelRegTrue.setStyle ("-fx-text-fill: #35dc6b");

        vBox.getChildren ().addAll (label1,tFNameClient, label2,tFNameService, label3,tFCostService, label4, hBox,labelRegTrue ,vBoxReg);

        stackPane.setAlignment (Pos.CENTER);
        stackPane.getChildren ().add (vBox);

        newWindow.setScene(scene);

        checkBox.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
                if (checkBox.isSelected ()){
                    labelKeyNum.setOpacity (1);
                    hBoxMen.setOpacity (1);
                    hBoxWomen.setOpacity (1);
                } else {
                    labelKeyNum.setOpacity (0.5);
                    hBoxMen.setOpacity (0.5);
                    hBoxWomen.setOpacity (0.5);
                }
            }
        });

//        stackPane.getStylesheets().add("file:/C:/Users/%d0%a0%d0%be%d0%bc%d0%b0%d0%bd/Documents/REPO_My/DateBaseSpartakGUI/out/production/DataBaseSpartakGUI/GUIMain/Styles/style.css");
        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(mainStage);

        newWindow.initStyle(StageStyle.UTILITY);

        // Set position of second window, related to primary window.

//        newWindow.setOpacity(0.7);
//        newWindow.setX(getXbottLeftStage(mainStage, scene) - 20);
//        newWindow.setY(getYbottLeftStage(mainStage, scene) - 20);

        newWindow.show();

    }


    private void newTran(int idTypePayment){
//        Добавить тип услуги вместо проверки баланса
        if (service.getBalance () > 0){
            LOG.info (String.format ("Покупка [%s]", service.toString ()));
            LOG.info (String.format ("Текущий остаток [%s]", service.getBalance ()));
            service.setBalance (service.getBalance () - 1);
            LOG.info (String.format ("Остаток после операции [%s]", service.getBalance ()));
        }
        Transaction t = new Transaction (1, service, client, user, idTypePayment);
        CashBook.addTransactionToCashBook (t);

    }
}
