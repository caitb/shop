package com.masiis.shop.api.bean.message;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * @Date 2016/8/4
 * @Author lzh
 */
public class ShopMessageCreateReq extends BaseBusinessReq {
    private String message;
    private Integer sendType;
    private Integer urlAppType;
    private String urlAppParam;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getUrlAppType() {
        return urlAppType;
    }

    public void setUrlAppType(Integer urlAppType) {
        this.urlAppType = urlAppType;
    }

    public String getUrlAppParam() {
        return urlAppParam;
    }

    public void setUrlAppParam(String urlAppParam) {
        this.urlAppParam = urlAppParam;
    }
}
