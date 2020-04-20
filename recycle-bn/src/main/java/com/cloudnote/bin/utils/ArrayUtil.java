package com.cloudnote.bin.utils;

public class ArrayUtil {

    public static Integer[] StringToInteger(String[] arr){
        Integer[] arrInt=new Integer[arr.length];
        for (int i = 0; i <arr.length ; i++) {
            arrInt[i]=Integer.valueOf(arr[i]);
        }
        return arrInt;
    }
}
