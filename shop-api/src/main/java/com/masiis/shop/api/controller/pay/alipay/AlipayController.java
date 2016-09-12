package com.masiis.shop.api.controller.pay.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.pay.AlipayPayResultCheckReq;
import com.masiis.shop.api.bean.pay.AlipayPayResultCheckRes;
import com.masiis.shop.api.bean.pay.GetAlipayOrderStrReq;
import com.masiis.shop.api.bean.pay.GetAlipayOrderStrRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.utils.SysSignUtils;
import com.masiis.shop.common.beans.tb.alipay.AlipayBaseReq;
import com.masiis.shop.common.beans.tb.alipay.AlipayBizContentPay;
import com.masiis.shop.common.beans.tb.alipay.AlipayTradeAppPayRes;
import com.masiis.shop.common.beans.tb.alipay.AlipayTradeAppPayResponse;
import com.masiis.shop.common.constant.tb.AlipayConsAPP;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.api.service.AlipayService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
            alipayBaseReq.setApp_id(AlipayConsAPP.APP_ID);
            alipayBaseReq.setMethod("alipay.trade.app.pay");
            alipayBaseReq.setCharset(AlipayConsAPP.CHARSET);
            alipayBaseReq.setSign_type(AlipayConsAPP.SIGN_TYPE);
            alipayBaseReq.setTimestamp(DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
            alipayBaseReq.setVersion(AlipayConsAPP.PAY_VERSION);
            alipayBaseReq.setNotify_url(AlipayConsAPP.PAY_NOTIFY_URL);
            alipayBaseReq.setBiz_content(JSONObject.toJSONString(pay));
            alipayBaseReq.setSign(SysSignUtils.toSignString(alipayBaseReq, null));

            String signContent = SysSignUtils.getSignContent(alipayBaseReq);

            String sign = AlipaySignature.rsaSign(signContent, AlipayConsAPP.APP_PRIVATE_KEY_PKCS8,
                    AlipayConsAPP.CHARSET);

            alipayBaseReq.setSign(sign);

            String orderStr = SysSignUtils.getEncodeContent(alipayBaseReq);

            res.setOrderStr(orderStr);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }


    @RequestMapping("/paycheck")
    @ResponseBody
    @SignValid(paramType = AlipayPayResultCheckReq.class)
    public AlipayPayResultCheckRes checkAlipaySyncResultInfo(HttpServletRequest request,
                                                             AlipayPayResultCheckReq req, ComUser user){
        AlipayPayResultCheckRes res = new AlipayPayResultCheckRes();
        try {
            // 支付结果校验
            if(StringUtils.isBlank(req.getResultStatus())){
                res.setResCode(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_PARAMERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_PARAMERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_PARAMERROR_MSG);
            }
            // 用户取消
            if(AlipayConsAPP.PAY_RESULT_STATUS_CANCEL.equals(req.getResultStatus())){
                res.setResCode(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_USERCANCEL);
                res.setResMsg(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_USERCANCEL_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_USERCANCEL_MSG);
            }

            if(!AlipayConsAPP.PAY_RESULT_STATUS_SUCCESS.equals(req.getResultStatus())){
                res.setResCode(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_RESULT_FAIL);
                res.setResMsg(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_RESULT_FAIL_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_RESULT_FAIL_MSG);
            }
            AlipayTradeAppPayRes alipayTradeAppPayRes = null;
            AlipayTradeAppPayResponse alipayTradeAppPayResponse = null;
            try{
                // 结果json解析
                alipayTradeAppPayRes = JSONObject.parseObject(req.getResult(), AlipayTradeAppPayRes.class);
                alipayTradeAppPayResponse = JSONObject.parseObject(alipayTradeAppPayRes.getAlipay_trade_app_pay_response(),
                        AlipayTradeAppPayResponse.class);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                res.setResCode(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_PARAMERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_PARAMERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_PARAMERROR_MSG);
            }

            // 签名信息简单校验
            if(StringUtils.isBlank(alipayTradeAppPayRes.getSign())
                    || StringUtils.isBlank(alipayTradeAppPayRes.getSign_type())
                    || !AlipayConsAPP.SIGN_TYPE.equals(alipayTradeAppPayRes.getSign_type())){
                res.setResCode(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_SIGNINFOERROR);
                res.setResCode(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_SIGNINFOERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_SIGNINFOERROR_MSG);
            }

            // 签名加密校验
            boolean signCheck = AlipaySignature.rsaCheck(
                    getJSONStructStringByKey(req.getResult(), "alipay_trade_app_pay_response"),
                    getStringValueByKey(req.getResult(), "sign"),
                    AlipayConsAPP.ALIPAY_PUBLIC_KEY,
                    AlipayConsAPP.CHARSET,
                    alipayTradeAppPayRes.getSign_type());
            if(!signCheck){
                res.setResCode(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_SIGNERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_SIGNERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_SIGNERROR_MSG);
            }

            // 支付结果数据校验
            try{
                alipayService.checkPayInfo(alipayTradeAppPayResponse);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                res.setResCode(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_PAYINFOERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_PAYINFOERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_ALIPAY_PAYCHECK_PAYINFOERROR_MSG);
            }

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }


    @RequestMapping("/notify")
    @SignValid(paramType = BaseReq.class, isSysApi = false)
    public void alipayAsyncNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> reqMap = request.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>();
        String res = "failure";
        try{
            for(Map.Entry<String, String[]> entry:reqMap.entrySet()){
                paramsMap.put(entry.getKey(), entry.getValue()[0]);
            }

            log.info("支付宝支付异步通知:" + paramsMap);

            // 校验签名
            boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap,
                    AlipayConsAPP.ALIPAY_PUBLIC_KEY, AlipayConsAPP.CHARSET);
            if(!signVerified){
                throw new BusinessException("支付异步回调签名错误");
            }

            log.info("校验签名成功");

            //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
            try{
                synchronized (this) {
                    // 放到service中处理
                    alipayService.handleAlipayNotify(paramsMap);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new BusinessException(e);
            }

            // 处理完成
            res = "success";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = "failure";
        }

        response.getOutputStream().write(res.getBytes("UTF-8"));
    }

    private static String getJSONStructStringByKey(String jsonStr, String point){
        String[] str1Arr = jsonStr.split(point);
        if(str1Arr.length < 2){
            throw new BusinessException("parse pointed key error");
        }
        String str1 = str1Arr[1];
        Integer indexStart = 0;
        Integer indexEnd = 0;
        int z = 0;
        boolean flag = false;
        boolean isError = false;
        for(int i = 0; i < str1.length(); i++){
            if("{".equals(str1.charAt(i) + "") && !flag){
                flag = true;
                indexStart = i;
                z++;
            }
            if("}".equals(str1.charAt(i) + "")){
                z--;
            }
            if(z == 0 && flag){
                indexEnd = i;
                break;
            }
            if(i == str1.length() - 1){
                isError = true;
            }
        }
        if(isError){
            throw new BusinessException("parse pointed key error");
        }

        String res = str1.substring(indexStart, indexEnd + 1).replaceAll("\\\\\"", "\"");

        return res;
    }

    private static String getStringValueByKey(String jsonStr, String point){
        String[] str1Arr = jsonStr.split(point);
        if(str1Arr.length < 2){
            throw new BusinessException("parse pointed key error");
        }
        String str1 = str1Arr[1];
        Integer indexStart = 0;
        Integer indexEnd = 0;
        int z = 0;
        boolean flag = false;
        boolean isError = false;
        for(int i = 0; i < str1.length(); i++){
            if(":".equals(str1.charAt(i) + "") && !flag){
                flag = true;
                indexStart = i + 1;
                z++;
            }
            if(",".equals(str1.charAt(i) + "")){
                z--;
            }
            if(z == 0 && flag){
                indexEnd = i;
                break;
            }
            if(i == str1.length() - 1){
                isError = true;
            }
        }
        if(isError){
            throw new BusinessException("parse pointed key error");
        }

        String res = str1.substring(indexStart, indexEnd);
        res = res.replaceAll("\\\\\"", "");
        res = res.replaceAll("\\\"", "").replaceAll("\"", "").replaceAll(" ", "+");

        return res;
    }

    public static void main(String... args) throws Exception {
        String str = "{\\\"alipay_trade_app_pay_response\\\":{\\\"code\\\":\\\"10000\\\",\\\"msg\\\":\\\"Success\\\",\\\"app_id\\\":\\\"2016042701341751\\\",\\\"auth_app_id\\\":\\\"2016042701341751\\\",\\\"charset\\\":\\\"UTF-8\\\",\\\"timestamp\\\":\\\"2016-09-06 09:51:45\\\",\\\"total_amount\\\":\\\"0.01\\\",\\\"trade_no\\\":\\\"2016090621001004950222498354\\\",\\\"seller_id\\\":\\\"2088221617968217\\\",\\\"out_trade_no\\\":\\\"BLSH20160906095139127ZW01KYNCK7G\\\"},\\\"sign\\\":\\\"obHOhd3d2Fc31X+nr7bW\\/r2updbILj3OkLnDpc4vrb2jOoky05bhfiGIPoYdKEu1iWxlnOmdXstv3MDlKx2cQwDzxoqTTg3lKNs3Ga9w6hdwLNWDNVuMq4jNWKKZvrp0u\\/3meR2HF7LcgvRZH3WFLBg5+Ekcp3C\\/G5LjUcYGGpc=\\\",\\\"sign_type\\\":\\\"RSA\\\"}";
        String content = getJSONStructStringByKey(str, "alipay_trade_app_pay_response");
        //AlipayTradeAppPayRes alipayTradeAppPayRes = JSONObject.parseObject(str.replaceAll("\\\"", "\""), AlipayTradeAppPayRes.class);
        String sign = getStringValueByKey(str, "sign");
        System.out.println(content);
        System.out.println(sign);
        boolean result = AlipaySignature.rsaCheck(content,
                sign,
                AlipayConsAPP.ALIPAY_PUBLIC_KEY, AlipayConsAPP.CHARSET, "RSA");
        System.out.println(result);
    }
}
