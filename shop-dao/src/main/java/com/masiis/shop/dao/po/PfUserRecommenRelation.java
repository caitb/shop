/*
 * PfUserRecommenRelation.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfUserRecommenRelation {

    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
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
     * 代理订单id
     */
    private Long pfBorderId;
    /**
     * 树结构编码(方便查询)
     */
    private String treeCode;
    /**
     * 树结构等级(方便查询)
     */
    private Integer treeLevel;
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
    public Long getPfBorderId() {
        return pfBorderId;
    }
    public void setPfBorderId(Long pfBorderId) {
        this.pfBorderId = pfBorderId;
    }
    public String getTreeCode() {
        return treeCode;
    }
    public void setTreeCode(String treeCode) {
        this.treeCode = treeCode == null ? null : treeCode.trim();
    }
    public Integer getTreeLevel() {
        return treeLevel;
    }
    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}