package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfUserSku;

/**
 * Created by JingHao on 2016/5/19 0019.
 */
public class ProDetailRes extends BaseRes {

    private Product product;

    private PfUserSku pfUserSku;

    private PfBorder pfBorder;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PfUserSku getPfUserSku() {
        return pfUserSku;
    }

    public void setPfUserSku(PfUserSku pfUserSku) {
        this.pfUserSku = pfUserSku;
    }

    public PfBorder getPfBorder() {
        return pfBorder;
    }

    public void setPfBorder(PfBorder pfBorder) {
        this.pfBorder = pfBorder;
    }
}
