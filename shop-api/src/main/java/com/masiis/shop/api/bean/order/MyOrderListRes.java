package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.po.PfBorder;

import java.util.List;

/**
 * Created by hw on 2016/8/5.
 */
public class MyOrderListRes extends BasePagingRes {

    private List<PfBorder> pfBorders;
    private String imgUrlPrefix;
    private Integer totalPage;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public String getImgUrlPrefix() {
        return imgUrlPrefix;
    }

    public void setImgUrlPrefix(String imgUrlPrefix) {
        this.imgUrlPrefix = imgUrlPrefix;
    }

    public List<PfBorder> getPfBorders() {
        return pfBorders;
    }

    public void setPfBorders(List<PfBorder> pfBorders) {
        this.pfBorders = pfBorders;
    }
}
