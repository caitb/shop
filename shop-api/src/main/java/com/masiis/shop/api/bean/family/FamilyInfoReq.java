package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by jiajinghao on 2016/8/6.
 */
public class FamilyInfoReq extends BasePagingReq {

    private Integer agentLevelId;

    public Integer getAgentLevelId() {
        return agentLevelId;
    }

    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }
}
