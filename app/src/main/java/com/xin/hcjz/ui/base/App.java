package com.xin.hcjz.ui.base;

import android.app.Application;

/**
 * Created by Y on 2018/2/27.
 */

public class App extends Application {
    private static App instance = null;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static App getInstance(){
        return instance;
    }
}
