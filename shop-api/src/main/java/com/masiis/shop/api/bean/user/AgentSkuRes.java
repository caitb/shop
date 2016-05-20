package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai_tb on 16/5/19.
 */
public class AgentSkuRes extends BaseBusinessRes {

    private List<AgentSku> agentSkus = new ArrayList<>();

    public List<AgentSku> getAgentSkus() {
        return agentSkus;
    }

    public void setAgentSkus(List<AgentSku> agentSkus) {
        this.agentSkus = agentSkus;
    }

    @Override
    public String toString() {
        return "AgentSkuRes{" +
                "agentSkus=" + agentSkus +
                '}';
    }
}
