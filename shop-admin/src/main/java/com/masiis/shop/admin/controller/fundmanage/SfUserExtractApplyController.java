package com.masiis.shop.admin.controller.fundmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.fundmanage.SfUserExtractApplyService;
import com.masiis.shop.dao.po.SfUserExtractApply;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cai_tb on 16/4/18.
 */
@Controller
@RequestMapping("/fundmanage/sf-extract")
public class SfUserExtractApplyController {

    private final static Log log = LogFactory.getLog(SfUserExtractApplyController.class);

    @Resource
    private SfUserExtractApplyService sfUserExtractApplyService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "fundmanage/sf-extract/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortOrder){

        Map<String, Object> conditionMap = new HashMap<>();
        try {

            Map<String, Object> pageMap = sfUserExtractApplyService.listByCondition(pageNumber, pageSize, conditionMap);

            return pageMap;
        } catch (Exception e) {
            log.error("获取分销用户提现申请列表失败![conditionMap="+conditionMap+"]");
        }

        return "error";
    }

    @RequestMapping("/audit.do")
    @ResponseBody
    public Object audit(HttpServletRequest request, HttpServletResponse response,
                        Long id,
                        Integer auditType,
                        String auditCause){
        try {
            sfUserExtractApplyService.audit(id, auditType, auditCause);

            return "success";
        } catch (Exception e) {
            log.error("提现申请审核失败![id="+id+"][auditType="+auditType+"][auditCause="+auditCause+"]");
            e.printStackTrace();
        }

        return "error";
    }
}
