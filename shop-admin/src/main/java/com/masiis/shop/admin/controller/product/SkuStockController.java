package com.masiis.shop.admin.controller.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.product.SkuStockService;
import com.masiis.shop.dao.po.PfSkuStock;
import com.masiis.shop.dao.po.PfUserSku;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by cai_tb on 16/3/12.
 */
@Controller
@RequestMapping("/stock")
public class SkuStockController extends BaseController{

    private final static Log log = LogFactory.getLog(SkuStockController.class);

    @Resource
    private SkuStockService skuStockService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "product/stockList";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortOrder){

        try {
            Map<String, Object> pageMap = skuStockService.listByCondition(pageNumber, pageSize, new PfSkuStock());

            return pageMap;
        } catch (Exception e) {
            log.error("获取库存列表失败!");
        }

        return "error";
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public Object update(HttpServletRequest request, HttpServletResponse response,
                         PfSkuStock pfSkuStock){

        try {
            skuStockService.update(pfSkuStock,getPbUser(request));

            return "success";
        } catch (Exception e) {
            log.error("更新库存失败![pfSkuStock=" + pfSkuStock + "]");
            e.printStackTrace();
        }

        return "error";
    }
}
