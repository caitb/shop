package com.masiis.shop.api.constants;

/**
 * @Date:2016/4/25
 * @auth:lzh
 */
public class SysResCodeCons {
    public static final String RES_CODE_SUCCESS = "0";
    public static final String RES_CODE_SUCCESS_MSG = "请求成功";

    public static final String RES_CODE_PHONENUM_BLANK = "100001";
    public static final String RES_CODE_PHONENUM_BLANK_MSG = "电话号码为空";

    public static final String RES_CODE_PHONENUM_INVALID = "100002";
    public static final String RES_CODE_PHONENUM_INVALID_MSG = "电话号码格式不正确";

    public static final String RES_CODE_VALIDCODE_SMS_FAIL = "100003";
    public static final String RES_CODE_VALIDCODE_SMS_FAIL_MSG = "验证码短信发送失败";

    public static final String RES_CODE_VALIDCODE_REQ_OFTEN = "100004";
    public static final String RES_CODE_VALIDCODE_REQ_OFTEN_MSG = "验证码请求过于频繁";

    public static final String RES_CODE_VALIDCODE_IS_BLANK = "100101";
    public static final String RES_CODE_VALIDCODE_IS_BLANK_MSG = "验证码为空";

    public static final String RES_CODE_VALIDCODE_IS_INVALID = "100102";
    public static final String RES_CODE_VALIDCODE_IS_INVALID_MSG = "验证码不匹配";

    public static final String RES_CODE_VALIDCODE_IS_EXPIRED = "100103";
    public static final String RES_CODE_VALIDCODE_IS_EXPIRED_MSG = "验证码不存在或已过期";

    public static final String RES_CODE_USER_ISNOT_SIGNUP = "100104";
    public static final String RES_CODE_USER_ISNOT_SIGNUP_MSG = "该手机号未绑定用户";
}
