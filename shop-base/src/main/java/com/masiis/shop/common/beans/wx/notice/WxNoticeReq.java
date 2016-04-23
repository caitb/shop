package com.masiis.shop.common.beans.wx.notice;

/**
 * @date 2016/4/20
 * @author lzh
 */
public class WxNoticeReq<T extends WxNoticeDataBase> {
    /**
     * 填用户openid
     */
    private String touser;
    /**
     * 模板id
     */
    private String template_id;
    /**
     * 自己系统url,可按自己需求展示,可不填
     */
    private String url;
    /**
     * 标题颜色
     */
    private String topcolor;
    private T data;

    public WxNoticeReq(T data){
        super();
        this.data = data;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
