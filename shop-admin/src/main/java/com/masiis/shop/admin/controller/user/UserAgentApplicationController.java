package com.masiis.shop.admin.controller.user;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.user.UserAgentApplicationService;
import com.masiis.shop.dao.po.PfUserAgentApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 合伙意向申请
 * Created by cai_tb on 16/7/13.
 */
@Controller
@RequestMapping("/userAgentApplication")
public class UserAgentApplicationController {

    private final static Log log = LogFactory.getLog(UserAgentApplicationController.class);

    @Resource
    private UserAgentApplicationService userAgentApplicationService;

    /**
     * 合伙意向申请页面
     * @return
     */
    @RequestMapping("/list.shtml")
    public String list() {
        return "user/agentuser/userAgentApplicationList";
    }

    /**
     * 合伙意向申请列表
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(
            Integer pageNumber,
            Integer pageSize,
            String sortName,
            String sortOrder
    ){
        Map<String, Object> conditionMap = new HashMap<>();
        Map<String, Object> pageMap = new HashMap<>();

        try {
            pageMap = userAgentApplicationService.listByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap);
        } catch (Exception e) {
            log.error("获取合伙意向申请列表失败![conditionMap="+conditionMap+"]"+e);
            e.printStackTrace();
        }

        return pageMap;
    }

    @RequestMapping("/dispose.do")
    @ResponseBody
    public Map<String, Object> dispose(PfUserAgentApplication pfUserAgentApplication) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            userAgentApplicationService.updateById(pfUserAgentApplication);
            resultMap.put("code", "success");
            resultMap.put("msg", "处理成功");
        } catch (Exception e) {
            resultMap.put("code", "fail");
            resultMap.put("msg", "处理失败!");
            log.error("合伙意向申请处理失败![pfUserAgentApplication="+pfUserAgentApplication+"]"+e);
            e.printStackTrace();
        }

        return resultMap;
    }

}
