package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.user.PfIncomRecordPo;


/**
 * Created by wangbingjian on 2016/5/6.
 */
public class AccountUserIncomeRes extends BaseBusinessRes{

    private PfIncomRecordPo pfIncomRecordPo;

    public PfIncomRecordPo getPfIncomRecordPo() {
        return pfIncomRecordPo;
    }

    public void setPfIncomRecordPo(PfIncomRecordPo pfIncomRecordPo) {
        this.pfIncomRecordPo = pfIncomRecordPo;
    }
}
