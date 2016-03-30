package com.masiis.shop.admin.controller.user;

import com.masiis.shop.admin.service.user.ComUserService;
import com.masiis.shop.dao.po.ComUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 会员管理
 * Created by cai_tb on 16/3/30.
 */
@Controller
@RequestMapping("/comuser")
public class ComUserController {

    private final static Log log = LogFactory.getLog(ComUserController.class);

    @Resource
    private ComUserService comUserService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "user/comuser/list";
    }

    @RequestMapping("/auditlist.shtml")
    public String auditList(){
        return "user/comuser/auditList";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortOrder,
                       ComUser comUser){

        try {
            comUser.setAuditStatus(2);
            Map<String, Object> pageMap = comUserService.listByCondition(pageNumber, pageSize, comUser);

            return pageMap;
        } catch (Exception e) {
            log.error("获取会员列表信息失败!");
            e.printStackTrace();
        }

        return "error";
    }

    @RequestMapping("/auditlist.do")
    @ResponseBody
    public Object auditList(HttpServletRequest request, HttpServletResponse response,
                            Integer pageNumber,
                            Integer pageSize,
                            String sortOrder,
                            ComUser comUser){

        try {
            Map<String, Object> pageMap = comUserService.auditListByCondition(pageNumber, pageSize, comUser);

            return pageMap;
        } catch (Exception e) {
            log.error("获取待审核会员列表失败!");
            e.printStackTrace();
        }

        return "error";
    }

}
