package com.masiis.shop.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.util.CookieUtils;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.web.common.beans.AccessTokenRes;
import com.masiis.shop.web.common.beans.ErrorRes;
import com.masiis.shop.web.common.beans.RedirectParam;
import com.masiis.shop.web.common.constants.SysConstants;
import com.masiis.shop.web.common.constants.WxAuthConstants;
import com.masiis.shop.web.redis.utils.SpringRedisUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by lzh on 2016/2/23.
 */
@Controller
@RequestMapping("/verify")
public class VerifyController {
    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping("/actk")
    public String getCode(HttpServletRequest request, HttpServletResponse response,
                        String code, String state){
        if(StringUtils.isBlank(code)){
            request.setAttribute("state", state);
            return "";
        }
        if(StringUtils.isBlank(state)){
            return "";
        }
        // 获取access_token
        System.out.println("开始获取access_token...");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                        + "appid=wx7c874d5a102dccef&"
                        + "secret=67f21b9a5df948c9dbf203393d8fb1a1&"
                        + "code=" + code + "&"
                        + "grant_type=authorization_code";
        String result = HttpClientUtils.httpGet(url);
        System.out.println("result:" + result);
        AccessTokenRes res = JSONObject.parseObject(result, AccessTokenRes.class);
        ErrorRes resErr = JSONObject.parseObject(result, ErrorRes.class);
        if(StringUtils.isBlank(resErr.getErrcode())){
            // 登录
            request.getSession().setAttribute("login", "login");
            try {
                return "redirect:" + URLDecoder.decode(state, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "index";
            }
        }else{
            // 请求失败

        }

        return "redirect:/";
    }

    @RequestMapping("/wxcheck")
    public String wxCheck(HttpServletRequest request, HttpServletResponse response, String state, RedirectAttributes attr) throws IOException {
        String basepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
        if(StringUtils.isNotBlank(state)) {
            // 解析state
            RedirectParam rp = null;
            try{
                rp = JSONObject.parseObject(URLDecoder.decode(state, "UTF-8"), RedirectParam.class);
            } catch (Exception e) {
                log.error("json解析错误:" + e.getMessage());
                rp = new RedirectParam();
            }
            if(StringUtils.isNotBlank(rp.getCode())
                    // 校验state参数完整性
                    && DigestUtils.sha1Hex(this.toString()).equals(rp.getSignCk())){
                Cookie cookie = CookieUtils.getCookie(request, SysConstants.COOKIE_WX_ID_NAME);
                if (cookie != null) {
                    String val = cookie.getValue();
                    String openid = null;
                    String token = null;
                    if (StringUtils.isNotBlank(val)
                            && StringUtils.isNotBlank(openid = SpringRedisUtil.get(val, String.class))
                            && StringUtils.isNotBlank(token = SpringRedisUtil.get(openid, String.class))) {
                        // 取得了openid和token,并进行验证有效期
                        String checkTokenUrl = WxAuthConstants.URL_CHECK_ACCESS_TOKEN
                                + "?access_token=" + token
                                + "&openid=" + openid;
                        try {
                            String result = HttpClientUtils.httpGet(checkTokenUrl);
                            ErrorRes resBean = JSONObject.parseObject(result, ErrorRes.class);
                            if (resBean != null && "0".equals(resBean.getErrcode())) {
                                // token仍有效,跳转目标链接
                                return "redirect:" + rp.getSurl();
                            } else if (resBean != null && "40014".equals(resBean.getErrcode())) {
                                // token失效,刷新token,成功或失败.成功,重定向目标页面,失败,重新授权

                            }
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }
            }
        }

        String redirect_url = WxAuthConstants.URL_AUTH
                + "?appid=" + WxAuthConstants.APPID
                + "&redirect_uri=" + URLEncoder.encode(basepath + WxAuthConstants.REDIECT_URI_GET_ACCESS_TOKEN, "UTF-8")
                + "&response_type=" + WxAuthConstants.RESPONSE_TYPE_AUTH
                + "&scope=" + WxAuthConstants.SCOPE_AUTH_USRINFO
                + "&&state=" + state
                + WxAuthConstants.TAILSTR_AUTH;

        // 向微信获取授权code
        return "redirect:" + redirect_url;
    }
}
