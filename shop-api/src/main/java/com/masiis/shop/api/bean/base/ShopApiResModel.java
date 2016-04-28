package com.masiis.shop.api.bean.base;

/**
 * Created by wangbingjian on 2016/4/22.
 */
public class ShopApiResModel extends BaseRes {
    /**
     * 总数量
     */
    private int totalCount;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 每页条数
     */
    private int pageSize;

    private Object data;

    public ShopApiResModel() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ShopApiResModel(Object data) {
        super();
        this.data = data;
    }

    public ShopApiResModel(String code, String msg){
        super(code, msg);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
