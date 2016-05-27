package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.beans.order.BorderDetail;
import com.masiis.shop.dao.mallBeans.OrderMallDetail;
import com.masiis.shop.dao.po.ComShipMan;
import com.masiis.shop.dao.po.PfBorder;

import java.util.List;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
public class BorderDetailRes extends BasePagingRes {
    private BorderDetail borderDetail;
    private OrderMallDetail orderMallDetail;
    private String stringBuffer;
    private Integer stockNum;
    private List<ComShipMan> comShipMans;

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public void setOrderMallDetail(OrderMallDetail orderMallDetail) {
        this.orderMallDetail = orderMallDetail;
    }

    public OrderMallDetail getOrderMallDetail() {
        return orderMallDetail;
    }

    public Integer getStockNum() {return stockNum;}
    public void setStringBuffer(String stringBuffer) {this.stringBuffer = stringBuffer;}

    public String getStringBuffer() {return stringBuffer;}

    public void setComShipMans(List<ComShipMan> comShipMans) {
        this.comShipMans = comShipMans;
    }

    public List<ComShipMan> getComShipMans() {
        return comShipMans;
    }

    public void setBorderDetail(BorderDetail borderDetail) {
        this.borderDetail = borderDetail;
    }

    public BorderDetail getBorderDetail() {
        return borderDetail;
    }
}
