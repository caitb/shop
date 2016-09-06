package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.po.*;

import java.util.List;

/**
 * Created by hw on 2016/8/5.
 */
public class OrderDetailRes extends BaseBusinessRes {

    private SfOrder sfOrder;//订单信息
    private SfOrderConsignee consignee;//收货人
    private String buyerName;//购买人
    private List<SfOrderItem> orderItems;//订单项
    private List<SfOrderFreight> orderFreights;//快递公司信息
    private List<SfOrderPayment> payments;//支付方式
    private List<SfOrderItemDistribution> distribution;//三级分佣
    private String skuImgUrl;//商品图片

    public String getSkuImgUrl() {
        return skuImgUrl;
    }

    public void setSkuImgUrl(String skuImgUrl) {
        this.skuImgUrl = skuImgUrl;
    }

    public List<SfOrderItemDistribution> getDistribution() {
        return distribution;
    }

    public void setDistribution(List<SfOrderItemDistribution> distribution) {
        this.distribution = distribution;
    }

    public List<SfOrderPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<SfOrderPayment> payments) {
        this.payments = payments;
    }

    public List<SfOrderFreight> getOrderFreights() {
        return orderFreights;
    }

    public void setOrderFreights(List<SfOrderFreight> orderFreights) {
        this.orderFreights = orderFreights;
    }

    public List<SfOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<SfOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public SfOrderConsignee getConsignee() {
        return consignee;
    }

    public void setConsignee(SfOrderConsignee consignee) {
        this.consignee = consignee;
    }

    public SfOrder getSfOrder() {
        return sfOrder;
    }

    public void setSfOrder(SfOrder sfOrder) {
        this.sfOrder = sfOrder;
    }
}
