package GUIMain.CustomStage;

import GUIMain.Styles.CssUrl;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

public class InfoStage {

    public InfoStage(String msg) {

        HBox hBox = new HBox();
        hBox.getStylesheets().add(new CssUrl().get());
        hBox.getStyleClass().add("info-stage");
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        Label label = new Label (msg);
        label.getStyleClass().add("info-stage");
        hBox1.getChildren ().add (label);
        hBox1.setMinWidth(390);
        try {
            ImageView imageView1 = new ImageView(new javafx.scene.image.Image(new File("img/logo/info1.png").toURI().toURL().toString()));
            imageView1.setFitHeight(40);
            imageView1.setFitWidth(60);
            hBox.getChildren().addAll(imageView1, hBox1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        Stage newWindow = new Stage ();
        newWindow.setAlwaysOnTop(true);

//        newWindow.setTitle("Second Stage");

        int w = 450;
        int h = 50;
        hBox.setPadding(new Insets(0, 10, 0, 0));
        Scene scene = new Scene (hBox, w, h);
        scene.setOnMouseClicked (new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                newWindow.close ();
            }
        });

        newWindow.setScene (scene);

        // Specifies the modality for new window.
//        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window


        newWindow.initStyle (StageStyle.TRANSPARENT);

        // Set position of second window, related to primary window.

        newWindow.setOpacity (0.90);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        newWindow.setX (width - w - 20);
        newWindow.setY (height - h - 20);

        newWindow.show ();

        new Thread (() -> {
            try {

                Thread.sleep (2000);
            } catch (InterruptedException e) {
                e.printStackTrace ();
new SystemErrorStage (e);
            }
            if (newWindow.isShowing ()) {
                Platform.runLater (newWindow::close);
            }
        }).start ();
    }
}
