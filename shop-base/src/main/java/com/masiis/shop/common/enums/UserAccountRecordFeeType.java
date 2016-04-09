package com.masiis.shop.common.enums;

/**
 * UserAccountRecordFeeType
 *
 * @author ZhaoLiang
 * @date 2016/4/9
 * 操作资金类型: 0,订单入账; 1,结算减少; 2,用户提现 ; 3,订单计入总销售额; 4,可提现增加; 5,保证金入账; 6,保证金扣除; 7,计入成本; 8,订单计入总利润额;
 **/
public enum UserAccountRecordFeeType {

    AddCountingFee {
        public Integer getCode() {
            return 0;
        }

        public String getDesc() {
            return "订单入账结算中";
        }
    },
    SubCountingFee {
        public Integer getCode() {
            return 1;
        }

        public String getDesc() {
            return "结算中减少";
        }
    },
    SubExtractableFee {
        public Integer getCode() {
            return 2;
        }

        public String getDesc() {
            return "用户提现";
        }
    },
    AddTotalIncomeFee {
        public Integer getCode() {
            return 3;
        }

        public String getDesc() {
            return "订单计入总销售额";
        }
    },
    AddExtractableFee {
        public Integer getCode() {
            return 4;
        }

        public String getDesc() {
            return "可提现增加";
        }
    },
    AddBailFee {
        public Integer getCode() {
            return 5;
        }

        public String getDesc() {
            return "保证金入账";
        }
    },
    SubBailFee {
        public Integer getCode() {
            return 6;
        }

        public String getDesc() {
            return "保证金扣除";
        }
    },
    AddCostFee {
        public Integer getCode() {
            return 7;
        }

        public String getDesc() {
            return "计入成本";
        }
    },
    AddProfitFee {
        public Integer getCode() {
            return 8;
        }

        public String getDesc() {
            return "订单计入总利润额";
        }
    };

    public abstract Integer getCode();
    public abstract String getDesc();
}
