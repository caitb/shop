package com.masiis.shop.web.platform.controller.order;

import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.web.platform.controller.base.BaseController;
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

    /**
     *
     */
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSkuImageMapper comSkuImageMapper;

    /**
     * 合伙人申请
     *
     * @return
     */
    @RequestMapping("/apply")
    public ModelAndView partnersApply(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "skuId", required = false) Integer skuId) {
//        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        ModelAndView mv = new ModelAndView();
        skuId = 1;
        ComSku comSku = comSkuMapper.selectByPrimaryKey(skuId);
//        ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(skuId);
        mv.addObject("skuName", comSku.getName());
//        mv.addObject("skuImg", comSkuImage.getImgUrl());
        mv.setViewName("platform/order/shenqing");
        return mv;
    }

    @RequestMapping("/register")
    public String partnersRegister(HttpServletRequest request,
                                   HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        return "";
    }
}
