package com.masiis.shop.api.constants;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

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

    public static final String RES_CODE_REQ_BUSINESS_ERROR = "100006";
    public static final String RES_CODE_REQ_BUSINESS_ERROR_MSG = "系统业务错误";

    public static final String RES_CODE_REQ_OPERATE_ERROR = "100007";
    public static final String RES_CODE_REQ_OPERATE_ERROR_MSG = "业务操作失败";

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

    public static final String RES_CODE_VALIDCODE_TYPE_NOTKNOWN = "100105";
    public static final String RES_CODE_VALIDCODE_TYPE_NOTKNOWN_MSG = "验证码请求类型错误";

    public static final String RES_CODE_PHONENUM_USER_BINDED = "100106";
    public static final String RES_CODE_PHONENUM_USER_BINDED_MSG = "该用户已经绑定过手机号";

    public static final String RES_CODE_PHONENUM_PHONE_BINDED = "100107";
    public static final String RES_CODE_PHONENUM_PHONE_BINDED_MSG = "该手机号已经绑定过用户";

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
    public static final String RES_CODE_ADDRESS_DELETE_FAIL_MSG = "删除地址失败";
    public static final String RES_CODE_ADDRESS_DEFAULT_FAIL = "100405";
    public static final String RES_CODE_ADDRESS_DEFAULT_FAIL_MSG = "设置默认地址失败";

    /**
     * 商品
     */
    public static final String RES_CODE_SKU_NULL = "100501";
    public static final String RES_CODE_SKU_NULL_MSG = "sku不合法,系统不存在该sku";

    public static final String RES_CODE_AREA_PROVINCE_QUERY_FAIL = "100601";
    public static final String RES_CODE_AREA_PROVINCE_QUERY_FAIL_MSG = "查询所有的省失败";

    public static final String RES_CODE_AREA_CITY_QUERY_FAIL = "100602";
    public static final String RES_CODE_AREA_CITY_QUERY_FAIL_MSG = "查询所有的市失败";

    public static final String RES_CODE_AREA_COUNTY_QUERY_FAIL = "100603";
    public static final String RES_CODE_AREA_COUNTY_QUERY_FAIL_MSG = "查询所有的区失败";

    /**
     * 合伙人申请
     */
    public static final String RES_CODE_UPAPPLY_SKU_NULL = "100701";
    public static final String RES_CODE_UPAPPLY_SKU_NULL_MSG = "skuid为空";

    public static final String RES_CODE_UPAPPLY_SKU_INVALID = "100702";
    public static final String RES_CODE_UPAPPLY_SKU_INVALID_MSG = "sku不合法,系统不存在该sku";

    public static final String RES_CODE_CANAGENT_SKU_NULL = "100705";
    public static final String RES_CODE_CANAGENT_SKU_NULL_MSG = "skuid为空";

    public static final String RES_CODE_CANAGENT_SKU_INVALID = "100706";
    public static final String RES_CODE_CANAGENT_SKU_INVALID_MSG = "sku不合法,系统不存在该sku";

    public static final String RES_CODE_CANAGENT_NOTAGENT = "100707";
    public static final String RES_CODE_CANAGENT_NOTAGENT_MSG = "您还不能申请该产品合伙人";

    public static final String RES_CODE_CANAGENT_ALREADY_AGENT = "100708";
    public static final String RES_CODE_CANAGENT_ALREADY_AGENT_MSG = "您已经申请该产品的合伙人";

    public static final String RES_CODE_UPAPPLY_PUSER_INVALID = "100709";
    public static final String RES_CODE_UPAPPLY_PUSER_INVALID_MSG = "其他";

    public static final String RES_CODE_UPAPPLY_PHONENUM_NULL = "100710";
    public static final String RES_CODE_UPAPPLY_PHONENUM_NULL_MSG = "电话号码为空";

    public static final String RES_CODE_UPAPPLY_PHONENUM_INVALID = "100711";
    public static final String RES_CODE_UPAPPLY_PHONENUM_INVALID_MSG = "手机号码格式不正确";

    public static final String RES_CODE_UPAPPLY_PHONENUM_NOTKNOWN = "100712";
    public static final String RES_CODE_UPAPPLY_PHONENUM_NOTKNOWN_MSG = "手机号尚未注册";

    public static final String RES_CODE_UPAPPLY_PHONENUMUSER_NOTAGENT = "100713";
    public static final String RES_CODE_UPAPPLY_PHONENUMUSER_NOTAGENT_MSG = "手机号用户尚未代理该产品";

    public static final String RES_CODE_UPAPPLY_AGENTLEVELID_INVALID = "100714";
    public static final String RES_CODE_UPAPPLY_AGENTLEVELID_INVALID_MSG = "代理等级id不正确";

    public static final String RES_CODE_UPAPPLY_WXID_INVALID = "100715";
    public static final String RES_CODE_UPAPPLY_WXID_INVALID_MSG = "微信号为空";

    /**
     * 实名认证
     */
    public static final String RES_CODE_IDENTITY_AUTH_FAIL = "100801";
    public static final String RES_CODE_IDENTITY_AUTH_FAIL_MSG = "实名认证提交失败";
    public static final String RES_CODE_UPLOAD_IDENTITY_FAIL = "100802";
    public static final String RES_CODE_UPLOAD_IDENTITY_FAIL_MSG = "上传身份证失败";

    /**
     * 微信支付
     */
    public static final String RES_CODE_WXPAY_ORDERCODE_NULL = "900101";
    public static final String RES_CODE_WXPAY_ORDERCODE_NULL_MSG = "ordercode为空";

    public static final String RES_CODE_WXPAY_ORDERCODE_NOTVALID = "900102";
    public static final String RES_CODE_WXPAY_ORDERCODE_NOTVALID_MSG = "ordercode不合法";

    public static final String RES_CODE_WXPAY_PREORDER_FAIL = "900105";
    public static final String RES_CODE_WXPAY_PREORDER_FAIL_MSG = "微信预付单下单失败";

}
