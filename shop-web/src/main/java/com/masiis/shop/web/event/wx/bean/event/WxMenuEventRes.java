package com.masiis.shop.web.event.wx.bean.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @Date 2016/5/7
 * @Auther lzh
 */
@XStreamAlias("xml")
public class WxMenuEventRes extends WxBaseMessage {
    @XStreamAlias("Content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
