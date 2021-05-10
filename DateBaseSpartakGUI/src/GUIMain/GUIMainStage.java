package GUIMain;

import Authorization.AuthorizationInit;
import WorkDataBase.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class GUIMainStage extends Application {

    public static UserSpartak activeUser;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("GUIMain.fxml"));

        Scene scene = new Scene(root);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        primaryStage.setHeight(height - (height / 3));
        primaryStage.setWidth(width - (width / 3));
        primaryStage.setY(height / 6);
        primaryStage.setX(width / 6);

        primaryStage.initStyle(StageStyle.UNDECORATED);
//        String css = this.getClass().getResource("/styles/style.css").toExternalForm();
//        scene.getStylesheets().add(css);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        if ((activeUser = AuthUserDateBase.checkUserAuthStart()) == null){
            primaryStage.setOpacity(0.5);
            new AuthorizationInit(primaryStage, activeUser);
        } else {

        }

    }


    public static void main(String[] args) {
            launch(args);
    }


}
