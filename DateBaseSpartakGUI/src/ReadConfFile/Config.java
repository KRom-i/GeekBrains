package ReadConfFile;

import Logger.LOG;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Config {


    public String getValueConf(String param) {

        try (FileReader fileReader = new FileReader(new File("config.txt"))) {
            Scanner s = new Scanner(fileReader);
            while (s.hasNextLine()) {
                String str = s.nextLine();
                if (str.startsWith(param)) {
                    return str.split("=")[1];
                }
            }
            } catch(Exception e){
                e.printStackTrace();
            }
        return null;
    }
}
