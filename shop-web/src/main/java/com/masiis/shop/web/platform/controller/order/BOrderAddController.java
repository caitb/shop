package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.BOrder.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrderAdd;
import com.masiis.shop.dao.beans.order.BOrderConfirm;
import com.masiis.shop.dao.beans.order.BorderAgentParamForAddress;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderAddService;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import com.masiis.shop.web.platform.service.user.UserCertificateService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * BOrderAddController
 *
 * @author ZhaoLiang
 * @date 2016/4/17
 */
@Controller
@RequestMapping("/BOrderAdd")
public class BOrderAddController extends BaseController {
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
    private BOrderAddService bOrderAddService;
    @Resource
    private UserCertificateService userCertificateService;

    @RequestMapping("/agentBOrder.shtml")
    public ModelAndView agentBOrder(HttpServletRequest request,
                                    @RequestParam(value = "skuId", required = true) Integer skuId,
                                    @RequestParam(value = "agentLevelId", required = true) Integer agentLevelId,
                                    @RequestParam(value = "weiXinId", required = true) String weiXinId,
                                    @RequestParam(value = "sendType", required = true) Integer sendType,
                                    @RequestParam(value = "pUserId", required = false) Long pUserId,
                                    @RequestParam(value = "userAddressId", required = false) Long userAddressId) throws Exception {
        ModelAndView mv = new ModelAndView("platform/order/BOrderAdd/agentBOrderAdd");
        ComUser comUser = getComUser(request);
        if (agentLevelId == null || agentLevelId <= 0) {
            throw new BusinessException("商品代理等级有误");
        }
        if (comUser.getSendType() == null || comUser.getSendType() <= 0) {
            if (sendType == null || sendType <= 0) {
                throw new BusinessException("还未选中拿货方式");
            }
        } else {
            sendType = comUser.getSendType();
        }
        JSONObject jsonObject = new JSONObject();
        BorderAgentParamForAddress agentParamForAddress = new BorderAgentParamForAddress();
        agentParamForAddress.setAgentLevelId(agentLevelId);
        agentParamForAddress.setpUserId(pUserId);
        agentParamForAddress.setSendType(sendType);
        agentParamForAddress.setSkuId(skuId);
        agentParamForAddress.setWeiXinId(weiXinId);
        String paramForAddress = JSONObject.toJSONString(agentParamForAddress);
        mv.addObject("agentOrderparamForAddress", paramForAddress);


        BOrderConfirm bOrderConfirm = new BOrderConfirm();
        bOrderConfirm.setOrderType(BOrderType.agent.getCode());
        bOrderConfirm.setWenXinId(weiXinId);
        bOrderConfirm.setpUserId(pUserId);
        //拿货方式
        bOrderConfirm.setSendType(sendType);
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
        ComAgentLevel comAgentLevel = bOrderService.findComAgentLevel(agentLevelId);
        bOrderConfirm.setAgentLevelId(comAgentLevel.getId());
        bOrderConfirm.setAgentLevelName(comAgentLevel.getName());
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, comAgentLevel.getId());
        bOrderConfirm.setSkuQuantity(pfSkuAgent.getQuantity());
        bOrderConfirm.setBailAmount(pfSkuAgent.getBail());
        BigDecimal unitPrice = comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount()).setScale(2, BigDecimal.ROUND_DOWN);
        bOrderConfirm.setProductTotalPrice(unitPrice.multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity())));
        BigDecimal highProfit = comSku.getPriceRetail().multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity())).setScale(2, BigDecimal.ROUND_DOWN);//最高利润
        bOrderConfirm.setHighProfit(highProfit);
        Integer lowerAgentLevelId = agentLevelId + 1;
        if (lowerAgentLevelId > 3) {
            lowerAgentLevelId = 3;
        }
        PfSkuAgent lowerSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, lowerAgentLevelId);
        BigDecimal lowProfit = comSku.getPriceRetail().multiply(lowerSkuAgent.getDiscount()).multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity())).setScale(2, BigDecimal.ROUND_DOWN);//最低利润
        bOrderConfirm.setLowProfit(lowProfit);
        bOrderConfirm.setOrderTotalPrice(bOrderConfirm.getProductTotalPrice().add(bOrderConfirm.getBailAmount()).setScale(2, BigDecimal.ROUND_DOWN));
        boolean isQueuing = false;
        Integer count = 0;
        // 判断排单标志位,如果处于排单状态下,显示排单人数
        if (pUserId == null) {
            pUserId = 0l;
        }
        int n = skuService.checkSkuStock(skuId, 1, pUserId);
        if (n < 0) {
            isQueuing = true;
            count = bOrderService.selectQueuingOrderCount(skuId);
        }
        mv.addObject("bOrderConfirm", bOrderConfirm);
        mv.addObject("isQueuing", isQueuing);
        mv.addObject("count", count);
        return mv;
    }

    @ResponseBody
    @RequestMapping("/agentBOrder/add.do")
    public String agentBOrderAdd(HttpServletRequest request,
                                 @RequestParam(value = "skuId", required = true) Integer skuId,
                                 @RequestParam(value = "agentLevelId", required = true) Integer agentLevelId,
                                 @RequestParam(value = "weiXinId", required = true) String weiXinId,
                                 @RequestParam(value = "sendType", required = true) Integer sendType,
                                 @RequestParam(value = "pUserId", required = false) Long pUserId,
                                 @RequestParam(value = "userMessage", required = false) String userMessage,
                                 @RequestParam(value = "userAddressId", required = false) Long userAddressId) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (skuId == null || skuId <= 0) {
                throw new BusinessException("参数校验失败：skuId:" + skuId);
            }
            if (agentLevelId == null || agentLevelId <= 0) {
                throw new BusinessException("参数校验失败：agentLevelId:" + agentLevelId);
            }
            if (StringUtils.isBlank(weiXinId)) {
                throw new BusinessException("参数校验失败：weiXinId:" + weiXinId);
            }
            ComUser comUser = getComUser(request);
            if (comUser.getSendType() > 0) {
                sendType = comUser.getSendType();
            } else if (pUserId != null && pUserId > 0) {
                sendType = userService.getUserById(pUserId).getSendType();
            } else {
                if (sendType == null && sendType <= 0) {
                    throw new BusinessException("参数校验失败：sendType:" + sendType);
                }
            }
            if (pUserId == null) {
                pUserId = 0l;
            }
            if (StringUtils.isBlank(userMessage)) {
                userMessage = "";
            }
            if (sendType == 2 && (userAddressId == null || userAddressId <= 0)) {
                throw new BusinessException("参数校验失败：userAddressId:" + userAddressId);
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
            jsonObject.put("isError", false);
            jsonObject.put("bOrderId", bOrderId);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping("/supplementBOrder.shtml")
    public ModelAndView supplementBOrder(HttpServletRequest request,
                                         @RequestParam(value = "skuId", required = true) Integer skuId,
                                         @RequestParam(value = "quantity", required = true) Integer quantity,
                                         @RequestParam(value = "userAddressId", required = false) Long userAddressId) throws Exception {
        ModelAndView mv = new ModelAndView("platform/order/BOrderAdd/supplementBOrderAdd");
        ComUser comUser = getComUser(request);
        Integer sendType = comUser.getSendType();
        Integer agentLevelId = 0;
        PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), skuId);
        agentLevelId = pfUserSku.getAgentLevelId();
        PfUserCertificate pfUserCertificate = userCertificateService.getCertificateBypfuId(pfUserSku.getId());
        BOrderConfirm bOrderConfirm = new BOrderConfirm();
        bOrderConfirm.setOrderType(BOrderType.agent.getCode());
        bOrderConfirm.setWenXinId(pfUserCertificate.getWxId());
        bOrderConfirm.setpUserId(pfUserSku.getUserPid());
        //拿货方式
        bOrderConfirm.setSendType(sendType);
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
        ComAgentLevel comAgentLevel = bOrderService.findComAgentLevel(agentLevelId);
        bOrderConfirm.setAgentLevelId(comAgentLevel.getId());
        bOrderConfirm.setAgentLevelName(comAgentLevel.getName());
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, comAgentLevel.getId());
        bOrderConfirm.setSkuQuantity(quantity);
        bOrderConfirm.setBailAmount(BigDecimal.ZERO);
        BigDecimal unitPrice = comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount()).setScale(2, BigDecimal.ROUND_DOWN);
        bOrderConfirm.setProductTotalPrice(unitPrice.multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity())));
        BigDecimal highProfit = comSku.getPriceRetail().multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity())).setScale(2, BigDecimal.ROUND_DOWN);//最高利润
        bOrderConfirm.setHighProfit(highProfit);
        Integer lowerAgentLevelId = agentLevelId + 1;
        if (lowerAgentLevelId > 3) {
            lowerAgentLevelId = 3;
        }
        PfSkuAgent lowerSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, lowerAgentLevelId);
        BigDecimal lowProfit = comSku.getPriceRetail().multiply(lowerSkuAgent.getDiscount()).multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity())).setScale(2, BigDecimal.ROUND_DOWN);//最低利润
        bOrderConfirm.setLowProfit(lowProfit);
        bOrderConfirm.setOrderTotalPrice(bOrderConfirm.getProductTotalPrice().add(bOrderConfirm.getBailAmount()).setScale(2, BigDecimal.ROUND_DOWN));
        //获取排单信息
        PfSkuStock pfSkuStock = skuService.getPfSkuStockInfoBySkuId(skuId);
        if (pfSkuStock != null && pfSkuStock.getIsQueue() == 1) {
            mv.addObject("isQueue", true);
        }
        mv.addObject("bOrderConfirm", bOrderConfirm);
        return mv;
    }

    @ResponseBody
    @RequestMapping("/supplementBOrder/add.do")
    public String supplementBOrderAdd(HttpServletRequest request,
                                      @RequestParam(value = "skuId", required = true) Integer skuId,
                                      @RequestParam(value = "quantity", required = true) Integer quantity,
                                      @RequestParam(value = "userMessage", required = false) String userMessage,
                                      @RequestParam(value = "userAddressId", required = false) Long userAddressId) {
        JSONObject jsonObject = new JSONObject();
        try {
            Integer sendType = 0;
            ComUser comUser = getComUser(request);
            if (comUser.getSendType() <= 0) {
                throw new BusinessException("您还没有确定拿货状态不能补货");
            }
            sendType = comUser.getSendType();
            if (skuId == null || skuId <= 0) {
                throw new BusinessException("参数校验失败：skuId:" + skuId);
            }
            if (StringUtils.isBlank(userMessage)) {
                userMessage = "";
            }
            if (sendType == 2 && (userAddressId == null || userAddressId <= 0)) {
                throw new BusinessException("参数校验失败：userAddressId:" + userAddressId);
            }
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), skuId);
            PfUserCertificate pfUserCertificate = userCertificateService.getCertificateBypfuId(pfUserSku.getId());
            BOrderAdd bOrderAdd = new BOrderAdd();
            bOrderAdd.setOrderType(BOrderType.agent.getCode());
            bOrderAdd.setpUserId(pfUserSku.getUserPid());
            bOrderAdd.setUserId(comUser.getId());
            bOrderAdd.setSendType(sendType);
            bOrderAdd.setSkuId(skuId);
            bOrderAdd.setAgentLevelId(pfUserSku.getAgentLevelId());
            bOrderAdd.setWeiXinId(pfUserCertificate.getWxId());
            bOrderAdd.setUserMessage(userMessage);
            bOrderAdd.setUserAddressId(userAddressId);
            bOrderAdd.setQuantity(quantity);
            Long bOrderId = bOrderAddService.addBOrder(bOrderAdd);
            jsonObject.put("isError", false);
            jsonObject.put("bOrderId", bOrderId);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping("/goToPayBOrder.shtml")
    public ModelAndView toPayBOrder(@RequestParam(value = "bOrderId", required = true) Long bOrderId) {
        ModelAndView modelAndView = new ModelAndView("platform/user/orderProview");
        if (bOrderId == null || bOrderId <= 0) {
            throw new BusinessException("订单号不正确");
        }
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemDetail(bOrderId);
        modelAndView.addObject("pfBorder", pfBorder);
        modelAndView.addObject("pfBorderItems", pfBorderItems);
        return modelAndView;
    }
}
