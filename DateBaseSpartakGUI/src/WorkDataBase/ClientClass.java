package WorkDataBase;

import javafx.scene.text.Text;

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

    public String nameClassDataBase(){
        return NAME_Class_DB;
    }

    public ClientClass(){
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

        int control = 0;

        String[] strings = findStr.split (" ");

        for(String s: strings

            ) {

            s = editText (s);

            if (this.getLastName ().startsWith (s)){
                control++;
                continue;
            }
            if (this.getFirstName ().startsWith (s)){
                control++;
                continue;
            }
            if (this.getPatronymicName ().startsWith (s)){
                control++;
                continue;
            }
        }

        return control == strings.length;
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

}
