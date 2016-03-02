/*
 * PfCorder.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ƽ̨����������
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfCorder {

    private Long id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ������
     */
    private Long createMan;
    /**
     * ��������
     */
    private String orderCode;
    /**
     * ��������(0����)
     */
    private Integer orderType;
    /**
     * �û�id
     */
    private Long userId;
    /**
     * ��һ���û�id���ɿ�
     */
    private Long userPid;
    /**
     * �������
     */
    private String userMassage;
    /**
     * ƽ̨��Ӧ��id(��ʱ���ã�����)
     */
    private Integer supplierId;
    /**
     * �޸�ʱ��
     */
    private Date modifyTime;
    /**
     * �޸���
     */
    private Long modifyMan;
    /**
     * Ӧ�շ���
     */
    private BigDecimal receivableAmount;
    /**
     * ��������
     */
    private BigDecimal orderAmount;
    /**
     * ��Ʒ�ܷ���
     */
    private BigDecimal productAmount;
    /**
     * �˷�
     */
    private BigDecimal shipAmount;
    /**
     * ֧�����
     */
    private BigDecimal payAmount;
    /**
     * ֧��ʱ��
     */
    private Date payTime;
    /**
     * ������id
     */
    private Integer shipManId;
    /**
     * ����������
     */
    private String shipManName;
    /**
     * ����ʱ��
     */
    private Date deliveryTime;
    /**
     * ���ͱ�ע��ֻ�������ͻ��������ͻ�
     */
    private String shipRemark;
    /**
     * ����������
     */
    private Long replaceOrderId;
    /**
     * ����״̬
     */
    private Integer orderStatus;
    /**
     * ����״̬
     */
    private Integer shipStatus;
    /**
     * ֧��״̬
     */
    private Integer payStatus;
    /**
     * ������־λ(0δ����1�ѷ���)
     */
    private Integer isShip;
    /**
     * ������־λ(0δ����1�ѻ���)
     */
    private Integer isReplace;
    /**
     * �ջ���־λ(0δ�ջ�1���ջ�)
     */
    private Integer isReceipt;
    /**
     * ȷ���ջ�ʱ��
     */
    private Date receiptTime;
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
    public Integer getOrderType() {
        return orderType;
    }
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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
    public String getUserMassage() {
        return userMassage;
    }
    public void setUserMassage(String userMassage) {
        this.userMassage = userMassage == null ? null : userMassage.trim();
    }
    public Integer getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
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
    public BigDecimal getReceivableAmount() {
        return receivableAmount;
    }
    public void setReceivableAmount(BigDecimal receivableAmount) {
        this.receivableAmount = receivableAmount;
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
    public Date getDeliveryTime() {
        return deliveryTime;
    }
    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    public String getShipRemark() {
        return shipRemark;
    }
    public void setShipRemark(String shipRemark) {
        this.shipRemark = shipRemark == null ? null : shipRemark.trim();
    }
    public Long getReplaceOrderId() {
        return replaceOrderId;
    }
    public void setReplaceOrderId(Long replaceOrderId) {
        this.replaceOrderId = replaceOrderId;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}