package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;

/**
 * Created by wangbingjian on 2016/5/19.
 */
public class WithdrawRes extends BaseRes {
    /**
     * 持卡人
     */
    private String bankOwner;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 开户行名称
     */
    private String openedBankName;
    /**
     * 卡号
     */
    private String bankCode;
    /**
     * 提现方式
     */
    private String withdrawWay;
    /**
     * 可提现金额
     */
    private String extractFee;
    /**
     * 已申请提现金额
     */
    private String appliedFee;
    /**
     * 是否有银行卡
     */
    private boolean hasCard;

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

    public String getExtractFee() {
        return extractFee;
    }

    public void setExtractFee(String extractFee) {
        this.extractFee = extractFee;
    }

    public String getAppliedFee() {
        return appliedFee;
    }

    public void setAppliedFee(String appliedFee) {
        this.appliedFee = appliedFee;
    }

    public boolean isHasCard() {
        return hasCard;
    }

    public void setHasCard(boolean hasCard) {
        this.hasCard = hasCard;
    }

    public String getOpenedBankName() {
        return openedBankName;
    }

    public void setOpenedBankName(String openedBankName) {
        this.openedBankName = openedBankName;
    }

    public String getWithdrawWay() {
        return withdrawWay;
    }

    public void setWithdrawWay(String withdrawWay) {
        this.withdrawWay = withdrawWay;
    }
}
