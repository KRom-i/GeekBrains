package GUIMain.CustomStage;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class WarningStage {

    public WarningStage(String msg) {

        StackPane stackPane = new StackPane ();

        stackPane.setStyle ("-fx-background-radius: 5; " +
                " -fx-background-color: #eee661; " +
                " -fx-font-size: 15pt;" +
                " -fx-text-fill: black;");
        stackPane.getChildren ().add (new Label (msg));

        Stage newWindow = new Stage ();
        newWindow.setAlwaysOnTop(true);

//        newWindow.setTitle("Second Stage");

        int w = 300;
        int h = 150;
        Scene scene = new Scene (stackPane, w, h);
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


        newWindow.initStyle (StageStyle.UNDECORATED);

        // Set position of second window, related to primary window.

        newWindow.setOpacity (0.85);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        newWindow.setX (width - w - 20);
        newWindow.setY (height - h - 50);

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
