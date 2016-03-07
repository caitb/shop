package com.masiis.shop.web.platform.controller.order;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserTrial;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.COrderService;
import com.masiis.shop.web.platform.service.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Controller
@RequestMapping("/corder")
public class COrderController extends BaseController {

    @Resource
    private COrderService cOrderService;
    @Resource
    private ProductService productService;

    /**
     *
     *@return
     */
    @RequestMapping("/index")
    public String toIndex(HttpServletRequest request, HttpServletResponse response){
        request.getSession().setAttribute("userId","1");
        return "index";
    }

    /**
     * 跳转到试用申请界面
     * @author  hanzengzhi
     * @date  2016/3/5 13:45
     */
    @RequestMapping("/applyTrialToPage.json")
    public String applyTrialToPage(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(value = "skuId", required = true) Integer skuId,
                                   Model model)throws Exception{
        if (StringUtils.isEmpty(skuId)){
            skuId = 1;
        }
        Product productDetails = productService.applyTrialToPageService(skuId);
        String skuImg = PropertiesUtils.getStringValue("index_product_100_100_url");
        model.addAttribute("skuName", productDetails.getName());
        if (productDetails.getComSkuImages()!=null&&productDetails.getComSkuImages().size()>0){
            model.addAttribute("skuDefaultImg",skuImg + productDetails.getComSkuImages().get(0).getImgUrl());
            model.addAttribute("skuImgAlt", productDetails.getComSkuImages().get(0).getImgName());
        }
        model.addAttribute("product",productDetails);
        return "platform/order/shiyong";
    }
    /**
     * 试用申请
     * @author  hanzengzhi
     * @date  2016/3/5 13:46
     */
    @RequestMapping("/trialApply.json")
    @ResponseBody
    public String trialApply(
            HttpServletRequest request,
            @RequestParam(value = "skuId", required = true) Long skuId,
            @RequestParam(value = "spuId", required = true) Long spuId,
            @RequestParam(value = "applyReason", required = false) String applyReason,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "phone", required = true) String phone,
            @RequestParam(value = "wechat", required = true) String wechat
    ) {
        if (StringUtils.isEmpty(skuId)){
            skuId = 111L;
        }
        if (StringUtils.isEmpty(spuId)){
            spuId = 222L;
        }
        if (StringUtils.isEmpty(name)){

        }
        if (StringUtils.isEmpty(phone)){

        }
        if (StringUtils.isEmpty(wechat)){

        }
        ComUser comUser = (ComUser)request.getSession().getAttribute("comUser");
        if (comUser==null){
            comUser = new ComUser();
            comUser.setId(1L);
        }
        comUser.setWxId(wechat);
        comUser.setRealName(name);
        comUser.setMobile(phone);

        PfUserTrial pfUserTrial = new PfUserTrial();
        pfUserTrial.setUserId(comUser.getId());
        pfUserTrial.setSkuId(skuId);
        pfUserTrial.setSpuId(spuId);
        pfUserTrial.setStatus(0);
        pfUserTrial.setReason(applyReason);
        pfUserTrial.setName(name);
        pfUserTrial.setMobile(phone);
        pfUserTrial.setWeixinId(wechat);
        pfUserTrial.setCreateTime(new Date());
        cOrderService.trialApplyService(comUser,pfUserTrial);
        return "";
    }

}
