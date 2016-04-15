package com.masiis.shop.admin.service.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.dao.mall.order.*;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 店铺订单业务
 * Created by cai_tb on 16/4/14.
 */
@Service
public class OrderService {

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSpuMapper comSpuMapper;
    @Resource
    private SfOrderMapper sfOrderMapper;
    @Resource
    private SfOrderConsigneeMapper sfOrderConsigneeMapper;
    @Resource
    private SfOrderFreightMapper sfOrderFreightMapper;
    @Resource
    private SfOrderItemMapper sfOrderItemMapper;
    @Resource
    private SfOrderPaymentMapper sfOrderPaymentMapper;
    @Resource
    private SfOrderOperationLogMapper sfOrderOperationLogMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;

    /**
     * 店铺订单列表
     * @param pageNumber
     * @param pageSize
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, Map<String, Object> conditionMap){
        PageHelper.startPage(pageNumber, pageSize);
        List<SfOrder> sfOrders = sfOrderMapper.selectByMap(conditionMap);
        PageInfo<SfOrder> pageInfo = new PageInfo<>(sfOrders);

        List<Order> orders = new ArrayList<>();
        for(SfOrder sfOrder : sfOrders){
            ComUser comUser = comUserMapper.selectByPrimaryKey(sfOrder.getUserId());
            SfOrderConsignee sfOrderConsignee = sfOrderConsigneeMapper.getOrdConByOrdId(sfOrder.getId());
            List<SfOrderFreight> sfOrderFreights = sfOrderFreightMapper.selectByOrderId(sfOrder.getId());
            List<SfOrderPayment> sfOrderPayments = sfOrderPaymentMapper.selectBySfOrderId(sfOrder.getId());

            Order order = new Order();
            order.setComUser(comUser);
            order.setSfOrder(sfOrder);
            order.setSfOrderConsignee(sfOrderConsignee);
            order.setSfOrderFreights(sfOrderFreights);
            order.setSfOrderPayments(sfOrderPayments);

            orders.add(order);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", orders);

        return pageMap;
    }

    /**
     * 获取订单明细
     * @param id
     * @return
     */
    public Order find(Long id){
        SfOrder sfOrder = sfOrderMapper.selectByPrimaryKey(id);
        ComUser comUser = comUserMapper.selectByPrimaryKey(sfOrder.getUserId());
        SfOrderConsignee sfOrderConsignee = sfOrderConsigneeMapper.getOrdConByOrdId(sfOrder.getId());
        List<SfOrderFreight> sfOrderFreights = sfOrderFreightMapper.selectByOrderId(sfOrder.getId());
        List<SfOrderItem> sfOrderItems = sfOrderItemMapper.getOrderItemByOrderId(sfOrder.getId());

        List<ProductInfo> productInfos = new ArrayList<>();
        for(SfOrderItem sfOrderItem : sfOrderItems){
            ComSku comSku = comSkuMapper.selectById(sfOrderItem.getSkuId());
            ComSpu comSpu = comSpuMapper.selectById(sfOrderItem.getSpuId());

            ProductInfo productInfo = new ProductInfo();
            productInfo.setComSku(comSku);
            productInfo.setComSpu(comSpu);

            productInfos.add(productInfo);
        }

        Order order = new Order();
        order.setSfOrder(sfOrder);
        order.setComUser(comUser);
        order.setSfOrderConsignee(sfOrderConsignee);
        order.setSfOrderFreights(sfOrderFreights);
        order.setSfOrderItems(sfOrderItems);
        order.setProductInfos(productInfos);

        if(sfOrder.getPayStatus().intValue() == 1){
            SfOrderPayment sfOrderPayment = new SfOrderPayment();
            sfOrderPayment.setSfOrderId(sfOrder.getId());
            sfOrderPayment.setIsEnabled(1);
            List<SfOrderPayment> sfOrderPayments = sfOrderPaymentMapper.selectByCondition(sfOrderPayment);
            order.setSfOrderPayments(sfOrderPayments);
        }

        return order;
    }

    /**
     * 发货
     * @param sfOrderFreight
     */
    public void delivery(SfOrderFreight sfOrderFreight, PbUser operationUser) throws Exception {
        SfOrder sfOrder = sfOrderMapper.selectByPrimaryKey(sfOrderFreight.getSfOrderId());
        sfOrder.setOrderStatus(8);
        sfOrder.setShipStatus(5);
        sfOrder.setShipTime(new Date());

        sfOrderFreight.setCreateTime(new Date());

        sfOrderMapper.updateByPrimaryKey(sfOrder);
        sfOrderFreightMapper.insert(sfOrderFreight);

        updateOrderStock(sfOrder);

        //添加订单日志
        SfOrderOperationLog sfOrderOperationLog = new SfOrderOperationLog();
        sfOrderOperationLog.setCreateMan(sfOrder.getUserId());
        sfOrderOperationLog.setCreateTime(new Date());
        sfOrderOperationLog.setSfOrderStatus(8);
        sfOrderOperationLog.setSfOrderId(sfOrder.getId());
        sfOrderOperationLog.setRemark(operationUser.toString());
        sfOrderOperationLogMapper.insert(sfOrderOperationLog);
    }

    public void updateOrderStock(SfOrder sfOrder) throws Exception {
        List<SfOrderItem> sfOrderItems = sfOrderItemMapper.getOrderItemByOrderId(sfOrder.getId());
        for(SfOrderItem sfOrderItem : sfOrderItems){
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(sfOrder.getShopUserId(), sfOrderItem.getSkuId());
            if(pfUserSkuStock.getStock()-sfOrderItem.getQuantity()>=0 && pfUserSkuStock.getFrozenStock()-sfOrderItem.getQuantity()>=0){
                pfUserSkuStock.setStock(pfUserSkuStock.getStock()-sfOrderItem.getQuantity());
                pfUserSkuStock.setFrozenStock(pfUserSkuStock.getFrozenStock()-sfOrderItem.getQuantity());
                int c = pfUserSkuStockMapper.updateByIdAndVersion(pfUserSkuStock);
                if(c == 0) throw new Exception("更改库存失败!");
            }else{
                throw new Exception("库存异常!");
            }
        }
    }
}
