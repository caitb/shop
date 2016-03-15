package com.masiis.shop.admin.beans.order;

import com.masiis.shop.dao.po.*;

import java.util.List;

/**
 * Created by caitingbiao on 2016/3/14.
 */
public class Order {

    private ComUser comUser;

    private PfBorder pfBorder;
    private PfCorder pfCorder;

    private PfBorderConsignee pfBorderConsignee;
    private PfCorderConsignee pfCorderConsignee;

    private List<PfBorderFreight> pfBorderFreights;
    private List<PfCorderFreight> pfCorderFreights;

    private List<PfBorderItem> pfBorderItems;

    private PfBorderPayment pfBorderPayment;
    private PfCorderPayment pfCorderPayment;

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

    public List<PfBorderItem> getPfBorderItems() {
        return pfBorderItems;
    }

    public void setPfBorderItems(List<PfBorderItem> pfBorderItems) {
        this.pfBorderItems = pfBorderItems;
    }

    public PfBorderPayment getPfBorderPayment() {
        return pfBorderPayment;
    }

    public void setPfBorderPayment(PfBorderPayment pfBorderPayment) {
        this.pfBorderPayment = pfBorderPayment;
    }

    public PfCorderPayment getPfCorderPayment() {
        return pfCorderPayment;
    }

    public void setPfCorderPayment(PfCorderPayment pfCorderPayment) {
        this.pfCorderPayment = pfCorderPayment;
    }
}
