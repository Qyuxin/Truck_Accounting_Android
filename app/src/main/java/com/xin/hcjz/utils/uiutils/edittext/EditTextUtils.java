package com.xin.hcjz.utils.uiutils.edittext;

import android.text.TextUtils;
import android.widget.EditText;


import java.util.List;

/**
 * Created by huangju on 2018/1/11.
 */

public class EditTextUtils {
    /**
     * @param et
     * @param errMsg
     * @return 为空 --> true
     * 不为空 --> false
     */
    public static boolean checkEtIsNull(EditText et, String errMsg) {
        String str = et.getText().toString().trim();
        if (TextUtils.isEmpty(str)) {
            setEtError(et, errMsg);
            return true;
        }
        return false;
    }

    /**
     * 检查身份证位数
     * @param et
     * @return 号码正确-->false  号码错误-->true
     */
    public static boolean checkEtSfzError(EditText et) {
        String str = et.getText().toString().trim();
        if (!(str.length() == 15 || str.length() == 18)) {
            setEtError(et, "请输入正确的身份证号码");
            return true;
        }
        return false;
    }

    private static void setEtError(EditText et, String errMsg) {
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        et.setError(errMsg);
    }

    /**
     * 设置List里的EditText不可编辑
     *
     * @param list
     */
    public static void setEtEnableFalse_List(List<EditText> list) {
        for (EditText editText : list) {
            editText.setEnabled(false);
        }
    }

    /**
     * 获取EditText值
     *
     * @param editText
     * @return
     */
    public static String getTextByEditText(EditText editText) {
        String etText = editText.getText().toString().trim();
        return etText;
    }
}
