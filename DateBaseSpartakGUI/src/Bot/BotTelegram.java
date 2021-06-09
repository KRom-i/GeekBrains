package Bot;

import Cash.CashBook;
import Cash.Transaction;
import Format.DateTime;
import Format.Round;
import GUIMain.CustomStage.SystemErrorStage;
import Logger.LOG;
import MySQLDB.MySqlClass;
import MySQLDB.ServerMySQL;
import ReadConfFile.Config;
import Services.Service;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

//        LOG.info (String.format (">>> TELEGRAM BOT <<< update.toString [%s]", update.toString ()));

        boolean checkAuth = false;

        if (update.getCallbackQuery() != null) {
            LOG.info(String.format(">>> TELEGRAM BOT <<< update.getCallbackQuery().getData()[%s]", update.getCallbackQuery().getData()));
            String data = update.getCallbackQuery().getData();
            String idIn = update.getCallbackQuery().getFrom().getId().toString();
            String fullName = update.getCallbackQuery().getFrom().getLastName() + " " + update.getCallbackQuery().getFrom().getFirstName();
            String nickTelegram = update.getCallbackQuery().getFrom().getUserName();

            if (checkIdAdmin(idIn, "admin", false)) {
                checkAuth = true;
                if (data.startsWith("/CashBookTran")) {
                    try {

                        String[] s = data.split("=");

                        double sum = Double.valueOf(s[1]);
                        Transaction t = new Transaction();
                        t.setDateTransaction(new DateTime().currentDate());
                        t.setTimeTransaction(new DateTime().currentTime());
                        t.setIdTransaction(123);
                        t.setNameTransaction("IPA tran");
                        t.setIdUser(Integer.valueOf(idIn));
                        t.setNameUser(fullName);


                        if (s[0].equalsIgnoreCase("/CashBookTranNonCashWriteOf")) {
                            if (sum > getBalanceNonCash()) {
                                sendMsg(idIn, "Ошибка обработки запроса !\nНедостаточно средств.");
                            } else {
                                t.setSumNonCashConsumptionDay(sum);
                                CashBook.addTransactionToCashBook(t);
                                sendMsg(idIn, msgBalanсe());
                            }
                        } else if (s[0].equalsIgnoreCase("/CashBookTranNonCashEnroll")) {
                            t.setSumNonCashReceipt(sum);
                            CashBook.addTransactionToCashBook(t);
                            sendMsg(idIn, msgBalanсe());
                        } else if (s[0].equalsIgnoreCase("/CashBookTranNonCashEndValue")) {
                            if (sum < getBalanceNonCash()) {
                                t.setSumNonCashConsumptionDay(new Round().getDoubleValue(getBalanceNonCash() - sum));
                                CashBook.addTransactionToCashBook(t);
                                sendMsg(idIn, msgBalanсe());
                            } else if (sum > getBalanceNonCash()) {
                                t.setSumNonCashReceipt(new Round().getDoubleValue(sum - getBalanceNonCash()));
                                CashBook.addTransactionToCashBook(t);
                                sendMsg(idIn, msgBalanсe());
                            }

                        }

                    } catch (Exception e) {
                        sendMsg(idIn, "Ошибка выполнения операции.");
                        e.printStackTrace();
                    }
                }

            }

            try {
                if (data.startsWith("REG") && data.endsWith("OK")) {
                    checkAuth = true;
                    String[] strReg = data.split("-");
                    if (strReg.length == 4) {
                        LOG.info(String.format("Регистрацию idTelegram[%s] idКлиента[%s] кодРегистрации[%s] nickTelegram[%s] FullNameTelegram[%s] DataTimeReg[%s] ",
                                               idIn, strReg[1], strReg[2], nickTelegram, fullName, new DateTime().currentDate() + " " + new DateTime().currentTime()));

                        if (updateReg(idIn, strReg[1], strReg[2], nickTelegram, fullName, new DateTime().currentDate() + " " + new DateTime().currentTime())){
                            sendMsg(idIn, "Успешное завершение регистрации.");
                        } else {
                            sendMsg(idIn, "Ошибка регистрации.");
                        }
                    }
                }
            } catch (Exception e){
                sendMsg(idIn, "Ошибка регистрации.");
            }


        }

        Message message = update.getMessage();

        if (message != null && message.hasText()) {

            String idInMsg = message.getChatId ().toString ();
            String inMsg = message.getText ();

            LOG.info  (String.format ("Сообщение от клиента [%s] [%s]", idInMsg, inMsg));


//            Взаимодействие с администратором Telegram


            if (checkIdAdmin (idInMsg, "admin", false)) {
                checkAuth = true;
                 if (inMsg.equalsIgnoreCase ("Кассовая книга")) {

                     File[] f = new File ("FilesXLS\\In\\CashBook").listFiles ();
                     String[] nameFile = new String[f.length + 1];
                     for (int i = 0; i < f.length; i++) {
                         nameFile[i] = "Кассовая книга " +  f[i].getName () + " год";
                     }
                     nameFile[f.length] = "Главное меню";
                     setButtonsMsg (idInMsg, "Выбор файла", nameFile);

                 } else if (inMsg.startsWith ("Кассовая книга") && inMsg.endsWith("год")) {

                     String year = inMsg.split(" ")[2];
                     File[] f = new File ("FilesXLS\\In\\CashBook\\" + year).listFiles ();
                     String[] nameFile = new String[f.length + 1];
                     for (int i = 0; i < f.length; i++) {
                         nameFile[i] = f[i].getName ();
                     }
                     nameFile[f.length] = "Главное меню";
                     setButtonsMsg (idInMsg, "Выбор файла", nameFile);

                } else if (inMsg.endsWith (".xls")) {

                     if (inMsg.startsWith ("Касс")) {
                         File[] dir = new File ("FilesXLS\\In\\CashBook").listFiles ();
                         for(File d: dir
                             ) {
                            if (d.isDirectory()){
                                File[] f = d.listFiles ();
                                for(File file: f
                                    ) {
                                    if (file.getName().equalsIgnoreCase(inMsg)){
                                        sendDoc (message, file);
                                    }
                                }
                            }
                         }

                     }

                } else if (checkChars(inMsg)) {

                     try {
                         double sum = Double.valueOf(inMsg);
                         sum = new Round().getDoubleValue(sum);
                         sendMsg(idInMsg, msgBalanсe());
                         setInlineCashBook(idInMsg, sum);
                     } catch (NumberFormatException e){
                         sendMsg(idInMsg, "Ошибка ! Некорректная сумма. \nФормат ввода 0000.00 ");
                         e.printStackTrace();
                     }


                 } else if (inMsg.equalsIgnoreCase("Баланс кассы")){

                     setButtonsMsg (idInMsg, msgBalanсe() , "Главное меню");

                 }else {

                    if (checkIdAdmin (idInMsg, "developer", false)){
                        setButtonsMsg (idInMsg, "Главное меню", "Баланс кассы", "Кассовая книга", "Отчет", "/developer");
                    }  else {
                        setButtonsMsg (idInMsg, "Главное меню", "Баланс кассы", "Кассовая книга", "Отчет");
                    }

                }

            }

                //                Взаимодействие с клиентом Telegram && Взаимодействие с тренером Telegram
            if (checkIdAdmin (idInMsg, "client", false)) {
                checkAuth = true;
                sendMsg(idInMsg, "СТАТУС КЛИЕНТ ДОСПУПЕН");
            }



                //                Взаимодействие с разработчиком Telegram
                if (checkIdAdmin (idInMsg, "developer", false)) {
                    checkAuth = true;
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



//            Запрос на регистрацию
            if (inMsg.startsWith("REG") || inMsg.startsWith("Reg") || inMsg.startsWith("reg")){
                checkAuth = true;
                String[] strReg = inMsg.split("-");
                if (strReg.length == 3) {
                    LOG.info(String.format("Запрос на регистрацию idTelegram[%s] idКлиента[%s] кодРегистрации[%s]",
                                           idInMsg, strReg[1], strReg[2]));

                    int idClient = Integer.valueOf(strReg[1]);
                    if (checkReg(idClient, strReg[2], false)) {
                        String fullName = getNameDateBase(idClient);

                        if (fullName != null){
                            setRegLineButton(idInMsg,String.valueOf(idClient), strReg[2], fullName);
                        }
                    } else {
                        sendMsg(idInMsg, "Ошибка аутентификации.\nДля регистрации обратитесь к администратору ФЦ.");
                    }

                } else {
                    sendMsg(idInMsg, "Ошибка.\nФормат кода регистрации Reg-N-N.");
                }
            }

            if (inMsg.startsWith (getBotToken ())){
                checkAuth = true;
                try {
                    String[] strings = inMsg.split ("=");
                    if (strings[1].equalsIgnoreCase ("limit2301")){
                        if (strings[2].equalsIgnoreCase ("add")){
                            if (strings[3].equalsIgnoreCase("admin")){
                                addAdmin(idInMsg, "admin");
                            } else  if (strings[3].equalsIgnoreCase("developer")){
                                addAdmin(idInMsg, "developer");
                            }
                        } else  if (strings[2].equalsIgnoreCase ("delete")){
                            deleteAdmin(idInMsg);
                        }

                    }

                } catch (Exception e){
                    e.printStackTrace ();
                }
            }


            if (!checkAuth){
                sendMsg(idInMsg, "Ошибка аутентификации.\nДля регистрации обратитесь к администратору ФЦ.");
            }

        }





    }


    public void setButtonsMsg(String id, String textMsg, String... textButtons) {


        List<KeyboardButton> buttonList = new ArrayList<> ();
        for (String s: textButtons
             ) {
            KeyboardButton keyboardButton = new KeyboardButton (s.toString ());
            buttonList.add (keyboardButton);
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

    private void setInlineCashBook(String id, double sum) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();

        List<InlineKeyboardButton> buttons2 = new ArrayList<>();

        List<InlineKeyboardButton> buttons3 = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton =
                new InlineKeyboardButton().setText("Списать с р/c " + getDoubleToStringDelimit(sum)).setCallbackData ("/CashBookTranNonCashWriteOf=" + sum);


        InlineKeyboardButton inlineKeyboardButton2 =
                new InlineKeyboardButton().setText("Зачислить на р/c "+ getDoubleToStringDelimit(sum)).setCallbackData ("/CashBookTranNonCashEnroll=" + sum);

        InlineKeyboardButton inlineKeyboardButton3 =
                new InlineKeyboardButton().setText("Установить остаток р/c " + getDoubleToStringDelimit(sum)).setCallbackData ("/CashBookTranNonCashEndValue=" + sum);

        buttons1.add(inlineKeyboardButton);
        buttons2.add(inlineKeyboardButton2);
        buttons3.add(inlineKeyboardButton3);

        buttons.add(buttons1);
        buttons.add(buttons2);
        buttons.add(buttons3);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);

        SendMessage sendMessage = new SendMessage ();
        sendMessage.setReplyMarkup (markupKeyboard);
        sendMessage.enableMarkdown (true);
        sendMessage.setChatId (id);
        sendMessage.setText("Выбор опрерации");

        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace ();
        }
    }

    private void setRegLineButton(String idTelegram, String id, String cod, String name) {

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton =
                new InlineKeyboardButton().setText("Зарегистрироваться").setCallbackData ("REG-" + id +"-"+ cod +"-OK");

        buttons1.add(inlineKeyboardButton);
        buttons.add(buttons1);


        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);

        SendMessage sendMessage = new SendMessage ();
        sendMessage.setReplyMarkup (markupKeyboard);
        sendMessage.enableMarkdown (true);
        sendMessage.setChatId (idTelegram);
        sendMessage.setText("ФИО: " + name + "\nПодтверждение регистрации.");

        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace ();
        }
    }

    public String getBotUsername() {
        return new Config().getValueConf("BOT_USERNAME");
    }

    public String getBotToken() {
        return new Config().getValueConf("BOT_TOKEN");
    }


    private boolean checkIdAdmin(String id, String checkStatus, boolean delete){

        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean check = false;
        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM telegram WHERE id_telegram = ? and status = ? and deleteCheck = ?;"
            );

            statement.setString (1, id);
            statement.setString (2, checkStatus);
            statement.setBoolean (3, delete);
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


    private boolean checkReg(int idClient, String codReg, boolean delete){

        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean check = false;
        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM telegram WHERE codReg = ? and deleteCheck = ?;"
            );

            statement.setString (1, codReg);
            statement.setBoolean (2, delete);

            rs = statement.executeQuery();

            while (rs.next()){
                int idClientRs = rs.getInt ("id_client");
                if (idClientRs == idClient){
                    check = true;
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }
        return check;
    }


    private String getNameDateBase(int idClient){

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM clientlist WHERE id = ?;"
            );

            statement.setInt (1, idClient);
            rs = statement.executeQuery();

            if (rs.next()){
                return rs.getString(2) + " " + rs.getString(3);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return null;
    }


    public static boolean addNewCodeRegClient(int idClient, String code){

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "INSERT INTO telegram SET id_client  = ?, codReg = ?, status = ?, msg = ?, deleteCheck = ?;"
            );

