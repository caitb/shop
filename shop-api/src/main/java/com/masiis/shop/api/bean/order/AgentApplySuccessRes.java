package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * @Date 2016/8/16
 * @Author lzh
 */
public class AgentApplySuccessRes extends BaseBusinessRes {
    private String userName;
    private String brandName;
    private String agentLevelName;
    private String pName;
    private Integer isRealNameAuth;
    private String notRealNameAuthDesc;
    private Integer isMPS;
    private Integer mpsNum;
    private Long orderId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getAgentLevelName() {
        return agentLevelName;
    }

    public void setAgentLevelName(String agentLevelName) {
        this.agentLevelName = agentLevelName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getIsRealNameAuth() {
        return isRealNameAuth;
    }

    public void setIsRealNameAuth(Integer isRealNameAuth) {
        this.isRealNameAuth = isRealNameAuth;
    }

    public String getNotRealNameAuthDesc() {
        return notRealNameAuthDesc;
    }

    public void setNotRealNameAuthDesc(String notRealNameAuthDesc) {
        this.notRealNameAuthDesc = notRealNameAuthDesc;
    }

    public Integer getIsMPS() {
        return isMPS;
    }

    public void setIsMPS(Integer isMPS) {
        this.isMPS = isMPS;
    }

    public Integer getMpsNum() {
        return mpsNum;
    }

    public void setMpsNum(Integer mpsNum) {
        this.mpsNum = mpsNum;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
