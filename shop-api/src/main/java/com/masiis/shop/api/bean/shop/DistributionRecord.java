package com.masiis.shop.api.bean.shop;

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
    private String saleAmount;
    /**
     * 发放佣金总额
     */
    private String distributionAmount;
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

    public String getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(String saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getDistributionAmount() {
        return distributionAmount;
    }

    public void setDistributionAmount(String distributionAmount) {
        this.distributionAmount = distributionAmount;
    }

    public List<SfDistribution> getSfDistributions() {
        return sfDistributions;
    }

    public void setSfDistributions(List<SfDistribution> sfDistributions) {
        this.sfDistributions = sfDistributions;
    }
}
