package com.masiis.shop.admin.beans.order;

import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.dao.po.*;

import java.util.List;

/**
 * Created by caitingbiao on 2016/3/14.
 */
public class Order {

    private ComUser comUser;

    private PfBorder pfBorder;
    private PfCorder pfCorder;
    private SfOrder sfOrder;

    private List<PfBorderItem> pfBorderItems;
    private List<SfOrderItem> sfOrderItems;

    private PfBorderConsignee pfBorderConsignee;
    private PfCorderConsignee pfCorderConsignee;
    private SfOrderConsignee sfOrderConsignee;

    private List<PfBorderFreight> pfBorderFreights;
    private List<PfCorderFreight> pfCorderFreights;
    private List<SfOrderFreight> sfOrderFreights;

    private List<PfBorderPayment> pfBorderPayments;
    private List<PfCorderPayment> pfCorderPayments;
    private List<SfOrderPayment> sfOrderPayments;

    private List<ProductInfo> productInfos;

    public ComUser getComUser() {
        return comUser;
    }

    public void setComUser(ComUser comUser) {
        this.comUser = comUser;
    }

    public PfBorder getPfBorder() {
        return pfBorder;
    }

    public void setPfBorder(PfBorder pfBorder) {
        this.pfBorder = pfBorder;
    }

    public PfCorder getPfCorder() {
        return pfCorder;
    }

    public void setPfCorder(PfCorder pfCorder) {
        this.pfCorder = pfCorder;
    }

    public SfOrder getSfOrder() {
        return sfOrder;
    }

    public void setSfOrder(SfOrder sfOrder) {
        this.sfOrder = sfOrder;
    }

    public List<PfBorderItem> getPfBorderItems() {
        return pfBorderItems;
    }

    public void setPfBorderItems(List<PfBorderItem> pfBorderItems) {
        this.pfBorderItems = pfBorderItems;
    }

    public List<SfOrderItem> getSfOrderItems() {
        return sfOrderItems;
    }

    public void setSfOrderItems(List<SfOrderItem> sfOrderItems) {
        this.sfOrderItems = sfOrderItems;
    }

    public PfBorderConsignee getPfBorderConsignee() {
        return pfBorderConsignee;
    }

    public void setPfBorderConsignee(PfBorderConsignee pfBorderConsignee) {
        this.pfBorderConsignee = pfBorderConsignee;
    }

    public PfCorderConsignee getPfCorderConsignee() {
        return pfCorderConsignee;
    }

    public void setPfCorderConsignee(PfCorderConsignee pfCorderConsignee) {
        this.pfCorderConsignee = pfCorderConsignee;
    }

    public SfOrderConsignee getSfOrderConsignee() {
        return sfOrderConsignee;
    }

    public void setSfOrderConsignee(SfOrderConsignee sfOrderConsignee) {
        this.sfOrderConsignee = sfOrderConsignee;
    }

    public List<PfBorderFreight> getPfBorderFreights() {
        return pfBorderFreights;
    }

    public void setPfBorderFreights(List<PfBorderFreight> pfBorderFreights) {
        this.pfBorderFreights = pfBorderFreights;
    }

    public List<PfCorderFreight> getPfCorderFreights() {
        return pfCorderFreights;
    }

    public void setPfCorderFreights(List<PfCorderFreight> pfCorderFreights) {
        this.pfCorderFreights = pfCorderFreights;
    }

    public List<SfOrderFreight> getSfOrderFreights() {
        return sfOrderFreights;
    }

    public void setSfOrderFreights(List<SfOrderFreight> sfOrderFreights) {
        this.sfOrderFreights = sfOrderFreights;
    }

    public List<PfBorderPayment> getPfBorderPayments() {
        return pfBorderPayments;
    }

    public void setPfBorderPayments(List<PfBorderPayment> pfBorderPayments) {
        this.pfBorderPayments = pfBorderPayments;
    }

    public List<PfCorderPayment> getPfCorderPayments() {
        return pfCorderPayments;
    }

    public void setPfCorderPayments(List<PfCorderPayment> pfCorderPayments) {
        this.pfCorderPayments = pfCorderPayments;
    }

    public List<SfOrderPayment> getSfOrderPayments() {
        return sfOrderPayments;
    }

    public void setSfOrderPayments(List<SfOrderPayment> sfOrderPayments) {
        this.sfOrderPayments = sfOrderPayments;
    }

    public List<ProductInfo> getProductInfos() {
        return productInfos;
    }

    public void setProductInfos(List<ProductInfo> productInfos) {
        this.productInfos = productInfos;
    }

    @Override
    public String toString() {
        return "Order{" +
                "comUser=" + comUser +
                ", pfBorder=" + pfBorder +
                ", pfCorder=" + pfCorder +
                ", sfOrder=" + sfOrder +
                ", pfBorderItems=" + pfBorderItems +
                ", sfOrderItems=" + sfOrderItems +
                ", pfBorderConsignee=" + pfBorderConsignee +
                ", pfCorderConsignee=" + pfCorderConsignee +
                ", sfOrderConsignee=" + sfOrderConsignee +
                ", pfBorderFreights=" + pfBorderFreights +
                ", pfCorderFreights=" + pfCorderFreights +
                ", sfOrderFreights=" + sfOrderFreights +
                ", pfBorderPayments=" + pfBorderPayments +
                ", pfCorderPayments=" + pfCorderPayments +
                ", sfOrderPayments=" + sfOrderPayments +
                ", productInfos=" + productInfos +
                '}';
    }
}
