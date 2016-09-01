package com.masiis.shop.dao.beans.user;

import java.util.List;

/**
 * Created by wangbingjian on 2016/8/8.
 */
public class HelpRecommended {

    private Long totoalCount;

    private Integer totalPage;

    private Integer currentPage;

    private Integer pageSize;

    private List<HelpRecommedPo> recommedPos;

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

    public List<HelpRecommedPo> getRecommedPos() {
        return recommedPos;
    }

    public void setRecommedPos(List<HelpRecommedPo> recommedPos) {
        this.recommedPos = recommedPos;
    }
}
