package GUIMain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;

import Logger.LOG;

public class GUIMainStage extends Application {

    @Override
    public void start(Stage primaryStage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GUIMain.fxml"));
        Scene scene = new Scene(root);
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        double width = screenSize.getWidth();
//        double height = screenSize.getHeight();
//        primaryStage.setY(height / 6);
//        primaryStage.setX(width / 6);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        String css = this.getClass().getResource("/styles/style.css").toExternalForm();
//        scene.getStylesheets().add(css);
//        scene.setFill(Color.TRANSPARENT);
//        primaryStage.setWidth(width - (width / 3));
//        primaryStage.setHeight(height - (height / 3));
        primaryStage.setHeight (740);
        primaryStage.setWidth (1024);
        primaryStage.setScene(scene);
        primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        LOG.info ("Успешный старт приложения.");
    }


    public static void main(String[] args) {
            launch(args);
    }

    @Override
    public void stop(){

        LOG.info ("Приложение закрыто без ошибок.");
    }
}
