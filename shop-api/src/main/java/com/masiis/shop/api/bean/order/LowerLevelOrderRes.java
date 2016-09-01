package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.po.PfBorder;

import java.util.List;

/**
 * Created by hw on 2016/8/8.
 */
public class LowerLevelOrderRes extends BasePagingRes {

    private List<PfBorder> pfBorders;
    private String imgUrlPrefix;
    private Integer totalPage;//总页数

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<PfBorder> getPfBorders() {
        return pfBorders;
    }

    public void setPfBorders(List<PfBorder> pfBorders) {
        this.pfBorders = pfBorders;
    }

    public String getImgUrlPrefix() {
        return imgUrlPrefix;
    }

    public void setImgUrlPrefix(String imgUrlPrefix) {
        this.imgUrlPrefix = imgUrlPrefix;
    }
}
