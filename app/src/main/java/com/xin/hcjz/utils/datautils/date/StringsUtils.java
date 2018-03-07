package com.xin.hcjz.utils.datautils.date;

/**
 * Created by Y on 2018/3/1.
 */

public class StringsUtils {
    public static boolean contains(String[] strings,String target){
        for (String string:strings){
            if (string.equals(target)){
                return true;
            }
        }
        return false;
    }
}
