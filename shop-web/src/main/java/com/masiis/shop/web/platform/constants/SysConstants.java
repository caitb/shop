package com.masiis.shop.web.platform.constants;

/**
 * Created by lzh on 2016/2/24.
 */
public class SysConstants {
    /**
     * cookie的名称
     */
    public static final String COOKIE_WX_ID_NAME = "wx_usr_coo";
    /**
     * 登录信息保存cookie加密密钥
     */
    public static final String COOKIE_AES_KEY = "p^5iMmJz!5q=awMT";
    /**
     * 登录信息保存cookie加密的盐
     */
    public static final String COOKIE_KEY_SALT = "b+1!7|Zw%79797+[";

    /**
     * 订单类型session
     */
    public static final String SESSION_ORDER_TYPE = "orderType";
    public static final String SESSION_TRIAL_ORDER_TYPE_VALUE = "zhifushiyong";//试用支付
    public static final String SESSION_PAY_ORDER_TYPE_VALUE = "zhifu";//支付

    /**
     * session
     * 当前订单的id和当前的订单的地址
     */
    public static final String SESSION_ORDER_Id="orderId";
    public static final String SESSION_ORDER_SELECTED_ADDRESS="orderSelectedAddress";
    public static final String SESSION_ORDER_SKU_ID="skuId";
    /**
     * 商品最小图片
     */
    public static final String INDEX_PRODUCT_IMAGE_MIN = "index_product_220_220_url";

    public static final String SESSION_LOGIN_USER_NAME = "comUser";
}
