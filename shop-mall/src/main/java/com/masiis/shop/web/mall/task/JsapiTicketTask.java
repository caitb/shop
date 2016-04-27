package com.masiis.shop.web.mall.task;

import com.alibaba.druid.support.json.JSONParser;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.web.mall.utils.SpringRedisUtil;
import com.masiis.shop.web.mall.utils.wx.WxCredentialUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * 获取jsapi票据
 * Created by cai_tb on 16/3/17.
 */
public class JsapiTicketTask {

    private final Log log = LogFactory.getLog(JsapiTicketTask.class);

    public String requestTicket(){
        String jsapiTicketUrl = null;
        String jsonResult = null;

        try {
            String accessTocken = SpringRedisUtil.get("mall_jsapi_access_tocken", String.class);
            if(accessTocken == null){
                log.info("从redis获取的mall_jsapi_access_tocken=null");
                accessTocken = WxCredentialUtils.getInstance()
                        .getCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET);
            }
            log.info("[mall_jsapi_access_tocken="+accessTocken+"]");

            jsapiTicketUrl = WxConsSF.URL_JSSDK_JSAPI_TICKET
                            + "?access_token=" + accessTocken
                            + "&type=jsapi";
            log.info("开始请求jsapi票据jsapi_ticket中......");
            jsonResult = HttpClientUtils.httpGet(jsapiTicketUrl);
            log.info("请求jsapi票据jsapi_ticket返回的结果[jsonResult="+jsonResult+"]");

            Map<String, Object> resultMap = new JSONParser(jsonResult).parseMap();

            if("0".equals(resultMap.get("errcode").toString()) && "ok".equals(resultMap.get("errmsg").toString())){
                SpringRedisUtil.saveEx("mall_jsapi_ticket", resultMap.get("ticket"), Long.parseLong(resultMap.get("expires_in").toString()));
            }else{
                throw new RuntimeException("mall请求jsapi票据失败!");
            }

            return (String)resultMap.get("ticket");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("mall请求jsapi票据失败![jsapiTicketUrl=" + jsapiTicketUrl + "][jsonResult=" + jsonResult + "]");
            return jsonResult;
        }
    }

    public static void main(String[] args){
        JsapiTicketTask jtt = new JsapiTicketTask();
        String ticket = jtt.requestTicket();
        System.out.println("ticket: " + ticket);
    }
}
