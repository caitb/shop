package com.masiis.shop.common.enums.platform;

/**
 * Created by hzz on 2016/9/7.
 */
public enum PfBorderPromotionIsTakeEnum {

    NO_MAY_TAKE {
        public Integer getCode() {
            return 0;
        }
        public String getDesc() {
            return "不支持拿货";
        }
    },
    MAY_TAKE {
        public Integer getCode() {
            return 1;
        }
        public String getDesc() {
            return "支持拿货";
        }
    };

    public abstract Integer getCode();
    public abstract String getDesc();
}
