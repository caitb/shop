package com.masiis.shop.api.bean.user;

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
    private String levelName;
    private Integer levelId;
    private BigDecimal agentFee;
    private BigDecimal sinFee;
    private BigDecimal bailFee;
    private Integer isShow;
    private Integer quantity;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public BigDecimal getAgentFee() {
        return agentFee;
    }

    public void setAgentFee(BigDecimal agentFee) {
        this.agentFee = agentFee;
    }

    public BigDecimal getSinFee() {
        return sinFee;
    }

    public void setSinFee(BigDecimal sinFee) {
        this.sinFee = sinFee;
    }

    public BigDecimal getBailFee() {
        return bailFee;
    }

    public void setBailFee(BigDecimal bailFee) {
        this.bailFee = bailFee;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
