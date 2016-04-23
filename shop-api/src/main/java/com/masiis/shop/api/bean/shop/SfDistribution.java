package com.masiis.shop.api.bean.shop;

import com.masiis.shop.dao.beans.order.SfDistributionPerson;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wangbingjian on 2016/4/13.
 */
public class SfDistribution {

    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 用户平台昵称，不同于微信昵称
     */
    private String wxNkName;
    /**
     * 订单商品子表Id
     */
    private Long itemId;
    /**
     * 商品skuId
     */
    private Integer skuId;
    /**
     * 商品skuName
     */
    private String skuName;
    /**
     * 用户分销级别
     */
    private Integer level;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 总数
     */
    private Integer count;
    /**
     * 参与总人数
     */
    private Integer sumLevel;
    /**
     * 分润总金额
     */
    private BigDecimal totalAmount;
    /**
     * 分润人列表
     */
    List<SfDistributionPerson> sfDistributionPersons;

    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }
    public String getWxNkName() {
        return wxNkName;
    }
    public void setWxNkName(String wxNkName) {
        this.wxNkName = wxNkName == null ? null : wxNkName.trim();
    }
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public String getSkuName() {
        return skuName;
    }
    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSumLevel() {
        return sumLevel;
    }

    public void setSumLevel(Integer sumLevel) {
        this.sumLevel = sumLevel;
    }

    public List<SfDistributionPerson> getSfDistributionPersons() {
        return sfDistributionPersons;
    }

    public void setSfDistributionPersons(List<SfDistributionPerson> sfDistributionPersons) {
        this.sfDistributionPersons = sfDistributionPersons;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
