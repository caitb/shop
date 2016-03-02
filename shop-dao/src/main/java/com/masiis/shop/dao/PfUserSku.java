/*
 * PfUserSku.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * ƽ̨�û�������Ʒ��ϵ��
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfUserSku {

    private Integer id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ��Ȩ�����
     */
    private String code;
    /**
     * ����id(0��ʱ��ƽ̨���)
     */
    private Integer pid;
    /**
     * sku����id
     */
    private Integer skuId;
    /**
     * ����ȼ�id
     */
    private Integer agentLevelId;
    /**
     * �Ƿ񸶿�(0δ����1�Ѹ���)
     */
    private Integer isPay;
    /**
     * ��Ȩ�����ɱ�־λ(0δ����1������)
     */
    private Integer isCertificate;
    /**
     * ������id
     */
    private Long pfCorderId;
    /**
     * �Ƿ���Ȩ0δ��Ȩ1����Ȩ
     */
    private Integer isAuthorized;
    /**
     * ��ע
     */
    private String remark;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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
    public Integer getPid() {
        return pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public Integer getAgentLevelId() {
        return agentLevelId;
    }
    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }
    public Integer getIsPay() {
        return isPay;
    }
    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }
    public Integer getIsCertificate() {
        return isCertificate;
    }
    public void setIsCertificate(Integer isCertificate) {
        this.isCertificate = isCertificate;
    }
    public Long getPfCorderId() {
        return pfCorderId;
    }
    public void setPfCorderId(Long pfCorderId) {
        this.pfCorderId = pfCorderId;
    }
    public Integer getIsAuthorized() {
        return isAuthorized;
    }
    public void setIsAuthorized(Integer isAuthorized) {
        this.isAuthorized = isAuthorized;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}