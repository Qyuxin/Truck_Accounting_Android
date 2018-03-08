package com.xin.hcjz.debug;

import com.xin.hcjz.utils.datautils.okhttp3.UrlUtils;

/**
 * Created by Y on 2018/3/7.
 */

public class DebugConfig {
    public static boolean isDebug = true;
    public static boolean urlDebug = false;

    public static boolean changeUrlDebug() {
        if (urlDebug == true) {
            //改为用户模式
            urlDebug = false;
            UrlUtils.BASE_URL = UrlUtils.BASE_URL_COMMON;
        } else {
            //改为测试模式
            urlDebug = true;
            UrlUtils.BASE_URL = UrlUtils.BASE_URL_DEBUG;
        }
        return urlDebug;

    }
}
