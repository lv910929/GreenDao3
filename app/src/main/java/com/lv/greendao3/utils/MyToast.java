package com.lv.greendao3.utils;

import android.content.Context;
import android.widget.Toast;

import com.lv.greendao3.MyApp;

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

    public static void showShortToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApp.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}
