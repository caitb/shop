package com.masiis.shop.admin.controller.extract;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.beans.extract.ExtractApply;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.extract.ComUserExtractApplyService;
import com.masiis.shop.dao.po.ComUserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/extract")
public class ExtractApplyController extends BaseController {

    @Resource
    private ComUserExtractApplyService comUserExtractApplyService;


    @RequestMapping("/list.shtml")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "extract/extract_list";
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
    )throws Exception {
        offset = offset==null ? 0 : offset;
        limit  = limit ==null ? 10 : limit;
        Integer pageNo = offset/limit + 1;
        PageHelper.startPage(pageNo, limit);
       /* Map<String, Object> searchParam = new HashMap<>();//组合搜索
        searchParam.put("pid", pid);
        searchParam.put("beginTime",beginTime);
        searchParam.put("endTime",endTime);
        searchParam.put("mobile",mobile);*/
        List<ExtractApply> extractApplyList = comUserExtractApplyService.getExtractApplyList();
        if (extractApplyList!=null&&extractApplyList.size()!=0){
            for (ExtractApply extractApply:extractApplyList) {
                if(extractApply != null && extractApply.getComUserId()!=null){
                    ComUserAccount comUserAccount = comUserExtractApplyService.findByUserId(extractApply.getComUserId());
                    extractApply.setComUserAccount(comUserAccount);
                }
            }
        }
        PageInfo<ExtractApply> pageInfo = new PageInfo<>(extractApplyList);
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", extractApplyList);
        return pageMap;
    }


}
