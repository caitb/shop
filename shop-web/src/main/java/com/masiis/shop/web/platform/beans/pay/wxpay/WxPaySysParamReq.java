package com.masiis.shop.web.platform.beans.pay.wxpay;

import com.masiis.shop.common.annotation.SignField;

/**
 * 微信支付系统内调用参数bean
 *
 * Created by lzh on 2016/3/10.
 */
public class WxPaySysParamReq {
    private String orderId;
    @SignField
    private String sign;
    private String nonceStr;
    private String signType;
    private String successUrl;
    private String errorUrl;
    private String cancelUrl;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }
}
