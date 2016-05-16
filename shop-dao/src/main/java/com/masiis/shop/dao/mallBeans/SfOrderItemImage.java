package com.masiis.shop.dao.mallBeans;

import com.masiis.shop.dao.po.SfOrderItem;

/**
 * 小铺订单关系
 * @author muchaofeng
 * @date 2016/4/8 19:33
 */

public class SfOrderItemImage {
    private String skuUrl;//商品首页地址
    private String skuMoney;

    public void setSkuMoney(String skuMoney) {
        this.skuMoney = skuMoney;
    }
    public String getSkuMoney() {
        return skuMoney;
    }
    public void setSkuUrl(String skuUrl) {
        this.skuUrl = skuUrl;
    }

    public String getSkuUrl() {
        return skuUrl;
    }
}
