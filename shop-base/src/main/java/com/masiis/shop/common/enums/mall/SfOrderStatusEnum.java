package com.masiis.shop.common.enums.mall;

/**
 * 小铺订单状态枚举
 *
 * @Date:2016/4/12
 * @auth:lzh
 */
public enum SfOrderStatusEnum {
    ORDER_UNPAY{
        public Integer getCode() {
            return 0;
        }

        public String getDesc() {
            return "未付款";
        }
    },
    ORDER_PAID{
        public Integer getCode() {
            return 1;
        }

        public String getDesc() {
            return "已付款";
        }
    },
    ORDER_CANCEL{
        public Integer getCode() {
            return 2;
        }

        public String getDesc() {
            return "已取消";
        }
    },
    ORDER_COMPLETE{
        public Integer getCode() {
            return 3;
        }

        public String getDesc() {
            return "已完成";
        }
    },
    ORDER_REFUNDING{
        public Integer getCode() {
            return 4;
        }

        public String getDesc() {
            return "退款中";
        }
    },
    ORDER_REFUNDED{
        public Integer getCode() {
            return 5;
        }

        public String getDesc() {
            return "已退款";
        }
    },
    ORDER_MPS{
        public Integer getCode() {
            return 6;
        }

        public String getDesc() {
            return "排单中";
        }
    },
    ORDER_WAITSHIP{
        public Integer getCode() {
            return 7;
        }

        public String getDesc() {
            return "待发货";
        }
    },
    ORDER_SHIPED{
        public Integer getCode() {
            return 8;
        }

        public String getDesc() {
            return "已发货";
        }
    };

    public abstract Integer getCode();
    public abstract String getDesc();
    public SfOrderStatusEnum getByCode(Integer code){
        if(code == null){
            return null;
        }
        for(SfOrderStatusEnum pt: SfOrderStatusEnum.values()){
            if (pt.getCode().intValue() == code.intValue()) {
                return pt;
            }
        }
        return null;
    }
}
