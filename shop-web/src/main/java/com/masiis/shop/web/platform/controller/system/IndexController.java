package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.PbBanner;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.system.PbBannerService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/2.
 */

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Resource
    private PbBannerService pbBannerService;

    @RequestMapping("banner")
    public ModelAndView BannerList(HttpSession session){
        String value = PropertiesUtils.getStringValue("index_banner_url");
        List<PbBanner> pbBanner = pbBannerService.findPbBanner();
        List<String> urls = new ArrayList<>();
        for (PbBanner banner:pbBanner) {
            String url = value+"/"+banner.getImgUrl() +"/" +banner.getName();
            urls.add(url);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("urls",urls);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
