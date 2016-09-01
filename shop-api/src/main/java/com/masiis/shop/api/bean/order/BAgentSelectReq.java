package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * @Date 2016/8/8
 * @Author lzh
 */
public class BAgentSelectReq extends BaseBusinessReq {
    /**
     * 上级用户id,必填
     */
    private Long userPid;
    /**
     * 品牌id,必填
     */
    private Integer skuId;
    /**
     * 来源类型:1,世界市场; 2,搜索手机号; 3,扫描二维码;必填
     */
    private Integer sourceType;
    /**
     * 合伙等级id,可选
     */
    private Integer agentLevelId;

    public Long getUserPid() {
        return userPid;
    }

    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getAgentLevelId() {
        return agentLevelId;
    }

    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }
}
