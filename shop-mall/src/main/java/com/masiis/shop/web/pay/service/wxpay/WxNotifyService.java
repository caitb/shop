package com.masiis.shop.web.pay.service.wxpay;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.PfBorderPayment;
import com.masiis.shop.dao.po.PfCorderPayment;
import com.masiis.shop.web.mall.beans.pay.wxpay.CallBackNotifyReq;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lzh on 2016/3/16.
 */
@Service
public class WxNotifyService {
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * 处理微信支付订单异步回调业务
     *
     * @param param
     */
    public void handleWxPayNotify(CallBackNotifyReq param, String rootPath) {

    }
}
