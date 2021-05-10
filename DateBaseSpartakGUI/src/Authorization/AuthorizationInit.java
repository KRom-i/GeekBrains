package Authorization;

import WorkDataBase.UserSpartak;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;

public class AuthorizationInit extends Stage {

    public static Stage stageMain;
    public static  UserSpartak activeUser;

    public AuthorizationInit(Stage stageMain, UserSpartak activeUser) {
        this.stageMain = stageMain;
        this.activeUser = activeUser;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Authorization.fxml"));


        Scene scene = new Scene(root);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();


//        setWidth(width - (width / 2));
//        setHeight(200);
//
//        setY(height / 4);
//        setX(width / 4);

        initStyle(StageStyle.UNDECORATED);

//        String css = this.getClass().getResource("/styles/style2.css").toExternalForm();
//        scene.getStylesheets().add(css);

        scene.setFill(Color.ALICEBLUE);
        setScene(scene);
        initModality(Modality.WINDOW_MODAL);
        initOwner(stageMain);
        show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
