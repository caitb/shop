package com.masiis.shop.api.bean.base;

import com.masiis.shop.common.annotation.SignField;

/**
 * @Date 2016/4/28
 * @Auther lzh
 */
public class BaseReq {
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 签名字段
     */
    @SignField
    private String sign;

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
