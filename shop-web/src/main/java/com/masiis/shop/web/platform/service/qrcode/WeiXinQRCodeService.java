package com.masiis.shop.web.platform.service.qrcode;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.dao.mall.user.SfUserShareParamMapper;
import com.masiis.shop.dao.po.SfUserShareParam;
import com.masiis.shop.web.platform.utils.SpringRedisUtil;
import com.masiis.shop.web.platform.utils.wx.WxCredentialUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 带参数微信公众号二维码
 * Created by cai_tb on 16/5/6.
 */
@Service
public class WeiXinQRCodeService {

    private final static Log log = LogFactory.getLog(WeiXinQRCodeService.class);

    @Resource
    private SfUserShareParamMapper sfUserShareParamMapper;

    /**
     * 创建带代理商参数到公众号二维码
     * @param pfUserSkuId
     * @return
     */
    public String createAgentQRCode(Integer pfUserSkuId){
        String access_token = WxCredentialUtils.getInstance().getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET);
        String jsonParam = "{\"action_name\": \"QR_SCENE\", \"expire_seconds\": \"2592000\", \"action_info\": {\"scene\": {\"scene_id\": \""+pfUserSkuId+"\"}}}";

        log.info("[WxConsPF.APPID="+WxConsPF.APPID+"], [WxConsPF.APPSECRET="+WxConsPF.APPSECRET+"]");
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

    /**
     * 创建带特定参数的公众号二维码(用于分享店铺或商品)
     * @param comUserId
     * @param shopId
     * @param skuId
     * @return
     */
    public String createShopOrSkuQRCode(Long comUserId, Long shopId, Integer skuId){
        Long scene_id = null;

        SfUserShareParam condition = new SfUserShareParam();
        condition.setfUserId(comUserId);
        condition.setShopId(shopId);
        condition.setSkuId(skuId);

        SfUserShareParam sfUserShareParam = sfUserShareParamMapper.selectByCondition(condition);
        if(sfUserShareParam == null){
            condition.setCreateTime(new Date());
            sfUserShareParamMapper.insert(condition);
            scene_id = condition.getId();
        }else{
            scene_id = sfUserShareParam.getId();
        }

        String access_token = WxCredentialUtils.getInstance().getCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET);
        String jsonParam = "{\"action_name\": \"QR_SCENE\", \"expire_seconds\": \"2592000\", \"action_info\": {\"scene\": {\"scene_id\": \""+scene_id+"\"}}}";

        log.info("[WxConsPF.APPID="+WxConsPF.APPID+"], [WxConsPF.APPSECRET="+WxConsPF.APPSECRET+"]");
        log.info("开始请求二维码ticket:[scene_id="+scene_id+"][access_token="+access_token+"][jsonParam="+jsonParam+"]");
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
