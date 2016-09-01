package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BasePagingReq;
import com.masiis.shop.api.bean.base.BasePagingRes;

import java.math.BigDecimal;

/**
 * Created by jiajinghao on 2016/8/8.
 */
public class UpperFamilyReq extends BasePagingReq {

    private Integer agentLevelId;

    private Integer reorder;//是否重排 0 默认 1 需要重排

    private Integer brandId;

    public Integer getAgentLevelId() {
        return agentLevelId;
    }

    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }

    public Integer getReorder() {
        return reorder;
    }

    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
