/*
 * ComWxUser.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.po;

import com.masiis.shop.common.util.EmojiUtils;

import java.util.Date;

public class ComWxUser {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户id
     */
    private Long comUserId;
    /**
     * 微信同一开放平台下唯一标识
     */
    private String unionid;
    /**
     * 微信同一公众号或者移动项目下唯一标识及支付标识
     */
    private String openid;
    /**
     * 微信昵称,不同于平台用户昵称
     */
    private String nkName;
    /**
     * 性别(0女1男)
     */
    private Integer sex;
    /**
     * 微信填写的省
     */
    private String province;
    /**
     * 微信填写的市
     */
    private String city;
    /**
     * 微信填写的国家
     */
    private String country;
    /**
     * 微信头像url
     */
    private String headImgUrl;
    /**
     * 公众号或者移动项目的access_token
     */
    private String accessToken;
    /**
     * 公众号或者移动项目的refresh_token
     */
    private String refreshToken;
    /**
     * access_token过期时间
     */
    private Date atokenExpire;
    /**
     * refresh_token过期时间
     */
    private Date rtokenExpire;
    /**
     * 公众号或者移动账户的appid,用于获取微信业务所对应的openid
     */
    private String appid;

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
    public Long getComUserId() {
        return comUserId;
    }
    public void setComUserId(Long comUserId) {
        this.comUserId = comUserId;
    }
    public String getUnionid() {
        return unionid;
    }
    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }
    public String getNkName() {
        return nkName;
    }
    public void setNkName(String nkName) {
        this.nkName = nkName == null ? null : EmojiUtils.removeNonBmpUnicode(nkName.trim());
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }
    public String getHeadImgUrl() {
        return headImgUrl;
    }
    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }
    public Date getAtokenExpire() {
        return atokenExpire;
    }
    public void setAtokenExpire(Date atokenExpire) {
        this.atokenExpire = atokenExpire;
    }
    public Date getRtokenExpire() {
        return rtokenExpire;
    }
    public void setRtokenExpire(Date rtokenExpire) {
        this.rtokenExpire = rtokenExpire;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}