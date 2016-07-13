package com.masiis.shop.common.beans.wx.event.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @Date 2016/5/14
 * @Auther lzh
 */
@XStreamAlias("xml")
public class WxContentRes extends WxBaseMessage {
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
