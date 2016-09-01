package com.masiis.shop.api.bean.family;


import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * 招商展示参数
 * Created by cai_tb on 16/8/10.
 */
public class MerchantsReq extends BaseBusinessReq {

    private Long    userPid;
    private Integer brandId;
    private Integer levelId;
    private Integer sourceType;

    public Long getUserPid() {
        return userPid;
    }

    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    @Override
    public String toString() {
        return "MerchantsReq{" +
                "userPid=" + userPid +
                ", brandId=" + brandId +
                ", levelId=" + levelId +
                ", sourceType=" + sourceType +
                '}';
    }
}
