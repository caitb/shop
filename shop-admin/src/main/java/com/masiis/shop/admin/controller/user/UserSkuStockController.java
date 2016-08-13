package com.masiis.shop.admin.controller.user;

import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.service.product.PfUserSkuStockService;
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
 * Created by admin on 2016/8/8.
 */
@Controller
@RequestMapping("/agentStock")
public class UserSkuStockController {

    @Resource
    private PfUserSkuStockService pfUserSkuStockService;


    @RequestMapping("/list.shtml")
    public String list() {
        return "product/userSkuStockList";
    }


    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String realName,
                       String mobile,
                       String skuName) {

        if(realName != null) {
            realName = realName.trim();
        }

        if(mobile != null) {
            mobile = mobile.trim();
        }

        if(skuName != null) {
            skuName = skuName.trim();
        }

        List<Map<String,Object>> userSkuStockList = pfUserSkuStockService.selectByCondition(pageNumber, pageSize, realName, mobile, skuName);

        PageInfo pageInfo = new PageInfo(userSkuStockList);
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", userSkuStockList);

        return pageMap;
    }
}
