package com.masiis.shop.web.api.service;

import com.masiis.shop.common.beans.tb.alipay.AlipayBizContentPay;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.AlipayPropertiesUtils;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderPayment;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.dao.po.PfCorderPayment;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.COrderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付宝支付业务类
 *
 * @Date 2016/9/2
 * @Author lzh
 */
@Service("appAlipayService")
public class AlipayService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private BOrderService bOrderService;
    @Resource
    private COrderService cOrderService;
    @Resource
    private SkuService skuService;

    public void createPfBorderPaymentByOrderCode(String orderCode, AlipayBizContentPay pay) throws Exception {
        String orderType = String.valueOf(orderCode.charAt(0));
        pay.setProduct_code(AlipayPropertiesUtils.getStringValue("alipay.conf.PAY_PRODUCT_CODE"));
        if ("B".equals(orderType)) {
            // B类型订单
            PfBorder order = bOrderService.findByOrderCode(orderCode);
            if(order == null){
                throw new BusinessException("订单号不存在");
            }
            PfBorderPayment payment = createBorderPayment(order, SysBeanUtils.createPaySerialNumByOrderType("B"));
            payment.setPfBorderId(order.getId());
            bOrderService.addBOrderPayment(payment);
            pay.setSubject("麦链合伙人" + BOrderType.getByCode(order.getOrderType()).getDesc());
            pay.setOut_trade_no(payment.getPaySerialNum());
            pay.setTotal_amount(order.getReceivableAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        } else if ("C".equals(orderType)) {
            PfCorder order = cOrderService.findByOrderCode(orderCode);
            if(order == null){
                throw new BusinessException("订单号不存在");
            }
            PfCorderPayment payment = createCorderPayment(order, SysBeanUtils.createPaySerialNumByOrderType("C"));
            payment.setPfCorderId(order.getId());
            cOrderService.addCOrderPayment(payment);
            pay.setSubject("麦链合伙人试用订单");
            pay.setOut_trade_no(payment.getPaySerialNum());
            pay.setTotal_amount(order.getReceivableAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        } else {
            throw new BusinessException("订单类型不正确!");
        }
    }

    private PfBorderPayment createBorderPayment(PfBorder order, String paySerialNum) {
        PfBorderPayment payment = new PfBorderPayment();

        payment.setAmount(order.getOrderAmount());
        payment.setCreateTime(new Date());
        payment.setIsEnabled(0);
        // 给外部支付使用支付流水号
        payment.setPaySerialNum(paySerialNum);
        payment.setPayTypeId(1);
        payment.setPayTypeName("支付宝支付");

        return payment;
    }

    private PfCorderPayment createCorderPayment(PfCorder order, String paySerialNum) {
        PfCorderPayment payment = new PfCorderPayment();

        payment.setAmount(order.getOrderAmount());
        payment.setCreateTime(new Date());
        payment.setIsEnabled(0);
        // 给外部支付使用支付流水号
        payment.setPaySerialNum(paySerialNum);
        payment.setPayTypeId(1);
        payment.setPayTypeName("支付宝支付");

        return payment;
    }
}

