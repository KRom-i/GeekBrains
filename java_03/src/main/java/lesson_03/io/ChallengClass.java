package lesson_03.io;

import com.mysql.cj.util.StringUtils;

import java.io.*;

public class ChallengClass {

    public static void main(String[] args) {

        File file = new File("src/main/java/lesson_03/io/text.txt");

        long start = System.currentTimeMillis();
        int read = readFileInputStreamNotBuffer(file);
        long finish = System.currentTimeMillis();
        System.out.println("Не используя буфер, прочитано: " + read + " за " +
                (finish - start) + " мс");


        start = System.currentTimeMillis();
        read = readFileInputStreamThisBuffer(file);
        finish = System.currentTimeMillis();
        System.out.println("Используя буфер, прочитано: " + read + " за " +
                (finish - start) + " мс");

        start = System.currentTimeMillis();
        read = readFileInputStreamThisBufferedStream(file);
        finish = System.currentTimeMillis();
        System.out.println("Используя BufferedString, прочитано: " + read + " за " +
                (finish - start) + " мс");

        start = System.currentTimeMillis();
        read = readFileInputStreamThisBufferedRead(file);
        finish = System.currentTimeMillis();
        System.out.println("Используя BufferedRead, прочитано: " + read + " за " +
                (finish - start) + " мс");

        writeWithBifferedWriter("Test string write");

        testMyReadWrite(file);

    }



    private static int readFileInputStreamNotBuffer(File file) {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int b;
            while ((b = fileInputStream.read()) != -1) {
                sb.append((char) b);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.length();
    }

    private static int readFileInputStreamThisBuffer(File file) {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[8 * 1024];

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                sb.append(StringUtils.toString(buffer));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.length();
    }

    private static int readFileInputStreamThisBufferedStream(File file) {
        StringBuilder sb = new StringBuilder();

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            int c;
            while ((c = bufferedInputStream.read()) != -1){
                sb.append(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.length();
    }

    private static int readFileInputStreamThisBufferedRead(File file) {

        StringBuilder sb = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.length();
    }


    private static void writeWithBifferedWriter(String value){

        File file = new File("src/main/java/lesson_03/io/text2.txt");

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))){

            bufferedWriter.write("Start line");
            bufferedWriter.newLine();
            bufferedWriter.write(value);
            bufferedWriter.newLine();
            bufferedWriter.write("End line");
            bufferedWriter.newLine();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void testMyReadWrite(File file) {

        File filenew = new File("src/main/java/lesson_03/io/text3.txt");

        if (!filenew.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filenew, true))){

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null){

                bufferedWriter.write(line);

                bufferedWriter.newLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        } catch (IOException e){
            e.printStackTrace();
        }

    }


}