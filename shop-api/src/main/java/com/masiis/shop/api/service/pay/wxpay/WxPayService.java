package com.masiis.shop.api.service.pay.wxpay;

import com.masiis.shop.api.bean.pay.WxPayPrepareBOrderRes;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.service.order.BOrderService;
import com.masiis.shop.api.service.order.COrderService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.utils.SysSignUtils;
import com.masiis.shop.common.beans.wxpay.UnifiedOrderReq;
import com.masiis.shop.common.constant.wx.WxConsAPP;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.exceptions.OrderPaidException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Date 2016/5/20
 * @Auther lzh
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

    /**
     * 创建微信预付单的请求数据
     *
     * @param orderCode
     * @param res
     * @param wxUser
     * @param ipAddr
     * @return
     */
    public UnifiedOrderReq createPrepareBOrderByOrderCode(String orderCode, WxPayPrepareBOrderRes res, ComWxUser wxUser, String ipAddr) {
        if(StringUtils.isBlank(orderCode)){
            res.setResCode(SysResCodeCons.RES_CODE_WXPAY_ORDERCODE_NULL);
            res.setResMsg(SysResCodeCons.RES_CODE_WXPAY_ORDERCODE_NULL_MSG);
            return null;
        }
        // 创建预付单请求bean
        UnifiedOrderReq result = createUnifiedOrderReq(wxUser.getOpenid(), ipAddr);
        // 获取订单类型
        String orderType = orderCode.substring(0, 1);
        if (orderType.equals("B")) {
            // 代理和补货订单
            PfBorder order = bOrderService.findByOrderCode(orderCode);
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
            result.setBody(body.substring(0, body.length() - 1));
            // 给外部支付的是系统的支付流水号,自己生成
            result.setOut_trade_no(SysBeanUtils.createPaySerialNumByOrderType(orderType));
            result.setTotal_fee(order.getReceivableAmount().multiply(new BigDecimal(100)).intValue() + "");
            log.info("订单类型orderType:B");
        } else if (orderType.equals("C")) {
            // 试用订单
            log.info("订单类型为C,订单编号为:" + orderCode);
            // 使用订单
            PfCorder order = cOrderService.findByOrderCode(orderCode);
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
            result.setBody(sku.getName());
            result.setOut_trade_no(SysBeanUtils.createPaySerialNumByOrderType(orderType));
            result.setTotal_fee(order.getReceivableAmount().multiply(new BigDecimal(100)).intValue() + ""); //res.setTotal_fee("1");
            log.info("订单类型orderType:B");
        } else {
            res.setResCode(SysResCodeCons.RES_CODE_WXPAY_ORDERCODE_NOTVALID);
            res.setResMsg(SysResCodeCons.RES_CODE_WXPAY_ORDERCODE_NOTVALID_MSG);
            return null;
        }

        return result;
    }

    private UnifiedOrderReq createUnifiedOrderReq(String openid, String ipAddr) {
        UnifiedOrderReq result = new UnifiedOrderReq();
        result.setAppid(WxConsAPP.APPID);
        result.setMch_id(WxConsAPP.WX_PAY_MCHID);
        result.setNonce_str(SysSignUtils.createGenerateStr());
        result.setNotify_url(WxConsAPP.WX_PAY_URL_UNIORDER_NOTIFY);
        result.setOpenid(openid);
        // PC网页或公众号内支付传"WEB"
        result.setDevice_info("WEB");
        result.setSpbill_create_ip(ipAddr);
        result.setTrade_type("APP");
        return result;
    }
}
