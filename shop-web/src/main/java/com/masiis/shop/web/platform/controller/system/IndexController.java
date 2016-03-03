package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.ComSkuImage;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.system.ComSkuImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/2.
 */

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Resource
    private ComSkuImageService comSkuImageService;

    @RequestMapping("banner")
    public ModelAndView BannerList(){
        String value = PropertiesUtils.getStringValue("index_banner_url");
        List<ComSkuImage> imageByExample = comSkuImageService.findImage();
        List<String> urls = new ArrayList<>();
        for (ComSkuImage image:imageByExample) {
            String url = value+"/"+image.getImgUrl() +"/" +image.getImgName();
            urls.add(url);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("urls",urls);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
