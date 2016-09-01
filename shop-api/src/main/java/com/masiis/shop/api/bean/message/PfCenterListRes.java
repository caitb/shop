package com.masiis.shop.api.bean.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;

import java.util.List;

/**
 * @Date 2016/8/1
 * @Author lzh
 */
public class PfCenterListRes extends BasePagingRes {
    private List<PfMessageCenterDetail> mList;
    private Integer isAgent;
    private Integer isLaseLevel;

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

    public Integer getIsLaseLevel() {
        return isLaseLevel;
    }

    public void setIsLaseLevel(Integer isLaseLevel) {
        this.isLaseLevel = isLaseLevel;
    }
}
