package GUIMain.Styles;

import java.io.File;
import java.net.MalformedURLException;

public class CssUrl {

    public String get()  {
        String url = "";
        try {
            url = new File ("src/GUIMain/Styles/style2.css").toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace ();
        }
        return url;
    }

    public String getPanorama()  {
        String url = "";
        try {
            url = new File ("img/panorama/style3.css").toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace ();
        }
        return url;
    }
}
