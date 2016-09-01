package com.masiis.shop.api.bean.search;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cai_tb on 16/8/9.
 */
public class SearchRes extends BaseBusinessRes {

    private Map<String, Object> dataMap = new HashMap<>();

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
