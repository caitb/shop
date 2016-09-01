package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderItem;
import com.masiis.shop.dao.po.PfSupplierBank;

/**
 * Created by hw on 2016/8/6.
 */
public class OffinePaymentRes extends BaseRes {

    private PfBorder pfBorder;
    private PfSupplierBank supplierBank;
    private String latestTime;
    private PfBorderItem orderItem;

    public PfBorder getPfBorder() {
        return pfBorder;
    }

    public void setPfBorder(PfBorder pfBorder) {
        this.pfBorder = pfBorder;
    }

    public PfSupplierBank getSupplierBank() {
        return supplierBank;
    }

    public void setSupplierBank(PfSupplierBank supplierBank) {
        this.supplierBank = supplierBank;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public PfBorderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(PfBorderItem orderItem) {
        this.orderItem = orderItem;
    }
}
