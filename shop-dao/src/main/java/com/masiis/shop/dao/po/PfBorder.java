/*
 * PfBorder.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-15 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PfBorder {

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
     * 上一级用户id，可空
     */
    private Long userPid;
    /**
     * 买家留言
     */
    private String userMessage;
    /**
     * 平台供应商id(暂时不用，保留)
     */
    private Integer supplierId;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人
     */
    private Long modifyMan;
    /**
     * 应收费用
     */
    private BigDecimal receivableAmount;
    /**
     * 订单费用
     */
    private BigDecimal orderAmount;
    /**
     * 商品总费用
     */
    private BigDecimal productAmount;
    /**
     * 运费
     */
    private BigDecimal shipAmount;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
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
     * 订单商品状态名称
     */
    private String orderSkuStatus;
    private String imgUrl;//图片地址
    /**
     * 数量
     */
    private Integer totalQuantity =0;
    /**
     * 订单商品
     */
    private List<PfBorderItem> pfBorderItems;

    public void setTotalQuantity(Integer totalQuantity) {this.totalQuantity = totalQuantity;}
    public Integer getTotalQuantity() {return totalQuantity;}
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setOrderSkuStatus(String orderSkuStatus) {this.orderSkuStatus = orderSkuStatus;}
    public String getOrderSkuStatus() {return orderSkuStatus;}
    public List<PfBorderItem> getPfBorderItems() {return pfBorderItems;}
    public void setPfBorderItems(List<PfBorderItem> pfBorderItems) {this.pfBorderItems = pfBorderItems;}
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

    public Long getUserPid() {
        return userPid;
    }

    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage == null ? null : userMessage.trim();
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
}