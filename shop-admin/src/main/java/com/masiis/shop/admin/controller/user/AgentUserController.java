package com.masiis.shop.admin.controller.user;

import com.masiis.shop.admin.service.user.AgentUserService;
import com.masiis.shop.admin.service.user.ComUserService;
import com.masiis.shop.dao.po.ComUser;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
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
    @Resource
    private ComUserService comUserService;

    @RequestMapping("/list.shtml")
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, Integer pid){
        model.addAttribute("pid", pid);
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
                       String sortName,
                       String sortOrder,
                       Integer pid){

        Map<String, Object> conMap = new HashMap<>();
        try {
            conMap.put("pid", pid);
            if(StringUtils.isNotBlank(request.getParameter("realName")))  conMap.put("realName", "%"+request.getParameter("realName")+"%");
            if(StringUtils.isNotBlank(request.getParameter("mobile")))    conMap.put("mobile", request.getParameter("mobile"));
            if(StringUtils.isNotBlank(request.getParameter("pRealName"))) conMap.put("pRealName", "%"+request.getParameter("pRealName")+"%");

            Map<String, Object> pageMap = agentUserService.listByCondition(pageNumber, pageSize, sortName, sortOrder, conMap);

            return pageMap;
        } catch (Exception e) {
            log.error("获取合伙人列表失败![conMap="+conMap+"]");
            e.printStackTrace();
        }

        return conMap;
    }
}
