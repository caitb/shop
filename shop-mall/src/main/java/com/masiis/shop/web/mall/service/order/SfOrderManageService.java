package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.common.enums.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.order.*;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.mallBeans.SfOrderItemImage;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.constants.SysConstants;
import com.masiis.shop.web.mall.service.product.SkuService;
import com.masiis.shop.web.mall.service.user.SfUserAccountService;
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
    private SfOrderConsigneeMapper sfOrderConsigneeMapper;
    @Autowired
    private ComSkuImageMapper comSkuImageMapper;
    @Autowired
    private SfOrderMallFreightMapper sfOrderMallFreightMapper;
    @Autowired
    private SfOrderItemMallMapper sfOrderItemMallMapper;
    @Autowired
    private SkuService skuService;
    @Autowired
    private SfOrderMallConsigneeMapper sfOrderMallConsigneeMapper;
    @Autowired
    private SfUserRelationMapper sfUserRelationMapper;
    @Autowired
    private SfOrderOperationLogMapper sfOrderOperationLogMapper;
    @Autowired
    private SfUserAccountService sfUserAccountService;

    /**
     * 订单
     * @author muchaofeng
     * @date 2016/4/9 17:12
     */
    public List<SfOrder> findOrdersByUserId(Long userId,Integer orderStatus, Long shopId){
        List<SfOrder> sfOrders = sfOrderManageMapper.selectByUserId(userId, orderStatus, shopId);
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        for (SfOrder sfOrder : sfOrders) {
            List<SfOrderItem> sfOrderItems = sfOrderItemMallMapper.selectBySfOrderId(sfOrder.getId());
            for (SfOrderItem sfOrderItem : sfOrderItems) {
                ComSkuImage comSkuImage = skuService.findComSkuImage(sfOrderItem.getSkuId());
                sfOrderItem.setSkuUrl(skuValue + comSkuImage.getImgUrl());
                sfOrder.setTotalQuantity(sfOrder.getTotalQuantity() + sfOrderItem.getQuantity());//订单商品总量
            }
            sfOrder.setSfOrderItems(sfOrderItems);
        }
        return sfOrders;
    }


    /**
     * 获取上级
     * @author muchaofeng
     * @date 2016/4/9 17:11
     */
    public SfUserRelation findSfUserRelationByUserId(Long userId){
        return sfUserRelationMapper.getSfUserRelationByUserId(userId);
    }

    /**
     * 根据订单Id获取订单
     * @author muchaofeng
     * @date 2016/4/9 17:12
     */
    public SfOrder findOrderByOrderId(Long orderId){
        return sfOrderMapper.selectByPrimaryKey(orderId);
    }
    /**
     * 根据订单id找订单商品关系
     * @author muchaofeng
     * @date 2016/4/9 17:13
     */
    public List<SfOrderItem> findSfOrderItemBySfOrderId(Long sfOrderId){
        return sfOrderItemMallMapper.selectBySfOrderId(sfOrderId);
    }
    /**
     * 查询图片地址
     * @author muchaofeng
     * @date 2016/4/9 17:14
     */
    public ComSkuImage findComSkuImage(Integer skuId) {
        return comSkuImageMapper.selectDefaultImgBySkuId(skuId);
    }
    /**
     * 获取快递公司
     * @author muchaofeng
     * @date 2016/4/9 17:15
     */
    public List<SfOrderFreight> findSfOrderFreight(Long orderId) {
        return sfOrderMallFreightMapper.selectByOrderId(orderId);
    }
    /**
     * 收货人信息
     * @author muchaofeng
     * @date 2016/4/9 17:15
     */
    public SfOrderConsignee findSfOrderConsignee(Long orderId) {
        return sfOrderMallConsigneeMapper.selectBySfOrderId(orderId);
    }
    /**
     * 收货
     * @author muchaofeng
     * @date 2016/4/1 18:12
     */
    @Transactional
    public void deliver( Long orderId) throws Exception {
        SfOrder sfOrder = sfOrderMapper.selectByPrimaryKey(orderId);
        sfOrder.setOrderStatus(3);
        sfOrderMapper.updateByPrimaryKey(sfOrder);
        SfOrderOperationLog sfOrderOperationLog = new SfOrderOperationLog();
        sfOrderOperationLog.setCreateMan(sfOrder.getUserId());
        sfOrderOperationLog.setCreateTime(new Date());
        sfOrderOperationLog.setSfOrderStatus(BOrderStatus.Complete.getCode());
        sfOrderOperationLog.setSfOrderId(sfOrder.getId());
        sfOrderOperationLog.setRemark("订单完成");
        sfOrderOperationLogMapper.insert(sfOrderOperationLog);
        // 进行订单分润和代理商销售额、收入计算
        sfUserAccountService.countingSfOrder(sfOrder);
    }
}
