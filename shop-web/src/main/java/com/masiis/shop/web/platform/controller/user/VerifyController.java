package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.*;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.beans.wxauth.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.constants.WxConstants;
import com.masiis.shop.web.platform.constants.WxResCodeCons;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.utils.SpringRedisUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by lzh on 2016/2/23.
 */
@Controller
@RequestMapping("/verify")
public class VerifyController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserService userService;

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

        HttpSession session = request.getSession();

        // 解析state,并验证有效性
        String stateStr = (String) session.getAttribute(state);
        RedirectParam rp = null;
        try{
            rp = JSONObject.parseObject(URLDecoder.decode(stateStr, "UTF-8"), RedirectParam.class);
            log.info("微信访问授权通信成功,参数:" + rp.toString());
        } catch (Exception e) {
            log.error("json解析错误:" + e.getMessage());
            rp = null;
        }
        // 获取access_token
        log.info("开始获取access_token...");
        String url = WxConstants.URL_GET_ACCESS_TOKEN
                        + "?appid=" + WxConstants.APPID
                        + "&secret=" + WxConstants.APPSECRET
                        + "&code=" + code
                        + "&grant_type=" + WxConstants.GRANT_TYPE_ACCESSTOKEN;
        String result = HttpClientUtils.httpGet(url);
        log.info("getAccessToken请求成功:" + result);
        AccessTokenRes res = JSONObject.parseObject(result, AccessTokenRes.class);
        if(StringUtils.isNotBlank(res.getOpenid())){
            try{
                log.info("微信访问授权成功{openid:" + res.getOpenid() + "}");
                log.info("开始获取微信用户的信息...");

                String infoUrl = WxConstants.URL_GET_USERINFO
                        + "?access_token=" + res.getAccess_token()
                        + "&openid=" + res.getOpenid()
                        + "&lang=zh_CN";
                String infoRes = HttpClientUtils.httpGet(infoUrl);
                System.out.println("info:" + infoRes);
                WxUserInfo userRes = JSONObject.parseObject(infoRes, WxUserInfo.class);
                if(userRes == null || StringUtils.isBlank(userRes.getOpenid())){
                    // 没获取到信息
                    System.out.println("userRes:空!");
                }

                // 检查openid是否已经存在数据库
                ComUser user = userService.getUserByOpenid(res.getOpenid());
                if(user == null){
                    user = new ComUser();
                    user.setOpenid(res.getOpenid());
                    user.setCreateTime(new Date());
                    user.setIsAgent(0);
                }
                // 设置最新的信息
                user.setAccessToken(res.getAccess_token());
                user.setRefreshToken(res.getRefresh_token());
                Long atoken_ex = res.getExpires_in();
                if(res.getExpires_in() == null || res.getExpires_in() <= 0){
                    atoken_ex = 7200L;
                }
                user.setAtokenExpire(new Date(new Date().getTime() + atoken_ex));
                user.setWxHeadImg(userRes.getHeadimgurl());
                user.setWxNkName(userRes.getNickname());
                user.setSex(new Integer(userRes.getSex()));
                if(user.getId() == null){
                    userService.insertComUser(user);
                } else {
                    userService.updateComUser(user);
                }
                // 登录
                session.setAttribute(SysConstants.SESSION_LOGIN_USER_NAME, user);
                // 保存Cookie
                String openidkey = getEncryptByOpenid(res.getOpenid());
                CookieUtils.setCookie(response, SysConstants.COOKIE_WX_ID_NAME,
                        openidkey, 3600 * 24 * 7);
                // 保存redis
                SpringRedisUtil.save(openidkey, res.getOpenid());
                SpringRedisUtil.save(res.getOpenid() + "_token", res.getAccess_token());
                SpringRedisUtil.save(res.getOpenid() + "_rftoken", res.getRefresh_token());

                return createRedirectRes(rp.getSurl());
            } catch (Exception e) {
                log.error("" + e.getMessage());
            }
        }
        // 请求失败
        return "redirect:/";
    }

    @RequestMapping("/wxcheck")
    public String wxCheck(HttpServletRequest request, HttpServletResponse response, String state, RedirectAttributes attr) throws IOException {
        String basepath = request.getScheme()+"://"+request.getServerName()
                + ("80".equals(request.getServerPort() + "") ? "" : ":"+request.getServerPort())
                + request.getContextPath()+"/";
        HttpSession session = request.getSession();
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
                    || !SHAUtils.encodeSHA1(rp.toString().getBytes()).equals(rp.getSignCk())) {
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
                    || StringUtils.isBlank(openid = SpringRedisUtil.get(val, String.class))
                    || StringUtils.isBlank(token = SpringRedisUtil.get(openid + "_token", String.class))) {
                System.out.println("err_val:" + val);
                System.out.println("err_openid:" + openid);
                throw new BusinessException("cookie中openid信息为空或者token无效!");
            }
            System.out.println("val:" + val);
            System.out.println("openid:" + openid);
            // 根据openid获取用户对象
            ComUser user = userService.getUserByOpenid(openid);
            if(user == null){
                throw new BusinessException("该openid无效,需要重新对该用户授权!");
            }

            // 取得了openid和token,并进行验证有效期
            String checkTokenUrl = WxConstants.URL_CHECK_ACCESS_TOKEN
                    + "?access_token=" + token
                    + "&openid=" + openid;
            try {
                String result = HttpClientUtils.httpGet(checkTokenUrl);
                ErrorRes resBean = JSONObject.parseObject(result, ErrorRes.class);
                if (resBean != null && "0".equals(resBean.getErrcode())) {
                    // token仍有效,进行登录操作,并跳转目标链接
                    log.info("token仍有效,跳转目标链接!");
                    session.setAttribute(SysConstants.SESSION_LOGIN_USER_NAME, user);
                } else if (resBean != null
                        && (WxResCodeCons.ACCESS_TOKEN_INVALID.equals(resBean.getErrcode())
                            || WxResCodeCons.ACCESS_TOKEN_TIMEOUT.equals(resBean.getErrcode()))) {
                    // token失效,刷新token,成功或失败.成功,重定向目标页面;失败,重新授权
                    log.info("token失效,刷新token...");
                    String rftoken = SpringRedisUtil.get(openid + "_rftoken", String.class);
                    if(StringUtils.isBlank(rftoken)){
                        throw new BusinessException("token超时,且redis取不到refreshtoken!");
                    }

                    String rftoken_url = WxConstants.URL_REFRESH_TOKEN
                                + "?appid=" + WxConstants.APPID
                                + "&grant_type=" + WxConstants.GRANT_TYPE_RFTOKEN
                                + "&refresh_token=REFRESH_TOKEN";
                    String rfResult = HttpClientUtils.httpGet(rftoken_url);

                    ErrorRes rfRes = JSONObject.parseObject(rfResult, ErrorRes.class);
                    if (resBean != null && StringUtils.isNotBlank(resBean.getErrcode())) {
                        log.error("刷新token失败...");
                        throw new BusinessException(rfRes.getErrmsg());
                    }
                    log.info("刷新token成功,进行登录操作...");
                    RefreshTokenRes rfResBean = JSONObject.parseObject(rfResult, RefreshTokenRes.class);
                    // 刷新token成功,授权继续,此处保存access_token,refreshtoken和openid;
                    String infoUrl = WxConstants.URL_GET_USERINFO
                            + "?appid=" + WxConstants.APPID
                            + "&openid=" + rfResBean.getOpenid()
                            + "&lang=zh_CN";
                    String infoRes = HttpClientUtils.httpGet(infoUrl);
                    WxUserInfo userRes = JSONObject.parseObject(infoRes, WxUserInfo.class);
                    if(userRes == null || StringUtils.isBlank(userRes.getOpenid())){
                        // 没获取到信息
                    }

                    // 设置最新的信息
                    user.setAccessToken(rfResBean.getAccess_token());
                    user.setRefreshToken(rfResBean.getRefresh_token());
                    Long atoken_ex = new Long(rfResBean.getExpires_in());
                    if(rfResBean.getExpires_in() == null || atoken_ex <= 0){
                        atoken_ex = 7200L;
                    }
                    user.setAtokenExpire(new Date(new Date().getTime() + atoken_ex));
                    user.setWxHeadImg(userRes.getHeadimgurl());
                    user.setWxNkName(userRes.getNickname());
                    user.setSex(new Integer(userRes.getSex()));
                    userService.insertComUser(user);
                    // 登录
                    session.setAttribute(SysConstants.SESSION_LOGIN_USER_NAME, user);
                    // 保存Cookie
                    String openidkey = getEncryptByOpenid(rfResBean.getOpenid());
                    CookieUtils.setCookie(response, SysConstants.COOKIE_WX_ID_NAME,
                            openidkey, 3600 * 24 * 7);
                    // 保存redis
                    SpringRedisUtil.save(openidkey, rfResBean.getOpenid());
                    SpringRedisUtil.save(rfResBean.getOpenid() + "_token", rfResBean.getAccess_token());
                    SpringRedisUtil.save(rfResBean.getOpenid() + "_rftoken", rfResBean.getRefresh_token());
                }
                log.info("跳转目标页面,targetUrl:" + rp.getSurl());
                // 跳转目标页面;
                return createRedirectRes(rp.getSurl());
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        String stateStr = MD5Utils.encrypt(state);
        session.setAttribute(stateStr, state);
        String redirect_url = WxConstants.URL_AUTH
                + "?appid=" + WxConstants.APPID
                + "&redirect_uri=" + URLEncoder.encode(basepath + WxConstants.REDIECT_URI_GET_ACCESS_TOKEN, "UTF-8")
                + "&response_type=" + WxConstants.RESPONSE_TYPE_AUTH
                + "&scope=" + WxConstants.SCOPE_AUTH_USRINFO
                + "&state=" + stateStr
                + WxConstants.TAILSTR_AUTH;

        // 向微信获取授权code
        return "redirect:" + redirect_url;
    }

    private Boolean isOpenidValid(String openid){
        return null;
    }

    private String getEncryptByOpenid(String openid){
        if(StringUtils.isBlank(openid)){
            throw new BusinessException("openid is null");
        }
        String res = AESUtils.encrypt(openid + SysConstants.COOKIE_KEY_SALT, SysConstants.COOKIE_AES_KEY);
        return res;
    }

    private String getOpenidByDecrypt(String tar){
        if(StringUtils.isBlank(tar)){
            throw new BusinessException("openid is null");
        }
        String res = AESUtils.decrypt(tar, SysConstants.COOKIE_AES_KEY);
        res.lastIndexOf(SysConstants.COOKIE_KEY_SALT);
        return res;
    }
}
