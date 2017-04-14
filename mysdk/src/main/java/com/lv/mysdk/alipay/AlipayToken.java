package com.lv.mysdk.alipay;


import java.io.Serializable;

/**
 * Created by Lv on 2016/5/31.
 */
public class AlipayToken implements Serializable {
    /**
     * body : 印刷画 印刷画 陶瓷摆件 金属摆件
     * sec_id : 0001
     * _input_charset : utf-8
     * subject : BDHOME移动APP订单
     * paymentId : 25769816008
     * totalAmount : 3688.00
     * notify_url : http://m.bdhome.cn/appAlipayNotify_url.action
     * req_id : 20160531155348
     * seller_email : bdhome@bdhome.hk
     */

    private String body;
    private String sec_id;
    private String _input_charset;
    private String subject;
    private String paymentId;//交易号
    private String totalAmount;
    private String notify_url;
    private String req_id;
    private String seller_email;

    private long payOrderId;//订单号
    private PayResult payResult;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSec_id() {
        return sec_id;
    }

    public void setSec_id(String sec_id) {
        this.sec_id = sec_id;
    }

    public String get_input_charset() {
        return _input_charset;
    }

    public void set_input_charset(String _input_charset) {
        this._input_charset = _input_charset;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(long payOrderId) {
        this.payOrderId = payOrderId;
    }

    public PayResult getPayResult() {
        return payResult;
    }

    public void setPayResult(PayResult payResult) {
        this.payResult = payResult;
    }
}
