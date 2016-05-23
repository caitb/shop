package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * @Date 2016/5/23
 * @Auther lzh
 */
public class PartnerPreApplyRes extends BaseBusinessRes {
    private Integer isAgent;
    private Integer isBind;
    private Integer isQueuing;
    private Integer queueNum;

    public Integer getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(Integer isAgent) {
        this.isAgent = isAgent;
    }

    public Integer getIsBind() {
        return isBind;
    }

    public void setIsBind(Integer isBind) {
        this.isBind = isBind;
    }

    public Integer getIsQueuing() {
        return isQueuing;
    }

    public void setIsQueuing(Integer isQueuing) {
        this.isQueuing = isQueuing;
    }

    public Integer getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(Integer queueNum) {
        this.queueNum = queueNum;
    }
}
