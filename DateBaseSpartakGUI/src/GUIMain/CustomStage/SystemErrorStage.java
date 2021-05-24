package GUIMain.CustomStage;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.concurrent.Executors;

public class SystemErrorStage {


    public SystemErrorStage(Exception exception) {


        StackPane stackPane = new StackPane ();

        stackPane.setStyle ("-fx-background-radius: 5; " +
                " -fx-background-color: #f36e5b; " +
                " -fx-font-size: 15pt;" +
                " -fx-text-fill: #ffffff;");
        stackPane.getChildren ().add (new Label ("System ERROR !"));

        Stage newWindow = new Stage ();
        newWindow.setAlwaysOnTop(true);

//        newWindow.setTitle("Second Stage");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        int w = 300;
        int h = 150;
        Scene scene = new Scene (stackPane, w, h);
        scene.setOnMouseClicked (new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
//                StackPane sP = new StackPane ();
//                Scene s = new Scene (sP,width, height);
//                Stage w = new Stage ();
//                sP.getChildren ().add (new TextArea (exception.toString ()));
//                w.setAlwaysOnTop(true);
//                w.setScene (s);
//                w.show ();
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



        newWindow.setX (width - w - 20);
        newWindow.setY (height - h - 50);

        newWindow.show ();

    }
}
