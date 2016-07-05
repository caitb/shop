package com.masiis.shop.dao.beans.user;

import java.util.Date;

/**
 * 代言人、粉丝列表展示信息
 * Created by wangbingjian on 2016/7/5.
 */
public class SfSpokesAndFansInfo {

    /**
     * 用户微信昵称
     */
    private String wxName;
    /**
     * 头像路径
     */
    private String headImg;
    /**
     * 展示ID
     */
    private String ID;
    /**
     * 是否为代言人
     */
    private Integer sopkenMan;
    /**
     * 创建关系时间
     */
    private Date createTime;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户粉丝级别
     */
    private Integer userLevel;
    /**
     * 小铺id
     */
    private Long shopId;
    /**
     * 是否已经在小铺购买
     */
    private Integer isBuy;

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Integer getSopkenMan() {
        return sopkenMan;
    }

    public void setSopkenMan(Integer sopkenMan) {
        this.sopkenMan = sopkenMan;
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

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }
}
