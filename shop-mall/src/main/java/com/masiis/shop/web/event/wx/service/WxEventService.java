package com.masiis.shop.web.event.wx.service;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.user.SfUserShareParamMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComWxUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.dao.po.SfUserShareParam;
import com.masiis.shop.web.event.wx.bean.event.*;
import com.masiis.shop.web.mall.beans.wxauth.AccessTokenRes;
import com.masiis.shop.web.mall.beans.wxauth.WxUserInfo;
import com.masiis.shop.web.mall.service.user.UserService;
import com.masiis.shop.web.mall.service.user.WxUserService;
import com.masiis.shop.web.mall.utils.wx.WxCredentialUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Date 2016/5/6
 * @Auther lzh
 */
@Service
public class WxEventService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfUserShareParamMapper paramMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SfShopMapper shopMapper;
    @Resource
    private UserService userService;
    @Resource
    private WxUserService wxUserService;

    /**
     * 处理微信事件推送
     *
     * @param body
     */
    public WxBaseMessage handleEvent(WxEventBody body) {
        if(body == null){
            throw new BusinessException("request body is null");
        }
        WxBaseMessage res = null;
        switch (body.getEvent()){
            case "SCAN":
            case "subscribe":
                res = handleQRScanEvent(body);
                break;
            case "CLICK":
                res = handleMenuClickEvent(body);
                break;
            default:
        }
        return res;
    }

    /**
     * 处理菜单点击事件
     *
     * @param body
     * @return
     */
    private WxBaseMessage handleMenuClickEvent(WxEventBody body) {
        if(!"menu_click_event_contact_us".equals(body.getEventKey())){
            throw new BusinessException();
        }
        WxMenuEventRes res = new WxMenuEventRes();
        res.setToUserName(body.getFromUserName());
        res.setFromUserName(body.getToUserName());
        res.setCreateTime(new Date().getTime());
        res.setMsgType("text");
        res.setContent("您可以通过两种方式联系我们：\n" +
                "1.直接在公众账号内输入您想要说的话，发送给我们。\n" +
                "2.拨打我们的客服电话：400-961-9616。");

        return res;
    }

    /**
     * 发送注册继续链接
     *
     * @param body
     * @return
     */
    private WxArticleRes handleQRScanEvent(WxEventBody body) {
        Long paramId = null;
        String eventStr = body.getEventKey();
        if("SCAN".equals(body.getEvent())){
            if(StringUtils.isNotBlank(eventStr)) {
                paramId = Long.valueOf(eventStr);
            } else {
                return null;
            }
        }
        if("subscribe".equals(body.getEvent())){
            if(StringUtils.isNotBlank(eventStr)) {
                eventStr = eventStr.replaceAll("qrscene_", "");
                paramId = Long.valueOf(eventStr);
            } else {
                return null;
            }
        }
        if(paramId == null){
            throw new BusinessException();
        }

        SfUserShareParam param = paramMapper.selectByPrimaryKey(paramId);
        if(param == null){
            throw new BusinessException();
        }

        // 扫码注册用户
        ComUser user = scanEventUserSignUp(body);
        // 绑定分销关系
        userService.getShareUser(user.getId(), param.getfUserId());

        String url = null;
        if(param.getSkuId() != null && param.getSkuId().intValue() != 0){
            //url = "http://mall.qc.iimai.com/";
        } else {
            url = PropertiesUtils.getStringValue("mall.domain.name.address") + "/" + param.getShopId() + "/"
                    + param.getfUserId() + "/shop.shtml";
        }

        ComUser pUser = comUserMapper.selectByPrimaryKey(param.getfUserId());
        SfShop shop = shopMapper.selectByPrimaryKey(param.getShopId());

        WxArticleRes res = new WxArticleRes();
        res.setToUserName(body.getFromUserName());
        res.setFromUserName(body.getToUserName());
        res.setCreateTime(new Date().getTime());
        res.setMsgType("news");
        res.setArticleCount(1);
        List<Article> articles = new ArrayList<>();
        Article article = new Article("点击继续", null);
        article.setDescription("您的好友" + pUser.getWxNkName() + "分享了" + shop.getName() + "，点击前往");
        article.setUrl(url);
        articles.add(article);
        res.setArticles(articles);

        return res;
    }

    /**
     * 扫描关注二维码事件,用户注册逻辑
     *
     * @param body
     * @return
     */
    private ComUser scanEventUserSignUp(WxEventBody body){
        ComWxUser wxUser = wxUserService.getWxUserByOpenIdAndAppID(body.getFromUserName(), WxConsSF.APPID);
        ComUser user = null;
        if(wxUser == null){
            String url = WxConsSF.URL_CGIBIN_USERINFO
                    + "?access_token=" + WxCredentialUtils.getInstance().getCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET)
                    + "&openid=" + body.getFromUserName()
                    + "&lang=zh_CN";
            String result = HttpClientUtils.httpGet(url);
            WxUserInfo res = JSONObject.parseObject(result, WxUserInfo.class);
            if(res == null || !body.getFromUserName().equals(res.getOpenid())){
                return null;
            }

            AccessTokenRes atRes = new AccessTokenRes();
            atRes.setAccess_token("sss");
            atRes.setExpires_in(1l);
            atRes.setOpenid(res.getOpenid());
            atRes.setRefresh_token("sss");
            atRes.setUnionid(res.getUnionid());

            user = userService.signWithCreateUserByWX(atRes, res);
        } else {
            user = userService.getUserByUnionid(wxUser.getUnionid());
        }

        return user;
    }
}
