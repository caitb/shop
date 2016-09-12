package com.masiis.shop.common.enums.platform;

/**
 * Created by hzz on 2016/9/7.
 */
public enum PfBorderPromotionGiveStockChangeEnum  {
    agent {
        public Integer getCode() {return 0;
        }
        public String getDesc() {
            return "代理订单";
        }
    }, Supplement {
        public Integer getCode() {
            return 1;
        }
        public String getDesc() {
            return "补货订单";
        }
    },Take {
        public Integer getCode() {
            return 2;
        }
        public String getDesc() {
            return "拿货订单";
        }
    },
    UPGRADE {
        public Integer getCode() {return 3;}
        public String getDesc() {
            return "升级订单";
        }
    }, sell {
        public Integer getCode() {return 4;}
        public String getDesc() {
            return "小铺端卖货";
        }
    }, recovery {
        public Integer getCode() {return 5;}
        public String getDesc() {
            return "时间到期回收";
        }
    };

    public abstract Integer getCode();
    public abstract String getDesc();
}
