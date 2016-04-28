package com.masiis.shop.web.platform.controller.product;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderAddService;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
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
import java.util.Map;

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
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private BOrderAddService bOrderAddService;
    @RequestMapping(value = "/{skuId}", method = RequestMethod.GET)
    public ModelAndView getProductDetails(HttpServletRequest request, HttpServletResponse response, @PathVariable("skuId") String skuId) throws Exception {
        ModelAndView mav = new ModelAndView("/platform/product/product");
        HttpSession session = request.getSession();
        ComUser comUser = (ComUser) session.getAttribute("comUser");
        Product productDetails = productService.getSkuDetails(skuId);
        if (comUser != null && comUser.getIsAgent() == 1) {
            productDetails.setIsPartner(true);
        }
        productDetails.setMaxDiscount(productService.getMaxDiscount(Integer.parseInt(skuId)));
        PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), Integer.parseInt(skuId));
        mav.addObject("pfUserSku", pfUserSku);//是否代理过该商品
        mav.addObject("productDetails", productDetails);
        return mav;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ModelAndView getProductByUser(HttpServletRequest request, HttpServletResponse response, @PathVariable("userId") Long userId) throws Exception {
        ModelAndView mav;
        ComUser comUser = userService.getUserById(userId);
        if (comUser.getSendType() == 1) { //平台发货
            mav = new ModelAndView("/platform/user/userSkulist");
        } else {
            mav = new ModelAndView("/platform/user/selfSkuList");
        }
        List<Product> userProducts = productService.productListByUser(userId);
        if (userProducts != null) {
            for (Product product : userProducts) {
                product.setUpperStock(productService.getUpperStock(userId, product.getId()));
            }
        }
        mav.addObject("userProducts", userProducts);
        return mav;
    }

    /**
     * 自己发货，维护库存
     */
    @RequestMapping(value = "/selfUser/updateStock.do")
    @ResponseBody
    public String updateProductStock(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(required = true) Integer stock,
                                     @RequestParam(required = true) Integer id) {
        JSONObject object = new JSONObject();
        try {
            productService.updateStock(stock, id);
            object.put("isError", false);
            object.put("message", "√库存更改成功 ");
        } catch (Exception ex) {
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
                             @RequestParam(required = false) String message,
                             @RequestParam(required = true) Long userAddressId) {
        JSONObject object = new JSONObject();
        try {
            HttpSession session = request.getSession();
            ComUser comUser = (ComUser) session.getAttribute("comUser");
            PfUserSkuStock product = productService.getStockByUser(id);
            if (product.getStock() - stock < 0) {
                throw new BusinessException("拿货数量超出库存!");
            }
            Long orderCode = bOrderAddService.addProductTake(comUser.getId(), product.getSkuId(), stock, message, userAddressId);
            object.put("borderId", orderCode);
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
    public ModelAndView applySkuInfo(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "selectedAddressId", required = false) Long selectedAddressId,
                                     @RequestParam("id") Long id) throws Exception {
        ModelAndView mav = new ModelAndView("/platform/user/nahuo");
        ComUser comUser = getComUser(request);
        ComUserAddress comUserAddress = userAddressService.getOrderAddress(selectedAddressId, comUser.getId());
        if (comUserAddress != null) {
            mav.addObject("addressId", comUserAddress.getId());
            mav.addObject("comUserAddress", comUserAddress);
        }
        mav.addObject("pfUserSkuStockId", id);
        PfUserSkuStock product = productService.getStockByUser(id);
        ComSku comSku = skuService.getSkuById(product.getSkuId());
        ComSkuImage comSkuImage = skuService.findComSkuImage(comSku.getId());
        String productImgValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(product.getUserId(), product.getSkuId());
        Map<String, Object> objectMap = productService.getLowerCount(product.getSkuId(), product.getStock(), pfUserSku.getAgentLevelId());
        mav.addObject("productInfo", product);
        mav.addObject("lowerCount", objectMap.get("countLevel"));//下级人数
        mav.addObject("comSku", comSku);
        mav.addObject("comSkuImage", productImgValue + comSkuImage.getImgUrl());
        mav.addObject("levelStock", objectMap.get("levelStock"));
        mav.addObject("priceDiscount", objectMap.get("priceDiscount"));
        return mav;
    }

    /**
     * 补货信息
     * Jing Hao
     */
    @RequestMapping(value = "/user/addSkuInfo.list")
    @ResponseBody
    public ModelAndView addSkuInfo(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam("id") Long id) throws Exception {
        ModelAndView mav = new ModelAndView("/platform/user/buhuo");
        PfUserSkuStock product = productService.getStockByUser(id);
        ComSku comSku = skuService.getSkuById(product.getSkuId());
        Integer upperStock = productService.getUpperStock(product.getUserId(), product.getSkuId());
        mav.addObject("productInfo", product);
        mav.addObject("upperStock", upperStock);//上级库存
        mav.addObject("comSku", comSku);
        return mav;
    }

    /**
     * 补货验证
     * JJH
     */
    @RequestMapping("/checkStock.do")
    @ResponseBody
    public String checkStock(HttpServletRequest request,
                             HttpServletResponse response,
                                   @RequestParam(value = "skuId",required = true) Integer skuId,
                                   @RequestParam(value = "stock",required = true) Integer stock,
                                   @RequestParam(value = "pUserId",required = true) Long pUserId
                                   ) throws Exception {
        JSONObject object = new JSONObject();
         try{
             int status = skuService.getSkuStockStatus(skuId,stock,pUserId);
             object.put("isError", false);
             object.put("stockStatus", status);
         }catch (Exception ex){
             object.put("isError", true);
             object.put("message", ex.getMessage());
         }
        return object.toJSONString();
    }
}
