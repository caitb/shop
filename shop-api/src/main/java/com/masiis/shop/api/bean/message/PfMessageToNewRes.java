package com.masiis.shop.api.bean.message;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.message.PfMessageToNewBean;
import com.masiis.shop.dao.beans.user.upgrade.UserSkuAgent;

import java.util.List;

/**
 * @Date 2016/8/2
 * @Author lzh
 */
public class PfMessageToNewRes extends BaseBusinessRes {
    private Integer childAgentNum;
    private String baseName;
    private List<PfMessageToNewBean> tos;
    private List<UserSkuAgent> skus;

    public Integer getChildAgentNum() {
        return childAgentNum;
    }

    public void setChildAgentNum(Integer childAgentNum) {
        this.childAgentNum = childAgentNum;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public List<PfMessageToNewBean> getTos() {
        return tos;
    }

    public void setTos(List<PfMessageToNewBean> tos) {
        this.tos = tos;
    }

    public List<UserSkuAgent> getSkus() {
        return skus;
    }

    public void setSkus(List<UserSkuAgent> skus) {
        this.skus = skus;
    }
}
