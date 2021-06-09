package GUIMain;


import ControlOperation.Check;
import GUIMain.CustomStage.*;
import Bot.BotTelegram;
import Cash.CashBook;
import Cash.NodeViewHistory;
import Format.DateTime;
import GUIMain.CustomStage.DialogDateInit;
import Logger.LOG;
import MySQLDB.ServerMySQL;
import Services.ActionServices.ActionService;
import Services.ImroptExcel.ServiceImport;
import Services.StageService.*;
import WorkDataBase.*;
import WorkDataBase.AuthUser.DialogAuth;
import WorkDataBase.Clients.ClientStage;
import WorkDataBase.Clients.NewClientStage;
import WorkDataBase.Trainers.TrainerList;
import WorkDataBase.User.DialogAddNewUser;
import WorkDataBase.User.DialogUserEdit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GUIMainController {


    public VBox vBoxListClients;
    public ListView listViewBalanceTrainers;
    public HBox hBoxBalanceCashBook;
    public HBox vBoxActionService;
    @FXML
    private ListView listViewEditServices;
    @FXML
    private TextField textFieldBirthDay;
    @FXML
    private TextField textFieldNameClient;
    @FXML
    private ListView listViewHistoryTran;
    @FXML
    private TextField textFieldDateTameStart;
    @FXML
    private TextField textFieldNameUser;
    @FXML
    private VBox panelUserInterface;
    @FXML
    private VBox vBoxEditServices;
    @FXML
    private Button buttonNewClient;
    @FXML
    private Button buttonListClients;
    @FXML
    private  Button buttonFindClient;
    @FXML
    private HBox hBoxFindClient;
    @FXML
    private VBox vBoxChoiceService;
    @FXML
    private ListView<HBox> listViewListClients;
    @FXML
    private VBox vBoxOrderService;
    @FXML
    private HBox hBoxWorkClient;
    @FXML
    private HBox hBoxOrderService;
    @FXML
    private VBox vBoxFindClient;
    @FXML
    private VBox vBOXEditClient;
    @FXML
    private Button buttonGuest;
    @FXML
    private  Label labelSaveEdit;
    @FXML
    private TextField textTelephone;
    @FXML
    private TextField textDateBirth;
    @FXML
    private TextField textEmail;
    @FXML
    private TextArea textInfoClient;
    @FXML
    private TextField textFirtsName;
    @FXML
    private TextField textLastName;
    @FXML
    private TextField textPatronymicName;
    private static List<ClientClass> clientClassList;
    private ClientClass clientClassActive;
    
    public void initialize() {




        new LabelInfo(panelUserInterface);

        methodAuth();

        NodeViewHistory.init (listViewHistoryTran);

        new ActionService(vBoxActionService);


        try {
            ImageView imageView = new ImageView(new Image(new File("img/logo/search.gif").toURI().toURL().toString()));
            imageView.setFitHeight(40);
            imageView.setFitWidth(50);
            hBoxFindClient.getChildren().add(imageView);
            hBoxFindClient.getStyleClass().add("box-class");
        } catch (Exception e){e.printStackTrace();}


        new TrainerList(listViewBalanceTrainers);
        TrainerList.updateListView();

        new CashBook(hBoxBalanceCashBook);
        CashBook.updateBoxBalanceCashBook();

        Platform.runLater (()->{
            ServiceImport serviceImport = new ServiceImport (vBoxChoiceService, listViewEditServices);
            serviceImport.readDataBase (false);
            serviceImport.updateVbox (0, 0,0);

        });

            initBot ();

    }

//    Установка конфигураций для теста
    @FXML
    private void addConfigDate(){
        Platform.runLater (()->{
            new DialogDateInit (panelUserInterface);
        });

    }

//    Мето для обновляет иторию кассовых операций за день

//    public void methodNewHistoryTran(){
//        Platform.runLater (()->{
//            List<Transaction> t = CashBook.getListTranDay (new DateTime ().currentDate ());
//            if (!t.isEmpty ()) {
//                for (int i = 0; i < t.size (); i++) {
//                    listViewGHistoryTransactionDay.getItems ().add(t.get (i).toString ());
//                }
//
//            }
//        });
//    }


//    Метод для авторизации
    private void methodAuth(){
        Platform.runLater (()->{
            textFieldNameUser.setEditable (false);
            textFieldDateTameStart.setEditable (false);
            new DialogAuth (panelUserInterface, textFieldNameUser, textFieldDateTameStart);
        });
    }



//    Установить всплывающую подсказку Tooltip
    private void addTooltip(){
//       textFieldFind.setTooltip(new Tooltip("Введите ФИО клиента"));
        Tooltip tt = new Tooltip();
        tt.setText("Text on Hover");
        tt.setStyle("-fx-font: normal bold 20 Langdon; "
                + "-fx-base: #AE3522; "
                + "-fx-text-fill: orange;");
        buttonGuest.setTooltip(tt);
    }


    public static TelegramBotsApi telegramBotsApi;
    public static BotTelegram bot;

    public void initBot() {



        new Thread(()->{
            int check = 0;
            while (true) {

                if (new Check().netIsAvailable()) {

                    try {
                        ApiContextInitializer.init();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        telegramBotsApi = new TelegramBotsApi();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        bot = new BotTelegram();
                        telegramBotsApi.registerBot(bot);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(()->{
                        new InfoStage("Соединение с интернетом установлено");
                    });
                    break;
                } else {
                    check++;
                    Platform.runLater(()->{
                        new WarningStage("Отсутвует соединение с интернетом");
                    });
                }

                try {
                    if (check < 10){
                        Thread.sleep(30000);
                    } else {
                        Thread.sleep(60000 * 5);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }



    private ExecutorService serviceTextClear = Executors.newFixedThreadPool (1);

//    Редактирование или добавление нового клиента
    public void methodSaveEditClient(ActionEvent actionEvent) {

        if (ActionClient.getClient() == null){
        if (textFirtsName.getText ().length () < 2 &&
                textLastName.getText ().length () < 2) {
        } else {
            try {
                ClientClass clientNew = new ClientClass();
                clientNew.setId(ClientDataBase.idUpDateBase(clientNew.nameClassDataBase()));
                clientNew.setFirstName(editText(textLastName.getText()));
                clientNew.setLastName(editText(textFirtsName.getText()));
                clientNew.setPatronymicName(editText(textPatronymicName.getText()));
                clientNew.setTelephone(checkText(textTelephone));
                clientNew.setDateBirth(checkText(textDateBirth));
                clientNew.setEmail(checkText(textEmail));
                clientNew.setInfoClient(checkText(textInfoClient));
                ClientDataBase.addNewClientDateBase(clientNew);
                new InfoStage ("Успешное сохранение данных");
                LOG.info("Добавление нового клиента: " + clientNew.toString());
//                bot.sendMsgStart("Добавление нового клиента: " + clientNew.toString ());
                clearText(textFirtsName);
                clearText(textLastName);
                clearText(textPatronymicName);
                clearText(textTelephone);
                clearText(textDateBirth);
                clearText(textEmail);
                clearText(textInfoClient);
//                vBOXEditClient.setOpacity(0.3);
                textFieldFind.setText(clientNew.toStringIteam());
                methodFindClientDataBase ();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("Ошибка при добавление нового клиента", e);

            }

            serviceTextClear.submit(() -> {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    labelSaveEdit.setOpacity(0);
                }
            });
        }
        } else {

            if (textFirtsName.getText().length() < 2 &&
                    textLastName.getText().length() < 2) {
            } else {
                try {

                    ActionClient.getClient().setFirstName(editText(textLastName.getText()));
                    ActionClient.getClient().setLastName(editText(textFirtsName.getText()));
                    ActionClient.getClient().setPatronymicName(editText(textPatronymicName.getText()));

                    ActionClient.getClient().setTelephone(checkText(textTelephone));
                    ActionClient.getClient().setDateBirth(checkText(textDateBirth));
                    ActionClient.getClient().setEmail(checkText(textEmail));
                    ActionClient.getClient().setInfoClient(checkText(textInfoClient));
                    ClientDataBase.editClientDateBase(ActionClient.getClient());
                    new DialogStageCustom(textInfoClient, "Успешное сохранение данных");
                    labelSaveEdit.setOpacity(1);
                    LOG.info("Редактор клиента: " + ActionClient.getClient().toString());
//                bot.sendMsgStart("Добавление нового клиента: " + clientNew.toString ());
                    clearText(textFirtsName);
                    clearText(textLastName);
                    clearText(textPatronymicName);
                    clearText(textTelephone);
                    clearText(textDateBirth);
                    clearText(textEmail);
                    clearText(textInfoClient);
//                    vBOXEditClient.setOpacity(0.3);
                    textFieldFind.setText(ActionClient.getClient().toStringIteam());
                    methodFindClientDataBase ();
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error("Ошибка! Редактор лиента", e);
                    labelSaveEdit.setStyle("-fx-text-fill: red;");
                    labelSaveEdit.setText("Ошибка !");
                    labelSaveEdit.setOpacity(1);
                }

                serviceTextClear.submit(() -> {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        labelSaveEdit.setOpacity(0);
                    }
                });
            }
        }


    }


    private String checkText(TextInputControl text) {
        if (text.getText ().length () > 0) {
            return text.getText ();
        }
        return "";
    }

//  Метод clear textField.
    private void clearText(TextInputControl text){
        text.setText ("");
    }


//  Метод преобразует текст Первый символ(Верхний регистр) последующие символы в нижний регистр.

    private String editText(String str){
        String newString = "";
        if (str.length () > 0) {
            String[] strings = str.split ("");
            for(int i = 0; i < strings.length; i++) {
                if (i == 0) {
                    strings[i] = strings[i].toUpperCase (Locale.ROOT);
                } else {
                    strings[i] = strings[i].toLowerCase (Locale.ROOT);
                }
                newString += strings[i];
            }
        }
        return newString;
    }

    private boolean findAction = false;
    private ExecutorService findClientThread = Executors.newFixedThreadPool (1);
    @FXML
    private TextField textFieldFind;

    List<ClientClass> clientClassListFind = new ArrayList<> ();

//    Метод поиска клиента
    public void methodFindClientDataBase (){

        ActionClient.setClient(null) ;
        actionEditClientData = false;

        if (!findAction
                && !textFieldFind.getText ().startsWith (" ")
                && textFieldFind.getText ().length () > 1){


            Platform.runLater (()->{
                textFieldFind.setStyle("fx-text-fill: black;");
            });

            findAction = true;

            clientClassList = ClientDataBase.newListClient();

            findClientThread.submit (()->{
                while (true){

                    String findString = textFieldFind.getText ();

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
                        for(ClientClass client: clientClassList
                            ) {
                            if (client.booleanFind (findString)){
                                clientClassListFind.add (client);
                            }

                        }

                        if (!clientClassListFind.isEmpty()){
                            menuReturnFind(clientClassListFind, lengthStr);
                        } else {
                            Platform.runLater (()->{
                                if (contextMenu != null){
                                    contextMenu.getItems ().clear ();
                                }

                                textFieldFind.setStyle("-fx-background-color: #FF6347;");
                                contextMenuAddNewUser(lengthStr);
                            });
                        }
                        findAction = false;
                        break;
                    }


                }
            });

        }





    }

    ContextMenu contextMenu;

    private void contextMenuAddNewUser(int lengthStr){
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
                    new NewClientStage(panelUserInterface, textFieldFind.getText());
                }
            });
                contextMenu.getItems ().add (item);


            Stage stage = (Stage) textFieldFind.getScene ().getWindow ();

            double x = stage.getX ();
            double y = stage.getY ();

            double nodeLength = (hBoxWorkClient.getWidth()) / 2;

            double xMenu = x + (nodeLength - (lengthStr * 5));
            double yMenu = y + 215;

            contextMenu.show (stage, xMenu,yMenu);

        });
    }

