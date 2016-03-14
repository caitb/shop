package com.masiis.shop.admin.beans.order;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserTrial;

/**
 * Created by cai_tb on 16/3/5.
 */
public class TrialInfo {

    private PfUserTrial pfUserTrial;
    private ComUser comUser;
    private ComSku comSku;
    private ComUser referrer;

    public PfUserTrial getPfUserTrial() {
        return pfUserTrial;
    }

    public void setPfUserTrial(PfUserTrial pfUserTrial) {
        this.pfUserTrial = pfUserTrial;
    }

    public ComUser getComUser() {
        return comUser;
    }

    public void setComUser(ComUser comUser) {
        this.comUser = comUser;
    }

    public ComSku getComSku() {
        return comSku;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    public ComUser getReferrer() {
        return referrer;
    }

    public void setReferrer(ComUser referrer) {
        this.referrer = referrer;
    }

    @Override
    public String toString() {
        return "TrialInfo{" +
                "pfUserTrial=" + pfUserTrial +
                ", comUser=" + comUser +
                ", comSku=" + comSku +
                ", referrer=" + referrer +
                '}';
    }
}
