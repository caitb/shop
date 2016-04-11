package com.masiis.shop.admin.controller.user;

import com.masiis.shop.admin.service.user.AgentUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 代理商(合伙人)
 * Created by cai_tb on 16/4/11.
 */
@Controller
@RequestMapping("/agentUser")
public class AgentUserController {

    private final static Log log = LogFactory.getLog(AgentUserController.class);

    @Resource
    private AgentUserService agentUserService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "user/agentuser/list";
    }

    /**
     * 代理商(合伙人)列表
     * @param request
     * @param response
     * @param pageNumber
     * @param pageSize
     * @param sortOrder
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortOrder){

        Map<String, Object> conMap = new HashMap<>();
        try {

            Map<String, Object> pageMap = agentUserService.listByCondition(pageNumber, pageSize, conMap);

            return pageMap;
        } catch (Exception e) {
            log.error("获取合伙人列表失败![conMap="+conMap+"]");
            e.printStackTrace();
        }

        return conMap;
    }
}
