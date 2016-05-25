package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.BOrder.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrderAdd;
import com.masiis.shop.dao.beans.order.BOrderConfirm;
import com.masiis.shop.dao.beans.order.BorderAgentParamForAddress;
import com.masiis.shop.dao.beans.order.BorderSupplementParamForAddress;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderAddService;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
    @Resource
    private PfUserRelationService pfUserRelationService;

    /**
     * 代理订单确认订单页
     *
     * @param request
     * @param skuId            代理skuId
     * @param agentLevelId     代理等级
     * @param weiXinId         微信号
     * @param sendType         拿货方式
     * @param previousPageType 0：上一页是注册 1：上一页是选择拿货方式
     * @param userAddressId    用户地址
     * @return
     * @throws Exception
     */
    @RequestMapping("/agentBOrder.shtml")
    public ModelAndView agentBOrder(HttpServletRequest request,
                                    @RequestParam(value = "skuId", required = true) Integer skuId,
                                    @RequestParam(value = "agentLevelId", required = true) Integer agentLevelId,
                                    @RequestParam(value = "weiXinId", required = true) String weiXinId,
                                    @RequestParam(value = "sendType", required = true) Integer sendType,
                                    @RequestParam(value = "previousPageType", required = true) Integer previousPageType,
                                    @RequestParam(value = "userAddressId", required = false) Long userAddressId) throws Exception {
        ModelAndView mv = new ModelAndView("platform/order/agent/agentBOrder");
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
        BorderAgentParamForAddress agentParamForAddress = new BorderAgentParamForAddress();
        agentParamForAddress.setAgentLevelId(agentLevelId);
        agentParamForAddress.setSendType(sendType);
        agentParamForAddress.setSkuId(skuId);
        agentParamForAddress.setWeiXinId(weiXinId);
        agentParamForAddress.setPreviousPageType(previousPageType);
        String paramForAddress = JSONObject.toJSONString(agentParamForAddress);
        mv.addObject("agentOrderparamForAddress", paramForAddress);
        BOrderConfirm bOrderConfirm = new BOrderConfirm();
        bOrderConfirm.setOrderType(BOrderType.agent.getCode());
        bOrderConfirm.setWeiXinId(weiXinId);
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
        //货币类型格式化
//        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        //获取用户代理等级
        bOrderConfirm.setAgentLevelId(agentLevelId);
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, agentLevelId);
        bOrderConfirm.setSkuQuantity(pfSkuAgent.getQuantity());
        bOrderConfirm.setBailAmount(pfSkuAgent.getBail().toString());
        BigDecimal unitPrice = pfSkuAgent.getUnitPrice();
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity()));
        bOrderConfirm.setProductTotalPrice(totalPrice.toString());
        BigDecimal highProfit = (comSku.getPriceRetail().subtract(unitPrice))
                .multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity()))
                .setScale(2, BigDecimal.ROUND_DOWN);//最高利润
        bOrderConfirm.setHighProfit(highProfit.toString());
        Integer lowerAgentLevelId = agentLevelId + 1;
        //获取下级代理信息
        PfSkuAgent lowerSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, lowerAgentLevelId);
        BigDecimal lowProfit = BigDecimal.ZERO;
        if (lowerSkuAgent == null) {
            lowProfit = highProfit;
        } else {
            lowProfit = (lowerSkuAgent.getUnitPrice().subtract(pfSkuAgent.getUnitPrice()))
                    .multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity()))
                    .setScale(2, BigDecimal.ROUND_DOWN);//最低利润
        }
        bOrderConfirm.setLowProfit(lowProfit.toString());
        bOrderConfirm.setOrderTotalPrice(totalPrice.add(pfSkuAgent.getBail()).toString());
        Long pUserId = pfUserRelationService.getPUserId(comUser.getId(), skuId);
        boolean isQueuing = false;
        Integer count = 0;
        int status = skuService.getSkuStockStatus(skuId, 1, pUserId);
        if (status == 1) {
            isQueuing = true;
            count = bOrderService.selectQueuingOrderCount(skuId);
        }
        mv.addObject("comUser", comUser);
        mv.addObject("bOrderConfirm", bOrderConfirm);
        mv.addObject("previousPageType", previousPageType);
        mv.addObject("isQueuing", isQueuing);
        mv.addObject("count", count);
        return mv;
    }

    /**
     * 生成代理订单
     *
     * @param request
     * @param skuId
     * @param agentLevelId  代理等级
     * @param weiXinId      微信号
     * @param sendType      拿货方式
     * @param userMessage   用户留言
     * @param userAddressId 用户地址
     * @return
     */
    @ResponseBody
    @RequestMapping("/agentBOrder/add.do")
    public String agentBOrderAdd(HttpServletRequest request,
                                 @RequestParam(value = "skuId", required = true) Integer skuId,
                                 @RequestParam(value = "agentLevelId", required = true) Integer agentLevelId,
                                 @RequestParam(value = "weiXinId", required = true) String weiXinId,
                                 @RequestParam(value = "sendType", required = true) Integer sendType,
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
            PfBorder pfBorder = bOrderService.getPfBorderBySkuAndUserId(skuId, comUser.getId());
            if (pfBorder != null) {
                throw new BusinessException("您已经有了此款产品的合伙订单，订单号编码为:" + pfBorder.getOrderCode()+"，请去合伙人订单中完成合伙人申请。");
            }
            Long pUserId = pfUserRelationService.getPUserId(comUser.getId(), skuId);
            if (comUser.getSendType() > 0) {
                sendType = comUser.getSendType();
            } else if (pUserId > 0) {
                sendType = userService.getUserById(pUserId).getSendType();
            }
            if (sendType == null && sendType <= 0) {
                throw new BusinessException("参数校验失败：sendType:" + sendType);
            }
            if (StringUtils.isBlank(userMessage)) {
                userMessage = "";
            }
            if (sendType == 2 && (userAddressId == null || userAddressId <= 0)) {
                throw new BusinessException("参数校验失败：userAddressId:" + userAddressId);
            }
            PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, agentLevelId);
            int status = skuService.getSkuStockStatus(skuId, pfSkuAgent.getQuantity(), pUserId);
            if (status == 2) {
                throw new BusinessException("您的上级合伙人库存不足，请联系补货");
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

    /**
     * 补货订单确认订单页
     *
     * @param request
     * @param skuId         补货sku
     * @param quantity      补货数量
     * @param userAddressId 用户地址
     * @return
     * @throws Exception
     */
    @RequestMapping("/supplementBOrder.shtml")
    public ModelAndView supplementBOrder(HttpServletRequest request,
                                         @RequestParam(value = "skuId", required = true) Integer skuId,
                                         @RequestParam(value = "quantity", required = true) Integer quantity,
                                         @RequestParam(value = "userAddressId", required = false) Long userAddressId) throws Exception {
        ModelAndView mv = new ModelAndView("platform/order/agent/supplementBOrder");
        ComUser comUser = getComUser(request);

        BorderSupplementParamForAddress paramForAddress = new BorderSupplementParamForAddress();
        paramForAddress.setSkuId(skuId);
        paramForAddress.setQuantity(quantity);
        mv.addObject("supplementOrderParamForAddress", JSONObject.toJSONString(paramForAddress));

        Integer sendType = comUser.getSendType();
        Integer agentLevelId = 0;
        PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), skuId);
        agentLevelId = pfUserSku.getAgentLevelId();
        PfUserCertificate pfUserCertificate = userCertificateService.getCertificateBypfuId(pfUserSku.getId());
        BOrderConfirm bOrderConfirm = new BOrderConfirm();
        bOrderConfirm.setOrderType(BOrderType.agent.getCode());
        bOrderConfirm.setWeiXinId(pfUserCertificate.getWxId());
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
        //货币类型格式化