//   contextMenu  при поиске клиента;
    private void menuReturnFind(List<ClientClass> clientClassListFind, int lengthStr){

        Platform.runLater (()->{
            if (contextMenu != null){
                contextMenu.getItems ().clear ();
            }
            contextMenu = new ContextMenu ();
            textFieldFind.setContextMenu (contextMenu);
            contextMenu.setStyle("-fx-max-width: 670;");
            contextMenu.setStyle("-fx-min-width: 670;");
            contextMenu.setStyle("-fx-max-height: 300;");

            MenuItem[] items =  new MenuItem[clientClassListFind.size ()];

            for(int j = 0; j < clientClassListFind.size (); j++) {

                items[j] = new MenuItem (clientClassListFind.get (j).toStringIteam ());
                int finalJ = j;
                items[j].setOnAction (new EventHandler<ActionEvent> () {
                    @Override
                    public void handle (ActionEvent event){
                        ActionClient.setClient(clientClassListFind.get (finalJ));
//                        rutFindClient(ActionClient.getClient());
                        methodOrderService ();
                        findAction = false;
                    }
                });
                contextMenu.getItems ().add (items[j]);
            }

            Stage stage = (Stage) textFieldFind.getScene ().getWindow ();

            double x = stage.getX ();
            double y = stage.getY ();

            double w = stage.getWidth();
            double h = stage.getHeight();

            double ww = w / 2;
            double hh = h / 2;

            double nodeLength = (hBoxWorkClient.getWidth()) / 2;


            double xMenu = x + (nodeLength - (lengthStr * 5));
            double yMenu = y + 215;

            contextMenu.show (stage, xMenu,yMenu);

        });

    }

    private void argsPrint(Object... objects){
        for(Object o: objects
            ) {

            LOG.info (String.format("%s %s \n", o.getClass().getName(), o.toString()));
        }
    }



