package Lesson_6;

import java.util.Arrays;

public class WorkingWithArrays {

    public static void main(String[] args) {

        Integer[] integers = WorkingWithArrays.newArrayAfterLastFour(1, 1,2,3,4,5,4,7,8);


        for (int i = 0; i < integers.length; i++) {

            System.out.println(integers[i]);
        }

    }


    public static Integer[] newArrayAfterLastFour(Integer... integers){
        int[] ints = new int[3];
        return integers;
    }

    public static boolean availabilityOneFour(Integer... integers){
        return false;
    }


}
