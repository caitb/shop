package com.masiis.shop.dao.mallBeans;

import com.masiis.shop.dao.po.*;

import java.util.List;

/**
 * 小铺订单详情
 * @author muchaofeng
 * @date 2016/4/9 13:52
 */

public class OrderMallDetail {
    private String buyerName;
    private SfOrder sfOrder;
    private List<SfOrderItem> sfOrderItems;
    private List<SfOrderFreight> sfOrderFreights;//快递公司信息
    private SfOrderConsignee sfOrderConsignee;//收货人
    private List<SfOrderPayment> sfOrderPayments;//支付方式

    public void setSfOrderItems(List<SfOrderItem> sfOrderItems) {
        this.sfOrderItems = sfOrderItems;
    }

    public void setSfOrder(SfOrder sfOrder) {
        this.sfOrder = sfOrder;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setSfOrderConsignee(SfOrderConsignee sfOrderConsignee) {
        this.sfOrderConsignee = sfOrderConsignee;
    }

    public void setSfOrderFreights(List<SfOrderFreight> sfOrderFreights) {
        this.sfOrderFreights = sfOrderFreights;
    }

    public void setSfOrderPayments(List<SfOrderPayment> sfOrderPayments) {
        this.sfOrderPayments = sfOrderPayments;
    }

    public List<SfOrderFreight> getSfOrderFreights() {
        return sfOrderFreights;
    }

    public List<SfOrderItem> getSfOrderItems() {
        return sfOrderItems;
    }

    public SfOrder getSfOrder() {
        return sfOrder;
    }

    public List<SfOrderPayment> getSfOrderPayments() {
        return sfOrderPayments;
    }

    public SfOrderConsignee getSfOrderConsignee() {
        return sfOrderConsignee;
    }

    public String getBuyerName() {
        return buyerName;
    }
}

