package GUIMain;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class MyListView {

    static private ListView listView;
    static private VBox vBox;

    public MyListView(ListView listView, VBox vBox) {
        this.listView = listView;
        this.vBox = vBox;
        vBox.setSpacing (0.5);

        Button button = new Button ("Clear");
        button.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent actionEvent) {
                vBox.getChildren ().clear ();
            }
        });
        vBox.getChildren ().add (button);
    }

    public static void addButton(int b){

        Button[] buttons = new Button[b];
        for(int j = 0; j < buttons.length; j++) {
            buttons[j] = new Button ("" + j);
            final double d = (double) j;
            buttons[j].setOnAction (new EventHandler<ActionEvent> () {
                @Override
                public void handle(ActionEvent actionEvent) {
                    vBox.getChildren ().clear ();
                }
            });
            listView.getItems ().add (buttons[j]);
        }

    }

}
