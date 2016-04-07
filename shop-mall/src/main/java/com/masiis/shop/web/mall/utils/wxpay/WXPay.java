package com.masiis.shop.web.mall.utils.wxpay;

/**
 * Created by lzh on 2016/3/9.
 */

import com.masiis.shop.web.mall.beans.pay.wxpay.Configure;
import com.masiis.shop.web.mall.beans.pay.wxpay.UnifiedOrderReq;
import com.masiis.shop.web.mall.constants.WxConstants;
import com.masiis.shop.web.pay.service.wxpay.UniOrderService;

/**
 * SDK总入口
 */
public class WXPay {
    static {
        initSDKConfiguration(WxConstants.WX_PAY_SIGN_KEY, WxConstants.APPID, WxConstants.WX_PAY_MCHID, null, null, null);
    }

    /**
     * 初始化SDK依赖的几个关键配置
     * @param key 签名算法需要用到的秘钥
     * @param appID 公众账号ID
     * @param mchID 商户ID
     * @param sdbMchID 子商户ID，受理模式必填
     * @param certLocalPath HTTP证书在服务器中的路径，用来加载证书用
     * @param certPassword HTTP证书的密码，默认等于MCHID
     */
    public static void initSDKConfiguration(String key,String appID,String mchID,String sdbMchID,String certLocalPath,String certPassword){
        Configure.setKey(key);
        Configure.setAppID(appID);
        Configure.setMchID(mchID);
        Configure.setSubMchID(sdbMchID);
        Configure.setCertLocalPath(certLocalPath);
        Configure.setCertPassword(certPassword);
    }

    /**
     * 请求预订单服务
     * @param unifiedOrderReq 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public static String requestUniOrderService(UnifiedOrderReq unifiedOrderReq) throws Exception{
        return new UniOrderService().request(unifiedOrderReq);
    }

}
