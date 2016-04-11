package com.masiis.shop.admin.beans.user;

import com.masiis.shop.dao.po.*;

/**
 * 代理商(合伙人)
 * Created by cai_tb on 16/4/11.
 */
public class AgentUser {

    private PfUserCertificate pfUserCertificate;
    private PfUserSku pfUserSku;
    private PfUserSkuStock pfUserSkuStock;
    private ComUser comUser;
    private ComUser parentUser;
    private ComSku comSku;
    private ComAgentLevel comAgentLevel;

    public PfUserCertificate getPfUserCertificate() {
        return pfUserCertificate;
    }

    public void setPfUserCertificate(PfUserCertificate pfUserCertificate) {
        this.pfUserCertificate = pfUserCertificate;
    }

    public PfUserSku getPfUserSku() {
        return pfUserSku;
    }

    public void setPfUserSku(PfUserSku pfUserSku) {
        this.pfUserSku = pfUserSku;
    }

    public PfUserSkuStock getPfUserSkuStock() {
        return pfUserSkuStock;
    }

    public void setPfUserSkuStock(PfUserSkuStock pfUserSkuStock) {
        this.pfUserSkuStock = pfUserSkuStock;
    }

    public ComUser getComUser() {
        return comUser;
    }

    public void setComUser(ComUser comUser) {
        this.comUser = comUser;
    }

    public ComUser getParentUser() {
        return parentUser;
    }

    public void setParentUser(ComUser parentUser) {
        this.parentUser = parentUser;
    }

    public ComSku getComSku() {
        return comSku;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    public ComAgentLevel getComAgentLevel() {
        return comAgentLevel;
    }

    public void setComAgentLevel(ComAgentLevel comAgentLevel) {
        this.comAgentLevel = comAgentLevel;
    }

    @Override
    public String toString() {
        return "AgentUser{" +
                "pfUserCertificate=" + pfUserCertificate +
                ", pfUserSku=" + pfUserSku +
                ", pfUserSkuStock=" + pfUserSkuStock +
                ", comUser=" + comUser +
                ", parentUser=" + parentUser +
                ", comSku=" + comSku +
                ", comAgentLevel=" + comAgentLevel +
                '}';
    }
}