//        NumberFormat rmb = NumberFormat.getCurrencyInstance(Locale.CHINA);
        //获取用户代理等级
        bOrderConfirm.setAgentLevelId(agentLevelId);
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, agentLevelId);
        bOrderConfirm.setSkuQuantity(quantity);
        BigDecimal unitPrice = pfSkuAgent.getUnitPrice();
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity()));
        bOrderConfirm.setProductTotalPrice(totalPrice.toString());
        BigDecimal highProfit = (comSku.getPriceRetail().subtract(unitPrice))
                .multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity()))
                .setScale(2, BigDecimal.ROUND_DOWN);//最高利润
        bOrderConfirm.setHighProfit(highProfit.toString());
        Integer lowerAgentLevelId = agentLevelId + 1;
        //获取下级代理信息
        PfSkuAgent lowerSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, lowerAgentLevelId);
        BigDecimal lowProfit = BigDecimal.ZERO;
        if (lowerSkuAgent == null) {
            lowProfit = highProfit;
        } else {
            lowProfit = (lowerSkuAgent.getUnitPrice().subtract(pfSkuAgent.getUnitPrice()))
                    .multiply(BigDecimal.valueOf(bOrderConfirm.getSkuQuantity()))
                    .setScale(2, BigDecimal.ROUND_DOWN);//最低利润
        }
        bOrderConfirm.setLowProfit(lowProfit.toString());
        bOrderConfirm.setOrderTotalPrice(totalPrice.toString());
        //获取排单信息
        boolean isQueuing = false;
        Integer count = 0;
        int status = skuService.getSkuStockStatus(skuId, quantity, pfUserSku.getUserPid());
        if (status == 1) {
            isQueuing = true;
            count = bOrderService.selectQueuingOrderCount(skuId);
        }
        mv.addObject("bOrderConfirm", bOrderConfirm);
        mv.addObject("isQueuing", isQueuing);
        mv.addObject("count", count);
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
            bOrderAdd.setOrderType(BOrderType.Supplement.getCode());
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
}
