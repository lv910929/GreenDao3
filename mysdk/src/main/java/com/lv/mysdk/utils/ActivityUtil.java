package com.lv.mysdk.utils;

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

import com.lv.mysdk.R;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ActivityUtil {

    public static void startActivityNotInActivity(Context context, Class targetActivity, Bundle bundle) {
        if (!DoubleClickUtil.isFastDoubleClick()) return;
        Intent intent = new Intent();
        intent.setClass(context, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void startActivity(Activity activity, Class targetActivity, Bundle bundle) {
        if (!DoubleClickUtil.isFastDoubleClick()) return;
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void startActivityWithFinish(Activity activity, Class targetActivity, Bundle bundle) {
        if (!DoubleClickUtil.isFastDoubleClick()) return;
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
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
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void startActivityForResult(Activity activity, String action, Bundle bundle, int result) {
        if (!DoubleClickUtil.isFastDoubleClick())
            return;
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, result);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void startActivityForResult(Activity activity, Class targetActivity, int result) {
        startActivityForResult(activity, targetActivity, result, null);
    }


    public static void startActivityForResult(Activity activity, String action, Uri uri, int result) {
        startActivityForResult(activity, action, uri, result, null);
    }

    public static void startActivityForResult(Activity activity, String action, Uri uri, int result, Bundle bundle) {
        if (!DoubleClickUtil.isFastDoubleClick()) return;
        Intent intent = new Intent(action, uri);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, result);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void startActivityForResult(Activity activity, Class targetActivity, int result, Bundle bundle) {
        if (!DoubleClickUtil.isFastDoubleClick()) return;
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, result);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int result, Bundle bundle) {
        if (intent == null || !DoubleClickUtil.isFastDoubleClick())
            return;
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivityForResult(intent, result);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
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

    public static final String ImagePositionForImageShow = "PositionForImageShow";
    public static final String ImageArrayList = "BigImageArrayList";
    public static final String WebTitleFlag = "WebTitleFlag";
    public static final String WebTitle = "WebTitle";
    public static final String WebUrl = "WebUrl";
    public static final String DayDate = "DayDate";

    public static void startAppShareText(Context context, String shareTitle, String shareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // 纯文本
        shareIntent.putExtra(Intent.EXTRA_TITLE, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    public static void startAppShareImage(Context context, String shareTitle, String shareText, Uri uri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TITLE, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    public void shareMsg(Context context, String activityTitle, String msgTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }

    /**
     * 跳转到网络设置界面
     */
    public static void redirectToNETWORK(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        ((Activity) context).startActivityForResult(intent, 0);
        return;
    }

}
