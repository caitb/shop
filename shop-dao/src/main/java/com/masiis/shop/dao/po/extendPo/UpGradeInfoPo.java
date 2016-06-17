package com.masiis.shop.dao.po.extendPo;

import java.util.Date;

/**
 * 升级信息页面po
 * Created by wangbingjian on 2016/6/16.
 */
public class UpGradeInfoPo {
    /**
     * 申请信息表id
     */
    private Long upgradeId;
    /**
     * skuId
     */
    private Integer skuId;
    /**
     * skuName
     */
    private String skuName;
    /**
     * 申请人id
     */
    private Long applyId;
    /**
     * 申请人当前上级id
     */
    private Long applyPid;
    /**
     * 申请人姓名
     */
    private String applyName;
    /**
     * 当前代理等级
     */
    private Integer orgAgentId;
    /**
     * 当前代理名称
     */
    private String orgAgentName;
    /**
     * 申请代理等级
     */
    private Integer wishAgentId;
    /**
     * 申请代理名称
     */
    private String wishAgentName;
    /**
     * 申请人处理状态码
     */
    private Integer applyStatus;
    /**
     * 升级处理状态码
     */
    private Integer upStatus;
    /**
     * 申请时间
     */
    private Date createTime;
    /**
     * 申请码
     */
    private String applyCode;

    public Long getUpgradeId() {
        return upgradeId;
    }

    public void setUpgradeId(Long upgradeId) {
        this.upgradeId = upgradeId;
    }

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

    public Long getApplyId() {
        return applyId;
    }

    public Long getApplyPid() {
        return applyPid;
    }

    public void setApplyPid(Long applyPid) {
        this.applyPid = applyPid;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Integer getOrgAgentId() {
        return orgAgentId;
    }

    public void setOrgAgentId(Integer orgAgentId) {
        this.orgAgentId = orgAgentId;
    }

    public String getOrgAgentName() {
        return orgAgentName;
    }

    public void setOrgAgentName(String orgAgentName) {
        this.orgAgentName = orgAgentName;
    }

    public Integer getWishAgentId() {
        return wishAgentId;
    }

    public void setWishAgentId(Integer wishAgentId) {
        this.wishAgentId = wishAgentId;
    }

    public String getWishAgentName() {
        return wishAgentName;
    }

    public void setWishAgentName(String wishAgentName) {
        this.wishAgentName = wishAgentName;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Integer getUpStatus() {
        return upStatus;
    }

    public void setUpStatus(Integer upStatus) {
        this.upStatus = upStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }
}
