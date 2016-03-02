/*
 * PfCorderFreight.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * ƽ̨���������˵���
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfCorderFreight {

    private Long id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ����id
     */
    private Long pfCorderId;
    /**
     * ������id
     */
    private Integer shipManId;
    /**
     * ����������
     */
    private String shipManName;
    /**
     * �˵���
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
    public Long getPfCorderId() {
        return pfCorderId;
    }
    public void setPfCorderId(Long pfCorderId) {
        this.pfCorderId = pfCorderId;
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
}