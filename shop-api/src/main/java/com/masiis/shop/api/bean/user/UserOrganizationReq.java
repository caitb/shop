package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.dao.po.PfUserOrganization;

/**
 * Created by cai_tb on 16/8/8.
 */
public class UserOrganizationReq extends BaseBusinessReq {

    private Integer organizationId;
    private Long userPid;
    private String userPids;
    private Integer brandId;
    private Integer agentLevelId;



    private String slogan;
    private String introduction;
    private String wxQrCode;
    private String wxId;
    private String logo;

    private String backImg;
    private String addDescription;
    private String name;

    private PfUserOrganization pfUserOrganization;

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Long getUserPid() {
        return userPid;
    }

    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }

    public String getUserPids() {
        return userPids;
    }

    public void setUserPids(String userPids) {
        this.userPids = userPids;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getAgentLevelId() {
        return agentLevelId;
    }

    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getWxQrCode() {
        return wxQrCode;
    }

    public void setWxQrCode(String wxQrCode) {
        this.wxQrCode = wxQrCode;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    public String getAddDescription() {
        return addDescription;
    }

    public void setAddDescription(String addDescription) {
        this.addDescription = addDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PfUserOrganization getPfUserOrganization() {
        return pfUserOrganization;
    }

    public void setPfUserOrganization(PfUserOrganization pfUserOrganization) {
        this.pfUserOrganization = pfUserOrganization;
    }

    @Override
    public String toString() {
        return "UserOrganizationReq{" +
                "organizationId=" + organizationId +
                ", userPid=" + userPid +
                ", userPids='" + userPids + '\'' +
                ", brandId=" + brandId +
                ", agentLevelId=" + agentLevelId +
                ", slogan='" + slogan + '\'' +
                ", introduction='" + introduction + '\'' +
                ", wxQrCode='" + wxQrCode + '\'' +
                ", wxId='" + wxId + '\'' +
                ", logo='" + logo + '\'' +
                ", backImg='" + backImg + '\'' +
                ", addDescription='" + addDescription + '\'' +
                ", name='" + name + '\'' +
                ", pfUserOrganization=" + pfUserOrganization +
                '}';
    }
}
