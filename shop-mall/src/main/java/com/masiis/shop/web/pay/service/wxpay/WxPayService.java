package com.masiis.shop.web.pay.service.wxpay;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.beans.pay.wxpay.UnifiedOrderReq;
import com.masiis.shop.web.mall.beans.pay.wxpay.UnifiedOrderRes;
import com.masiis.shop.web.mall.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.mall.constants.WxConstants;
import com.masiis.shop.web.mall.service.order.SfOrderItemService;
import com.masiis.shop.web.mall.service.order.SfOrderPayService;
import com.masiis.shop.web.mall.service.order.SfOrderPaymentService;
import com.masiis.shop.web.mall.service.order.SfOrderService;
import com.masiis.shop.web.mall.service.user.WxUserService;
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

    @Resource
    private WxUserService wxUserService;
    @Resource
    private SfOrderService sfOrderService;
    @Resource
    private SfOrderItemService itemService;
    @Resource
    private SfOrderPayService orderPayService;

    public UnifiedOrderReq createUniFiedOrder(WxPaySysParamReq req, ComUser user, String ip) {
        UnifiedOrderReq res = null;
        ComWxUser wxUser = wxUserService.getUserByUnionidAndAppid(user.getWxUnionid(), WxConstants.APPID);
        try {
            String orderType = req.getOrderId().charAt(0) + "";
            res = new UnifiedOrderReq();

            res.setAppid(WxConstants.APPID);
            res.setMch_id(WxConstants.WX_PAY_MCHID);
            res.setNonce_str(WXBeanUtils.createGenerateStr());
            res.setNotify_url(WxConstants.WX_PAY_URL_UNIORDER_NOTIFY);
            res.setOpenid(wxUser.getOpenid());
            // PC网页或公众号内支付传"WEB"
            res.setDevice_info("WEB");
            res.setSpbill_create_ip(ip);
            res.setTrade_type("JSAPI");

            if ("S".equals(orderType)) {
                // 代理订单
                SfOrder order = sfOrderService.findByOrderCode(req.getOrderId());
                if (order == null) {
                    throw new BusinessException("订单号错误,不存在该订单号!");
                }
                List<SfOrderItem> orderList = itemService.getSforderItemByOrderId(order.getId());
                StringBuilder body = new StringBuilder();
                for (SfOrderItem item : orderList) {
                    body.append(item.getSkuName()).append(",");
                }
                res.setBody(body.substring(0, body.length() - 1));
                // 给外部支付的是系统的支付流水号,自己生成
                res.setOut_trade_no(SysBeanUtils.createPaySerialNumByOrderType(orderType));
                res.setTotal_fee(order.getReceivableAmount().multiply(new BigDecimal(100)).intValue() + "");
                log.info("订单类型orderType:S");
            } else {
                throw new BusinessException("订单号错误,不存在该订单号!");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e);
        }

        return res;
    }

    @Transactional
    public void createPaymentRecord(UnifiedOrderReq req, UnifiedOrderRes res, String orderid) throws Exception {
        String orderType = String.valueOf(orderid.charAt(0));
        if ("S".equals(orderType)) {
            // B类型订单
            SfOrder order = sfOrderService.findByOrderCode(orderid);
            SfOrderPayment payment = createSfOrderPayment(req, res, order);
            payment.setSfOrderId(order.getId());
            orderPayService.addSfOrderPayment(payment);
        } else {
            throw new BusinessException("订单类型不正确!");
        }

    }

    private SfOrderPayment createSfOrderPayment(UnifiedOrderReq p, UnifiedOrderRes r, SfOrder order) {
        SfOrderPayment payment = new SfOrderPayment();

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
