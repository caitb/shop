package com.masiis.shop.dao.beans.recommend;

import com.masiis.shop.common.enums.platform.BOrderType;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 推荐获得奖励订单列表po
 * Created by wangbingjian on 2016/7/29.
 */
public class RecommenOrder {

    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 订单号
     */
    private String orderCode;
    /**
     * 订单类型
     */
    private Integer orderType;
    /**
     * 订单类型页面展示
     */
    private String orderTypeView;
    /**
     * 订单数量
     */
    private Integer quantity;
    /**
     * 奖励单价
     */
    private BigDecimal unitPrice;
    /**
     * 奖励单价页面暂时
     */
    private String unitPriceView;
    /**
     * 奖励总价
     */
    private BigDecimal totalPrice;
    /**
     * 奖励总价页面展示
     */
    private String totalPriceView;
    /**
     * 购买人id
     */
    private Long buyUserId;
    /**
     * 购买人姓名
     */
    private String buyUserName;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 发送奖励人id
     */
    private Long sendUserId;
    /**
     * 发送奖励人姓名
     */
    private String sendUserName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 接受奖励人id
     */
    private Long receiveUserId;
    /**
     * 接受奖励人姓名
     */
    private String receiveUserName;

    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
        setOrderTypeView(BOrderType.getByCode(orderType).getDesc());
    }

    public String getOrderTypeView() {
        return orderTypeView;
    }

    public void setOrderTypeView(String orderTypeView) {
        this.orderTypeView = orderTypeView;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        setUnitPriceView(numberFormat.format(unitPrice));
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        setTotalPriceView(numberFormat.format(totalPrice));
    }

    public String getUnitPriceView() {
        return unitPriceView;
    }

    public void setUnitPriceView(String unitPriceView) {
        this.unitPriceView = unitPriceView;
    }

    public String getTotalPriceView() {
        return totalPriceView;
    }

    public void setTotalPriceView(String totalPriceView) {
        this.totalPriceView = totalPriceView;
    }

    public Long getBuyUserId() {
        return buyUserId;
    }

    public void setBuyUserId(Long buyUserId) {
        this.buyUserId = buyUserId;
    }

    public String getBuyUserName() {
        return buyUserName;
    }

    public void setBuyUserName(String buyUserName) {
        this.buyUserName = buyUserName;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }
}
