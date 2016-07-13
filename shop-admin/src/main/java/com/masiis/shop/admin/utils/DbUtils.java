package com.masiis.shop.admin.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class DbUtils {

    public static Map<String,Object> request2TimeCondition(HttpServletRequest request, Map<String, Object> conditionMap) {
        if(conditionMap == null) {
            conditionMap = new HashMap<>();
        }

        String beginTime = request.getParameter("beginTime");
        String endTime   = request.getParameter("endTime");

        return createTimeCondition(beginTime, endTime, conditionMap);
    }

    public static Map<String,Object> createTimeCondition(String beginTime, String endTime, Map<String,Object> conditionMap) {
        if(conditionMap == null) {
            conditionMap = new HashMap<>();
        }

        if(StringUtils.isNotEmpty(beginTime)) {
            conditionMap.put("beginTime", beginTime);
        }
        if(StringUtils.isNotEmpty(endTime)) {
            conditionMap.put("endTime", endTime);
        }

        return conditionMap;
    }

}
