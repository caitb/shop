package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.PfUserSku;

/**
 * Created by JingHao on 2016/5/19 0019.
 */
public class ProDetailRes extends BaseRes {

    private Product product;

    private PfUserSku pfUserSku;

    private Integer orderStatus;

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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
