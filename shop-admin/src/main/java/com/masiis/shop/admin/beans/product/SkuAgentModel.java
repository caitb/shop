package com.masiis.shop.admin.beans.product;

import com.masiis.shop.dao.po.PfSkuAgent;
import com.masiis.shop.dao.po.SfSkuDistribution;

import java.util.List;

/**
 * Created by cai_tb on 16/9/9.
 */
public class SkuAgentModel {

    private List<PfSkuAgent> pfSkuAgents;
    private List<SfSkuDistribution> sfSkuDistributions;

    public List<PfSkuAgent> getPfSkuAgents() {
        return pfSkuAgents;
    }

    public void setPfSkuAgents(List<PfSkuAgent> pfSkuAgents) {
        this.pfSkuAgents = pfSkuAgents;
    }

    public List<SfSkuDistribution> getSfSkuDistributions() {
        return sfSkuDistributions;
    }

    public void setSfSkuDistributions(List<SfSkuDistribution> sfSkuDistributions) {
        this.sfSkuDistributions = sfSkuDistributions;
    }

    @Override
    public String toString() {
        return "SkuAgentModel{" +
                "pfSkuAgents=" + pfSkuAgents +
                ", sfSkuDistributions=" + sfSkuDistributions +
                '}';
    }
}
