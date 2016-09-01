package com.masiis.shop.web.platform.controller.wxpay;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.exceptions.OrderPaidException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.common.beans.wx.wxpay.BrandWCPayReq;
import com.masiis.shop.common.beans.wx.wxpay.UnifiedOrderReq;
import com.masiis.shop.common.beans.wx.wxpay.UnifiedOrderRes;
import com.masiis.shop.common.beans.wx.wxpay.WxPaySysParamReq;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.wxpay.WxPayService;
import com.masiis.shop.web.platform.utils.HttpsRequest;
import com.masiis.shop.web.common.utils.wx.WxPFBeanUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * Created by lzh on 2016/3/9.
 */
@Controller
@RequestMapping("/wxpay")
public class WxPayController extends BaseController{
    private Logger log = Logger.getLogger(this.getClass());

    @Resource(name = "bWxPayService")
    private WxPayService wxPayService;

    @RequestMapping(value = "/wtpay")
    public String wxpayPage(HttpServletRequest request, String param) {
        String ip = request.getRemoteAddr();
        WxPaySysParamReq req = null;
        ComUser user = null;
        try{
            // 参数校验
            req = checkRequestParma(req, param);
            log.info("wxpayPage:参数校验通过");
            // 获取当前登录用户
            user = getComUser(request);
            if(user == null){
                // 未知原因错误
                log.error("wxpayPage:未知原因错误");
            }
        } catch (Exception e) {
            log.error("" + e.getMessage());
            return "redirect:/index";
        }

        // 处理业务
        // 组织微信预付订单参数对象,并生成签名
        HttpsRequest h = new HttpsRequest();
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        // 生成预付订单参数签名
        String res = null;
        UnifiedOrderRes resObj = null;
        try {
            UnifiedOrderReq uniOrder = wxPayService.createUniFiedOrder(req, user, ip,
                    WxConsPF.WX_PAY_TRADE_TYPE, WxConsPF.APPID, WxConsPF.WX_PAY_MCHID,
                    WxConsPF.WX_PAY_URL_UNIORDER_NOTIFY);
            uniOrder.setSign(WxPFBeanUtils.toSignString(uniOrder));
            // 微信下预付订单,并获取预付订单号
            res = h.sendPost(WxConsPF.WX_PAY_URL_UNIORDER, uniOrder);
            log.info("wxpayPage:下预付单响应成功,response:" + res);

            xStream.ignoreUnknownElements();
            xStream.processAnnotations(UnifiedOrderRes.class);
            resObj = (UnifiedOrderRes) xStream.fromXML(res);
            if(resObj == null || StringUtils.isBlank(resObj.getReturn_code())){
                throw new BusinessException("网络错误");
            }
            if(!"SUCCESS".equals(resObj.getReturn_code())){
                // 通信错误,如参数格式错误,签名错误
                throw new BusinessException(resObj.getReturn_msg());
            }
            if(!"SUCCESS".equals(resObj.getResult_code())){
                // 业务错误,可以做一些操作
                throw new BusinessException(resObj.getErr_code_des());
            }
            // 预付单下单成功
            log.info("wxpayPage:预付单下单成功");
            // 创建支付记录
            wxPayService.createPaymentRecord(uniOrder, resObj, req.getOrderId());
        } catch (Exception e) {
            log.error("wxpayPage:下预付单失败," + e.getMessage(), e);
            if(e instanceof OrderPaidException) {
                request.setAttribute("url", "http://m.qc.iimai.com/");
            } else {
                request.setAttribute("url", req.getErrorUrl());
            }
            // 预付单下单失败处理
            return "pay/wxpay/wx_redirect_page";
        }

        // 组织微信支付请求参数,并形成签名
        BrandWCPayReq payReq = createBrandWCPayReq(resObj);

        // 获取成功支付后的跳转页面url
        request.setAttribute("req", payReq);
        request.setAttribute("successUrl", req.getSuccessUrl());
        request.setAttribute("cancelUrl", req.getCancelUrl());
        request.setAttribute("errUrl", req.getErrorUrl());
        /*////// 检查jsapi_ticket和access_token有效性,次access_token不是微信网页授权access_token
        ////// 组织页面wx.config参数,并形成签名
        发现可以不用这种方式实现*/
        return "pay/wxpay/wxpayPage";
    }


