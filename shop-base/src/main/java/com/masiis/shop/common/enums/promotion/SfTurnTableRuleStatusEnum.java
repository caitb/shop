package com.masiis.shop.common.enums.promotion;

/**
 * Created by hzz on 2016/8/2.
 */
public enum SfTurnTableRuleStatusEnum {
    EFFECT(0, "生效"),
    NO_EFFECT(1, "失效");

    private Integer code;
    private String desc;

    SfTurnTableRuleStatusEnum(Integer code, String desc) {
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
