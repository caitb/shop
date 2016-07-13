package com.masiis.shop.common.enums.promotion;

import com.masiis.shop.common.enums.mall.SfOrderStatusEnum;

/**
 * Created by hzz on 2016/7/5.
 */
public enum SfGOrderPayStatusEnum {
    ORDER_UNPAY(0, "未付款"),
    ORDER_PAID(1, "已付款"),
    ORDER_CANCEL(2, "已取消"),
    ORDER_COMPLETE(3, "已完成"),
    ORDER_REFUNDING(4, "退款中"),
    ORDER_REFUNDED(5, "已退货"),
    ORDER_MPS(6, "排单中"),
    ORDER_WAITSHIP(7, "待发货"),
    ORDER_SHIPED(8, "已发货");

    private Integer code;
    private String desc;

    SfGOrderPayStatusEnum(Integer code, String desc) {
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

    public SfOrderStatusEnum getByCode(Integer code){
        if(code == null){
            return null;
        }
        for(SfOrderStatusEnum pt: SfOrderStatusEnum.values()){
            if (pt.getCode().intValue() == code.intValue()) {
                return pt;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "SfGOrderPayStatusEnum{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
