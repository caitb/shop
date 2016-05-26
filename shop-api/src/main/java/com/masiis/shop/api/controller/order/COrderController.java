 package com.masiis.shop.api.controller.order;

 import com.alibaba.fastjson.JSONObject;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.masiis.shop.api.bean.order.TrialApplyPayOrderDetailRes;
 import com.masiis.shop.api.bean.order.TrialApplyPayReq;
 import com.masiis.shop.api.bean.user.ComUserAddressReq;
 import com.masiis.shop.api.bean.user.ComUserAddressRes;
 import com.masiis.shop.api.constants.SignValid;
 import com.masiis.shop.api.constants.SysConstants;
 import com.masiis.shop.api.constants.SysResCodeCons;
 import com.masiis.shop.api.controller.base.BaseController;
 import com.masiis.shop.api.service.order.COrderService;
 import com.masiis.shop.common.beans.wxpay.WxPaySysParamReq;
 import com.masiis.shop.common.exceptions.BusinessException;
 import com.masiis.shop.common.util.PropertiesUtils;
 import com.masiis.shop.dao.beans.product.Product;
 import com.masiis.shop.dao.platform.order.PfCorderPaymentMapper;
 import com.masiis.shop.dao.po.ComUser;
 import com.masiis.shop.dao.po.ComUserAddress;
 import com.masiis.shop.dao.po.PfCorder;
 import com.masiis.shop.dao.po.PfCorderConsignee;
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
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;

 /**
  * Created by ZhaoLiang on 2016/3/2.
  */
 @Controller
 @RequestMapping("/corder")
 public class COrderController extends BaseController {

     private Logger log = Logger.getLogger(this.getClass());
     @Resource
     private COrderService cOrderService;
     @Resource
     PfCorderPaymentMapper pfCorderPaymentMapper;



     @RequestMapping("/getOrderAddress.do")
     @ResponseBody
     @SignValid(paramType = ComUserAddressReq.class)
    public ComUserAddressRes getOrderAddress(HttpServletRequest request, ComUserAddressReq addressReq,
                                             ComUser comUser ){
         ComUserAddressRes comUserAddressRes = new ComUserAddressRes();
         if (addressReq == null|| comUser == null){
             comUserAddressRes.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
             comUserAddressRes.setResMsg(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID_MSG);
             return comUserAddressRes;
         }else{
             ComUserAddress _address = cOrderService.getOrderAddress(addressReq.getId(),comUser.getId());
             List<ComUserAddress> comUserAddressList = new ArrayList<ComUserAddress>();
             comUserAddressList.add(_address);
             comUserAddressRes.setAddresses(comUserAddressList);
             comUserAddressRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
             comUserAddressRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
         }
         return comUserAddressRes;
    }
     /**
      * 试用申请支付
      *
      * @author hanzengzhi
      * @date 2016/3/5 13:46
      */
     @RequestMapping("/trialApplyPay.do")
     @SignValid(paramType = TrialApplyPayReq.class)
     public String trialApplyPay(HttpServletRequest request,
             TrialApplyPayReq trialApplyPayReq,
             ComUser comUser,
             RedirectAttributes attrs)throws Exception {
         log.info("试用申请---从session里获得comUser---" + comUser.toString());
         Long userId = null;
         if (comUser != null) {
             userId = comUser.getId();
         }
         log.info("试用申请---userId---" + userId);
         log.info("试用申请---skuId---" + trialApplyPayReq.getSkuId());
         if (!StringUtils.isEmpty(trialApplyPayReq.getReason())){
             String reason = new String(trialApplyPayReq.getReason().getBytes("ISO-8859-1"), "UTF-8");
             trialApplyPayReq.setReason(reason);
         }
         log.info("申请理由:--------"+trialApplyPayReq.getReason());
         WxPaySysParamReq wpspr = cOrderService.trialApplyPay(request, userId, comUser, trialApplyPayReq.getSkuId(), trialApplyPayReq.getAddressId(), trialApplyPayReq.getReason());
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
     @RequestMapping(value = "/weChatCallBackSuccess.do")
     @SignValid(paramType = TrialApplyPayReq.class)
     public TrialApplyPayOrderDetailRes weChatCallBackSuccess(HttpServletRequest request, HttpServletResponse response,
                                                              @RequestParam(value = "pfCorderId", required = false) Long pfCorderId){
         TrialApplyPayOrderDetailRes orderDetailRes = new TrialApplyPayOrderDetailRes();
         try {
             Map<String, Object> map = cOrderService.getOrderDetail(pfCorderId);
             orderDetailRes.setCorder((PfCorder) map.get("pfCorder"));
             orderDetailRes.setCorderConsignee((PfCorderConsignee) map.get("corderConsignee"));
             Product product = (Product)map.get("product");
             if (product != null) {
                 String skuImg = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
                 if (product.getComSkuImages() != null && product.getComSkuImages().size() > 0) {
                     orderDetailRes.setSkuDefaultImg(skuImg + product.getComSkuImages().get(0).getImgUrl());
                     orderDetailRes.setSkuImgAlt(product.getComSkuImages().get(0).getImgName());
                 }
             }
             orderDetailRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
             orderDetailRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
         } catch (Exception e) {
             orderDetailRes.setResCode(SysResCodeCons.RES_CODE_TRIAL_APPLY_GET_ORDER_DETAIL_FAIL);
             orderDetailRes.setResMsg(SysResCodeCons.RES_CODE_TRIAL_APPLY_GET_ORDER_DETAIL_FAIL_MSG);
         }
         return orderDetailRes;
     }

     /**
      * 微信支付失败回调 继续支付
      *
      * @author hanzengzhi
      * @date 2016/3/17 16:45
      */
     @RequestMapping(value = "/weChatCallBackFail.shtml")
     @SignValid(paramType = TrialApplyPayReq.class)
     public TrialApplyPayOrderDetailRes weChatCallBackFail(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(value = "pfCorderId", required = false) Long pfCorderId) {
         TrialApplyPayOrderDetailRes orderDetailRes = new TrialApplyPayOrderDetailRes();
         orderDetailRes.setResCode(SysResCodeCons.RES_CODE_TRIAL_APPLY_GET_ORDER_DETAIL_FAIL);
         orderDetailRes.setResMsg(SysResCodeCons.RES_CODE_TRIAL_APPLY_GET_ORDER_DETAIL_FAIL_MSG);
         return orderDetailRes;
     }
 }
