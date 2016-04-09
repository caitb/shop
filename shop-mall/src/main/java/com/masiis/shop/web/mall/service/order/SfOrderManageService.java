package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.order.*;
import com.masiis.shop.dao.mallBeans.SfOrderItemImage;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 小铺订单
 * @author muchaofeng
 * @date 2016/4/8 17:52
 */

@Service
@Transactional
public class SfOrderManageService {
    @Autowired
    private SfOrderManageMapper sfOrderManageMapper;
    @Autowired
    private SfOrderMapper sfOrderMapper;
    @Autowired
    private ComSkuImageMapper comSkuImageMapper;
    @Autowired
    private SfOrderMallFreightMapper sfOrderMallFreightMapper;
    @Autowired
    private SfOrderItemMallMapper sfOrderItemMallMapper;
    @Autowired
    private SfOrderMallConsigneeMapper sfOrderMallConsigneeMapper;
    @Autowired
    private SfOrderFreightMapper sfOrderFreightMapper;

    public List<SfOrder> findOrdersByUserId(Long userId,Integer orderStatus, Integer sendType){
        return sfOrderManageMapper.selectByUserId(userId,orderStatus,sendType);
    }

    public SfOrder findOrderByOrderId(Long orderId){
        return sfOrderMapper.selectByPrimaryKey(orderId);
    }

    public List<SfOrderItem> findSfOrderItemBySfOrderId(Long sfOrderId){
        return sfOrderItemMallMapper.selectBySfOrderId(sfOrderId);
    }

    public ComSkuImage findComSkuImage(Integer skuId) {
        return comSkuImageMapper.selectDefaultImgBySkuId(skuId);
    }
    public List<SfOrderFreight> findSfOrderFreight(Long orderId) {
        return sfOrderMallFreightMapper.selectByOrderId(orderId);
    }

    public SfOrderConsignee findSfOrderConsignee(Long orderId) {
        return sfOrderMallConsigneeMapper.selectBySfOrderId(orderId);
    }

    /**
     * 收货
     * @author muchaofeng
     * @date 2016/4/1 18:12
     */
    public void deliver( Long orderId) throws Exception {
        SfOrder sfOrder = sfOrderMapper.selectByPrimaryKey(orderId);
        sfOrder.setOrderStatus(3);
        sfOrderMapper.updateByPrimaryKey(sfOrder);
    }
}
