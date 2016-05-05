package com.masiis.shop.api.bean.base;

/**
 * 分页响应基类
 *
 * @Date 2016/5/5
 * @Auther lzh
 */
public class BasePagingRes extends BaseRes {
    /**
     * 总数量
     */
    private Integer totalCount;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * 是否有数据返回; 0,无数据; 1,有数据
     */
    private Integer hasQueryData;
    /**
     * 当前页; hasQueryData为1,此项有效; hasQueryData为0,此项无效
     */
    private Integer currentPage;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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

    public Integer getHasQueryData() {
        return hasQueryData;
    }

    public void setHasQueryData(Integer hasQueryData) {
        this.hasQueryData = hasQueryData;
    }
}
