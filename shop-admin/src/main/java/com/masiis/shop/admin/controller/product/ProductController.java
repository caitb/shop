package com.masiis.shop.admin.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.admin.service.product.BrandService;
import com.masiis.shop.admin.service.product.CategoryService;
import com.masiis.shop.admin.service.product.ProductService;
import com.masiis.shop.common.util.ImageUtils;
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
import java.util.*;

/**
 * Created by caitingbiao on 2016/3/2.
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

    @RequestMapping("/edit.shtml")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, Integer skuId) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("product/edit");

        List<ComBrand> comBrands = brandService.list(new ComBrand());
        List<ComCategory> comCategories = categoryService.listByCondition(new ComCategory());
        ProductInfo productInfo = productService.findSku(skuId);

        ComCategory comCategory = categoryService.find(productInfo.getComSpu().getCategoryId());
        mav.addObject("c3id", comCategory.getId());
        comCategory = categoryService.find(comCategory.getPid());
        mav.addObject("c2id", comCategory.getId());
        mav.addObject("c1id", comCategory.getPid());

        mav.addObject("brands", comBrands);
        mav.addObject("categories", objectMapper.writeValueAsString(comCategories));
        mav.addObject("productInfo", productInfo);

        return mav;
    }

    @RequestMapping("/list.shtml")
    public String list(HttpServletRequest request, HttpServletResponse response){
        return "product/list";
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
                      @RequestParam("mainImgNames")String[] mainImgNames,
                      @RequestParam("mainImgOriginalNames")String[] mainImgOriginalNames) throws FileNotFoundException {

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

                sfSkuDistributions.add(sfSkuDistribution);
            }

            List<ComSkuImage> comSkuImages = new ArrayList<>();
            String realPath = request.getServletContext().getRealPath("/");
                   realPath = realPath.substring(0, realPath.lastIndexOf("/"));
            String folderPath = realPath + "/current";
            int[] imgPxs = {220, 308, 800};
            File folder = new File(folderPath);
            if(!folder.exists()){
                folder.mkdir();
            }
            for(int i=0; i<mainImgUrls.length; i++){
                ComSkuImage comSkuImage = new ComSkuImage();
                comSkuImage.setCreateTime(new Date());
                comSkuImage.setCreateMan(pbUser.getId());
                comSkuImage.setImgUrl(mainImgNames[i]);
                comSkuImage.setImgName(mainImgOriginalNames[i]);
                comSkuImage.setIsDefault(i==1?1:0);

                String imgAbsoluteUrl = realPath + mainImgUrls[i];
                String resultPath = folderPath + "/" + mainImgNames[i];
                for(int px=0; px<imgPxs.length; px++){
                    ImageUtils.scale2(imgAbsoluteUrl, resultPath, imgPxs[px], imgPxs[px], true);
                    File curFile = new File(resultPath);
                    OSSObjectUtils.uploadFile("mmshop", curFile, "static/product/"+imgPxs[px]+"_"+imgPxs[px]+"/");

                    //删除缩放的图片
                    curFile.delete();

                    comSkuImage.setFullImgUrl(PropertiesUtils.getStringValue("index_product_"+imgPxs[px]+"_"+imgPxs[px]+"_url") + mainImgNames[i]);
                }

                //删除原图
                new File(imgAbsoluteUrl).delete();

                comSkuImages.add(comSkuImage);
            }

            /* 上传商品详情图 */
            File detailDir = new File(realPath + "/static/product/detail_img");
            if(detailDir.exists() && detailDir.listFiles().length > 0){
                File[] files = detailDir.listFiles();
                for(File f : files){
                    OSSObjectUtils.uploadFile("mmshop", f, "static/product/detail_img/");
                    f.delete();
                }
            }

            productService.save(comSpu, comSku, comSkuImages, pfSkuAgents, sfSkuDistributions);
        }
        return "保存成功!";
    }

    @RequestMapping("/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response,
                         Integer spuId,
                         Integer skuId,
                         @RequestParam("skuAgentIds")Integer[] skuAgentIds,
                         @RequestParam("skuDistributionIds")Integer[] skuDistributionIds,
                         ComSpu comSpu,
                         ComSku comSku,
                         @RequestParam("discounts")String[] discounts,
                         @RequestParam("quantitys")Integer[] quantitys,
                         @RequestParam("distributionDiscounts")String[] distributionDiscounts,
                         @RequestParam(value = "mainImgUrls", required = false)String[] mainImgUrls,
                         @RequestParam(value = "mainImgNames", required = false)String[] mainImgNames) throws FileNotFoundException {

        PbUser pbUser = (PbUser)request.getSession().getAttribute("pbUser");
        if(pbUser != null){
            comSpu.setId(spuId);
            comSpu.setModifyTime(new Date());
            comSpu.setModifyMan(pbUser.getId());

            comSku.setId(skuId);
            comSku.setModifyTime(new Date());
            comSku.setModifyMan(pbUser.getId());

            //代理分润
            List<PfSkuAgent> pfSkuAgents = new ArrayList<>();
            for(int i=0; i<discounts.length; i++){
                PfSkuAgent pfSkuAgent = new PfSkuAgent();
                pfSkuAgent.setId(skuAgentIds[i]);
                pfSkuAgent.setDiscount(new BigDecimal(discounts[i]));
                pfSkuAgent.setQuantity(quantitys[i]);

                pfSkuAgents.add(pfSkuAgent);
            }

            //分销分润
            List<SfSkuDistribution> sfSkuDistributions = new ArrayList<>();
            for(int i=0; i<distributionDiscounts.length; i++){
                SfSkuDistribution sfSkuDistribution = new SfSkuDistribution();
                sfSkuDistribution.setId(skuDistributionIds[i]);
                sfSkuDistribution.setDiscount(new BigDecimal(distributionDiscounts[i]));

                sfSkuDistributions.add(sfSkuDistribution);
            }

//            List<ComSkuImage> comSkuImages = new ArrayList<>();
//            String realPath = request.getServletContext().getRealPath("/");
//            realPath = realPath.substring(0, realPath.lastIndexOf("/"));
//            String folderPath = realPath + "/current";
//            int[] imgPxs = {220, 308, 800};
//            File folder = new File(folderPath);
//            if(!folder.exists()){
//                folder.mkdir();
//            }
//            for(int i=0; i<mainImgUrls.length; i++){
//                ComSkuImage comSkuImage = new ComSkuImage();
//                comSkuImage.setCreateTime(new Date());
//                comSkuImage.setCreateMan(pbUser.getId());
//                comSkuImage.setImgUrl(mainImgNames[i]);
//                comSkuImage.setImgName(mainImgNames[i]);
//                comSkuImage.setIsDefault(i==1?1:0);
//
//                String imgAbsoluteUrl = realPath + mainImgUrls[i];
//                String resultPath = folderPath + "/" + mainImgNames[i];
//                for(int px=0; px<imgPxs.length; px++){
//                    ImageUtils.scale2(imgAbsoluteUrl, resultPath, imgPxs[px], imgPxs[px], true);
//                    File curFile = new File(resultPath);
//                    OSSObjectUtils.uploadFile("mmshop", curFile, "static/product/"+imgPxs[px]+"_"+imgPxs[px]+"/");
//
//                    //删除缩放的图片
//                    curFile.delete();
//
//                    comSkuImage.setFullImgUrl(PropertiesUtils.getStringValue("index_product_"+imgPxs[px]+"_"+imgPxs[px]+"_url") + mainImgNames[i]);
//                }
//
//                //删除原图
//                new File(imgAbsoluteUrl).delete();
//
//                comSkuImages.add(comSkuImage);
//            }

            productService.update(comSpu, comSku, null, pfSkuAgents, sfSkuDistributions);
        }
        return "保存成功!";
    }

    @RequestMapping("/putaway.do")
    @ResponseBody
    public Object putaway(HttpServletRequest request, HttpServletResponse response, ComSpu comSpu){
        productService.putaway(comSpu);

        return "success";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       ComSku comSku,
                       Integer pageNumber,
                       Integer pageSize){

        Map<String, Object> pageMap = productService.list(pageNumber, pageSize, comSku);

        return pageMap;
    }
}
