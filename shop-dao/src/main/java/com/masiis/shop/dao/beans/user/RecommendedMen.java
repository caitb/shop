package com.masiis.shop.dao.beans.user;

import com.masiis.shop.dao.beans.system.ComSkuSimple;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComSku;

import java.util.List;

/**
 * Created by wangbingjian on 2016/8/2.
 */
public class RecommendedMen {

    private Long totoalCount;

    private Integer totalPage;

    private Integer currentPage;

    private Integer pageSize;

    private List<UserRecommend> userRecommends;

    private List<ComSkuSimple> skuList;

    private List<ComAgentLevel> levelList;

    public Long getTotoalCount() {
        return totoalCount;
    }

    public void setTotoalCount(Long totoalCount) {
        this.totoalCount = totoalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<UserRecommend> getUserRecommends() {
        return userRecommends;
    }

    public void setUserRecommends(List<UserRecommend> userRecommends) {
        this.userRecommends = userRecommends;
    }

    public List<ComSkuSimple> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<ComSkuSimple> skuList) {
        this.skuList = skuList;
    }

    public List<ComAgentLevel> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<ComAgentLevel> levelList) {
        this.levelList = levelList;
    }
}
