import java.io.*;

public class AddCmdFile {


    public static void batFile(String str){


        File file = new File("C:\\testBotTelegram.txt");

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(file)
        )){

            bufferedWriter.write(str);
            bufferedWriter.newLine();


        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(str);

//        try {
//            Thread.sleep(5000);
//
//
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


//        try {
//            Runtime.getRuntime().exec("cmd /c C:\\Users\\Роман\\Documents\\REPO_GeekBrains\\TelegramBotApi\\close.bat");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
}
