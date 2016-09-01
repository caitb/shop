package com.masiis.shop.api.bean.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.common.util.jacksonformat.CustomDateSerialize;
import com.masiis.shop.dao.po.PfMessageContent;

import java.util.List;

/**
 * @Date 2016/8/2
 * @Author lzh
 */
public class PfMessageDetailListRes extends BasePagingRes {
    private String fromUserName;
    private List<PfMessageContent> mList;

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public List<PfMessageContent> getmList() {
        return mList;
    }

    public void setmList(List<PfMessageContent> mList) {
        this.mList = mList;
    }
}
