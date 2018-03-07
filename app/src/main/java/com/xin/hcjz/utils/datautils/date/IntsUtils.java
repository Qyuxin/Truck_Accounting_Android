package com.xin.hcjz.utils.datautils.date;

/**
 * Created by Y on 2018/3/1.
 */

public class IntsUtils {
    public static boolean contains(int[] ints, int target) {
        for (int i : ints) {
            if (i == target) {
                return true;
            }
        }
        return false;
    }
}
