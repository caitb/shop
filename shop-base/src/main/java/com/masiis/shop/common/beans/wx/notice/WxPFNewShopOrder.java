package com.masiis.shop.common.beans.wx.notice;

/**
 * @Date:2016/4/23
 * @auth:lzh
 */
public class WxPFNewShopOrder extends WxNoticeDataBase {
    /**
     * 内容标题
     */
    private WxNoticeDataItem first;
    /**
     * 收件人
     */
    private WxNoticeDataItem keyword1;
    /**
     * 联系电话
     */
    private WxNoticeDataItem keyword2;
    /**
     * 收货地址
     */
    private WxNoticeDataItem keyword3;
    /**
     * 购物清单
     */
    private WxNoticeDataItem keyword4;
    /**
     * 备注
     */
    private WxNoticeDataItem keyword5;
    /**
     * 备注之下的备注
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

    public WxNoticeDataItem getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(WxNoticeDataItem keyword3) {
        this.keyword3 = keyword3;
    }

    public WxNoticeDataItem getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(WxNoticeDataItem keyword4) {
        this.keyword4 = keyword4;
    }

    public WxNoticeDataItem getKeyword5() {
        return keyword5;
    }

    public void setKeyword5(WxNoticeDataItem keyword5) {
        this.keyword5 = keyword5;
    }

    public WxNoticeDataItem getRemark() {
        return remark;
    }

    public void setRemark(WxNoticeDataItem remark) {
        this.remark = remark;
    }
}
