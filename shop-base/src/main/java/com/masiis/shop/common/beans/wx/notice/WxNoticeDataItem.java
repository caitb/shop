package com.masiis.shop.common.beans.wx.notice;

/**
 * @Date:2016/4/20
 * @auth:lzh
 */
public class WxNoticeDataItem {
    private String value;
    private String color;

    public WxNoticeDataItem(){}

    public WxNoticeDataItem(String value, String color){
        super();
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
