package com.masiis.shop.common.util.wx;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.beans.wx.config.WxNoticConfig;
import com.masiis.shop.common.beans.wx.notice.WxNoticeReq;
import com.masiis.shop.common.beans.wx.notice.WxNoticeNotPay;
import com.masiis.shop.common.beans.wx.notice.WxNoticeRes;
import com.masiis.shop.common.util.HttpClientUtils;

/**
 * @Date:2016/4/21
 * @auth:lzh
 */
public class WxNoticeUtils {
    /**
     * 发送微信未支付提醒
     *
     * @param noPay
     * @return
     */
    public static WxNoticeRes userNoticByNoPay(WxNoticConfig config,
                                               WxNoticeReq<WxNoticeNotPay> noPay){
        String url = config.getUrl() + "?access_token=" + config.getNoticeToken();
        String result = HttpClientUtils.httpPost(url, JSONObject.toJSONString(noPay));
        WxNoticeRes res = JSONObject.parseObject(result, WxNoticeRes.class);
        return res;
    }
}
