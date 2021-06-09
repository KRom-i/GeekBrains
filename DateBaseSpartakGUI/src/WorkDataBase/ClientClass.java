package WorkDataBase;

import MySQLDB.ServerMySQL;
import Services.ActionServices.ActionService;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClientClass {


    private int id;
    private String firstName = "";
    private String lastName = "";
    private String patronymicName = "";
    private String telephone = "";
    private String dateBirth = "";
    private String Email = "";
    private String infoClient = "";
    private List<Object> subscription;
    private final String NAME_Class_DB = "Client";

    private String codRegTelegram;
    private boolean telegramReg;

    public String nameClassDataBase(){
        return NAME_Class_DB;
    }

    public ClientClass(){
    }

    public String getCodRegTelegram () {
        return codRegTelegram;
    }

    public void setCodRegTelegram (String codRegTelegram) {
        this.codRegTelegram = codRegTelegram;
    }

    public boolean isTelegramReg () {
        return telegramReg;
    }

    public void setTelegramReg (boolean telegramReg) {
        this.telegramReg = telegramReg;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getInfoClient() {
        return infoClient;
    }

    public void setInfoClient(String infoClient) {
        this.infoClient = infoClient;
    }

    public List<Object> getSubscription() {
        return subscription;
    }

    public void addSubscription(Object subscription) {

        if (this.subscription == null){
            this.subscription = new ArrayList<>();
        }

        this.subscription.add(subscription);
    }

    public boolean booleanFind(String findStr){

            String[] s = findStr.split (" ");

            boolean checkLastName = false;
            boolean checkFirstName = false;
            boolean checkPatronymicName = false;

            int control = 0;

            for(int i = 0; i < s.length; i++) {

                s[i] = editText(s[i]);

                if (this.getLastName().startsWith(s[i]) && !checkLastName) {
                    checkLastName = true;
                    control++;
                } else if (this.getFirstName().startsWith(s[i]) && !checkFirstName) {
                    checkFirstName = true;
                    control++;
                } else if (this.getPatronymicName().startsWith(s[i]) && !checkPatronymicName) {
                    checkPatronymicName = true;
                    control++;
                }

            }

            return control == s.length;


    }



    private String editText(String str){

        String newString = "";
        if (str.length () > 0) {
            String[] strings = str.split ("");
            for(int i = 0; i < strings.length; i++) {
                if (i == 0) {
                    strings[i] = strings[i].toUpperCase (Locale.ROOT);
                } else {
                    strings[i] = strings[i].toLowerCase (Locale.ROOT);
                }
                newString += strings[i];
            }
        }
        return newString;

    }


    @Override
    public String toString (){

        return "ClientClass{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymicName='" + patronymicName + '\'' +
                ", telephone='" + telephone + '\'' +
                ", dateBirth='" + dateBirth + '\'' +
                ", Email='" + Email + '\'' +
                ", infoClient='" + infoClient + '\'' +
                ", subscription=" + subscription +
                ", NAME_Class_DB='" + NAME_Class_DB + '\'' +
                '}';

    }


    public String toStringIteam (){

        return String.format ("%s %s %s", lastName, firstName, patronymicName);

    }

    public String toStringInfo (){

        return String.format ("ID[%s] ФИО[%s %s %s] Дата рождения[%s]", getId (), lastName, firstName, patronymicName, getDateBirth ());

    }

    public HBox infoHBox(){
        Label labelID = new Label ("ID: ");
        TextField textFieldID = new TextField (getId () + "");
        textFieldID.setMinWidth (60);
        textFieldID.setMaxWidth (60);
        textFieldID.setMaxHeight (15);
        textFieldID.setEditable (false);

        Label labelName = new Label ("ФИО: ");
        TextField textFieldName= new TextField (toStringIteam ());
        textFieldName.setMinWidth (300);
        textFieldName.setMaxHeight (15);
        textFieldName.setEditable (false);

        Label labelBirthDay= new Label ("Дата рождения: ");
        TextField textFieldBirthDay= new TextField (getDateBirth ());
        textFieldBirthDay.setMinWidth (100);
        textFieldBirthDay.setMaxWidth (100);
        textFieldBirthDay.setMaxHeight (15);
        textFieldBirthDay.setEditable (false);


        Label labelTel= new Label ("Телефон: ");
        TextField textFieldTel= new TextField (getTelephone ());
        textFieldTel.setMinWidth (150);
        textFieldTel.setMaxWidth (150);
        textFieldTel.setMaxHeight (15);
        textFieldTel.setEditable (false);

        HBox hBox = new HBox ();
        hBox.setAlignment (Pos.CENTER_LEFT);
        hBox.setSpacing (20);
        hBox.getChildren ().addAll (labelID, textFieldID,
                labelName,textFieldName,
                labelBirthDay , textFieldBirthDay,
                labelTel, textFieldTel);
//        hBox.getChildren ().addAll (textFieldID, textFieldName, textFieldBirthDay, textFieldTel);

        return hBox;
    }


    public void getRegTelegramInfo(){

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement("SELECT * FROM telegram WHERE id_client = ? and status = ? and deleteCheck = ?;");

            statement.setInt (1, this.getId());
            statement.setString (2, "client");
            statement.setBoolean (3, false);

            rs = statement.executeQuery();

            if (rs.next()){
                String cod = rs.getString ("codReg");

                if (cod.length() > 2){
                    this.setCodRegTelegram(cod);
                }
                this.setTelegramReg(true);
            } else{
                this.setTelegramReg(false);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

    }


    public boolean checkTrainer(){

        boolean check = false;

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement(
                    "SELECT * FROM trainers Where idClientThisTrainer = ?;"
            );

            statement.setInt(1,this.getId());
            rs = statement.executeQuery();


            if (rs.next()) {
                check = true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose(rs);
        }

        return check;
    }


    public List<ActionService> getListActionService(){
        return new ActionService().getListActionService(this.id, false, true);
    }

}
