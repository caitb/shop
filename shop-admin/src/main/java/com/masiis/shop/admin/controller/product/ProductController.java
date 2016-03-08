package com.masiis.shop.admin.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.admin.service.product.BrandService;
import com.masiis.shop.admin.service.product.CategoryService;
import com.masiis.shop.admin.service.product.ProductService;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
    @Resource
    private ProductService productService;

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
                      ComSku comSku,
                      @RequestParam("discounts")String[] discounts,
                      @RequestParam("quantitys")Integer[] quantitys,
                      @RequestParam("distributionDiscounts")String[] distributionDiscounts,
                      @RequestParam("mainImgUrls")String[] mainImgUrls,
                      @RequestParam("mainImgNames")String[] mainImgNames) throws FileNotFoundException {

        String realPath1 = request.getServletContext().getRealPath("/");
        PbUser pbUser = (PbUser)request.getSession().getAttribute("pbUser");
        if(pbUser != null){
            comSpu.setCreateTime(new Date());
            comSpu.setCreateMan(pbUser.getId());
            comSpu.setStatus(0);
            comSpu.setIsSale(0);
            comSpu.setIsDelete(0);

            comSku.setCreateTime(new Date());
            comSku.setCreateMan(pbUser.getId());

            //代理分润
            List<PfSkuAgent> pfSkuAgents = new ArrayList<>();
            for(int i=0; i<discounts.length; i++){
                PfSkuAgent pfSkuAgent = new PfSkuAgent();
                pfSkuAgent.setAgentLevelId(new Integer(i + 1));
                pfSkuAgent.setDiscount(new BigDecimal(discounts[i]));
                pfSkuAgent.setQuantity(quantitys[i]);

                pfSkuAgents.add(pfSkuAgent);
            }

            //分销分润
            List<SfSkuDistribution> sfSkuDistributions = new ArrayList<>();
            for(int i=0; i<distributionDiscounts.length; i++){
                SfSkuDistribution sfSkuDistribution = new SfSkuDistribution();
                sfSkuDistribution.setDiscount(new BigDecimal(distributionDiscounts[i]));
                sfSkuDistribution.setSort(i+1);
            }

            List<ComSkuImage> comSkuImages = new ArrayList<>();
            String realPath = request.getServletContext().getRealPath("/");
                   realPath = realPath.substring(0, realPath.lastIndexOf("/"));
            for(int i=0; i<mainImgUrls.length; i++){
                String imgAbsoluteUrl = realPath + mainImgUrls[i];
                OSSObjectUtils.uploadFile("mmshop", new File(imgAbsoluteUrl), "product/100_100/");

                ComSkuImage comSkuImage = new ComSkuImage();
                comSkuImage.setCreateTime(new Date());
                comSkuImage.setCreateMan(pbUser.getId());
                comSkuImage.setFullImgUrl(PropertiesUtils.getStringValue("index_product_100_100_url") + mainImgNames[i]);
                comSkuImage.setImgUrl(mainImgNames[i]);
                comSkuImage.setImgName(mainImgNames[i]);

                comSkuImages.add(comSkuImage);
            }

            productService.save(comSpu, comSku, comSkuImages, pfSkuAgents, sfSkuDistributions);
        }
        return "保存成功!";
    }
}
