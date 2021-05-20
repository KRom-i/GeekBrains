package Methods;

import WorkDataBase.ClientClass;
import WorkDataBase.ClientDataBase;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FindClientDateBase {

    private List<ClientClass> clientList;

    public FindClientDateBase(){
        this.clientList = ClientDataBase.newListClient ();
    }

    public List<ClientClass> methodFindClient(String str){

        List<ClientClass> clientClassList = new ArrayList<> ();

        String[] strings = str.split (" ");

        for (ClientClass c: clientList
             ) {

            for (String s: strings
                 ) {
                if (c.getFirstName ().startsWith (editText (s))){
                    clientClassList.add (c);
                    break;
                } else if (c.getLastName ().startsWith (editText(s))){
                    clientClassList.add (c);
                    break;
                }

                }
            }
        System.out.println (clientClassList);
        return clientClassList;
    }

    private String editText(String str){

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
