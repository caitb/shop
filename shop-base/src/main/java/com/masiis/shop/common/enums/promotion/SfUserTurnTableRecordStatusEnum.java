package com.masiis.shop.common.enums.promotion;

/**
 * Created by hzz on 2016/7/30.
 */
public enum SfUserTurnTableRecordStatusEnum {
    GIFT_NOT_RECEIVE(0, "未领取"),
    GIFT_RECEIVED(1, "已领取");

    private Integer code;
    private String desc;

    SfUserTurnTableRecordStatusEnum(Integer code, String desc) {
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
}
