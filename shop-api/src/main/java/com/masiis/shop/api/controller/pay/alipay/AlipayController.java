package com.masiis.shop.api.controller.pay.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.pay.GetAlipayOrderStrReq;
import com.masiis.shop.api.bean.pay.GetAlipayOrderStrRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.utils.SysSignUtils;
import com.masiis.shop.common.beans.tb.alipay.AlipayBaseReq;
import com.masiis.shop.common.beans.tb.alipay.AlipayBizContentPay;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.AlipayPropertiesUtils;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.web.api.service.AlipayService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Date 2016/5/19
 * @Auther lzh
 */
@Controller
@RequestMapping("/alipay")
public class AlipayController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource(name = "appAlipayService")
    private AlipayService alipayService;

    @RequestMapping("/getOrderStr")
    @ResponseBody
    @SignValid(paramType = GetAlipayOrderStrReq.class)
    public GetAlipayOrderStrRes getAlipayOrderStr(HttpServletRequest request, GetAlipayOrderStrReq req, ComUser user){
        GetAlipayOrderStrRes res = new GetAlipayOrderStrRes();
        AlipayBizContentPay pay = new AlipayBizContentPay();
        try {
            String orderCode = req.getOrderCode();
            if(StringUtils.isBlank(orderCode)){
                res.setResCode(SysResCodeCons.RES_CODE_ALIPAY_ORDERCODE_NULL);
                res.setResMsg(SysResCodeCons.RES_CODE_ALIPAY_ORDERCODE_NULL_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_ALIPAY_ORDERCODE_NULL_MSG);
            }
            try {
                alipayService.createPfBorderPaymentByOrderCode(orderCode, pay);
            } catch (Exception e) {
                res.setResCode(SysResCodeCons.RES_CODE_ALIPAY_ORDERCODE_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_ALIPAY_ORDERCODE_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_ALIPAY_ORDERCODE_ERROR_MSG);
            }

            AlipayBaseReq alipayBaseReq = new AlipayBaseReq();
            alipayBaseReq.setApp_id(AlipayPropertiesUtils.getStringValue("alipay.conf.APP_ID"));
            alipayBaseReq.setMethod("alipay.trade.app.pay");
            alipayBaseReq.setCharset(AlipayPropertiesUtils.getStringValue("alipay.conf.CHARSET"));
            alipayBaseReq.setSign_type(AlipayPropertiesUtils.getStringValue("alipay.conf.SIGN_TYPE"));
            alipayBaseReq.setTimestamp(DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
            alipayBaseReq.setVersion(AlipayPropertiesUtils.getStringValue("alipay.conf.PAY_VERSION"));
            alipayBaseReq.setNotify_url(AlipayPropertiesUtils.getStringValue("alipay.conf.PAY_NOTIFY_URL"));
            alipayBaseReq.setBiz_content(JSONObject.toJSONString(pay));
            alipayBaseReq.setSign(SysSignUtils.toSignString(alipayBaseReq, null));


        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }

    public static void main(String... args) {
        AlipayBaseReq alipayBaseReq = new AlipayBaseReq();
        AlipayBizContentPay pay = new AlipayBizContentPay();
        pay.setSubject("麦链合伙人");
        pay.setOut_trade_no("BLSH2016908oisdofksdjoiwjk");
        pay.setTotal_amount(new BigDecimal("10.80").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        pay.setProduct_code(AlipayPropertiesUtils.getStringValue("alipay.conf.PAY_PRODUCT_CODE"));
        alipayBaseReq.setApp_id(AlipayPropertiesUtils.getStringValue("alipay.conf.APP_ID"));
        alipayBaseReq.setMethod("alipay.trade.app.pay");
        alipayBaseReq.setCharset(AlipayPropertiesUtils.getStringValue("alipay.conf.CHARSET"));
        alipayBaseReq.setSign_type(AlipayPropertiesUtils.getStringValue("alipay.conf.SIGN_TYPE"));
        alipayBaseReq.setTimestamp(DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        alipayBaseReq.setVersion(AlipayPropertiesUtils.getStringValue("alipay.conf.PAY_VERSION"));
        alipayBaseReq.setNotify_url(AlipayPropertiesUtils.getStringValue("alipay.conf.PAY_NOTIFY_URL"));
        alipayBaseReq.setBiz_content(JSONObject.toJSONString(pay));

        String signContent = SysSignUtils.getSignContent(alipayBaseReq);
        System.out.println(signContent);

        String sign = null;
        try {
            sign = AlipaySignature.rsaSign(signContent,
                    AlipayPropertiesUtils.getStringValue("alipay.conf.APP_PRIVATE_KEY_PKCS8"),
                    AlipayPropertiesUtils.getStringValue("alipay.conf.CHARSET"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(sign);
        alipayBaseReq.setSign(sign);

        System.out.println(SysSignUtils.getEncodeContent(alipayBaseReq));
    }
}
