package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * @Date 2016/8/26
 * @Author lzh
 */
public class UpgradePaySuccessInfoRes extends BaseBusinessRes {
    private String userName;
    private String skuName;
    private String oldParentName;
    private String oldLevelName;
    private String nowParentName;
    private String nowLevelName;
    private String stockChangeView;

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

    public String getOldParentName() {
        return oldParentName;
    }

    public void setOldParentName(String oldParentName) {
        this.oldParentName = oldParentName;
    }

    public String getOldLevelName() {
        return oldLevelName;
    }

    public void setOldLevelName(String oldLevelName) {
        this.oldLevelName = oldLevelName;
    }

    public String getNowParentName() {
        return nowParentName;
    }

    public void setNowParentName(String nowParentName) {
        this.nowParentName = nowParentName;
    }

    public String getNowLevelName() {
        return nowLevelName;
    }

    public void setNowLevelName(String nowLevelName) {
        this.nowLevelName = nowLevelName;
    }

    public String getStockChangeView() {
        return stockChangeView;
    }

    public void setStockChangeView(String stockChangeView) {
        this.stockChangeView = stockChangeView;
    }
}
