package com.masiis.shop.common.enums.platform;

/**
 * BOrderShipType
 *
 * @author ZhaoLiang
 * @date 2016/4/14
 */
public enum BOrderShipType {

    Logistics(0, "物流配送");

    private Integer code;
    private String desc;

    BOrderShipType(Integer code, String desc) {
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

    public static BOrderShipType getByCode(Integer code) {

        if(code == null) return null;

        for(BOrderShipType orderShipType : BOrderShipType.values()){
            if(orderShipType.getCode().intValue() == code.intValue()) return orderShipType;
        }

        return null;
    }

    @Override
    public String toString() {
        return "BOrderShipType{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }

}
