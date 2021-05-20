package Cash;

import Format.CharFormat;
import Services.Subscription;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CashBook {

    String[][] fieldsName = {{
            "Номер кассовой операции","Дата операции","Время операции",
            "Идентификатор операции","Наименование отперации",
            "Идентификатор услуги(товара)","Наименование услуги товара",
            "Идентификатор клиента","ФИО клиента",
            "Идентификатор пользователя","ФИО пользователя",
            "Идентификатор вида оплаты","Наименование вида оплаты",
            "Приход наличных","Расход наличных",
            "Приход безналичный","Расход безналичный",
            "Остаток средств в кассе на начало","Остаток средства р/с на начало","Общий остаток на начало",
            "Остаток средств в кассе на конец","Остаток средства р/с на конец","Общий остаток на конец",
            "Сторно",
    },  {
            "numberTransaction","dateTransaction","timeTransaction",
            "idTransaction","nameTransaction",
            "idService","nameService",
            "idClient","nameClient",
            "idUser","nameUser",
            "idTypePayment","nameTypePaymean",
            "sumCashReceipt","sumCashCunsumption",
            "sumNonCashReceipt","sumNonCashCunsumption",
            "sumCashBalanceBegin","sumNonCashBalanceBegin","sumAllBalanceBegin",
            "sumCashBalanceEnd","sumNonCashBalanceEnd","sumAllBalanceEnd",
            "deleteTran",

    }};



    public void writeFileTran(Transaction transaction){
        String nameFile = "Касса-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".csv";
        File file = new File(nameFile);



        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(file, true)
        )){


            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (file.length() < 10){
                bufferedWriter.write(new CharFormat().utf8ToCp886(thisStringCsvFormat()));
                bufferedWriter.newLine();
            }

            bufferedWriter.write(new CharFormat().utf8ToCp886(transaction.toStringCsvFormat()));
            bufferedWriter.newLine();

        } catch (Exception e){
            e.printStackTrace();
        }

    }


    private String thisStringCsvFormat(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < fieldsName[0].length; i++) {
            stringBuilder.append(fieldsName[0][i] + ";");
        }
        return stringBuilder.toString();
    }

}
