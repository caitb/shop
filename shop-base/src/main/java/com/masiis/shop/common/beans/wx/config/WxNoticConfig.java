package com.masiis.shop.common.beans.wx.config;

/**
 * @Date:2016/4/21
 * @auth:lzh
 */
public class WxNoticConfig extends WxBaseConfig{
    private String noticeToken;
    private String url;

    public String getNoticeToken() {
        return noticeToken;
    }

    public void setNoticeToken(String noticeToken) {
        this.noticeToken = noticeToken;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
