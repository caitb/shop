/*
 * PfUserCertificate.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-11 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfUserCertificate {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 授权书编码
     */
    private String code;
    /**
     * sku主键id
     */
    private Integer pfUserSkuId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * spu主键id
     */
    private Integer spuId;
    /**
     * sku主键id
     */
    private Integer skuId;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 微信号
     */
    private String wxId;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 代理等级
     */
    private Integer agentLevelId;
    /**
     * 授权书图片地址
     */
    private String imgUrl;
    /**
     * 状态(0未审核1已审核)
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

    private ComSku comSku;

    private String tjDate;

    private String sjName;

    private String reason;//拒绝理由
    /**
     * 海报
     */
    private String poster;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPfUserSkuId() {
        return pfUserSkuId;
    }

    public void setPfUserSkuId(Integer pfUserSkuId) {
        this.pfUserSkuId = pfUserSkuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getAgentLevelId() {
        return agentLevelId;
    }

    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ComSku getComSku() {
        return comSku;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    public String getTjDate() {
        return tjDate;
    }

    public void setTjDate(String tjDate) {
        this.tjDate = tjDate;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}