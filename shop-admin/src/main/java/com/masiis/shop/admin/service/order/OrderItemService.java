package com.masiis.shop.admin.service.order;

import com.masiis.shop.dao.mall.order.SfOrderItemMapper;
import com.masiis.shop.dao.po.SfOrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/6/10.
 */
@Service
@Transactional
public class OrderItemService {

    @Resource
    private SfOrderItemMapper sfOrderItemMapper;

    /**
     * 获取订单明细
     * @param sfOrderId
     * @return
     */
    public List<SfOrderItem> findByOrderId(Long sfOrderId){
        return sfOrderItemMapper.getOrderItemByOrderId(sfOrderId);
    }
}
