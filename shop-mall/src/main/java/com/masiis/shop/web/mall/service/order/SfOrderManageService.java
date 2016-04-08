package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.mall.order.SfOrderItemMapper;
import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.mallBeans.SfOrderItemImage;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.po.ComSkuImage;
import com.masiis.shop.dao.po.SfOrder;
import com.masiis.shop.dao.po.SfOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private SfOrderMapper sfOrderMapper;
    @Autowired
    private ComSkuImageMapper comSkuImageMapper;
    @Autowired
    private SfOrderItemMapper sfOrderItemMapper;

    public List<SfOrder> findOrdersByUserId(Long userId,Integer orderStatus, Integer sendType){
        return sfOrderMapper.selectByUserId(userId,orderStatus,sendType);
    }

    public List<SfOrderItem> findSfOrderItemBySfOrderId(Long sfOrderId){
        return sfOrderItemMapper.selectBySfOrderId(sfOrderId);
    }

    public ComSkuImage findComSkuImage(Integer skuId) {
        return comSkuImageMapper.selectDefaultImgBySkuId(skuId);
    }

}
