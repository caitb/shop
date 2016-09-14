package com.masiis.shop.common.enums.platform;

/**
 * UserSkuStockLogType
 *
 * @author ZhaoLiang
 * @date 2016/5/13
 */
public enum UserSkuStockLogType {
    agent {
        public Integer getCode() {
            return 0;
        }

        public String getDesc() {
            return "合伙";
        }
    }, downAgent {
        public Integer getCode() {
            return 1;
        }

        public String getDesc() {
            return "下级合伙";
        }
    }, shopOrder {
        public Integer getCode() {
            return 2;
        }

        public String getDesc() {
            return "小铺发货";
        }
    }, shopReturn {
        public Integer getCode() {
            return 3;
        }

        public String getDesc() {
            return "小铺退货";
        }
    }, STORAGECHANGE_BILL_ADD {
        public Integer getCode() {
            return 4;
        }

        public String getDesc() {
            return "库存变更单增加";
        }
    }, PROMOTION_ADD {
        public Integer getCode() {
            return 5;
        }

        public String getDesc() {
            return "活动增加库存";
        }
    }, PROMOTION_REDUCE {
        public Integer getCode() {
            return 6;
        }

        public String getDesc() {
            return "活动减少库存";
        }
    }, TAKE{
        public Integer getCode() {
            return 7;
        }

        public String getDesc() {
            return "拿货订单减少库存";
        }
    };

    public abstract Integer getCode();

    public abstract String getDesc();
}
