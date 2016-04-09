package com.masiis.shop.common.enums.wx;

/**
 * 企业给用户打款错误码枚举
 *
 * @Date:2016/4/9
 * @auth:lzh
 */
public enum WxPayUserErrCodeEnum {
    /**
     * 没有权限
     */
    NOAUTH {
        @Override
        public String getCode() {
            return "NOAUTH";
        }

        @Override
        public String getDesc() {
            return "没有权限";
        }
    },
    /**
     * 付款金额不能小于最低限额
     */
    AMOUNT_LIMIT {
        @Override
        public String getCode() {
            return "AMOUNT_LIMIT";
        }

        @Override
        public String getDesc() {
            return "付款金额不能小于最低限额";
        }
    },
    /**
     * 参数错误
     */
    PARAM_ERROR {
        @Override
        public String getCode() {
            return "PARAM_ERROR";
        }

        @Override
        public String getDesc() {
            return "参数错误";
        }
    },
    /**
     * Openid错误
     */
    OPENID_ERROR {
        @Override
        public String getCode() {
            return "OPENID_ERROR";
        }

        @Override
        public String getDesc() {
            return "Openid错误";
        }
    },
    /**
     * 余额不足
     */
    NOTENOUGH {
        @Override
        public String getCode() {
            return "NOTENOUGH";
        }

        @Override
        public String getDesc() {
            return "余额不足";
        }
    },
    /**
     * 系统繁忙，请稍后再试
     */
    SYSTEMERROR {
        @Override
        public String getCode() {
            return "SYSTEMERROR";
        }

        @Override
        public String getDesc() {
            return "系统繁忙，请稍后再试";
        }
    },
    /**
     * 姓名校验出错
     */
    NAME_MISMATCH {
        @Override
        public String getCode() {
            return "NAME_MISMATCH";
        }

        @Override
        public String getDesc() {
            return "姓名校验出错";
        }
    },
    /**
     * 签名错误
     */
    SIGN_ERROR {
        @Override
        public String getCode() {
            return "SIGN_ERROR";
        }

        @Override
        public String getDesc() {
            return "签名错误";
        }
    },
    /**
     * Post内容出错
     */
    XML_ERROR {
        @Override
        public String getCode() {
            return "XML_ERROR";
        }

        @Override
        public String getDesc() {
            return "Post内容出错";
        }
    },
    /**
     * 两次请求参数不一致
     */
    FATAL_ERROR {
        @Override
        public String getCode() {
            return "FATAL_ERROR";
        }

        @Override
        public String getDesc() {
            return "两次请求参数不一致";
        }
    },
    /**
     * 证书出错
     */
    CA_ERROR {
        @Override
        public String getCode() {
            return "CA_ERROR";
        }

        @Override
        public String getDesc() {
            return "证书出错";
        }
    }
    ;

    public abstract String getCode();
    public abstract String getDesc();
}
