package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.po.PfUserOrganization;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cai_tb on 16/8/8.
 */
public class UserOrganizationRes extends BaseBusinessRes {

    public Map<String, Object> dataMap = new HashMap<>();

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
