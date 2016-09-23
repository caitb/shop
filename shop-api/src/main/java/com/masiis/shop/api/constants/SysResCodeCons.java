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

    public static final String RES_CODE_PAGE_LAST = "-3";
    public static final String RES_CODE_PAGE_LAST_MSG = "暂无更多数据";
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

    public static final String RES_CODE_REQ_IS_AGENT_ERROR = "100008";
    public static final String RES_CODE_REQ_IS_AGENT_ERROR_MSG = "不是合伙人";


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

    public static final String RES_CODE_PHONENUM_ISIN_BLACKLIST = "100205";
    public static final String RES_CODE_PHONENUM_ISIN_BLACKLIST_MSG = "该手机号无法使用，请使用其他手机号注册，或联系客服400961616了解详情。";


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

    public static final String RES_CODE_UPAPPLY_PHONENUMUSER_LASTAGENT = "100716";
    public static final String RES_CODE_UPAPPLY_PHONENUMUSER_LASTAGENT_MSG = "手机号用户是该产品最后一级,不能作为上级";

    public static final String RES_CODE_CANAGENT_ALREADY_TEMPAGENT = "100717";
    public static final String RES_CODE_CANAGENT_ALREADY_TEMPAGENT_MSG = "您已经是该产品的临时合伙人";

    public static final String RES_CODE_UPAPPLY_AGENTBIND_FAIL = "100718";
    public static final String RES_CODE_UPAPPLY_AGENTBIND_FAIL_MSG = "绑定合伙人关系失败";

    /**
     * 实名认证
     */
    public static final String RES_CODE_IDENTITY_AUTH_FAIL = "100801";
    public static final String RES_CODE_IDENTITY_AUTH_FAIL_MSG = "实名认证提交失败";
    public static final String RES_CODE_UPLOAD_IDENTITY_FAIL = "100802";
    public static final String RES_CODE_UPLOAD_IDENTITY_FAIL_MSG = "上传身份证失败";

    /**
     * 上传头像
     */
    public static final String RES_CODE_UPLOAD_IMG_FAIL = "100809";
    public static final String RES_CODE_UPLOAD_IMG_FAIL_MSG = "上传头像失败";

    /**
     * 申请试用
     */
    public static final String RES_CODE_TRIAL_APPLY_ADDRESS_FAIL = "100901";
    public static final String RES_CODE_TRIAL_APPLY_ADDRESS_FAIL_MSG = "申请试用获取地址失败";
    public static final String RES_CODE_TRIAL_APPLY_GET_ORDER_DETAIL_FAIL = "100902";
    public static final String RES_CODE_TRIAL_APPLY_GET_ORDER_DETAIL_FAIL_MSG = "申请试用支付成功获取订单详情失败";
    public static final String RES_CODE_TRIAL_APPLY_FAIL = "100903";
    public static final String RES_CODE_TRIAL_APPLY_FAIL_MSG = "申请使用微信支付失败";

    /**
     * 补货
     */
    public static final String RES_CODE_NO_SEND_TYPE = "101001";
    public static final String RES_CODE_NO_SEND_TYPE_MSG = "您还没有确定拿货状态不能补货";

    public static final String RES_CODE_SUPPLEMENT_ORDERID_ERROR = "101002";
    public static final String RES_CODE_SUPPLEMENT_ORDERID_ERROR_MSG = "补货订单号错误";

    public static final String RES_CODE_SUPPLEMENT_ORDERTYPE_ERROR = "101003";
    public static final String RES_CODE_SUPPLEMENT_ORDERTYPE_ERROR_MSG = "订单类型不是补货类型";

    /**
     * 消息中心
     */
    public static final String RES_CODE_MESSAGE_PARAM_ERROR = "101201";
    public static final String RES_CODE_MESSAGE_PARAM_ERROR_MSG = "消息请求参数不正确";

    public static final String RES_CODE_MESSAGE_NOT_AGENT = "101202";
    public static final String RES_CODE_MESSAGE_NOT_AGENT_MSG = "该用户还不是代理商";

    public static final String RES_CODE_MESSAGE_PARAM_NOTENOUGH = "101203";
    public static final String RES_CODE_MESSAGE_PARAM_NOTENOUGH_MSG = "请求参数不完整";

    public static final String RES_CODE_MESSAGE_HAS_NO_CHILD = "101204";
    public static final String RES_CODE_MESSAGE_HAS_NO_CHILD_MSG = "该合伙人暂无下级合伙人";

    public static final String RES_CODE_MESSAGE_HAS_NO_DATA = "101205";
    public static final String RES_CODE_MESSAGE_HAS_NO_DATA_MSG = "暂无更多数据";

    public static final String RES_CODE_MESSAGE_FROM_USER_ERROR = "101206";
    public static final String RES_CODE_MESSAGE_FROM_USER_ERROR_MSG = "消息来源用户不正确";

    public static final String RES_CODE_MESSAGE_TO_USER_ERROR = "101207";
    public static final String RES_CODE_MESSAGE_TO_USER_ERROR_MSG = "接收消息用户类型不正确";


    /**
     * 代理注册
     */
    public static final String RES_CODE_AGENT_NOT_PID = "101301";
    public static final String RES_CODE_AGENT_NOT_PID_MSG = "上级用户id不正确";

    public static final String RES_CODE_AGENT_SOURCE_ERROR = "101302";
    public static final String RES_CODE_AGENT_SOURCE_ERROR_MSG = "用户注册来源不正确";

    public static final String RES_CODE_AGENT_BRANDID_ERROR = "101303";
    public static final String RES_CODE_AGENT_BRANDID_ERROR_MSG = "指定品牌主打商品id不正确";

    public static final String RES_CODE_AGENT_AGENTLEVEL_ERROR = "101304";
    public static final String RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG = "指定合伙等级id不正确";

    public static final String RES_CODE_AGENT_AGENT_SKU_ERROR = "101305";
    public static final String RES_CODE_AGENT_AGENT_SKU_ERROR_MSG = "上级不是该商品的合伙人";

    public static final String RES_CODE_AGENT_ALREADY_AGENT_SKU = "101306";
    public static final String RES_CODE_AGENT_ALREADY_AGENT_SKU_MSG = "该用户已经是该sku的合伙人";

    public static final String RES_CODE_AGENT_ALREADY_HAS_AGENTORDER = "101307";
    public static final String RES_CODE_AGENT_ALREADY_HAS_AGENTORDER_MSG = "该用户已经有合伙人订单";

    public static final String RES_CODE_AGENT_BORDER_ID_ERROR = "101308";
    public static final String RES_CODE_AGENT_BORDER_ID_ERROR_MSG = "订单id不正确";

    public static final String RES_CODE_AGENT_BORDER_ORDERTYPE_ERROR = "101309";
    public static final String RES_CODE_AGENT_BORDER_ORDERTYPE_ERROR_MSG = "订单id不正确";

    public static final String RES_CODE_AGENT_BORDER_ZERO_PAY_ERROR = "101310";
    public static final String RES_CODE_AGENT_BORDER_ZERO_PAY_ERROR_MSG = "不是0元订单";

    /**
     * 升级
     */
    public static final String RES_CODE_UPGRADE_ORDER_ID_ERROR = "101401";
    public static final String RES_CODE_UPGRADE_ORDER_ID_ERROR_MSG = "升级订单id不正确";

    public static final String RES_CODE_UPGRADE_ORDER_NOT_EXISTS = "101402";
    public static final String RES_CODE_UPGRADE_ORDER_NOT_EXISTS_MSG = "该升级订单不存在";

    public static final String RES_CODE_UPGRADE_ORDER_TYPE_ERROR = "101403";
    public static final String RES_CODE_UPGRADE_ORDER_TYPE_ERROR_MSG = "该订单类型不正确";

    public static final String RES_CODE_UPGRADE_ORDER_USER_NOTMATCH = "101404";
    public static final String RES_CODE_UPGRADE_ORDER_USER_NOTMATCH_MSG = "该订单不属于本人";

    /**
     * 搜索手机号
     */
    public static final String RES_CODE_PHONESEARCH_PHONENUM_ISIN_BLACKLIST = "101501";
    public static final String RES_CODE_PHONESEARCH_PHONENUM_ISIN_BLACKLIST_MSG = "查无此手机号用户";

    /**
     * 微信支付
     */
    public static final String RES_CODE_WXPAY_ORDERCODE_NULL = "900101";
    public static final String RES_CODE_WXPAY_ORDERCODE_NULL_MSG = "ordercode为空";

    public static final String RES_CODE_WXPAY_ORDERCODE_NOTVALID = "900102";
    public static final String RES_CODE_WXPAY_ORDERCODE_NOTVALID_MSG = "ordercode不合法";

    public static final String RES_CODE_WXPAY_PREORDER_FAIL = "900105";
    public static final String RES_CODE_WXPAY_PREORDER_FAIL_MSG = "微信预付单下单失败";

    public static final String RES_CODE_WXPAY_ORDER_PAID = "900106";
    public static final String RES_CODE_WXPAY_ORDER_PAID_MSG = "该订单已支付,无需再支付";

    /**
     * 支付宝支付
     */
    public static final String RES_CODE_ALIPAY_ORDERCODE_NULL = "900201";
    public static final String RES_CODE_ALIPAY_ORDERCODE_NULL_MSG = "orderCode为空";

    public static final String RES_CODE_ALIPAY_ORDERCODE_ERROR = "900202";
    public static final String RES_CODE_ALIPAY_ORDERCODE_ERROR_MSG = "orderCode错误";

    public static final String RES_CODE_ALIPAY_PAYCHECK_PARAMERROR = "900203";
    public static final String RES_CODE_ALIPAY_PAYCHECK_PARAMERROR_MSG = "支付宝支付同步结果校验参数不正确";

    public static final String RES_CODE_ALIPAY_PAYCHECK_RESULT_FAIL = "900204";
    public static final String RES_CODE_ALIPAY_PAYCHECK_RESULT_FAIL_MSG = "支付宝支付同步结果校验支付失败";

    public static final String RES_CODE_ALIPAY_PAYCHECK_SIGNERROR = "900205";
    public static final String RES_CODE_ALIPAY_PAYCHECK_SIGNERROR_MSG = "支付宝支付同步结果校验签名失败";

    public static final String RES_CODE_ALIPAY_PAYCHECK_SIGNINFOERROR = "900206";
    public static final String RES_CODE_ALIPAY_PAYCHECK_SIGNINFOERROR_MSG = "支付宝支付同步结果校验签名信息错误";

    public static final String RES_CODE_ALIPAY_PAYCHECK_PAYINFOERROR = "900207";
    public static final String RES_CODE_ALIPAY_PAYCHECK_PAYINFOERROR_MSG = "支付宝支付同步结果校验支付信息不正确";

    public static final String RES_CODE_ALIPAY_PAYCHECK_USERCANCEL = "900208";
    public static final String RES_CODE_ALIPAY_PAYCHECK_USERCANCEL_MSG = "用户取消支付";



    public static final String PHONE_DUPLICATE = "手机号重复";

    public static final String PHONE_IILEAGAL = "手机号非法";

    public static final String WXCODE_IILEAGAL = "微信号非法";

    public static final String NICHENG_IILEAGAL = "该昵称不合法";

    /**
     * 店铺信息
     */
    public static final String RES_CODE_SHOP_NULL = "110000";
    public static final String RES_CODE_SHOP_NULL_MSG = "该用户没有自己的店铺";

    /**
     * 绑定银行卡
     */
    public static final String RES_CODE_BANK_BIND_REP = "120000";
    public static final String RES_CODE_BANK_BIND_REP_MSG = "你已绑定该银行卡，不能再次绑定";

}
