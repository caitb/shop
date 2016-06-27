package com.masiis.shop.dao.beans.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合伙订单数据载体
 * Created by cai_tb on 16/6/24.
 */
public class BOrder {

    private Long        id;
    private String      orderCode;
    private Integer     orderStatus;
    private Integer     orderType;
    private Integer     sendType;
    private Date        createTime;
    private BigDecimal  receivableAmount;
    private BigDecimal  orderAmount;
    private BigDecimal  payAmount;
    private BigDecimal  bailAmount;
    private Date        payTime;

    private List<BItem> bItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getBailAmount() {
        return bailAmount;
    }

    public void setBailAmount(BigDecimal bailAmount) {
        this.bailAmount = bailAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public List<BItem> getbItems() {
        return bItems;
    }

    public void setbItems(List<BItem> bItems) {
        this.bItems = bItems;
    }

    @Override
    public String toString() {
        return "BOrder{" +
                "id=" + id +
                ", orderCode='" + orderCode + '\'' +
                ", orderStatus=" + orderStatus +
                ", orderType=" + orderType +
                ", sendType=" + sendType +
                ", createTime=" + createTime +
                ", receivableAmount=" + receivableAmount +
                ", orderAmount=" + orderAmount +
                ", payAmount=" + payAmount +
                ", bailAmount=" + bailAmount +
                ", payTime=" + payTime +
                ", bItems=" + bItems +
                '}';
    }
}
