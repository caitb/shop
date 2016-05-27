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
    },
    SF_AddCountingFee {
        public Integer getCode() {
            return 9;
        }

        public String getDesc() {
            return "小铺订单入账结算中";
        }
    },
    SF_AddTotalIncomeFee {
        public Integer getCode() {
            return 10;
        }

        public String getDesc() {
            return "小铺订单计入总销售额";
        }
    },
    SF_AddProfitFee {
        public Integer getCode() {
            return 11;
        }

        public String getDesc() {
            return "小铺订单计入总利润";
        }
    },
    SF_Refund_SubCountingFee {
        public Integer getCode() {
            return 12;
        }

        public String getDesc() {
            return "小铺订单退货减少结算中";
        }
    },
    SF_Refund_SubTotalIncomeFee {
        public Integer getCode() {
            return 13;
        }

        public String getDesc() {
            return "小铺订单退货减少总销售额";
        }
    },
    SF_Refund_SubProfitFee {
        public Integer getCode() {
            return 14;
        }

        public String getDesc() {
            return "小铺订单退货减少总利润";
        }
    },
    SFSHOP_COUNTING_SUB{
        public Integer getCode() {
            return 15;
        }

        public String getDesc() {
            return "小铺店铺账单结算,结算中减少";
        }
    },
    SFSHOP_EXTRACTABLE_ADD{
        public Integer getCode() {
            return 16;
        }

        public String getDesc() {
            return "小铺店铺账单结算,可提现增加";
        }
    };

    public abstract Integer getCode();
    public abstract String getDesc();
}
