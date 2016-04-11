package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.mall.order.SfOrderItemMallMapper;
import com.masiis.shop.dao.mall.order.SfOrderItemMapper;
import com.masiis.shop.dao.po.SfOrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hzz on 2016/4/10.
 */
@Service
public class SfOrderItemService {

    @Resource
    private SfOrderItemMapper sfOrderItemMapper;

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public int insert(SfOrderItem sfOrderItem){
        return sfOrderItemMapper.insert(sfOrderItem);
    }

    /**
     * 根据订单的id获得订单的详情信息
     * @author hanzengzhi
     * @date 2016/4/10 14:18
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public List<SfOrderItem> getOrderItemByOrderId(Long orderId){
        return sfOrderItemMapper.getOrderItemByOrderId(orderId);
    }

    public List<SfOrderItem> getSforderItemByOrderId(Long orderId) {
        return sfOrderItemMapper.getOrderItemByOrderId(orderId);
    }
}
