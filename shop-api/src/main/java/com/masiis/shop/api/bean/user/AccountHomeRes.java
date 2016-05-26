package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.po.PfUserBill;

import java.util.List;

/**
 * Created by wangbingjian on 2016/5/6.
 */
public class AccountHomeRes extends BaseRes {
    /**
     * 累计收入金额
     */
    private String totalIncomeFee;
    /**
     * 可提现金额
     */
    private String extractableFee;
    /**
     * 申请中金额
     */
    private String appliedFee;
    /**
     * 结算中金额
     */
    private String countingFee;
    /**
     * 已提现金额
     */
    private String withdrawedFee;

    public String getTotalIncomeFee() {
        return totalIncomeFee;
    }

    public void setTotalIncomeFee(String totalIncomeFee) {
        this.totalIncomeFee = totalIncomeFee;
    }

    public String getExtractableFee() {
        return extractableFee;
    }

    public void setExtractableFee(String extractableFee) {
        this.extractableFee = extractableFee;
    }

    public String getCountingFee() {
        return countingFee;
    }

    public void setCountingFee(String countingFee) {
        this.countingFee = countingFee;
    }

    public String getAppliedFee() {
        return appliedFee;
    }

    public void setAppliedFee(String appliedFee) {
        this.appliedFee = appliedFee;
    }

    public String getWithdrawedFee() {
        return withdrawedFee;
    }

    public void setWithdrawedFee(String withdrawedFee) {
        this.withdrawedFee = withdrawedFee;
    }
}
