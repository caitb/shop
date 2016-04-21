package com.masiis.shop.common.beans.wx.notice;

/**
 * @Date:2016/4/20
 * @auth:lzh
 */
public class WxNoticeNotPay extends WxNoticeDataBase {
    private WxNoticeDataItem first;
    private WxNoticeDataItem type;
    private WxNoticeDataItem e_title;
    private WxNoticeDataItem o_id;
    private WxNoticeDataItem order_date;
    private WxNoticeDataItem o_money;
    private WxNoticeDataItem remark;

    public WxNoticeDataItem getFirst() {
        return first;
    }

    public void setFirst(WxNoticeDataItem first) {
        this.first = first;
    }

    public WxNoticeDataItem getType() {
        return type;
    }

    public void setType(WxNoticeDataItem type) {
        this.type = type;
    }

    public WxNoticeDataItem getE_title() {
        return e_title;
    }

    public void setE_title(WxNoticeDataItem e_title) {
        this.e_title = e_title;
    }

    public WxNoticeDataItem getO_id() {
        return o_id;
    }

    public void setO_id(WxNoticeDataItem o_id) {
        this.o_id = o_id;
    }

    public WxNoticeDataItem getOrder_date() {
        return order_date;
    }

    public void setOrder_date(WxNoticeDataItem order_date) {
        this.order_date = order_date;
    }

    public WxNoticeDataItem getO_money() {
        return o_money;
    }

    public void setO_money(WxNoticeDataItem o_money) {
        this.o_money = o_money;
    }

    public WxNoticeDataItem getRemark() {
        return remark;
    }

    public void setRemark(WxNoticeDataItem remark) {
        this.remark = remark;
    }
}
