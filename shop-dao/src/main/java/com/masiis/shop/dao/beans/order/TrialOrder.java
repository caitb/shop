package com.masiis.shop.dao.beans.order;

import com.masiis.shop.dao.po.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

public class TrialOrder {
    private String consigneeName;//收货人
    private String UserName;//购买人
    private String orderName;//商品名
    private Integer orderNum;//商品数量

    private PfCorderPayment pfCorderPayment;//支付订单
    private PfCorder pfCorder;//名单信息
    private PfCorderConsignee pfCorderConsignee;//收货人
    private PfCorderOperationLog pfCorderOperationLog;//订单操作

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderName() {
        return orderName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setPfCorderPayment(PfCorderPayment pfCorderPayment) {
        this.pfCorderPayment = pfCorderPayment;
    }

    public PfCorderPayment getPfCorderPayment() {
        return pfCorderPayment;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setPfCorder(PfCorder pfCorder) {
        this.pfCorder = pfCorder;
    }

    public void setPfCorderConsignee(PfCorderConsignee pfCorderConsignee) {
        this.pfCorderConsignee = pfCorderConsignee;
    }

    public void setPfCorderOperationLog(PfCorderOperationLog pfCorderOperationLog) {
        this.pfCorderOperationLog = pfCorderOperationLog;
    }

    public PfCorder getPfCorder() {
        return pfCorder;
    }

    public PfCorderConsignee getPfCorderConsignee() {
        return pfCorderConsignee;
    }

    public PfCorderOperationLog getPfCorderOperationLog() {
        return pfCorderOperationLog;
    }
}
