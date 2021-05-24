package WorkDataBase.AuthUser;

import Cash.TypePaymentArray;
import Format.DateTime;
import GUIMain.CustomStage.SystemErrorStage;
import Logger.LOG;
import MySQLDB.ServerMySQL;
import Services.Service;
import WorkDataBase.ActionClient;
import WorkDataBase.ActionUser;
import WorkDataBase.AuthUserDateBase;
import WorkDataBase.ClientClass;
import WorkDataBase.UserSpartak;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DialogAuth {

    private TextField loginField;
    private PasswordField passwordField;
    private Stage newWindow;
    private Stage mainStage;
    private TextField nameUser;
    private TextField dateStart;
    private ExecutorService service;
    private ContextMenu contextMenu;
    private CheckBox checkBoxSavePass;

    public DialogAuth(Node node, TextField nameUser, TextField dateStart) {


        if (!authUser()){


            this.service = Executors.newFixedThreadPool (1);
            this.nameUser = nameUser;
            this.dateStart = dateStart;
            this.mainStage = (Stage) node.getScene().getWindow();

            mainStage.setOpacity (0.8);
            StackPane stackPane = new StackPane();

            contextMenu = new ContextMenu ();
            stackPane.setStyle("-fx-background-radius: 5; " +
                    " -fx-border-radius: 5;");
//        stackPane.getChildren().add(new Label (textInfo));

            this.newWindow = new Stage();

            newWindow.setTitle("Авторизация пользователя");

            Scene scene = new Scene(stackPane);

//        scene.setOnMouseClicked(new EventHandler<MouseEvent> () {
//            @Override
//            public void handle (MouseEvent event) {
//                newWindow.close();
//            }
//        });


            Label label = new Label ("Аторизация пользователя");
            this.loginField = new TextField ();
            loginField.setOnMouseClicked (new EventHandler<MouseEvent> () {
                @Override
                public void handle (MouseEvent event) {
                    menuLogins();
                }
            });
            loginField.setOnKeyPressed (new EventHandler<KeyEvent> () {
                @Override
                public void handle (KeyEvent event) {
                    menuLogins();
                }
            });
            loginField.setPromptText ("Логин");
            loginField.setMinSize (300,60);
            loginField.setMaxWidth (300);

            this.passwordField = new PasswordField ();
            passwordField.setPromptText ("Пароль");
            passwordField.setMinSize (300,60);
            passwordField.setMaxWidth (300);

            Button b1 = new Button ("Выход");
            b1.setMinSize (300,60);
            b1.setOnAction (new EventHandler<ActionEvent> () {
                @Override
                public void handle(ActionEvent event) {
                    newWindow.close();
                    mainStage.close ();
                    System.exit (0);
                }
            });
            Button b2 = new Button ("Авторизация");
            b2.setMinSize (300,60);

            b2.setOnAction (new EventHandler<ActionEvent> () {
                @Override
                public void handle(ActionEvent event) {
                    checkAuth();
                }
            });
            loginField.setOnAction (new EventHandler<ActionEvent> () {
                @Override
                public void handle (ActionEvent event) {
                    checkAuth();
                }
            });
            passwordField.setOnAction (new EventHandler<ActionEvent> () {
                @Override
                public void handle (ActionEvent event) {
                    checkAuth();
                }
            });


            VBox vBox = new VBox ();
            vBox.setAlignment (Pos.CENTER);
            vBox.setMinSize (350, 350);
            vBox.setSpacing (5);

            HBox hBoxSavePass = new HBox ();
            hBoxSavePass.setMinSize (300,20);
            hBoxSavePass.setMaxWidth (300);
            hBoxSavePass.setAlignment (Pos.CENTER_LEFT);
            checkBoxSavePass = new CheckBox ("Сохранить пароль");
            hBoxSavePass.getChildren ().add (checkBoxSavePass);



            vBox.getChildren ().addAll (label, loginField, passwordField, hBoxSavePass, b2, b1);

            stackPane.getChildren ().add (vBox);

//            String css = getClass().getResource("Styles/styleMy.css").toExternalForm();
//            stackPane.getStylesheets().add("file:/C:/Users/%d0%a0%d0%be%d0%bc%d0%b0%d0%bd/Documents/REPO_My/DateBaseSpartakGUI/out/production/DataBaseSpartakGUI/GUIMain/Styles/style.css");


            newWindow.setScene(scene);

            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);

            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(mainStage);

            newWindow.initStyle(StageStyle.UNDECORATED);

            // Set position of second window, related to primary window.

//        newWindow.setOpacity(0.7);
//        newWindow.setX(getXbottLeftStage(mainStage, scene) - 20);
//        newWindow.setY(getYbottLeftStage(mainStage, scene) - 20);
            newWindow.show();

        } else {
            UserSpartak user = AuthUserDateBase.checkUserAuthStart ();
            LOG.info  (user.toString ());
            ActionUser.setUser (user);
            nameUser.setText (user.getName ());
            DateTime d = new DateTime ();
            String s = d.currentDate () + " " + d.currentTime ();
            dateStart.setText (s);
        }
    }


    private void  checkAuth(){
        UserSpartak user;
        if ((user =  AuthUserDateBase.newUserAuth (loginField.getText (), passwordField.getText ().hashCode ())) != null){
            LOG.info  (user.toString ());
            ActionUser.setUser (user);
            AuthUserDateBase.editUserAuth (user.getLogin (), true);


            if (checkBoxSavePass.isSelected ()){
                new AuthUserDateBase ().setSavePassword (user.getId (), passwordField.getText ());
            } else {
                new AuthUserDateBase ().setSavePassword (user.getId (), null);
            }

            newWindow.close();
            mainStage.setOpacity (1);
            nameUser.setText (user.getName ());
            DateTime d = new DateTime ();
            String s = d.currentDate () + " " + d.currentTime ();
            dateStart.setText (s);

        } else {
            Platform.runLater (()->{
                service.submit (()->{
                    loginField.setStyle("-fx-background-color: #FF6347");
                    passwordField.setStyle("-fx-background-color: #FF6347");
                    try {
                        Thread.sleep (150);
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
new SystemErrorStage (e);
                    }
                    loginField.setStyle("-fx-text-fill: black");
                    passwordField.setStyle("-fx-text-fill: black");
                });
            });
        }
    }

    private boolean authUser(){
        ActionUser.setUser (AuthUserDateBase.checkUserAuthStart ());
        return ActionUser.getUser () != null;
    }


    private void menuLogins(){

        Platform.runLater (()->{

            contextMenu.getItems ().clear ();
            loginField.setContextMenu (contextMenu);
            contextMenu.setStyle("-fx-max-width: 300;");
            contextMenu.setStyle("-fx-max-height: 300;");


            List<UserSpartak> nicks = listUsersNickName ();
            MenuItem[] items =  new MenuItem[nicks.size ()];

            LOG.info (String.format ("Список всех пользователей[%S]", nicks.toString ()));

            for(int j = 0; j < nicks.size (); j++) {

                items[j] = new MenuItem (nicks.get (j).getLogin ());
                int finalJ = j;
                items[j].setOnAction (new EventHandler<ActionEvent> () {
                    @Override
                    public void handle (ActionEvent event){
                        loginField.setText (nicks.get (finalJ).getLogin ());
                        String savePass = nicks.get (finalJ).getSavePassword ();
                        if (savePass != null ){
                            passwordField.setText (savePass);
                            checkBoxSavePass.setSelected (true);
                        } else {
                            checkBoxSavePass.setSelected (false);
                            passwordField.clear ();
                        }
                    }
                });
                contextMenu.getItems ().add (items[j]);
            }

            Stage stage = (Stage) loginField.getScene ().getWindow ();

            double x = stage.getX ();
            double y = stage.getY ();

//            double w = stage.getWidth();
//            double h = stage.getHeight();
//
//            double ww = w / 2;
//            double hh = h / 2;


            double xMenu = x + 20;
            double yMenu = y + 90;

            contextMenu.show (stage, xMenu,yMenu);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
        });

    }


    //    Список пользователей
    public List<UserSpartak> listUsersNickName(){
        List<UserSpartak> users = new ArrayList<> ();

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM users;"
            );

            rs = statement.executeQuery();

            while (rs.next()){
                UserSpartak u = new UserSpartak ();
                u.setId (rs.getInt (1));
                u.setName (rs.getString (2));
                u.setLogin (rs.getString (3));
                u.setSavePassword (rs.getString (6));
                users.add (u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return users;

    }

}
