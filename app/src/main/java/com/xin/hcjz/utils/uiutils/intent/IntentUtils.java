package com.xin.hcjz.utils.uiutils.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Y on 2018/2/27.
 */

public class IntentUtils {
    public static void gotoNext(Activity mySelf, Class next) {
        mySelf.startActivity(getDefaultIntent(mySelf, next));
    }

    public static void gotoNext(Activity mySelf, Class next, boolean ifCloseCur) {
        mySelf.startActivity(getDefaultIntent(mySelf, next));
        if (ifCloseCur) {
            mySelf.finish();
        }
    }

    public static void gotoNext(Activity mySelf, Class next, Bundle bundle) {
        mySelf.startActivity(getIntentWithBundle(mySelf, next, bundle));
    }

    public static void gotoNextForResult(Activity mySelf, Class next, int result) {
        mySelf.startActivityForResult(getDefaultIntent(mySelf, next), result);
    }

    public static void gotoNextForResult(Activity mySelf, Class next, Bundle bundle, int result) {
        mySelf.startActivityForResult(getIntentWithBundle(mySelf, next, bundle), result);
    }

    public static Intent getDefaultIntent(Activity mySelf, Class next) {
        Intent intent = new Intent();
        intent.setClass(mySelf, next);
        return intent;
    }

    public static Intent getIntentWithBundle(Activity mySelf, Class next, Bundle bundle) {
        return getDefaultIntent(mySelf, next).putExtras(bundle);
    }
}
