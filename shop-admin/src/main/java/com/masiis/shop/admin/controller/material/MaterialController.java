package com.masiis.shop.admin.controller.material;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.material.MaterialService;
import com.masiis.shop.admin.service.product.SkuService;
import com.masiis.shop.dao.beans.material.MaterialLibrary;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuMaterial;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by cai_tb on 16/7/1.
 */
@Controller
@RequestMapping("/material")
public class MaterialController {

    private final static Log log = LogFactory.getLog(MaterialController.class);

    @Resource
    private MaterialService materialService;
    @Resource
    private SkuService skuService;

    @RequestMapping("/list.shtml")
    public String list() {
        return "material/list";
    }

    @RequestMapping("/addMaterial.shtml")
    public ModelAndView addMaterial() {
        ModelAndView mav = new ModelAndView("material/add");

        try {
            List<MaterialLibrary> materialLibraries = materialService.listAllLibrary();
            mav.addObject("materialLibraries", materialLibraries);
        } catch (Exception e) {
            log.error("获取素材库失败!"+e);
        }

        return mav;
    }

    /**
     * 加载商品
     * @return
     */
    @RequestMapping("/loadSku.do")
    @ResponseBody
    public Object loadSku() {
        List<ComSku> comSkus = null;
        try {
             comSkus = skuService.listByCondition(null);
        } catch (Exception e) {
            log.error("获取商品失败"+e);
            e.printStackTrace();
        }

        return comSkus;
    }




}
