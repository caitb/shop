package com.masiis.shop.web.mall.service.shop;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.web.mall.constants.WxConstants;
import com.masiis.shop.web.mall.task.JsapiTicketTask;
import com.masiis.shop.web.mall.utils.JSSDKUtil;
import com.masiis.shop.web.mall.utils.SpringRedisUtil;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Map;

/**
 * 获取调用微信js所需数据
 * Created by cai_tb on 16/4/20.
 */
@Service
public class JSSDKService {

    private final static Log log = LogFactory.getLog(JSSDKService.class);

    /**
     * 当前http请求路径(包括请求参数)
     * @param curUrl
     * @return
     */
    public Map<String, String> requestJSSDKData(String curUrl){
        String jsapi_ticket = SpringRedisUtil.get("mall_jsapi_ticket", String.class);
        if(jsapi_ticket == null){
            log.info("从redis获取的mall_jsapi_ticket=null");
            jsapi_ticket = new JsapiTicketTask().requestTicket();
        }

        Map<String, String> shareMap = JSSDKUtil.sign(jsapi_ticket, curUrl);
                            shareMap.put("appId", WxConstants.APPID);

        return shareMap;
    }
}
