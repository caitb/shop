package com.masiis.shop.admin.service.order;

import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.po.PfBorderPayment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/4/27.
 */
@Service
public class BOrderPaymentService {

    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;

    /**
     * 查找线下支付记录
     * @param bOrderId
     * @return
     */
    public PfBorderPayment findOfflinePayByBOrderId(Long bOrderId){
        return pfBorderPaymentMapper.selectOfflinePayByBOrderId(bOrderId);
    }
}
