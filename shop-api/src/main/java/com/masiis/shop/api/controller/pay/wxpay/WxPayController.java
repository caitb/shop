package com.masiis.shop.api.controller.pay.wxpay;

import com.masiis.shop.api.bean.pay.WxPayPrepareBOrderReq;
import com.masiis.shop.api.bean.pay.WxPayPrepareBOrderRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.pay.wxpay.WxPayService;
import com.masiis.shop.api.service.user.WxUserService;
import com.masiis.shop.api.utils.SysSignUtils;
import com.masiis.shop.common.beans.wxpay.UnifiedOrderReq;
import com.masiis.shop.common.beans.wxpay.UnifiedOrderRes;
import com.masiis.shop.common.constant.wx.WxConsAPP;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComWxUser;
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

/**
 * @Date 2016/5/19
 * @Auther lzh
 */
@Controller
@RequestMapping("/wxpay")
public class WxPayController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private WxPayService wxPayService;
    @Resource
    private WxUserService wxUserService;

    @RequestMapping("/preorder")
    @ResponseBody
    @SignValid(paramType = WxPayPrepareBOrderReq.class)
    public WxPayPrepareBOrderRes createPrepareBOrder(HttpServletRequest request,
                            WxPayPrepareBOrderReq req, ComUser user){
        WxPayPrepareBOrderRes res = new WxPayPrepareBOrderRes();
        String orderCode = req.getOrderCode();
        if(StringUtils.isBlank(orderCode)){
            res.setResCode(SysResCodeCons.RES_CODE_WXPAY_ORDERCODE_NULL);
            res.setResMsg(SysResCodeCons.RES_CODE_WXPAY_ORDERCODE_NULL_MSG);
            return res;
        }
        // 查找对应的wxUser
        ComWxUser wxUser = wxUserService.getUserByUnionidAndAppid(user.getWxUnionid(), WxConsAPP.APPID);
        // 创建对应预付单请求参数对象
        UnifiedOrderReq uniOrder = wxPayService.createPrepareBOrderByOrderCode(orderCode, res, wxUser, getIpAddr(request));
        if(uniOrder == null || StringUtils.isBlank(res.getResCode())){
            return res;
        }

        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        // 生成预付订单参数签名
        String resStr = null;
        UnifiedOrderRes resObj = null;
        try {
            uniOrder.setSign(SysSignUtils.toSignString(uniOrder, WxConsAPP.WX_PAY_SIGNKEY));
            // 微信下预付订单,并获取预付订单号
            resStr = HttpClientUtils.httpPost(WxConsPF.WX_PAY_URL_UNIORDER, xStream.toXML(uniOrder));
            log.info("wxpay:下预付单响应成功,response:" + res);

            xStream.ignoreUnknownElements();
            xStream.processAnnotations(UnifiedOrderRes.class);
            resObj = (UnifiedOrderRes) xStream.fromXML(resStr);
            if (resObj == null || StringUtils.isBlank(resObj.getReturn_code())) {
                throw new BusinessException("网络错误");
            }
            if (!"SUCCESS".equals(resObj.getReturn_code())
                    ||!"SUCCESS".equals(resObj.getResult_code())) {
                // 通信错误,如参数格式错误,签名错误
                res.setResCode(SysResCodeCons.RES_CODE_WXPAY_PREORDER_FAIL);
                res.setResMsg(SysResCodeCons.RES_CODE_WXPAY_PREORDER_FAIL_MSG);
                return res;
            }
            // 预付单下单成功
            log.info("wxpay:预付单下单成功");
            // 创建支付记录
            //wxPayService.createPaymentRecord(uniOrder, resObj, orderCode);
        } catch (Exception e) {

        }
        return null;
    }
}
