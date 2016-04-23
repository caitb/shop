package com.masiis.shop.api.bean.shop;

import java.math.BigDecimal;
import java.util.List;

/**
 * 分校记录
 * Created by wangbingjian on 2016/4/23.
 */
public class DistributionRecord {

    /**
     * 店铺总参与人数
     */
    private Integer sumLevel;
    /**
     * 店铺总销售额
     */
    private BigDecimal saleAmount;
    /**
     * 发放佣金总额
     */
    private BigDecimal distributionAmount;
    /**
     * 分销记录列表
     */
    private List<SfDistribution> sfDistributions;

    public Integer getSumLevel() {
        return sumLevel;
    }

    public void setSumLevel(Integer sumLevel) {
        this.sumLevel = sumLevel;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public BigDecimal getDistributionAmount() {
        return distributionAmount;
    }

    public void setDistributionAmount(BigDecimal distributionAmount) {
        this.distributionAmount = distributionAmount;
    }

    public List<SfDistribution> getSfDistributions() {
        return sfDistributions;
    }

    public void setSfDistributions(List<SfDistribution> sfDistributions) {
        this.sfDistributions = sfDistributions;
    }
}
