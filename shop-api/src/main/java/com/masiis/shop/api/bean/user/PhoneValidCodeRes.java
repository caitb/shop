package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * @Date 2016/5/24
 * @Auther lzh
 */
public class PhoneValidCodeRes extends BaseBusinessRes {
    private String validCode;
    private Integer exTime;

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public Integer getExTime() {
        return exTime;
    }

    public void setExTime(Integer exTime) {
        this.exTime = exTime;
    }
}
