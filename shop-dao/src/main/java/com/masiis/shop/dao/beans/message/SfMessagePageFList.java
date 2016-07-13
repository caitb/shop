package com.masiis.shop.dao.beans.message;

import com.masiis.shop.dao.po.ComUser;

import java.util.List;

/**
 * @Date 2016/7/5
 * @Author lzh
 */
public class SfMessagePageFList {
    private Integer cur;
    private Long totalPage;
    private Integer pageSize;
    private Integer isLast;
    private Integer hasData;
    private ComUser fromUser;
    private List<SfMessageDetail> detailList;

    public Integer getCur() {
        return cur;
    }

    public void setCur(Integer cur) {
        this.cur = cur;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getIsLast() {
        return isLast;
    }

    public void setIsLast(Integer isLast) {
        this.isLast = isLast;
    }

    public Integer getHasData() {
        return hasData;
    }

    public void setHasData(Integer hasData) {
        this.hasData = hasData;
    }

    public ComUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(ComUser fromUser) {
        this.fromUser = fromUser;
    }

    public List<SfMessageDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<SfMessageDetail> detailList) {
        this.detailList = detailList;
    }
}
