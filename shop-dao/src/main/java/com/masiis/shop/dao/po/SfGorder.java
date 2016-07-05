/*
 * SfGorder.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class SfGorder {

    /**
     * 奖励订单id
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createMan;
    /**
     * 活动id
     */
    private Integer promoId;
    /**
     * 活动规则id
     */
    private Integer promoRuleId;
    /**
     * 下单人
     */
    private Long userId;
    /**
     * 奖励订单编码
     */
    private String gorderCode;
    /**
     * 奖励订单类型(0粉丝奖励)
     */
    private Integer gorderType;
    /**
     * 奖励订单状态
     */
    private Integer gorderStatus;
    /**
     * 订单金额
     */
    private BigDecimal gorderAmount;
    /**
     * 奖品金额
     */
    private BigDecimal productAmount;
    /**
     * 运费
     */
    private BigDecimal shipAmount;
    /**
     * 应收金额
     */
    private BigDecimal receivableAmount;
    /**
     * 已付金额
     */
    private BigDecimal payAmount;
    /**
     * 付款时间
     */
    private Date payTime;
    /**
     * 配送商id
     */
    private Integer shipManId;
    /**
     * 配送商名称
     */
    private String shipManName;
    /**
     * 配送类型
     */
    private Integer shipType;
    /**
     * 配送时间
     */
    private Date shipTime;
    /**
     * 物流状态
     */
    private Integer shipStatus;
    /**
     * 支付状态(0未支付1已支付)
     */
    private Integer payStatus;
    /**
     * 发货标志位(0未发货1已发货)
     */
    private Integer isShip;
    /**
     * 收货标志位(0未收货1已收货)
     */
    private Integer isReceipt;
    /**
     * 收货时间
     */
    private Date receiptTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人
     */
    private Long modifyMan;
    /**
     * 备注
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
    public Long getCreateMan() {
        return createMan;
    }
    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }
    public Integer getPromoId() {
        return promoId;
    }
    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
    public Integer getPromoRuleId() {
        return promoRuleId;
    }
    public void setPromoRuleId(Integer promoRuleId) {
        this.promoRuleId = promoRuleId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getGorderCode() {
        return gorderCode;
    }
    public void setGorderCode(String gorderCode) {
        this.gorderCode = gorderCode == null ? null : gorderCode.trim();
    }
    public Integer getGorderType() {
        return gorderType;
    }
    public void setGorderType(Integer gorderType) {
        this.gorderType = gorderType;
    }
    public Integer getGorderStatus() {
        return gorderStatus;
    }
    public void setGorderStatus(Integer gorderStatus) {
        this.gorderStatus = gorderStatus;
    }
    public BigDecimal getGorderAmount() {
        return gorderAmount;
    }
    public void setGorderAmount(BigDecimal gorderAmount) {
        this.gorderAmount = gorderAmount;
    }
    public BigDecimal getProductAmount() {
        return productAmount;
    }
    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }
    public BigDecimal getShipAmount() {
        return shipAmount;
    }
    public void setShipAmount(BigDecimal shipAmount) {
        this.shipAmount = shipAmount;
    }
    public BigDecimal getReceivableAmount() {
        return receivableAmount;
    }
    public void setReceivableAmount(BigDecimal receivableAmount) {
        this.receivableAmount = receivableAmount;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
    public Date getPayTime() {
        return payTime;
    }
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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
    public Integer getShipType() {
        return shipType;
    }
    public void setShipType(Integer shipType) {
        this.shipType = shipType;
    }
    public Date getShipTime() {
        return shipTime;
    }
    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }
    public Integer getShipStatus() {
        return shipStatus;
    }
    public void setShipStatus(Integer shipStatus) {
        this.shipStatus = shipStatus;
    }
    public Integer getPayStatus() {
        return payStatus;
    }
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
    public Integer getIsShip() {
        return isShip;
    }
    public void setIsShip(Integer isShip) {
        this.isShip = isShip;
    }
    public Integer getIsReceipt() {
        return isReceipt;
    }
    public void setIsReceipt(Integer isReceipt) {
        this.isReceipt = isReceipt;
    }
    public Date getReceiptTime() {
        return receiptTime;
    }
    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }
    public Date getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Long getModifyMan() {
        return modifyMan;
    }
    public void setModifyMan(Long modifyMan) {
        this.modifyMan = modifyMan;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}