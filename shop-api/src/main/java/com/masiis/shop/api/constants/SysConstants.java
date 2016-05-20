package com.masiis.shop.api.constants;

import com.masiis.shop.common.util.PropertiesUtils;

/**
 * @Date 2016/5/3
 * @Auther lzh
 */
public class SysConstants {

    /**
     * 商品最小图片
     */
    public static final String INDEX_PRODUCT_IMAGE_MIN = "index_product_220_220_url";

    /**
     * 商品等级图片
     */
    public static final String  AGENT_LEVEL_PRODUCT_ICON_URL = PropertiesUtils.getStringValue("agent_level_product_icon_url");


    /**
     * 订单类型session
     */
    public static final String SESSION_ORDER_TYPE = "orderType";
    public static final String SESSION_TRIAL_ORDER_TYPE_VALUE = "zhifushiyong";//试用支付
    public static final String SESSION_PAY_ORDER_TYPE_VALUE = "zhifu";//支付
    public static final String SESSION_ORDER_TAKE_GOODS_VALUE = "takeGoods";//拿货
    public static final String SESSION_MANAGE_GOODS_TAKE_GOODS_VALUE = "manageGoodsTakeGoods";//管理商品拿货
    public static final String SESSION_MALL_CONFIRM_ORDER = "mallConfirmOrder";//小铺确定订单

    /**
     * session
     * 当前订单的id和当前的订单的地址
     */
    public static final String SESSION_ORDER_Id = "orderId";
    public static final String SESSION_ORDER_SELECTED_ADDRESS = "orderSelectedAddress";
    public static final String SESSION_ORDER_SKU_ID = "skuId";
    public static final String SESSION_PF_USER_SKU_STOCK_ID = "pfUserSkuStockId";
    public static final String SESSION_MALL_CONFIRM_ORDER_SHOP_ID = "mallConfirmOrderShopId";//小铺确认订单界面的小铺id
}
