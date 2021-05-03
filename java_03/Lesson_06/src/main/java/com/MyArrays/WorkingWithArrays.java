package com.MyArrays;

import java.util.Arrays;

public class WorkingWithArrays {

    public static void main(String[] args) {

        Integer[] integers = WorkingWithArrays.newArrayAfterLastFour(5, 1,4,3,5,8,5,5,4,5,7);

        for (int i = 0; i < integers.length; i++) {
            System.out.println(integers[i]);
        }

        System.out.println(availabilityOneFour(1,2, 1,3,4,5,6,7,8));
        System.out.println(availabilityOneFour(1,3, 1,3,4,5,6,7,8));

    }


    public static Integer[] newArrayAfterLastFour(Integer lastOfInteger, Integer... integers){

        int index = -1;

        for (int i = 0; i < integers.length; i++) {
            if (integers[i] == lastOfInteger) index = i + 1;
        }

        if (index > -1){
            return Arrays.copyOfRange (integers, index, integers.length);
        } else {
            throw new RuntimeException();
        }

    }

    public static boolean availabilityOneFour(Integer searchA, Integer searchB, Integer... integers){
        return Arrays.binarySearch(integers, searchA) > -1 && Arrays.binarySearch(integers, searchB) > -1;
    }


}
