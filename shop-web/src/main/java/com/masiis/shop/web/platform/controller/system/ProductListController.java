package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.system.IndexComSku;
import com.masiis.shop.dao.po.ComSpu;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.system.IndexShowService;
import com.masiis.shop.web.platform.service.system.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/4.
 */
@Controller
@RequestMapping("/productList")
public class ProductListController {

    @Resource
    private SpuService spuService;
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
        String value = PropertiesUtils.getStringValue("index_product_800_800_url");
        List<IndexComSku> indexComSk = indexShowService.findIndexComSku();
        List<IndexComSku> Com =new ArrayList<IndexComSku>();
        for (IndexComSku indexComSku:indexComSk) {
            ComSpu comSpu = spuService.getSpuById(indexComSku.getComSku().getSpuId());
            if(comSpu.getIsSale()==1) {
                //获取商品图片地址
                String url = value + indexComSku.getImgUrl();
                //重新封装商品图片地址
                indexComSku.setImgUrl(url);
                if (comUser != null && comUser.getIsAgent() == 1) {
                    //确定代理权限
                    indexComSku.setIsPartner(true);
                    //显示优惠区间
                    indexComSku.setDiscountLevel(productService.getDiscountByAgentLevel(indexComSku.getComSku().getPriceRetail()));
                } else {
                    indexComSku.setDiscountLevel("成为合伙人可查看");
                }
                Com.add(indexComSku);
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        //封装展示商品信息集合
        modelAndView.addObject("indexComSkus",Com);
        modelAndView.setViewName("platform/system/liebiao");
        return modelAndView;
    }
}
