package com.masiis.shop.web.event.wx.service;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.mall.user.SfUserShareParamMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
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
import java.util.*;

/**
 * @Date 2016/5/6
 * @Auther lzh
 */
@Service
public class WxEventService {
    private Logger log = Logger.getLogger(this.getClass());
    private final String[] titles = new String[4];
    private final String[] urls = new String[4];
    {
        titles[0] = "1、麦链模式";
        urls[0] = "http://mp.weixin.qq.com/s?__biz=MzI1OTIxNzgwNA==&mid=2247483656&idx=1&sn=555876e87000a8b289d535fb12ce4333#rd";
        titles[1] = "2、如何选购优质商品";
        urls[1] = "http://mp.weixin.qq.com/s?__biz=MzI1OTIxNzgwNA==&mid=2247483663&idx=1&sn=03733197c18a95dcbc3625606fcf340a&scene=0#rd";
        titles[2] = "3、如何赚取佣金";
        urls[2] = "http://mp.weixin.qq.com/s?__biz=MzI1OTIxNzgwNA==&mid=2247483665&idx=1&sn=ae92270714303d6247ef459de53bc404&scene=0#wechat_redirect";
        titles[3] = "4、常见问题解答";
        urls[3] = "http://mp.weixin.qq.com/s?__biz=MzI1OTIxNzgwNA==&mid=2247483664&idx=1&sn=1935f5fa95fb9405c54b2f01ef664714&scene=0#rd";
    }

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
    @Resource
    private SfUserRelationMapper sfUserRelationMapper;

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
            case "subscribe":
                res = handleDefaultEventReturn(body);
            case "SCAN":
                handleQRScanEvent(body);
                break;
            case "CLICK":
                res = handleMenuClickEvent(body);
                break;
            default:
        }
        return res;
    }

    private WxBaseMessage handleDefaultEventReturn(WxEventBody body) {
        String content = "欢迎关注麦链商城。请点击您想了解的内容，查看详情：\n\n";
        for(int i = 0; i < urls.length; i++){
            content += "<a href=\"" + urls[i] + "\">" + titles[i] + "</a>\n\n";
        }
        content = content.substring(0, content.lastIndexOf("\n\n"));
        return createDefaultSubscribeEventReturn(body, content);
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
    private WxBaseMessage handleQRScanEvent(final WxEventBody body) {
        Long paramId = null;
        String eventStr = body.getEventKey();
        if("SCAN".equals(body.getEvent())){
            if(StringUtils.isNotBlank(eventStr)) {
                paramId = Long.valueOf(eventStr);
            } else {
                return createDefaultSubscribeEventReturn(body, "欢迎关注麦链商城~");
            }
        }
        if("subscribe".equals(body.getEvent())){
            if(StringUtils.isNotBlank(eventStr)) {
                eventStr = eventStr.replaceAll("qrscene_", "");
                paramId = Long.valueOf(eventStr);
            } else {
                return createDefaultSubscribeEventReturn(body, "欢迎关注麦链商城~");
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
        if(param.getfUserId().longValue() != 0) {
            // 绑定分销关系
            userService.getShareUser(user.getId(), param.getfUserId());
        } else {
            SfUserRelation relation = sfUserRelationMapper.getSfUserRelationByUserId(user.getId());
            if(relation == null){
                SfUserRelation sfNewUserRelation = new SfUserRelation();
                sfNewUserRelation.setCreateTime(new Date());
                sfNewUserRelation.setUserId(user.getId());
                sfNewUserRelation.setTreeLevel(1);
                sfNewUserRelation.setUserPid(0l);
                sfUserRelationMapper.insert(sfNewUserRelation);
                String treeCode = sfNewUserRelation.getId() + ",";
                sfUserRelationMapper.updateTreeCodeById(sfNewUserRelation.getId(), treeCode);
            }
        }

        String url = null;
        if(param.getSkuId() != null && param.getSkuId().intValue() != 0){
            //url = "http://mall.qc.iimai.com/";
        } else {
            url = PropertiesUtils.getStringValue("mall.domain.name.address") + "/" + param.getShopId() + "/"
                    + param.getfUserId() + "/shop.shtml";
        }

        SfShop shop = shopMapper.selectByPrimaryKey(param.getShopId());

        WxArticleRes res = new WxArticleRes();
        res.setToUserName(body.getFromUserName());
        res.setFromUserName(body.getToUserName());
        res.setCreateTime(new Date().getTime());
        res.setMsgType("news");
        res.setArticleCount(1);
        List<Article> articles = new ArrayList<>();
        Article article = new Article("点击继续", null);
        if(param.getfUserId().longValue() != 0) {
            ComUser pUser = comUserMapper.selectByPrimaryKey(param.getfUserId());
            article.setDescription("您的好友" + pUser.getWxNkName() + "分享了" + shop.getName() + "，点击前往");
        } else {
            article.setDescription("您将要访问" + shop.getName() + "，点击前往");
        }
        article.setUrl(url);
        articles.add(article);
        res.setArticles(articles);

        final JSONObject mr = new JSONObject();
        JSONObject news = new JSONObject();
        mr.put("touser", res.getToUserName());
        mr.put("msgtype", res.getMsgType());
        mr.put("news", news);
        news.put("articles", res.getArticles());
        final String token = WxCredentialUtils.getInstance().getCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET);
        // 起线程发送继续注册消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
                String result = HttpClientUtils.httpPost(url, mr.toJSONString());
                log.info("openId{" + body.getFromUserName() + "}的关注公众号自动回复消息响应结果:" + result);
            }
        }).start();

        return null;
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
