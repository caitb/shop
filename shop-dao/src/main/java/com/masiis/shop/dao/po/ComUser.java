/*
 * ComUserNew.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.po;

import java.io.Serializable;
import java.util.Date;

public class ComUser implements Serializable {

    private static final long serialVersionUID = -2543606660018018096L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 电话号码,用户唯一标识
     */
    private String mobile;
    /**
     * 用户注册邮箱
     */
    private String userMail;
    /**
     * 用户头像
     */
    private String wxHeadImg;
    /**
     * 用户在平台的昵称,不同于微信昵称
     */
    private String wxNkName;
    /**
     * 性别(0女1男)
     */
    private Integer sex;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 身份证前面图片url
     */
    private String idCardFrontUrl;
    /**
     * 身份证后面图片url
     */
    private String idCardBackUrl;
    /**
     * 微信号(手填)
     */
    private String wxId;
    /**
     * 是否绑定（0否1是）
     */
    private Integer isBinding;
    /**
     * 是否合伙人(0否1是)
     */
    private Integer isAgent;
    /**
     * 拿货方式: 0,未选择; 1,平台代发; 2,自己发货
     */
    private Integer sendType;
    /**
     * 审核状态(0未审核1已提交审核(审核中)2审核通过3审核不通过)
     */
    private Integer auditStatus;
    /**
     * 审核通过或不通过原因
     */
    private String auditReason;
    /**
     * 微信unionid,微信用户唯一标识
     */
    private String wxUnionid;
    private String password;
    /**
     * pwd的盐
     */
    private String pwdSalt;
    /**
     * 注册来源: 0,微信注册; 1,app注册
     */
    private Integer registerSource;
    /**
     * app身份令牌
     */
    private String appToken;
    /**
     * appToken过期时间
     */
    private Date appTokenExpire;
    private Integer isBuy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail == null ? null : userMail.trim();
    }

    public String getWxHeadImg() {
        return wxHeadImg;
    }

    public void setWxHeadImg(String wxHeadImg) {
        this.wxHeadImg = wxHeadImg;
    }

    public String getWxNkName() {
        return wxNkName;
    }

    public void setWxNkName(String wxNkName) {
        this.wxNkName = wxNkName == null ? null : wxNkName.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getIdCardFrontUrl() {
        return idCardFrontUrl;
    }

    public void setIdCardFrontUrl(String idCardFrontUrl) {
        this.idCardFrontUrl = idCardFrontUrl == null ? null : idCardFrontUrl.trim();
    }

    public String getIdCardBackUrl() {
        return idCardBackUrl;
    }

    public void setIdCardBackUrl(String idCardBackUrl) {
        this.idCardBackUrl = idCardBackUrl == null ? null : idCardBackUrl.trim();
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId == null ? null : wxId.trim();
    }

    public Integer getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(Integer isAgent) {
        this.isAgent = isAgent;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason == null ? null : auditReason.trim();
    }

    public String getWxUnionid() {
        return wxUnionid;
    }

    public void setWxUnionid(String wxUnionid) {
        this.wxUnionid = wxUnionid == null ? null : wxUnionid.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPwdSalt() {
        return pwdSalt;
    }

    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt == null ? null : pwdSalt.trim();
    }

    public Integer getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(Integer registerSource) {
        this.registerSource = registerSource;
    }

    public Integer getIsBinding() {
        return isBinding;
    }

    public void setIsBinding(Integer isBinding) {
        this.isBinding = isBinding;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public Date getAppTokenExpire() {
        return appTokenExpire;
    }

    public void setAppTokenExpire(Date appTokenExpire) {
        this.appTokenExpire = appTokenExpire;
    }

    public Integer getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }
}