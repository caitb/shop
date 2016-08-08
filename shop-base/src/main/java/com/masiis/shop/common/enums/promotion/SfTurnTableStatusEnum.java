package com.masiis.shop.common.enums.promotion;

/**
 * Created by hzzh on 2016/8/1.
 */
public enum SfTurnTableStatusEnum {
    ING(0, "进行中"),
    STOP(1, "暂停"),
    END(2, "结束");

    private Integer code;
    private String desc;

    SfTurnTableStatusEnum(Integer code, String desc) {
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
