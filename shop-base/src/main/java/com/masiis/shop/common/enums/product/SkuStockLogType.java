package com.masiis.shop.common.enums.product;

/**
 * SkuStockLogType
 *
 * @author ZhaoLiang
 * @date 2016/5/13
 */
public enum SkuStockLogType {
    agent {
        public Integer getCode() {
            return 0;
        }

        public String getDesc() {
            return "新加入合伙";
        }
    }, Supplement {
        public Integer getCode() {
            return 0;
        }

        public String getDesc() {
            return "补货";
        }
    };

    public abstract Integer getCode();

    public abstract String getDesc();
}
