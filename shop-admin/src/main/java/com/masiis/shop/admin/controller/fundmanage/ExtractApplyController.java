package com.masiis.shop.admin.controller.fundmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.beans.extract.ExtractApply;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.extract.ComUserExtractApplyService;
import com.masiis.shop.dao.po.ComUserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xuechao
 * @Date 2016/3/17 11:35.
 */

@Controller
@RequestMapping("/fundmanage/extract")
public class ExtractApplyController extends BaseController {

    private final static Log log = LogFactory.getLog(ExtractApplyController.class);
    @Resource
    private ComUserExtractApplyService comUserExtractApplyService;


    @RequestMapping("/list.shtml")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "fundmanage/extract_list";
    }

    @RequestMapping("list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       String beginTime,
                       String endTime,
                       String mobile,
                       //@RequestParam(value="pid", required = false) Long pid,
                       String sort,
                       String order,
                       Integer offset,
                       Integer limit
    ) {
        offset = offset==null ? 0 : offset;
        limit  = limit ==null ? 10 : limit;
        Integer pageNo = offset/limit + 1;
        PageHelper.startPage(pageNo, limit);
       /* Map<String, Object> searchParam = new HashMap<>();//组合搜索
        searchParam.put("pid", pid);
        searchParam.put("beginTime",beginTime);
        searchParam.put("endTime",endTime);
        searchParam.put("mobile",mobile);*/
        ComUserAccount comUserAccount = null;
        try{
            List<ExtractApply> extractApplyList = comUserExtractApplyService.getExtractApplyList();
            if (extractApplyList!=null&&extractApplyList.size()!=0){
                for (ExtractApply extractApply:extractApplyList) {
                    if(extractApply != null && extractApply.getComUserId()!=null){
                        comUserAccount = comUserExtractApplyService.findByUserId(extractApply.getComUserId());
                        extractApply.setComUserAccount(comUserAccount);
                    }
                }
            }
            PageInfo<ExtractApply> pageInfo = new PageInfo<>(extractApplyList);
            Map<String, Object> pageMap = new HashMap<>();
            pageMap.put("total", pageInfo.getTotal());
            pageMap.put("rows", extractApplyList);
            return pageMap;
        }catch (Exception e){
            log.error("提现失败！[comUserAccount="+comUserAccount+"]");
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping("/toaudit.do")
    public ModelAndView toAudit(HttpServletRequest request, HttpServletResponse response, Long id){

        ModelAndView mav = new ModelAndView("fundmanage/toAudit");
        ExtractApply extractApply = null;
        try{
            extractApply = comUserExtractApplyService.findById(id);
            if (extractApply!=null){
                ComUserAccount comUserAccount = comUserExtractApplyService.findByUserId(extractApply.getComUserId());
                extractApply.setComUserAccount(comUserAccount);
            }
            mav.addObject(extractApply);
            return mav;
        }catch (Exception e){
            log.error("查看提现信息失败！[id="+id+"]");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 待打款
     * @param id
     * @return
     */
    @RequestMapping("pass.do")
    @ResponseBody
    public Object pass(Long id){
        ExtractApply extractApply = null;
        try {
            extractApply = comUserExtractApplyService.findById(id);
            if (extractApply!=null){
                ComUserAccount comUserAccount = comUserExtractApplyService.findByUserId(extractApply.getComUserId());
                if(comUserAccount!=null){
                    int a = extractApply.getExtractFee().compareTo(comUserAccount.getExtractableFee());
                    if (a==-1||a==0){
                        comUserExtractApplyService.pass(id);
                    }else {
                        return "0";
                    }

                 }
            }

        }catch (Exception e){
            log.error("提现通过审核失败！[id="+id+"]");
            e.printStackTrace();
        }
        return "1";
    }

    /**
     * 拒绝
     * @param id
     * @return
     */
    @RequestMapping("refuse.do")
    @ResponseBody
    public Object refuse(Long id){
        try {
            comUserExtractApplyService.refuse(id);
        }catch (Exception e){
            log.error("提现拒绝审核失败！[id="+id+"]");
            e.printStackTrace();
        }
        return "2";
    }

    /**
     * 已付款
     * @param id
     * @return
     */
    @RequestMapping("pay.do")
    @ResponseBody
    public Object pay(Long id){

        try {
            comUserExtractApplyService.pay(id);
        }catch (Exception e){
            log.error("提现拒绝审核失败！[id="+id+"]");
            e.printStackTrace();
        }
        return "3";
    }

    /*@RequestMapping("findById.do")
    @ResponseBody
    public Map<String,Object> findById(HttpServletRequest request,HttpServletResponse response,Long id){
        HashMap<String, Object> map = new HashMap<>();
        ExtractApply extractApply = comUserExtractApplyService.findById(id);
        if (extractApply!=null){
            ComUserAccount comUserAccount = comUserExtractApplyService.findByUserId(extractApply.getComUserId());
            extractApply.setComUserAccount(comUserAccount);
        }
        map.put("extract",extractApply);
        return map;
    }*/

}
