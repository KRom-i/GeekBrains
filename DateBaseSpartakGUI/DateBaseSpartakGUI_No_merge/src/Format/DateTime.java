package Format;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

    public DateTime (){

    }

    public String currentDate(){
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date());
    }

    public String currentTime(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}
