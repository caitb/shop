package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.user.AgentSkuViewInfo;
import com.masiis.shop.dao.po.ComBrand;

import java.util.List;

/**
 * @Date 2016/8/8
 * @Author lzh
 */
public class BAgentSelectRes extends BaseBusinessRes {
    /**
     * 家族信息
     */
    private String fName;
    private String fGeneral;
    private String fUrl;
    /**
     * 如果指定,上级姓名
     */
    private String pName;
    /**
     * 是否指定品牌: 0,未指定; 1,已指定
     */
    private Integer isAssignBrand;
    /**
     * 指定品牌主打商品
     */
    private ComBrand brand;

    private Integer isAssignLevel;

    private List<AgentSkuViewInfo> views;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfGeneral() {
        return fGeneral;
    }

    public void setfGeneral(String fGeneral) {
        this.fGeneral = fGeneral;
    }

    public String getfUrl() {
        return fUrl;
    }

    public void setfUrl(String fUrl) {
        this.fUrl = fUrl;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getIsAssignBrand() {
        return isAssignBrand;
    }

    public void setIsAssignBrand(Integer isAssignBrand) {
        this.isAssignBrand = isAssignBrand;
    }

    public ComBrand getBrand() {
        return brand;
    }

    public void setBrand(ComBrand brand) {
        this.brand = brand;
    }

    public Integer getIsAssignLevel() {
        return isAssignLevel;
    }

    public void setIsAssignLevel(Integer isAssignLevel) {
        this.isAssignLevel = isAssignLevel;
    }

    public List<AgentSkuViewInfo> getViews() {
        return views;
    }

    public void setViews(List<AgentSkuViewInfo> views) {
        this.views = views;
    }
}
