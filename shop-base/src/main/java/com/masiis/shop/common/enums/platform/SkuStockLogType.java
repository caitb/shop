package com.masiis.shop.common.enums.platform;

/**
 * SkuStockLogType
 *
 * @author ZhaoLiang
 * @date 2016/5/13
 */
public enum SkuStockLogType {
    downAgent {
        public Integer getCode() {
            return 0;
        }

        public String getDesc() {
            return "新加入合伙";
        }
    }, registerGiveSku {
        public Integer getCode() {
            return 1;
        }
    public String getDesc() {
        return "小白注册合伙赠送商品";
    }
    }, recoveryGiveSku {
        public Integer getCode() {
            return 2;
        }
        public String getDesc() {
            return "平台回收赠送小白的商品";
        }
    };

    public abstract Integer getCode();

    public abstract String getDesc();
}
