/*
 * PfUserCertificate.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * ƽ̨�û���Ȩ���
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfUserCertificate {

    private Long id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ��Ȩ�����
     */
    private String code;
    /**
     * sku����id
     */
    private Integer pfUserSkuId;
    /**
     * �û�id
     */
    private Long userId;
    /**
     * spu����id
     */
    private Integer spuId;
    /**
     * sku����id
     */
    private Integer skuId;
    /**
     * ���֤����
     */
    private String idCard;
    /**
     * �ֻ���
     */
    private String mobile;
    /**
     * ΢�ź�
     */
    private String wxId;
    /**
     * ��ʼʱ��
     */
    private Date beginTime;
    /**
     * ����ʱ��
     */
    private Date endTime;
    /**
     * ����ȼ�
     */
    private Integer agentLevelId;
    /**
     * ��Ȩ��ͼƬ��ַ
     */
    private String imgUrl;
    /**
     * ״̬(0δ���1�����)
     */
    private Integer status;
    /**
     * ��ע
     */
    private String remark;

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
        this.code = code == null ? null : code.trim();
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
        this.idCard = idCard == null ? null : idCard.trim();
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }
    public String getWxId() {
        return wxId;
    }
    public void setWxId(String wxId) {
        this.wxId = wxId == null ? null : wxId.trim();
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
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }
}