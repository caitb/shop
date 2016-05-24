package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.api.bean.base.BaseRes;


/**
 * Created by wangbingjian on 2016/5/23.
 */
public class ItemDistributionRes extends BaseRes {

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
}
