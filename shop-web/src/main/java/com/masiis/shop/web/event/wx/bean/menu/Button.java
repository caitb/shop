package com.masiis.shop.web.event.wx.bean.menu;

import java.util.List;

/**
 * @Date 2016/5/7
 * @Auther lzh
 */
public class Button {
    /**
     * 否,二级菜单数组，个数应为1~5个
     */
    private List<Button> sub_button;
    /**
     * 是,菜单的响应动作类型
     */
    private String type;
    /**
     * 是,菜单标题，不超过16个字节，子菜单不超过40个字节
     */
    private String name;
    /**
     * click等点击类型必须,菜单KEY值，用于消息接口推送，不超过128字节
     */
    private String key;
    /**
     * view类型必须,网页链接，用户点击菜单可打开链接，不超过1024字节
     */
    private String url;
    /**
     * media_id类型和view_limited类型必须,调用新增永久素材接口返回的合法media_id
     */
    private String media_id;

    public Button(){}
    public Button(String name, String type, String url){
        this.name = name;
        this.type = type;
        this.url = url;
    }
    public Button(String name, List<Button> sub_button){
        this.name = name;
        this.sub_button = sub_button;
    }

    public List<Button> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<Button> sub_button) {
        this.sub_button = sub_button;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
}
