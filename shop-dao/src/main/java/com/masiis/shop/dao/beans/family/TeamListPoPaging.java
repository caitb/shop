package com.masiis.shop.dao.beans.family;

import java.util.List;

/**
 * Created by wangbingjian on 2016/8/23.
 */
public class TeamListPoPaging {

    /**
     * 总页数
     */
    private Integer totalPages = 0;
    /**
     * 查询页码
     */
    private Integer pageNum = 0;
    /**
     * 每页展示数量
     */
    private Integer pageSize = 0;
    /**
     * 总数量
     */
    private Long totalNum = 0l;

    private List<TeamListPo> teamListPos;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
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

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public List<TeamListPo> getTeamListPos() {
        return teamListPos;
    }

    public void setTeamListPos(List<TeamListPo> teamListPos) {
        this.teamListPos = teamListPos;
    }
}
