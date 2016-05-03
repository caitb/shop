package com.masiis.shop.common.enums.BOrder;

/**
 * BOrderType
 *
 * @author ZhaoLiang
 * @date 2016/4/17
 * 订单类型(0代理1补货2拿货)
 */
public enum BOrderType {
    agent {
        public Integer getCode() {
            return 0;
        }

        public String getDesc() {
            return "代理";
        }
    },
    Supplement {
        public Integer getCode() {
            return 1;
        }

        public String getDesc() {
            return "补货";
        }
    },
    Take {
        public Integer getCode() {
            return 2;
        }

        public String getDesc() {
            return "拿货";
        }
    };

    public abstract Integer getCode();

    public abstract String getDesc();

    public static BOrderType getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (BOrderType pt : BOrderType.values()) {
            if (pt.getCode().intValue() == code.intValue()) {
                return pt;
            }
        }
        return null;
    }
}
