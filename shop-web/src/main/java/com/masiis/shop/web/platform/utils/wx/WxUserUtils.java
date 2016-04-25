package com.masiis.shop.web.platform.utils.wx;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import com.fasterxml.jackson.databind.ser.impl.JsonSerializerMap;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComWxUser;
import com.masiis.shop.web.platform.beans.wxauth.WxUserInfo;
import com.masiis.shop.web.platform.constants.WxResCodeCons;
import com.masiis.shop.web.platform.service.user.WxUserService;
import com.masiis.shop.web.platform.utils.ApplicationContextUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date:2016/4/22
 * @auth:lzh
 */
public class WxUserUtils {
    private WxUserUtils(){}

    private static class Holder {
        private static WxUserUtils INSTANCE = new WxUserUtils();
    }

    public static WxUserUtils getInstance(){
        return Holder.INSTANCE;
    }

    private WxUserService wxUserService = (WxUserService) ApplicationContextUtil.getBean("wxUserService");

    /**
     * 判断用户是否关注麦链合伙人
     *
     * @param user
     * @return
     */
    public Boolean isUserForcusPF(ComUser user){
        ComWxUser wxUser = wxUserService.getUserByUnionidAndAppid(user.getWxUnionid(), WxConsPF.APPID);
        if(wxUser == null){
            return false;
        }
        String url = WxConsPF.URL_CGIBIN_USERINFO
                + "?access_token=" + WxCredentialUtils.getInstance().getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET)
                + "&openid" + wxUser.getOpenid()
                + "&lang=zh_CN";
        String result = HttpClientUtils.httpGet(url);
        HashMap<String, Object> res = JSONObject.parseObject(result, HashMap.class);
        String subscribe = (String) res.get("subscribe");
        if(StringUtils.isNotBlank(subscribe)){
            if(subscribe.equals("1")){
                return true;
            }
            if(subscribe.equals("0")){
                return false;
            }
            throw new BusinessException("请求异常:" + result);
        }
        String errcode = (String) res.get("errcode");
        if(StringUtils.isNotBlank(errcode)
                || errcode.equals(WxResCodeCons.ACCESS_TOKEN_INVALID)
                || errcode.equals(WxResCodeCons.ACCESS_TOKEN_INVALID_OR_NOT_LATEST)
                || errcode.equals(WxResCodeCons.ACCESS_TOKEN_TIMEOUT)){
            // access_token失效
            String newToken = WxCredentialUtils.getInstance()
                    .refreshCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET);
            String urlNew = WxConsPF.URL_CGIBIN_USERINFO
                    + "?access_token=" + newToken
                    + "&openid" + wxUser.getOpenid()
                    + "&lang=zh_CN";
            String resultNew = HttpClientUtils.httpGet(urlNew);
            HashMap<String, Object> resNew = JSONObject.parseObject(result, HashMap.class);
            String subscribeNew = (String) res.get("subscribe");
            if(StringUtils.isNotBlank(subscribeNew)){
                if(subscribeNew.equals("1")){
                    return true;
                }
                if(subscribeNew.equals("0")){
                    return false;
                }
                throw new BusinessException("请求异常:" + result);
            }
        }
        throw new BusinessException("请求异常:" + result);
    }

    /**
     * 判断用户是否关注麦链商城
     *
     * @param openId
     * @return
     */
    public Boolean isUserForcusSF(ComUser user){
        ComWxUser wxUser = wxUserService.getUserByUnionidAndAppid(user.getWxUnionid(), WxConsPF.APPID);
        if(wxUser == null){
            return false;
        }

        String url = WxConsSF.URL_CGIBIN_USERINFO
                + "?access_token=" + WxCredentialUtils.getInstance().getCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET)
                + "&openid" + wxUser.getOpenid()
                + "&lang=zh_CN";
        String result = HttpClientUtils.httpGet(url);
        HashMap<String, Object> res = JSONObject.parseObject(result, HashMap.class);
        String subscribe = (String) res.get("subscribe");
        if(StringUtils.isNotBlank(subscribe)){
            if(subscribe.equals("1")){
                return true;
            }
            if(subscribe.equals("0")){
                return false;
            }
            throw new BusinessException("请求异常:" + result);
        }
        String errcode = (String) res.get("errcode");
        if(StringUtils.isNotBlank(errcode)
                || errcode.equals(WxResCodeCons.ACCESS_TOKEN_INVALID)
                || errcode.equals(WxResCodeCons.ACCESS_TOKEN_INVALID_OR_NOT_LATEST)
                || errcode.equals(WxResCodeCons.ACCESS_TOKEN_TIMEOUT)){
            // access_token失效
            String newToken = WxCredentialUtils.getInstance()
                    .refreshCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET);
            String urlNew = WxConsSF.URL_CGIBIN_USERINFO
                    + "?access_token=" + newToken
                    + "&openid" + wxUser.getOpenid()
                    + "&lang=zh_CN";
            String resultNew = HttpClientUtils.httpGet(urlNew);
            HashMap<String, Object> resNew = JSONObject.parseObject(result, HashMap.class);
            String subscribeNew = (String) res.get("subscribe");
            if(StringUtils.isNotBlank(subscribeNew)){
                if(subscribeNew.equals("1")){
                    return true;
                }
                if(subscribeNew.equals("0")){
                    return false;
                }
                throw new BusinessException("请求异常:" + result);
            }
        }
        throw new BusinessException("请求异常:" + result);
    }

    public static void main(String... args){
        String aaa = "{\"sus\":1,\"mmm\":\"不错\"}";
        HashMap<String, Object> res = JSONObject.parseObject(aaa, HashMap.class);
        System.out.println(res.get("sus"));
        System.out.println(res.get("mmm"));
    }
}
