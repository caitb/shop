package com.masiis.shop.common.enums;

/**
 * 小铺用户提现记录审核状态
 *
 * @Date:2016/4/10
 * @auth:lzh
 */
public enum SfUserExtractAuditTypeEnum {
    /**
     * 审核中
     */
    IN_AUDITING {
        @Override
        public Integer getCode() {
            return 0;
        }

        @Override
        public String getDesc() {
            return "审核中";
        }
    },
    /**
     * 未通过
     */
    AUDIT_FAIL {
        @Override
        public Integer getCode() {
            return 1;
        }

        @Override
        public String getDesc() {
            return "未通过";
        }
    },
    /**
     * 汇款中
     */
    IN_PAYING {
        @Override
        public Integer getCode() {
            return 2;
        }

        @Override
        public String getDesc() {
            return "汇款中";
        }
    },
    /**
     * 已汇款
     */
    ALREADY_PAY {
        @Override
        public Integer getCode() {
            return 3;
        }

        @Override
        public String getDesc() {
            return "已汇款";
        }
    }
    ;
    public abstract Integer getCode();
    public abstract String getDesc();
}
