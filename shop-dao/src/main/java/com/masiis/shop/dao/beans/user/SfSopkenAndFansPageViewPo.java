package com.masiis.shop.dao.beans.user;

import java.util.Date;

/**
 * mall 粉丝列表或代言人列表展示页面PO
 * Created by wangbingjian on 2016/7/4.
 */
public class SfSopkenAndFansPageViewPo {
    /**
     * 总数量
     */
    private Integer totalCount;
    /**
     * 第一级数量
     */
    private Integer firstCount;
    /**
     * 第二级数量
     */
    private Integer secondCount;
    /**
     * 第三极数量
     */
    private Integer thirdCount;
    /**
     * 用户微信昵称
     */
    private String wxName;
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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getFirstCount() {
        return firstCount;
    }

    public void setFirstCount(Integer firstCount) {
        this.firstCount = firstCount;
    }

    public Integer getSecondCount() {
        return secondCount;
    }

    public void setSecondCount(Integer secondCount) {
        this.secondCount = secondCount;
    }

    public Integer getThirdCount() {
        return thirdCount;
    }

    public void setThirdCount(Integer thirdCount) {
        this.thirdCount = thirdCount;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
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
