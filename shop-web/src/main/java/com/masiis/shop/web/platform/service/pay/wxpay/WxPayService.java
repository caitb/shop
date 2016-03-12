package com.masiis.shop.web.platform.service.pay.wxpay;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderItem;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.web.platform.beans.pay.wxpay.UnifiedOrderReq;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.platform.constants.WxConstants;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.COrderService;
import com.masiis.shop.web.platform.utils.WXBeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public UnifiedOrderReq createUniFiedOrder(WxPaySysParamReq req, String ip) {
        try{
            String orderType = req.getOrderId().charAt(0) + "";
            UnifiedOrderReq res = new UnifiedOrderReq();

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
                res.setAppid(WxConstants.APPID);
                //res.setAttach();
                res.setBody(body.substring(0, body.length() - 2));
                //res.setDetail();
                //res.setDevice_info();
                //res.setFee_type(); //默认中文
                //res.setGoods_tag();
                //res.setLimit_pay();
                res.setMch_id(WxConstants.WX_PAY_MCHID);
                res.setNonce_str(WXBeanUtils.createGenerateStr());
                res.setNotify_url(WxConstants.WX_PAY_URL_UNIORDER_NOTIFY);
                res.setOut_trade_no(order.getOrderCode());
                res.setOpenid("oUIwkwgLzn8CKMDrvbCSE3T-u5fs");
                //res.setProduct_id();
                res.setSpbill_create_ip(ip);
                //res.setSign();
                res.setTrade_type("JSAPI");
                res.setTotal_fee("1");
                //res.setTime_start();
                //res.setTime_expire();
                log.info("orderType:B");
            } else if ("C".equalsIgnoreCase(orderType)) {
                // 试用订单
                PfCorder order = cOrderService.findByOrderCode(req.getOrderId());
                if(order == null){
                    throw new BusinessException("订单号错误,不存在该订单号!");
                }
                log.info("orderType:C");
            } else {
                throw new BusinessException("订单号错误,不存在该订单号!");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e);
        }

        return null;
    }
}