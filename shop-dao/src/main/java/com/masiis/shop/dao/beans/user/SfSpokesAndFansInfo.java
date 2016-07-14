package com.masiis.shop.dao.beans.user;

import java.text.SimpleDateFormat;
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
     * 是否为代言人，页面展示
     */
    private String sopkenManView = "";
    /**
     * 创建关系时间
     */
    private Date createTime;
    /**
     * 创建时间，页面展示
     */
    private String createTimeView;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户粉丝级别
     */
    private Integer userLevel;
    /**
     * 页面展示粉丝级别
     */
    private String userLevelView;
    /**
     * 小铺id
     */
    private Long shopId;
    /**
     * 是否已经在小铺购买
     */
    private Integer isBuy;
    /**
     * 是否已经购买，页面展示
     */
    private String isBuyView = "";
    /**
     * 代言人数量
     */
    private Integer spokesManNum = 0;
    /**
     * 粉丝数量
     */
    private Integer fansNum = 0;

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
        switch (sopkenMan){
            case 1 : {
                setSopkenManView("已代言");
                break;
            }
        }
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        setCreateTimeView(format.format(createTime));
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
        switch (userLevel){
            case 1 : {
                setUserLevelView("一级");
                break;
            }
            case 2 : {
                setUserLevelView("二级");
                break;
            }
            case 3 : {
                setUserLevelView("三级");
                break;
            }
        }
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
        switch (isBuy.intValue()){
            case 0 : {
                setIsBuyView("未购买");
                break;
            }
            case 1 : {
                setIsBuyView("已购买");
                break;
            }
        }
    }

    public String getUserLevelView() {
        return userLevelView;
    }

    public void setUserLevelView(String userLevelView) {
        this.userLevelView = userLevelView;
    }

    public String getSopkenManView() {
        return sopkenManView;
    }

    public void setSopkenManView(String sopkenManView) {
        this.sopkenManView = sopkenManView;
    }

    public String getCreateTimeView() {
        return createTimeView;
    }

    public void setCreateTimeView(String createTimeView) {
        this.createTimeView = createTimeView;
    }

    public String getIsBuyView() {
        return isBuyView;
    }

    public void setIsBuyView(String isBuyView) {
        this.isBuyView = isBuyView;
    }

    public Integer getSpokesManNum() {
        return spokesManNum;
    }

    public void setSpokesManNum(Integer spokesManNum) {
        this.spokesManNum = spokesManNum;
    }

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }
}
