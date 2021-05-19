package GUIMain;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogStageCustom {


    public DialogStageCustom (Node node, String textInfo){

        Stage mainStage = (Stage) node.getScene().getWindow();

        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-radius: 5; " +
                           " -fx-background-color: #A9A9A9; " +
                           " -fx-text-fill: black;");
        stackPane.getChildren().add(new Label(textInfo));

        Stage newWindow = new Stage();

//        newWindow.setTitle("Second Stage");

        Scene scene = new Scene(stackPane ,200, 100);
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                newWindow.close();
            }
        });

        newWindow.setScene(scene);

        // Specifies the modality for new window.
//        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(mainStage);

        newWindow.initStyle(StageStyle.UNDECORATED);

        // Set position of second window, related to primary window.

        newWindow.setOpacity(0.7);
        newWindow.setX(getXbottLeftStage(mainStage, scene) - 20);
        newWindow.setY(getYbottLeftStage(mainStage, scene) - 20);

        newWindow.show();

        new Thread(()->{
            try {

                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (newWindow.isShowing()){
                Platform.runLater(newWindow::close);
            }
        }).start();
    }


//    Методы определяют нижний левый угол
    private double getXbottLeftStage(Stage mainStage, Scene newScene){
        double xDesktop = mainStage.getX();
        double wMainStage = mainStage.getWidth();
        double wNewScene = newScene.getWidth();
        return (xDesktop + wMainStage) - wNewScene;
    }
    private double getYbottLeftStage(Stage mainStage, Scene newScene){
        double yDesktop = mainStage.getY();
        double hMainStage = mainStage.getHeight();
        double hNewScene = newScene.getHeight();
        return (yDesktop + hMainStage) - hNewScene;
    }

    private void argsPrint(Object... objects){
        for(Object o: objects
        ) {

            System.out.printf("%s %s \n", o.getClass().getName(), o.toString());
        }
    }

}
