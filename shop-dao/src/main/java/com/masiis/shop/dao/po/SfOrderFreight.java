/*
 * SfOrderFreight.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfOrderFreight {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 订单id
     */
    private Long sfOrderId;
    /**
     * 配送商id
     */
    private Integer shipManId;
    /**
     * 配送商名称
     */
    private String shipManName;
    /**
     * 运单号
     */
    private String freight;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getSfOrderId() {
        return sfOrderId;
    }
    public void setSfOrderId(Long sfOrderId) {
        this.sfOrderId = sfOrderId;
    }
    public Integer getShipManId() {
        return shipManId;
    }
    public void setShipManId(Integer shipManId) {
        this.shipManId = shipManId;
    }
    public String getShipManName() {
        return shipManName;
    }
    public void setShipManName(String shipManName) {
        this.shipManName = shipManName == null ? null : shipManName.trim();
    }
    public String getFreight() {
        return freight;
    }
    public void setFreight(String freight) {
        this.freight = freight == null ? null : freight.trim();
    }

    @Override
    public String toString() {
        return "SfOrderFreight{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", sfOrderId=" + sfOrderId +
                ", shipManId=" + shipManId +
                ", shipManName='" + shipManName + '\'' +
                ", freight='" + freight + '\'' +
                '}';
    }
}