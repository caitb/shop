package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.PayBOrderService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import com.masiis.shop.web.platform.utils.WXBeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @autor jipengkun
 */
@Controller
@RequestMapping("/border")
public class BOrderController extends BaseController {
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
    private PayBOrderService payBOrderService;

    /**
     * 用户确认生成订单(合伙订单)
     *
     * @author ZhaoLiang
     * @date 2016/3/8 12:50
     */
    @ResponseBody
    @RequestMapping("/add.do")
    public String addBOrder(HttpServletRequest request,
                            @RequestParam(value = "weixinId", required = true) String weixinId,
                            @RequestParam(value = "skuId", required = true) Integer skuId,
                            @RequestParam(value = "levelId", required = true) Integer levelId,
                            @RequestParam(value = "pUserId", required = true) Long pUserId) {
        JSONObject obj = new JSONObject();
        try {
            if (StringUtils.isBlank(weixinId)) {
                throw new BusinessException("微信号不能为空");
            }
            if (levelId <= 0) {
                throw new BusinessException("代理等级有误");
            }
            if (pUserId != null && pUserId > 0) {
                userSkuService.checkParentData(pUserId, skuId, levelId);
            }
            ComUser comUser = getComUser(request);
            PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, levelId);
            ComSku comSku = skuService.getSkuById(skuId);
            //折扣后单价
            BigDecimal unitPrice = comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount());
            //保证金
            BigDecimal bailPrice = pfSkuAgent.getBail();
            //折扣后总价
            BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(pfSkuAgent.getQuantity()));
            //处理订单数据
            PfBorder order = new PfBorder();
            order.setCreateTime(new Date());
            order.setCreateMan(comUser.getId());
            String orderCode = OrderMakeUtils.makeOrder("B");
            order.setOrderCode(orderCode);
            order.setUserMessage("");
            order.setUserId(comUser.getId());
            if (pUserId != null && pUserId > 0) {
                order.setUserPid(pUserId);
            } else {
                order.setUserPid(0l);
            }
            order.setSupplierId(0);
            order.setReceivableAmount(totalPrice.add(bailPrice));
            order.setOrderAmount(totalPrice.add(bailPrice));
            order.setBailAmount(bailPrice);
            order.setProductAmount(totalPrice);
            order.setShipAmount(BigDecimal.ZERO);
            order.setPayAmount(BigDecimal.ZERO);
            order.setShipManId(0);
            order.setShipManName("");
            order.setShipType(0);
            order.setShipRemark("");
            //确定订单的拿货方式
            if (comUser.getSendType() == 0) {
                ComUser pUser = userService.getUserById(pUserId);
                if (pUser == null) {
                    order.setSendType(comUser.getSendType());
                } else {
                    order.setSendType(pUser.getSendType());
                }
            } else {
                order.setSendType(comUser.getSendType());
            }
            order.setOrderType(0);
            order.setOrderStatus(0);
            order.setShipStatus(0);
            order.setPayStatus(0);
            order.setIsCounting(0);
            order.setIsShip(0);
            order.setIsReplace(0);
            order.setIsReceipt(0);
            order.setReplaceOrderId(0l);
            order.setRemark("代理订单");
            //处理订单商品数据
            List<PfBorderItem> orderItems = new ArrayList<>();
            PfBorderItem pfBorderItem = new PfBorderItem();
            pfBorderItem.setCreateTime(new Date());
            pfBorderItem.setSpuId(comSku.getSpuId());
            pfBorderItem.setSkuId(comSku.getId());
            pfBorderItem.setSkuName(comSku.getName());
            pfBorderItem.setAgentLevelId(levelId);
            pfBorderItem.setWxId(weixinId);
            pfBorderItem.setQuantity(pfSkuAgent.getQuantity());
            pfBorderItem.setOriginalPrice(comSku.getPriceRetail());
            pfBorderItem.setDiscount(pfSkuAgent.getDiscount());
            pfBorderItem.setUnitPrice(unitPrice);
            pfBorderItem.setTotalPrice(totalPrice);
            pfBorderItem.setBailAmount(bailPrice);
            pfBorderItem.setIsComment(0);
            pfBorderItem.setIsReturn(0);
            orderItems.add(pfBorderItem);
            if (userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), comSku.getId()) != null) {
                throw new BusinessException("此商品已经建立过代理，请通过补货增加库存。");
            }
            Long bOrderId = bOrderService.AddBOrder(order, orderItems);
            obj.put("isError", false);
            obj.put("bOrderId", bOrderId);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return obj.toJSONString();
    }

    /**
     * 合伙人支付页面
     *
     * @author ZhaoLiang
     * @date 2016/3/5 16:32
     */
    @RequestMapping("/payBOrder.shtml")
    public ModelAndView payBOrder(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam(value = "userAddressId", required = false) Long userAddressId,
                                  @RequestParam(value = "userMessage", required = false) String userMessage,
                                  @RequestParam(value = "bOrderId", required = true) Long bOrderId) throws Exception {
        ModelAndView mv = new ModelAndView();
        ComUser comUser = getComUser(request);
        String skuImg = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(bOrderId);
        StringBuffer stringBuffer = new StringBuffer();
        int sumQuantity = 0;
        BigDecimal lowProfit = BigDecimal.ZERO;//最低利润
        BigDecimal highProfit = BigDecimal.ZERO;//最高利润
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            ComSkuImage comSkuImage = skuService.findComSkuImage(pfBorderItem.getSkuId());
            //获取用户代理等级
            ComAgentLevel comAgentLevel = bOrderService.findComAgentLevel(pfBorderItem.getAgentLevelId());
            stringBuffer.append("<section class=\"sec2\" >");
            stringBuffer.append("<p class=\"photo\" >");
            stringBuffer.append("<img src = '" + skuImg + comSkuImage.getImgUrl() + "' alt = \"\" >");
            stringBuffer.append("</p>");
            stringBuffer.append("<div>");
            stringBuffer.append("<h2> " + pfBorderItem.getSkuName() + "'<b style=\"float:right; margin-right:10px;font-size:12px;\">x" + pfBorderItem.getQuantity() + "</b></h2>");
            stringBuffer.append("<h3>合伙人等级：<span>" + comAgentLevel.getName() + "</span></h3>");
            stringBuffer.append("<p>商品总价:<span>" + pfBorderItem.getTotalPrice() + "</span>保证金:<span>" + pfBorderItem.getBailAmount() + "</span></p>");
            stringBuffer.append("</div>");
            stringBuffer.append("</section>");
            sumQuantity += pfBorderItem.getQuantity();
            if (pfBorderItem.getAgentLevelId() == SysConstants.MAX_AGENT_LEVEL) {
                lowProfit = lowProfit.add(pfBorderItem.getTotalPrice());
            } else {
                PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(pfBorderItem.getSkuId(), pfBorderItem.getAgentLevelId() + 1);
                BigDecimal lowerAmount = pfSkuAgent.getDiscount().multiply(BigDecimal.valueOf(pfBorderItem.getQuantity())).multiply(pfBorderItem.getOriginalPrice());//下级拿货价
                lowProfit = lowProfit.add(lowerAmount.subtract(pfBorderItem.getTotalPrice()));
            }
            BigDecimal higherAmount = pfBorderItem.getOriginalPrice().multiply(BigDecimal.valueOf(pfBorderItem.getQuantity()));
            highProfit = highProfit.add(higherAmount.subtract(pfBorderItem.getTotalPrice()));
        }
        //获得地址
        ComUserAddress comUserAddress = userAddressService.getOrderAddress(userAddressId, comUser.getId());
        if (comUserAddress != null) {
            request.getSession().setAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS, comUserAddress.getId());
        }
        mv.addObject("comUserAddress", comUserAddress);
        mv.addObject("pfBorder", pfBorder);
        mv.addObject("productInfo", stringBuffer.toString());
        mv.addObject("quantity", sumQuantity);
        mv.addObject("lowProfit", lowProfit.setScale(2, RoundingMode.HALF_DOWN));
        mv.addObject("highProfit", highProfit.setScale(2, RoundingMode.HALF_DOWN));
        mv.setViewName("platform/order/zhifu");
        return mv;
    }

    /**
     * 订单支付处理
     *
     * @author ZhaoLiang
     * @date 2016/3/17 14:32
     */
    @ResponseBody
    @RequestMapping("/payBOrderSubmit.do")
    public String payBOrderSubmit(HttpServletRequest request,
                                  @RequestParam(value = "bOrderId", required = true) Long bOrderId,
                                  @RequestParam(value = "userMessage", required = true) String userMessage,
                                  @RequestParam(value = "userAddressId", required = false) Long userAddressId) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (bOrderId <= 0) {
                throw new BusinessException("订单号错误");
            }
            PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
            //校验库存
            List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(bOrderId);
            for (PfBorderItem pfBorderItem : pfBorderItems) {
                if (pfBorder.getUserPid() > 0) {
                    userSkuService.checkParentData(pfBorder.getUserPid(), pfBorderItem.getSkuId(), pfBorderItem.getAgentLevelId());
                }
            }
            //拿货方式(0未选择1平台代发2自己发货)
            PfBorderConsignee pfBorderConsignee = null;
            if (pfBorder.getSendType() == 2) {
                if (userAddressId == null || userAddressId <= 0) {
                    throw new BusinessException("收货地址不能为空");
                }
                ComUserAddress comUserAddress = userAddressService.getUserAddressById(userAddressId);
                pfBorderConsignee = new PfBorderConsignee();
                pfBorderConsignee.setCreateTime(new Date());
                pfBorderConsignee.setPfBorderId(pfBorder.getId());
                pfBorderConsignee.setUserId(comUserAddress.getUserId());
                pfBorderConsignee.setConsignee(comUserAddress.getName());
                pfBorderConsignee.setMobile(comUserAddress.getMobile());
                pfBorderConsignee.setProvinceId(comUserAddress.getProvinceId());
                pfBorderConsignee.setProvinceName(comUserAddress.getProvinceName());
                pfBorderConsignee.setCityId(comUserAddress.getCityId());
                pfBorderConsignee.setCityName(comUserAddress.getCityName());
                pfBorderConsignee.setRegionId(comUserAddress.getRegionId());
                pfBorderConsignee.setRegionName(comUserAddress.getRegionName());
                pfBorderConsignee.setAddress(comUserAddress.getAddress());
                pfBorderConsignee.setZip(comUserAddress.getZip());
                //补货&自己发货 订单状态修改为待发货
                pfBorder.setOrderStatus(BOrderStatus.WaitShip.getCode());
            }
            pfBorder.setUserMessage(userMessage);
            bOrderService.toPayBOrder(pfBorder, pfBorderConsignee);
            jsonObject.put("isError", false);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping("/payBOrderReady.shtml")
    public String payBOrderReady(HttpServletRequest request,
                                 RedirectAttributes attrs,
                                 @RequestParam(value = "bOrderId", required = true) Long bOrderId) throws Exception {
        WxPaySysParamReq req = null;
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        //切换开发模式和测试模式
        String enviromentkey = PropertiesUtils.getStringValue(SysConstants.SYS_RUN_ENVIROMENT_KEY);
        if (StringUtils.isBlank(enviromentkey)
                || enviromentkey.equals("0")) {
            String uuid = UUID.randomUUID().toString();
            PfBorderPayment payment = new PfBorderPayment();
            payment.setPfBorderId(bOrderId);
            payment.setAmount(pfBorder.getReceivableAmount()); //new BigDecimal(p.getTotal_fee()).divide(new BigDecimal(100)));
            payment.setCreateTime(new Date());
            payment.setIsEnabled(0);
            // 给外部支付使用支付流水号
            payment.setPaySerialNum(uuid);
            payment.setPayTypeId(0);
            payment.setPayTypeName("微信支付");
            bOrderService.addBOrderPayment(payment);

            PfBorderPayment pfBorderPayment = bOrderService.findOrderPaymentBySerialNum(uuid);
            if (pfBorderPayment == null) {
                throw new BusinessException("该支付流水号不存在,pay_serial_num:" + uuid);
            }
            if (pfBorderPayment.getIsEnabled() == 0) {
                // 调用borderService的方法处理
                payBOrderService.mainPayBOrder(pfBorderPayment, UUID.randomUUID().toString(), getWebRootPath(request));
            }
            String successURL = "";
            //订单类型(0代理1补货2拿货)
            if (pfBorder.getOrderType() == 0) {
                //拿货方式(0未选择1平台代发2自己发货)
                if (pfBorder.getSendType() == 0) {
                    successURL += "border/setUserSendType.shtml";
                } else {
                    successURL += "border/payBOrdersSuccess.shtml";
                }
            } else if (pfBorder.getOrderType() == 1) {
                successURL += "payEnd/replenishment.shtml";
            } else {
                throw new BusinessException("订单类型不存在,orderType:" + pfBorder.getOrderType());
            }
            attrs.addAttribute("bOrderId", bOrderId);
            return "redirect:/" + successURL;
        } else if (enviromentkey.equals("1")) {
            String successURL = getBasePath(request);
            //订单类型(0代理1补货2拿货)
            if (pfBorder.getOrderType() == 0) {
                //拿货方式(0未选择1平台代发2自己发货)
                if (pfBorder.getSendType() == 0) {
                    successURL += "border/setUserSendType.shtml?bOrderId=" + pfBorder.getId();
                } else {
                    successURL += "border/payBOrdersSuccess.shtml?bOrderId=" + pfBorder.getId();
                }
            } else if (pfBorder.getOrderType() == 1) {
                successURL += "payEnd/replenishment.shtml?bOrderId=" + pfBorder.getId();
            } else {
                throw new BusinessException("订单类型不存在,orderType:" + pfBorder.getOrderType());
            }
            req = new WxPaySysParamReq();
            req.setOrderId(pfBorder.getOrderCode());
            req.setSignType("MD5");
            req.setNonceStr(WXBeanUtils.createGenerateStr());
            req.setSuccessUrl(successURL);
            req.setSign(WXBeanUtils.toSignString(req));
        }
        attrs.addAttribute("param", JSONObject.toJSONString(req));
        return "redirect:/wxpay/wtpay";
    }

    /**
     * 成功支付订单(代理订单)
     *
     * @author muchaofeng
     * @date 2016/3/9 15:06
     */
    @RequestMapping("/payBOrdersSuccess.shtml")
    public ModelAndView payBOrdersSuccess(HttpServletRequest request,
                                          @RequestParam(value = "bOrderId", required = true) Long bOrderId) throws Exception {
        ModelAndView mav = new ModelAndView();
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        String realName = "";//姓名
        String skuName = "";//合作产品
        String levelName = "";//合伙人等级
        String pRealName = "";//上级合伙人
        String sendType = "";//拿货方式
        ComUser comUser = getComUser(request);
        realName = comUser.getRealName();
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(bOrderId);
        skuName = pfBorderItems.get(0).getSkuName();
        //获取用户代理等级
        ComAgentLevel comAgentLevel = bOrderService.findComAgentLevel(pfBorderItems.get(0).getAgentLevelId());
        levelName = comAgentLevel.getName();
        //获取上级合伙人
        if (pfBorder.getUserPid() == 0) {
            pRealName = "平台";
        } else {
            ComUser pComuser = userService.getUserById(pfBorder.getUserPid());
            //判断是否已经
            pRealName = pComuser.getRealName();
        }
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 0) {
            sendType = "未选择";
        } else if (pfBorder.getSendType() == 1) {
            sendType = "平台代发";
        } else if (pfBorder.getSendType() == 2) {
            sendType = "自己发货";
        }
        mav.addObject("realName", realName);
        mav.addObject("skuName", skuName);
        mav.addObject("levelName", levelName);
        mav.addObject("pRealName", pRealName);
        mav.addObject("sendType", sendType);
        mav.addObject("userId", pfBorder.getUserId());
        mav.setViewName("platform/order/lingquzhengshu");
        return mav;
    }

    @RequestMapping("/payReplenishmentOrder.shtml")
    public ModelAndView payReplenishmentOrder() throws Exception {
        ModelAndView mv = new ModelAndView();
        try {

        } catch (Exception ex) {

        }
        return mv;
    }

    @RequestMapping("/setUserSendType.shtml")
    public ModelAndView setUserSendType(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestParam(value = "selectedAddressId", required = false) Long selectedAddressId,
                                        @RequestParam(value = "bOrderId") Long bOrderId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        ComUser comUser = getComUser(request);
        ComUserAddress comUserAddress = userAddressService.getOrderAddress(selectedAddressId, comUser.getId());
        modelAndView.addObject("comUserAddress", comUserAddress);
        modelAndView.addObject("bOrderId", bOrderId);
        if (comUserAddress != null) {
            modelAndView.addObject("addressId", comUserAddress.getId());
        }
        if (selectedAddressId == null) {
            modelAndView.addObject("isPlatformSendGoods", "true");
        } else {
            modelAndView.addObject("isPlatformSendGoods", "false");
        }
        modelAndView.setViewName("platform/order/nahuo");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/setUserSendType/save.do")
    public String setUserSendTypeSave(HttpServletRequest request,
                                      @RequestParam(value = "bOrderId", required = true) Long bOrderId,
                                      @RequestParam(value = "sendType", required = true) Integer sendType,
                                      @RequestParam(value = "userAddressId", required = false) Long userAddressId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isError", true);
        try {
            if (sendType == 2 && (userAddressId == null || userAddressId <= 0)) {
                throw new BusinessException("用户自发货，请选择收货地址。");
            }
            ComUser comUser = getComUser(request);
            payBOrderService.updateBOrderSendType(comUser, bOrderId, sendType, userAddressId);
            jsonObject.put("isError", false);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return jsonObject.toJSONString();
    }
}
