/*
 * PfBorderFreight.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

/**
 * 平台代理订单运单表
 * 
 * @author masiis
 * @version 1.0 2016-03-03
 */
public class PfBorderFreight {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 订单id
     */
    private Long pfBorderId;
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

    public Long getPfBorderId() {
        return pfBorderId;
    }

    public void setPfBorderId(Long pfBorderId) {
        this.pfBorderId = pfBorderId;
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
        this.shipManName = shipManName;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }
}