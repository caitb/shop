package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;

import java.util.List;

/**
 * Created by wangbingjian on 2016/5/19.
 */
public class WithdrawBankRes extends BaseRes {

    private boolean hasData = false;

    private List<WithdrawBank> withdrawBanks;

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public List<WithdrawBank> getWithdrawBanks() {
        return withdrawBanks;
    }

    public void setWithdrawBanks(List<WithdrawBank> withdrawBanks) {
        this.withdrawBanks = withdrawBanks;
    }
}
