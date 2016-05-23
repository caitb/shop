package com.masiis.shop.api.bean.user;

/**
 * Created by wangbingjian on 2016/5/19.
 */
public class WithdrawBank {
    /**
     * 银行卡id
     */
    private Long id;
    /**
     * 持卡人
     */
    private String bankOwner;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 卡号
     */
    private String bankCode;
    /**
     * 是否为默认
     */
    private boolean isDefault;

    public String getBankOwner() {
        return bankOwner;
    }

    public void setBankOwner(String bankOwner) {
        this.bankOwner = bankOwner;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
