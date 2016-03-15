package com.masiis.shop.web.platform.service.pay.wxpay;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.beans.pay.wxpay.UnifiedOrderReq;
import com.masiis.shop.web.platform.beans.pay.wxpay.UnifiedOrderRes;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.platform.constants.WxConstants;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.COrderService;
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
    private PfBorderPaymentMapper bPaymentMapper;

    public UnifiedOrderReq createUniFiedOrder(WxPaySysParamReq req, ComUser user, String ip) {
        try{
            String orderType = req.getOrderId().charAt(0) + "";
            UnifiedOrderReq res = new UnifiedOrderReq();

            res.setAppid(WxConstants.APPID);
            res.setMch_id(WxConstants.WX_PAY_MCHID);
            res.setNonce_str(WXBeanUtils.createGenerateStr());
            res.setNotify_url(WxConstants.WX_PAY_URL_UNIORDER_NOTIFY);
            res.setOpenid(user.getOpenid());
            res.setSpbill_create_ip(ip);
            res.setTrade_type("JSAPI");

            if("B".equalsIgnoreCase(orderType)){
                // 代理订单
                PfBorder order = bOrderService.findByOrderCode(req.getOrderId());
                if(order == null){
                    throw new BusinessException("订单号错误,不存在该订单号!");
                }
                List<PfBorderItem> orderList = bOrderService.getPfBorderItemByOrderId(order.getId());
                StringBuilder body = new StringBuilder();
                for(PfBorderItem item:orderList){
                    body.append(item.getSkuName()).append(",");
                }
                res.setBody(body.substring(0, body.length() - 1));
                res.setOut_trade_no(order.getOrderCode());
                res.setTotal_fee("1");
                log.info("订单类型orderType:B");
            } else {
                throw new BusinessException("订单号错误,不存在该订单号!");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e);
        }

        return null;
    }

    @Transactional
    public void createPaymentRecord(UnifiedOrderReq req, UnifiedOrderRes res){
        String orderid = req.getOut_trade_no();
        String orderType = String.valueOf(orderid.charAt(0));
        if("B".equals(orderType)){
            // B类型订单
            PfBorderPayment payment = createBorderPayment(req, res);
            bPaymentMapper.insert(payment);
        } else {
            throw new BusinessException("订单类型不正确!");
        }
    }

    private PfBorderPayment createBorderPayment(UnifiedOrderReq p, UnifiedOrderRes r){
        PfBorderPayment payment = new PfBorderPayment();

        payment.setAmount(new BigDecimal(p.getTotal_fee()));
        payment.setCreateTime(new Date());
        payment.setIsEnabled(0);
        payment.setOutOrderId(r.getPrepay_id());
        payment.setPayTypeId(0);
        payment.setPayTypeName("微信支付");
        payment.setPfBorderId(Long.valueOf(p.getOut_trade_no()));

        return payment;
    }
}
