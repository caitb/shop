package com.masiis.shop.dao.beans.family;


import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangbingjian on 2016/8/23.
 */
public class TeamMemberInfo {
    /**
     * 进货次数
     */
    private Integer purchaseTimes;
    /**
     * 进货总额
     */
    private BigDecimal purchaseAmount;
    /**
     * 下级合伙人
     */
    private Integer lowPartner;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 代理等级名称
     */
    private String agentLevelName;
    /**
     * 代理等级
     */
    private Integer agentLevel;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 微信id
     */
    private String wxId;
    /**
     * 认证状态
     */
    private String auditStatus;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 证书地址
     */
    private String certImg;
    /**
     * 加入时间
     */
    private Date createTime;

    private Integer Status;

    private Long userId;

    private Long userPid;

    private Integer skuId;

    public Integer getPurchaseTimes() {
        return purchaseTimes;
    }

    public void setPurchaseTimes(Integer purchaseTimes) {
        this.purchaseTimes = purchaseTimes;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Integer getLowPartner() {
        return lowPartner;
    }

    public void setLowPartner(Integer lowPartner) {
        this.lowPartner = lowPartner;
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

    public Integer getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(Integer agentLevel) {
        this.agentLevel = agentLevel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
//        this.auditStatus = AuditStatusEnum.getName(Integer.valueOf(auditStatus));
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCertImg() {
        return certImg;
    }

    public void setCertImg(String certImg) {
        this.certImg = certImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserPid() {
        return userPid;
    }

    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }
}
