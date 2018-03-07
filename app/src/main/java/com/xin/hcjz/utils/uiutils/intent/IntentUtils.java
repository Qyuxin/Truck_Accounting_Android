package com.xin.hcjz.utils.uiutils.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Y on 2018/2/27.
 */

public class IntentUtils {
    public static void gotoNext(Activity mySelf,Class next){
        Intent intent = new Intent();
        intent.setClass(mySelf,next);
        mySelf.startActivity(intent);
    }
    public static void gotoNext(Activity mySelf,Class next,boolean ifCloseCur){
        Intent intent = new Intent();
        intent.setClass(mySelf,next);
        mySelf.startActivity(intent);
        if (ifCloseCur){
            mySelf.finish();
        }
    }
    public static void gotoNext(Activity mySelf, Class next, Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(mySelf,next);
        intent.putExtras(bundle);
        mySelf.startActivity(intent);
    }
}
