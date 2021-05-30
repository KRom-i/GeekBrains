package Services.StageService;

import ImroptExcel.ServiceImport;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private HBox hBoxPerson;
    private VBox vBoxGroup;

    private Spinner<Double> spinnerTypeGroup;

    public StageNewService(Node node){

        Stage mainStage = (Stage) node.getScene ().getWindow ();

        Stage newWindow = new Stage ();
        newWindow.setTitle ("Добавление новой услуги");

        StackPane stackPane = new StackPane ();
        Scene scene = new Scene (stackPane, 500,500);

        VBox vBoxMain = new VBox ();

        Label labelTypeService = new Label ("Вид товара/услуги");
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

        Spinner<Integer> spinnerNumberGroup = new Spinner<> ();
        spinnerNumberGroup.setValueFactory (new SpinnerValueFactory.IntegerSpinnerValueFactory (1,100,1));
        Label labelNumberGroup = new Label ("Номер группы товара");
        HBox hBoxNumberGroup = new HBox ();
        hBoxNumberGroup.getChildren ().addAll (labelNumberGroup, spinnerNumberGroup);

        TextField textFieldName = new TextField ();
        textFieldName.setPromptText ("Наименование товара/услуги");

        textFieldCost = new TextField ();
        textFieldCost.setPromptText ("Цена (руб) товара/услуги");

        TextField textFieldBalance = new TextField ();
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
        hBoxBalance.getChildren ().addAll (checkBoxBalance, textFieldBalance);


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


        HBox hBoxUpTypeTre = new HBox ();
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
        hBoxPerson = new HBox ();
        vBoxGroup = new VBox ();
        vBoxGroup.setVisible (false);
        vBoxGroup.setVisible (false);
        hBoxPerson.setVisible (false);
        hBoxPerson.setVisible (false);
        vBoxTypeTreMain = new VBox ();
        vBoxTypeTreMain.setManaged (false);
        vBoxTypeTreMain.setVisible (false);
        vBoxTypeTreMain.getChildren ().addAll (hBoxUpTypeTre, hBoxPerson, vBoxGroup);



        vBoxMain.getChildren ().addAll (hBoxTypeService, hBoxNumberGroup,
                textFieldName, textFieldCost, hBoxBalance,
                hBoxNumVisDayCli, vBoxTypeTreMain);
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
            hBoxPerson.getChildren ().clear ();

            hBoxPerson.setVisible (true);
            hBoxPerson.setVisible (true);
            vBoxGroup.setVisible (false);
            vBoxGroup.setVisible (false);
        } else {
            vBoxGroup.getChildren ().clear ();
            Label label1 = new Label ("Зал итого");
            TextField textField1 = new TextField ();
            textField1.setEditable (false);
            Label label2 = new Label ("Тренер итого");
            TextField textField2 = new TextField ();
            textField2.setEditable (false);

            Label label = new Label ("Сумма тренеру за тренировку");
            spinnerTypeGroup = new Spinner<> ();
            spinnerTypeGroup.setValueFactory (new SpinnerValueFactory.DoubleSpinnerValueFactory (0,999999,200));
            spinnerTypeGroup.setEditable (true);

            Button returnBut = new Button ("Расчет суммы");
            returnBut.setOnAction (new EventHandler<ActionEvent> () {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        double cost = Double.valueOf (textFieldCost.getText ());
                        double visit = Double.valueOf (textFieldNumberVisits.getText ());

                        double sum2 = visit * spinnerTypeGroup.getValue ();
                        double sum1 = cost - sum2;

                        textField1.setText (String.valueOf (sum1));
                        textField2.setText (String.valueOf (sum2));
                    } catch (Exception e){
                        e.printStackTrace ();
                    }
                }
            });

            vBoxGroup.getChildren ().addAll (
                    new HBox (label, spinnerTypeGroup),
                    new HBox (label1, textField1),
                    new HBox (label2, textField2), returnBut);
            vBoxGroup.setVisible (true);
            vBoxGroup.setVisible (true);
            hBoxPerson.setVisible (false);
            hBoxPerson.setVisible (false);
        }

        });
    }


}
