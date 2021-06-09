package WorkDataBase.Trainers;

import MySQLDB.ServerMySQL;
import WorkDataBase.ClientClass;
import WorkDataBase.ClientDataBase;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.apache.poi.ss.formula.functions.T;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerList {

    private static ListView<HBox> listView;

    public TrainerList (ListView<HBox> listView) {
        this.listView = listView;
    }

    public static void updateListView(){
        List<Trainer> trainerList = get();
        Platform.runLater(()->{
            listView.getItems().clear();
            for(int i = 0; i < trainerList.size(); i++) {
                listView.getItems().add(trainerList.get(i).getHBoxInfo());
            }

        });

    }

    public static List<Trainer> get(){

        PreparedStatement statement = null;
        ResultSet rs = null;

        List<Trainer>  trainerLis = new ArrayList<>();

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM trainers;"
            );


            rs = statement.executeQuery();


            while (rs.next()) {

                int id = rs.getInt(1);
                ClientClass client = ClientDataBase.getClientDateBase(rs.getInt(2));
                double balance = rs.getDouble(4);
                Trainer trainer = new Trainer(id, client, balance);
                trainerLis.add(trainer);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return trainerLis;
    }

}
