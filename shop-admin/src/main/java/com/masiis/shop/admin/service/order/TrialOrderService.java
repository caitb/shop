package com.masiis.shop.admin.service.order;

import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.platform.order.PfCorderOperationLogMapper;
import com.masiis.shop.dao.platform.order.PfCorderPaymentMapper;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.dao.po.PfCorderConsignee;
import com.masiis.shop.dao.po.PfCorderPayment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */
@Service
@Transactional
public class TrialOrderService {
    @Resource
    private PfCorderMapper pfCorderMapper;

    @Resource
    private PfCorderOperationLogMapper pfCorderOperationLogMapper;
    @Resource
    private PfCorderPaymentMapper pfCorderPaymentMapper;

    public List<PfCorder> queryAll() {
        return pfCorderMapper.selectByCondition(new PfCorder());
    }

    public PfCorderConsignee findPfCorderConsignee(Long pfCorderId){
        return  pfCorderOperationLogMapper.selectByKey(pfCorderId);
    }

    public PfCorderPayment findPfCorderPayment(Long pfCorderId){
        return pfCorderPaymentMapper.selectById(pfCorderId);
    }

}
