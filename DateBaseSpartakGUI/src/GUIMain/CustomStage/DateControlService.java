package GUIMain.CustomStage;

import Format.DateTime;
import GUIMain.Styles.CssUrl;
import Logger.LOG;
import MySQLDB.ServerMySQL;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.swing.plaf.synth.DefaultSynthStyle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateControlService {

    private ListView<HBox> listView;

    public DateControlService(Node node) {

        Stage mainStage = ( Stage ) node.getScene ().getWindow ();
        StackPane stackPane = new StackPane ();
//        stackPane.setStyle("-fx-background-radius: 5; " +
//                " -fx-background-color: #A9A9A9; " +
//                " -fx-text-fill: black;");
//        stackPane.getChildren().add(new Label (textInfo));

        Stage newWindow = new Stage ();

        newWindow.setTitle ("Правила проверки даты");

        Scene scene = new Scene (stackPane, 650, 700);
        scene.getStylesheets().add(new CssUrl().get ());
//        scene.setOnMouseClicked(new EventHandler<MouseEvent> () {
//            @Override
//            public void handle (MouseEvent event) {
//                newWindow.close();
//            }
//        });



        VBox vBox = new VBox ();
        vBox.setSpacing (5);
        vBox.setAlignment (Pos.TOP_LEFT);
//        Label labelDate = new Label (" Дата");
//        labelDate.setMinWidth (250);
//        Label labelDate = new Label ("Дата (ДД.ММ.ГГГГ)");
//
//
//        String dateStr = new DateTime().currentDate ();

//        int dd = Integer.valueOf (dateStr.substring (0,2));
//        int mm = Integer.valueOf (dateStr.substring (3,5));
//        int yyyy = Integer.valueOf (dateStr.substring (6,10));
//
//        DatePicker datePickerTest = new DatePicker (LocalDate.of (yyyy, mm, dd));
//        datePickerTest.setMinSize (250,40);
//        datePickerTest.setMaxWidth (250);
//
//        Button buttonUpDay = new Button ("+ 1 день");
//        buttonUpDay.setMinSize (250,40);
//        buttonUpDay.setOnAction (new EventHandler<ActionEvent>() {
//            @Override
//            public void handle (ActionEvent event) {
//                new DateTime().upDay();
////                Stage stage = (Stage) mainStage.getScene ().getWindow ();
////                stage.close();
////                Platform.runLater( () -> new GUIMainStage ().start( new Stage() ) );
//                new WarningStage("Необходимо перезапустить приложение.");
//            }
//        });
//        TextField datePicker = new TextField ();
//        datePicker.setMinSize (250,40);
//        datePicker.setMaxWidth (250);

//        Label labelTime = new Label ("Добавить минуты \nк текущему времени");
//        Spinner<Integer> spinnerTime = new Spinner<>();
////        spinnerTime.setEditable (true);
//        spinnerTime.setMinSize (250,40);
//        spinnerTime.setMaxWidth (250);
//
//
//
//        int i = Integer.valueOf (new DateTime ().getTime ());

        Label label1 = new Label("УТРО");
        Label label2 = new Label("ВЕЧЕР");
        HBox hBoxTime = new HBox();
        Label labelHour = new Label("ч.");
        Label labelMin = new Label("м.");
        String time = getTimeControl();
        int h = Integer.valueOf(time.split(":")[0]);
        int m = Integer.valueOf(time.split(":")[1]);
        Spinner<Integer> spinnerHour = new Spinner<>();
        spinnerHour.setMaxWidth(70);
        spinnerHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory (0, 24, h, 1));
        Spinner<Integer> spinnerMin = new Spinner<>();
        spinnerMin.setMaxWidth(70);
        spinnerMin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory (0, 60, m, 5));
        hBoxTime.setSpacing(5);
        hBoxTime.getChildren().addAll(spinnerHour, labelHour, spinnerMin, labelMin);


        listView = new ListView();
        upDateList();
        String dateStr = new DateTime().currentDate ();
        int dd = Integer.valueOf (dateStr.substring (0,2));
        int mm = Integer.valueOf (dateStr.substring (3,5));
        int yyyy = Integer.valueOf (dateStr.substring (6,10));
        DatePicker datePickerTest = new DatePicker (LocalDate.of (yyyy, mm, dd));
        datePickerTest.setEditable(false);
        datePickerTest.setMinSize (250,40);
        datePickerTest.setMaxWidth (250);
        Button button1 = new Button(" + Рабочий день");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                String[] d = datePickerTest.getValue ().toString ().split ("-");
                String dateNew = String.format ("%s.%s.%s",d[2], d [1],d[0]);
                if (checkDate(dateNew)) {
                    Label label = new Label(dateNew + "    рабочий день");
                    label.setMinWidth(250);
                    addDay(dateNew, "рабочий день");
                    Button del = new Button("Удалить");
                    del.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle (ActionEvent event) {
                            deleteDay(dateNew);
                            upDateList();
                        }
                    });
                    HBox hBox = new HBox();
                    hBox.setSpacing(5);
                    hBox.setId(dateNew);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.getChildren().addAll(label, del);
                    listView.getItems().add(hBox);
                    upDateList();
                }
            }
        });
        Button button2 = new Button(" + Выходной день");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                String[] d = datePickerTest.getValue ().toString ().split ("-");
                String dateNew = String.format ("%s.%s.%s",d[2], d [1],d[0]);
                if (checkDate(dateNew)) {

                    Label label = new Label(dateNew + "   выходной день");
                    label.setMinWidth(250);
                    addDay(dateNew, "выходной день");
                    Button del = new Button("Удалить");
                    del.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle (ActionEvent event) {
                            deleteDay(dateNew);
                            upDateList();
                        }
                    });
                    HBox hBox = new HBox();
                    hBox.setId(dateNew);
                    hBox.setSpacing(5);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.getChildren().addAll(label, del);
                    listView.getItems().add(hBox);
                    upDateList();
                }
            }
        });
        HBox hBoxDays = new HBox();
        hBoxDays.setSpacing(5);
        hBoxDays.setAlignment(Pos.CENTER_LEFT);
        hBoxDays.getChildren().addAll(datePickerTest, button1, button2);




        Button buttonSave = new Button ("Сохранить");
        buttonSave.setMinSize (250,40);
        buttonSave.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                int h = spinnerHour.getValue();
                int m = spinnerMin.getValue();
                String strH = String.valueOf(h);
                String strM = String.valueOf(m);
                if (h < 10){
                    strH = "0" + strH;
                }
                if (m < 10){
                    strM = "0" + strM;
                }
                upDateTimeControl( strH+ ":" + strM);
                new InfoStage("Сохранение");
                newWindow.close();
            }
        });





        vBox.getChildren ().addAll (label1, hBoxTime, label2,hBoxDays, listView, buttonSave);
        vBox.setPadding(new Insets(10,10,10,10));
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

    private boolean checkDate(String date){

        for(int i = 0; i < listView.getItems().size(); i++) {
            if (listView.getItems().get(i).getId().equalsIgnoreCase(date)){
                new ErrorStage(date + " имеется в списке");
                return false;
            }

        }

        return true;
    }

    private void upDateList(){
        Platform.runLater(()->{
            listView.getItems().clear();
            listView.getItems().addAll(getDateList());
        });
    }

    private List<HBox> getDateList(){

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<HBox> hBoxList = new ArrayList<>();
        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "select * from  controlDate ORDER BY date;"
            );

