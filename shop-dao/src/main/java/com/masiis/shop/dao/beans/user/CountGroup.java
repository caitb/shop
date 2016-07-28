
package com.masiis.shop.dao.beans.user;

import java.math.BigDecimal;

/**
 * 团队统计
 * @author muchaofeng
 * @date 2016/6/7 10:51
 */

public class CountGroup {
    /**
     * 团队人数(包括自己)
     */
    private Integer count;
    /**
     * 团队订单数量(包括自己)
     */
    private Integer orderNum;
    /**
     * 团队总收入(包括自己)
     */
    private BigDecimal groupMoney;
    /**
     * 推荐团队人数(不包括自己)
     */
    private Integer r_count;
    /**
     * 推荐团队订单数量(不包括自己)
     */
    private Integer r_orderNum;
    /**
     * 推荐团队总收入(不包括自己)
     */
    private BigDecimal r_groupMoney;

    public BigDecimal getR_groupMoney() {
        return r_groupMoney;
    }

    public void setR_groupMoney(BigDecimal r_groupMoney) {
        this.r_groupMoney = r_groupMoney;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getGroupMoney() {
        return groupMoney;
    }

    public void setGroupMoney(BigDecimal groupMoney) {
        this.groupMoney = groupMoney;
    }

    public Integer getR_count() {
        return r_count;
    }

    public void setR_count(Integer r_count) {
        this.r_count = r_count;
    }

    public Integer getR_orderNum() {
        return r_orderNum;
    }

    public void setR_orderNum(Integer r_orderNum) {
        this.r_orderNum = r_orderNum;
    }
}
