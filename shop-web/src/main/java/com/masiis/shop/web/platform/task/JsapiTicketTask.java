package com.masiis.shop.web.platform.task;

import com.alibaba.druid.support.json.JSONParser;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.web.platform.constants.WxConstants;
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
                accessTocken = new JsapiAccessTokenTask().requestToken();
            }
            jsapiTicketUrl = WxConstants.URL_JSSDK_JSAPI_TICKET
                            + "?access_token=" + accessTocken
                            + "&type=jsapi";
            jsonResult = HttpClientUtils.httpGet(jsapiTicketUrl);
            Map<String, Object> resultMap = new JSONParser(jsonResult).parseMap();

            if("0".equals(resultMap.get("errcode")) && "ok".equals(resultMap.get("errmsg"))){
                SpringRedisUtil.saveEx("jsapi_ticket", resultMap.get("ticket"), Long.parseLong((String)resultMap.get("expires_in")));
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
