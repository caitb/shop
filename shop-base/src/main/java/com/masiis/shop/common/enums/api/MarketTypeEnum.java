package com.masiis.shop.common.enums.api;

/**
 * Created by jjh on 2016/4/2.
 */
public enum MarketTypeEnum {
    /**
     * 品牌
     */
    TYPE_Brand{
        public Integer getCode(){
            return 1;
        }
        public String getMessage(){
            return "品牌";
        }
    },
    /**
     * 家族
     */
    TYPE_Family{
        public Integer getCode(){
            return 2;
        }
        public String getMessage(){
            return "家族";
        }
    },
    /**
     * 团队
     */
    TYPE_Team{
        public Integer getCode(){
            return 3;
        }
        public String getMessage(){
            return "团队";
        }
    };

    public abstract Integer getCode();

    public abstract String getMessage();
}
