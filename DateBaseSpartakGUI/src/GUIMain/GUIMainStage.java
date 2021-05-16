package GUIMain;

import MySQLDB.ServerMySQL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import Logger.LOG;
public class GUIMainStage extends Application {

    @Override
    public void start(Stage primaryStage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GUIMain.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("DBS V 1.0");
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
        primaryStage.setHeight (780);
        primaryStage.setWidth (1080);
        primaryStage.setScene(scene);
        primaryStage.show();
        } catch (IOException e) {
            LOG.error ("Ошибка при запуске приложения.", e);
        }
        LOG.info ("Успешный старт приложения.");
    }


    public static void main(String[] args) {
            launch(args);
    }

    @Override
    public void stop(){
        ServerMySQL.disconnect();
        LOG.info ("Приложение закрыто без ошибок.");
    }
}
