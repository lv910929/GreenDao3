package com.lv.mysdk.http;

/**
 * Created by Lv on 2016/10/27.
 * 通用的通讯回调
 */

public interface CommCallBack {

    /**
     * 成功的回调
     */
    void onSuccess(int what, Object result);

    /**
     * 失败的回调
     */
    void onFail(int what, int code, String result);
}
