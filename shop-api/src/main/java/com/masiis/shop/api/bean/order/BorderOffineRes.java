package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderItem;
import com.masiis.shop.dao.po.PfSupplierBank;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hzz on 2016/8/19.
 */
public class BorderOffineRes extends BaseBusinessRes {

    private Boolean isOffinSuccess;
    private String latestTime;
    private BigDecimal totalPrice;
    private String orderCode;
    private String skuName;
    private PfSupplierBank pfSupplierBank;


    public Boolean getIsOffinSuccess() {
        return isOffinSuccess;
    }

    public void setIsOffinSuccess(Boolean offinSuccess) {
        isOffinSuccess = offinSuccess;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public PfSupplierBank getPfSupplierBank() {
        return pfSupplierBank;
    }

    public void setPfSupplierBank(PfSupplierBank pfSupplierBank) {
        this.pfSupplierBank = pfSupplierBank;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
}
