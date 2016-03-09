package com.masiis.shop.admin.beans.product;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSpu;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
public class ProductInfo {

    private ComSku comSku;
    private ComSpu comSpu;

    public ComSku getComSku() {
        return comSku;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    public ComSpu getComSpu() {
        return comSpu;
    }

    public void setComSpu(ComSpu comSpu) {
        this.comSpu = comSpu;
    }
}
