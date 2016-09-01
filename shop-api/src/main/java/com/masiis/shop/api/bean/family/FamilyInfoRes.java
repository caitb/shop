package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.beans.family.FamilyList;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jiajinghao on 2016/9/1.
 */
public class FamilyInfoRes extends BasePagingRes {

    private List<FamilyList> familyLists;

    private BigDecimal limitMoney;//代理等级门槛

    private BigDecimal createLimitMoney;//加入家族等级门槛

    public List<FamilyList> getFamilyLists() {
        return familyLists;
    }

    public void setFamilyLists(List<FamilyList> familyLists) {
        this.familyLists = familyLists;
    }

    public BigDecimal getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(BigDecimal limitMoney) {
        this.limitMoney = limitMoney;
    }

    public BigDecimal getCreateLimitMoney() {
        return createLimitMoney;
    }

    public void setCreateLimitMoney(BigDecimal createLimitMoney) {
        this.createLimitMoney = createLimitMoney;
    }
}
