package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.user.CountGroup;

/**
 * Created by wangbingjian on 2016/8/11.
 */
public class FamilyHomeRes extends BaseBusinessRes {

    private Integer brandNum;

    private CountGroup countGroup;

    public Integer getBrandNum() {
        return brandNum;
    }

    public void setBrandNum(Integer brandNum) {
        this.brandNum = brandNum;
    }

    public CountGroup getCountGroup() {
        return countGroup;
    }

    public void setCountGroup(CountGroup countGroup) {
        this.countGroup = countGroup;
    }
}