//           Новое значение

            resultSet = statement.executeQuery();

            while (resultSet.next()){

                String date = resultSet.getString(1);
                String status = resultSet.getString(2);

                Label label = new Label(date + "   " + status);
                label.setMinWidth(250);
                Button del = new Button("Удалить");
                del.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle (ActionEvent event) {
                        deleteDay(date);
                        upDateList();
                    }
                });
                HBox hBox = new HBox();
                hBox.setId(date);
                hBox.setSpacing(5);
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.getChildren().addAll(label,del);
                hBoxList.add(hBox);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(resultSet);
        }

        return hBoxList;
    }

    private void addDay(String date, String statusDay){
        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "INSERT INTO controlDate (date, status) values (?, ?);"
            );


//           Новое значение
            statement.setString (1, date);
            statement.setString (2,statusDay);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
        }
    }

    private void deleteDay(String date){
        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "delete from  controlDate WHERE date = ?;"
            );

//           Новое значение
            statement.setString (1, date);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }
    }

    private void upDateTimeControl(String time){
        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE Config SET valConfig = ? WHERE idConfig = ?;"
            );

//           Новое значение
            statement.setString (1, time);

            statement.setInt (2,3);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }
    }


    public static String getTimeControl(){

        PreparedStatement statement = null;
        ResultSet rs = null;

        String str = "";

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM Config WHERE idConfig = ?;"
            );

            statement.setInt (1,3);

            rs = statement.executeQuery();

            if (rs.next()){
                str = rs.getString (3);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return str;
    }


    public static boolean getDayControl(String date, String day){

        PreparedStatement statement = null;
        ResultSet rs = null;

        boolean check = false;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM controlDate WHERE date = ?;"
            );

            statement.setString (1,date);

            rs = statement.executeQuery();

            if (rs.next()){
                check =  rs.getString (2).equalsIgnoreCase(day);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return check;
    }


}
