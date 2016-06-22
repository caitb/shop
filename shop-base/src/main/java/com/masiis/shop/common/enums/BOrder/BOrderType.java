package com.masiis.shop.common.enums.BOrder;

/**
 * BOrderType
 *
 * @author ZhaoLiang
 * @date 2016/4/17
 * 订单类型(0代理1补货2拿货)
 */
public enum BOrderType {

    agent(0, "合伙订单"), Supplement(1, "补货订单"), Take(2, "拿货订单"), UPGRADE(3, "升级订单");

    private Integer code;
    private String desc;

    BOrderType(Integer code, String desc) {
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

    public static BOrderType getByCode(Integer code){
        switch (code) {
            case 0: return BOrderType.agent;
            case 1: return BOrderType.Supplement;
            case 2: return BOrderType.Take;
            case 3: return BOrderType.UPGRADE;
        }

        return null;
    }
}
