package com.xin.hcjz.utils.uiutils.sweetalertdialog;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Y on 2018/1/31.
 */

public class SweetAlertDialogListener {
    public interface onClickListener{
        void onConfirm(SweetAlertDialog sweetAlertDialog);
        void onCancel(SweetAlertDialog sweetAlertDialog);
    }
}
