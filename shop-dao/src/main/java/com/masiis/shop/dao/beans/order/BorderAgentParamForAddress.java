package com.masiis.shop.dao.beans.order;

/**
 * Created by hzz on 2016/4/18.
 *
 *  代理订单参数为选择地址时使用
 *
 *
 */
public class BorderAgentParamForAddress {

    private Integer skuId;
    private Integer agentLevelId;
    private String  weiXinId;
    private Integer sendType;

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

    public String getWeiXinId() {
        return weiXinId;
    }

    public void setWeiXinId(String weiXinId) {
        this.weiXinId = weiXinId;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }
}
