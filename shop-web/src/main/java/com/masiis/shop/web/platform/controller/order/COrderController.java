 package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.platform.order.PfCorderPaymentMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.COrderService;
import com.masiis.shop.web.platform.service.product.ProductService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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

    @Resource
    PfCorderPaymentMapper pfCorderPaymentMapper;

    private Logger log = Logger.getLogger(this.getClass());


/*    @RequestMapping(value = "testUpdatePayTime")
    public void testUpdatePayTime(HttpServletRequest request,HttpServletResponse response){

    }*/


    /**
     * 跳转到使用申请成功界面
     *
     * @author hanzengzhi
     * @date 2016/3/7 19:34
     */
    @RequestMapping("/continueStroll")
    public String toContinueStroll(HttpServletRequest request, HttpServletResponse response)throws Exception {
        return "platform/order/jixuguangguang";
    }

    /**
     * 验证商品是否试用过
     *
     * @author hanzengzhi
     * @date 2016/3/9 11:10
     */
    @RequestMapping("/isApplyTrial.do")
    @ResponseBody
    public String isApplyTrial(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "skuId", required = true) Integer skuId) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ComUser comUser = getComUser(request);
            Long userId = null;
            if (comUser != null) {
                userId = comUser.getId();
            } else {
                throw new BusinessException("请重新登录");
            }
            if (StringUtils.isEmpty(userId)) {
                throw new BusinessException("请重新登录");
            }
            if (StringUtils.isEmpty(skuId)) {
                throw new BusinessException("商品不存在");
            }
            List<PfCorder> pfCorders = cOrderService.isApplyTrial(userId, skuId);
            String returnJson = objectMapper.writeValueAsString(pfCorders);
            return returnJson;
        }catch (Exception ex){
            if (org.apache.commons.lang.StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("数据有误请核对", ex);
            }
        }
    }

    /**
     * 跳转到试用申请界面
     *
     * @author hanzengzhi
     * @date 2016/3/5 13:45
     */
    @RequestMapping("/applyTrialToPage.do")
    public String applyTrialToPage(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(value = "skuId", required = true) Integer skuId,
                                   Model model) throws Exception {
        Product productDetails = productService.applyTrialToPageService(skuId);
        String skuImg = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        model.addAttribute("skuName", productDetails.getName());
        if (productDetails.getComSkuImages() != null && productDetails.getComSkuImages().size() > 0) {
            model.addAttribute("skuDefaultImg", skuImg + productDetails.getComSkuImages().get(0).getImgUrl());
            model.addAttribute("skuImgAlt", productDetails.getComSkuImages().get(0).getImgName());
        }
        model.addAttribute("product", productDetails);
        return "platform/order/shiyong";
    }

    /**
     * 试用申请支付
     *
     * @author hanzengzhi
     * @date 2016/3/5 13:46
     */
    @RequestMapping("/trialApplyPay.do")
    public String trialApplyPay(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "skuId", required = true) Integer skuId,
            @RequestParam(value = "addressId", required = true) Long addressId,
            @RequestParam(value = "reason", required = false) String reason,
            RedirectAttributes attrs)throws Exception {
        ComUser comUser = getComUser(request);
        log.info("试用申请---从session里获得comUser---" + comUser.toString());
        Long userId = null;
        if (comUser != null) {
            userId = comUser.getId();
        }
        log.info("试用申请---userId---" + userId);
        log.info("试用申请---skuId---" + skuId);
        if (!StringUtils.isEmpty(reason)){
            reason = new String(reason.getBytes("ISO-8859-1"), "UTF-8");
        }
        WxPaySysParamReq wpspr = cOrderService.trialApplyPay(request, userId, comUser, skuId, addressId, reason);
        attrs.addAttribute("param", JSONObject.toJSONString(wpspr));
        log.info("试用申请-----开始调用微信支付");
        return "redirect:/wxpay/wtpay";
    }

    /**
     * 微信支付成功回调
     *
     * @author hanzengzhi
     * @date 2016/3/17 16:45
     */
    @RequestMapping(value = "/weChatCallBackSuccess.shtml")
    public String weChatCallBackSuccess(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "pfCorderId", required = true) Long pfCorderId,
                                        @RequestParam(value = "skuId", required = true) Integer skuId,
                                        @RequestParam(value = "addressId", required = true) Long addressId,
                                        Model model)throws Exception {
        try {
            model = getOrderInfo(request, model, skuId, addressId);
            model.addAttribute("pfCorder",cOrderService.queryPfCorderById(pfCorderId));
        } catch (Exception e) {
            e.getStackTrace();
        }
        return "platform/order/zhifuchenggong";
    }

    /**
     * 微信支付失败回调
     *
     * @author hanzengzhi
     * @date 2016/3/17 16:45
     */
    @RequestMapping(value = "/weChatCallBackFail.shtml")
    public String weChatCallBackFail(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(value = "pfCorderId", required = true) Long pfCorderId,
                                     @RequestParam(value = "skuId", required = true) Integer skuId,
                                     @RequestParam(value = "addressId", required = true) Long addressId,
                                     Model model)throws Exception {
        model = getOrderInfo(request, model, skuId, addressId);
        model.addAttribute("pfCorder",cOrderService.queryPfCorderById(pfCorderId));
        return "platform/order/zhifushibai";
    }

    /**
     * 确认订单
     *
     * @author hanzengzhi
     * @date 2016/3/8 10:16
     */
    @RequestMapping("/confirmOrder.do")
    public String confirmOrder(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "skuId", required = false) Integer skuId,
                               @RequestParam(value = "selectedAddressId", required = false) Long selectedAddressId,
                               Model model)throws Exception {
        model = getOrderInfo(request, model, skuId, selectedAddressId);
        return "platform/order/zhifushiyong";
    }

    /**
     * 获得试用订单信息
     *
     * @author hanzengzhi
     * @date 2016/3/19 15:06
     */
    private Model getOrderInfo(HttpServletRequest request, Model model, Integer skuId, Long selectedAddressId) {
        ComUser comUser = getComUser(request);
        Long userId = null;
        if (comUser != null) {
            userId = comUser.getId();
        }
        Map<String, Object> pfCorderMap = cOrderService.confirmOrder(request, skuId, userId, selectedAddressId);
        ComUserAddress comUserAddress = (ComUserAddress) pfCorderMap.get("comUserAddress");
        //图片
        Product product = (Product) pfCorderMap.get("product");
        if (product != null) {
            String skuImg = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
            model.addAttribute("skuName", product.getName());
            if (product.getComSkuImages() != null && product.getComSkuImages().size() > 0) {
                model.addAttribute("skuDefaultImg", skuImg + product.getComSkuImages().get(0).getImgUrl());
                model.addAttribute("skuImgAlt", product.getComSkuImages().get(0).getImgName());
            }
        }
        model.addAttribute("product", product);
        model.addAttribute("comUserAddress", comUserAddress);
        return model;
    }
}
