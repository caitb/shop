package com.masiis.shop.common.enums.promotion;

/**
 * Created by hzz on 2016/7/29.
 */
public enum SfGorderTypeEnum {
    ORDER_PROMOTION(0, "活动订单"),
    ORDER_TURN_TABLE(100, "抽奖订单");

    private Integer code;
    private String desc;

    SfGorderTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Integer getCode(Integer type){
        if (type.equals(0)){
            return SfGorderTypeEnum.ORDER_TURN_TABLE.getCode();
        }
        return 0;
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
}
