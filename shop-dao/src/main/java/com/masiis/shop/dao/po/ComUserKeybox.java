/*
 * ComUserKeybox.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-27 Created
 */
package com.masiis.shop.dao.po;

public class ComUserKeybox {

    private Long id;
    private Long comUserId;
    private String appToken;
    private String userKey;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getComUserId() {
        return comUserId;
    }
    public void setComUserId(Long comUserId) {
        this.comUserId = comUserId;
    }
    public String getAppToken() {
        return appToken;
    }
    public void setAppToken(String appToken) {
        this.appToken = appToken == null ? null : appToken.trim();
    }
    public String getUserKey() {
        return userKey;
    }
    public void setUserKey(String userKey) {
        this.userKey = userKey == null ? null : userKey.trim();
    }
}