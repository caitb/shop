package com.masiis.shop.api.bean.system;

import com.masiis.shop.api.bean.base.BaseResModel;

/**
 * @Date:2016/4/25
 * @auth:lzh
 */
public class RegisterValidCodeRes extends BaseResModel {
    private String validcode;

    public String getValidcode() {
        return validcode;
    }

    public void setValidcode(String validcode) {
        this.validcode = validcode;
    }
}
