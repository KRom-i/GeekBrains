package Bot;

import WorkDataBase.ClientClass;
import WorkDataBase.ClientDataBase;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BotTelegram extends TelegramLongPollingBot {



    public void sendMsgStart(String str){

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId("1623901137");
        sendMessage.setText(str);


        try {

            setButtons(sendMessage);
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
        }
    }

    public void sendDoc(Message message){

        SendDocument sendDocument = new SendDocument ();
        sendDocument.setChatId(message.getChatId().toString());
        sendDocument.setReplyToMessageId(message.getMessageId());
        File file = new File ("C:\\Users\\Роман\\Documents\\REPO_My\\DateBaseSpartakGUI\\logs.log");
        sendDocument.setNewDocument (file);

        try {
            sendDocument (sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace ();
        }
    }

    public void sendMsg(Message message, String text) {
 
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setReplyToMessageId(message.getMessageId());

        sendMessage.setText(text);



        try {

            setButtons(sendMessage);
            sendMessage(sendMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        if (message != null && message.hasText()) {

            switch (message.getText()) {
                case "ВЫКЛЮЧИТЬ КОМПЬЮТЕР":
                    sendMsg(message, "ОК! Выключа....");
                    break;
                case "Привет":
                    sendMsg(message, "Привет, я Lenovo!");
                    break;
                case "/cmd":
                    sendMsg(message, "Комманда!");
                    break;
                case "/cmd admin":
                    sendMsg(message, "Привет, Админ!");
                    break;
                case "/clients":
                    List<ClientClass> clientClasses = ClientDataBase.newListClient ();
                    String msg = "";
                    for(int c = 0; c < clientClasses.size (); c++) {
                        msg += clientClasses.get (c).toString () + "\n";
                    }
                    sendMsg(message, msg);
                    break;
                case "/doc":
                    sendDoc(message);
                    break;
                case "/photo":
                    sendPhoto (message);
                    break;
                default:
                    sendMsg(message, "Необходимо выбрать комманду");
            }

        }

    }


    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("ВЫКЛЮЧИТЬ КОМПЬЮТЕР"));
//        keyboardFirstRow.add(new KeyboardButton("/setting"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    public String getBotUsername() {
        return "FC_Spartacus_bot";
    }

    public String getBotToken() {
        return "1705739217:AAFCTm3Qa_iadfFekhB5DnTK-j_aq-aEn2Q";
    }
}
