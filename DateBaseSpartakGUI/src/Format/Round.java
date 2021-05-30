package Format;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Round {

    public Round () {
    }

    public double getDoubleValue(double doubleValue){
        int places = 2;
        BigDecimal db = new BigDecimal(Double.toString(doubleValue));
        db = db.setScale(places, RoundingMode.HALF_UP);
        return  db.doubleValue();
    }
}
