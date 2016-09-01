package com.masiis.shop.api.controller.pay.wxpay;

import com.masiis.shop.api.bean.pay.WxPayPrepareBOrderReq;
import com.masiis.shop.api.bean.pay.WxPayPrepareBOrderRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.utils.SysSignUtils;
import com.masiis.shop.common.beans.wx.wxpay.*;
import com.masiis.shop.common.constant.wx.WxConsAPP;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.exceptions.OrderPaidException;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.web.platform.service.wxpay.WxPayService;
import com.masiis.shop.dao.po.ComUser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;

/**
 * @Date 2016/5/19
 * @Auther lzh
 */
@Controller
@RequestMapping("/wxpay")
public class WxPayController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource(name = "bWxPayService")
    private WxPayService wxPayService;

    @RequestMapping("/preorder")
    @ResponseBody
    @SignValid(paramType = WxPayPrepareBOrderReq.class)
    public WxPayPrepareBOrderRes createPrepareBOrder(HttpServletRequest request,
                            WxPayPrepareBOrderReq req, ComUser user){
        WxPayPrepareBOrderRes res = new WxPayPrepareBOrderRes();
        String ip = request.getRemoteAddr();
        // 处理业务
        // 组织微信预付订单参数对象,并生成签名
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        // 生成预付订单参数签名
        UnifiedOrderRes resObj = null;
        String resStr = null;
        try {
            WxPaySysParamReq paramReq = new WxPaySysParamReq();
            paramReq.setOrderId(req.getOrderCode());
            UnifiedOrderReq uniOrder = wxPayService.createUniFiedOrder(paramReq, user, ip,
                    WxConsAPP.WX_PAY_TRADE_TYPE, WxConsAPP.APPID, WxConsAPP.WX_PAY_MCHID,
                    WxConsAPP.WX_PAY_URL_UNIORDER_NOTIFY);
            uniOrder.setSign(SysSignUtils.toSignString(uniOrder, WxConsAPP.WX_PAY_SIGNKEY));
            // 微信下预付订单,并获取预付订单号
            xStream.processAnnotations(UnifiedOrderReq.class);
            resStr = HttpClientUtils.httpPost(WxConsAPP.WX_PAY_URL_UNIORDER, xStream.toXML(uniOrder));
            log.info("wxpayAPP:下预付单响应成功,response:" + res);

            xStream.ignoreUnknownElements();
            xStream.processAnnotations(UnifiedOrderRes.class);
            resObj = (UnifiedOrderRes) xStream.fromXML(resStr);
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
            log.info("wxpayAPP:预付单下单成功");
            // 创建支付记录
            wxPayService.createPaymentRecord(uniOrder, resObj, req.getOrderCode());

            // 创建app支付bean,并做好签名
            WxAPPBrandWCPayReq appReq = createBrandWCPayReq(resObj);
            log.info("组织app端支付请求数据:" + appReq);

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            res.setParamReq(appReq);
        } catch (Exception e) {
            log.error("wxpayAPP:下预付单失败," + e.getMessage(), e);
            if(e instanceof OrderPaidException) {
                res.setResCode(SysResCodeCons.RES_CODE_WXPAY_ORDER_PAID);
                res.setResMsg(SysResCodeCons.RES_CODE_WXPAY_ORDER_PAID_MSG);
            }
        }

        return res;
    }

    /**
     * 创建支付请求对象
     *
     * @param resObj
     * @return
     */
    private WxAPPBrandWCPayReq createBrandWCPayReq(UnifiedOrderRes resObj) {
        // 创建app支付bean,并做好签名
        WxAPPBrandWCPayReq appReq = new WxAPPBrandWCPayReq();

        appReq.setAppid(WxConsAPP.APPID);
        appReq.setNoncestr(SysSignUtils.createGenerateStr());
        appReq.setPackages("Sign=WXPay");
        appReq.setPartnerid(WxConsAPP.WX_PAY_MCHID);
        appReq.setTimestamp(String.valueOf(new Date().getTime()).substring(0, 10));
        appReq.setPrepayid(resObj.getPrepay_id());
        appReq.setSign(SysSignUtils.toSignString(appReq, WxConsAPP.WX_PAY_SIGNKEY));

        return appReq;
    }

    public static void main(String... args) throws UnsupportedEncodingException {
        UnifiedOrderReq uniOrder = new UnifiedOrderReq();
        uniOrder.setAppid(WxConsAPP.APPID);
        uniOrder.setMch_id(WxConsAPP.WX_PAY_MCHID);
        uniOrder.setNonce_str("slkdjfLKJASKLdhsalk");
        uniOrder.setNotify_url(WxConsAPP.WX_PAY_URL_UNIORDER_NOTIFY);
        uniOrder.setBody("麦链合伙人-支付测试");
        // 给外部支付的是系统的支付流水号,自己生成
        uniOrder.setOut_trade_no("LAPP201608111816245L0JKSHKK");
        uniOrder.setTotal_fee("10");
        //uniOrder.setOpenid("o-7uowG9ajvs4A54MUirWiS1z0Lc");
        // PC网页或公众号内支付传"WEB"
        uniOrder.setDevice_info("WEB");
        uniOrder.setSpbill_create_ip("127.0.0.1");
        uniOrder.setTrade_type("APP");
        uniOrder.setSign(SysSignUtils.toSignString(uniOrder, WxConsAPP.WX_PAY_SIGNKEY));
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStream.processAnnotations(UnifiedOrderReq.class);
        String resStr = HttpClientUtils.httpPost(WxConsAPP.WX_PAY_URL_UNIORDER, xStream.toXML(uniOrder));
        System.out.println("wxpay:下预付单响应成功,response:" + resStr);
    }
}
