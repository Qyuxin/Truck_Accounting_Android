package com.xin.hcjz.utils.uiutils.toast;

import android.widget.Toast;

import com.xin.hcjz.ui.base.App;

/**
 * Created by Y on 2018/2/27.
 */

public class ToastUtils {
    private static Toast toast;
    public static void showToast(String msg){
        if (toast == null){
            toast = Toast.makeText(App.getInstance(),msg,Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }
}
