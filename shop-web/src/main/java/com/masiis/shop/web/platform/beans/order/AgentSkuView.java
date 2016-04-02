package com.masiis.shop.web.platform.beans.order;

import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfSkuAgent;

import java.math.BigDecimal;

/**
 *
 * @Date:2016/4/2
 * @Auth:lzh
 */
public class AgentSkuView {
    private ComSku sku;
    private PfSkuAgent agent;
    private ComAgentLevel level;
    private BigDecimal agentFee;

    public ComSku getSku() {
        return sku;
    }

    public void setSku(ComSku sku) {
        this.sku = sku;
    }

    public PfSkuAgent getAgent() {
        return agent;
    }

    public void setAgent(PfSkuAgent agent) {
        this.agent = agent;
    }

    public BigDecimal getAgentFee() {
        return agentFee;
    }

    public void setAgentFee(BigDecimal agentFee) {
        this.agentFee = agentFee;
    }

    public ComAgentLevel getLevel() {
        return level;
    }

    public void setLevel(ComAgentLevel level) {
        this.level = level;
    }
}
