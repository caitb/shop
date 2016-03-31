package com.masiis.shop.admin.beans.user;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cai_tb on 16/3/30.
 */
public class User {

    private ComUser comUser;
    private ComUserAccount comUserAccount;
    private Map<String, Object> wxAgentPro;

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

    public Map<String, Object> getWxAgentPro() {
        return wxAgentPro;
    }

    public void setWxAgentPro(Map<String, Object> wxAgentPro) {
        this.wxAgentPro = wxAgentPro;
    }

    @Override
    public String toString() {
        return "User{" +
                "comUser=" + comUser +
                ", comUserAccount=" + comUserAccount +
                ", wxAgentPro=" + wxAgentPro +
                '}';
    }
}
