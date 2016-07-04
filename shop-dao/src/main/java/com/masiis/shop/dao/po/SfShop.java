/*
 * SfShop.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-11 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class SfShop {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 小铺名称
     */
    private String name;
    /**
     * 小铺归属人id
     */
    private Long userId;
    /**
     * 小铺状态(0:关店1:开店)
     */
    private Integer status;
    /**
     * 小铺logo
     */
    private String logo;
    /**
     * 小铺说明
     */
    private String explanation;
    /**
     * 呐喊次数
     */
    private Long shoutNum;
    /**
     * 小铺浏览量
     */
    private Long pageviews;
    /**
     * 小铺销售额
     */
    private BigDecimal saleAmount;

    /**
     * 运费类型0：消费者出运费1：代理商出运费
     */
    private Integer shipType;

    /**
     * 代理运费金额, 0为包邮
     */
    private BigDecimal agentShipAmount;

    /**
     * 消费者运费金额，0为包邮
     */
    private BigDecimal shipAmount;
    /**
     * 二维码图片全称(不包括路径)
     */
    private String qrCode;
    /**
     * 备注
     */
    private String remark;
    private Long version;

    /**
     * 店主微信二维码
     */
    private String wxQrCode;
    /**
     * 店主微信二维码描述
     */
    private String wxQrCodeDescription;



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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }
    public String getExplanation() {
        return explanation;
    }
    public void setExplanation(String explanation) {
        this.explanation = explanation == null ? null : explanation.trim();
    }
    public Long getShoutNum() {
        return shoutNum;
    }
    public void setShoutNum(Long shoutNum) {
        this.shoutNum = shoutNum;
    }
    public Long getPageviews() {
        return pageviews;
    }
    public void setPageviews(Long pageviews) {
        this.pageviews = pageviews;
    }
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }
    public BigDecimal getShipAmount() {
        return shipAmount;
    }
    public void setShipAmount(BigDecimal shipAmount) {
        this.shipAmount = shipAmount;
    }
    public String getQrCode() {
        return qrCode;
    }
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getShipType() {
        return shipType;
    }

    public void setShipType(Integer shipType) {
        this.shipType = shipType;
    }

    public BigDecimal getAgentShipAmount() {
        return agentShipAmount;
    }

    public void setAgentShipAmount(BigDecimal agentShipAmount) {
        this.agentShipAmount = agentShipAmount;
    }

    public String getWxQrCode() {
        return wxQrCode;
    }

    public void setWxQrCode(String wxQrCode) {
        this.wxQrCode = wxQrCode;
    }

    public String getWxQrCodeDescription() {
        return wxQrCodeDescription;
    }

    public void setWxQrCodeDescription(String wxQrCodeDescription) {
        this.wxQrCodeDescription = wxQrCodeDescription;
    }
}