package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseRes;

import java.util.List;
import java.util.Map;

public class LowerLevelPartnerRes extends BaseRes {

    private List<Map<String, Object>> lowerLevelPartnerList;

    public List<Map<String, Object>> getLowerLevelPartnerList() {
        return lowerLevelPartnerList;
    }

    public void setLowerLevelPartnerList(List<Map<String, Object>> lowerLevelPartnerList) {
        this.lowerLevelPartnerList = lowerLevelPartnerList;
    }
}
