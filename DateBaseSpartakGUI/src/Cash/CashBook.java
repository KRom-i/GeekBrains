package Cash;

import Format.CharFormat;
import Format.DateTime;
import GUIMain.CustomStage.InfoStage;
import GUIMain.CustomStage.SystemErrorStage;
import GUIMain.GUIMainController;
import Logger.LOG;
import MySQLDB.ServerMySQL;
import Services.Product;
import Services.Service;
import Services.Subscription;
import WorkDataBase.ActionUser;
import WorkDataBase.ClientClass;
import WorkDataBase.UserSpartak;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CashBook {


    String thisNameRus = "Кассовая книга";
    String thisNameEng = "CashBook";
    String inDirCashBooK = "FilesXLS/In/CashBook";
    static String inDirCashBooKStatic = "FilesXLS/In/CashBook";

    String[][] fieldsName = {{
            "Номер кассовой операции", "Дата операции", "Время операции",
            "Идентификатор операции", "Наименование отперации",
            "Идентификатор услуги(товара)", "Наименование услуги товара",
            "Идентификатор клиента", "ФИО клиента",
            "Идентификатор пользователя", "ФИО пользователя",
            "Идентификатор вида оплаты", "Наименование вида оплаты",
            "Приход наличных", "Расход наличных",
            "Приход безналичный", "Расход безналичный",
            "Остаток средств в кассе на начало", "Остаток средства р/с на начало", "Общий остаток на начало",
            "Остаток средств в кассе на конец", "Остаток средства р/с на конец", "Общий остаток на конец",
            "Сторно",
    }, {
            "numberTransaction", "dateTransaction", "timeTransaction",
            "idTransaction", "nameTransaction",
            "idService", "nameService",
            "idClient", "nameClient",
            "idUser", "nameUser",
            "idTypePayment", "nameTypePaymean",
            "sumCashReceipt", "sumCashCunsumption",
            "sumNonCashReceipt", "sumNonCashCunsumption",
            "sumCashBalanceBegin", "sumNonCashBalanceBegin", "sumAllBalanceBegin",
            "sumCashBalanceEnd", "sumNonCashBalanceEnd", "sumAllBalanceEnd",
            "deleteTran",

    }};


    public void writeFileTran (Transaction transaction) {
        String nameFile = "Касса-" + new SimpleDateFormat ("yyyy-MM-dd").format (new Date ()) + ".csv";
        File file = new File (nameFile);


        try (BufferedWriter bufferedWriter = new BufferedWriter (
                new FileWriter (file, true)
        )) {


            if (!file.exists ()) {
                try {
                    file.createNewFile ();
                } catch (IOException e) {
                    e.printStackTrace ();
new SystemErrorStage (e);
                }

            }

            if (file.length () < 10) {
                bufferedWriter.write (new CharFormat ().utf8ToCp886 (thisStringCsvFormat ()));
                bufferedWriter.newLine ();
            }

            bufferedWriter.write (new CharFormat ().utf8ToCp886 (transaction.toStringCsvFormat ()));
            bufferedWriter.newLine ();

        } catch (Exception e) {
            e.printStackTrace ();
new SystemErrorStage (e);
        }

    }


    private String thisStringCsvFormat () {
        StringBuilder stringBuilder = new StringBuilder ();
        for (int i = 0; i < fieldsName[0].length; i++) {
            stringBuilder.append (fieldsName[0][i] + ";");
        }
        return stringBuilder.toString ();
    }


//    Метор возвращает транцакции за день

    public static List<Transaction> getListTranDay (String date) {
        List<Transaction> transactionList = new ArrayList<> ();

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement (
                    "SELECT * FROM CashBook WHERE dateTransaction = ?;"
            );

            statement.setString (1, date);

            rs = statement.executeQuery ();
            LOG.info (String.format ("Запрос истории кассовых операций за день [%s]\n", date));

            int numberTran = 0;
            while (rs.next ()) {
                numberTran++;

                Transaction t = new Transaction ();
                t.setNumberTransaction (rs.getInt (1));
                t.setDateTransaction (rs.getString (2));
                t.setTimeTransaction (rs.getString (3));

                t.setIdTransaction (rs.getInt (4));
                t.setNameTransaction (rs.getString (5));

                t.setIdService (rs.getInt (6));
                t.setNameService (rs.getString (7));

                t.setIdClient (rs.getInt (8));
                t.setNameClient (rs.getString (9));

                t.setIdUser (rs.getInt (10));
                t.setNameUser (rs.getString (11));

                t.setIdTypePayment (rs.getInt (12));
                t.setNameTypePayment (rs.getString (13));

                t.setSumCashReceipt (rs.getDouble (14));
                t.setSumCashConsumption (rs.getDouble (15));

                t.setSumNonCashReceipt (rs.getDouble (16));
                t.setSumNonCashConsumptionDay (rs.getDouble (17));

                t.setSumCashBalanceBegin (rs.getDouble (18));
                t.setSumNonCashBalanceBegin (rs.getDouble (19));
                t.setSumAllBalanceBegin (rs.getDouble (20));

                t.setSumCashBalanceEnd (rs.getDouble (21));
                t.setSumNonCashBalanceEnd (rs.getDouble (22));
                t.setSumAllBalanceEnd (rs.getDouble (23));

                t.setDeleteTran (rs.getBoolean (24));

//                LOG.info (String.format  ("Запрос истории за день [%s] \n", date);


                transactionList.add (t);
            }

            LOG.info (String.format  ("Найдено [%s] операций за день [%s]\n", numberTran, date));

        } catch (SQLException e) {
            e.printStackTrace ();
new SystemErrorStage (e);
        } finally {
            ServerMySQL.statementClose (statement);
            ServerMySQL.resultSetClose (rs);
        }



        return transactionList;
    }

    //    Сторно транцикции
    public static void deleteTran (Transaction t) {

        PreparedStatement statement = null;

        try {

            statement = ServerMySQL.getConnection ().prepareStatement (
                    "UPDATE CashBook SET deleteTran = ? WHERE numberTransaction = ?;"
            );

//           Новое значение
            statement.setBoolean (1, true);

            statement.setInt (2, t.getNumberTransaction ());


            LOG.info  ("INT statement UPDATE = " + statement.executeUpdate ());

            LOG.info  ("Завершение сторно: " + t.toString ());

        } catch (SQLException e) {
            e.printStackTrace ();
new SystemErrorStage (e);
        } finally {
            ServerMySQL.statementClose (statement);

        }

        calculationDelTran (t);
    }

    //    Обнуление кассовой книги
    public static void clearCashBook () {

        PreparedStatement statement = null;
        try {
            statement = ServerMySQL.getConnection ().prepareStatement ("TRUNCATE TABLE CashBook");
            statement.executeUpdate ();
            addTransactionToCashBookDataBase (new Transaction ());
            File file = new File (inDirCashBooKStatic);
            for (File f : file.listFiles ()
            ) {
                f.delete ();
            }
            LOG.info  ("Обнуление кассовой книги");
        } catch (SQLException e) {
            e.printStackTrace ();
new SystemErrorStage (e);
        } finally {
            ServerMySQL.statementClose (statement);
        }

    }

    //    Сторно транцикции перерасчет после транзакции
    private static void calculationDelTran (Transaction t1) {

        Transaction tEnd = getEndTransactionDataBase ();

        Transaction t2 = new Transaction ();

        t2.setNumberTransaction (tEnd.getNumberTransaction () + 1);

        t2.setDateTransaction (new DateTime ().currentDate ());
        t2.setTimeTransaction (new DateTime ().currentTime ());

        t2.setIdUser (ActionUser.getUser ().getId ());
        t2.setNameUser (ActionUser.getUser ().getName ());

        t2.setNameTransaction ("Удаление операции № " + t1.getNumberTransaction ());

        t2.setSumCashBalanceBegin (tEnd.getSumCashBalanceEnd ());
        t2.setSumNonCashBalanceBegin (tEnd.getSumNonCashBalanceEnd ());
        t2.setSumAllBalanceBegin (tEnd.getSumAllBalanceEnd ());

        t2.setSumCashReceipt (t1.getSumCashConsumption ());
        t2.setSumNonCashReceipt (t1.getSumNonCashConsumption ());

        t2.setSumCashConsumption (t1.getSumCashReceipt ());
        t2.setSumNonCashConsumptionDay (t1.getSumNonCashReceipt ());

        t2.setSumCashBalanceEnd (Transaction.round (
                t2.getSumCashBalanceBegin ()
                        + t2.getSumCashReceipt () - t2.getSumCashConsumption ()));

        t2.setSumNonCashBalanceEnd (Transaction.round (
                t2.getSumNonCashBalanceBegin ()
                        + t2.getSumNonCashReceipt () - t2.getSumNonCashConsumption ()));

        t2.setSumAllBalanceEnd (Transaction.round (
                t2.getSumCashBalanceEnd () + t2.getSumNonCashBalanceEnd ()));

        t2.setDeleteTran (true);

        addTransactionToCashBookDataBase (t2);
        NodeViewHistory.update ();
    }

    //    Метод возвращает последнюю транзакцию из БД;
    public static Transaction getEndTransactionDataBase () {

        PreparedStatement statement = null;
        ResultSet rs = null;
        Transaction transaction = null;
        try {

//            statement = ServerMySQL.getConnection ().prepareStatement(
//                    "SELECT * FROM CashBook;"
//            );


            statement = ServerMySQL.getConnection ().prepareStatement (
                    "SELECT * FROM CashBook ORDER BY numberTransaction DESC LIMIT 0, 1;"
            );


            rs = statement.executeQuery ();


            while (rs.next ()) {

                transaction = new Transaction ();
                transaction.setNumberTransaction (rs.getInt (1));
                transaction.setDateTransaction(rs.getString(2));
                transaction.setTimeTransaction(rs.getString(3));
                transaction.setSumCashBalanceEnd (rs.getDouble (21));
                transaction.setSumNonCashBalanceEnd (rs.getDouble (22));
                transaction.setSumAllBalanceEnd (rs.getDouble (23));
                LOG.info (String.format  ("Последняя транзакция: \n [%s] \n", transaction.toString ()));
            }

            return transaction;

        } catch (SQLException e) {
            e.printStackTrace ();
new SystemErrorStage (e);
        } finally {
            ServerMySQL.statementClose (statement);
            ServerMySQL.resultSetClose (rs);
        }

        return null;
    }

