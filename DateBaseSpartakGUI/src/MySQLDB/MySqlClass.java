package MySQLDB;

import Cash.Transaction;
import Logger.LOG;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySqlClass {

    String tableName;

    public MySqlClass () {
    }

    public static void main (String[] args) {

//        new MySqlClass ().getTableFileXls ("SELECT * FROM SERVICE_GENERAL_VALUES;");

//       List<String[]> test = new MySqlClass ().getSelect ("SELECT * FROM clientList;");
//
//        for (int i = 0; i < test.size (); i++) {
//            for (String s: test.get (i)
//                 ) {
//                System.out.print (s + " | ");
//            }
//            System.out.println ();
//        }
//
//        System.out.println (new MySqlClass ().setInsertInto ("INSERT INTO telegram (id_telegram, status) VALUES ('000000', 'admin');"));
//
//        System.out.println (new MySqlClass ().setInsertInto ("DELETE FROM telegram WHERE id_telegram = 000000;"));
    }

//    Возвращает файл для Bot telegram
    public File getTableFileXls(String sql){

        if (sql.startsWith ("SELECT")){
            return getFile (getSelect(sql), tableName);
        }
        return null;
    }



    public File getSelectAllTableXls(String nameTable){
        String sql = String.format ("select * from %s;", nameTable);
        return getFile (getSelect(sql), nameTable);

    }

    //    Список таблиц в базе данных;
    public String getShowTables() {

        PreparedStatement statement = null;
        ResultSet rs = null;
        List<String> strings = new ArrayList<> ();

        try {

            statement = ServerMySQL.getConnection ().prepareStatement ("SHOW TABLES");

            rs = statement.executeQuery ();

            StringBuffer stringBuffer = new StringBuffer ();

            while (rs.next ()) {
                String info = String.format ("/devShowTab@%s\n",rs.getObject (1).toString ());
                stringBuffer.append (rs.getObject (1).toString () + "\n");
                stringBuffer.append (info);
//                String s = rs.getObject (1).toString ();
//                if (s.length () > 3) {
//                    strings.add (String.format ("/devShowTab@%s", s));
//                }
            }

            return stringBuffer.toString ();
        } catch (Exception e) {
            e.printStackTrace ();

        } finally {
            ServerMySQL.statementClose (statement);
            ServerMySQL.resultSetClose (rs);
        }

        return "";

    }


//    Список таблиц в базе данных по имени SHOW TABLES;
public String[] getShowTables(String nameDataBase) {

    PreparedStatement statement = null;
    ResultSet rs = null;
    List<String> strings = new ArrayList<> ();

    try {

        statement = ServerMySQL.getConnectionNameDateBase (nameDataBase).prepareStatement ("SHOW TABLES");

        rs = statement.executeQuery ();

//            StringBuffer stringBuffer = new StringBuffer ();

        while (rs.next ()) {
//                String info = String.format ("%s\n",rs.getObject (1).toString ());
//                stringBuffer.append (rs.getObject (1).toString () + "\n");
            String s = rs.getObject (1).toString ();
            if (s.length () > 3) {
                strings.add (String.format ("/dev@ST@%s@%s", nameDataBase, s));
            }
        }

//            return stringBuffer.toString ();
    } catch (Exception e) {
        e.printStackTrace ();

    } finally {
        ServerMySQL.statementClose (statement);
        ServerMySQL.resultSetClose (rs);
    }

    return strings.toArray (new String[strings.size ()]);

}

//    Список баз данных на сервере
    public String[] getShowDateBases(){

        PreparedStatement statement = null;
        ResultSet rs = null;
        List<String> strings = new ArrayList<> ();

        try {

            statement = ServerMySQL.getConnection ().prepareStatement ("SHOW DATABASES");

            rs = statement.executeQuery ();

//            StringBuffer stringBuffer = new StringBuffer ();

            while (rs.next ()) {
//                String info = String.format ("%s\n",rs.getObject (1).toString ());
//                stringBuffer.append (rs.getObject (1).toString () + "\n");
                String s = rs.getObject (1).toString ();
                if (s.length () > 3){
                    strings.add ("/dev@SDB@"+s);
                }
            }

//            return stringBuffer.toString ();
        } catch (Exception e) {
            e.printStackTrace ();

        } finally {
            ServerMySQL.statementClose (statement);
            ServerMySQL.resultSetClose (rs);
        }

        return strings.toArray (new String[strings.size ()]);
    }


//     Метод выполняет комманду SELECT
    private List<String[]>  getSelect (String sql) {

        PreparedStatement statement = null;
        ResultSet rs = null;
        Transaction transaction = null;
        List<String []> list = new ArrayList<> ();

        try {

            statement = ServerMySQL.getConnection ().prepareStatement (sql);

            rs = statement.executeQuery ();

            tableName = rs.getMetaData ().getTableName (1);
            //System.out.println (tableName);
            int col = rs.getMetaData ().getColumnCount () + 1;

            String[] sName = new String[col - 1];
            for (int i = 1; i < col; i++) {
                    String name = rs.getMetaData ().getColumnName (i);
                sName[i -1] = name;
            }
            list.add (sName);

            while (rs.next ()) {

                String[] s = new String[col - 1];
                for (int i = 1; i < col; i++) {

                    String name = rs.getMetaData ().getColumnName (i);

                    String info = String.format (" (%s-%s) %s |", i, name, rs.getObject (i));
//                    //System.out.print (info);
                    try {
                        s[i -1] = rs.getObject (i).toString ();
                    } catch (Exception e){
                        s[i -1] = "";
                    }


                }
                list.add (s);
                //System.out.println ("");

            }


        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            ServerMySQL.statementClose (statement);
            ServerMySQL.resultSetClose (rs);
        }
        return list;
    }



//    Возвращает файл;
    private File getFile(List<String[]> list,String nameTable){

        File file = null;
        FileOutputStream outputStream = null;
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook ();
            HSSFSheet sheet = workbook.createSheet (nameTable);

            Cell cell;
            Row row;

            for (int r = 0; r < list.size (); r++) {
                row = sheet.createRow (r);

                for (int c = 0; c < list.get (r).length; c++) {
                    cell = row.createCell (c);
                    cell.setCellValue (list.get (r)[c]);
                }

            }


            String path = String.format ("FilesXLS//In//DB_Table_%s.xls", nameTable);
            file = new File (path);

            outputStream = new FileOutputStream (file);
            workbook.write (outputStream);

        } catch (Exception e) {
            e.printStackTrace ();

        } finally {
            if (outputStream != null){
                try {
                    outputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            if (workbook != null){
                try {
                    workbook.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }


        }
        return file;
    }


    public boolean setInsertDelUpdate (String query){
        boolean check = true;
        PreparedStatement statement = null;
        try {
            statement = ServerMySQL.getConnection ().prepareStatement (query);
            statement.executeUpdate ();

        } catch (SQLException e) {
            check = false;
            e.printStackTrace ();
        } finally {
            ServerMySQL.statementClose (statement);
        }

        return check;
    }

}
