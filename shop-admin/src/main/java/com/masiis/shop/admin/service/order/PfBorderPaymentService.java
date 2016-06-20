package com.masiis.shop.admin.service.order;

import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.po.PfBorderPayment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/6/16.
 */
@Service
@Transactional
public class PfBorderPaymentService {

    @Resource
    private PfBorderPaymentMapper borderPaymentMapper;

    public int update(PfBorderPayment po){
        return borderPaymentMapper.updateById(po);
    }
}
