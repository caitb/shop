package com.masiis.shop.dao.beans.family;

import java.util.List;

/**
 * 家族首页列表
 * Created by wangbingjian on 2016/8/12.
 */
public class FamilyHomeListPo {

    private List<PfUserOrganizationExtend> organizations;

    /**
     * 总数量
     */
    private Long totalCount;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 每页展示条数
     */
    private Integer pageSize;

    public List<PfUserOrganizationExtend> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<PfUserOrganizationExtend> organizations) {
        this.organizations = organizations;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
