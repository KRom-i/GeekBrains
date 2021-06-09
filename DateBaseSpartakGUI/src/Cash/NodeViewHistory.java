package Cash;

import Format.DateTime;
import GUIMain.Styles.CssUrl;
import Logger.LOG;
import Services.ImroptExcel.ServiceImport;
import Services.Service;
import WorkDataBase.AuthUserDateBase;
import WorkDataBase.Trainers.Trainer;
import WorkDataBase.Trainers.TrainerList;
import WorkDataBase.UserSpartak;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import org.apache.poi.ss.formula.functions.T;

import java.util.List;


public class NodeViewHistory {

    private static ListView history;


    public static void init(ListView listView){
        history = listView;
        update();
    }

    public static void update(){
        Platform.runLater (()->{
            List<Transaction> t = CashBook.getListTranDay (new DateTime ().currentDate ());
            history.getItems ().clear ();
            if (!t.isEmpty ()) {
                for (int i = 0; i < t.size (); i++) {

                    double sum = 0;
                    if (t.get (i).getSumCashReceipt () > 0){
                        sum = t.get (i).getSumCashReceipt ();
                    } else if (t.get (i).getSumCashConsumption () > 0){
                        sum = t.get (i).getSumCashConsumption ();
                    } else if (t.get (i).getSumNonCashReceipt () > 0){
                        sum = t.get (i).getSumNonCashReceipt ();
                    } else if (t.get (i).getSumNonCashConsumption () > 0){
                        sum = t.get (i).getSumNonCashConsumption ();
                    }

                    String strNameTran = t.get (i).getNameTransaction ();
//                    Label label = new Label ();
//                    if (t.get (i).isDeleteTran ()){
//                        label.getStyleClass().add("label-warning");
//
//                    }
                    if (t.get (i).getNameTransaction ().startsWith ("Удаление операции")){
                        strNameTran = "Отмена";
                    }

                    int n = i + 1;
//                    String str = String.format ("[ %s ] [ %s ] [ %s ] [ %s ] [ %s ] [ %s ]",
//                            n, t.get (i).getTimeTransaction (), t.get(i).getNameTypePayment() , strNameTran, t.get(i).getNameService() , sum);
//                    label.setText (str);
                    Label label1 = new Label("  " + n +"");
                    label1.setMinWidth(50);

                    Label label2 = new Label(t.get (i).getTimeTransaction ());
                    label2.setMinWidth(100);

                    Label label3 = new Label(t.get(i).getNameTypePayment());
                    label3.setMinWidth(150);

                    Label label4 = new Label(strNameTran);
                    label4.setMinWidth(150);

                    Label label5 = new Label(t.get(i).getNameService());
                    label5.setMinWidth(250);

                    Label label6 = new Label(sum + "");
                    label6.setMinWidth(150);


                    int finalI = i;
//                    label.setOnMouseClicked (new EventHandler<MouseEvent> () {
//                        @Override
//                        public void handle(MouseEvent event) {
//                            if (event.getClickCount () == 2){
//
//                            }
//                        }
//                    });
                    HBox hBox = new HBox();
                    if (t.get (i).isDeleteTran ()){
                        label1.getStyleClass().add("label-warning");
                        label2.getStyleClass().add("label-warning");
                        label3.getStyleClass().add("label-warning");
                        label4.getStyleClass().add("label-warning");
                        label5.getStyleClass().add("label-warning");
                        label6.getStyleClass().add("label-warning");

                    }

                    hBox.setSpacing(3);
                    hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle (MouseEvent event) {
                            if (event.getClickCount() == 2){
                                new DialogInfoTran (label1, t.get (finalI));
                            }
                        }
                    });
                    hBox.getChildren().addAll(label1, label2, label3, label4, label5, label6);
                    history.getItems ().add(hBox);
                }

            } else {
                Label label = new Label (" Нет кассовых операций");
                history.getItems ().addAll(label);
            }
        });


    }

    private static class DialogInfoTran{

        private DialogInfoTran(Node node, Transaction t){

            Stage mainStage = ( Stage ) node.getScene ().getWindow ();
            StackPane stackPane = new StackPane ();
//        stackPane.setStyle("-fx-background-radius: 5; " +
//                " -fx-background-color: #A9A9A9; " +
//                " -fx-text-fill: black;");
//        stackPane.getChildren().add(new Label (textInfo));

            Stage newWindow = new Stage ();

            newWindow.setTitle ("Операция № " + t.getNumberTransaction ());

            Scene scene = new Scene (stackPane, 620, 900);
            scene.getStylesheets().add(new CssUrl().get ());
//        scene.setOnMouseClicked(new EventHandler<MouseEvent> () {
//            @Override
//            public void handle (MouseEvent event) {
//                newWindow.close();
//            }
//        });
            VBox vBox = new VBox ();;
            vBox.setSpacing (5);
            String[] s = new CashBook ().fieldsName[0];
            for (int i = 0; i < s.length; i++) {
                HBox hBox = new HBox ();
                hBox.setSpacing (5);
                Label label = new Label (" " + s[i]);
                label.setAlignment (Pos.CENTER_LEFT);
                label.setMinWidth (300);
                TextField text = new TextField ();
                text.setMinWidth (300);
                text.setEditable (false);
                switch (i){
                    case 0:
                        text.setText (t.getNumberTransaction () + "");
                        break;
                    case 1:
                        text.setText (t.getDateTransaction () + "");
                        break;
                    case 2:
                        text.setText (t.getTimeTransaction () + "");
                        break;
                    case 3:
                        text.setText (t.getIdTransaction () + "");
                        break;
                    case 4:
                        text.setText (t.getNameTransaction () + "");
                        break;
                    case 5:
                        text.setText (t.getIdService () + "");
                        break;
                    case 6:
                        text.setText (t.getNameService () + "");
                        break;
                    case 7:
                        text.setText (t.getIdClient () + "");
                        break;
                    case 8:
                        text.setText (t.getNameClient () + "");
                        break;
                    case 9:
                        text.setText (t.getIdUser () + "");
                        break;
                    case 10:
                        text.setText (t.getNameUser () + "");
                        break;
                    case 11:
                        text.setText (t.getIdTypePayment () + "");
                        break;
                    case 12:
                        text.setText (t.getNameTypePayment () + "");
                        break;
                    case 13:
                        text.setText (t.getSumCashReceipt () + "");
                        break;
                    case 14:
                        text.setText (t.getSumCashConsumption () + "");
                        break;
                    case 15:
                        text.setText (t.getSumNonCashReceipt () + "");
                        break;
                    case 16:
                        text.setText (t.getSumNonCashConsumption () + "");
                        break;
                    case 17:
                        text.setText (t.getSumCashBalanceBegin () + "");
                        break;
                    case 18:
                        text.setText (t.getSumNonCashBalanceBegin () + "");
                        break;
                    case 19:
                        text.setText (t.getSumAllBalanceBegin () + "");
                        break;
                    case 20:
                        text.setText (t.getSumCashBalanceEnd () + "");
                        break;
                    case 21:
                        text.setText (t.getSumNonCashBalanceEnd () + "");
                        break;
                    case 22:
                        text.setText (t.getSumAllBalanceEnd () + "");
                        break;
                    case 23:
                        String str = "НЕТ";
                        if (t.isDeleteTran ()){
                            str = "ДА";
                            text.setStyle ("-fx-background-color: #FF6347");
                        }
                        text.setText (str);
                        break;
                }

                hBox.getChildren ().addAll (label, text);
                vBox.getChildren ().add (hBox);
            }

            HBox hBox = new HBox ();
            hBox.setSpacing (5);
            hBox.setAlignment (Pos.CENTER);
            if (!t.isDeleteTran ()){
            Button buttonDelTran = new Button ("Отмена операции");
            buttonDelTran.setOnAction (new EventHandler<ActionEvent> () {
                @Override
                public void handle(ActionEvent event) {
                    LOG.info  ("Удаление транзакции \n" + t.toString ());

                    if (t.getIdTransaction() < 4){

                        CashBook.deleteTran(t);
                         Service service = new Service().getServiceDataBase(t.getIdService());

                        if (service.getBalance() != 999_999){
                             new Service().updateBalance(t.getIdService (), service.getBalance() + 1);
                             ServiceImport.readDataBase(false);
                             ServiceImport.updateVbox(service.getType() - 1, service.getId(), 1);
                        }

                    } else if (t.getIdTransaction() == 4) {
                        CashBook.deleteTran(t);
                        Trainer trainer = new Trainer().getOneTrainerIdClient(t.getIdClient());
                        trainer.setBalanceDateBase(- t.getSumCashConsumption());
                        LOG.info(String.format("Отмена списания с баланса теренра id Тренера [%s] id Клиента [%s] имя тренера [%s] " +
                                 "сумма [%s]", trainer.getId(), trainer.getClient().getId(), trainer.getClient().toStringIteam(), t.getSumCashConsumption()));
                        TrainerList.updateListView();
                    }

                    newWindow.close ();
                }
            });
                hBox.getChildren ().add ( buttonDelTran);
            }
            Button buttonExit = new Button ("Выход");
            buttonExit.setOnAction (new EventHandler<ActionEvent> () {
                @Override
                public void handle(ActionEvent event) {
                    newWindow.close ();
                }
            });
            hBox.getChildren ().add ( buttonExit);
            vBox.getChildren ().add (hBox);
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

    }
}
