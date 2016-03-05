package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.system.IndexComSku;
import com.masiis.shop.web.platform.service.system.IndexShowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/4.
 */
@Controller
@RequestMapping("/productList")
public class ProductListController {


    @Resource
    private IndexShowService indexShowService;

    @RequestMapping("showProduct")
    public ModelAndView showProductList() throws Exception{
        //获取图片地址常量
        String value = PropertiesUtils.getStringValue("index_banner_url");
        List<IndexComSku> indexComSk = indexShowService.findIndexComSku();
        for (IndexComSku indexComSku:indexComSk) {
            //获取商品图片地址
            String url = value + indexComSku.getImgUrl();
            //重新封装商品图片地址
            indexComSku.setImgUrl(url);
        }
        ModelAndView modelAndView = new ModelAndView();
        //封装展示商品信息集合
        modelAndView.addObject("indexComSkus",indexComSk);

        modelAndView.setViewName("platform/system/liebiao");
        return modelAndView;
    }
}
