package com.masiis.shop.common.constant.wx;

import com.masiis.shop.common.util.WxPropertiesUtils;

/**
 * @Date 2016/5/21
 * @Auther lzh
 */
public class WxConsAPP {
    /**
     * APP的appid
     */
    public static final String APPID = WxPropertiesUtils.getStringValue("wx.conf.app.APPID");
    /**
     * APP的secret
     */
    public static final String APPSECRET = WxPropertiesUtils.getStringValue("wx.conf.app.APPSECRET");
    /**
     * APP的支付商户号
     */
    public static final String WX_PAY_MCHID = WxPropertiesUtils.getStringValue("wx.conf.app.WX_PAY_MCHID");
    /**
     * APP微信的签名密钥
     */
    public static final String WX_PAY_SIGNKEY = WxPropertiesUtils.getStringValue("wx.conf.app.WX_PAY_SIGN_KEY");
    /**
     * APP支付异步回调地址
     */
    public static final String WX_PAY_URL_UNIORDER_NOTIFY = WxPropertiesUtils.getStringValue("wx.conf.app.WX_PAY_URL_UNIORDER_NOTIFY");;
}
