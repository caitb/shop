package com.masiis.shop.common.enums.BOrder;

/**
 * 订单状态枚举类
 *
 * @author muchaofeng
 * @date 2016/4/5 15:22
 */

public enum BOrderStatus {

    NotPaid(0, "待付款"),
    accountPaid(1, "已付款"),
    Disabled(2, "已取消"),
    Complete(3, "已完成"),
    Refund(4, "退款中"),
    RefundComplete(5, "已退款"),
    MPS(6, "排单中"),
    WaitShip(7, "待发货"),
    Ship(8, "待收货"),
    offLineNoPay(9, "线下支付未付款");

    /* 订单状态 */
    private Integer code;
    /* 订单状态描述 */
    private String desc;

    BOrderStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static BOrderStatus getByCode(Integer code) {

        if (code == null) return null;

        for (BOrderStatus orderStatus : BOrderStatus.values()) {
            if (orderStatus.getCode().intValue() == code.intValue()) {
                return orderStatus;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "BOrderStatus{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }

}
