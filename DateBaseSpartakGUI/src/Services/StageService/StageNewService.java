package Services.StageService;

import Format.Round;
import GUIMain.CustomStage.DateControlService;
import GUIMain.CustomStage.ErrorStage;
import GUIMain.CustomStage.InfoStage;
import GUIMain.CustomStage.WarningStage;
import GUIMain.Styles.CssUrl;
import Logger.LOG;
import Services.*;
import Services.ImroptExcel.ServiceImport;
import WorkDataBase.ActionClient;
import WorkDataBase.Trainers.Trainer;
import WorkDataBase.Trainers.TrainerList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StageNewService {

    private CheckBox checkBoxBalance;

    private TextField textFieldNumberVisits;
    private TextField textFieldNumberDay;
    private TextField textFieldNumberClients;
    private VBox vBoxNumVisDayCli;

    private TextField textFieldCost;

    private int typeService;
    private int typeTypeTr;

    private VBox vBoxTypeTreMain;
    private VBox vBoxPerson;
    private VBox vBoxGroup;

    private Spinner<Double> spinnerTypeGroupSum;
    private Spinner<Integer> spinnerNumberGroup;
    private Button buttonSave;
    
    private TextField textFieldBalance;
    private TextField textFieldName;
    private Label labelTypeService;

    private HBox hBoxUpTypeTre;

    private Stage newWindow;
    private Label labelNumVis;
    private Label labelNumDays;
    private Label labelNumClients;


    private CheckBox checkControlTime;
    private TextField textFielControlTimeSum;
    private Label labelControlTime;

    private int idTen;

    public StageNewService(Node node){

        Stage mainStage = (Stage) node.getScene ().getWindow ();
        idTen = 0;
        this.newWindow = new Stage ();
        newWindow.setTitle ("Добавление новой услуги");

        StackPane stackPane = new StackPane ();
        Scene scene = new Scene (stackPane, 700,950);
        scene.getStylesheets().add(new CssUrl().get ());

        VBox vBoxMain = new VBox ();

        labelTypeService = new Label ("Вид товара/услуги");
        ChoiceBox<String> choiceBoxTypeService = new ChoiceBox<> ();
        choiceBoxTypeService.getItems ().addAll (ServiceImport.nameGroup);
        choiceBoxTypeService.setMinWidth (200);
        choiceBoxTypeService.setMinHeight (25);
        choiceBoxTypeService.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent actionEvent) {
                methodChoiceTypeService(choiceBoxTypeService.getSelectionModel ().getSelectedIndex ());
            }
        });

        HBox hBoxTypeService = new HBox ();
        hBoxTypeService.getChildren ().addAll (labelTypeService, choiceBoxTypeService);

        spinnerNumberGroup = new Spinner<> ();
        spinnerNumberGroup.setMinWidth (170);
        spinnerNumberGroup.setMinHeight (25);
        spinnerNumberGroup.setValueFactory (new SpinnerValueFactory.IntegerSpinnerValueFactory (1,100,1));
        Label labelNumberGroup = new Label ("Номер группы");
        HBox hBoxNumberGroup = new HBox ();
        hBoxNumberGroup.getChildren ().addAll (labelNumberGroup, spinnerNumberGroup);

        Label labelName = new Label ("Наименование товара/услуги");
        textFieldName = new TextField ();
        textFieldName.setPromptText ("Наименование товара/услуги");
        textFieldName.setEditable(false);
        textFieldName.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                if (typeService > 0){
                List<String> stringList = ServiceImport.getColServicesString(typeService, 4);
                    textFieldName.setEditable(true);
                    Platform.runLater (()->{
                        ContextMenu contextMenu = new ContextMenu();
                        contextMenu.getItems ().clear ();
                        textFieldName.setContextMenu (contextMenu);
                        contextMenu.setStyle("-fx-max-width: 300;");
                        contextMenu.setStyle("-fx-max-height: 300;");

                        MenuItem[] items =  new MenuItem[stringList.size ()];

                        for(int j = 0; j < stringList.size (); j++) {

                            items[j] = new MenuItem (stringList.get (j));
                            int finalJ = j;
                            items[j].setOnAction (new EventHandler<ActionEvent> () {
                                @Override
                                public void handle (ActionEvent event){
                                    textFieldName.setText(stringList.get (finalJ));
                                    int i = ServiceImport.getColServicesIntNumberGroup(typeService, "ID_GROUP", stringList.get (finalJ));
                                    spinnerNumberGroup.setValueFactory (new SpinnerValueFactory.IntegerSpinnerValueFactory (1,100,i));
                                }
                            });
                            contextMenu.getItems ().add (items[j]);
                        }


                        double x = MouseInfo.getPointerInfo().getLocation().getX();
                        double y = MouseInfo.getPointerInfo().getLocation().getY();

                        contextMenu.show (textFieldName, x,y);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
                    });
                } else {
                    Platform.runLater (()->{
                        new WarningStage("Необходимо выбрать вид услуги/товара");
                        ContextMenu contextMenu = new ContextMenu();
                        contextMenu.getItems ().clear ();
                        textFieldName.setContextMenu (contextMenu);
                        contextMenu.setStyle("-fx-max-width: 400;");
                        contextMenu.setStyle("-fx-max-height: 300;");

                        MenuItem item = new MenuItem ("Необходимо выбрать вид услуги");
                        item.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle (ActionEvent event) {
                                new Thread(()->{
                                    labelTypeService.getStyleClass().add("label-warning");
                                    try {
                                        Thread.sleep(1500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    labelTypeService.getStyleClass().add("label-null");
                                }).start();
                            }
                        });
                            contextMenu.getItems ().add (item);

                        double x = MouseInfo.getPointerInfo().getLocation().getX();
                        double y = MouseInfo.getPointerInfo().getLocation().getY();

                        contextMenu.show (textFieldName, x,y);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
                    });

                }
            }
        });

        Label labelCost = new Label ("Цена товара/услуги");
        textFieldCost = new TextField ();
        textFieldCost.setPromptText ("Цена товара/услуги");
        textFieldCost.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {

                    double[] costUp = {5,10,50,100,500,1000,5000};

                    Platform.runLater (()->{
                        ContextMenu contextMenu = new ContextMenu();
                        contextMenu.getItems ().clear ();
                        textFieldCost.setContextMenu (contextMenu);
                        contextMenu.setStyle("-fx-max-width: 300;");
                        contextMenu.setStyle("-fx-max-height: 300;");

                        MenuItem[] items =  new MenuItem[costUp.length];

                        for(int j = 0; j < costUp.length; j++) {

                            items[j] = new MenuItem (" + " + costUp[j]);
                            double D = costUp[j];
                            items[j].setOnAction (new EventHandler<ActionEvent> () {
                                @Override
                                public void handle (ActionEvent event){

                                    if (textFieldCost.getText().length() > 0){
                                        try {
                                            double cost = Double.valueOf(textFieldCost.getText());
                                            cost = cost + D;
                                            textFieldCost.setText(cost + "");
                                        } catch (NumberFormatException numberFormatException){
                                           new ErrorStage("Ошибка формата (Цена)");
                                            new Thread(()->{
                                                textFieldCost.getStyleClass().add("label-warning");
                                                try {
                                                    Thread.sleep(1500);
                                                } catch (InterruptedException e) {

                                                } finally {
                                                    textFieldCost.getStyleClass().add("label-null");
                                                }
                                            }).start();
                                        }

                                    } else {
                                        double cost = D;
                                        textFieldCost.setText(cost + "");
                                    }

                                }
                            });
                            contextMenu.getItems ().add (items[j]);
                        }


                        double x = MouseInfo.getPointerInfo().getLocation().getX();
                        double y = MouseInfo.getPointerInfo().getLocation().getY();

                        contextMenu.show (textFieldName, x,y);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
                    });

            }
        });

        Label labelBalance = new Label ("Остаток");
        textFieldBalance = new TextField ();
        textFieldBalance.setPromptText ("Остаток");
        textFieldBalance.setText("0");
        this.checkBoxBalance = new CheckBox ("Не учитывать");
        checkBoxBalance.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!checkBoxBalance.isSelected ()){
                    textFieldBalance.setEditable (true);
                    textFieldBalance.setOpacity (1);
                    labelBalance.setOpacity (1);
                    textFieldBalance.setText("0");
                } else {
                    textFieldBalance.setOpacity (0.5);
                    textFieldBalance.setEditable (false);
                    labelBalance.setOpacity (0.5);
                    textFieldBalance.clear ();
                }
            }
        });

        HBox hBoxBalance= new HBox ();
        hBoxBalance.getChildren ().addAll (textFieldBalance, checkBoxBalance);
        hBoxBalance.setAlignment(Pos.BOTTOM_LEFT);
        hBoxBalance.setSpacing (5);

        labelControlTime = new Label ("Сумма доплаты");
        textFielControlTimeSum = new TextField ();
        textFielControlTimeSum.setPromptText ("Сумма доплаты");
        textFielControlTimeSum.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                if (checkControlTime.isSelected()) {
                    double[] costUp = {5, 10, 50, 100, 500, 1000, 5000};

                    Platform.runLater(() -> {
                        ContextMenu contextMenu = new ContextMenu();
                        contextMenu.getItems().clear();
                        textFielControlTimeSum.setContextMenu(contextMenu);
                        contextMenu.setStyle("-fx-max-width: 300;");
                        contextMenu.setStyle("-fx-max-height: 300;");

                        MenuItem[] items = new MenuItem[costUp.length];

                        for(int j = 0; j < costUp.length; j++) {

                            items[j] = new MenuItem(" + " + costUp[j]);
                            double D = costUp[j];
                            items[j].setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle (ActionEvent event) {

                                    if (textFielControlTimeSum.getText().length() > 0) {
                                        try {
                                            double cost = Double.valueOf(textFielControlTimeSum.getText());
                                            cost = cost + D;
                                            textFielControlTimeSum.setText(cost + "");
                                        } catch (NumberFormatException numberFormatException) {
                                            new ErrorStage("Ошибка формата (Сумма доплаты)");
                                            new Thread(() -> {
                                                textFielControlTimeSum.getStyleClass().add("label-warning");
                                                try {
                                                    Thread.sleep(1500);
                                                } catch (InterruptedException e) {

                                                } finally {
                                                    textFielControlTimeSum.getStyleClass().add("label-null");
                                                }
                                            }).start();
                                        }

                                    } else {
                                        double cost = D;
                                        textFielControlTimeSum.setText(cost + "");
                                    }

                                }
                            });
                            contextMenu.getItems().add(items[j]);
                        }


                        double x = MouseInfo.getPointerInfo().getLocation().getX();
                        double y = MouseInfo.getPointerInfo().getLocation().getY();

                        contextMenu.show(textFieldName, x, y);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
                    });
                }
            }
        });
        textFielControlTimeSum.setMaxWidth (200);
        textFielControlTimeSum.setMinHeight (25);
        this.checkControlTime = new CheckBox ("Ограничение по веремени до " + DateControlService.getTimeControl());
        checkControlTime.setSelected(false);
        labelControlTime.setOpacity (0.5);
        textFielControlTimeSum.setOpacity (0.5);
        textFielControlTimeSum.setVisible(false);
        textFielControlTimeSum.setManaged(false);
        labelControlTime.setVisible(false);
        labelControlTime.setManaged(false);
        textFielControlTimeSum.setEditable (false);
        textFielControlTimeSum.clear ();
        checkControlTime.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (checkControlTime.isSelected ()){
                    textFielControlTimeSum.setEditable (true);
                    textFielControlTimeSum.setOpacity (1);
                    textFielControlTimeSum.setText("0");
                    labelControlTime.setOpacity (1);
                } else {
                    labelControlTime.setOpacity (0.5);
                    textFielControlTimeSum.setOpacity (0.5);
                    textFielControlTimeSum.setEditable (false);
                    textFielControlTimeSum.clear ();
                }
            }
        });

        HBox hBoxControlTime= new HBox ();
        hBoxControlTime.setAlignment(Pos.BOTTOM_LEFT);
        hBoxControlTime.getChildren ().addAll (textFielControlTimeSum, checkControlTime);
        hBoxControlTime.setSpacing (5);


        textFieldNumberVisits = new TextField ();
        textFieldNumberVisits.setPromptText ("Кол-во посещений");
        textFieldNumberVisits.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {

                   int[] visit = {1,2,4,6,8,10,12,14,16,18,20};

                    Platform.runLater (()->{
                        ContextMenu contextMenu = new ContextMenu();
                        contextMenu.getItems ().clear ();
                        textFieldNumberVisits.setContextMenu (contextMenu);
                        contextMenu.setStyle("-fx-max-width: 300;");
                        contextMenu.setStyle("-fx-max-height: 300;");

                        MenuItem[] items =  new MenuItem[visit.length];

                        for(int j = 0; j < visit.length; j++) {
                            String vis = String.valueOf(visit[j]);
                            items[j] = new MenuItem (vis);

                            items[j].setOnAction (new EventHandler<ActionEvent> () {
                                @Override
                                public void handle (ActionEvent event){
                                    textFieldNumberVisits.setText(vis);
                                }
                            });
                            contextMenu.getItems ().add (items[j]);
                        }


                        double x = MouseInfo.getPointerInfo().getLocation().getX();
                        double y = MouseInfo.getPointerInfo().getLocation().getY();

                        contextMenu.show (textFieldName, x,y);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
                    });

            }
        });

        labelNumVis = new Label("Кол-во посещений");

        textFieldNumberDay = new TextField ();
        textFieldNumberDay.setPromptText ("Срок (дн)");
        textFieldNumberDay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {

                int[] months = {1,3,6,12,24,36};

                Platform.runLater (()->{
                    ContextMenu contextMenu = new ContextMenu();
                    contextMenu.getItems ().clear ();
                    textFieldNumberDay.setContextMenu (contextMenu);
                    contextMenu.setStyle("-fx-max-width: 300;");
                    contextMenu.setStyle("-fx-max-height: 300;");

                    MenuItem[] items =  new MenuItem[months.length];

                    for(int j = 0; j < months.length; j++) {
                        final int month = months[j];
                        items[j] = new MenuItem (month + " мес.");

                        items[j].setOnAction (new EventHandler<ActionEvent> () {
                            @Override
                            public void handle (ActionEvent event){
                                int value = 0;
                                for(int i = 0; i < month; i++) {
                                    if ((i + 1) % 2 == 0){
                                        value += 30;
                                    } else {
                                        value += 31;
                                    }
                                }
                                textFieldNumberDay.setText(String.valueOf(value));

                            }
                        });
                        contextMenu.getItems ().add (items[j]);
                    }


                    double x = MouseInfo.getPointerInfo().getLocation().getX();
                    double y = MouseInfo.getPointerInfo().getLocation().getY();

                    contextMenu.show (textFieldName, x,y);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
                });

            }
        });

        labelNumDays = new Label("Срок (дн)");


        textFieldNumberClients = new TextField ();
        textFieldNumberClients.setPromptText ("Кол-во клиентов");
        textFieldNumberClients.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                int[] clients = {1,2,3,4,5};

                Platform.runLater (()->{
                    ContextMenu contextMenu = new ContextMenu();
                    contextMenu.getItems ().clear ();
                    textFieldNumberClients.setContextMenu (contextMenu);
                    contextMenu.setStyle("-fx-max-width: 300;");
                    contextMenu.setStyle("-fx-max-height: 300;");

                    MenuItem[] items =  new MenuItem[clients.length];

                    for(int j = 0; j < clients.length; j++) {
                        String value = String.valueOf(clients[j]);
                        items[j] = new MenuItem (value);

                        items[j].setOnAction (new EventHandler<ActionEvent> () {
                            @Override
                            public void handle (ActionEvent event){
                                textFieldNumberClients.setText(value);
                            }
                        });
                        contextMenu.getItems ().add (items[j]);
                    }


                    double x = MouseInfo.getPointerInfo().getLocation().getX();
                    double y = MouseInfo.getPointerInfo().getLocation().getY();

                    contextMenu.show (textFieldName, x,y);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
                });
            }
        });

        labelNumClients= new Label("Кол-во клиентов");

        vBoxNumVisDayCli = new VBox ();

        vBoxNumVisDayCli.setManaged (false);
        vBoxNumVisDayCli.setVisible (false);
        vBoxNumVisDayCli.getChildren ().addAll(labelNumVis,textFieldNumberVisits, labelNumDays, textFieldNumberDay,
                                               labelNumClients, textFieldNumberClients );
        vBoxNumVisDayCli.setSpacing (5);


        hBoxUpTypeTre = new HBox ();
        Label labelTypeTre = new Label ("Выплата тренеру");
        ChoiceBox<String> choiceBoxTypeTre = new ChoiceBox<>();
        choiceBoxTypeTre.getItems ().addAll ("Процентная", "Фиксированная");
        choiceBoxTypeTre.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent actionEvent) {
                methodChoiceTypeTre (choiceBoxTypeTre.getSelectionModel ().getSelectedIndex ());
            }


        });
        hBoxUpTypeTre.getChildren ().addAll (labelTypeTre, choiceBoxTypeTre);
        vBoxPerson = new VBox ();
        vBoxGroup = new VBox ();
        vBoxGroup.setVisible (false);
        vBoxGroup.setVisible (false);
        vBoxPerson.setVisible (false);
        vBoxPerson.setVisible (false);
        vBoxTypeTreMain = new VBox ();
        vBoxTypeTreMain.setManaged (false);
        vBoxTypeTreMain.setVisible (false);
        vBoxTypeTreMain.getChildren ().addAll (hBoxUpTypeTre, vBoxPerson, vBoxGroup);

        stackPane.setAlignment (Pos.CENTER);

        vBoxMain.setSpacing (10);
        vBoxMain.setPadding (new Insets (20,20,0,20));
        vBoxMain.setAlignment (Pos.TOP_LEFT);
        hBoxTypeService.setAlignment (Pos.CENTER_LEFT);
        hBoxTypeService.setMinSize (400,40);
        hBoxTypeService.setMaxSize (400,40);
        hBoxTypeService.setSpacing (68);
        hBoxNumberGroup.setAlignment (Pos.CENTER_LEFT);
        hBoxNumberGroup.setMinSize (400,40);
        hBoxNumberGroup.setMaxSize (400,40);
        hBoxNumberGroup.setSpacing (95);
        spinnerNumberGroup.setMaxSize (100,30);
        textFieldName.setMaxWidth (400);
        textFieldName.setMinHeight (25);
        textFieldCost.setMaxWidth (200);
        textFieldCost.setMinHeight (25);

        textFieldBalance.setMaxWidth (200);
        textFieldBalance.setMinHeight (25);

        textFieldNumberClients.setMaxWidth (200);
        textFieldNumberClients.setMinHeight (25);
        textFieldNumberDay.setMaxWidth (200);
        textFieldNumberDay.setMinHeight (25);
        textFieldNumberVisits.setMaxWidth (200);
        textFieldNumberVisits.setMinHeight (25);

        vBoxTypeTreMain.setSpacing (10);

        Label labelNameTre = new Label("Тренер");
        HBox hBoxNameTre = new HBox();
        hBoxNameTre.setSpacing(5);

        TextField textFieldNameTre = new TextField();
        Button buttonClear = new Button("Отмена");
        textFieldNameTre.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                List<Trainer> trainerList = TrainerList.get();

                Platform.runLater (()->{
                    ContextMenu contextMenu = new ContextMenu();
                    contextMenu.getItems ().clear ();
                    textFieldNameTre.setContextMenu (contextMenu);
                    contextMenu.setStyle("-fx-max-width: 300;");
                    contextMenu.setStyle("-fx-max-height: 300;");

                    MenuItem[] items =  new MenuItem[trainerList.size()];

                    for(int j = 0; j < trainerList.size(); j++) {

                        final int J = j;

                        items[j] = new MenuItem (trainerList.get(j).getClient().toStringIteam());

                        items[j].setOnAction (new EventHandler<ActionEvent> () {
                            @Override
                            public void handle (ActionEvent event){
                                textFieldNameTre.setText(trainerList.get(J).getClient().toStringIteam());
                                idTen = trainerList.get(J).getId();
                                buttonClear.setManaged(true);
                                buttonClear.setVisible(true);
                            }
                        });
                        contextMenu.getItems ().add (items[j]);
                    }

                    double x = MouseInfo.getPointerInfo().getLocation().getX();
                    double y = MouseInfo.getPointerInfo().getLocation().getY();
                    contextMenu.show (textFieldNameTre, x,y);
//            argsPrint(x,y,w,h,ww,hh,xMenu,yMenu);
                });


            }
        });

        textFieldNameTre.setEditable(false);
        textFieldNameTre.setPromptText("Тренер");
        textFieldNameTre.setMinWidth (400);
        textFieldNameTre.setMinHeight (25);
        buttonClear.setMinHeight (25);
        buttonClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                buttonClear.setManaged(false);
                buttonClear.setVisible(false);
                idTen = 0;
                textFieldNameTre.clear();
            }
        });
        buttonClear.setManaged(false);
        buttonClear.setVisible(false);

        hBoxNameTre.getChildren().addAll(textFieldNameTre, buttonClear);

        VBox vBoxAddTre = new VBox();
        vBoxAddTre.setAlignment(Pos.CENTER_LEFT);
        vBoxAddTre.getChildren().addAll(labelNameTre, hBoxNameTre);
        vBoxAddTre.setSpacing(5);
        vBoxTypeTreMain.getChildren().add(vBoxAddTre);

        hBoxUpTypeTre.setAlignment (Pos.CENTER_LEFT);
        hBoxUpTypeTre.setSpacing (80);

        vBoxNumVisDayCli.setAlignment (Pos.CENTER_LEFT);
        hBoxBalance.setAlignment (Pos.CENTER_LEFT);
        vBoxTypeTreMain.setAlignment (Pos.CENTER_LEFT);


        buttonSave = new Button ("Сохранить");
        buttonSave.setMinSize (400,50);
        buttonSave.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
                methodSaveNewService();
            }

        });

        vBoxMain.getChildren ().addAll (hBoxTypeService, hBoxNumberGroup, labelName, textFieldName, labelCost, textFieldCost,
                labelBalance, hBoxBalance, labelControlTime, hBoxControlTime, vBoxNumVisDayCli, vBoxTypeTreMain, buttonSave);

        stackPane.getChildren ().add (vBoxMain);

        newWindow.setScene (scene);
        newWindow.initModality (Modality.WINDOW_MODAL);
        newWindow.initOwner (mainStage);
        newWindow.initStyle (StageStyle.UTILITY);
        newWindow.show ();
    }


    private void methodChoiceTypeService(int idx) {

        typeService = idx + 1;

        Platform.runLater (()->{
            if (idx == 0 || idx == 1) {
                textFieldNumberVisits.clear ();
                textFieldNumberDay.clear ();
                textFieldNumberClients.clear ();
                vBoxNumVisDayCli.setManaged (false);
                vBoxNumVisDayCli.setVisible (false);
                vBoxTypeTreMain.setManaged (false);
                vBoxTypeTreMain.setVisible (false);
                textFielControlTimeSum.setVisible(false);
                textFielControlTimeSum.setManaged(false);
                labelControlTime.setVisible(false);
                labelControlTime.setManaged(false);
            } else if (idx == 2) {
                textFieldNumberVisits.setManaged (true);
                textFieldNumberVisits.setVisible (true);
                textFieldNumberClients.setManaged (true);
                textFieldNumberClients.setVisible (true);
                labelNumClients.setManaged (true);
                labelNumClients.setVisible (true);
                labelNumVis.setManaged (true);
                labelNumVis.setVisible (true);
                vBoxNumVisDayCli.setManaged (true);
                vBoxNumVisDayCli.setVisible (true);
                vBoxTypeTreMain.setManaged (false);
                vBoxTypeTreMain.setVisible (false);
                textFielControlTimeSum.setVisible(true);
                textFielControlTimeSum.setManaged(true);
                labelControlTime.setVisible(true);
                labelControlTime.setManaged(true);
            } else if (idx == 3) {
                textFieldNumberDay.setManaged (true);
                textFieldNumberDay.setVisible (true);
                textFieldNumberVisits.clear ();
                textFieldNumberClients.clear ();
                textFieldNumberClients.setManaged (false);
                textFieldNumberClients.setVisible (false);
                textFieldNumberVisits.setManaged (false);
                textFieldNumberVisits.setVisible (false);
                labelNumClients.setManaged (false);
                labelNumClients.setVisible (false);
                labelNumVis.setManaged (false);
                labelNumVis.setVisible (false);
                vBoxNumVisDayCli.setManaged (true);
                vBoxNumVisDayCli.setVisible (true);
                vBoxTypeTreMain.setManaged (false);
                vBoxTypeTreMain.setVisible (false);
                textFielControlTimeSum.setVisible(true);
                textFielControlTimeSum.setManaged(true);
                labelControlTime.setVisible(true);
                labelControlTime.setManaged(true);
            } else if (idx == 4) {
                textFieldNumberVisits.setManaged (true);
                textFieldNumberVisits.setVisible (true);
                textFieldNumberClients.setManaged (true);
                textFieldNumberClients.setVisible (true);
                labelNumClients.setManaged (true);
                labelNumClients.setVisible (true);
                labelNumVis.setManaged (true);
                labelNumVis.setVisible (true);
                vBoxNumVisDayCli.setManaged (true);
                vBoxNumVisDayCli.setVisible (true);
                vBoxTypeTreMain.setManaged (true);
                vBoxTypeTreMain.setVisible (true);
                textFielControlTimeSum.setVisible(true);
                textFielControlTimeSum.setManaged(true);
                labelControlTime.setVisible(true);
                labelControlTime.setManaged(true);
            }

        });

    }


    private void methodChoiceTypeTre(int idx) {
        Platform.runLater (()->{


        typeTypeTr = idx + 1;

        if (idx == 0){
            vBoxGroup.getChildren ().clear ();
            vBoxPerson.getChildren ().clear ();


            Label label1 = new Label ("Зал итого (руб)");
            TextField textField1 = new TextField ();
            textField1.setEditable (false);
            Label label2 = new Label ("Тренер итого (руб)");
            TextField textField2 = new TextField ();
            textField2.setEditable (false);

            Label label = new Label ("Доля тренера (%)");

            vBoxPerson.setSpacing (5);
            label.setPadding (new Insets (0,72,0,0));
            label1.setPadding (new Insets (0,88,0,0));
            label2.setPadding (new Insets (0,61,0,0));

            spinnerTypeGroupSum = new Spinner<> ();
            spinnerTypeGroupSum.setValueFactory (new SpinnerValueFactory.DoubleSpinnerValueFactory (0,100,60));

            Button returnBut = new Button ("Расчет суммы");

            returnBut.setOnAction (new EventHandler<ActionEvent> () {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        double cost = Double.valueOf (textFieldCost.getText ());
                        double visit = Double.valueOf (textFieldNumberVisits.getText ());

                        double sumOneTre = cost / visit;

                        double sum2 = sumOneTre  * (spinnerTypeGroupSum.getValue () / 100) * visit;
                        double sum1 = cost - sum2;

                        textField1.setText (String.valueOf (new Round ().getDoubleValue (sum1)));
                        textField2.setText (String.valueOf (new Round ().getDoubleValue (sum2)));
                    } catch (Exception e){
                        e.printStackTrace ();
                    }
                }
            });



            vBoxPerson.getChildren ().addAll (
                    new HBox (label, spinnerTypeGroupSum),
                    new HBox (label1, textField1),
                    new HBox (label2, textField2), returnBut);
            vBoxPerson.getChildren ().addAll ();
            vBoxPerson.setVisible (true);
            vBoxPerson.setVisible (true);
            vBoxGroup.setVisible (false);
            vBoxGroup.setVisible (false);
        } else {
            vBoxGroup.getChildren ().clear ();
            vBoxPerson.getChildren ().clear ();
            Label label1 = new Label ("Зал итого (руб)");
            TextField textField1 = new TextField ();
            textField1.setEditable (false);
            Label label2 = new Label ("Тренер итого (руб)");
            TextField textField2 = new TextField ();
            textField2.setEditable (false);

            Label label = new Label ("Сумма тренеру (руб)");

            vBoxGroup.setSpacing (5);
            label.setPadding (new Insets (0,50,0,0));
            label1.setPadding (new Insets (0,88,0,0));
            label2.setPadding (new Insets (0,61,0,0));

            spinnerTypeGroupSum = new Spinner<> ();
            spinnerTypeGroupSum.setValueFactory (new SpinnerValueFactory.DoubleSpinnerValueFactory (0,999999,200, 10));

            Button returnBut = new Button ("Расчет суммы");
            returnBut.setOnAction (new EventHandler<ActionEvent> () {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        double cost = Double.valueOf (textFieldCost.getText ());
                        double visit = Double.valueOf (textFieldNumberVisits.getText ());

                        double sum2 = visit * spinnerTypeGroupSum.getValue ();
                        double sum1 = cost - sum2;

                        textField1.setText (String.valueOf (new Round ().getDoubleValue (sum1)));
                        textField2.setText (String.valueOf (new Round ().getDoubleValue (sum2)));
                    } catch (Exception e){
                        e.printStackTrace ();
                    }
                }
            });

            vBoxGroup.getChildren ().addAll (
                    new HBox (label, spinnerTypeGroupSum),
                    new HBox (label1, textField1),
                    new HBox (label2, textField2), returnBut);
            vBoxGroup.setVisible (true);
            vBoxGroup.setVisible (true);
            vBoxPerson.setVisible (false);
            vBoxPerson.setVisible (false);
        }

        });
    }

    private void methodSaveNewService () {

        String name = null;
        int numberGroup = spinnerNumberGroup.getValue ();
        double cost = 0;
        int balance = 999999;
        int visit = 1;
        int clients = 1;
        int days = 0;
        boolean save = true;
        String typeTre = "";
        double sumTre = 0;
        boolean timeControl = checkControlTime.isSelected();
        double addSumTimeControl = 0;

        if (textFieldName.getText ().length () > 0 && typeService > 0){

            name = textFieldName.getText ();

            try {
                cost = Double.valueOf (textFieldCost.getText ());
                cost = new Round ().getDoubleValue (cost);
            } catch (NumberFormatException e){
                save = false;
                textFieldCost.setStyle ("-fx-background-color: #FF6347");
                e.printStackTrace ();
            }

            if (timeControl){
                try {
                    addSumTimeControl = Double.valueOf (textFielControlTimeSum.getText ());
                    addSumTimeControl = new Round ().getDoubleValue (addSumTimeControl);
                } catch (NumberFormatException e){
                    save = false;
                    textFielControlTimeSum.setStyle ("-fx-background-color: #FF6347");
                    e.printStackTrace ();
                }
            }

            if (!checkBoxBalance.isSelected ()){
                try {
                    balance = Integer.valueOf (textFieldBalance.getText ());
                    if (balance >= 999_999){
                        save = false;
                        new ErrorStage("Недопустимый остаток.");
                    }
                } catch (NumberFormatException e){
                    save = false;
                    textFieldBalance.setStyle ("-fx-background-color: #FF6347");
                    e.printStackTrace ();
                }
            }

            if (typeService == 3){

                    try {
                        visit = Integer.valueOf (textFieldNumberVisits.getText ());
                    } catch (NumberFormatException e){
                        textFieldNumberVisits.setStyle ("-fx-background-color: #FF6347");
                        e.printStackTrace ();
                        save = false;
                    }
                    try {
                        clients = Integer.valueOf (textFieldNumberClients.getText ());
                    } catch (NumberFormatException e){
                        textFieldNumberClients.setStyle ("-fx-background-color: #FF6347");
                        e.printStackTrace ();
                        save = false;
                    }

                try {
                    days = Integer.valueOf (textFieldNumberDay.getText ());
                } catch (NumberFormatException e){
                    textFieldNumberDay.setStyle ("-fx-background-color: #FF6347");
                    e.printStackTrace ();
                    save = false;
                }




            } else if (typeService == 4){
                try {
                    days = Integer.valueOf (textFieldNumberDay.getText ());
                } catch (NumberFormatException e){
                    textFieldNumberDay.setStyle ("-fx-background-color: #FF6347");
                    e.printStackTrace ();
                    save = false;
                }
            }


            if (typeService == 5) {
                try {
                    visit = Integer.valueOf (textFieldNumberVisits.getText ());
                } catch (NumberFormatException e){
                    textFieldNumberVisits.setStyle ("-fx-background-color: #FF6347");
                    e.printStackTrace ();
                    save = false;
                }
                try {
                    clients = Integer.valueOf (textFieldNumberClients.getText ());
                } catch (NumberFormatException e){
                    textFieldNumberClients.setStyle ("-fx-background-color: #FF6347");
                    e.printStackTrace ();
                    save = false;
                }
                try {
                    days = Integer.valueOf (textFieldNumberDay.getText ());
                } catch (NumberFormatException e){
                    textFieldNumberDay.setStyle ("-fx-background-color: #FF6347");
                    e.printStackTrace ();
                    save = false;
                }

                if (typeTypeTr == 2) {
                    try {
                        sumTre = spinnerTypeGroupSum.getValue ();
                        typeTre = "fixed";
                    } catch (NumberFormatException e) {
                        e.printStackTrace ();
                        vBoxGroup.setStyle ("-fx-background-color: #FF6347");
                        save = false;
                    }
                } else if (typeTypeTr == 1){
                    typeTre = "percent";
                    sumTre = spinnerTypeGroupSum.getValue () / 100;
                    
                }else if (typeTypeTr <= 0){
                    hBoxUpTypeTre.setStyle ("-fx-background-color: #FF6347");
                    save = false;
                }
            }


        } else {
            textFieldName.setStyle ("-fx-background-color: #FF6347");
            labelTypeService.setStyle ("-fx-text-fill: #FF6347");
            save = false;
        }

        if (save){
            LOG.info (String.format ("Формирование новой услуги НАИМЕНОВАНИЕ [%s]  ЦЕНА [%s] ОСТАТОК [%s] ПОСЕЩЕНИЙ [%s] " +
                                     "КЛИЕНТОВ [%s] ДНЕЙ [%s] ДОЛЯ ТРЕНЕРА [%s] КОНТРОЛЬ ВРЕМЕНИ [%s] ДОП СУММА [%s]",
                    name, cost, balance, visit, clients, days, sumTre, timeControl, addSumTimeControl));

            switch (typeService){
                case 1:
                    new OneTimeService (typeService,numberGroup, name, cost, balance, timeControl, addSumTimeControl).addToDataBase();
                    break;
                case 2:
                    new Product (typeService,numberGroup, name, cost, balance, timeControl, addSumTimeControl).addToDataBase();
                    break;
                case 3:
                    new Subscription (typeService,numberGroup, name, cost, balance, visit, clients, days, timeControl, addSumTimeControl).addToDataBase();
                    break;
                case 4:
                    new ClubCard (typeService,numberGroup, name, cost, balance, days, timeControl, addSumTimeControl).addToDataBase();
                    break;
                case 5:
                    new CoachServices (typeService,numberGroup, name, cost, balance, visit, clients, days, typeTre, sumTre, timeControl, addSumTimeControl, idTen).addToDataBase();
                    break;
            }

            ServiceImport.readDataBase(false);
            ServiceImport.updateVbox(typeService - 1, 0,0);
            new InfoStage("Созранение базы данных");
            newWindow.close();

        }

    }
}
