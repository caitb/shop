package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.order.BOrderConfirm;
import com.masiis.shop.dao.po.PfBorder;

import java.math.BigDecimal;

/**
 * Created by JingHao on 2016/5/24 0024.
 */
public class AddProRes extends BaseRes{

    private Boolean isQueuing;//是否排单

    private Integer count;//排单数量

    private BOrderConfirm bOrderConfirm;//订单确认信息

    private PfBorder pfBorder;//订单属性

    private Long pfBorderId;//订单号

    private BigDecimal unitPrice; //合伙人价

    public Boolean getIsQueuing() {
        return isQueuing;
    }

    public void setIsQueuing(Boolean isQueuing) {
        this.isQueuing = isQueuing;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BOrderConfirm getbOrderConfirm() {
        return bOrderConfirm;
    }

    public void setbOrderConfirm(BOrderConfirm bOrderConfirm) {
        this.bOrderConfirm = bOrderConfirm;
    }

    public PfBorder getPfBorder() {
        return pfBorder;
    }

    public void setPfBorder(PfBorder pfBorder) {
        this.pfBorder = pfBorder;
    }

    public Long getPfBorderId() {
        return pfBorderId;
    }

    public void setPfBorderId(Long pfBorderId) {
        this.pfBorderId = pfBorderId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
