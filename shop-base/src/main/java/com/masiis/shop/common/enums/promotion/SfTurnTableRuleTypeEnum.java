package com.masiis.shop.common.enums.promotion;

/**
 * Created by hzz on 2016/8/2.
 */
public enum SfTurnTableRuleTypeEnum {
    B(0, "B端"),
    C(1, "C端");

    private Integer code;
    private String desc;

    SfTurnTableRuleTypeEnum(Integer code, String desc) {
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
