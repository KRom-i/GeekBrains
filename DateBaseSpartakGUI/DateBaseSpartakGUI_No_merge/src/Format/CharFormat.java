package Format;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CharFormat {

    private Charset fUtf8 = Charset.forName("utf8");
    private Charset fCp886 = Charset.forName("866");

    public String utf8ToCp886(String strUtf8){
        try {
            return strUtf8.getBytes("CP866").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String utf8ToCp1251(String strUtf8){

        try {
            return new String(strUtf8.getBytes(StandardCharsets.UTF_8), "CP1251");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
