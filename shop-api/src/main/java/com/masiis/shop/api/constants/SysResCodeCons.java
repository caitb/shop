package com.masiis.shop.api.constants;

/**
 * @Date:2016/4/25
 * @auth:lzh
 */
public class SysResCodeCons {
    public static final String RES_CODE_NOT_KNOWN = "-1";
    public static final String RES_CODE_NOT_KNOWN_MSG = "请求繁忙,请稍候再试";

    public static final String RES_CODE_SUCCESS = "0";
    public static final String RES_CODE_SUCCESS_MSG = "请求成功";

    public static final String RES_CODE_ILLEGAL = "-2";
    public static final String RES_CODE_ILLEGAL_MSG = "非法请求";

    /**
     * 请求参数校验
     */
    public static final String RES_CODE_REQ_STRUCT_INVALID = "100001";
    public static final String RES_CODE_REQ_STRUCT_INVALID_MSG = "请求数据格式不合法";

    public static final String RES_CODE_REQ_SIGN_INVALID = "100002";
    public static final String RES_CODE_REQ_SIGN_INVALID_MSG = "请求签名错误";

    public static final String RES_CODE_REQ_TOKEN_INVALID = "100003";
    public static final String RES_CODE_REQ_TOKEN_INVALID_MSG = "请求token不合法";

    public static final String RES_CODE_REQ_TOKEN_PASTDUE = "100004";
    public static final String RES_CODE_REQ_TOKEN_PASTDUE_MSG = "请求token已过期";

    public static final String RES_CODE_REQ_PARAMETER_MISTAKEN = "100005";
    public static final String RES_CODE_REQ_PARAMETER_MISTAKEN_MSG = "请求参数有误";

    /**
     * 获取手机验证码返回码
     */
    public static final String RES_CODE_PHONENUM_BLANK = "100101";
    public static final String RES_CODE_PHONENUM_BLANK_MSG = "电话号码为空";

    public static final String RES_CODE_PHONENUM_INVALID = "100102";
    public static final String RES_CODE_PHONENUM_INVALID_MSG = "电话号码格式不正确";

    public static final String RES_CODE_VALIDCODE_SMS_FAIL = "100103";
    public static final String RES_CODE_VALIDCODE_SMS_FAIL_MSG = "验证码短信发送失败";

    public static final String RES_CODE_VALIDCODE_REQ_OFTEN = "100104";
    public static final String RES_CODE_VALIDCODE_REQ_OFTEN_MSG = "验证码请求过于频繁";

    /**
     * 手机号登录返回码
     */
    public static final String RES_CODE_VALIDCODE_IS_BLANK = "100201";
    public static final String RES_CODE_VALIDCODE_IS_BLANK_MSG = "验证码为空";

    public static final String RES_CODE_VALIDCODE_IS_INVALID = "100202";
    public static final String RES_CODE_VALIDCODE_IS_INVALID_MSG = "验证码不匹配";

    public static final String RES_CODE_VALIDCODE_IS_EXPIRED = "100203";
    public static final String RES_CODE_VALIDCODE_IS_EXPIRED_MSG = "验证码不存在或已过期";

    public static final String RES_CODE_USER_ISNOT_SIGNUP = "100204";
    public static final String RES_CODE_USER_ISNOT_SIGNUP_MSG = "该手机号未绑定用户";

    /**
     * 微信登录
     */
    public static final String RES_CODE_WXLOGIN_OPENID_NULL = "100301";
    public static final String RES_CODE_WXLOGIN_OPENID_NULL_MSG = "openid为空";

    public static final String RES_CODE_WXLOGIN_UNIONID_NULL = "100302";
    public static final String RES_CODE_WXLOGIN_UNIONID_NULL_MSG = "unionid为空";

    public static final String RES_CODE_WXLOGIN_APPID_NULL = "100303";
    public static final String RES_CODE_WXLOGIN_APPID_NULL_MSG = "appid为空";

    /*地址*/
    public static final String RES_CODE_ADDRESS_ADD_FAIL = "100401";
    public static final String RES_CODE_ADDRESS_ADD_FAIL_MSG = "添加地址失败";
    public static final String RES_CODE_ADDRESS_EDIT_FAIL = "100402";
    public static final String RES_CODE_ADDRESS_EDIT_FAIL_MSG = "编辑地址失败";
    public static final String RES_CODE_ADDRESS_QUERY_FAIL = "100403";
    public static final String RES_CODE_ADDRESS_QUERY_FAIL_MSG = "查询地址失败";
    public static final String RES_CODE_ADDRESS_DELETE_FAIL = "100404";
    public static final String RES_CODE_ADDRESS_DELETE_FAIL_MEG = "删除地址失败";
    public static final String RES_CODE_ADDRESS_DEFAULT_FAIL = "100405";
    public static final String RES_CODE_ADDRESS_DEFAULT_FAIL_MEG = "设置默认地址失败";

    /**
     * 商品
     */
    public static final String RES_CODE_SKU_NULL = "100501";
    public static final String RES_CODE_SKU_NULL_MSG = "sku不合法,系统不存在该sku";

    public static final String RES_CODE_AREA_PROVINCE_QUERY_FAIL = "100601";
    public static final String RES_CODE_AREA_PROVINCE_QUERY_FAIL_MEG = "查询所有的省失败";

    public static final String RES_CODE_AREA_CITY_QUERY_FAIL = "100602";
    public static final String RES_CODE_AREA_CITY_QUERY_FAIL_MEG = "查询所有的市失败";

    public static final String RES_CODE_AREA_COUNTY_QUERY_FAIL = "100603";
    public static final String RES_CODE_AREA_COUNTY_QUERY_FAIL_MEG = "查询所有的区失败";

    /**
     * 微信支付
     */
    public static final String RES_CODE_WXPAY_ORDERCODE_NULL = "900101";
    public static final String RES_CODE_WXPAY_ORDERCODE_NULL_MSG = "ordercode为空";


}
