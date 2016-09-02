package com.masiis.shop.api.utils.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.masiis.shop.common.util.AlipayPropertiesUtils;

/**
 * @Date 2016/9/2
 * @Author lzh
 */
public class AlipayClientUtils {
    private static class Holder {
        private static final AlipayClient INSTANCE = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                AlipayPropertiesUtils.getStringValue("alipay.conf.APP_ID"),
                AlipayPropertiesUtils.getStringValue("alipay.conf.APP_PRIVATE_KEY_PKCS8"),
                "json",
                AlipayPropertiesUtils.getStringValue("alipay.conf.CHARSET"),
                AlipayPropertiesUtils.getStringValue("alipay.conf.ALIPAY_PUBLIC_KEY"));
    }
    private AlipayClientUtils(){}
    // 单例懒加载
    public static final AlipayClient getAlipayClientInstance() {

        return Holder.INSTANCE;
    }
}
