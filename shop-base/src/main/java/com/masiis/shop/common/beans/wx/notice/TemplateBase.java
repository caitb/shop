package com.masiis.shop.common.beans.wx.notice;

/**
 * @date 2016/4/20
 * @author lzh
 */
public class TemplateBase {
    private String touser;
    private String template_id;
    private String url;
    private String topcolor;
    private TemplateDataBase data;

    public TemplateBase(TemplateDataBase data){
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

    public TemplateDataBase getData() {
        return data;
    }

    public void setData(TemplateDataBase data) {
        this.data = data;
    }
}