//           Новое значение
            statement.setInt (1, idClient);
            statement.setString(2, code);
            statement.setString(3, "client");
            statement.setBoolean(4, true);
            statement.setBoolean(5, false);

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }

        return false;
    }

    public static boolean addAdmin(String idTelegram, String status){

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "INSERT INTO telegram SET id_telegram = ?, status = ?, msg = ?, deleteCheck = ?;"
            );

//           Новое значение
            statement.setString(1, idTelegram);
            statement.setString(2, status);
            statement.setBoolean(3, true);
            statement.setBoolean(4, false);

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }

        return false;
    }

    public static boolean deleteAdmin(String idTelegram){

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "delete from telegram where id_telegram = ?;"
            );

//           Новое значение
            statement.setString(1, idTelegram);

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }

        return false;
    }

    public static void deleteClient (int id) {

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement("UPDATE telegram SET deleteCheck = ?, codReg = ?" +
                                                                      " WHERE id_client = ? and status = ?;");

//           Новое значение
            statement.setBoolean(1, true);
            statement.setString(2, null);
            statement.setInt(3, id);
            statement.setString(4, "client");
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }


    }



    public boolean updateReg(String idTelegram, String idClient, String codReg, String nickTelegram,
                          String nameTelegram, String dateTimeReg){

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE telegram SET" +
                    " id_telegram  = ?, nickTelegram  = ?, nameTelegram  = ?, DateTimeReg = ?, codReg = ? WHERE id_client  = ? and codReg = ?;"
            );

