package Services.ActionServices;

import Cash.Transaction;
import Format.DateTime;
import GUIMain.CustomStage.SystemErrorStage;
import GUIMain.Styles.CssUrl;
import Logger.LOG;
import MySQLDB.ServerMySQL;
import Services.Service;
import WorkDataBase.ActionClient;
import WorkDataBase.ActionUser;
import WorkDataBase.ClientClass;
import WorkDataBase.ClientDataBase;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActionService {

    private int idService;
    private int idServiceMain;
    private int idType;
    private String name;
    private double cost;
    private boolean deleteCheck;
    private int numberVisitsPay;
    private int termDay;
    private int numberClients;
    private String typeTrainer;
    private double sumTrainer;
    private boolean timeControl;
    private double addSumTimeControl;
    private int idTrainer;
    private List<ClientClass> clients;
    private int idClient;
    private String datePay;
    private String timePay;
    private int numberVisits;
    private boolean actionCheck;
    private int idTransaction;
    private boolean clientClub;

    private static HBox hBoxGUIInfo;

    public ActionService(HBox hBoxGUIInfo){
        this.hBoxGUIInfo = hBoxGUIInfo;
        hBoxGUIInfo.setPadding(new Insets(0,10,10,10));
        hBoxGUIInfo.setSpacing(10);
        hBoxGUIInfo.setAlignment(Pos.TOP_LEFT);
    }

    public static void updateHBoxGUIInfo(){
        Platform.runLater(()->{
            hBoxGUIInfo.getChildren().clear();
            List<ActionService> actionServiceList = ActionClient.getClient().getListActionService();
            for(int i = 0; i < actionServiceList.size(); i++) {
                hBoxGUIInfo.getChildren().add(actionServiceList.get(i).showVBoxInfo());
            }
        });
    }

    public ActionService(){}

    public ActionService (Service service, Transaction transaction, boolean clientClub, List<ClientClass> clients) {
        this.idService = getNewId();
        this.idServiceMain = service.getId();
        this.idType =service.getType();
        this.name = service.getName();
        this.cost = service.getCost();
        this.deleteCheck = false;
        this.numberVisitsPay = service.getNumberVisits();
        this.termDay = service.getTermDays();
        this.numberClients = service.getNumberClient();
        this.typeTrainer = service.getTypeTren();
        this.sumTrainer = service.getSumTren();
        this.timeControl = service.isTimeControl();
        this.addSumTimeControl = service.getAddSumTimeControl();
        this.idTrainer = service.getIdTre();
        this.datePay = new DateTime().currentDate();
        this.timePay = new DateTime().currentTime();
        this.numberVisits = service.getNumberVisits();
        this.actionCheck = false;
        this.idTransaction = transaction.getNumberTransaction();
        this.clientClub = clientClub;
        this.clients = clients;
    }

    public Trainer getTrainer(){
        if (idTrainer != 0){
            return new Trainer().getOneTrainer(this.idTrainer);
        }
        return null;
    }

    public int getIdService () {
        return idService;
    }

    public int getIdServiceMain () {
        return idServiceMain;
    }

    public int getIdType () {
        return idType;
    }

    public String getName () {
        return name;
    }

    public double getCost () {
        return cost;
    }

    public boolean isDeleteCheck () {
        return deleteCheck;
    }

    public int getNumberVisitsPay () {
        return numberVisitsPay;
    }

    public int getTermDay () {
        return termDay;
    }

    public int getNumberClients () {
        return numberClients;
    }

    public String getTypeTrainer () {
        return typeTrainer;
    }

    public double getSumTrainer () {
        return sumTrainer;
    }

    public boolean isTimeControl () {
        return timeControl;
    }

    public double getAddSumTimeControl () {
        return addSumTimeControl;
    }

    public int getIdTrainer () {
        return idTrainer;
    }



    public int getIdClient () {
        return idClient;
    }

    public String getDatePay () {
        return datePay;
    }

    public String getTimePay () {
        return timePay;
    }

    public int getNumberVisits () {
        return numberVisits;
    }

    public void setNumberVisits (int visits) {
        this.numberVisits = visits;
    }

    public boolean isActionCheck () {
        return actionCheck;
    }

    public int getIdTransaction () {
        return idTransaction;
    }

    public boolean isClientClub () {
        return clientClub;
    }

    public void setIdService (int idService) {
        this.idService = idService;
    }

    public void setIdServiceMain (int idServiceMain) {
        this.idServiceMain = idServiceMain;
    }

    public void setIdType (int idType) {
        this.idType = idType;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setCost (double cost) {
        this.cost = cost;
    }

    public void setDeleteCheck (boolean deleteCheck) {
        this.deleteCheck = deleteCheck;
    }

    public void setNumberVisitsPay (int numberVisitsPay) {
        this.numberVisitsPay = numberVisitsPay;
    }

    public void setTermDay (int termDay) {
        this.termDay = termDay;
    }

    public void setNumberClients (int numberClients) {
        this.numberClients = numberClients;
    }

    public void setTypeTrainer (String typeTrainer) {
        this.typeTrainer = typeTrainer;
    }

    public void setSumTrainer (double sumTrainer) {
        this.sumTrainer = sumTrainer;
    }

    public void setTimeControl (boolean timeControl) {
        this.timeControl = timeControl;
    }

    public void setAddSumTimeControl (double addSumTimeControl) {
        this.addSumTimeControl = addSumTimeControl;
    }

    public void setIdTrainer (int idTrainer) {
        this.idTrainer = idTrainer;
    }

    public void setClients (List<ClientClass> clients) {
        this.clients = clients;
    }

    public void setDatePay (String datePay) {
        this.datePay = datePay;
    }

    public void setTimePay (String timePay) {
        this.timePay = timePay;
    }

    public void setActionCheck (boolean actionCheck) {
        this.actionCheck = actionCheck;
    }

    public void setIdTransaction (int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public void setClientClub (boolean clientClub) {
        this.clientClub = clientClub;
    }

    public void setIdClient (int idClient) {
        this.idClient = idClient;
    }

    public void toDateBaseWrite(){

        for(int i = 0; i < clients.size(); i++) {
            this.setIdClient(clients.get(i).getId());
            toDataBase();
        }

    }

    public List<ClientClass> getClients(){

        PreparedStatement statement = null;
        ResultSet rs = null;
        List<ClientClass> clients = new ArrayList<>();

        try {

            statement = ServerMySQL.getConnection ().prepareStatement (
                    "SELECT idClient FROM ActionService WHERE deleteCheck = ? and actionCheck = ? and idService = ? ORDER BY idService;"
            );

            statement.setBoolean(1, deleteCheck);
            statement.setBoolean(2, actionCheck);
            statement.setInt(3, this.idService);

            rs = statement.executeQuery ();

            while (rs.next()){
                ClientClass client = ClientDataBase.getClientDateBase(rs.getInt(1));
                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace ();
            new SystemErrorStage(e);
        } finally {
            ServerMySQL.statementClose (statement);
            ServerMySQL.resultSetClose (rs);
        }

        return clients;
    }

    public List<ActionServiceHistory> getHistoryDateBase(){
        return new ActionServiceHistory().getHistoryThisId(this.idService);
    }

    public List<ActionService> getListActionService(int idClient, boolean deleteCheck, boolean actionCheck){

        PreparedStatement statement = null;
        ResultSet rs = null;
        List<ActionService> actionServiceList = new ArrayList<>();

        try {

            statement = ServerMySQL.getConnection ().prepareStatement (
                    "SELECT * FROM ActionService WHERE deleteCheck = ? and actionCheck = ? and idClient = ? ORDER BY idService;"
            );

            statement.setBoolean(1, deleteCheck);
            statement.setBoolean(2, actionCheck);
            statement.setInt(3, idClient);

            rs = statement.executeQuery ();

            while (rs.next ()) {

                ActionService as = new ActionService();
                as.setIdService(rs.getInt("idService"));
                as.setIdServiceMain(rs.getInt("idServiceMain"));
                as.setIdType(rs.getInt("idType"));
                as.setName(rs.getString("name"));
                as.setCost(rs.getDouble("cost"));
                as.numberVisitsPay = rs.getInt("numberVisitsPay");
                as.termDay = rs.getInt("termDay");
                as.numberClients = rs.getInt("numberClients");
                as.typeTrainer = rs.getString("typeTrainer");
                as.sumTrainer = rs.getDouble("sumTrainer");
                as.timeControl = rs.getBoolean("timeControl");
                as.addSumTimeControl = rs.getDouble("addSumTimeControl");
                as.idTrainer = rs.getInt("idTrainer");
                as.datePay = rs.getString("datePay");
                as.timePay = rs.getString("timePay");
                as.numberVisits = rs.getInt("numberVisits");
                as.idTransaction = rs.getInt("idTransaction");
                as.clientClub = rs.getBoolean("clientClub");;
                as.actionCheck = rs.getBoolean("actionCheck");
                as.deleteCheck = rs.getBoolean("deleteCheck");
                actionServiceList.add(as);
            }


        } catch (SQLException e) {
            e.printStackTrace ();
            new SystemErrorStage(e);
        } finally {
            ServerMySQL.statementClose (statement);
            ServerMySQL.resultSetClose (rs);
        }



        return actionServiceList;
    }

    private void toDataBase(){
        PreparedStatement ps = null;

        try {

            String q = ("INSERT INTO ActionService SET idService = ?, idServiceMain = ?, idType = ?, name = ?, cost = ?," +
                        " deleteCheck = ?, numberVisitsPay = ?, termDay = ?, numberClients = ?," +
                        " typeTrainer = ?, sumTrainer = ?, timeControl = ?, addSumTimeControl = ?, idTrainer = ?," +
                        " idClient = ?, datePay = ?, timePay = ?, numberVisits = ?, actionCheck = ?, idTransaction = ?," +
                        " clientClub = ?;");
            ps = ServerMySQL.getConnection ().prepareStatement (q);

            ps.setInt(1, idService);
            ps.setInt(2, idServiceMain);
            ps.setInt(3, idType);
            ps.setString(4, name);
            ps.setDouble(5, cost);
            ps.setBoolean(6, false);
            ps.setInt(7, numberVisitsPay);
            ps.setInt(8, termDay);
            ps.setInt(9, numberClients);
            ps.setString(10, typeTrainer);
            ps.setDouble(11, sumTrainer);
            ps.setBoolean(12, timeControl);
            ps.setDouble(13, addSumTimeControl);
            ps.setInt(14, idTrainer);
            ps.setInt(15, idClient);
            ps.setString(16, datePay);
            ps.setString(17, timePay);
            ps.setInt(18, numberVisits);
            ps.setBoolean(19, true);
            ps.setInt(20, idTransaction);
            ps.setBoolean(21, clientClub);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace ();
            new SystemErrorStage(e);
        } finally {
            ServerMySQL.statementClose (ps);
        }
    }

    public void updateValueVisitsDateBase(){

        PreparedStatement statement = null;
        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE ActionService SET numberVisits = ? Where idService = ?;"
            );

            statement.setDouble(1, this.numberVisits);
            statement.setInt(2, this.idService);
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }

        if (idType != 4 && numberVisits == 0){
            updateActionCheckDateBase(false);
        }

    }

    private void updateActionCheckDateBase (boolean actionCheck) {

        PreparedStatement statement = null;
        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "UPDATE ActionService SET actionCheck = ? Where idService = ?;"
            );

            statement.setBoolean(1, actionCheck);
            statement.setInt(2, this.idService);
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);

        }

    }

