package com.masiis.shop.admin.beans.order;

import com.masiis.shop.dao.po.*;

/**
 * Created by caitingbiao on 2016/3/14.
 */
public class Order {

    private ComUser comUser;
    private PfBorder pfBorder;
    private PfCorder pfCorder;
    private PfBorderConsignee pfBorderConsignee;
    private PfCorderConsignee pfCorderConsignee;

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
}
