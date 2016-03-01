package com.masiis.shop.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.CookieUtils;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.web.common.beans.AccessTokenRes;
import com.masiis.shop.web.common.beans.ErrorRes;
import com.masiis.shop.web.common.beans.RedirectParam;
import com.masiis.shop.web.common.beans.RefreshTokenRes;
import com.masiis.shop.web.common.constants.SysConstants;
import com.masiis.shop.web.common.constants.WxAuthConstants;
import com.masiis.shop.web.common.constants.WxResCodeCons;
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
public class VerifyController extends BaseController{
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

        // 解析state,并验证有效性
        RedirectParam rp = null;
        try{
            rp = JSONObject.parseObject(URLDecoder.decode(state, "UTF-8"), RedirectParam.class);
        } catch (Exception e) {
            log.error("json解析错误:" + e.getMessage());
            rp = null;
        }
        // 获取access_token
        System.out.println("开始获取access_token...");
        String url = WxAuthConstants.URL_GET_ACCESS_TOKEN
                        + "?appid=" + WxAuthConstants.APPID
                        + "&secret=" + WxAuthConstants.APPSECRET
                        + "&code=" + code
                        + "&grant_type=" + WxAuthConstants.GRANT_TYPE_ACCESSTOKEN;
        String result = HttpClientUtils.httpGet(url);
        System.out.println("result:" + result);
        ErrorRes resErr = JSONObject.parseObject(result, ErrorRes.class);
        if(StringUtils.isBlank(resErr.getErrcode())){
            AccessTokenRes res = JSONObject.parseObject(result, AccessTokenRes.class);
            // 登录
            request.getSession().setAttribute("login", "login");
            try {
                return createRedirectRes(URLDecoder.decode(state, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "index";
            }
        }
        // 请求失败

        return "redirect:/";
    }

    @RequestMapping("/wxcheck")
    public String wxCheck(HttpServletRequest request, HttpServletResponse response, String state, RedirectAttributes attr) throws IOException {
        String basepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
        try{
            if(StringUtils.isBlank(state)) {
                throw new BusinessException("state为空,调用异常!");
            }

            // 解析state,并验证有效性
            RedirectParam rp = null;
            try{
                rp = JSONObject.parseObject(URLDecoder.decode(state, "UTF-8"), RedirectParam.class);
            } catch (Exception e) {
                log.error("json解析错误:" + e.getMessage());
                rp = null;
            }
            if(rp == null
                    || StringUtils.isBlank(rp.getCode())
                    // 校验state参数完整性
                    || !DigestUtils.sha1Hex(this.toString()).equals(rp.getSignCk())) {
                throw new BusinessException("state参数不正确,调用异常!");
            }

            // 验证cookie
            Cookie cookie = CookieUtils.getCookie(request, SysConstants.COOKIE_WX_ID_NAME);
            if (cookie == null) {
                throw new BusinessException("cookie为空!");
            }
            String val = cookie.getValue();
            String openid = null;
            String token = null;
            if (StringUtils.isBlank(val)
                    && StringUtils.isBlank(openid = SpringRedisUtil.get(val, String.class))
                    && StringUtils.isBlank(token = SpringRedisUtil.get(openid + "_token", String.class))) {
                throw new BusinessException("cookie中openid信息为空或者token无效!");
            }
            // 取得了openid和token,并进行验证有效期
            String checkTokenUrl = WxAuthConstants.URL_CHECK_ACCESS_TOKEN
                    + "?access_token=" + token
                    + "&openid=" + openid;
            try {
                String result = HttpClientUtils.httpGet(checkTokenUrl);
                ErrorRes resBean = JSONObject.parseObject(result, ErrorRes.class);
                if (resBean != null && "0".equals(resBean.getErrcode())) {
                    // token仍有效,进行登录操作,并跳转目标链接
                    return "redirect:" + rp.getSurl();
                } else if (resBean != null
                        && (WxResCodeCons.ACCESS_TOKEN_INVALID.equals(resBean.getErrcode())
                            || WxResCodeCons.ACCESS_TOKEN_TIMEOUT.equals(resBean.getErrcode()))) {
                    // token失效,刷新token,成功或失败.成功,重定向目标页面;失败,重新授权
                    String rftoken = SpringRedisUtil.get(openid + "_rftoken", String.class);
                    if(StringUtils.isBlank(rftoken)){
                        throw new BusinessException("token超时,且redis取不到refreshtoken!");
                    }
                    String rftoken_url = WxAuthConstants.URL_REFRESH_TOKEN
                                + "?appid=" + WxAuthConstants.APPID
                                + "&grant_type=" + WxAuthConstants.GRANT_TYPE_RFTOKEN
                                + "&refresh_token=REFRESH_TOKEN";
                    String rfResult = HttpClientUtils.httpGet(rftoken_url);
                    ErrorRes rfRes = JSONObject.parseObject(rfResult, ErrorRes.class);
                    if (resBean != null && StringUtils.isBlank(resBean.getErrcode())) {
                        throw new BusinessException(rfRes.getErrmsg());
                    }
                    RefreshTokenRes rfResBean = JSONObject.parseObject(rfResult, RefreshTokenRes.class);
                    // 刷新token成功,授权继续,此处保存access_token,refreshtoken和openid;

                    // 跳转目标页面;
                    return createRedirectRes(rp.getSurl());
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        String redirect_url = WxAuthConstants.URL_AUTH
                + "?appid=" + WxAuthConstants.APPID
                + "&redirect_uri=" + URLEncoder.encode(basepath + WxAuthConstants.REDIECT_URI_GET_ACCESS_TOKEN, "UTF-8")
                + "&response_type=" + WxAuthConstants.RESPONSE_TYPE_AUTH
                + "&scope=" + WxAuthConstants.SCOPE_AUTH_USRINFO
                + "&state=" + state
                + WxAuthConstants.TAILSTR_AUTH;

        // 向微信获取授权code
        return "redirect:" + redirect_url;
    }
}
