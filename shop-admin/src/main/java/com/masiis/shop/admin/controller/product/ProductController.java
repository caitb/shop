package com.masiis.shop.admin.controller.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.admin.service.product.*;
import com.masiis.shop.common.util.ImageUtils;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.*;
import org.apache.commons.lang.StringUtils;
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

    private final static Log log = LogFactory.getLog(ProductController.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private BrandService brandService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private ProductService productService;
    @Resource
    private UnitMeasureService unitMeasureService;
    @Resource
    private AgentLevelService agentLevelService;
    @Resource
    private SkuImageService skuImageService;

    @RequestMapping("/add.shtml")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("product/add");

        List<ComBrand> comBrands = brandService.list(new ComBrand());
        List<ComUnitMeasure> comUnitMeasures = unitMeasureService.listAll();
        List<ComCategory> comCategories = categoryService.listByCondition(new ComCategory());
        List<ComAgentLevel> comAgentLevels = agentLevelService.listAll();

        mav.addObject("brands", comBrands);
        mav.addObject("unitMeasures", comUnitMeasures);
        mav.addObject("categories", objectMapper.writeValueAsString(comCategories));
        mav.addObject("agentLevels", objectMapper.writeValueAsString(comAgentLevels));

        return mav;
    }

    @RequestMapping("/edit.shtml")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, Integer skuId) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("product/edit");

        List<ComBrand> comBrands = brandService.list(new ComBrand());
        List<ComUnitMeasure> comUnitMeasures = unitMeasureService.listAll();
        List<ComCategory> comCategories = categoryService.listByCondition(new ComCategory());
        List<ComAgentLevel> comAgentLevels = agentLevelService.listAll();
        ProductInfo productInfo = productService.findSku(skuId);

        ComCategory comCategory = categoryService.find(productInfo.getComSpu().getCategoryId());
        mav.addObject("c3id", comCategory.getId());
        comCategory = categoryService.find(comCategory.getPid());
        mav.addObject("c2id", comCategory.getId());
        mav.addObject("c1id", comCategory.getPid());

        mav.addObject("brands", comBrands);
        mav.addObject("comUnitMeasures", comUnitMeasures);
        mav.addObject("categories", objectMapper.writeValueAsString(comCategories));
        mav.addObject("agentLevels", comAgentLevels);
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
                      @RequestParam(value = "unitPrices")String[] unitPrices,
                      @RequestParam(value = "totalPrices")String[] totalPrices,
                      @RequestParam("quantitys")Integer[] quantitys,
                      @RequestParam("bails")String[] bails,
                      @RequestParam("distributionDiscounts")String[] distributionDiscounts,
                      String proIconUrl,
                      String proIconName,
                      @RequestParam("mainImgUrls")String[] mainImgUrls,
                      @RequestParam("mainImgNames")String[] mainImgNames,
                      @RequestParam("mainImgOriginalNames")String[] mainImgOriginalNames,
                      @RequestParam("skuBackgroundImgName")String skuBackgroundImgName,
                      @RequestParam("developPosterName")String developPosterName,
                      @RequestParam("iconImgUrls")String[] iconImgUrls,
                      @RequestParam("iconImgNames")String[] iconImgNames) throws FileNotFoundException {

        String realPath = request.getServletContext().getRealPath("/");
        realPath = realPath.substring(0, realPath.lastIndexOf("/"));

        try{
            PbUser pbUser = (PbUser)request.getSession().getAttribute("pbUser");
            if(pbUser != null){
                log.info("保存商品-开始准备comSpu数据");
                comSpu.setCreateTime(new Date());
                comSpu.setCreateMan(pbUser.getId());
                comSpu.setStatus(0);
                comSpu.setIsSale(0);
                comSpu.setIsDelete(0);

                comSku.setCreateTime(new Date());
                comSku.setCreateMan(pbUser.getId());
                comSku.setIcon(proIconName);
                log.info("保存商品-comSpu数据[comSpu="+comSpu+"]");

                //代理分润
                log.info("保存商品-开始准备pfSkuAgents数据");
                List<PfSkuAgent> pfSkuAgents = new ArrayList<>();
                for(int i=0; i<unitPrices.length; i++){
                    PfSkuAgent pfSkuAgent = new PfSkuAgent();
                    pfSkuAgent.setAgentLevelId(new Integer(i + 1));
                    pfSkuAgent.setQuantity(quantitys[i]);
                    pfSkuAgent.setUnitPrice(new BigDecimal(unitPrices[i]));
                    pfSkuAgent.setBail(new BigDecimal(bails[i]));
                    pfSkuAgent.setTotalPrice(new BigDecimal(totalPrices[i]));
                    pfSkuAgent.setIsShow(1);
                    pfSkuAgent.setIcon(iconImgNames[i]);
                    pfSkuAgent.setIsUpgrade(1);

                    pfSkuAgents.add(pfSkuAgent);
                }
                log.info("保存商品-comSpu数据[pfSkuAgents="+pfSkuAgents+"]");

                //分销分润
                log.info("保存商品-开始准备sfSkuDistributions数据");
                List<SfSkuDistribution> sfSkuDistributions = new ArrayList<>();
                for(int i=0; i<distributionDiscounts.length; i++){
                    SfSkuDistribution sfSkuDistribution = new SfSkuDistribution();
                    sfSkuDistribution.setDiscount(new BigDecimal(Double.parseDouble(distributionDiscounts[i])*0.01));
                    sfSkuDistribution.setSort(i+1);

                    sfSkuDistributions.add(sfSkuDistribution);
                }
                log.info("保存商品-comSpu数据[sfSkuDistributions="+sfSkuDistributions+"]");

                log.info("保存商品-开始准备comSkuImages数据");
                List<ComSkuImage> comSkuImages = new ArrayList<>();
                int[] imgPxs = {220, 308, 800};
                for(int i=0; i<mainImgUrls.length; i++){
                    ComSkuImage comSkuImage = new ComSkuImage();
                    comSkuImage.setCreateTime(new Date());
                    comSkuImage.setCreateMan(pbUser.getId());
                    comSkuImage.setImgUrl(mainImgNames[i]);
                    comSkuImage.setImgName(mainImgOriginalNames[i]);
                    comSkuImage.setIsDefault(i==1?1:0);

                    for(int px=0; px<imgPxs.length; px++){
                        comSkuImage.setFullImgUrl(PropertiesUtils.getStringValue("index_product_"+imgPxs[px]+"_"+imgPxs[px]+"_url") + mainImgNames[i]);
                    }

                    comSkuImages.add(comSkuImage);
                }
                log.info("保存商品-comSpu数据[comSkuImages="+comSkuImages+"]");

                ComSkuExtension comSkuExtension = new ComSkuExtension();
                comSkuExtension.setSkuBackgroundImg(skuBackgroundImgName);
                comSkuExtension.setPoster(developPosterName);

                productService.save(comSpu, comSku, comSkuExtension, comSkuImages, pfSkuAgents, sfSkuDistributions);
                return "success";
            }
        } catch(Exception e) {
            log.error("保存商品失败!");
            e.printStackTrace();
        }
        return "fail";
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response,
                         Integer spuId,
                         Integer skuId,
                         Integer skuExtensionId,
                         @RequestParam("skuImageIds")Integer[] skuImageIds,
                         Integer unitId,
                         @RequestParam("skuAgentIds")Integer[] skuAgentIds,
                         @RequestParam("skuDistributionIds")Integer[] skuDistributionIds,
                         ComSpu comSpu,
                         ComSku comSku,
                         @RequestParam("unitPrices") String[]  unitPrices,
                         @RequestParam("totalPrices")String[]  totalPrices,
                         @RequestParam("quantitys")  Integer[] quantitys,
                         @RequestParam("bails")      String[]  bails,
                         @RequestParam("distributionDiscounts")String[] distributionDiscounts,
                         @RequestParam(value = "proIconUrl", required = false)String proIconUrl,
                         @RequestParam(value = "proIconName", required = false)String proIconName,
                         @RequestParam(value = "mainImgUrls", required = false)String[] mainImgUrls,
                         @RequestParam(value = "mainImgNames", required = false)String[] mainImgNames,
                         @RequestParam(value = "mainImgOriginalNames", required = false)String[] mainImgOriginalNames,
                         @RequestParam(value = "skuBackgroundImgName", required = false)String skuBackgroundImgName,
                         @RequestParam(value = "developPosterName", required = false)String developPosterName,
                         @RequestParam(value = "iconImgUrls", required = false)String[] iconImgUrls,
                         @RequestParam(value = "iconImgNames", required = false)String[] iconImgNames
                         ) throws FileNotFoundException {

        String realPath = request.getServletContext().getRealPath("/");
               realPath = realPath.substring(0, realPath.lastIndexOf("/"));

        try {
            PbUser pbUser = (PbUser)request.getSession().getAttribute("pbUser");
            if(pbUser != null){
                comSpu.setId(spuId);
                comSpu.setModifyTime(new Date());
                comSpu.setModifyMan(pbUser.getId());
                comSpu.setUnit(unitId);

                if(StringUtil.isEmpty(comSpu.getContent())) comSpu.setContent(null);
                if(StringUtil.isEmpty(comSpu.getPolicy()))  comSpu.setPolicy(null);

                comSku.setId(skuId);
                comSku.setModifyTime(new Date());
                comSku.setModifyMan(pbUser.getId());

                if(proIconName != null){
                    comSku.setIcon(proIconName);
                }

                //代理分润
                List<PfSkuAgent> pfSkuAgents = new ArrayList<>();
                for(int i=0; i<unitPrices.length; i++){
                    PfSkuAgent pfSkuAgent = new PfSkuAgent();
                    pfSkuAgent.setId(skuAgentIds[i]);
                    pfSkuAgent.setQuantity(quantitys[i]);
                    pfSkuAgent.setUnitPrice(new BigDecimal(unitPrices[i]));
                    pfSkuAgent.setBail(new BigDecimal(bails[i]));
                    pfSkuAgent.setTotalPrice(new BigDecimal(totalPrices[i]));
                    pfSkuAgent.setBackImg((i+1)+".png");

                    pfSkuAgents.add(pfSkuAgent);

                    if(iconImgNames != null){
                        pfSkuAgent.setIcon(iconImgNames[i]);
                    }
                }

                //分销分润
                List<SfSkuDistribution> sfSkuDistributions = new ArrayList<>();
                for(int i=0; i<distributionDiscounts.length; i++){
                    SfSkuDistribution sfSkuDistribution = new SfSkuDistribution();
                    sfSkuDistribution.setId(skuDistributionIds[i]);
                    sfSkuDistribution.setDiscount(new BigDecimal(Double.parseDouble(distributionDiscounts[i])*0.01));

                    sfSkuDistributions.add(sfSkuDistribution);
                }


                List<ComSkuImage> comSkuImages = null;
                if(mainImgUrls != null){//更新了图片
                    comSkuImages = new ArrayList<>();
                    int[] imgPxs = {220, 308, 800};
                    for(int i=0; i<skuImageIds.length; i++){
                        ComSkuImage comSkuImage = new ComSkuImage();
                        comSkuImage.setId(skuImageIds[i]);
                        comSkuImage.setModifyTime(new Date());
                        comSkuImage.setModifyMan(pbUser.getId());
                        comSkuImage.setImgUrl(mainImgNames[i]);
                        comSkuImage.setImgName(mainImgOriginalNames[i]);
                        comSkuImage.setIsDefault(i==1?1:0);

                        for(int px=0; px<imgPxs.length; px++){
                            comSkuImage.setFullImgUrl(PropertiesUtils.getStringValue("index_product_"+imgPxs[px]+"_"+imgPxs[px]+"_url") + mainImgNames[i]);
                        }

                        comSkuImages.add(comSkuImage);
                    }
                }

                ComSkuExtension comSkuExtension = null;
                if(StringUtils.isNotBlank(skuBackgroundImgName) || StringUtils.isNotBlank(developPosterName)){
                    comSkuExtension = new ComSkuExtension();
                    comSkuExtension.setId(skuExtensionId);
                    comSkuExtension.setSkuBackgroundImg(StringUtils.isNotBlank(skuBackgroundImgName)?skuBackgroundImgName:null);
                    comSkuExtension.setPoster(StringUtils.isNotBlank(developPosterName)?developPosterName:null);
                }

                productService.update(comSpu, comSku, comSkuExtension, comSkuImages, pfSkuAgents, sfSkuDistributions);
                return "success";
            }
        } catch(Exception e){
            e.printStackTrace();
            log.error("修改商品失败");
        }

        return "fail";
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

    public static void main(String[] args){
        String d = "80.3";
        BigDecimal bigDecimal = new BigDecimal(Double.parseDouble(d)*0.01);
        System.out.println(bigDecimal);
    }
}
