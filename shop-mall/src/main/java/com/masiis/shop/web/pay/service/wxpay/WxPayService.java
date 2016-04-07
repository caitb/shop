package com.masiis.shop.web.pay.service.wxpay;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.beans.pay.wxpay.UnifiedOrderReq;
import com.masiis.shop.web.mall.beans.pay.wxpay.UnifiedOrderRes;
import com.masiis.shop.web.mall.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.mall.constants.WxConstants;
import com.masiis.shop.web.mall.utils.WXBeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lzh on 2016/3/10.
 */
@Service
public class WxPayService {
    private Logger log = Logger.getLogger(this.getClass());

    public UnifiedOrderReq createUniFiedOrder(WxPaySysParamReq req, ComUser user, String ip) {
        UnifiedOrderReq res = null;

        return res;
    }

    @Transactional
    public void createPaymentRecord(UnifiedOrderReq req, UnifiedOrderRes res, String orderid) throws Exception {
    }

    private PfBorderPayment createBorderPayment(UnifiedOrderReq p, UnifiedOrderRes r, PfBorder order) {
        PfBorderPayment payment = new PfBorderPayment();

        payment.setAmount(order.getOrderAmount()); //new BigDecimal(p.getTotal_fee()).divide(new BigDecimal(100)));
        payment.setCreateTime(new Date());
        payment.setIsEnabled(0);
        // 给外部支付使用支付流水号
        payment.setPaySerialNum(p.getOut_trade_no());
        payment.setPayTypeId(0);
        payment.setPayTypeName("微信支付");

        return payment;
    }

    private PfCorderPayment createCorderPayment(UnifiedOrderReq p, UnifiedOrderRes r, PfCorder order) {
        PfCorderPayment payment = new PfCorderPayment();

        payment.setAmount(order.getOrderAmount()); //new BigDecimal(p.getTotal_fee()).divide(new BigDecimal(100)));
        payment.setCreateTime(new Date());
        payment.setIsEnabled(0);
        // 给外部支付使用支付流水号
        payment.setPaySerialNum(p.getOut_trade_no());
        payment.setPayTypeId(0);
        payment.setPayTypeName("微信支付");

        return payment;
    }

}
