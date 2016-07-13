/*
 * SfOrder.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.po;

import com.masiis.shop.dao.mallBeans.BaseOrder;

import java.math.BigDecimal;
import java.util.Date;

public class SfOrder extends BaseOrder {

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
     * 订单编码
     */
    private String orderCode;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 小铺id
     */
    private Long shopId;
    /**
     * 小铺归属人id
     */
    private Long shopUserId;
    /**
     * 买家留言
     */
    private String userMessage;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人
     */
    private Long modifyMan;
    /**
     * 订单费用
     */
    private BigDecimal orderAmount;
    /**
     * 商品总费用
     */
    private BigDecimal productAmount;
    /**
     * 消费者运费金额，0为包邮
     */
    private BigDecimal shipAmount;
    /**
     * 代理运费金额，0为包邮
     */
    private BigDecimal agentShipAmount;
    /**
     * 应收费用
     */
    private BigDecimal receivableAmount;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 分润总金额
     */
    private BigDecimal distributionAmount;
    /**
     * 支付时间
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
     * 发货时间
     */
    private Date shipTime;
    /**
     * 配送备注，只工作日送货、都可送货
     */
    private String shipRemark;
    /**
     * 拿货方式(0未选择1平台代发2自己发货)
     */
    private Integer sendType;
    /**
     * 订单类型(0代理1补货2拿货)
     */
    private Integer orderType;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 物流状态
     */
    private Integer shipStatus;
    /**
     * 支付状态
     */
    private Integer payStatus;
    /**
     * 是否结算(0未结算1已结算)
     */
    private Integer isCounting;
    /**
     * 发货标志位(0未发货1已发货)
     */
    private Integer isShip;
    /**
     * 换货标志位(0未换货1已换货)
     */
    private Integer isReplace;
    /**
     * 收货标志位(0未收货1已收货)
     */
    private Integer isReceipt;
    /**
     * 确认收货时间
     */
    private Date receiptTime;
    /**
     * 换货订单号
     */
    private Long replaceOrderId;
    /**
     * 备注
     */
    private String remark;

    /**
     * 发货人
     */
    private Long sendMan;

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
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getShopId() {
        return shopId;
    }
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
    public Long getShopUserId() {
        return shopUserId;
    }
    public void setShopUserId(Long shopUserId) {
        this.shopUserId = shopUserId;
    }
    public String getUserMessage() {
        return userMessage;
    }
    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage == null ? null : userMessage.trim();
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
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
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
    public BigDecimal getDistributionAmount() {
        return distributionAmount;
    }
    public void setDistributionAmount(BigDecimal distributionAmount) {
        this.distributionAmount = distributionAmount;
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
    public String getShipRemark() {
        return shipRemark;
    }
    public void setShipRemark(String shipRemark) {
        this.shipRemark = shipRemark == null ? null : shipRemark.trim();
    }
    public Integer getSendType() {
        return sendType;
    }
    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }
    public Integer getOrderType() {
        return orderType;
    }
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
    public Integer getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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
    public Integer getIsCounting() {
        return isCounting;
    }
    public void setIsCounting(Integer isCounting) {
        this.isCounting = isCounting;
    }
    public Integer getIsShip() {
        return isShip;
    }
    public void setIsShip(Integer isShip) {
        this.isShip = isShip;
    }
    public Integer getIsReplace() {
        return isReplace;
    }
    public void setIsReplace(Integer isReplace) {
        this.isReplace = isReplace;
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
    public Long getReplaceOrderId() {
        return replaceOrderId;
    }
    public void setReplaceOrderId(Long replaceOrderId) {
        this.replaceOrderId = replaceOrderId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getAgentShipAmount() {
        return agentShipAmount;
    }

    public void setAgentShipAmount(BigDecimal agentShipAmount) {
        this.agentShipAmount = agentShipAmount;
    }

    public Long getSendMan() {
        return sendMan;
    }

    public void setSendMan(Long sendMan) {
        this.sendMan = sendMan;
    }
}