//    Добавление в базу данных миную Графический интерфейс
    public static void addTransactionToCashBookNotGui (Transaction t) {

        Transaction t1 = CashBook.getEndTransactionDataBase ();
        t.balanceCalculation (t1);

        writeHistoryFileXls (t);

        PreparedStatement statement = null;

        try {

            String query = "INSERT INTO CashBook (" +
                    " numberTransaction, dateTransaction, timeTransaction," +
                    " idTransaction, nameTransaction," +
                    " idService, nameService," +
                    " idClient, nameClient," +
                    " idUser, nameUser," +
                    " idTypePayment, nameTypePayment," +
                    " sumCashReceipt, sumCashConsumption," +
                    " sumNonCashReceipt, sumNonCashConsumption," +
                    " sumCashBalanceBegin, sumNonCashBalanceBegin, sumAllBalanceBegin," +
                    " sumCashBalanceEnd, sumNonCashBalanceEnd, sumAllBalanceEnd," +
                    " deleteTran" +
                    ") VALUES (" +
                    "?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?);";


            statement = ServerMySQL.getConnection ().prepareStatement (query);

            statement.setInt (1, t.getNumberTransaction ());
            statement.setString (2, t.getDateTransaction ());
            statement.setString (3, t.getTimeTransaction ());

            statement.setInt (4, t.getIdTransaction ());
            statement.setString (5, t.getNameTransaction ());

            statement.setInt (6, t.getIdService ());
            statement.setString (7, t.getNameService ());

            statement.setInt (8, t.getIdClient ());
            statement.setString (9, t.getNameClient ());

            statement.setInt (10, t.getIdUser ());
            statement.setString (11, t.getNameUser ());

            statement.setInt (12, t.getIdTypePayment ());
            statement.setString (13, t.getNameTypePayment ());

            statement.setDouble (14, t.getSumCashReceipt ());
            statement.setDouble (15, t.getSumCashConsumption ());

            statement.setDouble (16, t.getSumNonCashReceipt ());
            statement.setDouble (17, t.getSumNonCashConsumption ());

            statement.setDouble (18, t.getSumCashBalanceBegin ());
            statement.setDouble (19, t.getSumNonCashBalanceBegin ());
            statement.setDouble (20, t.getSumAllBalanceBegin ());

            statement.setDouble (21, t.getSumCashBalanceEnd ());
            statement.setDouble (22, t.getSumNonCashBalanceEnd ());
            statement.setDouble (23, t.getSumAllBalanceEnd ());

            statement.setBoolean (24, t.isDeleteTran ());

            statement.executeUpdate ();

            LOG.info (String.format  ("Текущая транзакция: \n [%s] \n", t.toString ()));

        LOG.info ("Сохранение операции");

    } catch (SQLException e) {
        e.printStackTrace ();
    } finally {
        ServerMySQL.statementClose (statement);
    }

}

    //    Метод производит расчет транзакции
    public static void addTransactionToCashBook (Transaction t2) {
        Transaction t1 = CashBook.getEndTransactionDataBase ();
        t2.balanceCalculation (t1);
        addTransactionToCashBookDataBase (t2);
        NodeViewHistory.update ();
    }

    //    Метод добавляет транзакцию
    private static boolean addTransactionToCashBookDataBase (Transaction t) {

        writeHistoryFileXls (t);

        PreparedStatement statement = null;

        try {

            String query = "INSERT INTO CashBook (" +
                    " numberTransaction, dateTransaction, timeTransaction," +
                    " idTransaction, nameTransaction," +
                    " idService, nameService," +
                    " idClient, nameClient," +
                    " idUser, nameUser," +
                    " idTypePayment, nameTypePayment," +
                    " sumCashReceipt, sumCashConsumption," +
                    " sumNonCashReceipt, sumNonCashConsumption," +
                    " sumCashBalanceBegin, sumNonCashBalanceBegin, sumAllBalanceBegin," +
                    " sumCashBalanceEnd, sumNonCashBalanceEnd, sumAllBalanceEnd," +
                    " deleteTran" +
                    ") VALUES (" +
                    "?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?);";


            statement = ServerMySQL.getConnection ().prepareStatement (query);

            statement.setInt (1, t.getNumberTransaction ());
            statement.setString (2, t.getDateTransaction ());
            statement.setString (3, t.getTimeTransaction ());

            statement.setInt (4, t.getIdTransaction ());
            statement.setString (5, t.getNameTransaction ());

            statement.setInt (6, t.getIdService ());
            statement.setString (7, t.getNameService ());

            statement.setInt (8, t.getIdClient ());
            statement.setString (9, t.getNameClient ());

            statement.setInt (10, t.getIdUser ());
            statement.setString (11, t.getNameUser ());

            statement.setInt (12, t.getIdTypePayment ());
            statement.setString (13, t.getNameTypePayment ());

            statement.setDouble (14, t.getSumCashReceipt ());
            statement.setDouble (15, t.getSumCashConsumption ());

            statement.setDouble (16, t.getSumNonCashReceipt ());
            statement.setDouble (17, t.getSumNonCashConsumption ());

            statement.setDouble (18, t.getSumCashBalanceBegin ());
            statement.setDouble (19, t.getSumNonCashBalanceBegin ());
            statement.setDouble (20, t.getSumAllBalanceBegin ());

            statement.setDouble (21, t.getSumCashBalanceEnd ());
            statement.setDouble (22, t.getSumNonCashBalanceEnd ());
            statement.setDouble (23, t.getSumAllBalanceEnd ());

            statement.setBoolean (24, t.isDeleteTran ());

            statement.executeUpdate ();

            LOG.info (String.format  ("Текущая транзакция: \n [%s] \n", t.toString ()));

//            String query = "INSERT INTO CashBook (" +
//                    " numberTransaction, dateTransaction, timeTransaction," +
//                    " idTransaction, nameTransaction," +
//                    " idService, nameService," +
//                    " idClient, nameClient," +
//                    " idUser, nameUser," +
//                    " idTypePayment, nameTypePayment" +
//                    ") VALUES (" +
//                    "?, ?, ?, ?, ?," +
//                    " ?, ?, ?, ?, ?," +
//                    " ?, ?, ?);";
//
//            statement = ServerMySQL.getConnection ().prepareStatement(query);
//
//            statement.setInt(1, t.getNumberTransaction ());
//            statement.setString(2, t.getDateTransaction ());
//            statement.setString(3, t.getTimeTransaction ());
//
//            statement.setInt(4, t.getIdTransaction ());
//            statement.setString(5, t.getNameTransaction ());
//
//            statement.setInt(6, t.getIdService ());
//            statement.setString(7, t.getNameService ());
//
//            statement.setInt(8, t.getIdClient ());
//            statement.setString(9, t.getNameClient ());
//
//            statement.setInt(10, t.getIdUser ());
//            statement.setString(11, t.getNameUser ());
//
//            statement.setInt(12, t.getIdTypePayment ());
//            statement.setString(13, t.getNameTypePayment ());
//
//            statement.executeUpdate();
//
//            String query2 = "INSERT INTO CashBook (" +
//                    "sumNonCashReceipt, sumNonCashConsumption," +
//                    " sumCashBalanceBegin, sumNonCashBalanceBegin, sumAllBalanceBegin," +
//                    " sumCashBalanceEnd, sumNonCashBalanceEnd, sumAllBalanceEnd," +
//                    " deleteTran" +
//                    ") VALUES (" +
//                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
//
//            statement2 = ServerMySQL.getConnection ().prepareStatement(query2);
//
//            statement2.setDouble (1, t.getSumCashReceipt ());
//            statement2.setDouble(2, t.getSumCashConsumption ());
//
//            statement2.setDouble (3, t.getSumNonCashReceipt ());
//            statement2.setDouble(4, t.getSumNonCashConsumption ());
//
//            statement2.setDouble (5, t.getSumCashBalanceBegin ());
//            statement2.setDouble(6, t.getSumNonCashBalanceBegin ());
//            statement2.setDouble (7, t.getSumAllBalanceBegin ());
//
//            statement2.setDouble (8, t.getSumCashBalanceEnd ());
//            statement2.setDouble(9, t.getSumNonCashBalanceEnd ());
//            statement2.setDouble (10, t.getSumAllBalanceEnd ());
//
//            statement2.setBoolean (11, t.isDeleteTran ());
//
//            statement2.executeUpdate();
            if (t.getIdTransaction() != 123){
                new InfoStage ("Сохранение операции");
            }
            LOG.info ("Сохранение операции");
            return true;
        } catch (SQLException e) {
            e.printStackTrace ();
            new SystemErrorStage (e);
        } finally {
            ServerMySQL.statementClose (statement);
        }
        return false;
    }


    public static void main (String[] args) {


        for (int i = 0; i < 160; i++) {

        UserSpartak user = new UserSpartak (1, "admin", "log", 12345, false);
        Service service = new Product ("УСЛУГИ.ТОВАР", 100, 10, 1);
        ClientClass client = new ClientClass ();
        client.setId (1234);
        client.setLastName ("Иван");
        client.setFirstName ("Иванов");
        client.setPatronymicName ("Иванович");

        ServerMySQL.getConnection ();
        for (Transaction t: new Transaction[100]
        ) {
            t = new Transaction (1, service, client, user, 1 );
            CashBook.addTransactionToCashBookNotGui (t);
        }
            new DateTime ().upDay();
        }
        ServerMySQL.disconnect ();


//        ServerMySQL.getConnection ();
//        addEndCashBookStrNull ();
//        ServerMySQL.disconnect ();

//        ServerMySQL.getConnection ();
//        getListTranDay ("2021.05.21");
//        ServerMySQL.disconnect ();


//        newFileXlSCashBook(date, getListTranDay (date));
//        addTranToCashBookXLS(date, new Transaction ());


    }

    //    Запись транзпкции в файл
    private static void writeHistoryFileXls (Transaction t) {

        String path = String.format ("%s//Касса_%s.xls", inDirCashBooKStatic, new DateTime ().dateFormatMMyyyy ());
        File file = new File (path);
        if (!file.exists ()) {
            creatFile (file);
            addTranToCashBookXLS (t, file);
        } else {
            addTranToCashBookXLS (t, file);
        }

    }

    //    При отсутвии файла создает новый
    private static void creatFile (File file) {

        FileOutputStream outputStream = null;
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook ();
            HSSFSheet sheet = workbook.createSheet ("Касса " + new DateTime ().currentDate ());

            Cell cell;
            Row row;
            int r = 0;

//        Запись заголовка файла
            row = sheet.createRow (r);
            String[] f = new CashBook ().fieldsName[0];
            for (int i = 0; i < f.length; i++) {
                cell = row.createCell (i);
                cell.setCellValue (f[i]);
            }

            outputStream = new FileOutputStream (file);
            workbook.write (outputStream);
            LOG.info (String.format  ("Создание нового файла[%s]", file.getAbsolutePath ()));

        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }

        }
    }

    //    Добавляет в транзакцию в лист (усли лист отсутвует то сощдает новый)
    private static void addTranToCashBookXLS (Transaction t, File file) {

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        HSSFWorkbook workbook = null;

        try {
            String date = new DateTime ().currentDate ();

            inputStream = new FileInputStream (file);
            workbook = new HSSFWorkbook (inputStream);

            String sheetName = "Касса " + date;
            HSSFSheet sheet = workbook.getSheet (sheetName);
            if (sheet == null) {
                LOG.info (String.format  ("Листа [%s] не существует \n", sheetName));
                workbook.createSheet (sheetName);
                sheet = workbook.getSheet (sheetName);
                Cell cell;
                Row row;
                int r = 0;
//        Запись заголовка файла
                row = sheet.createRow (r);
                String[] f = new CashBook ().fieldsName[0];
                for (int i = 0; i < f.length; i++) {
                    cell = row.createCell (i);
                    cell.setCellValue (f[i]);
                }
            }


            LOG.info  ("Последная заполенненная строка " + sheet.getLastRowNum ());

//            sheet.protectSheet ("pass");

            int r = sheet.getLastRowNum () + 1;
            Row row = sheet.createRow (r);
            Cell cell;

            for (int j = 0; j < 24; j++) {
                switch (j) {
                    case 0:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getNumberTransaction ());
                        break;
                    case 1:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getDateTransaction ());
                        break;
                    case 2:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getTimeTransaction ());
                        break;
                    case 3:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getIdTransaction ());
                        break;
                    case 4:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getNameTransaction ());
                        break;
                    case 5:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getIdService ());
                        break;
                    case 6:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getNameService ());
                        break;
                    case 7:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getIdClient ());
                        break;
                    case 8:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getNameClient ());
                        break;
                    case 9:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getIdUser ());
                        break;
                    case 10:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getNameUser ());
                        break;
                    case 11:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getIdTypePayment ());
                        break;
                    case 12:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getNameTypePayment ());
                        break;
                    case 13:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getSumCashReceipt ());
                        break;
                    case 14:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getSumCashConsumption ());
                        break;
                    case 15:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getSumNonCashReceipt ());
                        break;
                    case 16:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getSumNonCashConsumption ());
                        break;
                    case 17:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getSumCashBalanceBegin ());
                        break;
                    case 18:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getSumNonCashBalanceBegin ());
                        break;
                    case 19:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getSumAllBalanceBegin ());
                        break;
                    case 20:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getSumCashBalanceEnd ());
                        break;
                    case 21:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getSumNonCashBalanceEnd ());
                        break;
                    case 22:
                        cell = row.createCell (j);
                        cell.setCellValue (t.getSumAllBalanceEnd ());
                        break;
                    case 23:
                        cell = row.createCell (j);
                        if (t.isDeleteTran ()) {
                            cell.setCellValue ("ДА");
                        } else {
                            cell.setCellValue ("НЕТ");
                        }
                        break;
                }
            }
            LOG.info (String.format  ("Добавление записи кассовой книги в файл[%s] \n", file.getAbsolutePath ()));

            inputStream.close ();

            outputStream = new FileOutputStream (file);
            workbook.write (outputStream);
        } catch (Exception e) {
            e.printStackTrace ();
new SystemErrorStage (e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
new SystemErrorStage (e);
                }
            }
            if (workbook != null) {
                try {
                    workbook.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
new SystemErrorStage (e);
                }
            }
        }

    }


    //    Создает файл и записывает List.size записей
    public static void newFileXlSCashBook (String date, List<Transaction> t) {

        FileOutputStream outputStream = null;
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook ();
            HSSFSheet sheet = workbook.createSheet ("Касса " + date);

            Cell cell;
            Row row;
            int r = 0;

            if (t.size () > 0) {
//        Запись заголовка файла

                row = sheet.createRow (r);
                String[] f = new CashBook ().fieldsName[0];
                for (int i = 0; i < f.length; i++) {
                    cell = row.createCell (i);
                    cell.setCellValue (f[i]);
                }

                //        Запись истории транзакций
                for (int i = 0; i < t.size (); i++) {
                    r++;

                    row = sheet.createRow (r);


                    for (int j = 0; j < 23; j++) {
                        cell = row.createCell (j);
                        switch (j) {
                            case 0:
                                cell.setCellValue (t.get (i).getNumberTransaction ());
                                break;
                            case 1:
                                cell.setCellValue (t.get (i).getDateTransaction ());
                                break;
                            case 2:
                                cell.setCellValue (t.get (i).getTimeTransaction ());
                                break;
                            case 3:
                                cell.setCellValue (t.get (i).getIdTransaction ());
                                break;
                            case 4:
                                cell.setCellValue (t.get (i).getNameTransaction ());
                                break;
                            case 5:
                                cell.setCellValue (t.get (i).getIdService ());
                                break;
                            case 6:
                                cell.setCellValue (t.get (i).getNameService ());
                                break;
                            case 7:
                                cell.setCellValue (t.get (i).getIdClient ());
                                break;
                            case 8:
                                cell.setCellValue (t.get (i).getNameClient ());
                                break;
                            case 9:
                                cell.setCellValue (t.get (i).getIdUser ());
                                break;
                            case 10:
                                cell.setCellValue (t.get (i).getNameUser ());
                                break;
                            case 11:
                                cell.setCellValue (t.get (i).getIdTypePayment ());
                                break;
                            case 12:
                                cell.setCellValue (t.get (i).getNameTypePayment ());
                                break;
                            case 13:
                                cell.setCellValue (t.get (i).getSumCashReceipt ());
                                break;
                            case 14:
                                cell.setCellValue (t.get (i).getSumCashConsumption ());
                                break;
                            case 15:
                                cell.setCellValue (t.get (i).getSumNonCashReceipt ());
                                break;
                            case 16:
                                cell.setCellValue (t.get (i).getSumNonCashConsumption ());
                                break;
                            case 17:
                                cell.setCellValue (t.get (i).getSumCashBalanceBegin ());
                                break;
                            case 18:
                                cell.setCellValue (t.get (i).getSumNonCashBalanceBegin ());
                                break;
                            case 19:
                                cell.setCellValue (t.get (i).getSumAllBalanceBegin ());
                                break;
                            case 20:
                                cell.setCellValue (t.get (i).getSumCashBalanceEnd ());
                                break;
                            case 21:
                                cell.setCellValue (t.get (i).getSumNonCashBalanceEnd ());
                                break;
                            case 22:
                                cell.setCellValue (t.get (i).getSumAllBalanceEnd ());
                                break;
                            case 23:
                                String str = "НЕТ";
                                if (t.get (i).isDeleteTran ()) {
                                    str = "ДА";
                                }
                                cell.setCellValue (str);
                                break;
                        }
                    }

                }
            } else {
                row = sheet.createRow (r);
                cell = row.createCell (0);
                cell.setCellValue ("Нет кассовых операций");
            }


//        cell = row.createCell (1);
//        cell.setCellValue ("2");

            String path = String.format ("CashBook//%s.xls", date);
            File file = new File (path);
//        file.getParentFile ().mkdirs ();

            outputStream = new FileOutputStream (file);
            workbook.write (outputStream);
            LOG.info (String.format  ("Запись кассовой книги в файл[%s]", path));

        } catch (Exception e) {
            e.printStackTrace ();
            new SystemErrorStage (e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                new SystemErrorStage (e);
                }
            }
            if (workbook != null) {
                try {
                    workbook.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                new SystemErrorStage (e);
                }
            }

        }
    }
}
