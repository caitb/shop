package com.masiis.shop.admin.controller.material;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.material.MaterialService;
import com.masiis.shop.admin.service.product.SkuService;
import com.masiis.shop.dao.beans.material.MaterialLibrary;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 素材库
 * Created by cai_tb on 16/7/1.
 */
@Controller
@RequestMapping("/material")
public class MaterialController extends BaseController {

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
     * 保存素材库
     * @param comSkuMaterialLibrary
     * @return
     */
    @RequestMapping("/saveLibrary.do")
    @ResponseBody
    public Map<String, Object> saveLibrary(HttpServletRequest request, ComSkuMaterialLibrary comSkuMaterialLibrary) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            PbUser pbUser = getPbUser(request);
            comSkuMaterialLibrary.setCreateTime(new Date());
            comSkuMaterialLibrary.setCreateMan(pbUser.getId());
            comSkuMaterialLibrary.setSubscriptionNum(0);
            comSkuMaterialLibrary.setSort(0);
            materialService.saveMaterialLibrary(comSkuMaterialLibrary);

            resultMap.put("code", "success");
            resultMap.put("msg", "保存成功!");
        } catch (Exception e) {
            resultMap.put("code", "fail");
            resultMap.put("msg", "保存失败!");
            log.error("保存素材库失败![comSkuMaterialLibrary="+comSkuMaterialLibrary+"]"+e);
            e.printStackTrace();
        }

        return resultMap;
    }

    /**
     * 保存素材组
     * @param request
     * @param comSkuMaterialGroup
     * @return
     */
    @RequestMapping("/saveGroup.do")
    @ResponseBody
    public Map<String, Object> saveGroup(HttpServletRequest request, ComSkuMaterialGroup comSkuMaterialGroup) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            PbUser pbUser = getPbUser(request);
            comSkuMaterialGroup.setCreateMan(pbUser.getId());
            comSkuMaterialGroup.setCreateTime(new Date());
            comSkuMaterialGroup.setSort(0);

            materialService.saveMaterialGroup(comSkuMaterialGroup);

            resultMap.put("code", "success");
            resultMap.put("msg", "保存成功!");
        } catch (Exception e) {
            resultMap.put("code", "fail");
            resultMap.put("msg", "保存失败!");
            log.error("保存素材组失败![comSkuMaterialGroup="+comSkuMaterialGroup+"]"+e);
            e.printStackTrace();
        }

        return resultMap;
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

    /**
     * 加载素材库
     * @return
     */
    @RequestMapping("/loadLibrary.do")
    @ResponseBody
    public Object loadLibrary() {
        List<ComSkuMaterialLibrary> libraries = null;
        try {
            libraries = materialService.loadAll();
        } catch (Exception e) {
            log.error("加载素材库失败!"+e);
            e.printStackTrace();
        }

        return libraries;
    }

    /**
     * 加载素材组
     * @return
     */
    @RequestMapping("/loadGroup.do")
    @ResponseBody
    public Object loadGroup() {
        List<MaterialLibrary> materialLibraries = null;

        try {
            materialLibraries = materialService.listAllLibrary();
        } catch (Exception e) {
            log.error("加载素材组失败!" + e);
            e.printStackTrace();
        }

        return materialLibraries;
    }


}
