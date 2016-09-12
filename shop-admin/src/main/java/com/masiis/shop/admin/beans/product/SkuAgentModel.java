package com.masiis.shop.admin.beans.product;

import com.masiis.shop.dao.po.PfSkuAgent;

import java.util.List;

/**
 * Created by cai_tb on 16/9/9.
 */
public class SkuAgentModel {

    private List<PfSkuAgent> pfSkuAgents;

    public List<PfSkuAgent> getPfSkuAgents() {
        return pfSkuAgents;
    }

    public void setPfSkuAgents(List<PfSkuAgent> pfSkuAgents) {
        this.pfSkuAgents = pfSkuAgents;
    }

    @Override
    public String toString() {
        return "SkuAgentModel{" +
                "pfSkuAgents=" + pfSkuAgents +
                '}';
    }
}
