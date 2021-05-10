package GUIMain;

import Bot.BotTelegram;
import Logger.LOG;
import WorkDataBase.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GUIMainController {

    private static Object lock = new Object ();

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


    public void initialize() {
        ConnectDateBase.connect ();
        initBot ();

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

        if (textFirtsName.getText ().length () < 2 &&
                textLastName.getText ().length () < 2) {
        } else {
            try{
                ClientClass clientNew = new ClientClass ();

                clientNew.setId (ClientDataBase.idUpDateBase (clientNew.nameClassDataBase ()));

                clientNew.setFirstName (editText (textFirtsName.getText ()));
                clientNew.setLastName (editText (textLastName.getText ()));
                clientNew.setPatronymicName (editText (textPatronymicName.getText ()));

                clientNew.setTelephone (checkText (textTelephone));
                clientNew.setDateBirth (checkText (textDateBirth));
                clientNew.setEmail (checkText (textEmail));
                clientNew.setInfoClient (checkText (textInfoClient));
                ClientDataBase.addNewClientDateBase (clientNew);
                labelSaveEdit.setText ("Данные сохранены");
                labelSaveEdit.setStyle ("-fx-text-fill: green;");
                labelSaveEdit.setOpacity (1);
                LOG.info ("Добавление нового клиента: " + clientNew.toString ());
                bot.sendMsgStart("Добавление нового клиента: " + clientNew.toString ());
                clearText(textFirtsName);
                clearText(textLastName);
                clearText(textPatronymicName);
                clearText(textTelephone);
                clearText(textDateBirth);
                clearText(textEmail);
                clearText(textInfoClient);
            } catch (Exception e){
                LOG.error ("Ошибка при добавление нового клиента", e);
                labelSaveEdit.setStyle ("-fx-text-fill: red;");
                labelSaveEdit.setText ("Ошибка при добавление нового клиента");
                labelSaveEdit.setOpacity (1);
            }

            serviceTextClear.submit (()->{
                try {
                    Thread.sleep (1500);
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                } finally {
                    labelSaveEdit.setOpacity (0);
                }
            });

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

//    Метод поиска клиента
    public void methodFindClientDataBase (){

        if (!findAction){
            findAction = true;
            findClientThread.submit (()->{
                while (true){
                    String findString = textFieldFind.getText ();
                    Thread.sleep (500);
                    if (findString.equalsIgnoreCase (textFieldFind.getText ())){
                        System.out.println (findString);
                    }
                }
            });
        }

    }
}
