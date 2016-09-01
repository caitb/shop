package com.masiis.shop.dao.beans.family;

import com.masiis.shop.dao.po.PfUserOrganization;

import java.math.BigDecimal;

/**
 * Created by jiajinghao on 2016/8/6.
 */
public class FamilyList {

    private PfUserOrganization pfUserOrganization;

    private Integer numOfFamily;//团队人数

    private BigDecimal saleNum;//销售额


    public Integer getNumOfFamily() {
        return numOfFamily;
    }

    public void setNumOfFamily(Integer numOfFamily) {
        this.numOfFamily = numOfFamily;
    }

    public PfUserOrganization getPfUserOrganization() {
        return pfUserOrganization;
    }

    public void setPfUserOrganization(PfUserOrganization pfUserOrganization) {
        this.pfUserOrganization = pfUserOrganization;
    }

    public BigDecimal getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(BigDecimal saleNum) {
        this.saleNum = saleNum;
    }

}
