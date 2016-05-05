package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingRes;

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
    private List<OrderDetail> oList;

    public Integer getWaitShipNum() {
        return waitShipNum;
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
