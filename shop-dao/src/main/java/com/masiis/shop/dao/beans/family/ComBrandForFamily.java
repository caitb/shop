package com.masiis.shop.dao.beans.family;

import com.masiis.shop.dao.po.ComBrand;
import com.masiis.shop.dao.po.PfUserBrand;

/**
 * Created by jiajinghao on 2016/8/5.
 */
public class ComBrandForFamily {

    private ComBrand comBrand;

    private PfUserBrand pfUserBrand;

    private Integer numOfAgent;

    public PfUserBrand getPfUserBrand() {
        return pfUserBrand;
    }

    public void setPfUserBrand(PfUserBrand pfUserBrand) {
        this.pfUserBrand = pfUserBrand;
    }

    public ComBrand getComBrand() {
        return comBrand;
    }

    public void setComBrand(ComBrand comBrand) {
        this.comBrand = comBrand;
    }

    public Integer getNumOfAgent() {
        return numOfAgent;
    }

    public void setNumOfAgent(Integer numOfAgent) {
        this.numOfAgent = numOfAgent;
    }
}
