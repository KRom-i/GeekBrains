package WorkDataBase.User;

import Format.DateTime;
import GUIMain.CustomStage.ErrorStage;
import GUIMain.CustomStage.InfoStage;
import GUIMain.CustomStage.SystemErrorStage;
import WorkDataBase.AuthUserDateBase;
import WorkDataBase.UserSpartak;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DialogAddNewUser {


    private TextField nameField;
    private TextField loginField;
    private PasswordField passwordField;
    private Stage newWindow;
    private Stage mainStage;
    private ExecutorService service;


    public DialogAddNewUser(Node node){

        this.mainStage = (Stage) node.getScene().getWindow();
        StackPane stackPane = new StackPane();
//        stackPane.setStyle("-fx-background-radius: 5; " +
//                " -fx-border-radius: 5;");
//        stackPane.getChildren().add(new Label (textInfo));

        this.newWindow = new Stage();
        service = Executors.newFixedThreadPool (1);
        newWindow.setTitle("Добавить нового пользователя");

        Scene scene = new Scene(stackPane);
//        scene.setOnMouseClicked(new EventHandler<MouseEvent> () {
//            @Override
//            public void handle (MouseEvent event) {
//                newWindow.close();
//            }
//        });

        this.nameField = new TextField ();
        nameField.setPromptText ("Имя пользователя");
        nameField.setMinSize (300,60);
        nameField.setMaxWidth (300);

        this.loginField = new TextField ();
        loginField.setPromptText ("Логин");
        loginField.setMinSize (300,60);
        loginField.setMaxWidth (300);

        this.passwordField = new PasswordField ();
        passwordField.setPromptText ("Пароль");
        passwordField.setMinSize (300,60);
        passwordField.setMaxWidth (300);

        Button b1 = new Button ("Отмена");
        b1.setMinSize (300,60);
        b1.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                newWindow.close();
            }
        });
        Button b2 = new Button ("Сохранить");
        b2.setMinSize (300,60);

        b2.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                updateUser ();
            }
        });
        nameField.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
                updateUser();
            }


        });

        loginField.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
                updateUser();
            }
        });
        passwordField.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
                updateUser();
            }
        });



        VBox vBox = new VBox ();
        vBox.setAlignment (Pos.CENTER);
        vBox.setMinSize (360, 360);
        vBox.setSpacing (5);

        vBox.getChildren ().addAll (nameField, loginField, passwordField, b2, b1);

        stackPane.getChildren ().add (vBox);

        newWindow.setScene(scene);

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


    private void updateUser () {
        String name = nameField.getText ();
        String log = loginField.getText ();
        int pas = passwordField.getText ().hashCode ();

        if (name.length () > 2 && log.length () > 2 && pas > 2){
            new AuthUserDateBase ().addNewUsr(name, log, pas);
            newWindow.close ();
            new InfoStage ("Новый пользователь сохранен.");
        } else {
            new ErrorStage ("Ошибка формата.");
            Platform.runLater (()->{
                service.submit (()->{
                    if (name.length () <=  2){
                        nameField.setStyle("-fx-background-color: #FF6347");
                    }
                    if (log.length () <=  2){
                        loginField.setStyle("-fx-background-color: #FF6347");
                    }
                    if (pas == 0){
                        passwordField.setStyle("-fx-background-color: #FF6347");
                    }

                    try {
                        Thread.sleep (150);
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    new SystemErrorStage (e);
                    }
                    nameField.setStyle("-fx-text-fill: black");
                    loginField.setStyle("-fx-text-fill: black");
                    passwordField.setStyle("-fx-text-fill: black");
                });
            });
        }

    }
}
