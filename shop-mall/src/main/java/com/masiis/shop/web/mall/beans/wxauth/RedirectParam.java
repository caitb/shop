package com.masiis.shop.web.mall.beans.wxauth;

import com.masiis.shop.common.util.SHAUtils;

/**
 * Created by lzh on 2016/2/25.
 */
public class RedirectParam {
    private String code;
    private String msg;
    private String surl;
    private String nonceStr;
    private Object data;
    private String signCk;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public Object getData() { return data; }

    public void setData(Object data) {
        this.data = data;
    }

    public String getSignCk() {
        return signCk;
    }

    public void setSignCk(String signCk) {
        this.signCk = signCk;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    @Override
    public String toString() {
        return "RedirectParam{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", surl='" + surl + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", data=" + data +
                '}';
    }

    public void creatSign(){
        this.signCk = SHAUtils.encodeSHA1(this.toString().getBytes());
    }
}
