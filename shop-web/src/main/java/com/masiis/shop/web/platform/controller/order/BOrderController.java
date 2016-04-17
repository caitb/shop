package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.enums.BOrder.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrderAdd;
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
 * @autor zhaoliang
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
        BigDecimal unitPrice = comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount()).setScale(2, BigDecimal.ROUND_DOWN);
        bOrderConfirm.setProductTotalPrice(unitPrice.multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity())));
        bOrderConfirm.setBailAmount(pfSkuAgent.getBail());
        BigDecimal highProfit = comSku.getPriceRetail().multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity())).setScale(2, BigDecimal.ROUND_DOWN);//最高利润
        bOrderConfirm.setHighProfit(highProfit);
        PfSkuAgent lowerSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, temLevelId + 1);
        BigDecimal lowProfit = comSku.getPriceRetail().multiply(lowerSkuAgent.getDiscount()).multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity())).setScale(2, BigDecimal.ROUND_DOWN);//最低利润
        bOrderConfirm.setLowProfit(lowProfit);
        bOrderConfirm.setOrderTotalPrice(bOrderConfirm.getProductTotalPrice().add(bOrderConfirm.getBailAmount()).setScale(2, BigDecimal.ROUND_DOWN));
        mv.addObject("bOrderConfirm", bOrderConfirm);
        mv.setViewName("platform/order/zhifu");
        return mv;
    }

    /**
     * 代理提交订单
     *
     * @param request
     * @param pUserId       上级合伙人id，平台为0
     * @param sendType      拿货方式(0未选择1平台代发2自己发货)
     * @param skuId         合伙skuId
     * @param levelId       合伙等级
     * @param weiXinId      微信Id
     * @param userMessage   用户留言
     * @param userAddressId 用户地址Id
     * @return
     */
    @ResponseBody
    @RequestMapping("/add.do")
    public String addBOrder(HttpServletRequest request,
                            @RequestParam(value = "pUserId", required = false) Long pUserId,
                            @RequestParam(value = "sendType", required = true) Integer sendType,
                            @RequestParam(value = "skuId", required = true) Integer skuId,
                            @RequestParam(value = "agentLevelId", required = true) Integer agentLevelId,
                            @RequestParam(value = "weiXinId", required = true) String weiXinId,
                            @RequestParam(value = "userMessage", required = false) String userMessage,
                            @RequestParam(value = "userAddressId", required = false) Long userAddressId) {
        JSONObject obj = new JSONObject();
        try {
            if (skuId == null) {
                throw new BusinessException("参数校验失败：skuId为空！");
            }
            if (pUserId == null) {
                pUserId = 0l;
            }
            ComUser comUser = getComUser(request);
            if (comUser.getSendType() > 0) {
                sendType = comUser.getSendType();
            } else if (pUserId > 0) {
                sendType = userService.getUserById(pUserId).getSendType();
            }
            if (sendType == null && sendType <= 0) {
                throw new BusinessException("参数校验失败：拿货方式异常！");
            }
            if (agentLevelId == null) {
                throw new BusinessException("参数校验失败：合伙等级为空！");
            }
            if (weiXinId == null) {
                throw new BusinessException("参数校验失败：合伙微信为空！");
            }
            if (userMessage == null) {
                userMessage = "";
            }
            if (sendType == 2 && (userAddressId == null || userAddressId <= 0)) {
                throw new BusinessException("参数校验失败：用户收货信息异常！");
            }
            BOrderAdd bOrderAdd = new BOrderAdd();
            bOrderAdd.setOrderType(BOrderType.agent.getCode());
            bOrderAdd.setpUserId(pUserId);
            bOrderAdd.setUserId(comUser.getId());
            bOrderAdd.setSendType(sendType);
            bOrderAdd.setSkuId(skuId);
            bOrderAdd.setAgentLevelId(agentLevelId);
            bOrderAdd.setWeiXinId(weiXinId);
            bOrderAdd.setUserMessage(userMessage);
            bOrderAdd.setUserAddressId(userAddressId);
            Long bOrderId = bOrderAddService.addBOrder(bOrderAdd);
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


    @RequestMapping("/goToPayBOrder.shtml")
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