//    Запрет на редактирование TextField (args)
    private void textFieldEdit(boolean booleanEdit, TextArea textArea, TextField... textFields){
        textArea.setEditable(booleanEdit);
        for(TextField tf: textFields
            ) {
            tf.setEditable(booleanEdit);
        }
    }



//    Action nodes
    private void setVisibleEndManaged(boolean booleanVal, Node... nodes){
        for(Node n: nodes
            ) {
            n.setManaged(booleanVal);
            n.setVisible(booleanVal);
        }

    }

//    Прозрачность
    private void nodesOpacity(double valOpacity, Node... nodes){
        if (valOpacity < 0 || valOpacity > 1){
            throw new RuntimeException("Value setOpacity " + valOpacity);
        } else {
            for(Node n: nodes
            ) {
                n.setOpacity(valOpacity);
            }
        }
    }

    @FXML
    private Button buttonOrderService;
    @FXML
    private Button ButtonEditDateClient;
    @FXML
    private Button ButtonCancelEditDateClient;
    @FXML
    private Button btnSaveEdit;
    @FXML
    private Button ButtonDelClient;
    private boolean actionEditClientData = false;

//    Доступ к редактору клиента
    public void methodEditDateClient (ActionEvent actionEvent){
        if (ActionClient.getClient() != null){
            actionEditClientData = true;
        setVisibleEndManaged(true, btnSaveEdit, ButtonCancelEditDateClient);
        setVisibleEndManaged(false, ButtonEditDateClient, ButtonDelClient);
        textFieldEdit(true,
                textInfoClient,
                textFirtsName,
                textLastName,
                textPatronymicName,
                textTelephone,
                textDateBirth,
                textEmail);
            nodesOpacity(1, textFirtsName, textLastName, textPatronymicName, textTelephone, textDateBirth,
                         textInfoClient, textEmail);
            nodesOpacity(0.5, buttonOrderService);
        }
    }
    //    Отмена доступа к редактору клиента
    public void methodCancelEditDateClient (ActionEvent actionEvent){
        actionEditClientData = false;
        setVisibleEndManaged(false, btnSaveEdit, ButtonCancelEditDateClient);
        setVisibleEndManaged(true, ButtonEditDateClient, ButtonDelClient);
        textFieldEdit(false,
                textInfoClient,
                textFirtsName,
                textLastName,
                textPatronymicName,
                textTelephone,
                textDateBirth,
                textEmail);
        nodesOpacity(0.8, textFirtsName, textLastName, textPatronymicName, textTelephone, textDateBirth,
                     textInfoClient, textEmail);
        nodesOpacity(1, buttonOrderService);
    }

