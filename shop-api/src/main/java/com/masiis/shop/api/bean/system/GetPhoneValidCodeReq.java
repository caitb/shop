package com.masiis.shop.api.bean.system;

import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.common.annotation.SignField;

/**
 * @Date 2016/4/28
 * @Auther lzh
 */
public class GetPhoneValidCodeReq extends BaseBusinessReq {
    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
