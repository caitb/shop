package com.masiis.shop.common.beans.wx.notice;

/**
 * @Date:2016/4/20
 * @auth:lzh
 */
public class TemplateNotPay {
    private TemplateDataItem first;
    private TemplateDataItem type;
    private TemplateDataItem e_title;
    private TemplateDataItem o_id;
    private TemplateDataItem order_date;
    private TemplateDataItem o_money;
    private TemplateDataItem remark;

    public TemplateDataItem getFirst() {
        return first;
    }

    public void setFirst(TemplateDataItem first) {
        this.first = first;
    }

    public TemplateDataItem getType() {
        return type;
    }

    public void setType(TemplateDataItem type) {
        this.type = type;
    }

    public TemplateDataItem getE_title() {
        return e_title;
    }

    public void setE_title(TemplateDataItem e_title) {
        this.e_title = e_title;
    }

    public TemplateDataItem getO_id() {
        return o_id;
    }

    public void setO_id(TemplateDataItem o_id) {
        this.o_id = o_id;
    }

    public TemplateDataItem getOrder_date() {
        return order_date;
    }

    public void setOrder_date(TemplateDataItem order_date) {
        this.order_date = order_date;
    }

    public TemplateDataItem getO_money() {
        return o_money;
    }

    public void setO_money(TemplateDataItem o_money) {
        this.o_money = o_money;
    }

    public TemplateDataItem getRemark() {
        return remark;
    }

    public void setRemark(TemplateDataItem remark) {
        this.remark = remark;
    }
}
