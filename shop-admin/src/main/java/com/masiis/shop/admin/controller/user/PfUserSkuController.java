package com.masiis.shop.admin.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.user.PfUserSkuService;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.beans.user.PfUserSkuCertificate;
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
 * Created by 49134 on 2016/3/9.
 */
@Controller
@RequestMapping("/userSku")
public class PfUserSkuController extends BaseController {

    @Resource
    private PfUserSkuService pfUserSkuService;


    @RequestMapping("/list.shtml")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "user/pfUserSku_list";
    }
    @RequestMapping("list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       String beginTime,
                       String endTime,
                       String mobile,
                       String agentLevel,
                       String sort,
                       String order,
                       Integer offset,
                       Integer limit
    )throws Exception {
        offset = offset==null ? 0 : offset;
        limit  = limit ==null ? 10 : limit;
        Integer pageNo = offset/limit + 1;
        PageHelper.startPage(pageNo, limit);
        Map<String, Object> searchParam = new HashMap<>();//组合搜索
        searchParam.put("beginTime",beginTime);
        searchParam.put("endTime",endTime);
        searchParam.put("mobile",mobile);
        List<PfUserSkuCertificate> pfUserSkuList = pfUserSkuService.getCertificates(searchParam);
        PageInfo<PfUserSkuCertificate> pageInfo = new PageInfo<>(pfUserSkuList);
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", pfUserSkuList);
        return pageMap;
    }

}
