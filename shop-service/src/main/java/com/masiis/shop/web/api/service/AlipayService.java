package com.masiis.shop.web.api.service;

import com.masiis.shop.common.beans.tb.alipay.AlipayBizContentPay;
import com.masiis.shop.common.beans.tb.alipay.AlipayTradeAppPayResponse;
import com.masiis.shop.common.constant.tb.AlipayConsAPP;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.platform.order.PfCorderPaymentMapper;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderPayment;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.dao.po.PfCorderPayment;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.platform.service.order.BOrderPayService;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.COrderService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

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
    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;
    @Resource
    private PfCorderPaymentMapper pfCorderPaymentMapper;
    @Resource
    private BOrderPayService payBOrderService;


    public void createPfBorderPaymentByOrderCode(String orderCode, AlipayBizContentPay pay) throws Exception {
        String orderType = String.valueOf(orderCode.charAt(0));
        pay.setProduct_code(AlipayConsAPP.PAY_PRODUCT_CODE);
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

        payment.setAmount(order.getReceivableAmount());
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

        payment.setAmount(order.getReceivableAmount());
        payment.setCreateTime(new Date());
        payment.setIsEnabled(0);
        // 给外部支付使用支付流水号
        payment.setPaySerialNum(paySerialNum);
        payment.setPayTypeId(1);
        payment.setPayTypeName("支付宝支付");

        return payment;
    }

    public void checkPayInfo(AlipayTradeAppPayResponse alipayTradeAppPayResponse) {
        String paySerialNum = alipayTradeAppPayResponse.getOut_trade_no();
        try{
            String orderType = String.valueOf(paySerialNum.charAt(0));
            if(!AlipayConsAPP.APP_ID.equals(alipayTradeAppPayResponse.getApp_id())){
                throw new BusinessException("app_id不正确");
            }
            if(!AlipayConsAPP.PAY_SELLER_ID.equals(alipayTradeAppPayResponse.getSeller_id())){
                throw new BusinessException("seller_id不正确");
            }
            if ("B".equals(orderType)) {
                // B类型订单
                PfBorderPayment payment = pfBorderPaymentMapper.selectBySerialNum(paySerialNum);
                if(payment == null){
                    throw new BusinessException("找不到支付单信息");
                }
                if(payment.getIsEnabled().intValue() == 1){
                    throw new BusinessException("该支付单已支付");
                }
                if(!new BigDecimal(alipayTradeAppPayResponse.getTotal_amount()).equals(payment.getAmount())){
                    throw new BusinessException("支付单金额不正确");
                }
            } else if ("C".equals(orderType)) {
                PfCorderPayment payment = pfCorderPaymentMapper.selectBySerialNum(paySerialNum);
                if(payment == null){
                    throw new BusinessException("找不到支付单信息");
                }
                if(payment.getIsEnabled().intValue() == 1){
                    throw new BusinessException("该支付单已支付");
                }
                if(!new BigDecimal(alipayTradeAppPayResponse.getTotal_amount()).equals(payment.getAmount())){
                    throw new BusinessException("支付单金额不正确");
                }
            } else {
                throw new BusinessException("订单类型不正确!");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    public void handleAlipayNotify(Map<String, String> params) {
        String paySerialNum = params.get("out_trade_no");
        try{
            if(StringUtils.isBlank(paySerialNum)){
                throw new BusinessException("支付单商户订单号为空");
            }
            if(!AlipayConsAPP.APP_ID.equals(params.get("app_id"))){
                throw new BusinessException("app_id不正确");
            }
            if(!AlipayConsAPP.PAY_SELLER_ID.equals(params.get("seller_id"))){
                throw new BusinessException("seller_id不正确");
            }
            String orderType = String.valueOf(paySerialNum.charAt(0));
            if ("B".equals(orderType)) {
                // B类型订单
                PfBorderPayment payment = pfBorderPaymentMapper.selectBySerialNum(paySerialNum);
                if(payment == null){
                    throw new BusinessException("找不到支付单信息");
                }
                if(payment.getIsEnabled().intValue() == 1){
                    log.info("该订单已经支付,支付流水号:" + paySerialNum);
                    throw new BusinessException("该支付单已支付");
                }
                if(!new BigDecimal(params.get("total_amount")).equals(payment.getAmount())){
                    log.info("金额校验不正确,支付流水号:" + paySerialNum + "||金额:" + payment.getAmount());
                    throw new BusinessException("支付单金额不正确");
                }

                log.info("处理订单开始,类型为B,支付流水号为:" + paySerialNum);

                payBOrderService.mainPayBOrder(payment, params.get("trade_no"));
            } else if ("C".equals(orderType)) {
                PfCorderPayment payment = pfCorderPaymentMapper.selectBySerialNum(paySerialNum);
                if(payment == null){
                    throw new BusinessException("找不到支付单信息");
                }
                if(payment.getIsEnabled().intValue() == 1){
                    log.info("该订单已经支付,支付流水号:" + paySerialNum);
                    throw new BusinessException("该支付单已支付");
                }
                if(!new BigDecimal(params.get("total_amount")).equals(payment.getAmount())){
                    log.info("金额校验不正确,支付流水号:" + paySerialNum + "||金额:" + payment.getAmount());
                    throw new BusinessException("支付单金额不正确");
                }

                log.info("处理订单开始,类型为C,支付流水号为:" + paySerialNum);

                cOrderService.payCOrder(payment, params.get("trade_no"));
            } else {
                throw new BusinessException("订单类型不正确!");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }
}

