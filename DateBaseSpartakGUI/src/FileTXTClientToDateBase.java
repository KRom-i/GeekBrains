import MySQLDB.ServerMySQL;
import WorkDataBase.ClientClass;

import java.io.FileReader;
import java.util.Scanner;

public class FileTXTClientToDateBase {

//    public static void main (String[] args){
//
//        toDateBase();
//
//    }

    public static void toDateBase(){

        ServerMySQL.getConnection();

        try (FileReader fileReader
                     = new FileReader (
                             "C:\\Users\\Роман\\Documents\\REPO_My\\DateBaseSpartakGUI\\FileTestClients.txt"
        )){

            Scanner scanner = new Scanner (fileReader);

            while (scanner.hasNextLine ()){

                ClientClass clientClass = new ClientClass ();

                String[] strings = scanner.nextLine ().split (";");

                for(int i = 0; i < strings.length; i++) {

                    if (i == 0){
                        clientClass.setId (Integer.valueOf (strings[i]));
                    }

                    if (i == 1) {

                        String[] strings1 = strings[i].split (" ");

                        for(int j = 0; j < strings1.length; j++) {

                            if (j == 0) {
                                clientClass.setLastName (strings1[j]);
                            }

                            if (j == 1) {
                                clientClass.setFirstName (strings1[j]);
                            }

                            if (j == 2) {
                                clientClass.setPatronymicName (strings1[j]);
                            }
                        }
                    }

                    if (i == 2) {
                        clientClass.setDateBirth(strings[i]);
                    }

                    if (i == 3) {
                        clientClass.setTelephone(strings[i]);
                    }

                    if (i == 4) {
                        clientClass.setInfoClient(strings[i]);
                    }

                    if (i == 5) {
                        clientClass.setEmail(strings[i]);
                    }

                }


                System.out.println(clientClass.toString());
            }


        } catch (Exception e){
            e.printStackTrace ();
        } finally {

            ServerMySQL.disconnect();
        }



    }

}
