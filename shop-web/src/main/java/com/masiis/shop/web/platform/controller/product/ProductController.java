package com.masiis.shop.web.platform.controller.product;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.dao.po.PfUserSkuStock;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @autor JingHao
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Resource
    private ProductService productService;

    @Resource
    private UserSkuService userSkuService;

    @Resource
    private BOrderService bOrderService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/{skuId}", method = RequestMethod.GET)
    public ModelAndView getProductDetails(HttpServletRequest request, HttpServletResponse response, @PathVariable("skuId") String skuId) throws Exception {
        ModelAndView mav = new ModelAndView("/platform/product/product");
        HttpSession session = request.getSession();
        ComUser comUser = (ComUser) session.getAttribute("comUser");
        Product productDetails = productService.getSkuDetails(skuId);
        if(comUser !=null && comUser.getIsAgent()==1){
            productDetails.setIsPartner(true);
            productDetails.setMaxDiscount(productService.getMaxDiscount());
        }
        PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(),Integer.parseInt(skuId));
        mav.addObject("pfUserSku",pfUserSku);//是否代理过该商品
        mav.addObject("productDetails",productDetails);
        return mav;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ModelAndView getProductByUser(HttpServletRequest request, HttpServletResponse response, @PathVariable("userId") Long userId) throws Exception{
        ModelAndView mav;
        ComUser comUser = userService.getUserById(userId);
        if(comUser.getSendType()==0){ //平台发货
            mav = new ModelAndView("/platform/user/userSkulist");
        }else{
            mav = new ModelAndView("/platform/user/selfSkuList");
        }
        List<Product> userProducts = productService.productListByUser(userId);
        mav.addObject("userProducts",userProducts);
        return mav;
    }

    /**
     * 自己拿货，更新库存
     */
    @RequestMapping(value = "/selfUser/updateStock.do")
    @ResponseBody
    public String updateProductStock(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(required = true) Integer stock,
                                     @RequestParam(required = true) Integer id) {
        JSONObject object = new JSONObject();
        try {
            ADD:
            productService.updateStock(stock, id);
            object.put("isError", false);
        } catch (Exception ex) {
            object.put("isError", true);
            object.put("message", ex.getMessage());
        }
        return object.toJSONString();
    }
    /**
     * @Author Jing Hao
     * @Date 2016/3/22 0022 下午 4:02
     * 补货（平台 自己）
     */
    @RequestMapping(value = "/user/addStock.do")
    @ResponseBody
    public String addProductStock(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(required = true) Integer stock,
                                  @RequestParam(required = true) Integer skuId){
        JSONObject object = new JSONObject();
        try {
            HttpSession session = request.getSession();
            ComUser comUser = (ComUser) session.getAttribute("comUser");
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(),skuId);//代理关系
            int usableStock = skuService.checkSkuStock(skuId,stock,pfUserSku.getPid().longValue());
            if(usableStock<0){
                throw new BusinessException("可用库存不足!");
            }
            Long orderCode = bOrderService.addReplenishmentOrders(comUser.getId(),skuId,stock);
            object.put("isError", false);
            object.put("orderCode", orderCode);
            if (orderCode==null) {
                throw new BusinessException("订单号生成失败，补货失败!");
            }
        }catch (Exception ex){
            object.put("isError", true);
            object.put("message", ex.getMessage());
        }
        return object.toJSONString();
    }

    /**
     * 平台发货，申请拿货
     */
    @RequestMapping(value = "/user/applyStock.do")
    @ResponseBody
    public String applyStock(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(required = true) Integer stock,
                             @RequestParam(required = true) Long id,
                             @RequestParam(required = false) String message) {
        JSONObject object = new JSONObject();
        try {
            HttpSession session = request.getSession();
            ComUser comUser = (ComUser) session.getAttribute("comUser");
            PfUserSkuStock product = productService.getStockByUser(id);
            if (product.getStock() - stock < 0) {
                throw new BusinessException("拿货数量超出平台库存!");
            }
            bOrderService.addProductTake(comUser.getId(), product.getSkuId(), stock, message);
            object.put("isError", false);
        } catch (Exception ex) {
            object.put("isError", true);
            object.put("message", ex.getMessage());
        }
        return object.toJSONString();
    }
    /**
     * 拿货信息
     * Jing Hao
     */
    @RequestMapping(value = "/user/applySkuInfo.list")
    @ResponseBody
    public ModelAndView applySkuInfo(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("id") Long id) throws Exception{
        ModelAndView mav = new ModelAndView("/platform/user/nahuo");
        PfUserSkuStock product = productService.getStockByUser(id);
        mav.addObject("productInfo",product);
        return mav;
    }
    /**
     * 补货信息
     * Jing Hao
     */
    @RequestMapping(value = "/user/addSkuInfo.list")
    @ResponseBody
    public ModelAndView addSkuInfo(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("id") Long id) throws Exception{
        ModelAndView mav = new ModelAndView("/platform/user/buhuo");
        PfUserSkuStock product = productService.getStockByUser(id);
        Integer upperStock = productService.getUpperStock(product.getUserId(), product.getSkuId());
        mav.addObject("productInfo",product);
        mav.addObject("upperStock",upperStock);//上级库存
        return mav;
    }
}
