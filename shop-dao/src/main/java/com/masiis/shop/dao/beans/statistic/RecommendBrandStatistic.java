package com.masiis.shop.dao.beans.statistic;

import java.math.BigDecimal;

/**
 * BrandStatistic
 *
 * @author ZhaoLiang
 * @date 2016/8/25
 */
public class RecommendBrandStatistic {
    /**
     * 总人数(自然人)
     */
    private Integer userNum;
    /**
     * 总订单数
     */
    private Integer orderNum;
    /**
     * 总销售额
     */
    private BigDecimal sellAmount;

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(BigDecimal sellAmount) {
        this.sellAmount = sellAmount;
    }

    @Override
    public String toString() {
        return "RecommendBrandStatistic{" +
                "userNum=" + userNum +
                ", orderNum=" + orderNum +
                ", sellAmount=" + sellAmount +
                '}';
    }
}
