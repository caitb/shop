package com.masiis.shop.admin.beans.product;

import com.masiis.shop.dao.po.*;

import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
public class ProductInfo {

    private ComSku comSku;
    private ComSpu comSpu;
    private PfSkuStock pfSkuStock;
    private PfSkuStatistic pfSkuStatistic;
    private List<ComSkuImage> comSkuImages;
    private List<PfSkuAgent> pfSkuAgents;
    private List<SfSkuDistribution> sfSkuDistributions;

    public ComSku getComSku() {
        return comSku;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    public ComSpu getComSpu() {
        return comSpu;
    }

    public void setComSpu(ComSpu comSpu) {
        this.comSpu = comSpu;
    }

    public PfSkuStock getPfSkuStock() {
        return pfSkuStock;
    }

    public void setPfSkuStock(PfSkuStock pfSkuStock) {
        this.pfSkuStock = pfSkuStock;
    }

    public PfSkuStatistic getPfSkuStatistic() {
        return pfSkuStatistic;
    }

    public void setPfSkuStatistic(PfSkuStatistic pfSkuStatistic) {
        this.pfSkuStatistic = pfSkuStatistic;
    }

    public List<ComSkuImage> getComSkuImages() {
        return comSkuImages;
    }

    public void setComSkuImages(List<ComSkuImage> comSkuImages) {
        this.comSkuImages = comSkuImages;
    }

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
}
