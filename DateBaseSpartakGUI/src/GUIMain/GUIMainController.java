package GUIMain;

import Authorization.AuthorizationInit;
import WorkDataBase.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.List;
import java.util.Locale;


public class GUIMainController {

    private static Object lock = new Object ();
    @FXML
    private HBox hBoxFildClient2;
    @FXML
    private TextField textTelephone;
    @FXML
    private TextField textDateBirth;
    @FXML
    private TextField textEmail;
    @FXML
    private TextArea textInfoClient;
    @FXML
    private Button btncloseActiveUser;
    @FXML
    private VBox vBOXEditClient;
    @FXML
    private TextField textFirtsName;
    @FXML
    private TextField textLastName;
    @FXML
    private TextField textPatronymicName;
    @FXML
    private HBox hBoxFildClient;
    @FXML
    private HBox labelCoordinateWin;
    @FXML
    private VBox vBOX;
    @FXML
    private Button btnwinVisible;
    @FXML
    private Button btnwinSize;
    @FXML
    private Button btncloseMain;
    @FXML
    private TextField textFieldFind;

    private ContextMenu contextMenu = new ContextMenu ();

    private double x;
    private double y;

    private static List<ClientClass> clientList;

    public void initialize() {

        ConnectDateBase.connect ();

//        clientList = ClientDataBase.newListClient();
        clientList = MainTestListDataBase.clientClassAdd ();

    }

    public static void opacity(Node node, Boolean visible) {

        new Thread (() -> {

            synchronized (lock) {

                if (!visible) {

                    for (double i = 0.99; i > 0.2; ) {
                        i -= 0.1;
                        try {
                            Thread.sleep (10);
                        } catch (InterruptedException e) {
                            e.printStackTrace ();
                        }
                        node.setOpacity (i);
                    }

                    node.setManaged (visible);
                    node.setVisible (visible);

                } else {

                    node.setVisible (visible);
                    node.setManaged (visible);

                    for (double i = 0; i < 1; ) {
                        i += 0.1;
                        node.setOpacity (i);
                        try {
                            Thread.sleep (10);
                        } catch (InterruptedException e) {
                            e.printStackTrace ();
                        }

                    }

                }
            }
        }).start ();

    }


    public void winSize(ActionEvent actionEvent) {

        Stage stage = (Stage) btnwinSize.getScene ().getWindow ();

        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        double width = screenSize.getWidth ();
        double height = screenSize.getHeight ();


        if (stage.getHeight () + stage.getWidth () < width + height) {
            stage.setHeight (height);
            stage.setWidth (width);
            stage.setY (0);
            stage.setX (0);
            btnwinSize.setText ("М");
        } else {
            stage.setHeight (height - (height / 3));
            stage.setWidth (width - (width / 3));
            stage.setY (height / 6);
            stage.setX (width / 6);
            btnwinSize.setText ("Б");
        }


    }


    public void methodCoordinateWin(MouseEvent mouseEvent) {

        Stage stage = (Stage) labelCoordinateWin.getScene ().getWindow ();
        stage.setOpacity (0.5);
        x = stage.getX () - mouseEvent.getScreenX ();
        y = stage.getY () - mouseEvent.getScreenY ();

    }

    public void methodCoordinateWin_2(MouseEvent mouseEvent) {
        Stage stage = (Stage) labelCoordinateWin.getScene ().getWindow ();
        stage.setOpacity (1);
    }

    public void methodCoordinateWin_3(MouseEvent mouseEvent) {
        Stage stage = (Stage) labelCoordinateWin.getScene ().getWindow ();
        stage.setX (mouseEvent.getScreenX () + x);
        stage.setY (mouseEvent.getScreenY () + y);
    }

    public void closeMain(ActionEvent actionEvent) {

        ConnectDateBase.disconnect ();
        Stage stage = (Stage) btncloseMain.getScene ().getWindow ();
        stage.close ();
    }

    public void winVisible(ActionEvent actionEvent) {
        Stage stage = (Stage) btnwinVisible.getScene ().getWindow ();
        stage.setIconified (true);

    }

