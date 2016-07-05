package com.masiis.shop.dao.beans.message;

import java.util.List;

/**
 * @Date 2016/7/5
 * @Author lzh
 */
public class SfMessagePageList {
    private Integer cur;
    private Integer totalSize;
    private Integer pageSize;
    private List<SfMessageDetail> detailList;

    public Integer getCur() {
        return cur;
    }

    public void setCur(Integer cur) {
        this.cur = cur;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<SfMessageDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<SfMessageDetail> detailList) {
        this.detailList = detailList;
    }
}
