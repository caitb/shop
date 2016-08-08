package com.masiis.shop.common.enums.promotion;

/**
 * Created by hzz on 2016/8/6.
 */
public enum ComGiftIsGiftEnum {
    isGift_true(0, "是奖品"),
    isGift_false(1, "不是奖品");

    private Integer code;
    private String desc;

    ComGiftIsGiftEnum(Integer code, String desc) {
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
