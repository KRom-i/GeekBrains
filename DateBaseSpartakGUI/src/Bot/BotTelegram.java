package Bot;

import Cash.CashBook;
import Cash.Transaction;
import Format.DateTime;
import Format.Round;
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




    public void sendMsg(String id, String text){

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

        LOG.info (String.format (">>> TELEGRAM BOT <<< update.toString [%s]", update.toString ()));
        if (update.getCallbackQuery() != null){
            LOG.info (String.format (">>> TELEGRAM BOT <<< update.getCallbackQuery().getData()[%s]", update.getCallbackQuery().getData()));
            String data = update.getCallbackQuery().getData();
            String idIn = update.getCallbackQuery().getFrom().getId().toString();
            String fullName = update.getCallbackQuery().getFrom().getLastName() + " " + update.getCallbackQuery().getFrom().getFirstName();

            if (checkIdAdmin (idIn, "admin")){
                if (data.startsWith("/CashBookTran")){
                    try {
                        String[] s = data.split("=");
                        double sum = Double.valueOf(s[1]);
                        Transaction t = new Transaction();
                        t.setDateTransaction(new DateTime().currentDate());
                        t.setTimeTransaction(new DateTime().currentTime());
                        t.setIdTransaction(123);
                        t.setIdUser(Integer.valueOf(idIn));
                        t.setNameUser(fullName);
                        t.setNameTransaction("IPA java Telegram");
                        if (s[0].equalsIgnoreCase("/CashBookTranNonCashWriteOf")) {
                            t.setSumNonCashConsumptionDay(sum);
                            CashBook.addTransactionToCashBook(t);
                            sendMsg(idIn, msgBalanсe());
                        } else if (s[0].equalsIgnoreCase("/CashBookTranNonCashEnroll")) {
                            t.setSumNonCashReceipt(sum);
                            CashBook.addTransactionToCashBook(t);
                            sendMsg(idIn, msgBalanсe());
                        }

                    } catch (Exception e){
                        sendMsg(idIn, "Ошибка выполнения операции.");
                        e.printStackTrace();
                    }
                }

            }
        }

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

                } else if (checkChars(inMsg)) {

                     try {
                         double sum = Double.valueOf(inMsg);
                         sum = new Round().getDoubleValue(sum);
                         sendMsg(idInMsg, msgBalanсe());
                         setInlineCashBook(idInMsg, String.format("Сумма безн. операции %s руб.", sum + ""), sum);
                     } catch (NumberFormatException e){
                         sendMsg(idInMsg, "Ошибка ! Некорректная сумма. \nФормат ввода 0000.00 ");
                         e.printStackTrace();
                     }


                 } else if (inMsg.equalsIgnoreCase("Баланс кассы")){

                     setButtonsMsg (idInMsg, msgBalanсe() , "Главное меню");

                 }else {

                    if (checkIdAdmin (idInMsg, "developer")){
                        setButtonsMsg (idInMsg, "Главное меню", "Баланс кассы", "Кассовая книга", "Отчет", "/developer");
                    }  else {
                        setButtonsMsg (idInMsg, "Главное меню", "Баланс кассы", "Кассовая книга", "Отчет");
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

                        setButtonsMsg(idInMsg, "Developer Choice CMD", "/dev Show tables","/devTestDataLine@","/dev@CMD 3","/dev@CMD 4", "/dev Main menu");

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
                    } if (inMsg.equalsIgnoreCase ("/devTestDataLine@")){

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

    private void setInlineCashBook(String id, String textMsg, double sum) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton =
                new InlineKeyboardButton().setText("Списать").setCallbackData ("/CashBookTranNonCashWriteOf=" + sum);
        InlineKeyboardButton inlineKeyboardButton2 =
                new InlineKeyboardButton().setText("Зачислить").setCallbackData ("/CashBookTranNonCashEnroll=" + sum);

        buttons1.add(inlineKeyboardButton);
        buttons1.add(inlineKeyboardButton2);
        buttons.add(buttons1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);

        SendMessage sendMessage = new SendMessage ();
        sendMessage.setReplyMarkup (markupKeyboard);
        sendMessage.enableMarkdown (true);
        sendMessage.setChatId (id);
        sendMessage.setText (textMsg);

        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace ();
        }
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


    private boolean checkChars(String str){
        String[] s1 = {"0", "1","2","3","4","5","6","7","8", "9",};

        for(String s: s1
            ) {
            if (str.startsWith(s)){
                return true;
            }
        }

        return false;
    }


    private String msgBalanсe(){

        Transaction t = CashBook.getEndTransactionDataBase();
        double cash = t.getSumCashBalanceEnd();
        double nonCash = t.getSumNonCashBalanceEnd();
        double all = t.getSumAllBalanceEnd();

        String date = t.getDateTransaction();
        String time = t.getTimeTransaction();

        return  String.format("[%s] [%s]\n" +
                              "Дата и время последней операции \n\n" +
                              "Текущий остаток:\n" +
                              "Нал [%s] руб.\n" +
                              "Счт [%s] руб.\n" +
                              "Общ [%s] руб.\n",date, time, cash, nonCash, all);

    }
}
