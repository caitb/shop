package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.ProductSimple;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * @autor jipengkun
 */
@Controller
@RequestMapping("/border")
public class BOrderController extends BaseController {

    @Resource
    private ProductService productService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserService userService;

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
        mv.addObject("skuId", skuId);
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
        //获取商品信息
        ComSku comSku = skuService.getSkuById(skuId);
        //获取商品代理信息
        List<PfSkuAgent> pfSkuAgents = null;
        if (parentUserId == null) {
            pfSkuAgents = skuAgentService.getAllBySkuId(skuId);
        } else {
            //do
            pfSkuAgents = skuAgentService.getAllBySkuId(skuId);
        }
        //获取代理信息
        List<ComAgentLevel> comAgentLevels = skuAgentService.getComAgentLevel();
        StringBuffer sb = new StringBuffer();
        for (PfSkuAgent pfSkuAgent : pfSkuAgents) {
            sb.append("<p>");
            sb.append("<label>");
            for (ComAgentLevel comAgentLevel : comAgentLevels) {
                if (pfSkuAgent.getAgentLevelId() == comAgentLevel.getId()) {
                    sb.append(comAgentLevel.getName());
                }
            }
            sb.append("</label>");
            sb.append("<b>商品数量：</b> <span name=\"quantity\">" + pfSkuAgent.getQuantity() + "</span>");
            sb.append("<b>金额：</b> <span name=\"amount\">" + comSku.getPriceRetail().multiply(BigDecimal.valueOf(pfSkuAgent.getQuantity())) + "</span>");
            sb.append("<p>");
        }
        mv.addObject("skuName", comSku.getName());
        mv.addObject("agentInfo", sb.toString());
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
                                                HttpServletResponse response,
                                                @RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "mobile", required = false) String mobile,
                                                @RequestParam(value = "weixinId", required = false) String weixinId,
                                                @RequestParam(value = "parentMobile", required = false) String parentMobile,
                                                @RequestParam(value = "skuName", required = false) String skuName,
                                                @RequestParam(value = "levelName", required = false) String levelName,
                                                @RequestParam(value = "amount", required = false) BigDecimal amount) {
        ComUser comUser = userService.getUserByMobile(mobile);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("platform/order/zhuce2");
        modelAndView.addObject("name", name);
        modelAndView.addObject("mobile", mobile);
        modelAndView.addObject("weixinId", weixinId);
        modelAndView.addObject("parentMobile", parentMobile);
        modelAndView.addObject("skuName", skuName);
        modelAndView.addObject("levelName", levelName);
        modelAndView.addObject("amount", amount);
        modelAndView.addObject("pName",comUser.getRealName());
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/registerConfirm/save")
    public String partnersRegisterConfirmDo(HttpServletRequest request,
                                            HttpServletResponse response) {
        JSONObject obj = new JSONObject();
        obj.put("isError", false);
        obj.put("url", "/border/pay");
        obj.put("message", "");
        return obj.toJSONString();
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
