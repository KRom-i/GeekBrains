package Cash;


import Format.DateTime;
import Services.Service;
import WorkDataBase.ClientClass;
import WorkDataBase.UserSpartak;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    private int numberTransaction;
    private String dateTransaction;
    private String timeTransaction;
    private int idTransaction;
    private String nameTransaction;
    private int idService;
    private String nameService;
    private int idClient;
    private String nameClient;
    private int idUser;
    private String nameUser;
    private int idTypePayment;
    private String nameTypePayment;
    private double sumCashReceipt;
    private double sumCashConsumption;
    private double sumNonCashReceipt;
    private double sumNonCashConsumption;
    private double sumCashBalanceBegin;
    private double sumNonCashBalanceBegin;
    private double sumAllBalanceBegin;
    private double sumCashBalanceEnd;
    private double sumNonCashBalanceEnd;
    private double sumAllBalanceEnd;
    private boolean deleteTran;


    /*  Конструктор принимает id вида операции, id услуги/товара, id клиента,
        id пользователя, id способа расчета(Нал/Безнал/QR-код) */
    public Transaction(int idTransaction,Service service, ClientClass client, UserSpartak user, int idTypePayment){

        DateTime dateTime = new DateTime();
        dateTransaction = dateTime.currentDate();
        timeTransaction = dateTime.currentTime();

        this.idTransaction = idTransaction;
        this.nameTransaction = new TransactionArray().getName(idTransaction);

        this.idService = service.getId();
        this.nameService = null;

        this.idClient = client.getId();
        this.nameClient = client.getInfoClient();

        this.idUser = user.getId();
        this.nameUser = user.getName();

        this.idTypePayment = idTypePayment;
        this.nameTypePayment = new TypePaymentArray().getName(idTypePayment);

        if (idTransaction == 1){

            if (idTypePayment == 1){
                this.sumCashReceipt = service.getCost();
                this.sumCashConsumption = 0;
                this.sumNonCashReceipt = 0;
                this.sumNonCashConsumption = 0;
            } else if (idTypePayment == 2 || idTypePayment == 3) {
                this.sumCashReceipt = 0;
                this.sumCashConsumption = 0;
                this.sumNonCashReceipt = service.getCost();
                this.sumNonCashConsumption = 0;
            }

            this.deleteTran = false;
        }


    }

    public int getNumberTransaction() {
        return numberTransaction;
    }

    public void setNumberTransaction(int numberTransaction) {
        this.numberTransaction = numberTransaction;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getTimeTransaction() {
        return timeTransaction;
    }

    public void setTimeTransaction(String timeTransaction) {
        this.timeTransaction = timeTransaction;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getNameTransaction() {
        return nameTransaction;
    }

    public void setNameTransaction(String nameTransaction) {
        this.nameTransaction = nameTransaction;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public int getIdTypePayment() {
        return idTypePayment;
    }

    public void setIdTypePayment(int idTypePayment) {
        this.idTypePayment = idTypePayment;
    }

    public String getNameTypePayment() {
        return nameTypePayment;
    }

    public void setNameTypePayment(String nameTypePayment) {
        this.nameTypePayment = nameTypePayment;
    }



    public double getSumCashReceipt() {
        return sumCashReceipt;
    }

    public void setSumCashReceipt(double sumCashReceipt) {
        this.sumCashReceipt = sumCashReceipt;
    }

    public double getSumCashConsumption() {
        return sumCashConsumption;
    }

    public void setSumCashConsumption(double sumCashConsumption) {
        this.sumCashConsumption = sumCashConsumption;
    }

    public double getSumNonCashReceipt() {
        return sumNonCashReceipt;
    }

    public void setSumNonCashReceipt(double sumNonCashReceipt) {
        this.sumNonCashReceipt = sumNonCashReceipt;
    }

    public double getSumNonCashConsumption() {
        return sumNonCashConsumption;
    }

    public void setSumNonCashConsumptionDay(double sumNonCashConsumption) {
        this.sumNonCashConsumption = sumNonCashConsumption;
    }

    public double getSumCashBalanceBegin() {
        return sumCashBalanceBegin;
    }

    public void setSumCashBalanceBegin(double sumCashBalanceBegin) {
        this.sumCashBalanceBegin = sumCashBalanceBegin;
    }

    public double getSumNonCashBalanceBegin() {
        return sumNonCashBalanceBegin;
    }

    public void setSumNonCashBalanceBegin(double sumNonCashBalanceBegin) {
        this.sumNonCashBalanceBegin = sumNonCashBalanceBegin;
    }

    public double getSumAllBalanceBegin() {
        return sumAllBalanceBegin;
    }

    public void setSumAllBalanceBegin(double sumAllBalanceBegin) {
        this.sumAllBalanceBegin = sumAllBalanceBegin;
    }

    public double getSumCashBalanceEnd() {
        return sumCashBalanceEnd;
    }

    public void setSumCashBalanceEnd(double sumCashBalanceEnd) {
        this.sumCashBalanceEnd = sumCashBalanceEnd;
    }

    public double getSumNonCashBalanceEnd() {
        return sumNonCashBalanceEnd;
    }

    public void setSumNonCashBalanceEnd(double sumNonCashBalanceEnd) {
        this.sumNonCashBalanceEnd = sumNonCashBalanceEnd;
    }

    public double getSumAllBalanceEnd() {
        return sumAllBalanceEnd;
    }

    public void setSumAllBalanceEnd(double sumAllBalanceEnd) {
        this.sumAllBalanceEnd = sumAllBalanceEnd;
    }

    public boolean isDeleteTran() {
        return deleteTran;
    }

    public void setDeleteTran(boolean storno) {
        this.deleteTran = deleteTran;
    }

    //    Метод округляет значение Double до 2x чисел после запятой.
    private double round(double doubleValue){
        int places = 2;
        BigDecimal db = new BigDecimal(Double.toString(doubleValue));
        db = db.setScale(places, RoundingMode.HALF_UP);
        return  db.doubleValue();
    }

//    Метод принимает транцакцию для расчета остатка на начало и на конец.

    public void balanceCalculation(Transaction t){

//        Необходимо добавить метод возврящающий последнюю транзакцию из базы данных
//        Transaction t = null;

        this.setNumberTransaction(t.getNumberTransaction() + 1);

        this.setSumCashBalanceBegin(round(t.getSumCashBalanceEnd()));
        this.setSumNonCashBalanceBegin(round(t.getSumNonCashBalanceEnd()));
        this.setSumAllBalanceBegin(round(t.getSumAllBalanceEnd()));

        this.setSumCashBalanceEnd(round(
                this.getSumCashBalanceBegin()
                        + this.getSumCashReceipt() - this.getSumCashConsumption()));

        this.setSumNonCashBalanceEnd(round(
                this.getSumNonCashBalanceBegin()
                + this.getSumNonCashReceipt() - this.getSumNonCashConsumption()));

        this.setSumAllBalanceEnd(round(
                this.getSumCashBalanceEnd() + this.getSumNonCashBalanceEnd()));
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "numberTransaction=" + numberTransaction +
                ", dateTransaction='" + dateTransaction + '\'' +
                ", timeTransaction='" + timeTransaction + '\'' +
                ", idTransaction=" + idTransaction +
                ", nameTransaction='" + nameTransaction + '\'' +
                ", idService=" + idService +
                ", nameService='" + nameService + '\'' +
                ", idClient=" + idClient +
                ", nameClient='" + nameClient + '\'' +
                ", idUser=" + idUser +
                ", nameUser='" + nameUser + '\'' +
                ", idTypePayment=" + idTypePayment +
                ", nameTypePayment='" + nameTypePayment + '\'' +
                ", sumCashReceipt=" + sumCashReceipt +
                ", sumCashConsumption=" + sumCashConsumption +
                ", sumNonCashReceipt=" + sumNonCashReceipt +
                ", sumNonCashConsumption=" + sumNonCashConsumption +
                ", sumCashBalanceBegin=" + sumCashBalanceBegin +
                ", sumNonCashBalanceBegin=" + sumNonCashBalanceBegin +
                ", sumAllBalanceBegin=" + sumAllBalanceBegin +
                ", sumCashBalanceEnd=" + sumCashBalanceEnd +
                ", sumNonCashBalanceEnd=" + sumNonCashBalanceEnd +
                ", sumAllBalanceEnd=" + sumAllBalanceEnd +
                ", deleteTran=" + deleteTran +
//                ", service=" + service +
                '}';
    }

    public String toStringCsvFormat() {

        String deleteTranStr = null;
        if (deleteTran){
            deleteTranStr = "Сторно";
        }

        return
                numberTransaction + ";" +
                dateTransaction + ";" +
                timeTransaction + ";" +
                idTransaction + ";" +
                nameTransaction +  ";" +
                idService + ";" +
                nameService +  ";" +
                idClient + ";" +
                nameClient +  ";" +
                idUser + ";" +
                nameUser +  ";" +
                idTypePayment + ";" +
                nameTypePayment +  ";" +
                sumCashReceipt + ";" +
                sumCashConsumption + ";" +
                sumNonCashReceipt + ";" +
                sumNonCashConsumption + ";" +
                sumCashBalanceBegin + ";" +
                sumNonCashBalanceBegin + ";" +
                sumAllBalanceBegin + ";" +
                sumCashBalanceEnd + ";" +
                sumNonCashBalanceEnd + ";" +
                sumAllBalanceEnd + ";" +
                deleteTranStr + ";";
    }
}
