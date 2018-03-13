package com.xin.hcjz.utils.datautils.date;

/**
 * Created by Y on 2018/3/1.
 */

public class StringsUtils {
    public static boolean contains(String[] strings, String target) {
        for (String string : strings) {
            if (string.equals(target)) {
                return true;
            }
        }
        return false;
    }

    public static int getIndex(String[] strings, String str) {
        for (int i = 0; i < strings.length; i++) {
            if (str.equals(strings[i])) {
                return i;
            }
        }
        return -1;
    }
}
