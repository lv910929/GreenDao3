package com.lv.mysdk.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * ToastUtils
 */
public class MyToast {

    private static Toast mToast = null;

    private static void showShortToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}
