package GUIMain;

import Bot.BotTelegram;
import Logger.LOG;
import MySQLDB.ServerMySQL;
import WorkDataBase.*;
import com.sun.javafx.scene.control.skin.TextFieldSkin;
import com.sun.javafx.scene.text.HitInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import СustomTitlePanel.ChoiceTitlePanel;
import СustomTitlePanel.EditServicesTitlePanel;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GUIMainController {

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
    private  VBox vBoxChoiceService;
    @FXML
    private ListView<String> listViewListClients;
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
    private List<ClientClass> clientClassList;
    private ClientClass clientClassActive;

    public void initialize() {

        ServerMySQL.getConnection ();
        clientClassList = ClientDataBase.newListClient();

        textFieldEdit(false,
                textInfoClient,
                textFirtsName,
                textLastName,
                textPatronymicName,
                textTelephone,
                textDateBirth,
                textEmail);

        new ChoiceTitlePanel(vBoxChoiceService, 5);
        new EditServicesTitlePanel(vBoxEditServices, 5);



//        initBot ();

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


    private TelegramBotsApi telegramBotsApi;
    private BotTelegram bot;

    public void initBot() {

        ApiContextInitializer.init();
        telegramBotsApi = new TelegramBotsApi();

        try {
            bot = new BotTelegram();
            telegramBotsApi.registerBot(bot);
            bot.sendMsgStart("Start server");
        }   catch (Exception e) {
            e.printStackTrace();
        }

    }



    private ExecutorService serviceTextClear = Executors.newFixedThreadPool (1);

//    Редактирование или добавление нового клиента
    public void methodSaveEditClient(ActionEvent actionEvent) {
        labelSaveEdit.setText ("");
        labelSaveEdit.setOpacity (0);

        if (clientClassActive == null){
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
                new DialogStageCustom(textInfoClient, "Успешное сохранение данных");
                labelSaveEdit.setOpacity(1);
                LOG.info("Добавление нового клиента: " + clientNew.toString());
//                bot.sendMsgStart("Добавление нового клиента: " + clientNew.toString ());
                clearText(textFirtsName);
                clearText(textLastName);
                clearText(textPatronymicName);
                clearText(textTelephone);
                clearText(textDateBirth);
                clearText(textEmail);
                clearText(textInfoClient);
                vBOXEditClient.setOpacity(0.3);
                textFieldFind.setText(clientNew.toStringIteam());
                methodFindClientDataBase ();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("Ошибка при добавление нового клиента", e);
                labelSaveEdit.setStyle("-fx-text-fill: red;");
                labelSaveEdit.setText("Ошибка при добавление нового клиента");
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
        } else {

            if (textFirtsName.getText().length() < 2 &&
                    textLastName.getText().length() < 2) {
            } else {
                try {

                    clientClassActive.setFirstName(editText(textLastName.getText()));
                    clientClassActive.setLastName(editText(textFirtsName.getText()));
                    clientClassActive.setPatronymicName(editText(textPatronymicName.getText()));

                    clientClassActive.setTelephone(checkText(textTelephone));
                    clientClassActive.setDateBirth(checkText(textDateBirth));
                    clientClassActive.setEmail(checkText(textEmail));
                    clientClassActive.setInfoClient(checkText(textInfoClient));
                    ClientDataBase.editClientDateBase(clientClassActive);
                    new DialogStageCustom(textInfoClient, "Успешное сохранение данных");
                    labelSaveEdit.setOpacity(1);
                    LOG.info("Редактор клиента: " + clientClassActive.toString());
//                bot.sendMsgStart("Добавление нового клиента: " + clientNew.toString ());
                    clearText(textFirtsName);
                    clearText(textLastName);
                    clearText(textPatronymicName);
                    clearText(textTelephone);
                    clearText(textDateBirth);
                    clearText(textEmail);
                    clearText(textInfoClient);
                    vBOXEditClient.setOpacity(0.3);
                    textFieldFind.setText(clientClassActive.toStringIteam());
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

        clientClassList = ClientDataBase.newListClient();
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

        clientClassActive = null;
        actionEditClientData = false;
        textFieldEdit(false,
                textInfoClient,
                textFirtsName,
                textLastName,
                textPatronymicName,
                textTelephone,
                textDateBirth,
                textEmail);
        vBOXEditClient.setOpacity(0.3);
        clearTextFields(
                textFirtsName,
                textLastName,
                textPatronymicName,
                textTelephone,
                textDateBirth,
                textEmail);
        textInfoClient.clear();
        setVisibleEndManaged(false, btnSaveEdit, ButtonCancelEditDateClient, buttonOrderService);
        setVisibleEndManaged(true, ButtonEditDateClient, ButtonDelClient);
        nodesOpacity(1, textFieldFind, buttonFindClient, buttonGuest, buttonListClients, buttonNewClient);
        if (!findAction
                && !textFieldFind.getText ().startsWith (" ")
                && textFieldFind.getText ().length () > 1){


            Platform.runLater (()->{
                textFieldFind.setStyle("fx-text-fill: black;");
            });

            findAction = true;
            findClientThread.submit (()->{
                while (true){

                    String findString = textFieldFind.getText ();

                    clientClassListFind.clear ();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (textFieldFind.getText ().startsWith (" ")
                            || textFieldFind.getText ().length () <= 2){
                        Platform.runLater (()->{
                            contextMenu.getItems ().clear ();
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
                            menuReturnFind(clientClassListFind);
                        } else {
                            Platform.runLater (()->{
                                contextMenu.getItems ().clear ();
                                textFieldFind.setStyle("-fx-background-color: #FF6347;");
                            });
                        }
                        findAction = false;
                        break;
                    }


                }
            });

        }





    }

    ContextMenu contextMenu = new ContextMenu ();

//   contextMenu  при поиске клиента;
    private void menuReturnFind(List<ClientClass> clientClassListFind){

        Platform.runLater (()->{

            contextMenu.getItems ().clear ();
            textFieldFind.setContextMenu (contextMenu);
            contextMenu.setStyle("-fx-max-width: 300;");
            contextMenu.setStyle("-fx-max-height: 300;");

            MenuItem[] items =  new MenuItem[clientClassListFind.size ()];

            for(int j = 0; j < clientClassListFind.size (); j++) {

                items[j] = new MenuItem (clientClassListFind.get (j).toStringIteam ());
                int finalJ = j;
                items[j].setOnAction (new EventHandler<ActionEvent> () {
                    @Override
                    public void handle (ActionEvent event){
                        clientClassActive = clientClassListFind.get (finalJ);
                        rutFindClient(clientClassActive);
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

            double nodeLength = (textFieldFind.getWidth() + buttonFindClient.getWidth()) / 2;

            double xMenu = x + ww - nodeLength;
            double yMenu = y + 180;

            contextMenu.show (stage, xMenu,yMenu);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
        });

    }

    private void argsPrint(Object... objects){
        for(Object o: objects
            ) {

            System.out.printf("%s %s \n", o.getClass().getName(), o.toString());
        }
    }


//    Выводим найденного клиента для дальнейшего взаимодействия
    private void rutFindClient(ClientClass clientClass){

        textFieldFind.setText("");
        actionEditClientData = false;
        textLastName.setText(clientClass.getFirstName());
        textFirtsName.setText(clientClass.getLastName());
        textPatronymicName.setText(clientClass.getPatronymicName());
        textTelephone.setText(clientClass.getTelephone());
        textDateBirth.setText(clientClass.getDateBirth());
        textEmail.setText(clientClass.getEmail());
        textInfoClient.setText(clientClass.getInfoClient());

        textFieldEdit(false,
                textInfoClient,
                textFirtsName,
                textLastName,
                textPatronymicName,
                textTelephone,
                textDateBirth,
                textEmail);
        setVisibleEndManaged(false, btnSaveEdit, ButtonCancelEditDateClient);
        setVisibleEndManaged(true, ButtonEditDateClient, ButtonDelClient, buttonOrderService, vBOXEditClient);
        vBOXEditClient.setOpacity(1);
        setVisibleEndManaged(false, listViewListClients);
        nodesOpacity(0.5, textFieldFind, buttonFindClient, buttonGuest, buttonListClients, buttonNewClient);
        nodesOpacity(0.8, textFirtsName, textLastName, textPatronymicName, textTelephone, textDateBirth,
                     textInfoClient, textEmail);
        nodesOpacity(1, buttonOrderService);
//        vBoxFindClient.setManaged(false);
//        vBoxFindClient.setVisible(false);
    }

//    Запрет на редактирование TextField (args)
    private void textFieldEdit(boolean booleanEdit, TextArea textArea, TextField... textFields){
        textArea.setEditable(booleanEdit);
        for(TextField tf: textFields
            ) {
            tf.setEditable(booleanEdit);
        }
    }

//    Clear TextFields

    private void clearTextFields(TextField... textFields){

        for(TextField tf: textFields
        ) {
            tf.setText("");
        }
    }

//    Метод улаляет клиента
    public void methodDelClient (ActionEvent actionEvent){

        if (clientClassActive != null){
            ClientDataBase.delClient(clientClassActive);
            clientClassActive = null;
            clearTextFields(
                    textFirtsName,
                    textLastName,
                    textPatronymicName,
                    textTelephone,
                    textDateBirth,
                    textEmail);
            textInfoClient.setText("");
            clientClassList = ClientDataBase.newListClient();
            vBOXEditClient.setOpacity(0.3);
        }
        setVisibleEndManaged(false, btnSaveEdit, ButtonCancelEditDateClient);
        setVisibleEndManaged(true, ButtonEditDateClient, ButtonDelClient);
        setVisibleEndManaged(false, listViewListClients);
        methodFindClientDataBase();

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
        if (clientClassActive != null){
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

        clientClassActive = null;
        clearTextFields(
                textFirtsName,
                textLastName,
                textPatronymicName,
                textTelephone,
                textDateBirth,
                textEmail);
        textInfoClient.clear();
        if (textFieldFind.getText().length() > 0){
            String[] s = textFieldFind.getText().split(" ",3);
            for(int i = 0; i < s.length; i++) {
                if (i == 0){
                    textFirtsName.setText(editText(s[i]));
                } else if (i == 1){
                    textLastName.setText(editText(s[i]));
                } else if (i == 2){
                    textPatronymicName.setText(editText(s[i]));
                }
            }
            textFieldFind.clear();
        }
        setVisibleEndManaged(true, btnSaveEdit, vBOXEditClient);
        setVisibleEndManaged(false, ButtonEditDateClient, ButtonDelClient, buttonOrderService, listViewListClients);
        textFieldEdit(true,
                textInfoClient,
                textFirtsName,
                textLastName,
                textPatronymicName,
                textTelephone,
                textDateBirth,
                textEmail);
        vBOXEditClient.setOpacity(1);
        nodesOpacity(0.5, textFieldFind, buttonFindClient, buttonGuest, buttonListClients);
        nodesOpacity(1, textFirtsName, textLastName, textPatronymicName, textTelephone, textDateBirth,
                     textInfoClient, textEmail);


    }

    public void onMouseClickedTextFieldFind (MouseEvent mouseEvent) {
        nodesOpacity(1, textFieldFind, buttonFindClient, buttonGuest, buttonListClients, buttonNewClient);
    }

    //    Оформление услуги
    public void methodOrderService (ActionEvent actionEvent) {
        if (clientClassActive != null) {
            if (!actionEditClientData){
            setVisibleEndManaged(false, vBOXEditClient);
            setVisibleEndManaged(false, vBoxFindClient);
            setVisibleEndManaged(true, vBoxOrderService);
            setVisibleEndManaged(false, listViewListClients);
            }
        }
    }

    //    Отмена оформления новой услуги
    public void methodCancelOrderService (ActionEvent actionEvent) {
        setVisibleEndManaged(true, vBOXEditClient);
        setVisibleEndManaged(true, vBoxFindClient);
        setVisibleEndManaged(false, vBoxOrderService);
        setVisibleEndManaged(false, listViewListClients);
    }


    public void openListClients (ActionEvent actionEvent){
        setVisibleEndManaged(true, listViewListClients);
        setVisibleEndManaged(false, vBOXEditClient, buttonOrderService);
        setVisibleEndManaged(false, vBoxOrderService);
        nodesOpacity(1, buttonListClients);
        nodesOpacity(0.5, textFieldFind, buttonFindClient, buttonGuest, buttonNewClient);
        Platform.runLater(()->{
            listViewListClients.getItems().clear();
            for(int i = 0; i < clientClassList.size(); i++) {
                listViewListClients.getItems().add(clientClassList.get(i).toStringIteam());
            }
        });
    }

    public void addFindClient (MouseEvent mouseEvent){

        if (mouseEvent.getClickCount() == 2){
            String name = listViewListClients.getSelectionModel().getSelectedItem();
            textFieldFind.setText(name);
            methodFindClientDataBase();
        }
    }


}
