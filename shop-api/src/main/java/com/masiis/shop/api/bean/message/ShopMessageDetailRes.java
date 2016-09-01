package com.masiis.shop.api.bean.message;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.po.SfMessageContent;

import java.util.List;

/**
 * @Date 2016/8/4
 * @Author lzh
 */
public class ShopMessageDetailRes extends BasePagingRes {
    private List<SfMessageContent> mList;

    public List<SfMessageContent> getmList() {
        return mList;
    }

    public void setmList(List<SfMessageContent> mList) {
        this.mList = mList;
    }
}
