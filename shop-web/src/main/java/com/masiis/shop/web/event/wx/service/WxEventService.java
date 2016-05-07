package com.masiis.shop.web.event.wx.service;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.web.event.wx.bean.event.*;
import com.masiis.shop.web.platform.service.user.UserSkuService;
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
    private UserSkuService userSkuService;

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
                "2.拨打我们的客服电话：010-4548878。");

        return res;
    }

    /**
     * 发送注册继续链接
     *
     * @param body
     * @return
     */
    private WxArticleRes handleQRScanEvent(WxEventBody body) {
        Integer pfUserSkuId = null;
        String eventStr = body.getEventKey();
        if("SCAN".equals(body.getEvent())){
            if(StringUtils.isNotBlank(eventStr)) {
                pfUserSkuId = Integer.valueOf(eventStr);
            } else {
                return null;
            }
        }
        if("subscribe".equals(body.getEvent())){
            if(StringUtils.isNotBlank(eventStr)) {
                eventStr = eventStr.replaceAll("qrscene_", "");
                pfUserSkuId = Integer.valueOf(eventStr);
            } else {
                return null;
            }
        }
        if(pfUserSkuId == null){
            throw new BusinessException();
        }

        PfUserSku userSku = userSkuService.getUserSkuById(pfUserSkuId);
        if(userSku == null){
            throw new BusinessException();
        }

        String url = "http://m.qc.iimai.com/product/skuDetails.shtml?skuId=" + userSku.getSkuId()
                + "&pUserId=" + userSku.getUserId();

        WxArticleRes res = new WxArticleRes();
        res.setToUserName(body.getFromUserName());
        res.setFromUserName(body.getToUserName());
        res.setCreateTime(new Date().getTime());
        res.setMsgType("news");
        res.setArticleCount(1);
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("点击继续注册", url));
        res.setArticles(articles);

        return res;
    }
}
