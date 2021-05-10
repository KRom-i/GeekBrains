package WorkFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadFilesXLS {

    public static void main (String... args){

////        Файл КодВидаДохода
//        for (FileKBKFormate kbk: readFileNumberIncome ()
//             ) {
//            System.out.println (kbk.getNumcodeKBK ());
//            System.out.println (kbk.getIncomeCode ());
//        }

        List<FileTranshNumber> fileTranshNumbers = readFileNumberTransh ();

    }

//    Метод возвращает лист из файла Номера траншей
    private static List<FileTranshNumber> readFileNumberTransh(){

        List<FileTranshNumber> fileTranshNumbers = new ArrayList<> ();

        File file = new File ("K:\\2804_Соцрегистры\\НОМЕРА ТРАНШЕЙ ДЛЯ КРЕДИТНЫХ.xls");

        FileInputStream fileXLS = null;
        HSSFWorkbook workbook = null;

        try {

            fileXLS = new FileInputStream (file);
            workbook = new HSSFWorkbook (fileXLS);
            HSSFSheet sheet = workbook.getSheetAt (0);

            for (int i = 2; i < sheet.getLastRowNum () + 1; i++) {

                System.out.println (sheet.getRow (i).getLastCellNum ());

                int numCodeKBK1 = (int) sheet.getRow (i).getCell (1).getNumericCellValue ();
                System.out.println (String.valueOf(numCodeKBK1));

                int numCodeKBK2 = (int) sheet.getRow (i).getCell (2).getNumericCellValue ();
                System.out.println (String.valueOf(numCodeKBK2));

                System.out.println (String.valueOf (sheet.getRow (i).getCell (1).getStringCellValue ()));

                System.out.println (String.valueOf (sheet.getRow (i).getCell (2).getStringCellValue ()));

                int incomeCode = (int) sheet.getRow (i).getCell (3).getNumericCellValue ();
                System.out.println (String.valueOf(incomeCode));

            }

        } catch (Exception e) {
            e.printStackTrace ();
        } finally {

            try {
                fileXLS.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }

        }

        return fileTranshNumbers;
    }

//    Метод возвращает массив из файла КодВидаДохода
    public static FileKBKFormate[] readFileNumberIncome() {

        File file = new File (
                "C:\\Users\\Роман\\Documents\\REPO_My\\Control_files_XML_kbk\\КодВидаДохода.xls");

        FileInputStream fileXLS = null;
        HSSFWorkbook workbook = null;
        FileKBKFormate[] fileKBKFormates= null;

        try {

            fileXLS = new FileInputStream (file);
            workbook = new HSSFWorkbook (fileXLS);

            HSSFSheet sheet = workbook.getSheetAt (0);


            fileKBKFormates = new FileKBKFormate[sheet.getLastRowNum () + 1];

            for (int i = 0; i < sheet.getLastRowNum () + 1; i++) {

                fileKBKFormates[i] = new FileKBKFormate ();

                int numCodeKBK = (int) sheet.getRow (i).getCell (0).getNumericCellValue ();
//                System.out.println (String.valueOf(numCodeKBK));
                fileKBKFormates[i].setNumcodeKBK (numCodeKBK);

//                System.out.println (String.valueOf (sheet.getRow (i).getCell (1).getStringCellValue ()));
                fileKBKFormates[i].setName (String.valueOf (sheet.getRow (i).getCell (1).getStringCellValue ()));

//                System.out.println (String.valueOf (sheet.getRow (i).getCell (2).getStringCellValue ()));
                fileKBKFormates[i].setCodeKBK (String.valueOf (sheet.getRow (i).getCell (2).getStringCellValue ()));

                int incomeCode = (int) sheet.getRow (i).getCell (3).getNumericCellValue ();
//                System.out.println (String.valueOf(incomeCode));
                fileKBKFormates[i].setIncomeCode (incomeCode);

            }


        } catch (Exception e) {
            e.printStackTrace ();
        } finally {

            try {
                fileXLS.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }

        return fileKBKFormates;
    }

}
