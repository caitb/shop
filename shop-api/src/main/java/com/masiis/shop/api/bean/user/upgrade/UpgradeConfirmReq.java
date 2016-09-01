package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by wangbingjian on 2016/8/5.
 */
public class UpgradeConfirmReq extends BaseBusinessReq{
    /**
     * 当前代理等级
     */
    private Integer curAgentLevel;
    /**
     * 申请代理等级
     */
    private Integer upgradeLevel;
    /**
     * 商品id
     */
    private Integer skuId;
    /**
     * 代理上级用户id
     */
    private Long userPid;

    public Integer getCurAgentLevel() {
        return curAgentLevel;
    }

    public void setCurAgentLevel(Integer curAgentLevel) {
        this.curAgentLevel = curAgentLevel;
    }

    public Integer getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(Integer upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Long getUserPid() {
        return userPid;
    }

    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }
}
