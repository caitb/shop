package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.system.IndexComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PbBanner;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.system.IndexShowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/2.
 */

@Controller
//@RequestMapping("/index")
public class IndexController extends BaseController {

    @Resource
    private IndexShowService indexShowService;
    @Resource
    private ProductService productService;

    @RequestMapping("/index")
    public ModelAndView indexList(HttpServletRequest request)throws Exception{
        HttpSession session = request.getSession();
        //获取用户信息
        ComUser comUser =(ComUser)session.getAttribute("comUser");
        //获取图片地址常量
        String value = PropertiesUtils.getStringValue("index_banner_url");
        //获取轮播图片
        List<PbBanner> pbBanner = indexShowService.findPbBanner();
        List<String> urls = new ArrayList<>();
        for (PbBanner banner:pbBanner) {
            //图片地址
            String url = value + banner.getImgUrl();
            urls.add(url);
        }
        ModelAndView modelAndView = new ModelAndView();
        //封装图片地址集合
        modelAndView.addObject("urls",urls);

        //获取商品图片地址常量
        String skuValue = PropertiesUtils.getStringValue("index_product_100_100_url");
        //获取主页展示商品信息
        List<IndexComSku> indexComSkus = indexShowService.findIndexComSku();
        for (IndexComSku indexComSku:indexComSkus) {
            //获取商品图片地址
            String url = skuValue + indexComSku.getImgUrl();
            //重新封装商品图片地址
            indexComSku.setImgUrl(url);
            //判断会员权限
            indexComSku.setDiscountLevel(productService.getDiscountByAgentLevel(indexComSku.getComSku().getPriceRetail()));
//            if(comUser!=null && comUser.getIsAgent()==1){
//                //确定代理权限，显示优惠区间
//                indexComSku.setDiscountLevel(productService.getDiscountByAgentLevel());
//            }else{
//                indexComSku.setDiscountLevel("成为合伙人可查看");
//            }
        }
        //封装展示商品信息集合
        modelAndView.addObject("indexComSkus",indexComSkus);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
