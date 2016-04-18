package com.masiis.shop.admin.beans.fundmanage;

import com.masiis.shop.dao.po.*;

/**
 * Created by cai_tb on 16/4/1.
 */
public class ExtractApply {

    private ComUser comUser;
    private ComUserAccount comUserAccount;
    private ComUserExtractApply comUserExtractApply;
    private SfUserExtractApply sfUserExtractApply;

    public ComUser getComUser() {
        return comUser;
    }

    public void setComUser(ComUser comUser) {
        this.comUser = comUser;
    }

    public ComUserAccount getComUserAccount() {
        return comUserAccount;
    }

    public void setComUserAccount(ComUserAccount comUserAccount) {
        this.comUserAccount = comUserAccount;
    }

    public ComUserExtractApply getComUserExtractApply() {
        return comUserExtractApply;
    }

    public void setComUserExtractApply(ComUserExtractApply comUserExtractApply) {
        this.comUserExtractApply = comUserExtractApply;
    }

    public SfUserExtractApply getSfUserExtractApply() {
        return sfUserExtractApply;
    }

    public void setSfUserExtractApply(SfUserExtractApply sfUserExtractApply) {
        this.sfUserExtractApply = sfUserExtractApply;
    }

    @Override
    public String toString() {
        return "ExtractApply{" +
                "comUser=" + comUser +
                ", comUserAccount=" + comUserAccount +
                ", comUserExtractApply=" + comUserExtractApply +
                ", sfUserExtractApply=" + sfUserExtractApply +
                '}';
    }
}
