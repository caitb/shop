package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.ProductSimple;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
import java.util.Date;
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
    @Resource
    private BOrderService bOrderService;
    @Resource
    private UserSkuService userSkuService;

    /**
     * 合伙人申请
     *
     * @author ZhaoLiang
     * @date 2016/3/5 13:51
     */
    @RequestMapping("/apply.shtml")
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
    @RequestMapping("/register.shtml")
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
            sb.append("<label levelId='" + pfSkuAgent.getAgentLevelId() + "'>");
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
        mv.addObject("skuId", comSku.getId());
        mv.addObject("skuName", comSku.getName());
        mv.addObject("agentInfo", sb.toString());
        mv.setViewName("platform/order/zhuce");
        return mv;
    }

    /**
     * 合伙人注册数据验证
     *
     * @author ZhaoLiang
     * @date 2016/3/5 14:27
     */
    @ResponseBody
    @RequestMapping("/registerConfirm/check.do")
    public String partnersRegisterConfirmCheck(HttpServletRequest request,
                                               HttpServletResponse response,
                                               @RequestParam(value = "name", required = true) String name,
                                               @RequestParam(value = "mobile", required = true) String mobile,
                                               @RequestParam(value = "weixinId", required = true) String weixinId,
                                               @RequestParam(value = "parentMobile", required = true) String parentMobile,
                                               @RequestParam(value = "skuName", required = true) String skuName,
                                               @RequestParam(value = "levelId", required = true) Long levelId,
                                               @RequestParam(value = "levelName", required = true) String levelName,
                                               @RequestParam(value = "amount", required = true) BigDecimal amount) {
        JSONObject object = new JSONObject();
        object.put("isError", false);
        //ComUser comUser = userService.getUserByMobile(mobile);
//        if (StringUtils.isNotBlank(mobile)) {
//            if (comUser != null) {
//                MemberProduct memberProduct = memberProductService.findByProIdAndMemId(pro.getId(), tjMember.getId());
//                if (memberProduct != null) {
//                    if (agenLevel.getLevel() == 1 && memberProduct.getLevel() != 0 && memberProduct.getLevel() != 1) {
//                        object.put("code", "0");
//                        object.put("msg", "您选择的是AAA合伙人，请填写正确的推荐人手机号，若不知道推荐人电话，请联系4009669889");
//                        return object.toString();
//                    }
//                    if (agenLevel.getLevel() < memberProduct.getLevel()) {
//                        object.put("code", "0");
//                        object.put("msg", "您的代理等级只能小于或等于您的推荐人代理等级");
//                        return object.toString();
//                    }
//                } else {
//                    object.put("code", "0");
//                    object.put("msg", "您的推荐人商品信息有误");
//                    return object.toString();
//                }
//                mp.setParentMemberId(tjMember.getId());
//            } else {
//                object.put("code", "0");
//                object.put("msg", "您的推荐人还未注册，请联系您的推荐人先注册");
//            }
//        } else {
//            object.put("code", "0");
//            object.put("msg", "请您填写推荐人电话");
//        }

//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("platform/order/zhuce2");
//        modelAndView.addObject("name", name);
//        modelAndView.addObject("mobile", mobile);
//        modelAndView.addObject("weixinId", weixinId);
//        modelAndView.addObject("parentMobile", parentMobile);
//        modelAndView.addObject("skuName", skuName);
//        modelAndView.addObject("levelId", levelId);
//        modelAndView.addObject("levelName", levelName);
//        modelAndView.addObject("amount", amount);
//        if(comUser!=null){
//            modelAndView.addObject("pName", comUser.getRealName());
//        }
        return object.toJSONString();
    }

    /**
     * 合伙人注册确认
     *
     * @author ZhaoLiang
     * @date 2016/3/5 14:27
     */
    @RequestMapping("/registerConfirm.shtml")
    public ModelAndView partnersRegisterConfirm(HttpServletRequest request,
                                                HttpServletResponse response,
                                                @RequestParam(value = "name", required = true) String name,
                                                @RequestParam(value = "mobile", required = true) String mobile,
                                                @RequestParam(value = "weixinId", required = true) String weixinId,
                                                @RequestParam(value = "parentMobile", required = true) String parentMobile,
                                                @RequestParam(value = "skuId", required = true) Long skuId,
                                                @RequestParam(value = "skuName", required = true) String skuName,
                                                @RequestParam(value = "levelId", required = true) Long levelId,
                                                @RequestParam(value = "levelName", required = true) String levelName,
                                                @RequestParam(value = "amount", required = true) BigDecimal amount) {
        ComUser comUser = userService.getUserByMobile(parentMobile);
        if (comUser == null) {
            throw new BusinessException("");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("platform/order/zhuce2");
        modelAndView.addObject("name", name);
        modelAndView.addObject("mobile", mobile);
        modelAndView.addObject("weixinId", weixinId);
        modelAndView.addObject("parentMobile", parentMobile);
        modelAndView.addObject("skuId", skuId);
        modelAndView.addObject("skuName", skuName);
        modelAndView.addObject("levelId", levelId);
        modelAndView.addObject("levelName", levelName);
        modelAndView.addObject("amount", amount);
        modelAndView.addObject("pName", comUser.getRealName());
        return modelAndView;
    }

    /**
     * 用户确认生成订单
     *
     * @author ZhaoLiang
     * @date 2016/3/8 12:50
     */
    @ResponseBody
    @RequestMapping("/registerConfirm/save.do")
    public String partnersRegisterConfirmDo(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestParam(value = "realName", required = true) String realName,
                                            @RequestParam(value = "mobile", required = true) String mobile,
                                            @RequestParam(value = "weixinId", required = true) String weixinId,
                                            @RequestParam(value = "skuId", required = true) Integer skuId,
                                            @RequestParam(value = "levelId", required = true) Integer levelId,
                                            @RequestParam(value = "parentUserId", required = true) Long parentUserId,
                                            @RequestParam(value = "userMassage", required = true) String userMassage) throws Exception {
        //处理用户数据
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        comUser.setRealName(realName);
        comUser.setMobile(mobile);
        comUser.setWxId(weixinId);
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, levelId);
        ComSku comSku = skuService.getSkuById(skuId);
        //折扣后单价
        BigDecimal unitPrice = comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount());
        //折扣后总价
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(pfSkuAgent.getQuantity()));
        //处理订单数据
        PfBorder order = new PfBorder();
        order.setCreateTime(new Date());
        order.setCreateMan(comUser.getId());
        order.setOrderCode("");
        order.setUserId(comUser.getId());
        order.setUserPid(parentUserId);
        order.setUserMassage(userMassage);
        order.setSupplierId(0);
        order.setReceivableAmount(totalPrice);
        order.setOrderAmount(totalPrice);//运费到付，商品总价即订单总金额
        order.setProductAmount(totalPrice);
        order.setShipAmount(new BigDecimal(0));
        order.setPayAmount(new BigDecimal(0));
        order.setOrderStatus(0);
        order.setShipStatus(0);
        order.setPayStatus(0);
        order.setIsShip(0);
        order.setIsReplace(0);
        order.setIsReceipt(0);
        //处理订单商品数据
        List<PfBorderItem> orderItems = new ArrayList<>();
        PfBorderItem pfBorderItem = new PfBorderItem();
        pfBorderItem.setCreateTime(new Date());
        pfBorderItem.setSpuId(comSku.getSpuId());
        pfBorderItem.setSkuId(comSku.getId());
        pfBorderItem.setSkuName(comSku.getName());
        pfBorderItem.setQuantity(pfSkuAgent.getQuantity());
        pfBorderItem.setOriginalPrice(comSku.getPriceRetail());
        pfBorderItem.setUnitPrice(unitPrice);
        pfBorderItem.setTotalPrice(totalPrice);
        pfBorderItem.setIsComment(0);
        pfBorderItem.setIsReturn(0);
        orderItems.add(pfBorderItem);
        //处理用户sku关系数据
        PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(parentUserId, comSku.getId());//获取上级代理ID
        PfUserSku userSku = new PfUserSku();
        userSku.setCreateTime(new Date());
        userSku.setPid(pfUserSku.getId());
        userSku.setUserId(comUser.getId());
        userSku.setSkuId(comSku.getId());
        userSku.setAgentLevelId(levelId);
        userSku.setIsPay(0);
        userSku.setIsCertificate(0);
        bOrderService.AddBOrder(order, orderItems, userSku, comUser);
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
    @RequestMapping("/pay.shtml")
    public ModelAndView paretnersPay(HttpServletRequest request,
                                     HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("platform/order/zhifu");
        return mv;
    }
}
