package com.masiis.shop.admin.controller.upgrade;

import com.masiis.shop.admin.service.upgrade.UpgradeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 升级管理
 */
@Controller
@RequestMapping("/upgrade")
public class UpgradeController {

    @Resource
    private UpgradeService upgradeService;

    @RequestMapping("/list.shtml")
    public String list() {
        return "upgrade/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(Integer pageSize,Integer pageNumber,
                                   String beginTime,
                                   String endTime,
                                   String code,
                                   Integer status) {

        Map<String,Object> conditionMap = new HashMap<>();
        if(StringUtils.isNotEmpty(beginTime)) {
            conditionMap.put("beginTime", beginTime);
        }
        if(StringUtils.isNotEmpty(endTime)) {
            conditionMap.put("endTime", endTime);
        }
        if(StringUtils.isNotEmpty(code)) {
            conditionMap.put("code", code);
        }
        if(status != null) {
            conditionMap.put("status", status);
        }

        Map<String,Object> pageMap = upgradeService.listByCondition(pageNumber, pageSize, conditionMap);
        return pageMap;
    }
}
