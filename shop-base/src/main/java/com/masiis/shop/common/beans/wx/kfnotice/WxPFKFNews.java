package com.masiis.shop.common.beans.wx.kfnotice;

/**
 * @Date 2016/7/25
 * @Author lzh
 */
public class WxPFKFNews extends  WxPFKFBase{
    private WxPFKFArticles news;

    public WxPFKFArticles getNews() {
        return news;
    }

    public void setNews(WxPFKFArticles news) {
        this.news = news;
    }
}
