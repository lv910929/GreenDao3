package com.lv.greendao3.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

import com.lv.greendao3.data.MainEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
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

    /**
     * @param phoneNumber
     * @param message
     */
    public static void doSendSMSTo(Activity activity, String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            activity.startActivity(intent);
        }
    }

    //跳转到拨打电话
    public static void intentPhoneCall(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //复制文本到剪切板
    public static void copyTextToClip(Context context, String text) {
        ClipboardManager clipboardManager = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // Creates a new text clip to put on the
        ClipData clip = ClipData.newPlainText("content", text);
        clipboardManager.setPrimaryClip(clip);
    }

    //服务是否开启
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (serviceList == null || serviceList.isEmpty())
            return false;
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) && TextUtils.equals(
                    serviceList.get(i).service.getPackageName(), context.getPackageName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    //通知更新通讯录
    public static void notifyUpdateContacts() {
        EventBus.getDefault().post(new MainEvent(0, null));
    }
}
