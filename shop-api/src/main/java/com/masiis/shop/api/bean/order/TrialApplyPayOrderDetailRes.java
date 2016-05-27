package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.dao.po.PfCorderConsignee;

/**
 * Created by hzz on 2016/5/25.
 */
public class TrialApplyPayOrderDetailRes extends BaseBusinessRes {

    private PfCorder corder;
    private PfCorderConsignee corderConsignee;
    private String skuName;
    private String skuDefaultImg;

    public PfCorder getCorder() {
        return corder;
    }

    public void setCorder(PfCorder corder) {
        this.corder = corder;
    }

    public PfCorderConsignee getCorderConsignee() {
        return corderConsignee;
    }

    public void setCorderConsignee(PfCorderConsignee corderConsignee) {
        this.corderConsignee = corderConsignee;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
    }
}
