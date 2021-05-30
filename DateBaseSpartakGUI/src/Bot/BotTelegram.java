package Bot;

import Cash.Transaction;
import GUIMain.CustomStage.SystemErrorStage;
import Logger.LOG;
import MySQLDB.MySqlClass;
import MySQLDB.ServerMySQL;
import WorkDataBase.ClientClass;
import WorkDataBase.ClientDataBase;
import WorkDataBase.UserSpartak;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BotTelegram extends TelegramLongPollingBot {




    public void sendMsg(String text, String id){

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(id);
        sendMessage.setText(text);

        try {
            sendMessage(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPhoto(Message message){

        SendPhoto sendPhoto = new SendPhoto ();
        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setReplyToMessageId(message.getMessageId());
        File file = new File ("C:\\Users\\Роман\\Pictures\\Saved Pictures\\833963.jpg");
        sendPhoto.setNewPhoto (file);

        try {
            sendPhoto (sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace ();
            new SystemErrorStage (e);
        }
    }

    public void sendDoc(Message message, File file){

        SendDocument sendDocument = new SendDocument ();
        sendDocument.setChatId(message.getChatId().toString());
        sendDocument.setReplyToMessageId(message.getMessageId());
        sendDocument.setNewDocument (file);

        try {
            sendDocument (sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace ();
            new SystemErrorStage (e);
        }
    }

    public void sendMsg(Message message, String text) {
 
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setReplyToMessageId(message.getMessageId());

        sendMessage.setText(text);



        try {

//            setButtons(sendMessage);
            sendMessage(sendMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();

        if (message != null && message.hasText()) {

            String idInMsg = message.getChatId ().toString ();
            String inMsg = message.getText ();

            LOG.info  (String.format ("Сообщение от клиента [%s] [%s]", idInMsg, inMsg));

//            Взаимодействие с администратором Telegram
            if (!inMsg.startsWith ("/dev")){

            if (checkIdAdmin (idInMsg, "admin")) {

                 if (inMsg.equalsIgnoreCase ("Кассовая книга")) {
                    File[] f = new File ("FilesXLS\\In\\CashBook").listFiles ();
                    String[] nameFile = new String[f.length + 1];
                    for (int i = 0; i < f.length; i++) { nameFile[i] = f[i].getName (); }
                    nameFile[f.length] = "Главное меню";
                    setButtonsMsg (idInMsg, "Выбор файла", nameFile);

                } else if (inMsg.endsWith (".xls")) {

                     if (inMsg.startsWith ("Касс")) {
                         File file = new File ("FilesXLS\\In\\CashBook\\" + message.getText ());
                         if (file.exists ()) {
                             sendDoc (message, file);
                         }
                     }

                } else {

                    if (checkIdAdmin (idInMsg, "developer")){
                        setButtonsMsg (idInMsg, "Главное меню", "Кассовая книга", "Отчет", "/developer");
                    }  else {
                        setButtonsMsg (idInMsg, "Главное меню", "Кассовая книга", "Отчет");
                    }

                }

            } else {
                //                Взаимодействие с клиентом Telegram && Взаимодействие с тренером Telegram


            }

            } else {
                //                Взаимодействие с разработчиком Telegram
                if (checkIdAdmin (idInMsg, "developer")) {

                    if (inMsg.equalsIgnoreCase ("/developer")||
                            inMsg.equalsIgnoreCase ( "/dev Main menu") ){

                        setButtonsMsg(idInMsg, "Developer menu", "/dev@CMD", "Главное меню");

                    } else if (inMsg.equalsIgnoreCase ("/dev@CMD")){

                        setButtonsMsg(idInMsg, "Developer Choice CMD", "/dev Show tables","/dev@CMD 2","/dev@CMD 3","/dev@CMD 4", "/dev Main menu");

                    } else if (inMsg.equalsIgnoreCase ("/dev Show tables")) {
                        try {
//                            String[] list = new MySqlClass ().getShowTables ();
//                            for (String s: list
//                            ) {
//                                Thread.sleep (200);
//                                sendMsg (s, idInMsg);
//                            }
                            setButtonsMsg(idInMsg, new MySqlClass ().getShowTables () , "/dev Main menu");
                        } catch (Exception e){
                            e.printStackTrace ();
                        }
                        setButtonsMsg(idInMsg, "Show tables" , "/dev Main menu");
                    }


                   if (inMsg.startsWith ("/devSQL@")){
                       try {
                           String sql = inMsg.split ("@")[1];
                           LOG.info ("BOT telegram CMD SQL << " + sql);

                           if (sql.startsWith ("/devSQL@SELECT")) {

                               File file = new MySqlClass ().getTableFileXls (sql);
                               if (file.exists ()) {
                                   sendDoc (message, file);
                                   file.delete ();
                               }

                           } else {

                               if (new MySqlClass ().setInsertDelUpdate (sql)){
                                   setButtonsMsg(idInMsg, "Запрос выполнен" , "/dev Main menu");
                               } else {
                                   setButtonsMsg(idInMsg, "Ошибка запроса" , "/dev Main menu");
                               }
                           }
                       } catch (Exception e){
                           e.printStackTrace ();
                       }
                   } else if (inMsg.startsWith("/devShowTab@")){
                       try {
                           String tableName = inMsg.split ("@")[1];
                           File file = new MySqlClass ().getSelectAllTableXls (tableName);
                           if (file.exists ()) {
                               sendDoc (message, file);
                               file.delete ();
                           }
                       } catch (Exception e){
                           e.printStackTrace ();
                       }

                   }
                }
            }

        }


    }


    public void setButtonsMsg(String id, String textMsg, String... textButtons) {


        List<KeyboardButton> buttonList = new ArrayList<> ();
        for (String s: textButtons
             ) {
            buttonList.add (new KeyboardButton (s.toString ()));
        }

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();


//        keyboardFirstRow.addAll (buttons);
        for (int i = 0; i < buttonList.size (); i++) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add (buttonList.get (i));
            keyboardRowList.add(keyboardRow);
        }

//        keyboardFirstRow.add(new KeyboardButton("/setting"));

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        SendMessage sendMessage = new SendMessage ();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.enableMarkdown (true);
        sendMessage.setChatId (id);
        sendMessage.setText (textMsg);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace ();
        }
    }

    private void setInline(SendMessage sendMessage) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton =
                new InlineKeyboardButton().setText("Списать").setCallbackData ("Data");
        InlineKeyboardButton inlineKeyboardButton2 =
                new InlineKeyboardButton().setText("Зачислить").setCallbackData ("Data");

        buttons1.add(inlineKeyboardButton);
        buttons1.add(inlineKeyboardButton2);
        buttons.add(buttons1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);

        sendMessage.setReplyMarkup (markupKeyboard);



    }

    public String getBotUsername() {
        return "FC_Spartacus_bot";
    }

    public String getBotToken() {
        return "1705739217:AAFCTm3Qa_iadfFekhB5DnTK-j_aq-aEn2Q";
    }


    private boolean checkIdAdmin(String id, String checkStatus){

        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean check = false;
        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM telegram WHERE id_telegram = ? and status = ?;"
            );

            statement.setString (1, id);
            statement.setString (2, checkStatus);
            rs = statement.executeQuery();


            while (rs.next()){
                int idClient = rs.getInt ("id_client");
                String rsStatus = rs.getString ("status");
                check = rsStatus.equalsIgnoreCase (checkStatus);
                LOG.info (String.format (
                        ">>> TELEGRAM BOT <<< Результат запроса проверки прав [%s] телеграм id [%s] id_client [%s] результат [%s]",
                        checkStatus ,id, idClient+"", check +""));
            }



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }
        return check;
    }
}
