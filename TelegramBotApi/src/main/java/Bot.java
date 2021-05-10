import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private MyJFrame myJFrame = new MyJFrame();

    public static void main(String[] args) {


        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {

                         Bot bot = new Bot();
            telegramBotsApi.registerBot(bot);
            bot.sendMsgStart("Start server");



        }   catch (TelegramApiRequestException e) {
                           e.printStackTrace();
        }
    }


    public void sendMsgStart(String str){

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId("1623901137");
        sendMessage.setText(str);


        try {

            setButtons(sendMessage);
            sendMessage(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
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

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {

            if (message.getText().startsWith("C")){
                myJFrame.colorButton(message.getText().split(" ",2)[1]);
            } else {

            switch (message.getText()) {
                case "ВЫКЛЮЧИТЬ КОМПЬЮТЕР":
                    sendMsg(message, "ОК! Выключа....");
                    sendMsg(message, message.getMessageId().toString());
                    sendMsg(message, message.getChatId().toString());
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
                case "/test":
                    sendMsg(message, TestList.toStringList());
                    break;
                default:
                    sendMsg(message, "Необходимо выбрать комманду");
            }
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
        return "Loenovo_my_pc_bot";
    }

    public String getBotToken() {
        return "1732163427:AAH6hWwazraK-QixMXUmreIO5ZAIG_Dx-a4";
    }
}
