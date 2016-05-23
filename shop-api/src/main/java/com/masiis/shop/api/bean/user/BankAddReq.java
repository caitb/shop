package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by wangbingjian on 2016/5/19.
 */
public class BankAddReq extends BaseReq {
    private String token;
    /**
     * 银行卡号
     */
    private String bankcard;
    /**
     * 银行基础表id
     */
    private Integer bankId;
    /**
     * 开户行名称
     */
    private String openedBankName;
    /**
     * 持卡人姓名
     */
    private String bankOwner;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getOpenedBankName() {
        return openedBankName;
    }

    public void setOpenedBankName(String openedBankName) {
        this.openedBankName = openedBankName;
    }

    public String getBankOwner() {
        return bankOwner;
    }

    public void setBankOwner(String bankOwner) {
        this.bankOwner = bankOwner;
    }
}
