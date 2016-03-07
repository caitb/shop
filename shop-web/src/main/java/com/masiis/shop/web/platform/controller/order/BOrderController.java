package com.masiis.shop.web.platform.controller.order;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.ProductSimple;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuImage;
import com.masiis.shop.dao.po.ComSpu;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @autor jipengkun
 */
@Controller
@RequestMapping("/border")
public class BOrderController extends BaseController {


    @Resource
    private ProductService productService;

    /**
     * 合伙人申请
     *
     * @author ZhaoLiang
     * @date 2016/3/5 13:51
     */
    @RequestMapping("/apply")
    public ModelAndView partnersApply(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "skuId", required = false) Integer skuId) throws Exception {
        String skuImg = PropertiesUtils.getStringValue("index_product_100_100_url");
        ModelAndView mv = new ModelAndView();
        skuId = 1;
        ProductSimple productSimple = productService.getSkuSimple(skuId);
        mv.addObject("skuName", productSimple.getSkuName());
        mv.addObject("skuImg", skuImg + productSimple.getSkuDefaultImgURL());
        mv.addObject("slogan", productSimple.getSlogan());
        mv.setViewName("platform/order/shenqing");
        return mv;
    }

    /**
     * 合伙人注册一
     *
     * @author ZhaoLiang
     * @date 2016/3/5 14:27
     */
    @RequestMapping("/register")
    public ModelAndView partnersRegister(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestParam(value = "skuId", required = true) Integer skuId,
                                         @RequestParam(value = "parentUserId", required = false) Long parentUserId) throws Exception {
        ModelAndView mv = new ModelAndView();
        skuId = 1;
        ProductSimple productSimple = productService.getSkuSimple(skuId);
        if (parentUserId == null) {

        }
        mv.addObject("skuName", productSimple.getSkuName());
        mv.setViewName("platform/order/zhuce");
        return mv;
    }

    /**
     * 合伙人注册确认
     *
     * @author ZhaoLiang
     * @date 2016/3/5 14:27
     */
    @RequestMapping("/registerConfirm")
    public ModelAndView partnersRegisterConfirm(HttpServletRequest request,
                                                HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("platform/order/zhuce2");
        return mv;
    }

    /**
     * 合伙人支付
     *
     * @author ZhaoLiang
     * @date 2016/3/5 16:32
     */
    @RequestMapping("/pay")
    public ModelAndView paretnersPay(HttpServletRequest request,
                                     HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("platform/order/zhifu");
        return mv;
    }
}
