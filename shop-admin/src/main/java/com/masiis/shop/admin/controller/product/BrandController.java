package com.masiis.shop.admin.controller.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.github.pagehelper.StringUtil;
import com.masiis.shop.admin.service.product.BrandService;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.dao.po.ComBrand;
import com.masiis.shop.dao.po.PbUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
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

    @RequestMapping("/add.shtml")
    public String add(){
        return "brand/add";
    }

    @RequestMapping("/edit.shtml")
    public String edit(HttpServletRequest request, HttpServletResponse response,
                       Model model,
                       Integer brandId){
        ComBrand comBrand = brandService.find(brandId);
        model.addAttribute("brand", comBrand);

        return "brand/edit";
    }

    @RequestMapping("/add.do")
    @ResponseBody
    public String add(HttpServletRequest request, HttpServletResponse response,
                      ComBrand comBrand,
                      @RequestParam("logoUrl")String logoUrl,
                      @RequestParam("logoName")String logoName) throws FileNotFoundException{

        String realPath = request.getServletContext().getRealPath("/");
        realPath = realPath.substring(0, realPath.lastIndexOf("/"));

        try {
            PbUser pbUser = (PbUser)request.getSession().getAttribute("pbUser");
            if(pbUser != null) {
                log.info("保存品牌-开始准备comBrand数据");
                comBrand.setCreateTime(new Date());
                comBrand.setLogoUrl("http://file.masiis.com/static/brand/"+logoName);
                //上传商品标志到OSS
                String logoAbsoluteUrl = realPath + logoUrl;
                File logoFile = new File(logoAbsoluteUrl);
                OSSObjectUtils.uploadFile(logoFile, "static/brand/");

                logoFile.delete();
                log.info("保存品牌-comBrand数据[comBrand=" + comBrand + "]");
            }

            log.info("保存品牌-开始上传品牌介绍图片");
            /* 上传商品详情图 */
            File detailDir = new File(realPath + "/static/product/detail_img");
            if(detailDir.exists() && detailDir.listFiles().length > 0){
                File[] files = detailDir.listFiles();
                for(File f : files){
                    OSSObjectUtils.uploadFile(f, "static/product/detail_img/");
                    f.delete();
                }
            }
            log.info("保存品牌-结束上传品牌介绍图片");

            brandService.add(comBrand);

            return "success";
        } catch (Exception e) {
            log.error("添加品牌失败![comBrand="+comBrand+"]");
            e.printStackTrace();
        }

        return "error";
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response,
                      ComBrand comBrand,
                      @RequestParam(value = "logoUrl", required = false)String logoUrl,
                      @RequestParam(value = "logoName", required = false)String logoName) throws FileNotFoundException{

        String realPath = request.getServletContext().getRealPath("/");
        realPath = realPath.substring(0, realPath.lastIndexOf("/"));

        try {
            PbUser pbUser = (PbUser)request.getSession().getAttribute("pbUser");
            if(pbUser != null && logoUrl != null) {
                log.info("保存品牌-开始准备comBrand数据");
                comBrand.setLogoUrl("http://file.masiis.com/static/brand/"+logoName);
                //上传商品标志到OSS
                String logoAbsoluteUrl = realPath + logoUrl;
                File logoFile = new File(logoAbsoluteUrl);
                OSSObjectUtils.uploadFile(logoFile, "static/brand/");

                logoFile.delete();
                log.info("保存品牌-comBrand数据[comBrand=" + comBrand + "]");
            }

            log.info("保存品牌-开始上传品牌介绍图片");
            /* 上传商品详情图 */
            File detailDir = new File(realPath + "/static/product/detail_img");
            if(detailDir.exists() && detailDir.listFiles().length > 0){
                File[] files = detailDir.listFiles();
                for(File f : files){
                    OSSObjectUtils.uploadFile(f, "static/product/detail_img/");
                    f.delete();
                }
            }
            log.info("保存品牌-结束上传品牌介绍图片");

            if(StringUtil.isEmpty(comBrand.getContent())) comBrand.setContent(null);

            brandService.update(comBrand);

            return "success";
        } catch (Exception e) {
            log.error("添加品牌失败![comBrand="+comBrand+"]");
            e.printStackTrace();
        }

        return "error";
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
