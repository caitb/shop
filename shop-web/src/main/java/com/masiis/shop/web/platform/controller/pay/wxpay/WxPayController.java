package com.masiis.shop.web.platform.controller.pay.wxpay;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.web.platform.beans.pay.wxpay.UnifiedOrderReq;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.COrderService;
import com.masiis.shop.web.platform.service.pay.wxpay.WxPayService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.utils.WXBeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("/wtpay")
    public String wxpayPage(HttpServletRequest request, String param) {
        String ip = getIpAddr(request);
        WxPaySysParamReq req = null;
        try{
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
        } catch (Exception e) {
            log.error("" + e.getMessage());
            return "redirect:/index";
        }


        ComUser user = (ComUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_NAME);

        // 处理业务
        String url = request.getRequestURL().toString();
        // 组织微信预付订单参数对象,并生成签名
        UnifiedOrderReq uniOrder = wxPayService.createUniFiedOrder(req, user, ip);
        // 生成预付订单参数签名

        // 微信下预付订单,并获取预付订单号

        /*////// 检查jsapi_ticket和access_token有效性,次access_token不是微信网页授权access_token

        ////// 组织页面wx.config参数,并形成签名
        发现可以不用这种方式实现*/

        // 组织微信支付请求参数,并形成签名

        // 获取成功支付后的跳转页面url

        return "pay/wxpay/wxpayPage";
    }
}
