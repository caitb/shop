package com.masiis.shop.common.constant.tb;

import com.masiis.shop.common.util.AlipayPropertiesUtils;

/**
 * @Date 2016/9/3
 * @Author lzh
 */
public class AlipayConsAPP {

    public static final String APP_ID = AlipayPropertiesUtils.getStringValue("alipay.conf.APP_ID");

    public static final String APP_PRIVATE_KEY = AlipayPropertiesUtils.getStringValue("alipay.conf.APP_PRIVATE_KEY");

    public static final String APP_PRIVATE_KEY_PKCS8 = AlipayPropertiesUtils.getStringValue("alipay.conf.APP_PRIVATE_KEY_PKCS8");

    public static final String APP_PUBLIC_KEY = AlipayPropertiesUtils.getStringValue("alipay.conf.APP_PUBLIC_KEY");

    public static final String ALIPAY_PUBLIC_KEY = AlipayPropertiesUtils.getStringValue("alipay.conf.ALIPAY_PUBLIC_KEY");

    public static final String CHARSET = AlipayPropertiesUtils.getStringValue("alipay.conf.CHARSET");

    public static final String PAY_PRODUCT_CODE = AlipayPropertiesUtils.getStringValue("alipay.conf.PAY_PRODUCT_CODE");

    public static final String SIGN_TYPE = AlipayPropertiesUtils.getStringValue("alipay.conf.SIGN_TYPE");

    public static final String PAY_VERSION = AlipayPropertiesUtils.getStringValue("alipay.conf.PAY_VERSION");

    public static final String PAY_NOTIFY_URL = AlipayPropertiesUtils.getStringValue("alipay.conf.PAY_NOTIFY_URL");

    public static final String PAY_SELLER_ID = AlipayPropertiesUtils.getStringValue("alipay.conf.PAY_SELLER_ID");


    /**===========================================================================================================*/
    /**
     * 订单支付成功
     */
    public static final String PAY_RESULT_STATUS_SUCCESS = "9000";
}
