package GUIMain.CustomStage;

import Format.DateTime;
import GUIMain.GUIMainStage;
import Logger.LOG;
import WorkDataBase.ActionUser;
import WorkDataBase.AuthUserDateBase;
import WorkDataBase.UserSpartak;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.ss.usermodel.PictureData;
import org.glassfish.grizzly.utils.StringFilter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static javafx.application.Application.launch;

public class DialogDateInit {

    private String date = "";


    public DialogDateInit(Node node) {

        Stage mainStage = ( Stage ) node.getScene ().getWindow ();
        StackPane stackPane = new StackPane ();
//        stackPane.setStyle("-fx-background-radius: 5; " +
//                " -fx-background-color: #A9A9A9; " +
//                " -fx-text-fill: black;");
//        stackPane.getChildren().add(new Label (textInfo));

        Stage newWindow = new Stage ();

        newWindow.setTitle ("Установка новой даты и времени");

        Scene scene = new Scene (stackPane, 310, 260);

//        scene.setOnMouseClicked(new EventHandler<MouseEvent> () {
//            @Override
//            public void handle (MouseEvent event) {
//                newWindow.close();
//            }
//        });



        VBox vBox = new VBox ();
        vBox.setSpacing (5);
        vBox.setAlignment (Pos.CENTER);
//        Label labelDate = new Label (" Дата");
//        labelDate.setMinWidth (250);
        Label labelDate = new Label ("Дата (ДД.ММ.ГГГГ)");


        String dateStr = new DateTime ().currentDate ();

        int dd = Integer.valueOf (dateStr.substring (0,2));
        int mm = Integer.valueOf (dateStr.substring (3,5));
        int yyyy = Integer.valueOf (dateStr.substring (6,10));

        DatePicker datePickerTest = new DatePicker (LocalDate.of (yyyy, mm,dd));
        datePickerTest.setMinSize (250,40);
        datePickerTest.setMaxWidth (250);

//        TextField datePicker = new TextField ();
//        datePicker.setMinSize (250,40);
//        datePicker.setMaxWidth (250);

        Label labelTime = new Label ("Добавить минуты \nк текущему времени");
        Spinner<Integer> spinnerTime = new Spinner<>();
//        spinnerTime.setEditable (true);
        spinnerTime.setMinSize (250,40);
        spinnerTime.setMaxWidth (250);

        int i = Integer.valueOf (new DateTime ().getTime ());
        spinnerTime.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory (0, 9999, i));

//        TextField textTime = new TextField ();
//        textTime.setText (new DateTime ().getTime());
//        textTime.setMinSize (250,40);
//        textTime.setMaxWidth (250);

//        datePicker.setOnKeyPressed (new EventHandler<KeyEvent> () {
//            @Override
//            public void handle(KeyEvent event) {
//                datePicker.setStyle ("-fx-text-fill: black");
//            }
//        });

//        textTime.setOnKeyPressed (new EventHandler<KeyEvent> () {
//            @Override
//            public void handle(KeyEvent event) {
//                textTime.setStyle ("-fx-text-fill: black");
//            }
//        });

//        datePicker.setText (new DateTime ().currentDate ());
        Button button = new Button ("Сохранить");
        button.setMinSize (250,40);
        button.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
//                date = datePicker.getText ();
                String[] d = datePickerTest.getValue ().toString ().split ("-");
                String dateNew = String.format ("%s.%s.%s",d[2], d [1],d[0]);
                new DateTime ().setDateConfig (dateNew);
                new DateTime ().setTimeConfig (String.valueOf (spinnerTime.getValue ()));
                new InfoStage ("Данные сохранены.");
                Stage stage = (Stage) mainStage.getScene ().getWindow ();
                stage.close();
                Platform.runLater( () -> new GUIMainStage ().start( new Stage() ) );



//                if (checkFormatDate(date)){
//                    if (checkFormatTime(textTime.getText ())){
//                        new DateTime ().setTimeConfig (textTime.getText ());

//                        new DateTime ().setDateConfig (date);


//                        newWindow.close ();
//                        System.exit (0);
//                    } else {
//                        textTime.setStyle ("-fx-background-color: #c71919");
//                    }

//                } else {
//                    datePicker.setStyle ("-fx-background-color: #c71919");
//                }

            }
        });
        Button button2 = new Button ("Продолжить без сохранения");
        button2.setMinSize (250,40);
        button2.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                new WarningStage ("Данные не сохранены.");
                LOG.info ("Рестарт приложения.");
                newWindow.close ();
            }
        });



        vBox.getChildren ().addAll (labelDate, datePickerTest,labelTime, spinnerTime, button, button2);

        stackPane.getChildren ().add (vBox);

        newWindow.setScene (scene);

        // Specifies the modality for new window.
        newWindow.initModality (Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner (mainStage);

        newWindow.initStyle (StageStyle.UTILITY);

        // Set position of second window, related to primary window.

//        newWindow.setOpacity(0.7);
//        newWindow.setX(getXbottLeftStage(mainStage, scene) - 20);
//        newWindow.setY(getYbottLeftStage(mainStage, scene) - 20);
        newWindow.show ();
    }


    private boolean checkFormatDate(String date){
        try {

            LOG.info  ("Date length new config: " + date.length ());
            LOG.info  ("Date string new config: " + date);
            if (date.length () == 10) {

                int dd = Integer.valueOf (date.substring (0,2));
                int mm = Integer.valueOf (date.substring (3,5));
                int yyyy = Integer.valueOf (date.substring (6,10));
                LOG.info (String.format("int: dd[%s] mm[%s] yyyy[%s]\n",dd, mm,yyyy));
                if (dd > 0 && dd < 32) {
                    if (mm > 0 && mm < 13) {
                        if (yyyy > 2001) {
                            return true;
                        }
                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace ();
new SystemErrorStage (e);
        }
        return false;
    }


    private boolean checkFormatTime(String Time){
        try {

            LOG.info  ("Time length new config: " + Time.length ());
            LOG.info  ("Time string new config: " + Time);
            if (date.length () > 0) {

                int m = Integer.valueOf (Time);

                LOG.info (String.format("int: m[%s]\n", m));
                if (m >= 0 ) {
                            return true;
                }
            }

        } catch (Exception e){
            e.printStackTrace ();
new SystemErrorStage (e);
        }
        return false;
    }



}
