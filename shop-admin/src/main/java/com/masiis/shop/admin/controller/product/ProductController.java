package com.masiis.shop.admin.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.admin.service.product.BrandService;
import com.masiis.shop.admin.service.product.CategoryService;
import com.masiis.shop.dao.po.ComBrand;
import com.masiis.shop.dao.po.ComCategory;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSpu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private BrandService brandService;
    @Resource
    private CategoryService categoryService;

    @RequestMapping("/add.shtml")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("product/add");

        List<ComBrand> comBrands = brandService.list(new ComBrand());
        List<ComCategory> comCategories = categoryService.listByCondition(new ComCategory());

        mav.addObject("brands", comBrands);
        mav.addObject("categories", objectMapper.writeValueAsString(comCategories));

        return mav;
    }

    @RequestMapping("/add.do")
    @ResponseBody
    public Object add(HttpServletRequest request, HttpServletResponse response,
                      ComSpu comSpu,
                      ComSku comSku){

        return "保存成功!";
    }
}
