package com.masiis.shop.common.enums.platform;

/**
 * Created by hzz on 2016/9/6.
 */
public enum PfBorderPromotionIsSendEnum {
    NO_GiVE {
        public Integer getCode() {
            return 0;
        }
        public String getDesc() {
            return "未发放";
        }
    },
    GiVED {
        public Integer getCode() {
            return 1;
        }
        public String getDesc() {
            return "已发放";
        }
    };

    public abstract Integer getCode();
    public abstract String getDesc();
}
