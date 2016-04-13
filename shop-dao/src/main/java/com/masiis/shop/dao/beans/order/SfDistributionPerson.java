package com.masiis.shop.dao.beans.order;

import java.math.BigDecimal;

/**
 * 小铺订单分润人
 * Created by wangbingjian on 2016/4/13.
 */
public class SfDistributionPerson {
    /**
     * 分润级别
     */
    private Integer sort;
    /**
     * 分润人昵称
     */
    private String wxNkName;
    /**
     * 分润金额
     */
    private BigDecimal amount;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getWxNkName() {
        return wxNkName;
    }

    public void setWxNkName(String wxNkName) {
        this.wxNkName = wxNkName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
