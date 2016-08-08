package com.masiis.shop.common.enums.promotion;

/**
 * Created by hzz on 2016/7/29.
 */
public enum SfUserTurnTableTimesTypeEnum {
    REDUCE_TIMES(1, "减少"),
    ADD_TIMES(0, "增加");

    private Integer code;
    private String desc;

    SfUserTurnTableTimesTypeEnum(Integer code, String desc) {
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
