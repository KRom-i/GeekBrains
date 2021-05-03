package Lesson_4_Thead.MyTest;

import java.util.ArrayList;
import java.util.List;

public class MyObject extends ArrayList<String> {

    public MyObject() {

        for (int i = 0; i < 1_000_000; i++) {
            super.add(String.format("%s string",(i + 1)));
        }
    }

 public void demoMyObject(int x) {

     for (int i = 0; i < this.size(); i++) {
         System.out.println(super.get(i) + "-Thread" + x);

     }
 }
}
