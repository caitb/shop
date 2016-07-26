package com.masiis.shop.web.common.utils.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.beans.wx.kfnotice.WxPFKFArticle;
import com.masiis.shop.common.beans.wx.kfnotice.WxPFKFArticles;
import com.masiis.shop.common.beans.wx.kfnotice.WxPFKFNews;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComWxUser;
import com.masiis.shop.web.common.service.WxUserService;
import com.masiis.shop.web.common.utils.ApplicationContextUtil;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Date 2016/7/25
 * @Author lzh
 */
public class WxPFKFNoticeUtils {
    private static Logger log = Logger.getLogger(WxPFNoticeUtils.class);
    private WxUserService wxUserService = (WxUserService) ApplicationContextUtil.getBean("wxUserService");

    private static class Holder {
        private static final WxPFKFNoticeUtils INSTANCE = new WxPFKFNoticeUtils();
    }
    private WxPFKFNoticeUtils() {
    }
    // 单例懒加载
    public static final WxPFKFNoticeUtils getInstance() {
        return Holder.INSTANCE;
    }



    public static void main(String... args) throws UnsupportedEncodingException {
        WxPFKFArticle article = new WxPFKFArticle();
        article.setDescription("老大：麦链来新人了\n姓名：面纱霜-联合创始人-李boss\n" +
                "时间：2015-10-01 18:00\n我们的团队又加入1位合伙人！恭喜恭喜");
        article.setPicurl("http://file.masiis.com/static/wx_team_notice.jpg");
        //article.setTitle("标题");
        //article.setUrl("http://www.baidu.com");
        List<WxPFKFArticle> list = new ArrayList<>();
        list.add(article);
        WxPFKFArticles articles = new WxPFKFArticles();
        articles.setArticles(list);
        WxPFKFNews news = new WxPFKFNews();
        news.setTouser("o1EHnwBUVHmWWCAc3wIXcJIuspYk");
        news.setMsgtype("news");
        news.setNews(articles);

        String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + "wxddaad94806955802"
                + "&secret=fdcef6f6d485847ee8824e38e6e1610e";
        String urlEn = URLEncoder.encode(url1, "UTF-8");
        String result1 = HttpClientUtils.httpGet(urlEn);

        String token = (String) JSONObject.parseObject(result1, Map.class).get("access_token");
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;

        String res = HttpClientUtils.httpPost(url, JSONObject.toJSONString(news));
        System.out.println(res);
    }

    private String getOpenIdByComUser(ComUser user) {
        ComWxUser wxUser = null;
        try {
            if (user == null) {
                throw new BusinessException("user为空");
            }
            wxUser = wxUserService.getUserByUnionidAndAppid(user.getWxUnionid(), WxConsPF.APPID);
            if (wxUser == null) {
                throw new BusinessException("wxUser为空");
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
        return wxUser.getOpenid();
    }
}
