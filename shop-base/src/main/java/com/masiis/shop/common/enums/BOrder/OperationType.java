package com.masiis.shop.common.enums.BOrder;

/**
 * BOrderType
 *
 * @author ZhaoLiang
 * @date 2016/4/17
 * 订单类型(0代理1补货2拿货)
 */
public enum OperationType {
    Insert {
        public Integer getCode() {
            return 0;
        }

        public String getDesc() {
            return "新增";
        }
    },
    Update {
        public Integer getCode() {
            return 1;
        }

        public String getDesc() {
            return "修改";
        }
    },
    Delect {
        public Integer getCode() {
            return 2;
        }

        public String getDesc() {
            return "删除";
        }
    };

    public abstract Integer getCode();

    public abstract String getDesc();

    public static OperationType getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (OperationType pt : OperationType.values()) {
            if (pt.getCode().intValue() == code.intValue()) {
                return pt;
            }
        }
        return null;
    }
}
