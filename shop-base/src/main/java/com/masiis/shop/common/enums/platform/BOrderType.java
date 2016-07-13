package com.masiis.shop.common.enums.platform;

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

        if(code == null) return null;

        for(BOrderType orderType : BOrderType.values()){
            if(orderType.getCode().intValue() == code.intValue()) return orderType;
        }

        return null;
    }

    @Override
    public String toString() {
        return "BOrderType{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }

}
