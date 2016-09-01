package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.user.upgrade.UserSkuAgent;

import java.util.List;

/**
 * Created by wangbingjian on 2016/8/5.
 */
public class InitUpgradeRes extends BaseBusinessRes{

    private List<UserSkuAgent> skuAgents;

    public List<UserSkuAgent> getSkuAgents() {
        return skuAgents;
    }

    public void setSkuAgents(List<UserSkuAgent> skuAgents) {
        this.skuAgents = skuAgents;
    }
}
