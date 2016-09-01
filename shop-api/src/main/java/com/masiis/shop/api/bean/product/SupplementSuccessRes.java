package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

import java.math.BigDecimal;

/**
 * @Date 2016/8/19
 * @Author lzh
 */
public class SupplementSuccessRes extends BaseBusinessRes {
    private Integer increaseStock;
    private BigDecimal payAmount;

    public Integer getIncreaseStock() {
        return increaseStock;
    }

    public void setIncreaseStock(Integer increaseStock) {
        this.increaseStock = increaseStock;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
}
