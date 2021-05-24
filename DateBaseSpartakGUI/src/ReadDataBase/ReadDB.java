package ReadDataBase;

import Cash.CashBook;
import Cash.Transaction;
import Format.DateTime;
import GUIMain.CustomStage.SystemErrorStage;
import Logger.LOG;
import MySQLDB.ServerMySQL;
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
import java.util.List;

public class ReadDB {

        static String nameTable = "users";
        static int numCol = 4;

    public static void main(String[] args) {

        writeTableToXls (readTable(nameTable, numCol));

    }

    private static void writeTableToXls(List<String[]> list){
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
            File file = new File (path);

            outputStream = new FileOutputStream (file);
            workbook.write (outputStream);

        } catch (Exception e) {
            e.printStackTrace ();
new SystemErrorStage (e);
        } finally {
            if (outputStream != null){
                try {
                    outputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
new SystemErrorStage (e);
                }
            }
            if (workbook != null){
                try {
                    workbook.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
new SystemErrorStage (e);
                }
            }

        }

    }

    private static List<String[]> readTable(String nameTable, int col){

        PreparedStatement statement = null;
        ResultSet rs = null;
        List<String[]> list = new ArrayList<> ();

        try {
            LOG.info (String.format("Чтение таблицы: [%s] \n", nameTable));
            String query = String.format ("SELECT * FROM %s;", nameTable);

            statement = ServerMySQL.getConnection ().prepareStatement(query);

            rs = statement.executeQuery();
            while (rs.next ()){


                String[] s = new String[col];
                for (int i = 0; i < col; i++) {
                    LOG.info (String.format("[%s]", rs.getString (i + 1)));
                    s[i] = rs.getString (i + 1);
                }
                list.add (s);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ServerMySQL.statementClose(statement);
            ServerMySQL.resultSetClose (rs);
            ServerMySQL.disconnect ();
        }

        return list;
    }
}
