package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.system.IndexComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.system.IndexShowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/4.
 */
@Controller
@RequestMapping("/productList")
public class ProductListController {


    @Resource
    private IndexShowService indexShowService;

    @Resource
    private ProductService productService;

    @RequestMapping("showProduct")
    public ModelAndView showProductList(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        //获取用户信息
        ComUser comUser =(ComUser)session.getAttribute("comUser");
        //获取图片地址常量
        String value = PropertiesUtils.getStringValue("index_product_100_100_url");
        List<IndexComSku> indexComSk = indexShowService.findIndexComSku();
        for (IndexComSku indexComSku:indexComSk) {
            //获取商品图片地址
            String url = value + indexComSku.getImgUrl();
            //重新封装商品图片地址
            indexComSku.setImgUrl(url);
            //确定代理权限
            indexComSku.setIsPartner(true);
            //显示优惠区间
            indexComSku.setDiscountLevel(productService.getDiscountByAgentLevel());
//            if(comUser!=null && comUser.getIsAgent()==1){
//            indexComSku.setIsPartner(true);
//                //确定代理权限，显示优惠区间
//                indexComSku.setDiscountLevel(productService.getDiscountByAgentLevel());
//            }else{
//                indexComSku.setDiscountLevel("成为合伙人可查看");
//            }
        }
        ModelAndView modelAndView = new ModelAndView();
        //封装展示商品信息集合
        modelAndView.addObject("indexComSkus",indexComSk);
        modelAndView.setViewName("platform/system/liebiao");
        return modelAndView;
    }
}
