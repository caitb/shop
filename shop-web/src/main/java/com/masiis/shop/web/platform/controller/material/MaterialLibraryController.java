package com.masiis.shop.web.platform.controller.material;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.dao.beans.material.MaterialLibrary;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.material.service.MaterialLibraryService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    /**
     * jjh
     * 素材列表B端
     * @param request
     * @return
     */
    @RequestMapping(value = "/infoB")
    public ModelAndView materialLibraryList(HttpServletRequest request,
                                            @RequestParam(value = "currentPage",required = true) int currentPage
                                            ){
        ModelAndView mv = new ModelAndView("/platform/material/materialGroup");
        try{
            ComUser comUser = getComUser(request);
            int pageSize = 5; //ajax请求时默认每页显示条数为5条
            currentPage = currentPage +1;
            List<MaterialLibrary> materialLibraryList =  materialLibraryService.SkuMaterialLibraryList(currentPage,pageSize,comUser.getId());
            mv.addObject("LibraryList",materialLibraryList);

        }catch (Exception e){
            log.info(e.getMessage());
        }
        return mv;
    }

}
