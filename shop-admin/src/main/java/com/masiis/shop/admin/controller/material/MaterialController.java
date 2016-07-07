package com.masiis.shop.admin.controller.material;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.material.MaterialService;
import com.masiis.shop.admin.service.product.SkuService;
import com.masiis.shop.dao.beans.material.MaterialLibrary;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @RequestMapping("/listLibrary.shtml")
    public String listLibrary() {
        return "material/listLibrary";
    }

    @RequestMapping("/listGroup.shtml")
    public String listGroup(Integer mlId, Model model) {
        model.addAttribute("mlId", mlId);
        return "material/listGroup";
    }

    @RequestMapping("/listMaterial.shtml")
    public String listMaterial(Integer mgId, Model model) {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("mgId", mgId);

        model.addAttribute("pageMap", materialService.listMaterialItem(conditionMap));
        model.addAttribute("mgId", mgId);
        return "material/materialItem";
    }

    /**
     * 素材库列表
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @return
     */
    @RequestMapping("/listLibrary.do")
    @ResponseBody
    public Object listLibrary(
                        Integer pageNumber,
                        Integer pageSize,
                        String sortName,
                        String sortOrder
                      ){

        Map<String, Object> conditionMap = new HashMap<>();
        Map<String, Object> pageMap = new HashMap<>();
        try {
            pageMap = materialService.listLibraryByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap);
        } catch (Exception e) {
            log.error("获取素材库列表失败![conditionMap="+conditionMap+"]"+e);
            e.printStackTrace();
        }

        return pageMap;
    }

    /**
     * 素材库模块列表
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @return
     */
    @RequestMapping("/listGroup.do")
    @ResponseBody
    public Object listGroup(
                            Integer pageNumber,
                            Integer pageSize,
                            String sortName,
                            String sortOrder,
                            Integer mlId
                           ){

        Map<String, Object> conditionMap = new HashMap<>();
        Map<String, Object> pageMap = new HashMap<>();
        try {
            conditionMap.put("mlId", mlId);
            pageMap = materialService.listGroupByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap);
        } catch (Exception e) {
            log.error("获取素材库列表失败![conditionMap="+conditionMap+"]"+e);
            e.printStackTrace();
        }

        return pageMap;
    }

    /**
     * 添加素材页面
     * @return
     */
    @RequestMapping("/addMaterial.shtml")
    public ModelAndView addMaterial() {
        ModelAndView mav = new ModelAndView("material/add");

        try {
//            List<MaterialLibrary> materialLibraries = materialService.listAllLibrary();
//            mav.addObject("materialLibraries", materialLibraries);
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
     * 更改素材库
     * @param comSkuMaterialLibrary
     * @return
     */
    @RequestMapping("/updateLibrary.do")
    @ResponseBody
    public Map<String, Object> updateLibrary(ComSkuMaterialLibrary comSkuMaterialLibrary) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            materialService.updateMaterialLibrary(comSkuMaterialLibrary);
            resultMap.put("code", "success");
            resultMap.put("msg", "更改成功!");
        } catch (Exception e) {
            resultMap.put("code", "fail");
            resultMap.put("msg", "更改失败!");
            log.error("更改素材库失败![comSkuMaterialLibrary="+comSkuMaterialLibrary+"]"+e);
            e.printStackTrace();
        }

        return resultMap;
    }

    /**
     * 更改素材组
     * @param comSkuMaterialGroup
     * @return
     */
    @RequestMapping("/updateGroup.do")
    @ResponseBody
    public Map<String, Object> updateGroup(ComSkuMaterialGroup comSkuMaterialGroup) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            materialService.updateMaterialGroup(comSkuMaterialGroup);
            resultMap.put("code", "success");
            resultMap.put("msg", "更改成功!");
        } catch (Exception e) {
            resultMap.put("code", "fail");
            resultMap.put("msg", "更改失败!");
            log.error("更改素材组失败![comSkuMaterialGroup="+comSkuMaterialGroup+"]"+e);
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
     * 保存素材
     * @param comSkuMaterial
     * @return
     */
    @RequestMapping("/saveMaterial.do")
    @ResponseBody
    public Map<String, Object> saveMaterial(
                                            HttpServletRequest request,
                                            ComSkuMaterial comSkuMaterial,
                                            @RequestParam(value = "types",       required = false)Integer[] types,
                                            @RequestParam(value = "fileNames",   required = false)String[]  fileNames,
                                            @RequestParam(value = "fileUrls",    required = false)String[]  fileUrls,
                                            @RequestParam(value = "fileSuffixs", required = false)String[]  fileSuffixs,
                                            @RequestParam(value = "fileSizes",   required = false)Long[]  fileSizes) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            PbUser pbUser = getPbUser(request);

            comSkuMaterial.setCreateTime(new Date());
            comSkuMaterial.setCreateMan(pbUser.getId());
            comSkuMaterial.setSort(0);
            List<ComSkuMaterialItem> comSkuMaterialItems = new ArrayList<>();
            for(int i=0; i<types.length; i++){
                ComSkuMaterialItem comSkuMaterialItem = new ComSkuMaterialItem();
                comSkuMaterialItem.setCreateTime(new Date());
                comSkuMaterialItem.setCreateMan(pbUser.getId());
                comSkuMaterialItem.setType(types[0]);
                comSkuMaterialItem.setFileName(fileNames[i]);
                comSkuMaterialItem.setFileUrl(fileUrls[i]);
                comSkuMaterialItem.setFileSuffix(fileSuffixs[i]);
                comSkuMaterialItem.setFileSize(fileSizes[i]);
                comSkuMaterialItem.setMaterialLibraryId(comSkuMaterial.getMaterialLibraryId());
                comSkuMaterialItem.setMaterialGroupId(comSkuMaterial.getMaterialGroupId());
                comSkuMaterialItem.setSort(0);

                comSkuMaterialItems.add(comSkuMaterialItem);
            }

            materialService.saveMaterial(comSkuMaterial, comSkuMaterialItems);

            resultMap.put("code", "success");
            resultMap.put("msg", "保存成功!");
        } catch (Exception e) {
            resultMap.put("code", "fail");
            resultMap.put("msg", "保存失败!");
            log.error("保存素材失败![comSkuMaterial="+comSkuMaterial+"]"+e);
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
