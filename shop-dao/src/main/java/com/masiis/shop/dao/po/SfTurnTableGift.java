/*
 * SfTurnTableGift.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfTurnTableGift {

    /**
     * 转盘奖品表主键id
     */
    private Integer id;
    /**
     * 转盘id
     */
    private Integer turnTableId;
    /**
     * 奖品的总数量
     */
    private Integer toatalQuantity;
    /**
     * 已中奖的数量
     */
    private Integer giftedQuantity;
    /**
     * 序号(奖品处于转盘的位置)
     */
    private Integer serialNumber;
    /**
     * 中奖的概率
     */
    private Integer probability;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getTurnTableId() {
        return turnTableId;
    }
    public void setTurnTableId(Integer turnTableId) {
        this.turnTableId = turnTableId;
    }
    public Integer getToatalQuantity() {
        return toatalQuantity;
    }
    public void setToatalQuantity(Integer toatalQuantity) {
        this.toatalQuantity = toatalQuantity;
    }
    public Integer getGiftedQuantity() {
        return giftedQuantity;
    }
    public void setGiftedQuantity(Integer giftedQuantity) {
        this.giftedQuantity = giftedQuantity;
    }
    public Integer getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }
    public Integer getProbability() {
        return probability;
    }
    public void setProbability(Integer probability) {
        this.probability = probability;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}