package com.xin.hcjz.utils.datautils.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.id.list;


/**
 * Created by Y on 2018/2/28.
 */

public class DateUtils {
    public static String getStringYMD() {
        int[] ints = getYMDHMS();
        return ints[0] + "-" + format2(ints[1]) + "-" + format2(ints[2]);
    }

    public static String getStringYMDHMS() {
        int[] ints = getYMDHMS();
        return getStringYMD() + " " + format2(ints[3]) + ":" + format2(ints[4]) + ":" + format2(ints[5]);
    }

    public static int[] getYMDHMS() {
        int[] ints = new int[6];
        Calendar calendar = Calendar.getInstance();
        ints[0] = calendar.get(Calendar.YEAR);
        ints[1] = calendar.get(Calendar.MONTH) + 1;
        ints[2] = calendar.get(Calendar.DAY_OF_MONTH);
        ints[3] = calendar.get(Calendar.HOUR_OF_DAY);
        ints[4] = calendar.get(Calendar.MINUTE);
        ints[5] = calendar.get(Calendar.SECOND);
        return ints;
    }

    public static String format2(String str) {
        if (str.length() == 1) {
            return "0" + str;
        } else {
            return str;
        }
    }

    public static String format2(int date) {
        String str = date + "";
        return format2(str);
    }


    private static int[] monthWith31 = new int[]{1, 3, 5, 7, 8, 10, 12};
    private static String[] periods = new String[]{"凌晨", "上午", "下午", "晚上"};

    private static List<String> listDays = new ArrayList<>();
    private static List<String> listMonth = new ArrayList<>();
    private static List<String> listYear = new ArrayList<>();
    private static List<String> listPeriod = new ArrayList<>();

    public static List<String> getListPeriod() {
        if (listPeriod.size() == 0) {
            for (String period : periods) {
                listPeriod.add(period);
            }
        }
        return listPeriod;
    }

    public static List<String> getListYear() {
        if (listYear.size() == 0) {
            int[] ints = getYMDHMS();
            listYear.add(ints[0] + "");
            listYear.add(ints[0] - 1 + "");
            listYear.add(ints[0] - 2 + "");
            listYear.add(ints[0] - 3 + "");
        }
        return listYear;
    }

    public static List<String> getListMonth() {
        if (listMonth.size() == 0) {
            for (int i = 1; i <= 12; i++) {
                listMonth.add(i + "");
            }
        }
        return listMonth;
    }

    public static List<String> getDays(String year, String month) {
        return getDays(Integer.parseInt(year), Integer.parseInt(month));
    }

    public static List<String> getDays(int year, int month) {
        if (listDays.size() == 0) {
            for (int i = 1; i <= 30; i++) {
                listDays.add(i + "");
            }
        }

//        for (int month31 : monthWith31) {
        if (IntsUtils.contains(monthWith31, month)) {
            System.out.println("当前天数-->31");
            addDaysIfNot(new String[]{"29", "30", "31"});
//                break;
        } else {
            if (month != 2) {
                System.out.println("当前天数-->30");
                delDays(new String[]{"31"});
                addDaysIfNot(new String[]{"29", "30"});
            } else {
                if (isLeapYear(year)) {
                    System.out.println("当前天数-->29");
                    delDays(new String[]{"31", "30"});
                    addDaysIfNot(new String[]{"29"});
                } else {
                    System.out.println("当前天数-->28");
                    delDays(new String[]{"31", "30", "29"});
                }
            }
        }
//        }
        return listDays;
    }

    private static void addDaysIfNot(String[] days) {
        for (String day : days) {
            if (!listDays.contains(day)) {
                listDays.add(day);
            }
        }
    }

    private static void delDays(String[] days) {
        for (String day : days) {
            if (listDays.contains(day)) {
                listDays.remove(day);
            }
        }
    }

    private static boolean isLeapYear(int year) {
        if (year % 100 == 0) {
            year /= 100;
        }
        return year % 4 == 0;
    }
}
