package com.masiis.shop.web.platform.controller.product;

import com.masiis.shop.dao.po.PbUser;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @autor jipengkun
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Resource
    private ProductService productService;

    @RequestMapping(value = "/{skuId}", method = RequestMethod.GET)
    public Product getProductDetails(HttpServletRequest request, HttpServletResponse response, @PathVariable("skuId") String skuId) throws Exception {
        HttpSession session = request.getSession();
        PbUser pbUser = (PbUser) session.getAttribute("pbUser");//权限需要验证添加
        Product productDetails = productService.getSkuDetails(skuId);
        return productDetails;
    }

}
