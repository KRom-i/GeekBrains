package Format;

import java.util.Random;

public class RandomCod {

    public String get(){
        Random random=new Random();
        int rage=9999;
        int generator=1000+random.nextInt(rage-1000);
        return String.valueOf(generator);
    }

}
