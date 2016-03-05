package com.masiis.shop.dao.beans.system;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuImage;


/**
 * Created by muchaofeng on 2016/3/4.
 */
public class IndexComSku extends ComSkuImage {

    private  String discountLevel;
    private ComSku comSku;//商品属性

    public void setDiscountLevel(String discountLevel) {
        this.discountLevel = discountLevel;
    }
    public String getDiscountLevel() {
        return discountLevel;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    public ComSku getComSku() {
        return comSku;
    }
}
