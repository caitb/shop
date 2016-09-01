package com.masiis.shop.dao.beans.product;

import com.masiis.shop.dao.po.ComSku;

/**
 * Created by jiajinghao on 2016/8/5.
 * for app
 */
public class SkuInfoaAPP {

    private ComSku comSku;

    private String comSkuImgDefault;

    private Integer numOfSale;//售卖人数

    private Integer skutType;//是否主打 0 主打 1 普通

    private Integer isAgentSku;//是否代理

    private Integer maxDiscount;//最高利润

    private String slogo;//一句话介绍

    private Integer UpperisAgentSku;//上级是否代理状态 0 未代理 1 已经代理

    private Integer isBoss;//是否boss 0 否 1 是

    private Integer upgradeIsagent;//推荐人的代理状态 0 未代理 1 代理

    public ComSku getComSku() {
        return comSku;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    public String getComSkuImgDefault() {
        return comSkuImgDefault;
    }

    public void setComSkuImgDefault(String comSkuImgDefault) {
        this.comSkuImgDefault = comSkuImgDefault;
    }

    public Integer getNumOfSale() {
        return numOfSale;
    }

    public void setNumOfSale(Integer numOfSale) {
        this.numOfSale = numOfSale;
    }

    public Integer getSkutType() {
        return skutType;
    }

    public void setSkutType(Integer skutType) {
        this.skutType = skutType;
    }

    public Integer getIsAgentSku() {
        return isAgentSku;
    }

    public void setIsAgentSku(Integer isAgentSku) {
        this.isAgentSku = isAgentSku;
    }

    public Integer getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Integer maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public String getSlogo() {
        return slogo;
    }

    public void setSlogo(String slogo) {
        this.slogo = slogo;
    }

    public Integer getUpperisAgentSku() {
        return UpperisAgentSku;
    }

    public void setUpperisAgentSku(Integer upperisAgentSku) {
        UpperisAgentSku = upperisAgentSku;
    }

    public Integer getIsBoss() {
        return isBoss;
    }

    public void setIsBoss(Integer isBoss) {
        this.isBoss = isBoss;
    }

    public Integer getUpgradeIsagent() {
        return upgradeIsagent;
    }

    public void setUpgradeIsagent(Integer upgradeIsagent) {
        this.upgradeIsagent = upgradeIsagent;
    }
}
