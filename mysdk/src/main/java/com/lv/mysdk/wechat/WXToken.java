package com.lv.mysdk.wechat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lv on 2016/6/2.
 */
public class WXToken implements Serializable {

    /**
     * appid : wx0545a770b0959342
     * noncestr : enEP3EQ1aCFTvYuV
     * package : Sign=WXPay
     * partnerid : 1309811401
     * prepayid : wx2016060217234097f92300fb0145499215
     * sign : B7CD25A8D49A2F7EEABCE7816AF91036
     * timestamp : 1464859420
     */

    private String appid;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timestamp;

    private long payOrderId;//订单号

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(long payOrderId) {
        this.payOrderId = payOrderId;
    }
}
