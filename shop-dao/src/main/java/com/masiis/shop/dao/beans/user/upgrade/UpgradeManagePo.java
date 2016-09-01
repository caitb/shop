package com.masiis.shop.dao.beans.user.upgrade;

import com.masiis.shop.dao.beans.user.PfUserUpGradeInfo;

import java.util.List;

/**
 * Created by wangbingjian on 2016/8/3.
 */
public class UpgradeManagePo {

    private Long totoalCount;

    private Integer totalPage;

    private Integer currentPage;

    private Integer pageSize;

    List<PfUserUpGradeInfo> upGradeInfos;

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

    public List<PfUserUpGradeInfo> getUpGradeInfos() {
        return upGradeInfos;
    }

    public void setUpGradeInfos(List<PfUserUpGradeInfo> upGradeInfos) {
        this.upGradeInfos = upGradeInfos;
    }
}
