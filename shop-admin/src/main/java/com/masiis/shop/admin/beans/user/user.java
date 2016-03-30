package com.masiis.shop.admin.beans.user;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;

/**
 * Created by cai_tb on 16/3/30.
 */
public class User {

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

    @Override
    public String toString() {
        return "User{" +
                "comUser=" + comUser +
                ", comUserAccount=" + comUserAccount +
                '}';
    }
}
