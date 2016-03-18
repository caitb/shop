package com.masiis.shop.dao.beans.extract;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.ComUserExtractApply;

/**
 * @Author xuechao
 * @Date 2016/3/18 11:56.
 */
public class ExtractApply extends ComUserExtractApply {
    private ComUser comUser;
    private ComUserAccount comUserAccount;

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
}

