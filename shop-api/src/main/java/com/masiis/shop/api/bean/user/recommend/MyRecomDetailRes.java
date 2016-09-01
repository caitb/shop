package com.masiis.shop.api.bean.user.recommend;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

import java.math.BigDecimal;

/**
 * Created by wangbingjian on 2016/8/6.
 */
public class MyRecomDetailRes extends BaseBusinessRes {
    /**
     * 进货数量
     */
    private Integer purchaseNum;
    /**
     * 进货总额
     */
    private BigDecimal purchaseAmount;
    /**
     *他的推荐团队
     */
    private Integer recommendTeam;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 代理等级名称
     */
    private String agentLevelName;
    /**
     * 代理图片地址
     */
    private String levelIcon;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 认证状态编码
     */
    private Integer auditStatusCode;
    /**
     * 认证状态
     */
    private String auditStatus;
    /**
     * 微信号
     */
    private String wxId;
    /**
     * 微信昵称
     */
    private String wxNickName;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 授权书地址
     */
    private String certificateIcon;

    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Integer getRecommendTeam() {
        return recommendTeam;
    }

    public void setRecommendTeam(Integer recommendTeam) {
        this.recommendTeam = recommendTeam;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getAgentLevelName() {
        return agentLevelName;
    }

    public void setAgentLevelName(String agentLevelName) {
        this.agentLevelName = agentLevelName;
    }

    public String getLevelIcon() {
        return levelIcon;
    }

    public void setLevelIcon(String levelIcon) {
        this.levelIcon = levelIcon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCertificateIcon() {
        return certificateIcon;
    }

    public void setCertificateIcon(String certificateIcon) {
        this.certificateIcon = certificateIcon;
    }

    public Integer getAuditStatusCode() {
        return auditStatusCode;
    }

    public void setAuditStatusCode(Integer auditStatusCode) {
        this.auditStatusCode = auditStatusCode;
    }
}
