package Services.ActionServices;

import Format.DateTime;
import Format.Round;
import GUIMain.CustomStage.SystemErrorStage;
import MySQLDB.ServerMySQL;
import WorkDataBase.Trainers.Trainer;
import WorkDataBase.UserSpartak;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActionServiceHistory {

    private int idActionServiceHistory;
    private int idActionService;
    private int idUser;
    private int idTrainer;
    private String nameHistory;
    private String time;
    private String date;
    private int valueVisits;
    private double sumTrainers;

    private ActionService actionService;

    public ActionServiceHistory () {

    }

    public ActionServiceHistory (ActionService actionService, UserSpartak userSpartak) {

        this.actionService = actionService;

        this.idActionService = actionService.getIdService();
        this.idUser = userSpartak.getId();

        if (actionService.getIdType() == 5){
            this.idTrainer = actionService.getIdTrainer();
            this.sumTrainers = getSumTrainers();
            this.nameHistory = "Списание тренировки Администратором ФЦ";
        } else {
            this.idTrainer = 0;
            this.sumTrainers = 0;
            this.nameHistory = "Регистрация посещения Администратором ФЦ";
        }


        this.time = new DateTime().currentTime();
        this.date = new DateTime().currentDate();

        if (actionService.getIdType() != 4){
            this.valueVisits = actionService.getNumberVisits() - 1;
        } else {
            this.valueVisits = 999_999;
        }

        actionService.setNumberVisits(valueVisits);


    }

    private double getSumTrainers(){

        double sumTrainer = 0;

        if (actionService.getTypeTrainer().equalsIgnoreCase("fixed")){

            sumTrainer = actionService.getSumTrainer();

        } else if (actionService.getTypeTrainer().equalsIgnoreCase("percent")){

            double cost = actionService.getCost();
            double visit = actionService.getNumberVisitsPay();

            double sumOneVisit = cost / visit;

            if (actionService.isClientClub()){

                sumTrainer = sumOneVisit  * (1 - actionService.getSumTrainer());

            } else {
                sumTrainer = sumOneVisit  *  actionService.getSumTrainer();
            }

        }

        return sumTrainer;
    }

    public void WriteDateBase(){
        writeHistoryTable();
        actionService.updateValueVisitsDateBase();
        if (actionService.getIdType() == 5){
            if (sumTrainers > 0){
                new Trainer().getOneTrainer(idTrainer).setBalanceDateBase(-sumTrainers);
            }
        }
    }

    private void writeHistoryTable () {
        PreparedStatement ps = null;

        try {

            String q = ("INSERT INTO ActionServiceHistory SET idActionService = ?, idUser = ?, idTrainer = ?, nameHistory = ?," +
                        " time = ?, date = ?, valueVisits = ?, sumTrainers = ?;");
            ps = ServerMySQL.getConnection ().prepareStatement (q);

            ps.setInt(1, idActionService);
            ps.setInt(2, idUser);
            ps.setInt(3, idTrainer);
            ps.setString(4, nameHistory);
            ps.setString(5, time);
            ps.setString(6, date);
            ps.setInt(7, valueVisits);
            ps.setDouble(8, sumTrainers);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace ();
            new SystemErrorStage(e);
        } finally {
            ServerMySQL.statementClose (ps);
        }

    }


    public List<ActionServiceHistory> getHistoryThisId (int idService) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ActionServiceHistory> historyList = new ArrayList<>();
        try {

            String q = ("SELECT * FROM ActionServiceHistory WHERE idActionService = ? ORDER BY idActionServiceHistory;");
            ps = ServerMySQL.getConnection ().prepareStatement (q);

            ps.setInt(1, idService);

            rs = ps.executeQuery();

            while (rs.next()){

                ActionServiceHistory action = new ActionServiceHistory();

                action.idActionServiceHistory = rs.getInt("idActionServiceHistory");
                action.idActionService = rs.getInt("idActionService");
                action.idTrainer = rs.getInt("idTrainer");

                action.nameHistory = rs.getString("nameHistory");
                action.time = rs.getString("time");
                action.date = rs.getString("date");

                action.valueVisits = rs.getInt("valueVisits");
                action.sumTrainers = rs.getDouble("sumTrainers");

                historyList.add(action);
            }

        } catch (SQLException e) {
            e.printStackTrace ();
            new SystemErrorStage(e);
        } finally {
            ServerMySQL.statementClose (ps);
            ServerMySQL.resultSetClose(rs);
        }

        return historyList;
    }


    public String toStringInfo(){
        return String.format(" - Дата %s - Время %s - %s",  this.date, this.time ,this.nameHistory);
    }
}
