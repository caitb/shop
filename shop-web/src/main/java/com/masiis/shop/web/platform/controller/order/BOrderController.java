package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrderConfirm;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderAddService;
import com.masiis.shop.web.platform.service.order.BOrderPayService;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import com.masiis.shop.web.platform.utils.WXBeanUtils;
import com.sun.javafx.sg.Border;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
    private BOrderPayService payBOrderService;
    @Resource
    private BOrderAddService bOrderAddService;

    /**
     * 选择拿货方式
     *
     * @param request
     * @param skuId
     * @param levelId
     * @param weixinId
     * @param pUserId
     * @return
     * @throws Exception
     */
    @RequestMapping("/setUserSendType.shtml")
    public ModelAndView setUserSendType(HttpServletRequest request,
                                        @RequestParam(value = "skuId", required = true) Integer skuId,
                                        @RequestParam(value = "levelId", required = false) Integer levelId,
                                        @RequestParam(value = "weixinId", required = false) String weixinId,
                                        @RequestParam(value = "pUserId", required = false) Long pUserId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        ComUser comUser = getComUser(request);
        if (comUser.getSendType() != 0) {
            throw new BusinessException("用户已经选择了拿货方式");
        }
        modelAndView.addObject("skuId", skuId);
        modelAndView.addObject("levelId", levelId);
        modelAndView.addObject("weixinId", weixinId);
        modelAndView.addObject("pUserId", pUserId);
        modelAndView.setViewName("platform/order/nahuo");
        return modelAndView;
    }

    /**
     * 确认订单页面
     *
     * @param orderType 订单类型(0代理1补货2拿货)
     * @param skuId     订单商品
     * @param levelId   代理等级(订单代理需要)
     * @param weixinId  微信id(订单代理需要)
     * @param pUserId   上级用户id(订单代理需要)
     * @param quantity  订单商品数量(订单补货或拿货需要)
     * @author ZhaoLiang
     * @date 2016/3/5 16:32
     */
    @RequestMapping("/confirmBOrder.shtml")
    public ModelAndView confirmBOrder(HttpServletRequest request,
                                      @RequestParam(value = "orderType", required = true) Integer orderType,
                                      @RequestParam(value = "skuId", required = true) Integer skuId,
                                      @RequestParam(value = "levelId", required = false) Integer levelId,
                                      @RequestParam(value = "weixinId", required = false) String weixinId,
                                      @RequestParam(value = "pUserId", required = false) Long pUserId,
                                      @RequestParam(value = "sendType", required = false) Integer sendType,
                                      @RequestParam(value = "quantity", required = false) Integer quantity,
                                      @RequestParam(value = "userAddressId", required = false) Long userAddressId) throws Exception {
        ModelAndView mv = new ModelAndView();
        ComUser comUser = getComUser(request);
        Integer userSendType = 0;
        if (comUser.getSendType() == null || comUser.getSendType() <= 0) {
            if (sendType == null || sendType <= 0) {
                throw new BusinessException("还未选中拿货方式");
            }
            userSendType = sendType;
        } else {
            userSendType = comUser.getSendType();
        }
        BOrderConfirm bOrderConfirm = new BOrderConfirm();
        bOrderConfirm.setOrderType(orderType);
        bOrderConfirm.setWenXinId(weixinId);
        bOrderConfirm.setpUserId(pUserId);
        //拿货方式
        bOrderConfirm.setSendType(userSendType);
        //获得地址
        ComUserAddress comUserAddress = userAddressService.getOrderAddress(userAddressId, comUser.getId());
        bOrderConfirm.setComUserAddress(comUserAddress);
        //获取sku
        ComSku comSku = skuService.getSkuById(skuId);
        bOrderConfirm.setSkuId(skuId);
        bOrderConfirm.setSkuName(comSku.getName());
        //获取sku主图片
        ComSkuImage comSkuImage = skuService.findComSkuImage(skuId);
        String skuImg = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        bOrderConfirm.setSkuImg(skuImg + comSkuImage.getImgUrl());
        //获取用户代理等级
        Integer temLevelId = 0;
        if (orderType == 0) {
            if (levelId == null || levelId <= 0) {
                throw new BusinessException("商品代理等级有误");
            }
            temLevelId = levelId;
        } else {
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), skuId);
            temLevelId = pfUserSku.getAgentLevelId();
        }
        ComAgentLevel comAgentLevel = bOrderService.findComAgentLevel(temLevelId);
        bOrderConfirm.setAgentLevelId(comAgentLevel.getId());
        bOrderConfirm.setAgentLevelName(comAgentLevel.getName());
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, comAgentLevel.getId());
        if (orderType == 0) {
            bOrderConfirm.setSkuQuantity(pfSkuAgent.getQuantity());
        } else {
            if (quantity == null || quantity <= 0) {
                throw new BusinessException("商品数量有误");
            }
            bOrderConfirm.setSkuQuantity(quantity);
        }
        BigDecimal unitPrice = comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount());
        bOrderConfirm.setProductTotalPrice(unitPrice.multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity())));
        bOrderConfirm.setBailAmount(pfSkuAgent.getBail());
        BigDecimal highProfit = comSku.getPriceRetail().multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity()));//最高利润
        bOrderConfirm.setHighProfit(highProfit);
        PfSkuAgent lowerSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, temLevelId + 1);
        BigDecimal lowProfit = comSku.getPriceRetail().multiply(lowerSkuAgent.getDiscount()).multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity()));//最低利润
        bOrderConfirm.setLowProfit(lowProfit);
        bOrderConfirm.setOrderTotalPrice(bOrderConfirm.getProductTotalPrice().add(bOrderConfirm.getBailAmount()));
        mv.addObject("bOrderConfirm", bOrderConfirm);
        mv.setViewName("platform/order/zhifu");
        return mv;
    }

    /**
     * 用户确认生成订单
     *
     * @author ZhaoLiang
     * @date 2016/3/8 12:50
     */
    @ResponseBody
    @RequestMapping("/add.do")
    public String addBOrder(HttpServletRequest request,
                            BOrderConfirm bOrderConfirm) {
        JSONObject obj = new JSONObject();
        try {
            if (bOrderConfirm.getSkuId() == null || bOrderConfirm.getSkuId() <= 0) {
                throw new BusinessException("商品skuId不正确");
            }
            ComUser comUser = getComUser(request);
            ComSku comSku = skuService.getSkuById(bOrderConfirm.getSkuId());
            //订单类型(0代理1补货2拿货)
            Integer orderType = bOrderConfirm.getOrderType();
            BigDecimal unitPrice = BigDecimal.ZERO;//折扣后单价
            BigDecimal bailPrice = BigDecimal.ZERO;//保证金
            Integer quantitiy = 0;//商品数量
            Integer agentLevel = 0;//代理等级
            BigDecimal discount = BigDecimal.ZERO;
            if (orderType == 0) {
                if (StringUtils.isBlank(bOrderConfirm.getWenXinId())) {
                    throw new BusinessException("微信号不能为空");
                }
                if (bOrderConfirm.getAgentLevelId() == null || bOrderConfirm.getAgentLevelId() <= 0) {
                    throw new BusinessException("商品代理等级不正确");
                }
                if (StringUtils.isBlank(bOrderConfirm.getWenXinId())) {
                    throw new BusinessException("商品代理微信号不正确");
                }
                if (bOrderConfirm.getpUserId() != null && bOrderConfirm.getpUserId() > 0) {
                    userSkuService.checkParentData(bOrderConfirm.getpUserId(), bOrderConfirm.getSkuId(), bOrderConfirm.getAgentLevelId());
                }
                PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(bOrderConfirm.getSkuId(), bOrderConfirm.getAgentLevelId());
                unitPrice = comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount());
                bailPrice = pfSkuAgent.getBail();
                quantitiy = pfSkuAgent.getQuantity();
                agentLevel = bOrderConfirm.getAgentLevelId();
                discount = pfSkuAgent.getDiscount();
            } else if (orderType == 1) {
                PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), bOrderConfirm.getSkuId());
                PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(bOrderConfirm.getSkuId(), pfUserSku.getAgentLevelId());
                unitPrice = comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount());
                bailPrice = BigDecimal.ZERO;
                quantitiy = bOrderConfirm.getSkuQuantity();
                agentLevel = pfUserSku.getAgentLevelId();
                discount = pfSkuAgent.getDiscount();
            } else if (orderType == 2) {
                PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), bOrderConfirm.getSkuId());
                unitPrice = BigDecimal.ZERO;
                bailPrice = BigDecimal.ZERO;
                quantitiy = bOrderConfirm.getSkuQuantity();
                agentLevel = pfUserSku.getAgentLevelId();
            } else {
                throw new BusinessException("订单类型不正确");
            }
            //处理订单数据
            PfBorder pfBorder = new PfBorder();
            pfBorder.setCreateTime(new Date());
            pfBorder.setCreateMan(comUser.getId());
            String orderCode = OrderMakeUtils.makeOrder("B");
            pfBorder.setOrderCode(orderCode);
            pfBorder.setUserMessage(bOrderConfirm.getUserMessage());
            pfBorder.setUserId(comUser.getId());
            if (bOrderConfirm.getpUserId() != null && bOrderConfirm.getpUserId() > 0) {
                pfBorder.setUserPid(bOrderConfirm.getpUserId());
            } else {
                pfBorder.setUserPid(0l);
            }
            pfBorder.setSupplierId(0);
            pfBorder.setReceivableAmount(unitPrice.multiply(BigDecimal.valueOf(quantitiy)).add(bailPrice));
            pfBorder.setOrderAmount(unitPrice.multiply(BigDecimal.valueOf(quantitiy)).add(bailPrice));
            pfBorder.setBailAmount(bailPrice);
            pfBorder.setProductAmount(unitPrice.multiply(BigDecimal.valueOf(quantitiy)));
            pfBorder.setShipAmount(BigDecimal.ZERO);
            pfBorder.setPayAmount(BigDecimal.ZERO);
            pfBorder.setShipManId(0);
            pfBorder.setShipManName("");
            pfBorder.setShipType(0);
            pfBorder.setShipRemark("");
            //确定订单的拿货方式
            if (comUser.getSendType() == 0) {
                pfBorder.setSendType(bOrderConfirm.getSendType());
            } else {
                pfBorder.setSendType(comUser.getSendType());
            }
            pfBorder.setOrderType(orderType);
            pfBorder.setOrderStatus(0);
            pfBorder.setShipStatus(0);
            pfBorder.setPayStatus(0);
            pfBorder.setIsCounting(0);
            pfBorder.setIsShip(0);
            pfBorder.setIsReplace(0);
            pfBorder.setIsReceipt(0);
            pfBorder.setReplaceOrderId(0l);
            pfBorder.setRemark("");
            //处理订单商品数据
            List<PfBorderItem> orderItems = new ArrayList<>();
            PfBorderItem pfBorderItem = new PfBorderItem();
            pfBorderItem.setCreateTime(new Date());
            pfBorderItem.setSpuId(comSku.getSpuId());
            pfBorderItem.setSkuId(comSku.getId());
            pfBorderItem.setSkuName(comSku.getName());
            pfBorderItem.setAgentLevelId(agentLevel);
            pfBorderItem.setWxId(bOrderConfirm.getWenXinId());
            pfBorderItem.setQuantity(quantitiy);
            pfBorderItem.setOriginalPrice(comSku.getPriceRetail());
            pfBorderItem.setDiscount(discount);
            pfBorderItem.setUnitPrice(unitPrice);
            pfBorderItem.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(quantitiy)));
            pfBorderItem.setBailAmount(bailPrice);
            pfBorderItem.setIsComment(0);
            pfBorderItem.setIsReturn(0);
            orderItems.add(pfBorderItem);
            if (userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), comSku.getId()) != null) {
                throw new BusinessException("此商品已经建立过代理，请通过补货增加库存。");
            }
            PfBorderConsignee pfBorderConsignee = null;
            //拿货方式(0未选择1平台代发2自己发货)
            if (pfBorder.getOrderType() == 2 || pfBorder.getSendType() == 2) {
                //获得地址
                ComUserAddress comUserAddress = userAddressService.getOrderAddress(bOrderConfirm.getUserAddressId(), comUser.getId());
                if (comUserAddress == null) {
                    throw new BusinessException("请填写收货地址");
                }
                pfBorderConsignee = new PfBorderConsignee();
                pfBorderConsignee.setCreateTime(new Date());
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
            }
            Long bOrderId = bOrderAddService.AddBOrder(pfBorder, orderItems, pfBorderConsignee);
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


    @RequestMapping("/goToCheckoutBOrder.shtml")
    public ModelAndView toPayBOrder(@RequestParam(value = "bOrderId", required = true) Long bOrderId) {
        ModelAndView modelAndView = new ModelAndView();
        if (bOrderId == null || bOrderId <= 0) {
            throw new BusinessException("订单号不正确");
        }
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemDetail(bOrderId);
        modelAndView.addObject("pfBorder", pfBorder);
        modelAndView.addObject("pfBorderItems", pfBorderItems);
        modelAndView.setViewName("platform/order/shouyintai");
        return modelAndView;
    }

    @RequestMapping("/payBOrder.shtml")
    public String payBOrder(HttpServletRequest request,
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
