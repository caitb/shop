package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by wangbingjian on 2016/8/5.
 */
public class UpGradePackageReq extends BaseBusinessReq {
    /**
     * 商品id
     */
    private Integer skuId;
    /**
     * 等级id
     */
    private Integer agentLevelId;
    /**
     * 上级用户id
     */
    private Long userPid;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getAgentLevelId() {
        return agentLevelId;
    }

    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }

    public Long getUserPid() {
        return userPid;
    }

    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }

}
