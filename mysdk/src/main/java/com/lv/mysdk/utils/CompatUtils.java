package com.lv.mysdk.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 描述 : 工具类
 */
public class CompatUtils {

    private int getDisplayWidth(Context context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int W = mDisplayMetrics.widthPixels;
        Log.i("Main", "Width = " + W);
        return W;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