//           Новое значение
            statement.setString (1, idTelegram);
            statement.setString(2, nickTelegram);
            statement.setString(3, nameTelegram);
            statement.setString(4, dateTimeReg);
            statement.setString(5, " ");

            statement.setInt(6, Integer.valueOf(idClient));
            statement.setString(7, codReg);

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }

        return false;
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



        return  String.format("Дата и время последней операции \n\n" +
                              " %s %s\n\n" +
                              "Текущий остаток\n\n" +
                              "Н а л и ч н ы е : \n" +
                              "%s  (руб)\n\n" +
                              "С ч е т : \n" +
                              "%s  (руб)\n\n" +
                              "И Т О Г О : \n"+
                              "%s  (руб)\n",date, time,
                              getDoubleToStringDelimit(cash),
                              getDoubleToStringDelimit(nonCash),
                              getDoubleToStringDelimit(all));

    }


    private double getBalanceNonCash(){
        return CashBook.getEndTransactionDataBase().getSumNonCashBalanceEnd();
    }


    private String getDoubleToStringDelimit(double d){
        String[] languages = { "en", "de", "ru" };
            Locale loc = new Locale(languages[2]);
            NumberFormat formatter = NumberFormat.getInstance(loc);

            boolean check = false;
        for(char c: formatter.format(d).toCharArray()
            ) {
            if (c == ','){
                check = true;
            }
        }

        if (check){
            return formatter.format(d);
        }


            return formatter.format(d) + ",0";

    }
}
