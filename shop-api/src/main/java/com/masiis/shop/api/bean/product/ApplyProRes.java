package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.dao.po.PfBorderConsignee;
import com.masiis.shop.dao.po.PfUserSkuStock;

/**
 * Created by JingHao on 2016/5/20 0020.
 */
public class ApplyProRes extends BaseRes{

    private ComUserAddress comUserAddress;//地址属性

    private PfUserSkuStock pfUserSkuStock;//库存属性

    private ComSku comSku;//商品属性

    private String comSkuImg;//商品小图

    private String lowerCount;//下级人数

    private String priceDiscount;//合伙人价

    private String levelStock;//代理等级拿货门槛

    private Long orderCode;//订单id

    private PfBorderConsignee pfBorderConsignee;//订单地址

    public ComUserAddress getComUserAddress() {
        return comUserAddress;
    }

    public void setComUserAddress(ComUserAddress comUserAddress) {
        this.comUserAddress = comUserAddress;
    }

    public PfUserSkuStock getPfUserSkuStock() {
        return pfUserSkuStock;
    }

    public void setPfUserSkuStock(PfUserSkuStock pfUserSkuStock) {
        this.pfUserSkuStock = pfUserSkuStock;
    }

    public ComSku getComSku() {
        return comSku;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    public String getComSkuImg() {
        return comSkuImg;
    }

    public void setComSkuImg(String comSkuImg) {
        this.comSkuImg = comSkuImg;
    }

    public String getLowerCount() {
        return lowerCount;
    }

    public void setLowerCount(String lowerCount) {
        this.lowerCount = lowerCount;
    }

    public String getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(String priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public String getLevelStock() {
        return levelStock;
    }

    public void setLevelStock(String levelStock) {
        this.levelStock = levelStock;
    }

    public Long getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Long orderCode) {
        this.orderCode = orderCode;
    }

    public PfBorderConsignee getPfBorderConsignee() {
        return pfBorderConsignee;
    }

    public void setPfBorderConsignee(PfBorderConsignee pfBorderConsignee) {
        this.pfBorderConsignee = pfBorderConsignee;
    }
}
