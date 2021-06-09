package GUIMain.CustomStage;

import GUIMain.Styles.CssUrl;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

public class PanoramaStage extends Stage{

    public PanoramaStage (Node node) {

    StackPane stackPane = new StackPane ();
//        stackPane.getStylesheets().add(new CssUrl().getPanorama());
        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                close();
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        try {
            ImageView imageView1 = new ImageView(new javafx.scene.image.Image(new File("img/panorama/imgBlue.jpg").toURI().toURL().toString()));
            imageView1.setFitWidth(width);
            imageView1.setFitHeight(height);
            stackPane.getChildren().addAll(imageView1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Stage mainStage = (Stage) node.getScene().getWindow();

        // Specifies the owner Window (parent) for new window
        initOwner(mainStage);

        setAlwaysOnTop(true);

        initStyle (StageStyle.UNDECORATED);

        setX (0);
        setY (0);


        Scene scene = new Scene (stackPane, width, height);
        setScene (scene);
    }
}
