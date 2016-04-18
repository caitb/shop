/*
 * PfUserSku.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-31 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

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
     * 用户id
     */
    private Long userId;
    /**
     * 上级用户id
     */
    private Long userPid;
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
    private Long pfBorderId;
    /**
     * 保证金
     */
    private BigDecimal bail;
    /**
     * 所有下级代理人数(每日统计)
     */
    private Long agentNum;
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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserPid() {
        return userPid;
    }
    public void setUserPid(Long userPid) {
        this.userPid = userPid;
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
    public Long getPfBorderId() {
        return pfBorderId;
    }
    public void setPfBorderId(Long pfBorderId) {
        this.pfBorderId = pfBorderId;
    }
    public BigDecimal getBail() {
        return bail;
    }
    public void setBail(BigDecimal bail) {
        this.bail = bail;
    }
    public Long getAgentNum() {
        return agentNum;
    }
    public void setAgentNum(Long agentNum) {
        this.agentNum = agentNum;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}