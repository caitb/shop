package com.masiis.shop.api.bean.base;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
public class BasePagingReq extends BaseReq {
    /**
     * 请求的页数
     */
    private Integer pageNum;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
