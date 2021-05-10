package Authorization;

import GUIMain.GUIMainController;
import WorkDataBase.AuthUserDateBase;
import WorkDataBase.ConnectDateBase;
import WorkDataBase.UserSpartak;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.text.Style;

public class AuthorizationController {

    @FXML
    private Button btnCloseAuth;
    @FXML
    private Button btnAuthCheck;
    @FXML
    private PasswordField textPass;
    @FXML
    private TextField textLogin;

    private String styleDefault;


    public void methodAuthExit(ActionEvent actionEvent) {

        ConnectDateBase.disconnect();

        Stage stage = (Stage) btnCloseAuth.getScene().getWindow();
        stage.close();

        AuthorizationInit.stageMain.close();

    }


    public void methodCheckAuth(ActionEvent actionEvent) {

        if (!textLng(textLogin)){


                nodeTextCheckFalse(textLogin);


        } else if (!textLng(textPass)){


                nodeTextCheckFalse(textPass);


        } else {


            if ((AuthorizationInit.activeUser = AuthUserDateBase.newUserAuth(
                    textLogin.getText(), textPass.getText().hashCode())) != null){

//                nodeTextCheckTrue(textLogin);
//                nodeTextCheckTrue(textPass);

                AuthUserDateBase.editUserDateBase(AuthorizationInit.activeUser.getLogin(), true);

                    Stage stage = (Stage) btnAuthCheck.getScene().getWindow();
                    stage.close();

                    AuthorizationInit.stageMain.setOpacity(1);


            } else {

                    nodeTextCheckFalse(textLogin);
                    nodeTextCheckFalse(textPass);

            }

        }

    }

    public void nodeTextCheckFalse(TextField textField){

        textField.setText("");
        styleDefault = textField.getStyle();
        textField.setStyle("-fx-background-color: #800000;");
        textField.setFocusTraversable(false);

    }

    public static void nodeTextCheckTrue(TextField textField){
        textField.setStyle("-fx-background-color: green;");
        textField.setFocusTraversable(false);
    }

    public static boolean textLng(TextField textField){
        return textField.getText().length() > 0;
    }

    public void defaltColorLog(MouseEvent mouseEvent) {

//        if (styleDefault != null){
//                textLogin.setStyle(styleDefault);
//            }

        System.out.println(textLogin.getStyle());
        System.out.println(btnCloseAuth.getStyle());
    }

    public void defaltColorPass(MouseEvent mouseEvent) {

        if (styleDefault != null){
                textPass.setStyle(styleDefault);
            }

    }
}
