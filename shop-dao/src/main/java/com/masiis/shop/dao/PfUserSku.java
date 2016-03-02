/*
 * PfUserSku.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * 平台用户代理商品关系表
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfUserSku {

    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 授权书编码
     */
    private String code;
    /**
     * 父级id(0的时候平台审核)
     */
    private Integer pid;
    /**
     * sku主键id
     */
    private Integer skuId;
    /**
     * 代理等级id
     */
    private Integer agentLevelId;
    /**
     * 是否付款(0未付款1已付款)
     */
    private Integer isPay;
    /**
     * 授权书生成标志位(0未生成1已生成)
     */
    private Integer isCertificate;
    /**
     * 代理订单id
     */
    private Long pfCorderId;
    /**
     * 是否授权0未授权1已授权
     */
    private Integer isAuthorized;
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