package com.masiis.shop.web.platform.service.pay.wxpay;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.web.platform.beans.pay.wxpay.UnifiedOrderReq;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.COrderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    public UnifiedOrderReq createUniFiedOrder(WxPaySysParamReq req) {
        try{
            String orderType = req.getOrderId().charAt(0) + "";
            UnifiedOrderReq res = new UnifiedOrderReq();

            if("B".equalsIgnoreCase(orderType)){
                // 代理订单
                PfBorder order = bOrderService.findByOrderCode(req.getOrderId());
                log.info("orderType:B");
            } else if ("C".equalsIgnoreCase(orderType)) {
                // 试用订单
                PfCorder order = cOrderService.findByOrderCode(req.getOrderId());
                log.info("orderType:C");
            } else {
                throw new BusinessException("订单号错误,不存在该订单号!");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }
}
