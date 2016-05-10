package com.masiis.shop.web.pay.service.wxpay;

import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.exceptions.OrderPaidException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.beans.pay.wxpay.UnifiedOrderReq;
import com.masiis.shop.web.platform.beans.pay.wxpay.UnifiedOrderRes;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.COrderService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.WxUserService;
import com.masiis.shop.web.platform.utils.WXBeanUtils;
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

    @Resource
    private BOrderService bOrderService;
    @Resource
    private COrderService cOrderService;
    @Resource
    private SkuService skuService;
    @Resource
    private WxUserService wxUserService;

    public UnifiedOrderReq createUniFiedOrder(WxPaySysParamReq req, ComUser user, String ip) throws OrderPaidException {
        UnifiedOrderReq res = null;
        ComWxUser wxUser = wxUserService.getUserByUnionidAndAppid(user.getWxUnionid(), WxConsPF.APPID);
        try {
            String orderType = req.getOrderId().charAt(0) + "";
            res = new UnifiedOrderReq();

            res.setAppid(WxConsPF.APPID);
            res.setMch_id(WxConsPF.WX_PAY_MCHID);
            res.setNonce_str(WXBeanUtils.createGenerateStr());
            res.setNotify_url(WxConsPF.WX_PAY_URL_UNIORDER_NOTIFY);
            res.setOpenid(wxUser.getOpenid());
            // PC网页或公众号内支付传"WEB"
            res.setDevice_info("WEB");
            res.setSpbill_create_ip(ip);
            res.setTrade_type("JSAPI");

            if ("B".equals(orderType)) {
                // 代理订单
                PfBorder order = bOrderService.findByOrderCode(req.getOrderId());
                if (order == null) {
                    throw new BusinessException("订单号错误,不存在该订单号!");
                }
                if(order.getOrderStatus().intValue() != BOrderStatus.offLineNoPay.getCode().intValue()
                        && order.getOrderStatus().intValue() != BOrderStatus.NotPaid.getCode().intValue()){
                    if(order.getPayStatus().intValue() == 1){
                        throw new OrderPaidException("该订单已支付,无需再支付");
                    }
                    throw new BusinessException("订单状态错误，不是可支付状态!");
                }
                List<PfBorderItem> orderList = bOrderService.getPfBorderItemByOrderId(order.getId());
                StringBuilder body = new StringBuilder();
                for (PfBorderItem item : orderList) {
                    body.append(item.getSkuName()).append(",");
                }
                res.setBody(body.substring(0, body.length() - 1));
                // 给外部支付的是系统的支付流水号,自己生成
                res.setOut_trade_no(SysBeanUtils.createPaySerialNumByOrderType(orderType));
                res.setTotal_fee(order.getReceivableAmount().multiply(new BigDecimal(100)).intValue() + "");
                log.info("订单类型orderType:B");
            } else if ("C".equals(orderType)) {
                log.info("订单类型为C,订单编号为:" + req.getOrderId());
                // 使用订单
                PfCorder order = cOrderService.findByOrderCode(req.getOrderId());
                if (order == null) {
                    throw new BusinessException("订单号错误,不存在该订单号!");
                }
                if(order.getOrderStatus().intValue() != 0){
                    throw new BusinessException("订单状态错误，不是可支付状态!");
                }
                ComSku sku = skuService.getSkuById(order.getSkuId());
                if(sku == null){
                    throw new BusinessException("商品对象s为空,skuid为:" + order.getSkuId());
                }
                res.setBody(sku.getName());
                res.setOut_trade_no(SysBeanUtils.createPaySerialNumByOrderType(orderType));
                res.setTotal_fee(order.getReceivableAmount().multiply(new BigDecimal(100)).intValue() + ""); //res.setTotal_fee("1");
                log.info("订单类型orderType:B");
            } else {
                throw new BusinessException("订单号错误,不存在该订单号!");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            if(e instanceof OrderPaidException){
                throw new OrderPaidException(e);
            }else {
                throw new BusinessException(e);
            }
        }

        return res;
    }

    @Transactional
    public void createPaymentRecord(UnifiedOrderReq req, UnifiedOrderRes res, String orderid) throws Exception {
        String orderType = String.valueOf(orderid.charAt(0));
        if ("B".equals(orderType)) {
            // B类型订单
            PfBorder order = bOrderService.findByOrderCode(orderid);
            PfBorderPayment payment = createBorderPayment(req, res, order);
            payment.setPfBorderId(order.getId());
            bOrderService.addBOrderPayment(payment);
        } else if ("C".equals(orderType)) {
            PfCorder order = cOrderService.findByOrderCode(orderid);
            PfCorderPayment payment = createCorderPayment(req, res, order);
            payment.setPfCorderId(order.getId());
            cOrderService.addCOrderPayment(payment);
        } else {
            throw new BusinessException("订单类型不正确!");
        }
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
