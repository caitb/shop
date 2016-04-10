package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.mall.order.SfOrderItemMallMapper;
import com.masiis.shop.dao.mall.order.SfOrderItemMapper;
import com.masiis.shop.dao.po.SfOrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
}
