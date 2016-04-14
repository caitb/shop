package com.masiis.shop.web.platform.controller.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.ManageShopProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by JingHao on 2016/4/13 0013.
 */
@Controller
@RequestMapping("/shop")
public class ManageProductController extends BaseController {
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private ManageShopProductService manageShopProductService;

    @RequestMapping("/managePro.htmls")
    public ModelAndView manageShopProduct(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(value="shopId",required = true) Long shopId,
                                         @RequestParam(value="isSale",required = true) Integer isSale) throws Exception{
            ModelAndView mav = new ModelAndView("/platform/product/shop_product");
            ComUser comUser = getComUser(request);
            List<SkuInfo> skuInfoList = manageShopProductService.getShopProductsList(shopId, isSale, comUser.getId());
            mav.addObject("skuInfoList", skuInfoList);
            mav.addObject("comUser", comUser);
            mav.addObject("shopId", shopId);
            return mav;
    }

    @RequestMapping("/updateSale.do")
    @ResponseBody
    public String updateProductSale(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value="shopSkuId",required = true) Long shopSkuId,
                                    @RequestParam(value="isSale",required = true) Integer isSale
                                    ){
        JSONObject object = new JSONObject();
     try {
         manageShopProductService.updateSale(shopSkuId,isSale);
         object.put("isError", false);

     }catch (Exception ex){
         object.put("isError", true);
         object.put("message", ex.getMessage());
     }
        return object.toJSONString();
    }

    @RequestMapping("/deliverSale.do")
    @ResponseBody
    public String deliverProductSale(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value="shopId",required = true) Long shopId,
                                    @RequestParam(value="isSale",required = true) Integer isSale
    ){
        JSONObject object = new JSONObject();
        try {
            ComUser comUser = getComUser(request);
            List<SkuInfo> skuInfoList = manageShopProductService.getShopProductsList(shopId, isSale, comUser.getId());
            object.put("isError", false);
            object.put("skuInfoList",skuInfoList);
        }catch (Exception ex){
            object.put("isError", true);
            object.put("message", ex.getMessage());
        }
        return object.toJSONString();
    }
}
