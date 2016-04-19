package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderPayService;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.PfUserRelationService;
import com.masiis.shop.web.platform.service.user.UserService;
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
    private UserService userService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private BOrderPayService payBOrderService;
    @Resource
    private SkuService skuService;
    @Resource
    private PfUserRelationService pfUserRelationService;

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
                                        @RequestParam(value = "agentLevelId", required = false) Integer agentLevelId,
                                        @RequestParam(value = "weiXinId", required = false) String weiXinId) throws Exception {
        ModelAndView modelAndView = new ModelAndView("platform/order/nahuo");
        ComUser comUser = getComUser(request);
        if (comUser.getSendType() != 0) {
            throw new BusinessException("用户已经选择了拿货方式");
        }
        Long pUserId = pfUserRelationService.getPUserId(comUser.getId(), skuId);
        modelAndView.addObject("skuId", skuId);
        modelAndView.addObject("agentLevelId", agentLevelId);
        modelAndView.addObject("weiXinId", weiXinId);
        boolean isQueuing = false;
        Integer count = 0;
        int status = skuService.getSkuStockStatus(skuId, 1, pUserId);
        if (status == 1) {
            isQueuing = true;
            count = bOrderService.selectQueuingOrderCount(skuId);
        }
        modelAndView.addObject("isQueuing", isQueuing);
        modelAndView.addObject("count", count);
        return modelAndView;
    }


    @RequestMapping("/goToPayBOrder.shtml")
    public ModelAndView toPayBOrder(@RequestParam(value = "bOrderId", required = true) Long bOrderId) {
        ModelAndView modelAndView = new ModelAndView("platform/order/shouyintai");
        if (bOrderId == null || bOrderId <= 0) {
            throw new BusinessException("订单号不正确");
        }
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemDetail(bOrderId);
        modelAndView.addObject("pfBorder", pfBorder);
        modelAndView.addObject("pfBorderItems", pfBorderItems);
        return modelAndView;
    }

    @RequestMapping("/payBOrder.shtml")
    public String payBOrder(HttpServletRequest request,
                            RedirectAttributes attrs,
                            @RequestParam(value = "bOrderId", required = true) Long bOrderId) throws Exception {
        WxPaySysParamReq req = null;
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        if (pfBorder.getOrderStatus() != BOrderStatus.NotPaid.getCode()) {
            throw new BusinessException("订单状态异常");
        }
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
        ModelAndView mav = new ModelAndView("platform/order/lingquzhengshu");
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        String realName = "";//姓名
        String skuName = "";//合作产品
        String levelName = "";//合伙人等级
        String pRealName = "";//上级合伙人
        String sendTypeName = "";//拿货方式
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
            sendTypeName = "未选择";
        } else if (pfBorder.getSendType() == 1) {
            sendTypeName = "平台代发";
        } else if (pfBorder.getSendType() == 2) {
            sendTypeName = "自己发货";
        }
        mav.addObject("realName", realName);
        mav.addObject("skuName", skuName);
        mav.addObject("levelName", levelName);
        mav.addObject("pRealName", pRealName);
        mav.addObject("sendTypeName", sendTypeName);
        mav.addObject("pfBorder", pfBorder);
        boolean isQueuing = false;
        Integer count = 0;
        if (pfBorder.getOrderStatus() == BOrderStatus.MPS.getCode()) {
            isQueuing = true;
            count = bOrderService.selectQueuingOrderCount(pfBorderItems.get(0).getSkuId());
        }
        mav.addObject("isQueuing", isQueuing);
        mav.addObject("count", count);
        mav.addObject("quantity", pfBorderItems.get(0).getQuantity());
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
