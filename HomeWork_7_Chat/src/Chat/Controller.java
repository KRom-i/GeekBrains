package Chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    public HBox dnPanel;
    @FXML
    public VBox upPanel;

    @FXML
    private TextField log;
    @FXML
    private PasswordField pass;

    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    @FXML
    private ListView listContacts;

    private boolean isAuth;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    private static final String ADDRESS = "localhost";
    public static final int PORT = 8002;

    public void setAuth(boolean auth){
        this.isAuth = auth;

        if(!auth){
            upPanel.setVisible(true);
            upPanel.setManaged(true);

            dnPanel.setVisible(false);
            dnPanel.setManaged(false);
        } else {
            upPanel.setVisible(false);
            upPanel.setManaged(false);

            dnPanel.setVisible(true);
            dnPanel.setManaged(true);
        }
    }


    public void chatExit(ActionEvent actionEvent) {
        if (socket != null) {
            try {
                out.writeUTF("/end");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Main.primaryStage.close();
        System.exit(0);
    }

    public void rollUp(ActionEvent actionEvent){
        Main.primaryStage.setIconified(true);
    }


    public void listCon(ActionEvent actionEvent) {

        if (listContacts.getPrefWidth()==0){
            textArea.setPrefWidth(400);
            listContacts.setPrefWidth(190);
        } else {
            listContacts.getItems().clear();
            textArea.setPrefWidth(590);
            listContacts.setPrefWidth(0);

        }

    }


    public void sendMsg() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void connect() {

        try {

            socket = new Socket(ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() ->{

                try {
                        while (true){
                            String str = in.readUTF();
                            if ("/auth-OK".equals(str)){
                                setAuth(true);
                                textArea.clear();
                                break;
                            } else {
                                authFalse();
                            }
                        }
                    while (true){
                        String str = in.readUTF();
                        if ("serveClosed".equals(str)){
                            break;
                        }
                        textArea.appendText(str + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setAuth(false);
                }

            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void singIn() {
        if (socket == null || socket.isClosed()){
            connect();
        }
        try {
            if (log.getText().length() > 0 && pass.getText().length() > 0){
                out.writeUTF("/auth " + log.getText() + " " + pass.getText());
                log.clear();
                pass.clear();
            } else {
                authFalse();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void authFalse(){
        log.setStyle("-fx-background-color: #800000");
        pass.setStyle("-fx-background-color: #800000");
    }

    public void colorBtn(MouseEvent mouseEvent) {
        log.setStyle("-fx-background-color: #696969");
        pass.setStyle("-fx-background-color: #696969");
    }

}
