package com.masiis.shop.dao.mallBeans;

import com.masiis.shop.dao.po.PfBorderConsignee;
import com.masiis.shop.dao.po.PfBorderItem;
import com.masiis.shop.dao.po.SfOrderConsignee;
import com.masiis.shop.dao.po.SfOrderItem;

import java.util.List;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

public class BaseOrder {
    private String imgUrl;//图片地址
    private String orderSkuStatus;
    /**
     * 数量
     */
    private Integer totalQuantity = 0;
    private String pidUserName;
    private String orderMoney;
    /**
     * 订单商品
     */
    private List<SfOrderItem> sfOrderItems;
    private SfOrderConsignee sfOrderConsignee;

    public void setOrderSkuStatus(String orderSkuStatus) {
        this.orderSkuStatus = orderSkuStatus;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderSkuStatus() {
        return orderSkuStatus;
    }

    public SfOrderConsignee getSfOrderConsignee() {
        return sfOrderConsignee;
    }

    public void setSfOrderConsignee(SfOrderConsignee sfOrderConsignee) {
        this.sfOrderConsignee = sfOrderConsignee;
    }

    public void setPidUserName(String pidUserName) {
        this.pidUserName = pidUserName;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setSfOrderItems(List<SfOrderItem> sfOrderItems) {
        this.sfOrderItems = sfOrderItems;
    }

    public List<SfOrderItem> getSfOrderItems() {
        return sfOrderItems;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getPidUserName() {
        return pidUserName;
    }

}
