package com.masiis.shop.dao.beans.order;

/**
 * 用户商品库存表
 * @author muchaofeng
 * @date 2016/4/5 12:08
 */
public class StockManage {
    private String skuName;
    private Integer stockNum;

    public Integer getStockNum() {
        return stockNum;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

}