    @RequestMapping(value = "/aspay", method = RequestMethod.POST)
    @ResponseBody
    public String wxpayJS(HttpServletRequest request, String param) {
        String ip = request.getRemoteAddr();
        WxPaySysParamReq req = null;
        ComUser user = null;
        JSONObject sysRes = null;
        System.out.println("param:" + param);
        try{
            // 参数校验
            req = checkRequestParma(req, param);
            log.info("wxpayJs:参数校验通过");
            // 获取当前登录用户
            user = getComUser(request);
            if(user == null){
                // 未知原因错误
                log.error("wxpayJs:未知原因错误");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "{\"resMsg\":\"请求错误\"}";
        }

        // 处理业务
        // 组织微信预付订单参数对象,并生成签名
        HttpsRequest h = new HttpsRequest();
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        // 生成预付订单参数签名
        String res = null;
        UnifiedOrderRes resObj = null;
        try {
            UnifiedOrderReq uniOrder = wxPayService.createUniFiedOrder(req, user, ip,
                    WxConsPF.WX_PAY_TRADE_TYPE, WxConsPF.APPID, WxConsPF.WX_PAY_MCHID,
                    WxConsPF.WX_PAY_URL_UNIORDER_NOTIFY);
            uniOrder.setSign(WxPFBeanUtils.toSignString(uniOrder));
            // 微信下预付订单,并获取预付订单号
            res = h.sendPost(WxConsPF.WX_PAY_URL_UNIORDER, uniOrder);
            log.info("wxpayJs:下预付单响应成功,response:" + res);

            xStream.ignoreUnknownElements();
            xStream.processAnnotations(UnifiedOrderRes.class);
            resObj = (UnifiedOrderRes) xStream.fromXML(res);
            if(resObj == null || StringUtils.isBlank(resObj.getReturn_code())){
                throw new BusinessException("网络错误");
            }
            if(!"SUCCESS".equals(resObj.getReturn_code())){
                // 通信错误,如参数格式错误,签名错误
                throw new BusinessException(resObj.getReturn_msg());
            }
            if(!"SUCCESS".equals(resObj.getResult_code())){
                // 业务错误,可以做一些操作
                throw new BusinessException(resObj.getErr_code_des());
            }
            // 预付单下单成功
            log.info("wxpayJs:预付单下单成功");
            // 创建支付记录
            wxPayService.createPaymentRecord(uniOrder, resObj, req.getOrderId());
        } catch (Exception e) {
            log.error("wxpayJs:下预付单失败," + e.getMessage(), e);
            if(e instanceof OrderPaidException) {
                return "{\"resCode\":1, \"resMsg\":\"该订单已支付,无需再支付\"}";
            } else {
                // 预付单下单失败处理
                return "{\"resCode\":2, \"resMsg\":\"请求错误\"}";
            }
        }

        // 组织微信支付请求参数,并形成签名
        BrandWCPayReq payReq = createBrandWCPayReq(resObj);

        // 获取成功支付后的跳转页面url
        sysRes = (JSONObject) JSONObject.toJSON(payReq);
        sysRes.put("successUrl", req.getSuccessUrl());
        sysRes.put("cancelUrl", req.getCancelUrl());
        sysRes.put("errUrl", req.getErrorUrl());
        /*////// 检查jsapi_ticket和access_token有效性,次access_token不是微信网页授权access_token
        ////// 组织页面wx.config参数,并形成签名
        发现可以不用这种方式实现*/
        return sysRes.toJSONString();
    }



    /**
     * 针对请求的参数合法性进行校验
     *
     * @param req
     * @param param
     */
    private WxPaySysParamReq checkRequestParma(WxPaySysParamReq req, String param) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(param)){
            // 跳转错误页面,暂跳首页
            throw new BusinessException("参数错误,param为空!");
        }
        param = URLDecoder.decode(param, "UTF-8");
        req = JSONObject.parseObject(param, WxPaySysParamReq.class);

        if(req == null
                || StringUtils.isBlank(req.getOrderId())
                || StringUtils.isBlank(req.getSign())
                || StringUtils.isBlank(req.getNonceStr())
                || StringUtils.isBlank(req.getSignType())){
            // 参数有空值
            // 跳转错误页面,暂跳首页
            throw new BusinessException("参数错误,有部分参数为空!");
        }
        if(!WxPFBeanUtils.toSignString(req).equals(req.getSign())){
            throw new BusinessException("签名错误");
        }

        return req;
    }

    /**
     * 创建支付请求对象
     *
     * @param resObj
     * @return
     */
    private BrandWCPayReq createBrandWCPayReq(UnifiedOrderRes resObj) {
        BrandWCPayReq payReq = new BrandWCPayReq();
        payReq.setAppId(WxConsPF.APPID);
        payReq.setTimeStamp(String.valueOf(new Date().getTime()).substring(0, 10));
        payReq.setNonceStr(WxPFBeanUtils.createGenerateStr());
        payReq.setSignType("MD5");
        payReq.setPackages("prepay_id=" + resObj.getPrepay_id());
        payReq.setPaySign(WxPFBeanUtils.toSignString(payReq));
        return payReq;
    }

}
