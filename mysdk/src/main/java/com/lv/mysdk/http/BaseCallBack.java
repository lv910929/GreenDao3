package com.lv.mysdk.http;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lv on 2016/10/26.
 * HTTP回调的统一处理
 */
public abstract class BaseCallBack<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            if (response.body() != null)
                onSuccess(response.body());
            else
                onFailure(HttpConstant.FAIL_CODE_1001, "数据为空");
        } else {
            onFailure(HttpConstant.FAIL_CODE_1002, "连接超时，请稍后再试");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();
        if (t.toString().contains("Socket closed")) {
            onFailure(HttpConstant.FAIL_CODE_1003, "手动关闭请求");
        } else {
            if (t instanceof HttpException) {
                HttpException httpException = (HttpException) t;
                int code = httpException.code();
                String msg = httpException.getMessage();
                if (code == HttpConstant.FAIL_CODE_504) {
                    msg = "网络不给力";
                } else if (code == HttpConstant.FAIL_CODE_502 || code == HttpConstant.FAIL_CODE_404) {
                    msg = "服务器异常，请稍后再试";
                }
                onFailure(code, msg);
            } else {
                onFailure(HttpConstant.FAIL_CODE_1004, "服务器异常，请稍后再试");
            }
        }
    }

    //成功的回调
    public abstract void onSuccess(T object);

    //失败的回调
    public abstract void onFailure(int code, String msg);
}
