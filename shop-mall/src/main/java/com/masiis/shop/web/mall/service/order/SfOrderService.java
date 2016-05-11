package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.SfOrder;
import com.masiis.shop.dao.po.SfOrderItem;
import com.masiis.shop.dao.po.SfOrderPayment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hzz on 2016/4/10.
 */
@Service
public class SfOrderService {

    @Resource
    private SfOrderMapper sfOrderMapper;

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public int insert(SfOrder sfOrder){
        return sfOrderMapper.insert(sfOrder);
    }

    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public SfOrder getOrderById(Long ordId){
       return sfOrderMapper.selectByPrimaryKey(ordId);
    }

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public int update(SfOrder sfOrder){
        return sfOrderMapper.updateByPrimaryKey(sfOrder);
    }

    /**
     * 根据订单号查询订单
     *
     * @param orderCode
     * @return
     */
    public SfOrder findByOrderCode(String orderCode) {
        return sfOrderMapper.selectByOrderCode(orderCode);
    }

    public List<SfOrder> findByShopUserIds(List<Long> userIds){
        return sfOrderMapper.selectByShopUserIds(userIds);
    }

    public List<SfOrder> findByUserIds(List<Long> userIds){
        return sfOrderMapper.selectByUserIds(userIds);
    }
}
