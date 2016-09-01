package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.product.AgentSku;

import java.util.List;

/**
 * Created by jiajinghao on 2016/8/17.
 */
public class ProDetailNotAgentRes extends BaseRes{

    private List<AgentSku> agentSkuList;

    public List<AgentSku> getAgentSkuList() {
        return agentSkuList;
    }

    public void setAgentSkuList(List<AgentSku> agentSkuList) {
        this.agentSkuList = agentSkuList;
    }
}
