package com.masiis.shop.web.platform.task;

import com.alibaba.druid.support.json.JSONParser;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.web.platform.utils.SpringRedisUtil;
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
            String accessTocken = SpringRedisUtil.get("jsapi_access_tocken", String.class);
            if(accessTocken == null){
                log.info("从redis获取的jsapi_access_tocken=null");
                accessTocken = new JsapiAccessTokenTask().requestToken();
            }
            log.info("[jsapi_access_tocken="+accessTocken+"]");

            jsapiTicketUrl = WxConsPF.URL_JSSDK_JSAPI_TICKET
                            + "?access_token=" + accessTocken
                            + "&type=jsapi";
            log.info("开始请求jsapi票据jsapi_ticket中......");
            jsonResult = HttpClientUtils.httpGet(jsapiTicketUrl);
            log.info("请求jsapi票据jsapi_ticket返回的结果[jsonResult="+jsonResult+"]");

            Map<String, Object> resultMap = new JSONParser(jsonResult).parseMap();

            if("0".equals(resultMap.get("errcode").toString()) && "ok".equals(resultMap.get("errmsg").toString())){
                SpringRedisUtil.saveEx("jsapi_ticket", resultMap.get("ticket"), Long.parseLong(resultMap.get("expires_in").toString()));
            }else{
                throw new RuntimeException("请求jsapi票据失败!");
            }

            return (String)resultMap.get("ticket");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求jsapi票据失败![jsapiTicketUrl=" + jsapiTicketUrl + "][jsonResult=" + jsonResult + "]");
            return jsonResult;
        }
    }

    public static void main(String[] args){
        JsapiTicketTask jtt = new JsapiTicketTask();
        String ticket = jtt.requestTicket();
        System.out.println("ticket: " + ticket);
    }
}
