package com.masiis.shop.common.beans.wx.notice;

/**
 * @Date 2016/6/18
 * @Author lzh
 */
public class WxPFUpgradeResultUPNotice extends WxNoticeDataBase {
    /**
     * 内容标题
     */
    private WxNoticeDataItem first;
    /**
     * 用户昵称
     */
    private WxNoticeDataItem keyword1;
    /**
     * 最新等级
     */
    private WxNoticeDataItem keyword2;
    /**
     * 生效时间
     */
    private WxNoticeDataItem keyword3;
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

    public WxNoticeDataItem getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(WxNoticeDataItem keyword3) {
        this.keyword3 = keyword3;
    }

    public WxNoticeDataItem getRemark() {
        return remark;
    }

    public void setRemark(WxNoticeDataItem remark) {
        this.remark = remark;
    }
}
