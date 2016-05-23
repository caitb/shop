package com.masiis.shop.api.bean.system;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.po.ComBank;

import java.util.List;

/**
 * Created by wangbingjian on 2016/5/21.
 */
public class ComBankRes extends BaseRes {

    private List<ComBank> comBanks;

    public List<ComBank> getComBanks() {
        return comBanks;
    }

    public void setComBanks(List<ComBank> comBanks) {
        this.comBanks = comBanks;
    }
}
