package com.xin.hcjz.utils.uiutils.Picker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;

import java.util.Calendar;

import cn.qqtheme.framework.picker.DatePicker;

/**
 * Created by Y on 2017/8/8.
 */

public class PickerUtils {

    public void onYearMonthPicker(Context context, final PickerListener.onYearMonthListener listener) {
        DatePicker picker = new DatePicker((Activity) context, DatePicker.YEAR_MONTH);
//        picker.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
//        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
        picker.setGravity(Gravity.CENTER);//弹框居中

        int color = Color.parseColor("#fda538");
        picker.setTextColor(color);
        picker.setDividerColor(color);
        picker.setCancelTextColor(color);
        picker.setSubmitTextColor(color);
        picker.setTopLineColor(color);
        picker.setPressedTextColor(color);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        picker.setRangeStart(2000, 1);
        picker.setRangeEnd(year, month);
        picker.setSelectedItem(year, month);

        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                listener.onYearMonthListener(year, month);
            }
        });
        picker.show();
    }

    public void onYearMonthDayPicker(Context context, final PickerListener.onYearMonthDayListener listener) {
        DatePicker picker = new DatePicker((Activity) context, DatePicker.YEAR_MONTH_DAY);
//        picker.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
//        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
        picker.setGravity(Gravity.CENTER);//弹框居中

        int color = Color.parseColor("#fda538");
        picker.setTextColor(color);
        picker.setDividerColor(color);
        picker.setCancelTextColor(color);
        picker.setSubmitTextColor(color);
        picker.setTopLineColor(color);
        picker.setPressedTextColor(color);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        picker.setRangeStart(2000, 1, 1);
        picker.setRangeEnd(year, month, day);
        picker.setSelectedItem(year, month, day);

        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                listener.onYearMonthDayListener(year, month, day);
            }
        });
        picker.show();
    }

    public void onYearMonthDayPickerSinceToday(Context context, final PickerListener.onYearMonthDayListener listener) {
        DatePicker picker = new DatePicker((Activity) context, DatePicker.YEAR_MONTH_DAY);
//        picker.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
//        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
        picker.setGravity(Gravity.CENTER);//弹框居中

        int color = Color.parseColor("#fda538");
        picker.setTextColor(color);
        picker.setDividerColor(color);
        picker.setCancelTextColor(color);
        picker.setSubmitTextColor(color);
        picker.setTopLineColor(color);
        picker.setPressedTextColor(color);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        picker.setRangeStart(year, month, day);
        picker.setRangeEnd(2039, 12, 31);
        picker.setSelectedItem(year, month, day);

        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                listener.onYearMonthDayListener(year, month, day);
            }
        });
        picker.show();
    }

}
