package com.masiis.shop.web.pay.controller.wxpay;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.beans.pay.wxpay.BrandWCPayReq;
import com.masiis.shop.web.platform.beans.pay.wxpay.UnifiedOrderReq;
import com.masiis.shop.web.platform.beans.pay.wxpay.UnifiedOrderRes;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.common.constant.wx.WxConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.pay.service.wxpay.WxPayService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.utils.HttpsRequest;
import com.masiis.shop.web.platform.utils.WXBeanUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by lzh on 2016/3/9.
 */
@Controller
@RequestMapping("/wxpay")
public class WxPayController extends BaseController{
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private WxPayService wxPayService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/wtpay")
    public String wxpayPage(HttpServletRequest request, String param) {
        String ip = request.getRemoteAddr();
        WxPaySysParamReq req = null;
        ComUser user = null;
        try{
            // 参数校验
            req = checkRequestParma(req, param);
            log.info("参数校验通过");
            // 获取当前登录用户
            user = getComUser(request);
            if(user == null){
                // 未知原因错误
                log.error("未知原因错误");
            }
        } catch (Exception e) {
            log.error("" + e.getMessage());
            return "redirect:/index";
        }

        // 处理业务
        // 组织微信预付订单参数对象,并生成签名
        HttpsRequest h = new HttpsRequest();
        UnifiedOrderReq uniOrder = wxPayService.createUniFiedOrder(req, user, ip);
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        // 生成预付订单参数签名
        String res = null;
        UnifiedOrderRes resObj = null;
        try {
            uniOrder.setSign(WXBeanUtils.toSignString(uniOrder));
            // 微信下预付订单,并获取预付订单号
            res = h.sendPost(WxConstants.WX_PAY_URL_UNIORDER, uniOrder);
            log.info("wxpayPage:下预付单响应成功,response:" + res);

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
            request.setAttribute("url", req.getErrorUrl());
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

    /**
     * 针对请求的参数合法性进行校验
     *
     * @param req
     * @param param
     */
    private WxPaySysParamReq checkRequestParma(WxPaySysParamReq req, String param) {
        if(StringUtils.isBlank(param)){
            // 跳转错误页面,暂跳首页
            throw new BusinessException("参数错误,param为空!");
        }
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
        if(!WXBeanUtils.toSignString(req).equals(req.getSign())){
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
        payReq.setAppId(WxConstants.APPID);
        payReq.setTimeStamp(String.valueOf(new Date().getTime()));
        payReq.setNonceStr(WXBeanUtils.createGenerateStr());
        payReq.setSignType("MD5");
        payReq.setPackages("prepay_id=" + resObj.getPrepay_id());
        payReq.setPaySign(WXBeanUtils.toSignString(payReq));
        return payReq;
    }
}
