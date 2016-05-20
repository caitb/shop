package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;

/**
 * Created by hzz on 2016/5/19.
 */
public class ComUserAddressRes extends BaseRes {

    private String resultCode;
    private String resultMessage;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
