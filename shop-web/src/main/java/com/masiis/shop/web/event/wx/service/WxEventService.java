package com.masiis.shop.web.event.wx.service;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.web.event.wx.bean.event.Article;
import com.masiis.shop.web.event.wx.bean.event.WxArticleRes;
import com.masiis.shop.web.event.wx.bean.event.WxEventBody;
import com.masiis.shop.web.platform.service.user.UserSkuService;
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
    public WxArticleRes handleEvent(WxEventBody body) {
        if(body == null){
            throw new BusinessException("request body is null");
        }
        WxArticleRes res = null;
        switch (body.getEvent()){
            case "SCAN":
            case "subscribe":
                res = handleQRScanEvent(body);
                break;
        }
        return res;
    }

    private WxArticleRes handleQRScanEvent(WxEventBody body) {
        Integer pfUserSkuId = null;
        if("SCAN".equals(body.getEvent())){
            pfUserSkuId = Integer.valueOf(body.getEventKey());
        }
        if("subscribe".equals(body.getEvent())){
            pfUserSkuId = Integer.valueOf(body.getEventKey().replaceAll("qrscene_", ""));
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
