package com.masiis.shop.api.bean.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.masiis.shop.common.annotation.SignField;

/**
 * Created by wangbingjian on 2016/4/22.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BaseRes {
    /**
     * 返回状态结果码 0为成功 其他各业务自定义
     */
    private String resCode;
    private String resMsg;
    private String nonceStr;
    @SignField
    private String sign;

    public BaseRes(){}

    public BaseRes(String resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

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
