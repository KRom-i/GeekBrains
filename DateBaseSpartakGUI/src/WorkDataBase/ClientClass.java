package WorkDataBase;

import java.util.ArrayList;
import java.util.List;

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


    public String toString() {
        return  lastName  + " " + firstName  + " " + patronymicName;
//                ", telephone='" + telephone + '\'' +
//                ", dateBirth='" + dateBirth + '\'' +
//                ", dateBirth='" + Email + '\'' +
//                ", infoClient='" + infoClient + '\'' +
//                ", subscription=" + subscription +
//                ", NAME_Class_DB='" + NAME_Class_DB + '\'' +
//                '}';
    }

    public String[] stringsClient(){

//        String[] strings = {lastName, firstName,
//                patronymicName, telephone,
//                dateBirth, Email};

//        String[] strings = {lastName, firstName,
//                patronymicName};
        String[] strings = this.toString().split(" ");
        return strings;
    }
}
