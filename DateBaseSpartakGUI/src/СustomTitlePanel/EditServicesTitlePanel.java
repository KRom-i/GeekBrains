package СustomTitlePanel;

import Services.*;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditServicesTitlePanel {


        public EditServicesTitlePanel(VBox vBox,int x) {

            ListView<HBox>[] listViews = new ListView[x];
            TitledPane[] titledPanes = new TitledPane[x];

            for(int i = 0; i < x; i++) {
                listViews[i] = new ListView<>();

                if (i == 0){
                    listViews[i].getItems().add(new OneTimeService("OneTimeServiceTest", 150.50, 1).getHBoxEdit());
                } else if (i == 1){
                    listViews[i].getItems().add(new Product("ProductTest", 300, 15, 1).getHBoxEdit());
                    listViews[i].getItems().add(new Product("ProductTest", 300, 0, 1).getHBoxEdit());
                } else if (i == 2){
                    listViews[i].getItems().add(new Subscription("SubscriptionTest", 1500, 15, 12, 1).getHBoxEdit());
                    listViews[i].getItems().add(new Subscription("SubscriptionTest", 1500, 0, 12, 1).getHBoxEdit());
                } else if (i == 3){
                    listViews[i].getItems().add(new ClubCard("ClubCardTest", 1500, 0, 180, 1).getHBoxInfo());
                    listViews[i].getItems().add(new ClubCard("ClubCardTest", 1500, 15, 30, 1).getHBoxInfo());
                } else if (i == 4){
                    listViews[i].getItems().add(new CoachServices("CoachServicesTest", 300,  30, 1).getHBoxEdit());
                    listViews[i].getItems().add(new CoachServices("CoachServicesTest", 300, 30, 1).getHBoxEdit());
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
