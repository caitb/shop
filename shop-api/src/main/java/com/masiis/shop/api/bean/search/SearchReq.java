package com.masiis.shop.api.bean.search;


import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by cai_tb on 16/8/9.
 */
public class SearchReq extends BaseBusinessReq {

    private Long userId;
    private String mobile;
    private Integer brandId;//品牌id(可选参数)
    private String deleteContent;
    private Boolean clearAll;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getDeleteContent() {
        return deleteContent;
    }

    public void setDeleteContent(String deleteContent) {
        this.deleteContent = deleteContent;
    }

    public Boolean getClearAll() {
        return clearAll;
    }

    public void setClearAll(Boolean clearAll) {
        this.clearAll = clearAll;
    }

    @Override
    public String toString() {
        return "SearchReq{" +
                "userId=" + userId +
                ", mobile='" + mobile + '\'' +
                ", brandId=" + brandId +
                ", deleteContent='" + deleteContent + '\'' +
                ", clearAll=" + clearAll +
                '}';
    }
}
