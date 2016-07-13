package com.masiis.shop.common.enums.promotion;

/**
 * Created by hzz on 2016/7/12.
 */
public enum SfGorderShipStatus {

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
