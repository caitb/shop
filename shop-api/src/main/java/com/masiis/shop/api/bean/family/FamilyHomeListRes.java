package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.family.FamilyHomeListPo;

/**
 * Created by wangbingjian on 2016/8/12.
 */
public class FamilyHomeListRes extends BaseBusinessRes {

    private FamilyHomeListPo familyHomeListPo;

    public FamilyHomeListPo getFamilyHomeListPo() {
        return familyHomeListPo;
    }

    public void setFamilyHomeListPo(FamilyHomeListPo familyHomeListPo) {
        this.familyHomeListPo = familyHomeListPo;
    }
}
