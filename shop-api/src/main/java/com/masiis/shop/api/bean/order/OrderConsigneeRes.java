package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.po.SfOrderConsignee;


/**
 * Created by hw on 2016/8/4.
 */
public class OrderConsigneeRes extends BaseBusinessRes {

    private SfOrderConsignee consignee;
    private String wxNkName;

    public SfOrderConsignee getConsignee() {
        return consignee;
    }

    public void setConsignee(SfOrderConsignee consignee) {
        this.consignee = consignee;
    }

    public String getWxNkName() {
        return wxNkName;
    }

    public void setWxNkName(String wxNkName) {
        this.wxNkName = wxNkName;
    }

}
