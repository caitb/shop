package com.masiis.shop.common.enums.platform;

/**
 * BOrderUserSource
 *
 * @author ZhaoLiang
 * @date 2016/8/19
 */
public enum BOrderUserSource {
    free(1, "世界市场"), search(2, "手机号搜索"), scan(3, "扫码");

    private Integer code;
    private String desc;

    BOrderUserSource(Integer code, String desc) {
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

    public static BOrderType getByCode(Integer code) {

        if (code == null) return null;

        for (BOrderType orderType : BOrderType.values()) {
            if (orderType.getCode().intValue() == code.intValue()) return orderType;
        }

        return null;
    }

    @Override
    public String toString() {
        return "BOrderUserSource{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
