package GUIMain;

import Services.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MyTitlePanel {

    public MyTitlePanel(VBox vBox, int x) {

        vBox.setSpacing (5);

        ListView<HBox>[] listViews = new ListView[x];
        TitledPane[] titledPanes = new TitledPane[x];

//        Тест сортировки
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("ProductTest", 400, 10, 1));
        productList.add(new Product("ProductTest", 100, 15, 1));
        productList.add(new Product("ProductTest", 300, 10, 1));
        productList.add(new Product("ProductTest", 200, 0, 1));


        for(int i = 0; i < x; i++) {
            listViews[i] = new ListView<>();

            if (i == 0){
                listViews[i].getItems().add(new OneTimeService("OneTimeServiceTest", 150.50, 1).getHBoxInfo());
            } else if (i == 1){
                for(int j = 0; j < productList.size(); j++) {
                    listViews[i].getItems().add(j, productList.get(j).getHBoxInfo());
                }
            } else if (i == 2){
                listViews[i].getItems().add(new Subscription("SubscriptionTest", 1500, 15, 12, 1).getHBoxInfo());
            } else if (i == 3){
                listViews[i].getItems().add(new ClubCard("ClubCardTest", 1500, 0, 183, 1).getHBoxInfo());
                listViews[i].getItems().add(new ClubCard("ClubCardTest", 1500, 15, 31, 1).getHBoxInfo());
            } else if (i == 4){
                listViews[i].getItems().add(new CoachServices("CoachServicesTest", 300,  30, 1).getHBoxInfo());
                listViews[i].getItems().add(new CoachServices("CoachServicesTest", 300, 30, 1).getHBoxInfo());
            }

            titledPanes[i] = new TitledPane("titledPane " + (i + 1) ,listViews[i]);
            titledPanes[i].setExpanded(false);
            titledPanes[i].setAnimated(true);
            final int I = i;
            titledPanes[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        }

    }

}
