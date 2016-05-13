package com.masiis.shop.common.enums.product;

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
    }, Supplement {
        public Integer getCode() {
            return 2;
        }

        public String getDesc() {
            return "补货";
        }
    },downTake{
        public Integer getCode() {
            return 3;
        }

        public String getDesc() {
            return "拿货";
        }
    },shopOrder{
        public Integer getCode() {
            return 4;
        }

        public String getDesc() {
            return "小铺发货";
        }
    },shopReturn{
        public Integer getCode() {
            return 5;
        }

        public String getDesc() {
            return "小铺退货";
        }
    };

    public abstract Integer getCode();

    public abstract String getDesc();
}
