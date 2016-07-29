/*
 * SfTurnTableGift.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.po;

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
     * 奖品id
     */
    private Integer giftId;
    /**
     * 奖品奖励数量
     */
    private Integer quantity;
    /**
     * 奖品的总数量
     */
    private Integer toatalQuantity;
    /**
     * 已中奖的数量
     */
    private Integer giftedQuantity;
    /**
     * 序号(奖品处于转盘的位置,小到大)
     */
    private Integer sort;
    /**
     * 中奖的概率
     */
    private Integer probability;
    /**
     * 备注
     */
    private String remark;

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
    public Integer getGiftId() {
        return giftId;
    }
    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public Integer getProbability() {
        return probability;
    }
    public void setProbability(Integer probability) {
        this.probability = probability;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}