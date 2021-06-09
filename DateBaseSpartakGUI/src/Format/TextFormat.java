package Format;

import java.util.Locale;

public class TextFormat {

    public String upperCaseOneChar(String str){
        String newString = "";
        if (str.length () > 0) {
            String[] strings = str.split ("");
            for(int i = 0; i < strings.length; i++) {
                if (i == 0) {
                    strings[i] = strings[i].toUpperCase (Locale.ROOT);
                } else {
                    strings[i] = strings[i].toLowerCase (Locale.ROOT);
                }
                newString += strings[i];
            }
        }
        return newString;
    }
}
