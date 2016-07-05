package com.masiis.shop.dao.beans.user;

import java.util.List;

/**
 * mall 粉丝列表或代言人列表展示页面PO
 * Created by wangbingjian on 2016/7/4.
 */
public class SfSpokenAndFansPageViewPo {
    /**
     * 总粉丝数量
     */
    private Integer totalCount = 0;
    /**
     * 第一级数量
     */
    private Integer firstCount = 0;
    /**
     * 第二级数量
     */
    private Integer secondCount = 0;
    /**
     * 第三极数量
     */
    private Integer thirdCount = 0;

    List<SfSpokesAndFansInfo> sfSpokesAndFansInfos;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getFirstCount() {
        return firstCount;
    }

    public void setFirstCount(Integer firstCount) {
        this.firstCount = firstCount;
    }

    public Integer getSecondCount() {
        return secondCount;
    }

    public void setSecondCount(Integer secondCount) {
        this.secondCount = secondCount;
    }

    public Integer getThirdCount() {
        return thirdCount;
    }

    public void setThirdCount(Integer thirdCount) {
        this.thirdCount = thirdCount;
    }

    public List<SfSpokesAndFansInfo> getSfSpokesAndFansInfos() {
        return sfSpokesAndFansInfos;
    }

    public void setSfSpokesAndFansInfos(List<SfSpokesAndFansInfo> sfSpokesAndFansInfos) {
        this.sfSpokesAndFansInfos = sfSpokesAndFansInfos;
    }
}
