package com.masiis.shop.admin.controller.sort;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.sort.BrandSortService;
import com.masiis.shop.dao.po.ComBrand;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfSkuStock;
import com.masiis.shop.dao.po.PfSysMenu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/15.
 */
@Controller
@RequestMapping("/brandsort")
public class BrandSortController extends BaseController {
    private final static Log log = LogFactory.getLog(BrandSortController.class);

    @Resource
    private BrandSortService brandSortService;

    @RequestMapping("/list.shtml")
    public String list() {
        return "brandsort/list";
    }

    @RequestMapping("/brandSortList.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder) {
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> pageMap = null;
        try {
            pageMap = brandSortService.bannerSortList(pageNumber, pageSize, sortName, sortOrder, map);
        } catch (Exception e) {
            log.error("获取bannerSort列表失败![conditionMap="+map+"]"+e);
            e.printStackTrace();
        }
        return pageMap;
    }

    @RequestMapping("/updateSort")
    @ResponseBody
    public Object update(HttpServletRequest request, HttpServletResponse response,
                         PfSysMenu pfSysMenu){
        try {
            brandSortService.update(pfSysMenu);
            return "success";
        } catch (Exception e) {
            log.error("更新库存失败![pfSkuStock=" + pfSysMenu + "]");
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/listBrand")
    @ResponseBody
    public Map<String,Object> listBrand(){
        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> brandList = brandSortService.brandList();
        map.put("brandList",brandList);
        return map;
    }

    @RequestMapping("/addBrand")
    @ResponseBody
    public Object addBrand(HttpServletRequest request ,PfSysMenu pfSysMenu){
        Map<String,Object> resultMap = new HashMap<>();

        try {
            pfSysMenu.setCreateMan(getPbUser(request).getId());
            brandSortService.addBrand(pfSysMenu);

            resultMap.put("code", "success");
            resultMap.put("msg", "设置成功");
        } catch (Exception e) {
            resultMap.put("code", "error");
            resultMap.put("msg", "设置失败");

            log.error("添加品牌排序失败![PfSysMenu="+pfSysMenu+"]"+e);
            e.printStackTrace();
        }
        return resultMap;
    }


    @RequestMapping("/deleteBrand")
    @ResponseBody
    public Object deleteBrand(Integer id){
        return brandSortService.deleteBrand(id);
    }

    @RequestMapping("/listFamily")
    @ResponseBody
    public Map<String,Object> listFamily(){
        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> familyList = brandSortService.familyList();
        map.put("familyList",familyList);
        return map;
    }

    @RequestMapping("/addFamily")
    @ResponseBody
    public Object addFamliy(HttpServletRequest request ,PfSysMenu pfSysMenu){
        Map<String,Object> resultMap = new HashMap<>();

        try {
            pfSysMenu.setCreateMan(getPbUser(request).getId());
            brandSortService.addFamily(pfSysMenu);

            resultMap.put("code", "success");
            resultMap.put("msg", "设置成功");
        } catch (Exception e) {
            resultMap.put("code", "error");
            resultMap.put("msg", "设置失败");

            log.error("添加品牌排序失败![PfSysMenu="+pfSysMenu+"]"+e);
            e.printStackTrace();
        }

        return resultMap;
    }

    @RequestMapping("/listTeam")
    @ResponseBody
    public Map<String,Object> listTeam(){
        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> teamList = brandSortService.teamList();
        map.put("teamList",teamList);
        return map;
    }

    @RequestMapping("/addTeam")
    @ResponseBody
    public Object addTeam(HttpServletRequest request ,PfSysMenu pfSysMenu){
        Map<String,Object> resultMap = new HashMap<>();

        try {
            pfSysMenu.setCreateMan(getPbUser(request).getId());
            brandSortService.addTeam(pfSysMenu);

            resultMap.put("code", "success");
            resultMap.put("msg", "设置成功");
        } catch (Exception e) {
            resultMap.put("code", "error");
            resultMap.put("msg", "设置失败");

            log.error("添加品牌排序失败![PfSysMenu="+pfSysMenu+"]"+e);
            e.printStackTrace();
        }

        return resultMap;
    }


}
