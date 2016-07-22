/*
 * PbStorageBill.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-19 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PbStorageBill {

    /**
     * 库存变更单id
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createMan;
    /**
     * 变更单编码
     */
    private String code;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 单据创建原因
     */
    private String billReason;
    /**
     * 商品总数量
     */
    private Integer productQuantity;
    /**
     * 状态(0未处理1已审核2已处理3已取消)
     */
    private Integer status;
    /**
     * 类型(0出库1入库)
     */
    private Integer type;
    /**
     * 审核人
     */
    private Long auditMan;
    /**
     * 审核时间
     */
    private Date auditTime;
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
    public Long getCreateMan() {
        return createMan;
    }
    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBillReason() {
        return billReason;
    }

    public void setBillReason(String billReason) {
        this.billReason = billReason;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Long getAuditMan() {
        return auditMan;
    }
    public void setAuditMan(Long auditMan) {
        this.auditMan = auditMan;
    }
    public Date getAuditTime() {
        return auditTime;
    }
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "PbStorageBill{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", createMan=" + createMan +
                ", code='" + code + '\'' +
                ", userId=" + userId +
                ", billReason='" + billReason + '\'' +
                ", productQuantity=" + productQuantity +
                ", status=" + status +
                ", type=" + type +
                ", auditMan=" + auditMan +
                ", auditTime=" + auditTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}