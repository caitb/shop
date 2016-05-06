package com.masiis.shop.web.platform.service.qrcode;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.web.platform.utils.SpringRedisUtil;
import com.masiis.shop.web.platform.utils.wx.WxCredentialUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 带参数微信公众号二维码
 * Created by cai_tb on 16/5/6.
 */
@Service
public class WeiXinQRCodeService {

    private final static Log log = LogFactory.getLog(WeiXinQRCodeService.class);

    /**
     * 创建带代理商参数到公众号二维码
     * @param pfUserSkuId
     * @return
     */
    public String createAgentQRCode(Integer pfUserSkuId){
        String access_token = WxCredentialUtils.getInstance().getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET);
        String jsonParam = "{\"action_name\": \"QR_SCENE\", \"expire_seconds\": \"2592000\", \"action_info\": {\"scene\": {\"scene_id\": \""+123+"\", \"pfUserSkuId\": \""+pfUserSkuId+"\"}}}";

        log.info("开始请求二维码ticket:[pfUserSkuId="+pfUserSkuId+"][access_token="+access_token+"][jsonParam="+jsonParam+"]");
        String result = HttpClientUtils.httpPost(WxConsPF.URL_CREATE_WEIXIN_PUBLIC_NUMBER_QRCODE+"?access_token="+access_token, jsonParam);
        log.info("请求二维码ticket返回结果[result="+result+"]");

        Map<String, Object> resultMap = new JSONParser(result).parseMap();
        if(resultMap.get("expire_seconds") != null){
            String ticket = (String)resultMap.get("ticket");
            return WxConsPF.URL_WEIXIN_PUBLIC_NUMBER_QRCODE + "?ticket=" + ticket;
        }

        return result;
    }
}
