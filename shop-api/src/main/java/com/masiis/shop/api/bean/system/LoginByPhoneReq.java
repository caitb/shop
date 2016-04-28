package com.masiis.shop.api.bean.system;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * @Date 2016/4/28
 * @Auther lzh
 */
public class LoginByPhoneReq extends BaseReq {
    private String phoneNum;
    private String validcode;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getValidcode() {
        return validcode;
    }

    public void setValidcode(String validcode) {
        this.validcode = validcode;
    }
}