//    Доступ для добавления нового клиента
    public void openEditVboxNewClient (ActionEvent actionEvent){

        ActionClient.setClient(null);
        new NewClientStage(panelUserInterface);
        



    }

    public void onMouseClickedTextFieldFind (MouseEvent mouseEvent) {
        nodesOpacity(1, textFieldFind, buttonFindClient, buttonGuest, buttonListClients, buttonNewClient);
    }

    //    Оформление услуги
    public void methodOrderService () {

        if (ActionClient.getClient() != null) {
            if (!actionEditClientData){
                textFieldNameClient.setText (ActionClient.getClient ().toStringIteam ());
                textFieldBirthDay.setText (ActionClient.getClient ().getDateBirth ());
//            setVisibleEndManaged(false, vBOXEditClient);
            setVisibleEndManaged(false, vBoxFindClient);
            setVisibleEndManaged(true, vBoxOrderService);
            setVisibleEndManaged(false, vBoxListClients);

                ActionService.updateHBoxGUIInfo();

            }
        }
    }

    //    Отмена оформления новой услуги
    public void methodCancelOrderService () {
//        setVisibleEndManaged(true, vBOXEditClient);
        setVisibleEndManaged(true, vBoxFindClient);
        setVisibleEndManaged(false, vBoxOrderService);
        setVisibleEndManaged(false, vBoxListClients);
        vBoxActionService.getChildren().clear();
    }


    public void openListClients (ActionEvent actionEvent){
        updateListClients();
        setVisibleEndManaged(true, vBoxListClients);

    }


    public void updateListClients(){
        Platform.runLater(()->{

            clientClassList = ClientDataBase.newListClient();

            listViewListClients.getItems().clear();
            for(int i = 0; i < clientClassList.size(); i++) {
                listViewListClients.getItems().add(clientClassList.get(i).infoHBox ());
            }


        });
    }


    public void addFindClient (MouseEvent mouseEvent){

        if (mouseEvent.getClickCount() == 2){
            int selected = listViewListClients.getSelectionModel().getSelectedIndex ();
//            textFieldFind.setText(name);
//            methodFindClientDataBase();
            ActionClient.setClient (clientClassList.get (selected));
            methodOrderService ();
        }
    }

    public void methodExitUser(ActionEvent actionEvent) {

        AuthUserDateBase.editUserAuth(ActionUser.getUser ().getLogin (), false);
        Platform.runLater (()->{
            methodCancelOrderService();
            textFieldDateTameStart.clear();
            textFieldNameUser.clear();
            new DialogAuth (panelUserInterface, textFieldDateTameStart, textFieldNameUser);
        });

    }

    public void systemClose() {
        Stage stage = (Stage) panelUserInterface.getScene ().getWindow ();
        stage.close ();
        ServerMySQL.disconnect ();
        LOG.info ("Приложение закрыто.");
        System.exit (0);
    }

    public void initCashBookNull(ActionEvent actionEvent) {
        CashBook.clearCashBook();
         new WarningStage("Необходимо перезапустить приложение.");
    }

    public void restart(){

        LOG.info ("Рестарт приложения.");
        Stage stage = (Stage) panelUserInterface.getScene ().getWindow ();
        stage.close();
        Platform.runLater( () -> new GUIMainStage ().start( new Stage() ) );
        new InfoStage ("Рестарт приложения.");
    }

    public void methodEditUser (ActionEvent actionEvent) {
        Platform.runLater (()->{
            new DialogUserEdit (panelUserInterface, textFieldDateTameStart,textFieldNameUser);
        });
    }

    public void methodAddNewUser (ActionEvent actionEvent) {

        Platform.runLater (()->{
            new DialogAddNewUser (panelUserInterface);
        });
    }

    public void payNullClient (ActionEvent actionEvent) {

        ClientClass clientClass = new ClientClass ();
        clientClass.setId (0);
        clientClass.setLastName ("Анонимный клиент");
        ActionClient.setClient (clientClass);
        methodOrderService ();
    }

    public void initStageNewService (ActionEvent actionEvent) {
        new StageNewService(panelUserInterface);
    }

    public void infoStageClient (ActionEvent actionEvent) {
        if (ActionClient.getClient() != null){
            if (ActionClient.getClient().getId() > 0){
                new ClientStage(panelUserInterface, ActionClient.getClient());
            }
        }

    }

    public void closeListClients (ActionEvent actionEvent) {
        setVisibleEndManaged(false, vBoxListClients);
    }

    public void openDateControlService (ActionEvent actionEvent) {

        new DateControlService(panelUserInterface);
    }

    public void testTimeControl (ActionEvent actionEvent) {

        System.out.println("Контроль веремени: " + new DateTime().checkTime());
    }



}
