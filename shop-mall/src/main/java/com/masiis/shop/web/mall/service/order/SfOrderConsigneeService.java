package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.mall.order.SfOrderConsigneeMapper;
import com.masiis.shop.dao.po.SfOrderConsignee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/4/10.
 */
@Service
public class SfOrderConsigneeService {

    @Resource
    private SfOrderConsigneeMapper sfOrderConsigneeMapper;

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public int insert(SfOrderConsignee orderCon){
        return sfOrderConsigneeMapper.insert(orderCon);
    }

    /**
     * 根据订单的id获得订单的收货地址
     * @author hanzengzhi
     * @date 2016/4/10 14:07
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public SfOrderConsignee getOrdConByOrdId(Long orderId){
            return sfOrderConsigneeMapper.getOrdConByOrdId(orderId);
    }
}
