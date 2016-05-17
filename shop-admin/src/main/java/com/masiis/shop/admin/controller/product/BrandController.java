package com.masiis.shop.admin.controller.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.product.BrandService;
import com.masiis.shop.dao.po.ComBrand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by cai_tb on 16/5/17.
 */
@Controller
@RequestMapping("/brand")
public class BrandController {

    private final static Log log = LogFactory.getLog(BrandController.class);

    @Resource
    private BrandService brandService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "brand/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       ComBrand comBrand,
                       Integer pageNumber,
                       Integer pageSize
    ){
        Map<String, Object> pageMap = brandService.list(pageNumber, pageSize, comBrand);

        return pageMap;
    }
}
