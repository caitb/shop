package com.masiis.shop.web.mall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.*;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComWxUser;
import com.masiis.shop.web.mall.beans.wxauth.*;
import com.masiis.shop.web.mall.constants.SysConstants;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.web.mall.constants.WxResCodeCons;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.user.UserService;
import com.masiis.shop.web.mall.service.user.WxUserService;
import com.masiis.shop.web.mall.utils.SpringRedisUtil;
import com.masiis.shop.web.mall.utils.wx.WxAuthUrlUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
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
    @Resource
    private WxUserService wxUserService;

    @RequestMapping("/actk")
    public String getCode(HttpServletRequest request, HttpServletResponse response,
                          String code, String state){
        if(StringUtils.isBlank(code)
                || StringUtils.isBlank(state)){
            log.error("请求参数不合法!");
            return "common/500";
        }

        HttpSession session = request.getSession();

        // 解析state,并验证有效性
        String stateStr = (String) session.getAttribute(state);
        RedirectParam rp = null;
        try{
            rp = JSONObject.parseObject(URLDecoder.decode(stateStr, "UTF-8"), RedirectParam.class);
            log.info("微信访问授权通信成功,参数:" + rp.toString());
            session.removeAttribute(state);
        } catch (Exception e) {
            log.error("json解析错误:" + e.getMessage());
            rp = null;
            return "common/500";
        }
        // 获取access_token
        log.info("开始获取access_token...");
        String url = WxAuthUrlUtils.createAccessTokenUrl(code);
        String result = HttpClientUtils.httpGet(url);
        log.info("getAccessToken请求成功:" + result);
        AccessTokenRes res = JSONObject.parseObject(result, AccessTokenRes.class);
        if(StringUtils.isNotBlank(res.getOpenid())){
            try{
                log.info("微信访问授权成功{openid:" + res.getOpenid() + "}");
                log.info("开始获取微信用户的信息...");

                String infoUrl = WxAuthUrlUtils.createUserInfoUrl(res.getAccess_token(), res.getOpenid());
                String infoRes = HttpClientUtils.httpGet(infoUrl);

                System.out.println("info:" + infoRes);

                WxUserInfo userRes = JSONObject.parseObject(infoRes, WxUserInfo.class);
                if(userRes == null || StringUtils.isBlank(userRes.getOpenid())){
                    // 没获取到信息
                    log.error("userRes:空!");
                    return "common/500";
                }
                if(StringUtils.isBlank(res.getUnionid())){
                    log.error("没有unionid,逻辑错误");
                    return "common/500";
                }

                ComUser user = null;
                try{
                    // 用户登录逻辑
                    user = userService.signWithCreateUserByWX(res, userRes);
                } catch (Exception e) {
                    log.error("登录出错," + e.getMessage());
                    return "common/500";
                }

                log.info("userid:" + user.getId());

                session.invalidate();
                session = request.getSession();
                // 登录
                setComUser(request,user);
                // 保存Cookie
                String unionidkey = getEncrypt(userRes.getUnionid());
                CookieUtils.setCookie(response, SysConstants.COOKIE_WX_ID_NAME,
                        unionidkey, 3600 * 24 * 7, true);
                // 保存redis
                SpringRedisUtil.save(unionidkey, userRes.getUnionid());
                SpringRedisUtil.save(userRes.getUnionid() + res.getOpenid() + "_token", res.getAccess_token());
                SpringRedisUtil.save(userRes.getUnionid() + res.getOpenid() + "_rftoken", res.getRefresh_token());

                return createRedirectRes(rp.getSurl());
            } catch (Exception e) {
                log.error("" + e.getMessage());
            }
        }
        // 请求失败
        return "common/500";
    }

    @RequestMapping("/wxcheck")
    public String wxCheck(HttpServletRequest request, HttpServletResponse response, String state, RedirectAttributes attr) throws IOException {
        String basepath = request.getScheme()+"://"+request.getServerName()
                + ("80".equals(request.getServerPort() + "") ? "" : ":"+request.getServerPort())
                + request.getContextPath()+"/";
        HttpSession session = request.getSession();
        RedirectParam rp = null;

        if(StringUtils.isBlank(state)) {
            log.error("state为空,调用异常,跳转错误页面!");
            return "common/500";
        }

        // 解析state,并验证有效性
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
            log.error("state参数不正确,调用异常,跳转错误页面!");
            return "common/500";
        }

        try{
            // 验证cookie
            Cookie cookie = CookieUtils.getCookie(request, SysConstants.COOKIE_WX_ID_NAME);
            if (cookie == null) {
                throw new BusinessException("cookie为空!");
            }
            String val = cookie.getValue();
            String unionid = null;
            String token = null;
            if (StringUtils.isBlank(val)
                    || StringUtils.isBlank(unionid = SpringRedisUtil.get(val, String.class))) {
                System.out.println("err_val:" + val);
                throw new BusinessException("cookie中unionid信息为空!");
            }
            System.out.println("val:" + val);
            // 根据openid获取用户对象
            ComWxUser wxUser = wxUserService.getUserByUnionidAndAppid(unionid, WxConsSF.APPID);
            if(wxUser == null){
                throw new BusinessException("该unionid无效,需要重新对该用户授权!");
            }
            // 从redis获取token
            token = SpringRedisUtil.get(wxUser.getUnionid() + wxUser.getOpenid() + "_token", String.class);
            ComUser user = userService.getUserByUnionid(unionid);
            // 取得了openid和token,并进行验证有效期
            String checkTokenUrl = WxAuthUrlUtils.createCheckTokenUrl(token, wxUser.getOpenid());
            try {
                String result = HttpClientUtils.httpGet(checkTokenUrl);
                System.out.println("检查token是否有效,请求结果:" + result);
                ErrorRes resBean = JSONObject.parseObject(result, ErrorRes.class);
                if (resBean != null && "0".equals(resBean.getErrcode())) {
                    // token仍有效,进行登录操作,并跳转目标链接
                    log.info("token仍有效,跳转目标链接!");
                    session.invalidate();
                    session = request.getSession();
                    setComUser(request,user);
                } else if (resBean != null
                        && (WxResCodeCons.ACCESS_TOKEN_INVALID.equals(resBean.getErrcode())
                        || WxResCodeCons.ACCESS_TOKEN_TIMEOUT.equals(resBean.getErrcode())
                        || WxResCodeCons.ACCESS_TOKEN_INVALID_OR_NOT_LATEST.equals(resBean.getErrcode()))) {
                    // token失效,刷新token,成功或失败.成功,重定向目标页面;失败,重新授权
                    log.info("token失效,刷新token...");
                    String rftoken = SpringRedisUtil.get(wxUser.getUnionid() + wxUser.getOpenid() + "_rftoken", String.class);
                    if(StringUtils.isBlank(rftoken)){
                        throw new BusinessException("token超时,且redis取不到refreshtoken!");
                    }

                    String rftoken_url = WxAuthUrlUtils.createRefreshTokenUrl(rftoken);
                    String rfResult = HttpClientUtils.httpGet(rftoken_url);
                    ErrorRes rfRes = JSONObject.parseObject(rfResult, ErrorRes.class);
                    if (resBean != null && StringUtils.isNotBlank(resBean.getErrcode())) {
                        log.error("刷新token失败...");
                        throw new BusinessException(rfRes.getErrmsg());
                    }
                    log.info("刷新token成功,进行登录操作...");
                    RefreshTokenRes rfResBean = JSONObject.parseObject(rfResult, RefreshTokenRes.class);
                    // 刷新token成功,授权继续,此处保存access_token,refreshtoken和openid;
                    String infoUrl = WxAuthUrlUtils.createUserInfoUrl(rfResBean.getAccess_token(),
                            rfResBean.getOpenid());
                    // 发送请求,获取用户信息
                    String infoRes = HttpClientUtils.httpGet(infoUrl);
                    WxUserInfo userRes = JSONObject.parseObject(infoRes, WxUserInfo.class);
                    if(userRes == null || StringUtils.isBlank(userRes.getOpenid())){
                        throw new BusinessException("获取微信用户信息失败,未获取到信息");
                    }
                    // 更新wxUser的信息
                    updateWxUserByRftkn(rfResBean, userRes, wxUser);
                    wxUserService.updateWxUser(wxUser);
                    // 登录
                    session.invalidate();
                    session = request.getSession();
                    setComUser(request,user);
                    // 保存Cookie
                    String openidkey = getEncrypt(userRes.getUnionid());
                    CookieUtils.setCookie(response, SysConstants.COOKIE_WX_ID_NAME,
                            openidkey, 3600 * 24 * 7, true);
                    // 保存redis
                    SpringRedisUtil.save(openidkey, rfResBean.getOpenid());
                    SpringRedisUtil.save(userRes.getUnionid() + userRes.getOpenid() + "_token", rfResBean.getAccess_token());
                    SpringRedisUtil.save(userRes.getUnionid() + userRes.getOpenid() + "_rftoken", rfResBean.getRefresh_token());
                } else {
                    log.error("校验openid和accesstoken未知错误,重新授权");
                    throw new BusinessException("校验openid和accesstoken未知错误,重新授权");
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
        // 创建微信授权链接,需用户点击确认
        String redirect_url = WxAuthUrlUtils.createAuthorizeInfoUrl(basepath, stateStr);

        // 向微信获取授权code
        return "redirect:" + redirect_url;
    }

    /**
     * 根据最新请求数据更新wxUser
     *
     * @param res
     * @param userInfo
     * @param wxUser
     */
    private void updateWxUserByRftkn(RefreshTokenRes res, WxUserInfo userInfo, ComWxUser wxUser) {
        if(wxUser == null){
            throw new BusinessException("传入目标对象为null");
        }

        wxUser.setAccessToken(res.getAccess_token());
        Long atoken_ex = Long.valueOf(res.getExpires_in());
        if(atoken_ex == null || atoken_ex <= 0){
            atoken_ex = 7200L * 1000;
        }
        wxUser.setAtokenExpire(new Date(new Date().getTime() + atoken_ex));
        wxUser.setCity(userInfo.getCity());
        wxUser.setCountry(userInfo.getCountry());
        wxUser.setHeadImgUrl(userInfo.getHeadimgurl());
        wxUser.setNkName(userInfo.getNickname());
        wxUser.setProvince(userInfo.getProvince());
        wxUser.setRefreshToken(res.getRefresh_token());
        wxUser.setSex(Integer.valueOf(userInfo.getSex()));
    }

    private Boolean isOpenidValid(String openid){
        return null;
    }

    private String getEncrypt(String openid){
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
        res = res.substring(0, res.lastIndexOf(SysConstants.COOKIE_KEY_SALT));
        return res;
    }
}
