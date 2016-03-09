package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.ProductSimple;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
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

    private Logger log = Logger.getLogger(this.getClass());

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
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private ComSkuImageMapper comSkuImageMapper;

    /**
     * 合伙人申请
     *
     * @author ZhaoLiang
     * @date 2016/3/5 13:51
     */
    @RequestMapping("/apply.shtml")
    public ModelAndView partnersApply(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "skuId", required = false) Integer skuId) {
        ModelAndView mv = new ModelAndView();
        try {
            skuId = 22;
            String skuImg = PropertiesUtils.getStringValue("index_product_100_100_url");
            ProductSimple productSimple = productService.getSkuSimple(skuId);
            mv.addObject("skuId", skuId);
            mv.addObject("skuName", productSimple.getSkuName());
            mv.addObject("skuImg", skuImg + productSimple.getSkuDefaultImgURL());
            mv.addObject("slogan", productSimple.getSlogan());
            mv.setViewName("platform/order/shenqing");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
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
                                         @RequestParam(value = "parentUserId", required = false) Long parentUserId) {
        ModelAndView mv = new ModelAndView();
        try {
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
                sb.append("<b>&nbsp;&nbsp;商品数量：</b> <span name=\"quantity\">" + pfSkuAgent.getQuantity() + "</span>");
                sb.append("<b>&nbsp;&nbsp;金额：</b> <span name=\"amount\">" + comSku.getPriceRetail().multiply(BigDecimal.valueOf(pfSkuAgent.getQuantity())) + "</span>");
                sb.append("<p>");
            }
            mv.addObject("skuId", comSku.getId());
            mv.addObject("skuName", comSku.getName());
            mv.addObject("agentInfo", sb.toString());
            mv.setViewName("platform/order/zhuce");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
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
//        if (comUser == null) {
//            throw new BusinessException("");
//        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("platform/order/zhuce2");
        modelAndView.addObject("name", name);
        modelAndView.addObject("mobile", mobile);
        modelAndView.addObject("weixinId", weixinId);
        modelAndView.addObject("parentUserId", 0);
        modelAndView.addObject("parentMobile", parentMobile);
        modelAndView.addObject("skuId", skuId);
        modelAndView.addObject("skuName", skuName);
        modelAndView.addObject("levelId", levelId);
        modelAndView.addObject("levelName", levelName);
        modelAndView.addObject("amount", amount);
        modelAndView.addObject("pName", "赵先生");
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
                                            @RequestParam(value = "parentUserId", required = true) Long parentUserId) {
        JSONObject obj = new JSONObject();
        try {
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
            order.setUserMassage("");
            order.setUserId(comUser.getId());
            order.setUserPid(0l);
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
            if (pfUserSku == null) {
                userSku.setPid(0);
            } else {
                userSku.setPid(pfUserSku.getId());
            }
            userSku.setCode("");
            userSku.setUserId(comUser.getId());
            userSku.setSkuId(comSku.getId());
            userSku.setAgentLevelId(levelId);
            userSku.setIsPay(0);
            userSku.setIsCertificate(0);
            Long bOrderId = bOrderService.AddBOrder(order, orderItems, userSku, comUser);
            obj.put("isError", false);
            obj.put("bOrderId", bOrderId);
            return obj.toJSONString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            obj.put("isError", true);
            obj.put("message", "添加订单失败");
            return obj.toJSONString();
        }
    }

    /**
     * 合伙人支付
     *
     * @author ZhaoLiang
     * @date 2016/3/5 16:32
     */
    @RequestMapping("/pay.shtml")
    public ModelAndView paretnersPay(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "userAddressId", required = false) Integer userAddressId,
                                     @RequestParam(value = "userMessage", required = false) String userMessage,
                                     @RequestParam(value = "bOrderId", required = false) Long bOrderId
    ) {
        ModelAndView mv = new ModelAndView();
        String skuImg = PropertiesUtils.getStringValue("index_product_100_100_url");
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(bOrderId);
        StringBuffer stringBuffer = new StringBuffer();
        int sumQuantity = 0;
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(pfBorderItem.getSkuId());
            stringBuffer.append("<section class=\"sec2\" >");
            stringBuffer.append("<p class=\"photo\" >");
            stringBuffer.append("<img src = '" + skuImg + comSkuImage.getImgUrl() + "' alt = \"\" >");
            stringBuffer.append("</p>");
            stringBuffer.append("<div>");
            stringBuffer.append("<h2> " + pfBorderItem.getSkuName() + "'</h2>");
            stringBuffer.append("<h3 ></h3>");
            stringBuffer.append("<p ><span> ￥" + pfBorderItem.getUnitPrice() + " </span ><b style = \"float:right; margin-right:10px;font-size:12px;\" > x" + pfBorderItem.getQuantity() + " </b ></p >");
            stringBuffer.append("</div>");
            stringBuffer.append("</section>");
            sumQuantity += pfBorderItem.getQuantity();
        }
        mv.addObject("receivableAmount", pfBorder.getReceivableAmount());
        mv.addObject("orderAmount", pfBorder.getOrderAmount());
        mv.addObject("productInfo", stringBuffer.toString());
        mv.addObject("quantity", sumQuantity);
        mv.setViewName("platform/order/zhifu");
        return mv;
    }
}
