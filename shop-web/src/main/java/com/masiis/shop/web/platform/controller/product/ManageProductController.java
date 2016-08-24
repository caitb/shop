package com.masiis.shop.web.platform.controller.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.ManageShopProductService;
import com.masiis.shop.web.platform.service.product.ProductService;
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
    @Resource
    private ProductService productService;
    @Resource
    private SfShopService sfShopService;


    /**
     * jjh
     * 加载初始化小铺商品列表
     * @param request
     * @param currentPage
     * @param shopId
     * @param isSale
     * @param deliverType 0:平台发货，1: 自己发货 null :全部
     * @return
     * @throws Exception
     */
    @RequestMapping("/managePro.htmls")
    public ModelAndView manageShopProduct(HttpServletRequest request,
                                          @RequestParam(value = "shopId", required = true) Long shopId,
                                          @RequestParam(value = "isSale", required = true) Integer isSale,
                                          @RequestParam(value = "deliverType", required = false) Integer deliverType,
                                          @RequestParam(value = "currentPage", required = true) Integer currentPage
                                          ) throws Exception {
        ModelAndView mav = new ModelAndView("/platform/product/shop_product");
        ComUser comUser = getComUser(request);
        int pageSize = 20; //ajax请求时默认每页显示条数为20条
        List<SkuInfo> skuInfoList  = manageShopProductService.getShopProductsList(shopId, isSale, comUser.getId(), deliverType, currentPage+1, pageSize);
        SfShop sfShop = sfShopService.getSfShopById(shopId);
        mav.addObject("skuInfoList", skuInfoList);
        mav.addObject("comUser", comUser);
        mav.addObject("shopId", shopId);
        mav.addObject("sfShop", sfShop);
        return mav;
    }

    /**
     * 商品上下架
     * @param request
     * @param response
     * @param shopSkuId
     * @param isSale
     * @return
     */
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

    /**
     * 仓库中，出售中tab切换
     * @param request
     * @param response
     * @param shopId
     * @param isSale :上下架标志
     * @return
     */
    @RequestMapping("/deliverSale.do")
    @ResponseBody
    public String deliverProductSale(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value="shopId",required = true) Long shopId,
                                    @RequestParam(value="isSale",required = true) Integer isSale,
                                    @RequestParam(value="deliverType",required = false) Integer deliverType,
                                    @RequestParam(value = "currentPage", required = true) int currentPage
    ){
        JSONObject object = new JSONObject();
        try {
            ComUser comUser = getComUser(request);
            int pageSize = 20; //ajax请求时默认每页显示条数为20条
            deliverType= 0;
            List<SkuInfo> skuInfoList  = manageShopProductService.getShopProductsList(shopId, isSale, comUser.getId(), deliverType, currentPage, pageSize);
            object.put("isError", false);
            object.put("skuInfoList",skuInfoList);
        }catch (Exception ex){
            object.put("isError", true);
            log.info(ex.getMessage());
        }
        return object.toJSONString();
    }

    /**
     * 生成店主发货
     * @param shopSkuId
     * @return
     */
    @RequestMapping("/addSelfDelivery.do")
    @ResponseBody
    public String addSelfDelivery(
            @RequestParam(value = "shopSkuId", required = true) Long shopSkuId) {
        JSONObject object = new JSONObject();
        try {
            manageShopProductService.addSelfDeliverySku(shopSkuId);
            object.put("isError",false);
        } catch (Exception ex) {
            object.put("isError",true);
        }
        return object.toJSONString();
    }

    /**
     * JJH
     * c 端店主自己发货更新库存
     * @param stock
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/updateStockBySelf.do")
    @ResponseBody
    public String updateProductStock(
            HttpServletRequest request,
            @RequestParam(required = true) Integer stock,
            @RequestParam(required = true) Integer skuId) {
        JSONObject object = new JSONObject();
        try {
            ComUser comUser = getComUser(request);
            productService.updateStock(stock, skuId,comUser.getId());
            object.put("isError", false);
        } catch (Exception ex) {
            object.put("isError", true);
            log.info(ex.getMessage());
        }
        return object.toJSONString();
    }

}
