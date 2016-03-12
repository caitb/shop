package com.masiis.shop.admin.controller.product;

import com.masiis.shop.admin.service.product.SkuStockService;
import com.masiis.shop.dao.po.PfSkuStock;
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
public class SkuStockController {

    @Resource
    private SkuStockService skuStockService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "product/stock-list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortOrder){

        Map<String, Object> pageMap = skuStockService.listByCondition(pageNumber, pageSize, new PfSkuStock());

        return pageMap;
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public Object update(HttpServletRequest request, HttpServletResponse response,
                         Integer id,
                         @RequestParam(value = "stock", required = false) Integer stock,
                         @RequestParam(value = "frozenStock", required = false) Integer frozenStock){

        skuStockService.update(id, stock, frozenStock);

        return "success";
    }
}
