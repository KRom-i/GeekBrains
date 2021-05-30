package ImroptExcel;

import Logger.LOG;
import Services.*;
import WorkDataBase.ConnectDateBase;
import javafx.application.Platform;
import javafx.event.EventHandler;
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

    private List<OneTimeService> oneTimeServiceList;
    private List<Product> productList;
    private List<Subscription> subscriptionList;
    private List<ClubCard> clubCardList;
    private List<CoachServices> coachServicesList;
    public static String[] nameGroup = {"Разовые услуги", "Товары", "Абонементы", "Клубные карты", "Услуги тренера"};

    private VBox vBox;
    private VBox editVBox;

    public ServiceImport(VBox vBox, VBox editVBox){
        this.vBox = vBox;
        this.editVBox = editVBox;
    }


    public  void readExelPriceService(){

        FileInputStream fileInputStream = null;
        HSSFWorkbook workbook = null;

        this.oneTimeServiceList = new ArrayList<> ();
        this.productList = new ArrayList<> ();
        this.subscriptionList = new ArrayList<> ();
        this.clubCardList = new ArrayList<> ();
        this.coachServicesList = new ArrayList<> ();

        try {

            fileInputStream = new FileInputStream (
                    new File ("C:\\Users\\007KutkinRI\\Desktop\\test.xls"));

            workbook = new HSSFWorkbook (fileInputStream);

            HSSFSheet sheet = workbook.getSheet ("Лист1");

            LOG.info (String.format ("Количество строк [%s] лист [%s]", sheet.getLastRowNum (), sheet.getSheetName ()));

            for(int i = 2; i < sheet.getLastRowNum () + 1; i++) {

                int type = (int) (sheet.getRow (i).getCell (1).getNumericCellValue ());
                int group = (int) sheet.getRow (i).getCell (2).getNumericCellValue ();
                String name = sheet.getRow (i).getCell (3).getStringCellValue ();
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


//        for(int i = 0; i < productList.size (); i++) {
//            productList.get (i).addToDataBase ();
//        }

//        for(int i = 0; i < clubCardList.size (); i++) {
//            clubCardList.get (i).addToDataBase ();
//        }
//
//        for(int i = 0; i < oneTimeServiceList.size (); i++) {
//            oneTimeServiceList.get (i).addToDataBase ();
//        }
//
//
//        for(int i = 0; i < subscriptionList.size (); i++) {
//            subscriptionList.get (i).addToDataBase ();
//        }
//
//        for(int i = 0; i < coachServicesList.size (); i++) {
//            coachServicesList.get (i).addToDataBase ();
//        }


    }


    public void readServicePrice(boolean delete){


            PreparedStatement statement = null;
            ResultSet rs = null;

            ConnectDateBase.connect ();

            this.oneTimeServiceList = new ArrayList<> ();
            this.productList = new ArrayList<> ();
            this.subscriptionList = new ArrayList<> ();
            this.clubCardList = new ArrayList<> ();
            this.coachServicesList = new ArrayList<> ();

            try {

                statement = ConnectDateBase.connection.prepareStatement(
                        "select * from service_price Where DELETE_CHECK = ? ORDER BY id_type, id_group, cost;"
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



                        if (type == 1) {
                            OneTimeService oneTimeService = new OneTimeService ();
                            oneTimeService.setId (id);
                            oneTimeService.setType (type);
                            oneTimeService.setNumberGroup (group);
                            oneTimeService.setName (name);
                            oneTimeService.setCost (cost);
                            oneTimeService.setBalance (balance);
                            LOG.info (String.format ("Read service  DataBase [ %s ]",oneTimeService.toString ()));
                            oneTimeServiceList.add (oneTimeService);
                        } else if (type == 2) {
                            Product product = new Product ();
                            product.setId (id);
                            product.setType (type);
                            product.setNumberGroup (group);
                            product.setName (name);
                            product.setCost (cost);
                            product.setBalance (balance);
                            LOG.info (String.format ("Read service  DataBase [ %s ]",product.toString ()));
                            productList.add (product);
                        } else if (type == 3) {
                            Subscription subscription = new Subscription ();
                            subscription.setId (id);
                            subscription.setType (type);
                            subscription.setNumberGroup (group);
                            subscription.setName (name);
                            subscription.setCost (cost);
                            subscription.setBalance (balance) ;
                            subscription.setNumberVisits (rs.getInt (8));
                            LOG.info (String.format ("Read service  DataBase [ %s ]", subscription.toString ()));
                            subscriptionList.add (subscription);
                        } else if (type == 4) {
                            ClubCard clubCard = new ClubCard ();
                            clubCard.setId (id);
                            clubCard.setType (type);
                            clubCard.setNumberGroup (group);
                            clubCard.setName (name);
                            clubCard.setCost (cost);
                            clubCard.setBalance (balance) ;
                            clubCard.setTermDays (rs.getInt (9));
                            LOG.info (String.format ("Read service  DataBase [ %s ]", clubCard.toString ()));
                            clubCardList.add (clubCard);
                        } else if (type == 5) {
                            CoachServices coachServices = new CoachServices ();
                            coachServices.setId (id);
                            coachServices.setType (type);
                            coachServices.setNumberGroup (group);
                            coachServices.setName (name);
                            coachServices.setCost (cost);
                            coachServices.setBalance (balance) ;
                            coachServices.setNumberVisits (rs.getInt (8));
                            coachServices.setNumberClient (rs.getInt (10));
                            coachServices.setTypeTren (rs.getString (11));
                            LOG.info (String.format ("Read service  DataBase [ %s ]", coachServices.toString ()));
                            coachServicesList.add (coachServices);
                          }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectDateBase.statementClose(statement);

            }



    }

    public void updateVbox(){

        Platform.runLater(()->{

        vBox.getChildren ().clear ();
        editVBox.getChildren ().clear ();

        int x = nameGroup.length;

        ListView<HBox>[] listViews = new ListView[x];
        TitledPane[] titledPanes = new TitledPane[x];

        ListView<HBox>[] listViewsEdit = new ListView[x];
        TitledPane[] titledPanesEdit = new TitledPane[x];

        for(int i = 0; i < x; i++) {

            listViews[i] = new ListView<> ();
            listViewsEdit[i] = new ListView<> ();

            switch (i){
                case 0:
                    for(int j = 0; j < this.oneTimeServiceList.size (); j++) {
                        listViews[i].getItems ().add (j, this.oneTimeServiceList.get(j).getHBoxInfo ());
                        listViewsEdit[i].getItems ().add (j, this.oneTimeServiceList.get(j).getHBoxEdit ());
                    }
                    break;
                case 1:
                    for(int j = 0; j < this.productList.size (); j++) {
                        listViews[i].getItems ().add (j, this.productList.get(j).getHBoxInfo ());
                        listViewsEdit[i].getItems ().add (j, this.productList.get(j).getHBoxEdit ());
                    }
                    break;
                case 2:
                    for(int j = 0; j < this.subscriptionList.size (); j++) {
                        listViews[i].getItems ().add (j, this.subscriptionList.get(j).getHBoxInfo ());
                        listViewsEdit[i].getItems ().add (j, this.subscriptionList.get(j).getHBoxEdit ());
                    }
                    break;
                case 3:
                    for(int j = 0; j < this.clubCardList.size (); j++) {
                        listViews[i].getItems ().add (j, this.clubCardList.get(j).getHBoxInfo ());
//                        listViewsEdit[i].getItems ().add (j, this.clubCardList.get(j).getHBoxEdit ());
                    }
                    break;
                case 4:
                    for(int j = 0; j < this.coachServicesList.size (); j++) {
                        listViews[i].getItems ().add (j, this.coachServicesList.get(j).getHBoxInfo ());
                        listViewsEdit[i].getItems ().add (j, this.coachServicesList.get(j).getHBoxEdit ());
                    }
                    break;

            }

            titledPanes[i] = new TitledPane(nameGroup[i], listViews[i]);
            titledPanes[i].setExpanded(false);
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
            vBox.getChildren().add(titledPanes[i]);

            titledPanesEdit[i] = new TitledPane(nameGroup[i], listViewsEdit[i]);
            titledPanesEdit[i].setExpanded(false);
            titledPanesEdit[i].setAnimated(true);
            final int J = i;
            titledPanesEdit[i].setOnMouseClicked(new EventHandler<MouseEvent> () {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for(int j = 0; j < titledPanesEdit.length; j++) {
                        if (j == J){
                            titledPanesEdit[j].setExpanded(true);
                        } else {
                            titledPanesEdit[j].setExpanded(false);
                        }
                    }
                }
            });
            editVBox.getChildren().add(titledPanesEdit[i]);
        }
        });
    }
}