    public void methodFindClientKey(KeyEvent keyEvent) {


        textFieldFind.setOnKeyTyped (new EventHandler<KeyEvent> () {

            @Override
            public void handle(KeyEvent keyEvent) {

                TextInputControl control = (TextInputControl) keyEvent.getSource ();
                Point2D pos = control.getInputMethodRequests ().getTextLocation (0);
                contextMenu.setUserData (control);

                if (keyEvent.getCharacter ().hashCode () != 8 &&
                        keyEvent.getCharacter ().hashCode () != 27
                        && keyEvent.hashCode () != 605303057
                ) {

                    String find = textFieldFind.getText () + keyEvent.getCharacter ().toString ();

                    if (find.length () > 4) {

                        contextMenu.setOpacity (0.85);
                        contextMenu.show (control, pos.getX (), pos.getY ());

                        if (contextMenu.getItems ().size () > 0) {
                            contextMenu.getItems ().clear ();
                        }

                        String[] finds = find.split (" ");

                        for (int i = 0; i < clientList.size (); i += 2) {

                            int finalI = i;

                            boolean controlStr = false;

                            String[] client = clientList.get (finalI).stringsClient ();

                            for (String f : finds
                            ) {
                                for (String c : client
                                ) {
                                    if (c.toUpperCase ().startsWith (f.toUpperCase ())) {
                                        controlStr = true;
                                        break;
                                    }
                                }

                                if (controlStr) {
                                    break;
                                }
                            }

                            if (controlStr) {

                                MenuItem menuItem = new MenuItem (clientList.get (finalI).toString () + " "
                                        + clientList.get (finalI).getId ());

                                menuItem.setId (clientList.get (finalI).getId () + "");

                                menuItem.setOnAction (new EventHandler<ActionEvent> () {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        textFieldFind.setText (clientList.get (finalI).toString () + " " + menuItem.getId ());
                                        contextMenu.getItems ().clear ();
                                    }
                                });
                                contextMenu.getItems ().add (menuItem);

                            }
                        }
                    }
                } else {
                    contextMenu.getItems ().clear ();
                }

            }
        });
    }


    public void editNewClient(MouseEvent mouseEvent) {

        opacity (hBoxFildClient2, false);
        opacity (hBoxFildClient, false);
        opacity (vBOXEditClient, true);

        String strAdd = textFieldFind.getText ();

        TextField[] textFields = {textFirtsName, textLastName, textPatronymicName};

        if (strAdd.length () > 0) {
            String[] strings = strAdd.split (" ");

            for (int i = 0; i < strings.length && i < textFields.length; i++) {

                if (strings[i].length () > 1) {
                    textFields[i].setText (strings[i].substring (0, 1).toUpperCase ()
                            + strings[i].substring (1).toLowerCase ());
                } else {
                    textFields[i].setText (strings[i].toUpperCase (Locale.ROOT));
                }

            }
        }

    }

    public void downEditClientMenu(ActionEvent actionEvent) {

        opacity (hBoxFildClient2, true);
        opacity (hBoxFildClient, true);
        opacity (vBOXEditClient, false);

    }

    public void closeActiveUser(ActionEvent actionEvent) {

        AuthUserDateBase.editUserDateBase (GUIMainStage.activeUser.getLogin (), false);
        Stage stage = (Stage) btncloseActiveUser.getScene ().getWindow ();
        stage.setOpacity (0.5);
        new AuthorizationInit (stage, GUIMainStage.activeUser);

    }

    public void infiActiveUser(MouseEvent mouseEvent) {
//        btncloseActiveUser.setText("Закрыть сессию ("+ GUIMainStage.activeUser.getName() +")");

    }

    public void methodSaveEditClient(ActionEvent actionEvent) {


        if (textFirtsName.getText ().length () < 2 &&
                textLastName.getText ().length () < 2) {

            System.out.println ("Ошибка");


        } else {

            ClientClass clientNew = new ClientClass ();

            clientNew.setId (ClientDataBase.idUpDateBase (clientNew.nameClassDataBase ()));
            clientNew.setFirstName (checkText (textFirtsName));
            clientNew.setLastName (checkText (textLastName));
            clientNew.setPatronymicName (checkText (textPatronymicName));
            clientNew.setTelephone (checkText (textTelephone));
            clientNew.setDateBirth (checkText (textDateBirth));
            clientNew.setEmail (checkText (textEmail));
            clientNew.setInfoClient (checkText (textInfoClient));

            ClientDataBase.addNewClientDateBase (clientNew);
        }

    }

    private String checkText(TextInputControl text) {

        if (text.getText ().length () > 0) {
            return text.getText ();
        }
        return " ";
    }
}
