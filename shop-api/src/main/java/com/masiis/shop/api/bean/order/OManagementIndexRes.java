package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseRes;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
public class OManagementIndexRes extends BaseRes {
    /**
     * 出货订单:我要发货数量
     */
    private Integer outShipNum;
    /**
     * 出货订单:我收到的排单数量
     */
    private Integer outMPSNum;
    /**
     * 出货订单:需要对方付款数量
     */
    private Integer outWaitPayNum;
    /**
     * 出货订单:需要对方发货数量
     */
    private Integer outWaitReceiveNum;
    /**
     * 进货订单:需要我收货数量
     */
    private Integer inReceiveNum;
    /**
     * 进货订单:我在排单数量
     */
    private Integer inMPSNum;
    /**
     * 进货订单:需要我付款数量
     */
    private Integer inPayingNum;
    /**
     * 进货订单:等待对方发货数量
     */
    private Integer inWaitShipNum;

    public Integer getOutShipNum() {
        return outShipNum;
    }

    public void setOutShipNum(Integer outShipNum) {
        this.outShipNum = outShipNum;
    }

    public Integer getOutMPSNum() {
        return outMPSNum;
    }

    public void setOutMPSNum(Integer outMPSNum) {
        this.outMPSNum = outMPSNum;
    }

    public Integer getOutWaitPayNum() {
        return outWaitPayNum;
    }

    public void setOutWaitPayNum(Integer outWaitPayNum) {
        this.outWaitPayNum = outWaitPayNum;
    }

    public Integer getOutWaitReceiveNum() {
        return outWaitReceiveNum;
    }

    public void setOutWaitReceiveNum(Integer outWaitReceiveNum) {
        this.outWaitReceiveNum = outWaitReceiveNum;
    }

    public Integer getInReceiveNum() {
        return inReceiveNum;
    }

    public void setInReceiveNum(Integer inReceiveNum) {
        this.inReceiveNum = inReceiveNum;
    }

    public Integer getInMPSNum() {
        return inMPSNum;
    }

    public void setInMPSNum(Integer inMPSNum) {
        this.inMPSNum = inMPSNum;
    }

    public Integer getInPayingNum() {
        return inPayingNum;
    }

    public void setInPayingNum(Integer inPayingNum) {
        this.inPayingNum = inPayingNum;
    }

    public Integer getInWaitShipNum() {
        return inWaitShipNum;
    }

    public void setInWaitShipNum(Integer inWaitShipNum) {
        this.inWaitShipNum = inWaitShipNum;
    }
}
