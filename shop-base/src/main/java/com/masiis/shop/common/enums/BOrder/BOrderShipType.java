package com.masiis.shop.common.enums.BOrder;

/**
 * BOrderShipType
 *
 * @author ZhaoLiang
 * @date 2016/4/14
 */
public enum BOrderShipType {
    Logistics {
        public Integer getCode() {
            return 0;
        }

        public String getDesc() {
            return "物流配送";
        }
    };

    public abstract Integer getCode();

    public abstract String getDesc();
}
