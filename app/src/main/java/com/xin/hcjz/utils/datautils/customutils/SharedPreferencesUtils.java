package com.xin.hcjz.utils.datautils.customutils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.xin.hcjz.ui.base.App;

/**
 * Created by Y on 2018/3/13.
 */

public class SharedPreferencesUtils {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static SharedPreferences getShared(String name) {
        return App.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getSharedEdit(String name) {
        return getShared(name).edit();
    }

    public static void putTjEveryNumber(int number) {
        getSharedEdit("tjEveryNumber").putInt("number", number).apply();
    }

    public static int getTjEveryNumber() {
        return getShared("tjEveryNumber").getInt("number", 10);
    }
}
