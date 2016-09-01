package com.masiis.shop.api.bean.message;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;

import java.util.List;

/**
 * @Date 2016/8/3
 * @Author lzh
 */
public class ShopMessageCenterListRes extends BaseBusinessRes{
    private List<PfMessageCenterDetail> mList;
    private Integer isAgent;

    public List<PfMessageCenterDetail> getmList() {
        return mList;
    }

    public void setmList(List<PfMessageCenterDetail> mList) {
        this.mList = mList;
    }

    public Integer getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(Integer isAgent) {
        this.isAgent = isAgent;
    }
}
