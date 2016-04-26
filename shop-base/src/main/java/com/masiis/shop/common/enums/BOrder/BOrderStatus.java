package com.masiis.shop.common.enums.BOrder;

/**
 * 订单状态枚举类
 *
 * @author muchaofeng
 * @date 2016/4/5 15:22
 */

public enum BOrderStatus {
    NotPaid {
        public Integer getCode() {
            return 0;
        }

        public String getDesc() {
            return "未付款";
        }
    },
    accountPaid {
        public Integer getCode() {
            return 1;
        }

        public String getDesc() {
            return "已付款";
        }
    },
    Disabled {
        public Integer getCode() {
            return 2;
        }

        public String getDesc() {
            return "已取消";
        }
    }, Complete {
        public Integer getCode() {
            return 3;
        }

        public String getDesc() {
            return "已完成";
        }
    }, Refund {
        public Integer getCode() {
            return 4;
        }

        public String getDesc() {
            return "退款中";
        }
    }, RefundComplete {
        public Integer getCode() {
            return 5;
        }

        public String getDesc() {
            return "已退款";
        }
    }, MPS {
        public Integer getCode() {
            return 6;
        }

        public String getDesc() {
            return "排单中";
        }
    }, WaitShip {
        public Integer getCode() {
            return 7;
        }

        public String getDesc() {
            return "待发货";
        }
    }, Ship {
        public Integer getCode() {
            return 8;
        }

        public String getDesc() {
            return "待收货";
        }
    }, offLineNoPay {
        public Integer getCode() {
            return 9;
        }

        public String getDesc() {
            return "线下支付未付款";
        }
    };

//    CodeBorderStatus0("未付款");
//    CodeBorderStatus1("1","已付款"),
//    CodeBorderStatus2("2","已取消"),
//    CodeBorderStatus3("3","已完成"),
//    CodeBorderStatus4("4","退款中"),
//    CodeBorderStatus5("5","已退款"),
//    CodeBorderStatus6("6","排单中"),
//    CodeBorderStatus7("7","待发货"),
//    CodeBorderStatus8("8","已发货");

    public abstract Integer getCode();

    public abstract String getDesc();

    public static BOrderStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (BOrderStatus pt : BOrderStatus.values()) {
            if (pt.getCode().intValue() == code.intValue()) {
                return pt;
            }
        }
        return null;
    }

}
