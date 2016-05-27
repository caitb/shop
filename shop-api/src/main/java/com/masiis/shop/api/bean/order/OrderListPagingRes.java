package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.po.ComShipMan;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.SfOrder;

import java.util.List;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
public class OrderListPagingRes extends BasePagingRes {
    private Integer waitShipNum;
    private Integer waitPayNum;
    private Integer shippedNum;
    private Integer completeNum;
    private Integer isMPS;
    private Integer mpsNum;
    private String index;
    private Integer sfOrderSize;
    private List<PfBorder> pfBorders;
    private List<SfOrder> sfOrders;
    private List<ComShipMan> comShipMans;
    private List<OrderDetail> oList;

    public void setSfOrders(List<SfOrder> sfOrders) {
        this.sfOrders = sfOrders;
    }

    public List<SfOrder> getSfOrders() {
        return sfOrders;
    }

    public void setSfOrderSize(Integer sfOrderSize) {
        this.sfOrderSize = sfOrderSize;
    }

    public Integer getSfOrderSize() {
        return sfOrderSize;
    }

    public void setComShipMans(List<ComShipMan> comShipMans) {
        this.comShipMans = comShipMans;
    }

    public List<ComShipMan> getComShipMans() {
        return comShipMans;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public Integer getWaitShipNum() {
        return waitShipNum;
    }

    public void setPfBorders(List<PfBorder> pfBorders) {
        this.pfBorders = pfBorders;
    }

    public List<PfBorder> getPfBorders() {
        return pfBorders;
    }

    public void setWaitShipNum(Integer waitShipNum) {
        this.waitShipNum = waitShipNum;
    }

    public Integer getWaitPayNum() {
        return waitPayNum;
    }

    public void setWaitPayNum(Integer waitPayNum) {
        this.waitPayNum = waitPayNum;
    }

    public Integer getShippedNum() {
        return shippedNum;
    }

    public void setShippedNum(Integer shippedNum) {
        this.shippedNum = shippedNum;
    }

    public Integer getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(Integer completeNum) {
        this.completeNum = completeNum;
    }

    public Integer getIsMPS() {
        return isMPS;
    }

    public void setIsMPS(Integer isMPS) {
        this.isMPS = isMPS;
    }

    public Integer getMpsNum() {
        return mpsNum;
    }

    public void setMpsNum(Integer mpsNum) {
        this.mpsNum = mpsNum;
    }

    public List<OrderDetail> getoList() {
        return oList;
    }

    public void setoList(List<OrderDetail> oList) {
        this.oList = oList;
    }
}
