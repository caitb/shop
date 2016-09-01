package com.masiis.shop.admin.controller.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.product.SkuNewService;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuNew;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新品
 * Created by cai_tb on 16/8/6.
 */
@Controller
@RequestMapping("/skuNew")
public class SkuNewController extends BaseController {

    private final static Log log = LogFactory.getLog(SkuNewController.class);

    @Resource
    private SkuNewService skuNewService;

    @RequestMapping("/list.shtml")
    public String list() {
        return "product/skuNewList";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder) {

        Map<String, Object> conditionMap = new HashMap<>();
        Map<String, Object> pageMap = null;
        try {
            pageMap = skuNewService.listByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap);
        } catch (Exception e) {
            log.error("获取新品列表失败![conditionMap="+conditionMap+"]"+e);
            e.printStackTrace();
        }

        return pageMap;
    }

    /**
     * 获取所有sku
     * @return
     */
    @RequestMapping("/listSku.do")
    @ResponseBody
    public Object listSku() {
        List<ComSku> comSkus = null;
        try {
            comSkus = skuNewService.listSku();

        } catch (Exception e) {
            log.error("获取sku列表失败!"+e);
            e.printStackTrace();
        }

        return comSkus;
    }



    @RequestMapping("/addSkuNew.do")
    @ResponseBody
    public Object addSkuNew(HttpServletRequest request, ComSkuNew comSkuNew) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            if(comSkuNew.getId() != null){
                skuNewService.update(comSkuNew);
                resultMap.put("code", "success");
                resultMap.put("msg", "修改新品成功!");
                return resultMap;
            }

            ComSkuNew oldComSkuNew = skuNewService.findBySkuId(comSkuNew.getSkuId());
            if(oldComSkuNew != null){
                resultMap.put("code", "fail");
                resultMap.put("msg", "新品已存在!");
            }else{
                comSkuNew.setSort(0);
                comSkuNew.setCreateMan(getPbUser(request).getId());
                comSkuNew.setCreateTime(new Date());
                comSkuNew.setStatus(1);

                skuNewService.add(comSkuNew);
                resultMap.put("code", "success");
                resultMap.put("msg", "添加新品成功!");
            }
        } catch (Exception e) {
            resultMap.put("code", "fail");
            resultMap.put("msg", "操作异常!");
            log.error("添加新品失败![comSkuNew="+comSkuNew+"]"+e);
            e.printStackTrace();
        }

        return resultMap;
    }
}
