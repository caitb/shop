package com.masiis.shop.web.event.wx.service;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.event.wx.bean.event.*;
import com.masiis.shop.web.platform.beans.wxauth.AccessTokenRes;
import com.masiis.shop.web.platform.beans.wxauth.WxUserInfo;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.PfUserRelationService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import com.masiis.shop.web.platform.service.user.WxUserService;
import com.masiis.shop.web.platform.utils.wx.WxCredentialUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Date 2016/5/6
 * @Auther lzh
 */
@Service
public class WxEventService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserSkuService userSkuService;
    @Resource
    private WxUserService wxUserService;
    @Resource
    private UserService userService;
    @Resource
    private PfUserRelationService pfUserRelationService;
    @Resource
    private SkuService skuService;
    @Resource
    private ComSkuImageMapper imageMapper;

    /**
     * 处理微信事件推送
     *
     * @param body
     */
    public WxBaseMessage handleEvent(WxEventBody body) throws UnsupportedEncodingException {
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
    private WxBaseMessage handleQRScanEvent(WxEventBody body) throws UnsupportedEncodingException {
        Integer pfUserSkuId = null;
        String eventStr = body.getEventKey();
        if("SCAN".equals(body.getEvent())){
            if(StringUtils.isNotBlank(eventStr)) {
                pfUserSkuId = Integer.valueOf(eventStr);
            } else {
                return createDefaultSubscribeEventReturn(body, "欢迎关注麦链合伙人~");
            }
        }
        if("subscribe".equals(body.getEvent())){
            if(StringUtils.isNotBlank(eventStr)) {
                eventStr = eventStr.replaceAll("qrscene_", "");
                pfUserSkuId = Integer.valueOf(eventStr);
            } else {
                return createDefaultSubscribeEventReturn(body, "欢迎关注麦链合伙人~");
            }
        }
        if(pfUserSkuId == null){
            throw new BusinessException();
        }

        PfUserSku userSku = userSkuService.getUserSkuById(pfUserSkuId);
        if(userSku == null){
            throw new BusinessException();
        }

        log.info("获取代理关系");
        // 用户注册或者查询用户
        ComUser user = scanEventUserSignUp(body);
        if(user == null){
            return null;
        }
        log.info("获取用户");

        Integer skuId = userSku.getSkuId();
        Long pUserId = userSku.getUserId();
        //Long temPUserId = pfUserRelationService.getPUserId(user.getId(), skuId);
        try {
            if (pUserId != null && pUserId > 0) {
                PfUserRelation existRelation = pfUserRelationService
                        .getRelationByUserIdAndSkuIdAndPUserId(user.getId(), skuId, pUserId);
                pfUserRelationService.updateAllToUnableByUserIdAndSkuId(user.getId(), skuId);
                if(existRelation == null) {
                    //校验上级合伙人数据是否合法,如果合法则建立临时绑定关系
                    userSkuService.checkParentData(user, pUserId, skuId);
                    PfUserRelation pfUserRelation = new PfUserRelation();
                    pfUserRelation.setUserId(user.getId());
                    pfUserRelation.setSkuId(skuId);
                    pfUserRelation.setCreateTime(new Date());
                    pfUserRelation.setIsEnable(1);
                    pfUserRelation.setUserPid(pUserId);
                    pfUserRelationService.insert(pfUserRelation);
                } else {
                    existRelation.setIsEnable(1);
                    pfUserRelationService.update(existRelation);
                }
            }
        } catch (Exception e) {
            log.error("扫描二维码注册失败:" + e.getMessage(), e);
        }

        log.info("处理代理关系结束");
        ComSku sku = skuService.getSkuById(skuId);
//        ComSkuImage skuImage = skuService.findComSkuImage(skuId);
//        String imgUrl = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN) + skuImage.getImgUrl();

        String url = PropertiesUtils.getStringValue("web.domain.name.address")
                + "/product/skuDetails.shtml?skuId=" + userSku.getSkuId()
                + "&pUserId=" + userSku.getUserId();
        WxArticleRes res = createReturnToWxUser(body, url, sku.getName(), null);

        return res;
    }

    private WxBaseMessage createDefaultSubscribeEventReturn(WxEventBody body, String content) {
        WxContentRes res = new WxContentRes();

        res.setToUserName(body.getFromUserName());
        res.setFromUserName(body.getToUserName());
        res.setCreateTime(new Date().getTime());
        res.setMsgType("text");
        res.setContent(content);

        return res;
    }

    private WxArticleRes createReturnToWxUser(WxEventBody body, String url, String skuName, String imgUrl) throws UnsupportedEncodingException {
        WxArticleRes res = new WxArticleRes();
        res.setToUserName(body.getFromUserName());
        res.setFromUserName(body.getToUserName());
        res.setCreateTime(new Date().getTime());
        res.setMsgType("news");
        res.setArticleCount(1);
        List<Article> articles = new ArrayList<>();
        Article article = new Article("点击继续", url);
        article.setPicUrl(PropertiesUtils.getStringValue("oss.BASE_URL") + "/static/wx_notice_pic.jpg");
        article.setDescription("您正在申请" + skuName + "合伙人，点击继续");
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
        ComWxUser wxUser = wxUserService.getWxUserByOpenIdAndAppID(body.getFromUserName(), WxConsPF.APPID);
        ComUser user = null;
        if(wxUser == null){
            String url = WxConsPF.URL_CGIBIN_USERINFO
                    + "?access_token=" + WxCredentialUtils.getInstance().getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET)
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

    public static void main(String... args) {
        WxArticleRes res = new WxArticleRes();
        res.setToUserName("aaaaa");
        res.setFromUserName("bbbbb");
        res.setCreateTime(new Date().getTime());
        res.setMsgType("news");
        res.setArticleCount(1);
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("点击继续注册",
                PropertiesUtils.getStringValue("web.domain.name.address") + "/product/skuDetails.shtml?skuId=40&pUserId=11"));
        res.setArticles(articles);
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStream.ignoreUnknownElements();
        xStream.processAnnotations(res.getClass());
        String result = xStream.toXML(res);
        result = result.replaceAll("&amp;", "&");
        System.out.println(result);

        WxArticleRes r = (WxArticleRes) xStream.fromXML(result);
        System.out.println(r);
    }
}
