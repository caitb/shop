package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.dao.po.ComSkuImage;
import com.masiis.shop.dao.po.ComSkuImageExample;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.system.ComSkuImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/2.
 */

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

    private ComSkuImageService comSkuImageService;

    @RequestMapping("banner")
    public ModelAndView BannerList(){
       /* List<ComSkuImage> imageByExample = comSkuImageService.findImageByExample(new ComSkuImageExample());
        List<String> urls = new ArrayList<String>();
        for (ComSkuImage image:imageByExample) {
            String url = "/"+image.getImgUrl() +"/" +image.getImgName();
            urls.add(url);
        }modelAndView.addObject("image",imageByExample);*/
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index");
        return modelAndView;
    }
}
