package Services.StageService;

import Format.Round;
import GUIMain.CustomStage.DateControlService;
import GUIMain.CustomStage.ErrorStage;
import GUIMain.CustomStage.InfoStage;
import GUIMain.Styles.CssUrl;
import Logger.LOG;
import Services.*;
import Services.ImroptExcel.ServiceImport;
import WorkDataBase.Trainers.Trainer;
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

public class StageEditService {


    private TextField textFieldNumberVisits;
    private TextField textFieldNumberDay;
    private TextField textFieldNumberClients;

    private TextField textFieldCost;

    private int typeService;
    private int typeTypeTr;

    private VBox vBoxTypeTreMain;
    private VBox vBoxPerson;
    private VBox vBoxGroup;

    private Spinner<Double> spinnerTypeGroupSum;
    private Button buttonSave;

    private TextField textFieldName;
    private Label labelTypeService;

    private HBox hBoxUpTypeTre;

    private Stage newWindow;

    private Service service;

    public StageEditService(Node node, Service service){

        Stage mainStage = (Stage) node.getScene ().getWindow ();
        this.service = service;
        this.newWindow = new Stage ();
        newWindow.setTitle ("Редактор");

        StackPane stackPane = new StackPane ();
        Scene scene = new Scene (stackPane, 600, 900);
        scene.getStylesheets().add(new CssUrl().get ());

        VBox vBoxMain = new VBox ();

        labelTypeService = new Label ("Вид товара/услуги");
        TextField textFieldTypeService = new TextField(ServiceImport.nameGroup[service.getType() - 1]);
        textFieldTypeService.setEditable(false);
        textFieldTypeService.setMaxWidth (400);
        textFieldTypeService.setMinHeight (25);

        Label labelNameService = new Label ("Наименование товара/услуги");
        textFieldName = new TextField (service.getName());
        textFieldName.setPromptText ("Наименование товара/услуги");
        textFieldName.setMaxWidth (400);
        textFieldName.setMinHeight (25);

        Label labelCostService = new Label ("Стоимомсть товара/услуги");
        textFieldCost = new TextField (service.getCost() + "");
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
        textFieldCost.setPromptText ("Цена товара/услуги");
        textFieldCost.setMaxWidth (200);
        textFieldCost.setMinHeight (25);

        vBoxMain.getChildren().addAll(labelTypeService, textFieldTypeService, labelNameService,
                                      textFieldName, labelCostService, textFieldCost);


        if (service.getNumberVisits() > 0) {
            Label labelNumberVisits = new Label("Кол-во посещений");
            textFieldNumberVisits = new TextField(service.getNumberVisits() + "");
            textFieldNumberVisits.setPromptText("Кол-во посещений");
            textFieldNumberVisits.setMaxWidth (200);
            textFieldNumberVisits.setMinHeight (25);
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
            vBoxMain.getChildren().addAll(labelNumberVisits, textFieldNumberVisits);
        }

        if (service.getTermDays() > 0) {
            Label labelNumberDay= new Label("Срок (дн)");
            textFieldNumberDay = new TextField(service.getTermDays()+"");
            textFieldNumberDay.setPromptText ("Срок (дн)");
            textFieldNumberDay.setMaxWidth (200);
            textFieldNumberDay.setMinHeight (25);
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
            vBoxMain.getChildren().addAll(labelNumberDay, textFieldNumberDay);
        }

        if (service.getNumberClient() > 0) {
            Label labelNumberClients= new Label("Кол-во клиентов");
            textFieldNumberClients = new TextField(service.getNumberClient()+"");
            textFieldNumberClients.setPromptText ("Кол-во клиентов");
            textFieldNumberClients.setMaxWidth (200);
            textFieldNumberClients.setMinHeight (25);
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
            vBoxMain.getChildren().addAll(labelNumberClients, textFieldNumberClients);
        }

        if (service.isTimeControl()){
            String str = "";
            if (service.getType() > 2){
                 str = String.format("Ограничение по вермени до %s\nСумма доплаты %s",
                                           DateControlService.getTimeControl(), service.getAddSumTimeControl());
            } else {
                str = String.format("Ограничение по вермени до %s", DateControlService.getTimeControl());
            }
            Label label = new Label(str);
            vBoxMain.getChildren().addAll(label);
        }


        if (service.getSumTren() > 0){
            hBoxUpTypeTre = new HBox ();
            vBoxPerson = new VBox ();
            vBoxGroup = new VBox ();
            Label labelTypeTre = new Label ("Вид выплаты");
            ChoiceBox<String> choiceBoxTypeTre = new ChoiceBox<>();
            choiceBoxTypeTre.getItems ().addAll ("Процент", "Фиксированная");
            if (service.getSumTren() <= 1){
                choiceBoxTypeTre.setValue("Процент");
                vBoxGroup.setVisible (false);
                vBoxGroup.setVisible (false);
                methodChoiceTypeTre(0);
            } else {
                choiceBoxTypeTre.setValue("Фиксированная");
                vBoxPerson.setVisible (false);
                vBoxPerson.setVisible (false);
                methodChoiceTypeTre(1);
            }

            choiceBoxTypeTre.setOnAction (new EventHandler<ActionEvent> () {
                @Override
                public void handle(ActionEvent actionEvent) {
                    methodChoiceTypeTre (choiceBoxTypeTre.getSelectionModel ().getSelectedIndex ());
                }


            });
            hBoxUpTypeTre.getChildren ().addAll (labelTypeTre, choiceBoxTypeTre);

            vBoxTypeTreMain = new VBox ();

            vBoxTypeTreMain.setSpacing (5);
            hBoxUpTypeTre.setAlignment (Pos.CENTER_LEFT);
            hBoxUpTypeTre.setSpacing (80);
            vBoxTypeTreMain.setAlignment (Pos.CENTER_LEFT);

            vBoxTypeTreMain.getChildren ().addAll (hBoxUpTypeTre, vBoxPerson, vBoxGroup);
            vBoxMain.getChildren ().addAll ( vBoxTypeTreMain);
        }


            if (service.getIdTre() > 0) {
                Label labelTrainer = new Label("Тренер: " + new Trainer().getOneTrainer(service.getIdTre()).getClient().toStringIteam());
                vBoxMain.getChildren().addAll(labelTrainer);
            }



        stackPane.setAlignment (Pos.CENTER);

        vBoxMain.setSpacing (10);
        vBoxMain.setPadding (new Insets(20, 20, 0, 20));
        vBoxMain.setAlignment (Pos.TOP_LEFT);


        buttonSave = new Button ("Сохранить");
        buttonSave.setMinSize (400,50);
        buttonSave.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
                methodSaveNewService();
            }

        });



        Button buttonDelete = new Button ("Удалить");
        buttonDelete.setMinSize (400,50);
        buttonDelete.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
                    new Service().serviceDelete(service);
                    new InfoStage("Сохранение");
                     newWindow.close();
            }

        });

        vBoxMain.getChildren ().addAll ( buttonSave, buttonDelete);

        stackPane.getChildren ().add (vBoxMain);

        newWindow.setScene (scene);
        newWindow.initModality (Modality.WINDOW_MODAL);
        newWindow.initOwner (mainStage);
        newWindow.initStyle (StageStyle.UTILITY);
        newWindow.show ();
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
                spinnerTypeGroupSum.setValueFactory (new SpinnerValueFactory.DoubleSpinnerValueFactory (0,100,service.getSumTren() * 100));

                Button returnBut = new Button ("Расчет суммы");

                double cost = Double.valueOf (textFieldCost.getText ());
                double visit = Double.valueOf (textFieldNumberVisits.getText ());

                double sumOneTre = cost / visit;

                double sum2 = sumOneTre  * (spinnerTypeGroupSum.getValue () / 100) * visit;
                double sum1 = cost - sum2;

                textField1.setText (String.valueOf (new Round().getDoubleValue (sum1)));
                textField2.setText (String.valueOf (new Round ().getDoubleValue (sum2)));

                returnBut.setOnAction (new EventHandler<ActionEvent> () {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            double cost = Double.valueOf (textFieldCost.getText ());
                            double visit = Double.valueOf (textFieldNumberVisits.getText ());

                            double sumOneTre = cost / visit;

                            double sum2 = sumOneTre  * (spinnerTypeGroupSum.getValue () / 100) * visit;
                            double sum1 = cost - sum2;

                            textField1.setText (String.valueOf (new Round().getDoubleValue (sum1)));
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
                Double sumTre = service.getSumTren();
                if (sumTre < 1){
                    sumTre = sumTre * 100;
                }
                spinnerTypeGroupSum.setValueFactory (new SpinnerValueFactory.DoubleSpinnerValueFactory (0,999999,sumTre, 10));

                double cost = Double.valueOf (textFieldCost.getText ());
                double visit = Double.valueOf (textFieldNumberVisits.getText ());

                double sum2 = visit * spinnerTypeGroupSum.getValue ();
                double sum1 = cost - sum2;

                textField1.setText (String.valueOf (new Round ().getDoubleValue (sum1)));
                textField2.setText (String.valueOf (new Round ().getDoubleValue (sum2)));

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

        boolean save = true;

        if (textFieldName.getText ().length () > 0){

            service.setName(textFieldName.getText ());

            try {
                service.setCost(new Round ().getDoubleValue (Double.valueOf (textFieldCost.getText ())));
            } catch (NumberFormatException e){
                save = false;
                textFieldCost.setStyle ("-fx-background-color: #FF6347");
                e.printStackTrace ();
            }

            if (textFieldNumberClients != null){
                try {
                    service.setNumberClient(Integer.valueOf(textFieldNumberClients.getText()));
                } catch (NumberFormatException e){
                    save = false;
                    textFieldNumberClients.setStyle ("-fx-background-color: #FF6347");
                    e.printStackTrace ();
                }
            }

            if (textFieldNumberVisits != null){
                try {
                    service.setNumberVisits(Integer.valueOf(textFieldNumberVisits.getText()));
                } catch (NumberFormatException e){
                    save = false;
                    textFieldNumberVisits.setStyle ("-fx-background-color: #FF6347");
                    e.printStackTrace ();
                }
            }

            if (textFieldNumberDay != null){
                try {
                    service.setTermDays(Integer.valueOf(textFieldNumberDay.getText()));
                } catch (NumberFormatException e){
                    save = false;
                    textFieldNumberDay.setStyle ("-fx-background-color: #FF6347");
                    e.printStackTrace ();
                }
            }

            if (vBoxTypeTreMain != null){
                if (typeTypeTr == 1){
                    service.setSumTren(spinnerTypeGroupSum.getValue() / 100);
                    service.setTypeTren("percent");
                } else  if (typeTypeTr == 2){
                    service.setSumTren(spinnerTypeGroupSum.getValue());
                    service.setTypeTren("fixed");
                }
            }


        } else {
            textFieldName.setStyle ("-fx-background-color: #FF6347");
            save = false;
        }

        if (save){
            LOG.info (String.format ("Редактор услуги НАИМЕНОВАНИЕ [%s]  ЦЕНА [%s] ОСТАТОК [%s] ПОСЕЩЕНИЙ [%s] КЛИЕНТОВ [%s] ДНЕЙ [%s] ДОЛЯ ТРЕНЕРА [%s]" +
                                     " КОНТРОЛЬ ВРЕМЕНИ [%s] ДОП СУММА [%s] ID ТРЕНЕРА [%s]",
                                     service.getName(), service.getCost(), service.getBalance(), service.getNumberVisits(),
                                     service.getNumberClient(), service.getTermDays(), service.getSumTren(), service.isTimeControl(), service.getAddSumTimeControl(), service.getIdTre()));

            new Service().updateService(service);

            ServiceImport.readDataBase(false);
            ServiceImport.updateVbox(typeService - 1, service.getId(), 2);
            new InfoStage("Сохранение");
            newWindow.close();

        }

    }
}
