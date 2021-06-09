package GUIMain.CustomStage;

import Cash.CashBook;
import Cash.Transaction;
import Cash.TypePaymentArray;
import GUIMain.Styles.CssUrl;
import Logger.LOG;
import Services.ActionServices.ActionService;
import Services.ImroptExcel.ServiceImport;
import Services.Service;
import WorkDataBase.ActionClient;
import WorkDataBase.ClientClass;
import WorkDataBase.ClientDataBase;
import WorkDataBase.Clients.NewClientStage;
import WorkDataBase.Trainers.Trainer;
import WorkDataBase.Trainers.TrainerList;
import WorkDataBase.UserSpartak;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialogSelectPaymentMethod {

    private Service service;
    private UserSpartak user;
    private ClientClass client;
    private List<ClientClass> clientListActionService;
    private List<ClientClass> clientListDateBase;
    private Stage newWindow;
    private boolean clientClub;
    private ChoiceBox<String> choiceBox;

    public DialogSelectPaymentMethod(Node node, Service service, UserSpartak user, ClientClass client) {

        this.service = service;
        this.user = user;
        this.client = client;

        Stage mainStage = (Stage) node.getScene().getWindow();

        StackPane stackPane = new StackPane();

        double height = 500;

//        stackPane.setStyle("-fx-background-radius: 5; " +
//                " -fx-background-color: #A9A9A9; " +
//                " -fx-text-fill: black;");
//        stackPane.getChildren().add(new Label (textInfo));

        newWindow = new Stage();

        newWindow.setTitle("Выбор способа оплаты");



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

            }
        });
        Button b2 = new Button (typePaymentArray.getName (2));
        b2.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                newTran(2);

            }
        });
        Button b3 = new Button (typePaymentArray.getName (3));
        b3.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                newTran(3);

            }
        });

        HBox hBox = new HBox ();
        hBox.getChildren ().addAll (b1, b2, b3);
        hBox.setSpacing (5);
        hBox.setAlignment (Pos.CENTER);

        VBox vBoxClients = new VBox();
        vBoxClients.setSpacing(5);

        clientListActionService = new ArrayList<>();
        clientListActionService.add(client);

        int clients = service.getNumberClient();
        if (clients > 1){
            height = height + (30 * clients);

            Label[] labelsName = new Label[clients];
            TextField[] textFieldsName = new TextField [clients];
            for(int i = 0; i < clients; i++) {
                String str = String.format("ФИО %s-го клиента", i + 1);
                labelsName[i] = new Label(str);
                textFieldsName[i] = new TextField();
                textFieldsName[i].setPromptText(str);
                if (i == 0){
                    textFieldsName[i].setText(client.toStringIteam());
                    textFieldsName[i].setEditable(false);
                } else {
                    final int I = i;
                    textFieldsName[i].setOnKeyReleased(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle (KeyEvent event) {
                            if (textFieldsName[I].isEditable()) {
                                event.consume();
                                TextInputControl control = (TextInputControl) event.getSource();
                                Point2D pos = control.getInputMethodRequests().getTextLocation(0);
                                methodFindClientDataBase(textFieldsName[I], pos.getX(), pos.getY());
                            }
                        }
                    });
                }

                textFieldsName[i].setMaxSize (350,60);
                textFieldsName[i].setMinHeight  (25);
                vBoxClients.getChildren().addAll(labelsName[i], textFieldsName[i]);

            }
        } else {
            Label label1 = new Label ("ФИО клиента");
            TextField tFNameClient = new TextField (client.toStringIteam ());
            tFNameClient.setMaxSize (350,60);
            tFNameClient.setMinHeight  (25);
            tFNameClient.setEditable (false);
            vBoxClients.getChildren().addAll(label1, tFNameClient);
        }

        if (service.getType() == 5) {

            height += 40;
            Label labelTrainer = new Label("Тренер");
            TextField textFieldTrainer = new TextField();
            textFieldTrainer.setMaxSize (350,60);
            textFieldTrainer.setMinHeight  (25);
            textFieldTrainer.setPromptText("ФИО тренера");

            if (service.getIdTre() > 0){
                textFieldTrainer.setText(new Trainer().getOneTrainer(service.getIdTre()).getClient().toStringIteam());
                textFieldTrainer.setEditable(false);
            } else {
                textFieldTrainer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle (MouseEvent event) {
                        List<Trainer> trainerList = TrainerList.get();

                        Platform.runLater (()->{
                            ContextMenu contextMenu = new ContextMenu();
                            contextMenu.getItems ().clear ();
                            textFieldTrainer.setContextMenu (contextMenu);
                            contextMenu.setStyle("-fx-max-width: 300;");
                            contextMenu.setStyle("-fx-max-height: 300;");

                            MenuItem[] items =  new MenuItem[trainerList.size()];

                            for(int j = 0; j < trainerList.size(); j++) {

                                final int J = j;

                                items[j] = new MenuItem (trainerList.get(j).getClient().toStringIteam());

                                items[j].setOnAction (new EventHandler<ActionEvent> () {
                                    @Override
                                    public void handle (ActionEvent event){
                                        textFieldTrainer.setText(trainerList.get(J).getClient().toStringIteam());
                                        service.setIdTre(trainerList.get(J).getId());

                                    }
                                });
                                contextMenu.getItems ().add (items[j]);
                            }

                            double x = MouseInfo.getPointerInfo().getLocation().getX();
                            double y = MouseInfo.getPointerInfo().getLocation().getY();
                            contextMenu.show (textFieldTrainer, x,y);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
                        });


                    }
                });
            }
            vBoxClients.getChildren().addAll(labelTrainer, textFieldTrainer);

            if (service.getTypeTren().equalsIgnoreCase("percent")){
                height += 30;
                clientClub = false;
                choiceBox = new ChoiceBox<>();
                choiceBox.getItems().addAll("Клиент КЛУБА", "Клиент ТРЕНЕРА");
                choiceBox.setMinWidth(200);
                choiceBox.setValue("Клиент ТРЕНЕРА");

                vBoxClients.getChildren().addAll(choiceBox);

            }
        }

        TextField tFNameService = new TextField (service.getName ());
        TextField tFCostService = new TextField (service.getCost () + "");



        tFNameService.setEditable (false);
        tFCostService.setEditable (false);


        tFNameService.setMaxSize (350,60);
        tFNameService.setMinHeight  (25);
        tFCostService.setMaxSize (350,60);
        tFCostService.setMinHeight  (25);
        b1.setMinSize (110,40);
        b2.setMinSize (110,40);
        b3.setMinSize (110,40);


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

        vBox.getChildren ().addAll ( vBoxClients, label2,tFNameService, label3,tFCostService, label4, hBox,labelRegTrue ,vBoxReg);

        stackPane.setAlignment (Pos.CENTER);
        stackPane.getChildren ().add (vBox);



        Scene scene = new Scene(stackPane ,500, height);
        scene.getStylesheets().add(new CssUrl().get ());
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


        if (service.getType() < 3) {
            if (service.getBalance() > 0 && service.getBalance() < 999_999) {
                service.setBalance(service.getBalance() - 1);
                new Service().updateService(service);
                ServiceImport.readDataBase(false);
                ServiceImport.updateVbox(service.getType() - 1, service.getId(), 1);
            }

            Transaction transaction = new Transaction(1, service, client, user, idTypePayment);
            CashBook.addTransactionToCashBook(transaction);
            newWindow.close();
        } else {

            boolean save = true;



           if (service.getNumberClient() > 1){
               if (service.getNumberClient() != clientListActionService.size()){
                   save = false;
                   new ErrorStage("Необходимо выбрать КЛИЕНТОВ");
               }
           }

           if (service.getType() == 5 && service.getIdTre() == 0 && save){
               save = false;
               new ErrorStage("Необходимо выбрать ТРЕНЕРА");
           }
           
           if (choiceBox != null) {
               clientClub = choiceBox.getValue().equalsIgnoreCase("Клиент КЛУБА");
           }

           if (save){

               if (service.getBalance() > 0 && service.getBalance() < 999_999) {
                   service.setBalance(service.getBalance() - 1);
                   new Service().updateService(service);
                   ServiceImport.readDataBase(false);
                   ServiceImport.updateVbox(service.getType() - 1, service.getId(), 1);
               }

               Transaction transaction = new Transaction(1, service, client, user, idTypePayment);
               CashBook.addTransactionToCashBook(transaction);

               ActionService actionService = new ActionService(service,transaction,clientClub, clientListActionService);
               actionService.toDateBaseWrite();

               ActionService.updateHBoxGUIInfo();

               newWindow.close();


           }
        }



    }



    private List<ClientClass> clientClassListFind;
    private boolean findAction;
    //    Метод поиска клиента
    private void methodFindClientDataBase (TextField textFieldFind, double coordinateX, double coordinateY){

        if (!findAction
            && !textFieldFind.getText ().startsWith (" ")
            && textFieldFind.getText ().length () > 1){


            Platform.runLater (()->{
                textFieldFind.setStyle("fx-text-fill: black;");
            });

            findAction = true;
            clientListDateBase = ClientDataBase.newListClient();
            new Thread(()->{
                while (true){

                    String findString = textFieldFind.getText ();
                    clientClassListFind = new ArrayList<>();
                    clientClassListFind.clear ();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int lengthStr = textFieldFind.getText ().length ();

                    if (textFieldFind.getText ().startsWith (" ")
                        || textFieldFind.getText ().length () <= 2){
                        Platform.runLater (()->{
                            if (contextMenu != null){
                                contextMenu.getItems ().clear ();
                            }
                        });
                        findAction = false;
                        break;
                    }

                    if (findString.equalsIgnoreCase (textFieldFind.getText ())){
                        for(ClientClass client: clientListDateBase
                        ) {
                            if (client.booleanFind (findString) && client.getId() != ActionClient.getClient().getId() ){

                                boolean checkAdd = false;
                                    for(int i = 0; i < clientListActionService.size(); i++) {
                                        checkAdd = clientListActionService.get(i).getId() == client.getId();
                                    }
                                if (!checkAdd){
                                    clientClassListFind.add (client);
                                }

                            }

                        }

                        if (!clientClassListFind.isEmpty()){
                            menuReturnFind(clientClassListFind, textFieldFind, coordinateX, coordinateY);
                        } else {
                            Platform.runLater (()->{
                                if (contextMenu != null){
                                    contextMenu.getItems ().clear ();
                                }
                                textFieldFind.setStyle("-fx-background-color: #FF6347;");
                                contextMenuAddNewUser(textFieldFind, coordinateX, coordinateY);
                            });
                        }
                        findAction = false;
                        break;
                    }


                }
            }).start();

        }





    }

    ContextMenu contextMenu;

    //   contextMenu  при поиске клиента;
    private void menuReturnFind(List<ClientClass> clientClassListFind, TextField textFieldFind, double coordinateX, double coordinateY){

            Platform.runLater(() -> {
                if (contextMenu != null) {
                    contextMenu.getItems().clear();
                }
                contextMenu = new ContextMenu();
                textFieldFind.setContextMenu(contextMenu);
                contextMenu.setStyle("-fx-max-width: 670;");
                contextMenu.setStyle("-fx-min-width: 670;");
                contextMenu.setStyle("-fx-max-height: 300;");

                MenuItem[] items = new MenuItem[clientClassListFind.size()];

                for(int j = 0; j < clientClassListFind.size(); j++) {

                    items[j] = new MenuItem(clientClassListFind.get(j).toStringIteam());
                    int finalJ = j;
                    items[j].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle (ActionEvent event) {
                            ClientClass client = clientClassListFind.get(finalJ);
                            textFieldFind.setText(client.toStringIteam());
                            textFieldFind.setEditable(false);
                            clientListActionService.add(client);
                            textFieldFind.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle (MouseEvent event) {
                                    if (!textFieldFind.isEditable()) {
                                        Platform.runLater(() -> {

                                            ContextMenu contextMenu = new ContextMenu();
                                            contextMenu.getItems().clear();
                                            textFieldFind.setContextMenu(contextMenu);
                                            contextMenu.setStyle("-fx-max-width: 400;");
                                            contextMenu.setStyle("-fx-max-height: 300;");

                                            MenuItem item = new MenuItem("Удалить");
                                            item.setOnAction(new EventHandler<ActionEvent>() {
                                                @Override
                                                public void handle (ActionEvent event) {

                                                    textFieldFind.clear();
                                                    textFieldFind.setEditable(true);
                                                    for(int i = 0; i < clientListActionService.size(); i++) {
                                                        if (clientListActionService.get(i).getId() == client.getId()) {
                                                            clientListActionService.remove(i);
                                                        }
                                                    }

                                                }
                                            });

                                            contextMenu.getItems().add(item);

                                            double x = MouseInfo.getPointerInfo().getLocation().getX();
                                            double y = MouseInfo.getPointerInfo().getLocation().getY();

                                            contextMenu.show(textFieldFind, x, y);

                                        });
                                    }
                                }
                            });
                            findAction = false;
                        }
                    });
                    contextMenu.getItems().add(items[j]);
                }

                Stage stage = (Stage) textFieldFind.getScene().getWindow();


                contextMenu.show(stage, coordinateX, coordinateY);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
            });

        }


    private void contextMenuAddNewUser(TextField textFieldFind, double coordinateX, double coordinateY){
        Platform.runLater (()->{
            if (contextMenu != null){
                contextMenu.getItems ().clear ();
            }
            contextMenu = new ContextMenu ();
            textFieldFind.setContextMenu (contextMenu);
            contextMenu.setStyle("-fx-max-width: 670;");
            contextMenu.setStyle("-fx-min-width: 670;");
            contextMenu.setStyle("-fx-max-height: 300;");

            MenuItem item =  new MenuItem("Добавить нового клиента");
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle (ActionEvent event) {
                    new NewClientStage(textFieldFind, textFieldFind.getText());
                }
            });
            contextMenu.getItems ().add (item);


            Stage stage = (Stage) textFieldFind.getScene ().getWindow ();

            contextMenu.show(stage, coordinateX, coordinateY);

        });
    }


}
