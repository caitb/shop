package com.masiis.shop.dao.beans.user;

import com.masiis.shop.dao.po.ComSku;

/**
 * @Date 2016/7/20
 * @Author lzh
 */
public class UserSkuInfo extends ComSku {
    private String pName;
    private String unitName;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
