package com.masiis.shop.web.platform.controller.product;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/{skuId}", method = RequestMethod.GET)
    public ModelAndView getProductDetails(HttpServletRequest request, HttpServletResponse response, @PathVariable("skuId") String skuId) throws Exception {
        ModelAndView mav = new ModelAndView("/platform/product/product");
        HttpSession session = request.getSession();
        ComUser comUser = (ComUser) session.getAttribute("comUser");
        Product productDetails = productService.getSkuDetails(skuId);
        if(comUser !=null && comUser.getIsAgent()==1){
            productDetails.setIsPartner(true);
            productDetails.setDiscountLevel(productService.getDiscountByAgentLevel(productDetails.getPriceRetail()));
        }
        mav.addObject("productDetails",productDetails);
        return mav;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ModelAndView getProductByUser(HttpServletRequest request, HttpServletResponse response, @PathVariable("userId") Integer userId) throws Exception{
        ModelAndView mav = new ModelAndView("/platform/user/userSkulist");
        List<Product> userProducts = productService.productListByUser(userId);
        mav.addObject("userProducts",userProducts);
        return mav;
    }

}
