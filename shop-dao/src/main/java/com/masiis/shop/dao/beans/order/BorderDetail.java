package com.masiis.shop.dao.beans.order;

import com.masiis.shop.dao.po.*;

import java.util.List;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

public class BorderDetail {
    private String buyerName;
    private PfBorder pfBorder;
    private List<PfBorderItem> pfBorderItems;
    private List<PfBorderFreight> pfBorderFreights;//快递公司信息
    private PfBorderConsignee pfBorderConsignee;//收货人
    private List<PfBorderPayment> pfBorderPayments;//支付方式

    public void setPfBorderPayments(List<PfBorderPayment> pfBorderPayments) {
        this.pfBorderPayments = pfBorderPayments;
    }

    public List<PfBorderPayment> getPfBorderPayments() {
        return pfBorderPayments;
    }

    public void setBuyerName(String buyerName) {this.buyerName = buyerName;}

    public String getBuyerName() {return buyerName;}

    public List<PfBorderItem> getPfBorderItems() {
        return pfBorderItems;
    }

    public List<PfBorderFreight> getPfBorderFreights() {
        return pfBorderFreights;
    }

    public PfBorderConsignee getPfBorderConsignee() {
        return pfBorderConsignee;
    }

    public void setPfBorder(PfBorder pfBorder) {
        this.pfBorder = pfBorder;
    }

    public PfBorder getPfBorder() {
        return pfBorder;
    }

    public void setPfBorderConsignee(PfBorderConsignee pfBorderConsignee) {
        this.pfBorderConsignee = pfBorderConsignee;
    }

    public void setPfBorderFreights(List<PfBorderFreight> pfBorderFreights) {
        this.pfBorderFreights = pfBorderFreights;
    }

    public void setPfBorderItems(List<PfBorderItem> pfBorderItems) {
        this.pfBorderItems = pfBorderItems;
    }
}
