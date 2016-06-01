package com.masiis.shop.admin.controller.fundmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.fundmanage.ExtractApplyService;
import com.masiis.shop.dao.po.ComUserExtractApply;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cai_tb on 16/3/31.
 */
@Controller
@RequestMapping("/fundmanage/com-extract")
public class ExtractApplyController extends BaseController{

    private final static Log log = LogFactory.getLog(ExtractApplyController.class);

    @Resource
    private ExtractApplyService extractApplyService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "fundmanage/com-extract/list";
    }

    /**
     * 提现申请列表
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

        Map<String, Object> con = null;
        try {
            con = new HashMap<>();
            Map<String, Object> pageMap = extractApplyService.listByCondition(pageNumber, pageSize, con);
            return pageMap;
        } catch (Exception e) {
            log.error("获取提现申请列表失败![comUserExtractApply="+con+"]");
            e.printStackTrace();
        }

        return "error";
    }

    @RequestMapping("/audit.do")
    @ResponseBody
    public Object audit(HttpServletRequest request, HttpServletResponse response, ComUserExtractApply comUserExtractApply){
        try {
            extractApplyService.audit(comUserExtractApply,getPbUser(request));

            return "success";
        } catch (Exception e) {
            log.error("提现申请审核失败![comUserExtractApply="+comUserExtractApply+"]");
            e.printStackTrace();
        }

        return "error";
    }

}
