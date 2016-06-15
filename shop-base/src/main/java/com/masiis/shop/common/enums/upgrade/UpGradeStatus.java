package com.masiis.shop.common.enums.upgrade;

/**
 * 代理用户升级状态枚举
 * Created by wangbingjian on 2016/6/15.
 */
public enum UpGradeStatus {
    /**
     * 未处理状态
     */
    STATUS_Untreated{
        public Integer getCode(){
            return 0;
        }
        public String getMessage(){
            return "未处理";
        }
    },
    /**
     * 处理中
     */
    STATUS_Processing{
        public Integer getCode(){
            return 1;
        }
        public String getMessage(){
            return "处理中";
        }
    },
    /**
     * 待支付
     */
    STATUS_NoPayment{
        public Integer getCode(){
            return 2;
        }
        public String getMessage(){
            return "待支付";
        }
    },
    /**
     * 完成
     */
    STATUS_Complete{
        public Integer getCode(){
            return 3;
        }
        public String getMessage(){
            return "完成";
        }
    },
    /**
     * 取消
     */
    STATUS_Revocation{
        public Integer getCode(){
            return 4;
        }
        public String getMessage(){
            return "取消";
        }
    },
    /**
     * 上级未处理
     */
    UP_STATUS_Untreated{
        public Integer getCode(){
            return 0;
        }
        public String getMessage(){
            return "未处理";
        }
    },
    /**
     * 上级暂不升级
     */
    UP_STATUS_NotUpgrade{
        public Integer getCode(){
            return 1;
        }
        public String getMessage(){
            return "暂不升级";
        }
    },
    /**
     * 我要升级
     */
    UP_STATUS_Upgrade{
        public Integer getCode(){
            return 2;
        }
        public String getMessage(){
            return "我要升级";
        }
    },
    /**
     * 我要升级
     */
    UP_STATUS_Complete{
        public Integer getCode(){
            return 3;
        }
        public String getMessage(){
            return "处理完成";
        }
    };

    public abstract Integer getCode();

    public abstract String getMessage();

}
