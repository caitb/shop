package com.masiis.shop.web.platform.controller.material;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.beans.material.MaterialLibrary;
import com.masiis.shop.dao.beans.material.SkuMaterial;
import com.masiis.shop.dao.po.ComSkuMaterialGroup;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.material.service.MaterialLibraryService;
import com.masiis.shop.web.material.service.SkuMaterialService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by jiajinghao on 2016/7/6.
 * 素材库列表controller B端
 */
@Controller
@RequestMapping("/materielList")
public class MaterialLibraryController extends BaseController{
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private MaterialLibraryService materialLibraryService;
    @Resource
    private SkuMaterialService skuMaterialService;

    /**
     * jjh
     * 素材列表B端
     * @param request
     * @return
     */
    @RequestMapping(value = "/infoB")
    public ModelAndView materialLibraryList(HttpServletRequest request
                                            ){
        ModelAndView mv = new ModelAndView("/platform/material/materialGroup");
        try{
            ComUser comUser = getComUser(request);
            int pageSize = 4; //ajax请求时默认每页显示条数为4条
            List<MaterialLibrary> materialLibraryList =  materialLibraryService.SkuMaterialLibraryList(1,pageSize,comUser.getId());
            mv.addObject("LibraryList",materialLibraryList);

        }catch (Exception e){
            log.info(e.getMessage());
        }
        return mv;
    }

    /**
     * 分页展示素材库B
     * @param request
     * @param currentPage
     * @return
     */
    @RequestMapping(value = "/infoBPagenation")
    @ResponseBody
    public String materialLibraryListPagenation(HttpServletRequest request,
                                            @RequestParam(value = "currentPage",required = true) int currentPage
    ){
        JSONObject object = new JSONObject();
        try{
            ComUser comUser = getComUser(request);
            int pageSize = 4; //ajax请求时默认每页显示条数4条
            List<MaterialLibrary> materialLibraryList =  materialLibraryService.SkuMaterialLibraryList(currentPage,pageSize,comUser.getId());
            object.put("LibraryList", materialLibraryList);
            object.put("isError",false);
        }catch (Exception e){
            object.put("isError",true);
            log.info(e.getMessage());
        }
        return object.toJSONString();
    }

    /**
     * jjh
     * 素材组列表B
     * @param mlId
     * @return
     */
    @RequestMapping(value = "/groupInfoB")
    public ModelAndView materialLibraryGroup(@RequestParam(value = "mlId",required = true) Integer mlId){
        ModelAndView mv = new ModelAndView("/platform/material/subscriptionlist");
        try {
            List<ComSkuMaterialGroup> comSkuMaterialGroupList = materialLibraryService.MaterialLibraryGroup(mlId, 1, null);
            mv.addObject("groupList",comSkuMaterialGroupList);
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return mv;
    }

    /**
     * jjh
     * 素材图片展示B
     * @param mgId
     * @return
     */
    @RequestMapping(value = "/materialInfoB")
    public ModelAndView materialList(@RequestParam(value = "mgId",required = true) Integer mgId){
        ModelAndView mv = new ModelAndView("/platform/material/skuimglist");
        try {
            int pageSize = 3; //ajax请求时默认每页显示条数为3条
            List<SkuMaterial> materials = skuMaterialService.skuMaterial(mgId, 1, pageSize);
            mv.addObject("materials",materials);
            mv.addObject("mgId",mgId);
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return mv;
    }

    /**
     * jjh
     * 分页展示素材图片
     * @param mgId
     * @param currentPage
     * @return
     */
    @RequestMapping(value = "/materialInfoBPage")
    @ResponseBody
    public String materialListPage(@RequestParam(value = "mgId",required = true) Integer mgId,
                                     @RequestParam(value = "currentPage",required = true) Integer currentPage){
        JSONObject object = new JSONObject();
        try {
            int pageSize = 3; //ajax请求时默认每页显示条数为3条
            List<SkuMaterial> materials = skuMaterialService.skuMaterial(mgId, currentPage, pageSize);
            object.put("materials",materials);
            object.put("mgId",mgId);
            object.put("isError",false);
        }catch (Exception e){
            log.info(e.getMessage());
            object.put("isError",true);
        }
        return object.toJSONString();
    }
}
