package com.masiis.shop.common.beans.wx.notice;

/**
 * @Date:2016/4/23
 * @auth:lzh
 */
public class WxPFNewOrder extends WxNoticeDataBase {
    /**
     * 内容标题
     */
    private WxNoticeDataItem first;
    /**
     * 订单编号(不是id)
     */
    private WxNoticeDataItem keyword1;
    /**
     * 时间
     */
    private WxNoticeDataItem keyword2;
    /**
     * 备注
     */
    private WxNoticeDataItem remark;

    public WxNoticeDataItem getFirst() {
        return first;
    }

    public void setFirst(WxNoticeDataItem first) {
        this.first = first;
    }

    public WxNoticeDataItem getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(WxNoticeDataItem keyword1) {
        this.keyword1 = keyword1;
    }

    public WxNoticeDataItem getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(WxNoticeDataItem keyword2) {
        this.keyword2 = keyword2;
    }

    public WxNoticeDataItem getRemark() {
        return remark;
    }

    public void setRemark(WxNoticeDataItem remark) {
        this.remark = remark;
    }
}
