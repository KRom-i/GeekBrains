package Services.ImroptExcel;

import GUIMain.Styles.CssUrl;
import Logger.LOG;
import MySQLDB.ServerMySQL;
import Services.*;
import Services.StageService.StageNewService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceImport {

    private static List<OneTimeService> oneTimeServiceList;
    private static List<Product> productList;
    private static List<Subscription> subscriptionList;
    private static List<ClubCard> clubCardList;
    private static List<CoachServices> coachServicesList;


    public static String[] nameGroup = {"Разовые услуги", "Товары", "Абонементы", "Клубные карты", "Услуги тренера"};

    private static VBox vBox;
    private static ListView listView;

    public ServiceImport(){}

    public ServiceImport(VBox vBox, ListView listView){
        this.vBox = vBox;
        this.listView = listView;
    }


    public static void main (String[] args) {

        new ServiceImport().readExelPriceService();

    }


    public void readExelPriceService(){

        FileInputStream fileInputStream = null;
        HSSFWorkbook workbook = null;

        this.oneTimeServiceList = new ArrayList<> ();
        this.productList = new ArrayList<> ();
        this.subscriptionList = new ArrayList<> ();
        this.clubCardList = new ArrayList<> ();
        this.coachServicesList = new ArrayList<> ();

        try {

            fileInputStream = new FileInputStream (
                    new File ("FilesXLS\\Out\\ImportToDateBase.xls"));

            workbook = new HSSFWorkbook (fileInputStream);

            HSSFSheet sheet = workbook.getSheet ("servicePrice");

            LOG.info (String.format ("Количество строк [%s] лист [%s]", sheet.getLastRowNum (), sheet.getSheetName ()));

            for(int i = 2; i < sheet.getLastRowNum () + 1; i++) {

                int type = (int) (sheet.getRow (i).getCell (1).getNumericCellValue ());
                int group = (int) sheet.getRow (i).getCell (2).getNumericCellValue ();
                String name = sheet.getRow (i).getCell (3).getStringCellValue().toString();
                double cost = sheet.getRow (i).getCell (4).getNumericCellValue ();
                int balance = (int) sheet.getRow (i).getCell (6).getNumericCellValue ();


                switch (type){

                    case 1:
                        OneTimeService oneTimeService = new OneTimeService ();
                        oneTimeService.setType (type);
                        oneTimeService.setNumberGroup (group);
                        oneTimeService.setName (name);
                        oneTimeService.setCost (cost);
                        oneTimeService.setBalance (balance);
                        LOG.info (String.format ("Sheet [ %s ] read service [ %s ]", sheet.getSheetName (), oneTimeService.toString ()));
                        oneTimeServiceList.add (oneTimeService);
                        break;
                    case 2:
                        Product product = new Product ();
                        product.setType (type);
                        product.setNumberGroup (group);
                        product.setName (name);
                        product.setCost (cost);
                        product.setBalance (balance) ;
                        LOG.info (String.format ("Sheet [ %s ] read service [ %s ]", sheet.getSheetName (), product.toString ()));
                        productList.add (product);
                        break;
                    case 3:
                        Subscription subscription = new Subscription ();
                        subscription.setType (type);
                        subscription.setNumberGroup (group);
                        subscription.setName (name);
                        subscription.setCost (cost);
                        subscription.setBalance (balance) ;
                        subscription.setNumberVisits ((int) sheet.getRow (i).getCell (7).getNumericCellValue ());
                        subscription.setNumberClient ((int) sheet.getRow (i).getCell (9).getNumericCellValue ());
                        LOG.info (String.format ("Sheet [ %s ] read service [ %s ]", sheet.getSheetName (), subscription.toString ()));
                        subscriptionList.add (subscription);
                        break;
                    case 4:
                        ClubCard clubCard = new ClubCard ();
                        clubCard.setType (type);
                        clubCard.setNumberGroup (group);
                        clubCard.setName (name);
                        clubCard.setCost (cost);
                        clubCard.setBalance (balance) ;
                        clubCard.setTermDays ((int) sheet.getRow (i).getCell (8).getNumericCellValue ());
                        LOG.info (String.format ("Sheet [ %s ] read service [ %s ]", sheet.getSheetName (), clubCard.toString ()));
                        clubCardList.add (clubCard);
                        break;
                    case 5:
                        CoachServices coachServices = new CoachServices ();
                        coachServices.setType (type);
                        coachServices.setNumberGroup (group);
                        coachServices.setName (name);
                        coachServices.setCost (cost);
                        coachServices.setBalance (balance) ;
                        coachServices.setNumberVisits ((int) sheet.getRow (i).getCell (7).getNumericCellValue ());
                        coachServices.setNumberClient ((int) sheet.getRow (i).getCell (9).getNumericCellValue ());
                        coachServices.setTypeTren (sheet.getRow (i).getCell (10).getStringCellValue ());
                        coachServices.setSumTren((int) sheet.getRow (i).getCell (11).getNumericCellValue ());
                        LOG.info (String.format ("Sheet [ %s ] read service [ %s ]", sheet.getSheetName (), coachServices.toString ()));
                        coachServicesList.add (coachServices);
                        break;

                }


            }



        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            try {
                fileInputStream.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
            try {
                workbook.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }


        for(int i = 0; i < productList.size (); i++) {
            productList.get (i).addToDataBase ();
        }
//
        for(int i = 0; i < clubCardList.size (); i++) {
            clubCardList.get (i).addToDataBase ();
        }
//
        for(int i = 0; i < oneTimeServiceList.size (); i++) {
            oneTimeServiceList.get (i).addToDataBase ();
        }


        for(int i = 0; i < subscriptionList.size (); i++) {
            subscriptionList.get (i).addToDataBase ();
        }

        for(int i = 0; i < coachServicesList.size (); i++) {
            coachServicesList.get (i).addToDataBase ();
        }


    }


    public static void readDataBase(boolean delete){


            PreparedStatement statement = null;
            ResultSet rs = null;

            ServerMySQL.getConnection();

            oneTimeServiceList = new ArrayList<> ();
            productList = new ArrayList<> ();
            subscriptionList = new ArrayList<> ();
            clubCardList = new ArrayList<> ();
            coachServicesList = new ArrayList<> ();

            try {

                statement = ServerMySQL.getConnection().prepareStatement(
                        "select * from servicePrice Where DELETE_CHECK = ? ORDER BY id_type, id_group, cost;"
                );

                statement.setBoolean (1,delete);

                rs = statement.executeQuery();


                while (rs.next()){

                    int id = rs.getInt (1);
                    int type = rs.getInt (2);
                    int group = rs.getInt (3);
                    String name = rs.getString (4);
                    double cost = rs.getDouble (5);

                    int balance = rs.getInt (7);
                    int visit = rs.getInt (8);
                    int days = rs.getInt (9);
                    int clients = rs.getInt (10);
                    String typeTre =  rs.getString (11);
                    double sumTre = rs.getDouble (12);
                    boolean timeControl = rs.getBoolean (13);;
                    double addSumTimeControl =  rs.getDouble (14);
                    int idTre = rs.getInt (15);

//                    LOG.info (String.format ("Выгрузка услуги из БД НАИМЕНОВАНИЕ [%s]  ЦЕНА [%s] ОСТАТОК [%s] ПОСЕЩЕНИЙ [%s] " +
//                                             "КЛИЕНТОВ [%s] ДНЕЙ [%s] ДОЛЯ ТРЕНЕРА [%s] КОНТРОЛЬ ВРЕМЕНИ [%s] ДОП СУММА [%s]",
//                                             name, cost, balance, visit, clients, days, sumTre, timeControl, addSumTimeControl));

                        if (type == 1) {
                            OneTimeService oneTimeService = new OneTimeService (id, type, group, name, cost, balance, timeControl, addSumTimeControl);
                            oneTimeServiceList.add (oneTimeService);
                        } else if (type == 2) {
                            Product product = new Product (id, type, group, name, cost, balance, timeControl, addSumTimeControl);
                            productList.add (product);
                        } else if (type == 3) {
                            Subscription subscription = new Subscription (id, type, group, name, cost, balance, visit, clients, days, timeControl, addSumTimeControl);
                            subscriptionList.add (subscription);
                        } else if (type == 4) {
                            ClubCard clubCard = new ClubCard (id, type, group, name, cost, balance, days, timeControl, addSumTimeControl);
                            clubCardList.add (clubCard);
                        } else if (type == 5) {
                            CoachServices coachServices = new CoachServices (id, type, group, name, cost, balance, visit,  clients, days, typeTre, sumTre, timeControl, addSumTimeControl, idTre);
                            coachServicesList.add (coachServices);
                          }



                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ServerMySQL.statementClose(statement);
                ServerMySQL.resultSetClose(rs);
            }



    }

    public static void updateVbox(int visPanel, int idServiceColor, int visBox){

        Platform.runLater(()->{

        vBox.getChildren ().clear ();
//        Label label = new Label("Выбор услуги/товара");
//        label.setPadding(new Insets(20,0,20,0));
//        vBox.getChildren().add(label);




        int x = nameGroup.length;

        ListView<HBox>[] listViews = new ListView[x];
        TitledPane[] titledPanes = new TitledPane[x];

        listView.getItems().clear ();

        for(int i = 0; i < x; i++) {

            listViews[i] = new ListView<> ();


            switch (i){
                case 0:
                    listView.getItems().add( "     " + nameGroup[i]);
                    for(int j = 0; j < oneTimeServiceList.size (); j++) {

                        if (oneTimeServiceList.get(j).getId() == idServiceColor && visBox == 1){
                            HBox hBox1 = oneTimeServiceList.get(j).getHBoxInfo ();
                            hBox1.getStyleClass().add("my-dox-class-true");
                            listViews[i].getItems ().add( hBox1);
                        } else {
                            listViews[i].getItems ().add (j, oneTimeServiceList.get(j).getHBoxInfo ());
                        }

                        if (oneTimeServiceList.get(j).getId() == idServiceColor && visBox == 2){
                            HBox hBox2 = oneTimeServiceList.get(j).getHBoxEdit ();
                            hBox2.getStyleClass().add("my-dox-class-true");
                            listView.getItems().add( hBox2);
                        } else {
                            listView.getItems().add( oneTimeServiceList.get(j).getHBoxEdit ());
                        }

                    }
                    listView.getItems().add( "     ");
                    break;
                case 1:
                    listView.getItems().add( "     " + nameGroup[i]);
                    
                    for(int j = 0; j < productList.size (); j++) {

                        if (productList.get(j).getId() == idServiceColor && visBox == 1){
                            HBox hBox1 = productList.get(j).getHBoxInfo ();
                            hBox1.getStyleClass().add("my-dox-class-true");
                            listViews[i].getItems ().add( hBox1);
                        } else {
                            listViews[i].getItems ().add (j, productList.get(j).getHBoxInfo ());
                        }

                        if (productList.get(j).getId() == idServiceColor && visBox == 2){
                            HBox hBox2 = productList.get(j).getHBoxEdit ();
                            hBox2.getStyleClass().add("my-dox-class-true");
                            listView.getItems().add( hBox2);
                        } else {
                            listView.getItems().add( productList.get(j).getHBoxEdit ());
                        }
                        
                    }
                    listView.getItems().add( "     ");
                    break;
                case 2:
                    listView.getItems().add( "     " + nameGroup[i]);

                    for(int j = 0; j < subscriptionList.size (); j++) {

                        if (subscriptionList.get(j).getId() == idServiceColor && visBox == 1){
                            HBox hBox1 = subscriptionList.get(j).getHBoxInfo ();
                            hBox1.getStyleClass().add("my-dox-class-true");
                            listViews[i].getItems ().add( hBox1);
                        } else {
                            listViews[i].getItems ().add (j, subscriptionList.get(j).getHBoxInfo ());
                        }

                        if (subscriptionList.get(j).getId() == idServiceColor && visBox == 2){
                            HBox hBox2 = subscriptionList.get(j).getHBoxEdit ();
                            hBox2.getStyleClass().add("my-dox-class-true");
                            listView.getItems().add( hBox2);
                        } else {
                            listView.getItems().add( subscriptionList.get(j).getHBoxEdit ());
                        }

                    }
                    listView.getItems().add( "     ");
                    break;
                case 3:
                    listView.getItems().add( "     " + nameGroup[i]);
                    
                    for(int j = 0; j < clubCardList.size (); j++) {
                        if (clubCardList.get(j).getId() == idServiceColor && visBox == 1){
                            HBox hBox1 = clubCardList.get(j).getHBoxInfo ();
                            hBox1.getStyleClass().add("my-dox-class-true");
                            listViews[i].getItems ().add( hBox1);
                        } else {
                            listViews[i].getItems ().add (j, clubCardList.get(j).getHBoxInfo ());
                        }

                        if (clubCardList.get(j).getId() == idServiceColor && visBox == 2){
                            HBox hBox2 = clubCardList.get(j).getHBoxEdit ();
                            hBox2.getStyleClass().add("my-dox-class-true");
                            listView.getItems().add( hBox2);
                        } else {
                            listView.getItems().add( clubCardList.get(j).getHBoxEdit ());
                        }
                        
                    }
                    listView.getItems().add( "     ");
                    break;
                case 4:
                    listView.getItems().add( "     " + nameGroup[i]);
                    
                    for(int j = 0; j < coachServicesList.size (); j++) {
                        if (coachServicesList.get(j).getId() == idServiceColor && visBox == 1){
                            HBox hBox1 = coachServicesList.get(j).getHBoxInfo ();
                            hBox1.getStyleClass().add("my-dox-class-true");
                            listViews[i].getItems ().add( hBox1);
                        } else {
                            listViews[i].getItems ().add (j, coachServicesList.get(j).getHBoxInfo ());
                        }

                        if (coachServicesList.get(j).getId() == idServiceColor && visBox == 2){
                            HBox hBox2 = coachServicesList.get(j).getHBoxEdit ();
                            hBox2.getStyleClass().add("my-dox-class-true");
                            listView.getItems().add( hBox2);
                        } else {
                            listView.getItems().add( coachServicesList.get(j).getHBoxEdit ());
                        }
                    }
                    listView.getItems().add( "     ");
                    break;

            }

            titledPanes[i] = new TitledPane(nameGroup[i], listViews[i]);
            if (i != visPanel) {
                titledPanes[i].setExpanded(false);
            }
            titledPanes[i].setPadding(new Insets(0,10,0,10));
            titledPanes[i].setAnimated(true);
            final int I = i;
            titledPanes[i].setOnMouseClicked(new EventHandler<MouseEvent> () {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for(int j = 0; j < titledPanes.length; j++) {
                        if (j == I){
                            titledPanes[j].setExpanded(true);
                        } else {
                            titledPanes[j].setExpanded(false);
                        }
                    }
                }
            });
            vBox.setPadding(new Insets(10,0,10,0));
            vBox.getChildren().add(titledPanes[i]);

        }
        });
    }





    public static List<String> getColServicesString(int numberTypeService, int numCol){

        PreparedStatement statement = null;
        ResultSet rs = null;

        List<String> stringList = new ArrayList<> ();

        try {

            statement = ServerMySQL.getConnection().prepareStatement(
                    "select * from servicePrice Where DELETE_CHECK = ? and ID_TYPE = ? ORDER BY id_type, id_group, cost;;"
            );

            statement.setBoolean (1,false);
            statement.setInt (2, numberTypeService);
            rs = statement.executeQuery();


            while (rs.next()){
                String srt = rs.getString (numCol);
                if (!stringList.contains(srt)){
                    stringList.add (srt);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return stringList;
    }

    public static List<String> getColServicesInt(int numberTypeService, String nameCol){

        PreparedStatement statement = null;
        ResultSet rs = null;

        List<String> stringList = new ArrayList<> ();

        try {

//            statement = ServerMySQL.getConnection().prepareStatement(
//                    "select * from servicePrice Where DELETE_CHECK = ? and ID_TYPE = ? ORDER BY id_type, id_group, cost;"
//            );
            statement = ServerMySQL.getConnection().prepareStatement(
                    String.format("select %s from servicePrice Where DELETE_CHECK = ? and ID_TYPE = ? ORDER BY %s;", nameCol, nameCol)
            );

            statement.setBoolean (1,false);
            statement.setInt (2, numberTypeService);
            rs = statement.executeQuery();

            while (rs.next()){
                String srt = String.valueOf(rs.getInt (1));
                if (!stringList.contains(srt) && !srt.equalsIgnoreCase("0")){
                    stringList.add (srt);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }


        return stringList;
    }

    public static Integer getColServicesIntNumberGroup(int numberTypeService, String nameCol, String nameService){

        PreparedStatement statement = null;
        ResultSet rs = null;

        int i = 1;

        try {

//            statement = ServerMySQL.getConnection().prepareStatement(
//                    "select * from servicePrice Where DELETE_CHECK = ? and ID_TYPE = ? ORDER BY id_type, id_group, cost;"
//            );
            statement = ServerMySQL.getConnection().prepareStatement(
                    String.format("select %s from servicePrice Where DELETE_CHECK = ? and ID_TYPE = ? and NAME = ?;", nameCol)
            );

            statement.setBoolean (1,false);
            statement.setInt (2, numberTypeService);
            statement.setString (3, nameService);
            rs = statement.executeQuery();

            while (rs.next()){
                i = rs.getInt (1);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }


        return i;
    }

    public static List<String> getColServicesDouble(int numberTypeService, String nameCol){

        PreparedStatement statement = null;
        ResultSet rs = null;

        List<String> stringList = new ArrayList<> ();

        try {

//            statement = ServerMySQL.getConnection().prepareStatement(
//                    "select * from servicePrice Where DELETE_CHECK = ? and ID_TYPE = ? ORDER BY id_type, id_group, cost;"
//            );
            statement = ServerMySQL.getConnection().prepareStatement(
                    String.format("select %s from servicePrice Where DELETE_CHECK = ? and ID_TYPE = ? ORDER BY %s;", nameCol, nameCol)
            );

            statement.setBoolean (1,false);
            statement.setInt (2, numberTypeService);
            rs = statement.executeQuery();

            while (rs.next()){
                String srt = String.valueOf(rs.getDouble (1));
                if (!stringList.contains(srt) && !srt.equalsIgnoreCase("0")){
                    stringList.add (srt);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }


        return stringList;
    }

}
