package com.lv.greendao3.utils;

import com.lv.greendao3.data.MainEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Lv on 2017/4/13.
 */

public class IntentUtil {

    /**
     * 通知更新通讯录
     */
    public static void notifyUpdateContacts() {
        EventBus.getDefault().post(new MainEvent(0, null));
    }

    /**
     * 通知更新消息角标
     */
    public static void notifyUpdateMenu(Integer notifyCountNum) {
        EventBus.getDefault().post(new MainEvent(1, notifyCountNum));
    }
}
