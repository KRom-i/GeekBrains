package Services.StageService;

import Format.Round;
import Logger.LOG;
import Services.*;
import Services.ImroptExcel.ServiceImport;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageNewService {

    private CheckBox checkBoxBalance;

    private TextField textFieldNumberVisits;
    private TextField textFieldNumberDay;
    private TextField textFieldNumberClients;
    private HBox hBoxNumVisDayCli;

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

    public StageNewService(Node node){

        Stage mainStage = (Stage) node.getScene ().getWindow ();

        Stage newWindow = new Stage ();
        newWindow.setTitle ("Добавление новой услуги");

        StackPane stackPane = new StackPane ();
        Scene scene = new Scene (stackPane, 450,600);

        VBox vBoxMain = new VBox ();

        labelTypeService = new Label ("Вид товара/услуги");
        ChoiceBox<String> choiceBoxTypeService = new ChoiceBox<> ();
        choiceBoxTypeService.getItems ().addAll (ServiceImport.nameGroup);
        choiceBoxTypeService.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent actionEvent) {
                methodChoiceTypeService(choiceBoxTypeService.getSelectionModel ().getSelectedIndex ());
            }
        });

        HBox hBoxTypeService = new HBox ();
        hBoxTypeService.getChildren ().addAll (labelTypeService, choiceBoxTypeService);

        spinnerNumberGroup = new Spinner<> ();
        spinnerNumberGroup.setValueFactory (new SpinnerValueFactory.IntegerSpinnerValueFactory (1,100,1));
        Label labelNumberGroup = new Label ("Номер группы");
        HBox hBoxNumberGroup = new HBox ();
        hBoxNumberGroup.getChildren ().addAll (labelNumberGroup, spinnerNumberGroup);

        textFieldName = new TextField ();
        textFieldName.setPromptText ("Наименование товара/услуги");

        textFieldCost = new TextField ();
        textFieldCost.setPromptText ("Цена товара/услуги");

        textFieldBalance = new TextField ();
        textFieldBalance.setPromptText ("Остаток");
        this.checkBoxBalance = new CheckBox ("Не учитывать");
        checkBoxBalance.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!checkBoxBalance.isSelected ()){
                    textFieldBalance.setEditable (true);
                    textFieldBalance.setOpacity (1);
                } else {
                    textFieldBalance.setOpacity (0.5);
                    textFieldBalance.setEditable (false);
                    textFieldBalance.clear ();
                }
            }
        });
        HBox hBoxBalance= new HBox ();
        hBoxBalance.getChildren ().addAll (textFieldBalance, checkBoxBalance);
        hBoxBalance.setSpacing (5);


        textFieldNumberVisits = new TextField ();
        textFieldNumberVisits.setPromptText ("Кол-во посещений");
        textFieldNumberDay = new TextField ();
        textFieldNumberDay.setPromptText ("Срок (дн)");
        textFieldNumberClients = new TextField ();
        textFieldNumberClients.setPromptText ("Кол-во клиентов");
        hBoxNumVisDayCli = new HBox ();
        hBoxNumVisDayCli.setManaged (false);
        hBoxNumVisDayCli.setVisible (false);
        hBoxNumVisDayCli.getChildren ().addAll(textFieldNumberVisits, textFieldNumberDay, textFieldNumberClients );
        hBoxNumVisDayCli.setSpacing (5);


        hBoxUpTypeTre = new HBox ();
        Label labelTypeTre = new Label ("Вид тренировок");
        ChoiceBox<String> choiceBoxTypeTre = new ChoiceBox<>();
        choiceBoxTypeTre.getItems ().addAll ("Персональные", "Групповые");
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
        textFieldName.setMinHeight (40);
        textFieldCost.setMaxWidth (200);
        textFieldCost.setMinHeight (40);

        textFieldBalance.setMaxWidth (200);
        textFieldBalance.setMinHeight (40);

        textFieldNumberClients.setMaxWidth (200);
        textFieldNumberClients.setMinHeight (40);
        textFieldNumberDay.setMaxWidth (200);
        textFieldNumberDay.setMinHeight (40);
        textFieldNumberVisits.setMaxWidth (200);
        textFieldNumberVisits.setMinHeight (40);

        vBoxTypeTreMain.setSpacing (10);
        hBoxUpTypeTre.setAlignment (Pos.CENTER_LEFT);
        hBoxUpTypeTre.setSpacing (80);

        hBoxNumVisDayCli.setAlignment (Pos.CENTER_LEFT);
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

        vBoxMain.getChildren ().addAll (hBoxTypeService, hBoxNumberGroup,
                textFieldName, textFieldCost, hBoxBalance,
                hBoxNumVisDayCli, vBoxTypeTreMain, buttonSave);
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
                hBoxNumVisDayCli.setManaged (false);
                hBoxNumVisDayCli.setVisible (false);
                vBoxTypeTreMain.setManaged (false);
                vBoxTypeTreMain.setVisible (false);
            } else if (idx == 2) {
                textFieldNumberVisits.setManaged (true);
                textFieldNumberVisits.setVisible (true);
                textFieldNumberClients.setManaged (true);
                textFieldNumberClients.setVisible (true);
                textFieldNumberDay.clear ();
                textFieldNumberDay.setVisible (false);
                textFieldNumberDay.setManaged (false);
                hBoxNumVisDayCli.setManaged (true);
                hBoxNumVisDayCli.setVisible (true);
                vBoxTypeTreMain.setManaged (false);
                vBoxTypeTreMain.setVisible (false);
            } else if (idx == 3) {
                textFieldNumberDay.setManaged (true);
                textFieldNumberDay.setVisible (true);
                textFieldNumberVisits.clear ();
                textFieldNumberClients.clear ();
                textFieldNumberClients.setManaged (false);
                textFieldNumberClients.setVisible (false);
                textFieldNumberVisits.setManaged (false);
                textFieldNumberVisits.setVisible (false);
                hBoxNumVisDayCli.setManaged (true);
                hBoxNumVisDayCli.setVisible (true);
                vBoxTypeTreMain.setManaged (false);
                vBoxTypeTreMain.setVisible (false);
            } else if (idx == 4) {
                textFieldNumberVisits.setManaged (true);
                textFieldNumberVisits.setVisible (true);
                textFieldNumberClients.setManaged (true);
                textFieldNumberClients.setVisible (true);
                textFieldNumberDay.clear ();
                textFieldNumberDay.setVisible (false);
                textFieldNumberDay.setManaged (false);
                hBoxNumVisDayCli.setManaged (true);
                hBoxNumVisDayCli.setVisible (true);
                vBoxTypeTreMain.setManaged (true);
                vBoxTypeTreMain.setVisible (true);
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
        int visit = 0;
        int clients = 1;
        int days = 0;
        boolean save = true;
        double sumTre = 0;

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

            if (!checkBoxBalance.isSelected ()){
                try {
                    balance = Integer.valueOf (textFieldBalance.getText ());
                } catch (NumberFormatException e){
                    save = false;
                    textFieldBalance.setStyle ("-fx-background-color: #FF6347");
                    e.printStackTrace ();
                }
            }

            if (typeService == 3){
                if (!checkBoxBalance.isSelected ()){
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
                if (typeTypeTr == 2) {
                    try {
                        sumTre = spinnerTypeGroupSum.getValue ();
                    } catch (NumberFormatException e) {
                        e.printStackTrace ();
                        vBoxGroup.setStyle ("-fx-background-color: #FF6347");
                        save = false;
                    }
                } else if (typeTypeTr == 1){
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
            LOG.info (String.format ("Формирование новой услуги НАИМЕНОВАНИЕ [%s]  ЦЕНА [%s] ОСТАТОК [%s] ПОСЕЩЕНИЙ [%s] КЛИЕНТОВ [%s] ДНЕЙ [%s] ДОЛЯ ТРЕНЕРА [%s]",
                    name, cost, balance, visit, clients, days, sumTre));

            switch (typeService){
                case 1:
                    new OneTimeService (name, cost, numberGroup);
                    break;
                case 2:
                    new Product (name, cost, balance,numberGroup);
                    break;
                case 3:
                    new Subscription (name, cost, balance, visit, numberGroup);
                    break;
                case 4:
                    new ClubCard (name, cost, days, visit, numberGroup);
                    break;
                case 5:
                    new CoachServices (name, cost, days, numberGroup);
                    break;
            }
        }

    }
}
