package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * @Date 2016/5/23
 * @Auther lzh
 */
public class CheckPUserPhoneRes extends BaseBusinessRes {
    private Integer phoneIsOk;

    public Integer getPhoneIsOk() {
        return phoneIsOk;
    }

    public void setPhoneIsOk(Integer phoneIsOk) {
        this.phoneIsOk = phoneIsOk;
    }
}
