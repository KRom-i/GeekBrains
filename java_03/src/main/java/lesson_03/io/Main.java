package lesson_03.io;

import com.sun.xml.internal.bind.v2.util.ByteArrayOutputStreamEx;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
//        doByteArrayStreamDemo();
        doDataStreamDemo();
    }




    static void doDataStreamDemo(){

        File file = new File("src/main/java/lesson_03/io/test.txt");

        doDataOutStreamDemo(file, "Hello!"); // write
        doDataInStreamDemo(file); // read

    }

    private static void doDataOutStreamDemo(File file, String value) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))){

            dos.writeUTF(value);
            dos.writeInt(1234);
            dos.writeDouble(1.86);
            dos.writeBoolean(true);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void doDataInStreamDemo(File file) {

        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))){

            System.out.println(dis.readUTF());
            System.out.println(dis.readInt());
            System.out.println(dis.readDouble());
            System.out.println(dis.readBoolean());

        } catch (Exception e){
            e.printStackTrace();
        }
    }





// Byte array Demo

    public static void doByteArrayStreamDemo(){

        byte[] bytes = doByteArrayOutputStreamDemo("Hello-hello!");
        doByteArrayInputStreamDemo(bytes);

    }

    static void doByteArrayInputStreamDemo(byte[] bytes){


        try(ByteArrayInputStream baIn = new ByteArrayInputStream(bytes)){
            int b;
            while ((b = baIn.read()) != -1){
                System.out.print(b + "-" + (char) b + " ");
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    static byte[] doByteArrayOutputStreamDemo(String value){

        try(ByteArrayOutputStream daOut = new ByteArrayOutputStreamEx()){
            daOut.write(value.getBytes());
            return daOut.toByteArray();
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
