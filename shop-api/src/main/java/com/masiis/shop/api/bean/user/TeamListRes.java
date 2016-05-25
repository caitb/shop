package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/5/24.
 */
public class TeamListRes extends BaseBusinessRes {

    private Integer countAgentSku;
    private Integer countTeamMember;
    private BigDecimal totalIncomeFee;
    private List<Map<String, Object>> agentSkuMaps;

    public Integer getCountAgentSku() {
        return countAgentSku;
    }

    public void setCountAgentSku(Integer countAgentSku) {
        this.countAgentSku = countAgentSku;
    }

    public Integer getCountTeamMember() {
        return countTeamMember;
    }

    public void setCountTeamMember(Integer countTeamMember) {
        this.countTeamMember = countTeamMember;
    }

    public BigDecimal getTotalIncomeFee() {
        return totalIncomeFee;
    }

    public void setTotalIncomeFee(BigDecimal totalIncomeFee) {
        this.totalIncomeFee = totalIncomeFee;
    }

    public List<Map<String, Object>> getAgentSkuMaps() {
        return agentSkuMaps;
    }

    public void setAgentSkuMaps(List<Map<String, Object>> agentSkuMaps) {
        this.agentSkuMaps = agentSkuMaps;
    }
}
