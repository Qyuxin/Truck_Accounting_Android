package com.xin.hcjz.utils.uiutils.Picker;

/**
 * Created by Y on 2017/8/8.
 */


public class PickerListener {
    public interface onYearMonthListener {
        void onYearMonthListener(String year, String month);
    }

    public interface onYearMonthDayListener {
        void onYearMonthDayListener(String year, String month, String day);
    }
}