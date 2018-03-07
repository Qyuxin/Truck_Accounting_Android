package com.xin.hcjz.utils.uiutils.sweetalertdialog;

import android.app.Activity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Y on 2018/2/27.
 */

public class SweetAlertDialogUtils {
    private static SweetAlertDialog sweetAlertDialog = null;


    public static SweetAlertDialog getProgressDialog(Activity activity, String title) {
        dismissDialog();
        sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setCancelable(false);
        return sweetAlertDialog;
    }

    public static void showProgressDialog(Activity activity,String title){
        getProgressDialog(activity,title).show();
    }

    public static void dismissDialog() {
        if (sweetAlertDialog == null){
            return;
        }
        if (sweetAlertDialog.isShowing()) {
            sweetAlertDialog.dismiss();
        }
    }


    public static void showWarningDialog(final Activity mySelf, String title, String confirmText, String cancelText, final SweetAlertDialogListener.onClickListener listener) {
        sweetAlertDialog = new SweetAlertDialog(mySelf, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setConfirmText(confirmText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        listener.onConfirm(sDialog);
                    }
                })
                .setCancelText(cancelText)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        listener.onCancel(sweetAlertDialog);
                    }
                });
        sweetAlertDialog.show();
    }
}
