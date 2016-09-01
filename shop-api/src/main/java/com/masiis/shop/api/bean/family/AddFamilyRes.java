package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BaseRes;

/**
 * Created by jiajinghao on 2016/8/11.
 */
public class AddFamilyRes extends BaseRes {

    private Integer isDuplicatePhone;//手机号是否重复 0 ：未重复 1 重复

    private Integer isLegalMobile;//手机号是否合法 0 合法 1 不合法
    private Integer islegalWxcode;//微信号是否合法

    public Integer getIsDuplicatePhone() {
        return isDuplicatePhone;
    }

    public void setIsDuplicatePhone(Integer isDuplicatePhone) {
        this.isDuplicatePhone = isDuplicatePhone;
    }

    public Integer getIsLegalMobile() {
        return isLegalMobile;
    }

    public void setIsLegalMobile(Integer isLegalMobile) {
        this.isLegalMobile = isLegalMobile;
    }

    public Integer getIslegalWxcode() {
        return islegalWxcode;
    }

    public void setIslegalWxcode(Integer islegalWxcode) {
        this.islegalWxcode = islegalWxcode;
    }
}
