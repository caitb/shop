package com.masiis.shop.dao.beans.statistic;

import java.math.BigDecimal;

/**
 * Created by admin on 2016/8/29.
 */
public class StatisticOrder {

    //总量
    private Integer orderTotal;

    //新增量

    private Integer newOrderTotal;

    //总金额
    private BigDecimal sale;

    //新增金额
    private BigDecimal newSale;


    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getNewOrderTotal() {
        return newOrderTotal;
    }

    public void setNewOrderTotal(Integer newOrderTotal) {
        this.newOrderTotal = newOrderTotal;
    }

    public BigDecimal getNewSale() {
        return newSale;
    }

    public void setNewSale(BigDecimal newSale) {
        this.newSale = newSale;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "StatisticOrder{" +
                "orderTotal=" + orderTotal +
                ", newOrderTotal=" + newOrderTotal +
                ", sale=" + sale +
                ", newSale=" + newSale +
                '}';
    }
}