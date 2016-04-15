package com.masiis.shop.common.enums.BOrder;

/**
 * BOrderShipStatus
 *
 * @author ZhaoLiang
 * @date 2016/4/14
 */
public enum BOrderShipStatus {
    WaitShip {
        public Integer getCode() {
            return 0;
        }

        public String getDesc() {
            return "未发货";
        }
    },
    Ship {
        public Integer getCode() {
            return 5;
        }

        public String getDesc() {
            return "已发货";
        }
    },
    Receipt {
        public Integer getCode() {
            return 9;
        }

        public String getDesc() {
            return "已收货";
        }
    };

    public abstract Integer getCode();
    public abstract String getDesc();
}
