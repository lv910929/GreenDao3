package com.lv.greendao3.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Map;

public class ActivityUtil {

    public static void startActivityNotInActivity(Context context, Class targetActivity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void startActivity(Activity activity, Class targetActivity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        //activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void startActivityWithFinish(Activity activity, Class targetActivity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        activity.finish();
        //activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void startActivity(Activity activity, Class targetActivity, Map<String, String> map, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        //activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

}