//    Количество дней прошедших с момента покупки
    public  int getIntDaysDatePay(){
        return new DateTime().getDaysDatePay(datePay);
    }

//    Дата окончания срока
    public String getStringDateLast(){
        return new DateTime().getDateLast(datePay , termDay);
    }


    private int getNewId(){
        int id = 1;
            PreparedStatement statement = null;
            ResultSet rs = null;

            try {

                statement = ServerMySQL.getConnection ().prepareStatement (
                        "SELECT idService FROM ActionService ORDER BY idService DESC LIMIT 0, 1;"
                );

                rs = statement.executeQuery ();

                if (rs.next ()) {
                    id = rs.getInt(1) + 1;
                }


            } catch (SQLException e) {
                e.printStackTrace ();
                new SystemErrorStage(e);
            } finally {
                ServerMySQL.statementClose (statement);
                ServerMySQL.resultSetClose (rs);
            }

            return id;
    }


    public VBox showVBoxInfo(){
        VBox vBoxMain = new VBox();
        vBoxMain.setMinSize(350,200);
        vBoxMain.setMaxSize(350,200);
        vBoxMain.setPadding(new Insets(10,10,10,10));
        vBoxMain.setSpacing(5);
        vBoxMain.setAlignment(Pos.TOP_RIGHT);

//        vBoxMain.getChildren().addAll(new TextField(toString()));
//
//        TextField[] t = new TextField[this.getClients().size()];
//        for(int i = 0; i < this.getClients().size(); i++) {
//            t[i] = new TextField( "Клиент "+ this.getClients().get(i).toStringIteam());
//            vBoxMain.getChildren().addAll(t[i]);
//        }
//
//        Button button = new Button("Списать");
//        vBoxMain.getChildren().addAll(button);
//
//        final ActionService actionService = this;
//
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle (ActionEvent event) {
//                new ActionServiceHistory(actionService, ActionUser.getUser()).WriteDateBase();
//                ActionService.updateHBoxGUIInfo();
//            }
//        });
//
//        if (this.idType == 5){
//            if (this.idTrainer > 0){
//                TextField textField = new TextField("Тренер " + this.getTrainer().getClient().toStringIteam());
//                vBoxMain.getChildren().addAll(textField);
//            }
//        }





        TextField textFieldName = new TextField(this.name);
        textFieldName.setEditable(false);




        Label labelVisit = new Label();
        if (this.idType == 3 || this.idType == 5){
            labelVisit.setText("Осталось посещений: " + this.numberVisits);
        } else {
            labelVisit.setText("Кол-во посещений не ограничено");
        }

        Label labelDatePay = new Label("От " + datePay);
        Label labelDateLast = new Label("До " + getStringDateLast());

        vBoxMain.getChildren().addAll(textFieldName, labelVisit, labelDatePay, labelDateLast);

//        System.out.println("Дата покупки " + datePay + "  срок в днях " + termDay);
//        System.out.println("Прошло дней c момента покупки: " + getIntDaysDatePay());
//        System.out.println("Дествителен до : " + getStringDateLast());
//        System.out.println("Осталось дней : " + (termDay - getIntDaysDatePay()));


        Label labelDay = new Label();
        vBoxMain.getChildren().addAll(labelDay);

        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(3);
        hBoxButtons.setAlignment(Pos.BOTTOM_RIGHT);

        Button buttonInfo= new Button("Инфо");
        buttonInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                getInfoStage(buttonInfo).show();
            }
        });

        Button buttonClose = new Button("Закрыть");
        buttonClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                updateActionCheckDateBase(false);
                updateHBoxGUIInfo();
            }
        });

        Button buttonDown = new Button("Регистрация посещения");

        buttonDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                regDown();
            }
        });

        hBoxButtons.getChildren().addAll(buttonInfo, buttonClose, buttonDown);
        vBoxMain.getChildren().addAll(hBoxButtons);

        if (termDay >= getIntDaysDatePay()){
            labelDay.setText("Осталось дней : " + (termDay - getIntDaysDatePay()));
            vBoxMain.getStyleClass().add("box-action-service");
            buttonDown.getStyleClass().add("button-gold");
            buttonClose.setManaged(false);
            buttonClose.setVisible(false);
        } else {
            labelDay.setText("Срок действия истек");
            vBoxMain.getStyleClass().add("box-action-service-false");
            buttonDown.setOpacity(0.6);
        }



        return vBoxMain;
    }


    public Stage getInfoStage(Node node){

        VBox mainVBox = new VBox();
        mainVBox.getStylesheets().add(new CssUrl().get());
        mainVBox.setPadding(new Insets(10,10,10,10));

        Stage newWindow = new Stage ();


        VBox vBoxInfo = new VBox();
        vBoxInfo.getStyleClass().add("box-class");
        vBoxInfo.setPadding(new Insets(10,10,10,10));


        Label labelInfo = new Label("Подробная информация");
        labelInfo.setPadding(new Insets(5,5,5,5));

        TextArea textAreaInfo = new TextArea(toStringInfo());
        textAreaInfo.setPadding(new Insets(5,5,5,5));
        textAreaInfo.setEditable(false);

        Label labelHistory = new Label("История посещений");
        labelHistory.setPadding(new Insets(5,5,5,5));

        ListView<String> listView = new ListView<>();
        Platform.runLater(()->{
            List<ActionServiceHistory> history = new ActionServiceHistory().getHistoryThisId(this.idService);
            for(int i = 0; i < history.size(); i++) {
                listView.getItems().add("№ " +( i + 1) + history.get(i).toStringInfo());
            }
        });
        listView.setPadding(new Insets(5,5,5,5));

        vBoxInfo.getChildren().addAll(labelInfo, textAreaInfo, labelHistory, listView);

        mainVBox.getChildren().add(vBoxInfo);

        int w = 1024;
        int h = 760;
        mainVBox.setMinWidth(h);
        Scene scene = new Scene (mainVBox);

        newWindow.setScene (scene);
        newWindow.initModality (Modality.WINDOW_MODAL);
        Stage mainStage = (Stage) node.getScene().getWindow();
        newWindow.initOwner (mainStage);
        newWindow.initStyle (StageStyle.UTILITY);

        return newWindow;
    }


    public void regDown(){

        new ActionServiceHistory(this, ActionUser.getUser()).WriteDateBase();
       ActionService.updateHBoxGUIInfo();
       TrainerList.updateListView();

    }


    public String toStringInfo(){

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Наименование : %s\n", this.name));

        sb.append(String.format("Дата покупки : %s\nЦена: %s\nДействует до : %s\n", this.datePay, this.cost, getStringDateLast()));

        if (this.idType == 3 || this.idType == 5){
            sb.append(String.format("Общее количество посещений : %s\nОтстаток : %s\n", this.numberVisitsPay, this.numberVisits));
        }

        for(int i = 0; i < getClients().size(); i++) {
            sb.append(String.format("ФИО клиента : %s\n", getClients().get(i).toStringIteam()));
        }

        if (this.idType == 5){
            sb.append(String.format("ФИО тренера : %s\n", getTrainer().getClient().toStringIteam()));
        }


        return sb.toString();

    }


    @Override
    public String toString () {
        return "ActionService{" +
               "idService=" + idService +
               ", idServiceMain=" + idServiceMain +
               ", idType=" + idType +
               ", name='" + name + '\'' +
               ", cost=" + cost +
               ", deleteCheck=" + deleteCheck +
               ", numberVisitsPay=" + numberVisitsPay +
               ", termDay=" + termDay +
               ", numberClients=" + numberClients +
               ", typeTrainer='" + typeTrainer + '\'' +
               ", sumTrainer=" + sumTrainer +
               ", timeControl=" + timeControl +
               ", addSumTimeControl=" + addSumTimeControl +
               ", idTrainer=" + idTrainer +
               ", idClient=" + idClient +
               ", datePay='" + datePay + '\'' +
               ", timePay='" + timePay + '\'' +
               ", numberVisits=" + numberVisits +
               ", actionCheck=" + actionCheck +
               '}';
    }
}