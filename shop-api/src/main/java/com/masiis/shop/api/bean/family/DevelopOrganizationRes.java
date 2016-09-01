package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

import java.util.List;
import java.util.Map;

public class DevelopOrganizationRes extends BaseBusinessRes {

    private List<Map<String,Object>> organWrapList;

    public List<Map<String, Object>> getOrganWrapList() {
        return organWrapList;
    }

    public void setOrganWrapList(List<Map<String, Object>> organWrapList) {
        this.organWrapList = organWrapList;
    }
}
