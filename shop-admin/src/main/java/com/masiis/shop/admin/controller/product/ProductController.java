package com.masiis.shop.admin.controller.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.admin.service.product.*;
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
                      @RequestParam("discounts")String[] discounts,
                      @RequestParam("quantitys")Integer[] quantitys,
                      @RequestParam("bails")Integer[] bails,
                      @RequestParam("distributionDiscounts")String[] distributionDiscounts,
                      String proIconUrl,
                      String proIconName,
                      @RequestParam("mainImgUrls")String[] mainImgUrls,
                      @RequestParam("mainImgNames")String[] mainImgNames,
                      @RequestParam("mainImgOriginalNames")String[] mainImgOriginalNames,
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

                //上传商品标志到OSS
                String proIconAbsoluteUrl = realPath + proIconUrl;
                File proIconFile = new File(proIconAbsoluteUrl);
                OSSObjectUtils.uploadFile(proIconFile, "static/product/product_icon/");

                proIconFile.delete();
                log.info("保存商品-comSpu数据[comSpu="+comSpu+"]");

                log.info("保存商品-开始准备pfSkuAgents数据");
                //代理分润
                List<PfSkuAgent> pfSkuAgents = new ArrayList<>();
                for(int i=0; i<discounts.length; i++){
                    PfSkuAgent pfSkuAgent = new PfSkuAgent();
                    pfSkuAgent.setAgentLevelId(new Integer(i + 1));
                    pfSkuAgent.setDiscount(new BigDecimal(Double.parseDouble(discounts[i])*0.01));
                    pfSkuAgent.setQuantity(quantitys[i]);
                    pfSkuAgent.setBail(new BigDecimal(bails[i]));
                    pfSkuAgent.setIsShow(1);
                    pfSkuAgent.setIcon(iconImgNames[i]);

                    pfSkuAgents.add(pfSkuAgent);

                    //上传代理等级图标到OSS
                    String imgAbsoluteUrl = realPath + iconImgUrls[i];
                    File iconFile = new File(imgAbsoluteUrl);
                    OSSObjectUtils.uploadFile(iconFile, "static/product/agent_icon/");

                    iconFile.delete();
                }
                log.info("保存商品-comSpu数据[pfSkuAgents="+pfSkuAgents+"]");

                log.info("保存商品-开始准备sfSkuDistributions数据");
                //分销分润
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
                        OSSObjectUtils.uploadFile(curFile, "static/product/"+imgPxs[px]+"_"+imgPxs[px]+"/");

                        //删除缩放的图片
                        curFile.delete();

                        comSkuImage.setFullImgUrl(PropertiesUtils.getStringValue("index_product_"+imgPxs[px]+"_"+imgPxs[px]+"_url") + mainImgNames[i]);
                    }

                    //删除原图
                    new File(imgAbsoluteUrl).delete();

                    comSkuImages.add(comSkuImage);
                }
                log.info("保存商品-comSpu数据[comSkuImages="+comSkuImages+"]");

            log.info("保存商品-开始上传商品详情图片");
            /* 上传商品详情图 */
            File detailDir = new File(realPath + "/static/product/detail_img");
            if(detailDir.exists() && detailDir.listFiles().length > 0){
                File[] files = detailDir.listFiles();
                for(File f : files){
                    OSSObjectUtils.uploadFile(f, "static/product/detail_img/");
                    f.delete();
                }
            }
            log.info("保存商品-结束上传商品详情图片");

                productService.save(comSpu, comSku, comSkuImages, pfSkuAgents, sfSkuDistributions);
                return "保存成功!";
            }
        } catch(Exception e) {
            log.error("保存商品失败!");
            e.printStackTrace();
        }
        return "保存失败!";
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response,
                         Integer spuId,
                         Integer skuId,
                         @RequestParam("skuImageIds")Integer[] skuImageIds,
                         Integer unitId,
                         @RequestParam("skuAgentIds")Integer[] skuAgentIds,
                         @RequestParam("skuDistributionIds")Integer[] skuDistributionIds,
                         ComSpu comSpu,
                         ComSku comSku,
                         @RequestParam("discounts")String[] discounts,
                         @RequestParam("quantitys")Integer[] quantitys,
                         @RequestParam("bails")String[] bails,
                         @RequestParam("distributionDiscounts")String[] distributionDiscounts,
                         @RequestParam(value = "proIconUrl", required = false)String proIconUrl,
                         @RequestParam(value = "proIconName", required = false)String proIconName,
                         @RequestParam(value = "mainImgUrls", required = false)String[] mainImgUrls,
                         @RequestParam(value = "mainImgNames", required = false)String[] mainImgNames,
                         @RequestParam(value = "mainImgOriginalNames", required = false)String[] mainImgOriginalNames,
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

                comSku.setId(skuId);
                comSku.setModifyTime(new Date());
                comSku.setModifyMan(pbUser.getId());

                if(proIconName != null){
                    comSku.setIcon(proIconName);

                    //上传代理等级图标到OSS
                    String proIconAbsoluteUrl = realPath + proIconUrl;
                    File proIconFile = new File(proIconAbsoluteUrl);
                    OSSObjectUtils.uploadFile(proIconFile, "static/product/product_icon/");

                    proIconFile.delete();
                }

                //代理分润
                List<PfSkuAgent> pfSkuAgents = new ArrayList<>();
                for(int i=0; i<discounts.length; i++){
                    PfSkuAgent pfSkuAgent = new PfSkuAgent();
                    pfSkuAgent.setId(skuAgentIds[i]);
                    pfSkuAgent.setDiscount(new BigDecimal(Double.parseDouble(discounts[i])*0.01));
                    pfSkuAgent.setQuantity(quantitys[i]);
                    pfSkuAgent.setBail(new BigDecimal(bails[i]));
                    pfSkuAgent.setBackImg((i+1)+".png");

                    pfSkuAgents.add(pfSkuAgent);

                    if(iconImgNames != null){
                        pfSkuAgent.setIcon(iconImgNames[i]);

                        //上传代理等级图标到OSS
                        String imgAbsoluteUrl = realPath + iconImgUrls[i];
                        File iconFile = new File(imgAbsoluteUrl);
                        OSSObjectUtils.uploadFile(iconFile, "static/product/agent_icon/");

                        iconFile.delete();
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
                    String folderPath = realPath + "/current";
                    int[] imgPxs = {220, 308, 800};
                    File folder = new File(folderPath);
                    if(!folder.exists()){
                        folder.mkdir();
                    }
                    for(int i=0; i<skuImageIds.length; i++){
                        ComSkuImage comSkuImage = new ComSkuImage();
                        comSkuImage.setId(skuImageIds[i]);
                        comSkuImage.setModifyTime(new Date());
                        comSkuImage.setModifyMan(pbUser.getId());
                        comSkuImage.setImgUrl(mainImgNames[i]);
                        comSkuImage.setImgName(mainImgOriginalNames[i]);
                        comSkuImage.setIsDefault(i==1?1:0);

                        String imgAbsoluteUrl = realPath + mainImgUrls[i];
                        String resultPath = folderPath + "/" + mainImgNames[i];
                        for(int px=0; px<imgPxs.length; px++){
                            ImageUtils.scale2(imgAbsoluteUrl, resultPath, imgPxs[px], imgPxs[px], true);
                            File curFile = new File(resultPath);
                            OSSObjectUtils.uploadFile(curFile, "static/product/"+imgPxs[px]+"_"+imgPxs[px]+"/");

                            //删除缩放的图片
                            curFile.delete();

                            comSkuImage.setFullImgUrl(PropertiesUtils.getStringValue("index_product_"+imgPxs[px]+"_"+imgPxs[px]+"_url") + mainImgNames[i]);
                        }

                        //删除原图
                        new File(imgAbsoluteUrl).delete();

                        comSkuImages.add(comSkuImage);
                    }
                }

                /* 上传商品详情图 */
                File detailDir = new File(realPath + "/static/product/detail_img");
                if(detailDir.exists() && detailDir.listFiles().length > 0){
                    File[] files = detailDir.listFiles();
                    for(File f : files){
                        OSSObjectUtils.uploadFile(f, "static/product/detail_img/");
                        f.delete();
                    }
                }

                productService.update(comSpu, comSku, comSkuImages, pfSkuAgents, sfSkuDistributions);
                return "修改商品成功!";
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
