package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * Created by cai_tb on 16/5/19.
 */
public class AgentSkuRes extends BaseBusinessRes {

    private Integer skuId;

    private String skuName;

    private Integer levelId;

    private String levelName;

    private Integer userSkuId;

    private String brandLogo;

    /* 是否可发展 */
    private Boolean develop;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getUserSkuId() {
        return userSkuId;
    }

    public void setUserSkuId(Integer userSkuId) {
        this.userSkuId = userSkuId;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public Boolean getDevelop() {
        return develop;
    }

    public void setDevelop(Boolean develop) {
        this.develop = develop;
    }

    @Override
    public String toString() {
        return "AgentSkuRes{" +
                "skuId=" + skuId +
                ", skuName='" + skuName + '\'' +
                ", levelId=" + levelId +
                ", levelName='" + levelName + '\'' +
                ", userSkuId=" + userSkuId +
                ", brandLogo='" + brandLogo + '\'' +
                ", develop=" + develop +
                '}';
    }
}
