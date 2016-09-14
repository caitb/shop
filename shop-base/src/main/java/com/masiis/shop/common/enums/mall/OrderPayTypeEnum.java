package com.masiis.shop.common.enums.mall;

/**
 * 订单支付方式
 * Created by hw on 2016/9/14.
 */
public enum OrderPayTypeEnum {

    PAY_TYPE_WX(0, "微信支付"),
    PAY_TYPE_OFFLINE(1, "线下支付"),
    PAY_TYPE_ALI(2, "支付宝支付"),
    PAY_TYPE_ZERO(3, "0元免支付");

    private Integer code;
    private String desc;

    OrderPayTypeEnum(Integer code, String desc) {
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

    public static OrderPayTypeEnum getByCode(Integer code){
        if(code == null){
            return null;
        }
        for(OrderPayTypeEnum pt : OrderPayTypeEnum.values()){
            if (pt.getCode().intValue() == code.intValue()) {
                return pt;
            }
        }
        return null;
    }

}
