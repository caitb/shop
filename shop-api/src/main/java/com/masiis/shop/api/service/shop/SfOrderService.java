package com.masiis.shop.api.service.shop;

import com.masiis.shop.api.constants.SysConstants;
import com.masiis.shop.api.service.order.BOrderSkuStockService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.utils.wx.WxPFNoticeUtils;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.order.*;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 小铺订单
 * @author muchaofeng
 * @date 2016/4/8 17:52
 */

@Service
@Transactional
public class SfOrderService {
    @Autowired
    private SfOrderManageMapper sfOrderManageMapper;
    @Autowired
    private SfOrderMapper sfOrderMapper;
    @Autowired
    private SfOrderConsigneeMapper sfOrderConsigneeMapper;
    @Autowired
    private SfOrderOperationLogMapper sfOrderOperationLogMapper;
    @Autowired
    private BOrderSkuStockService borderSkuStockService;
    @Autowired
    private SfOrderFreightMapper sfOrderFreightMapper;
    @Autowired
    private SfOrderItemMallMapper sfOrderItemMallMapper;
    @Autowired
    private SkuService skuService;
    @Autowired
    private ComUserMapper comUserMapper;


    /**
     * 通过订单Id获取订单
     * @author muchaofeng
     * @date 2016/4/13 19:59
     */
    public SfOrder findSforderByorderId(Long id){
        return sfOrderMapper.selectByPrimaryKey(id);
    }

   /**
    * 小铺订单
    * @author muchaofeng
    * @date 2016/4/13 10:40
    */
    public List<SfOrder> findOrdersByShopUserId(Long shopUserId,Integer orderStatus, Long shopId,Integer sendType){
        List<SfOrder> sfOrders = sfOrderManageMapper.selectByShopUserId(shopUserId, orderStatus, shopId,sendType);
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        for (SfOrder sfOrder : sfOrders) {
            SfOrderConsignee orderConsignee = sfOrderConsigneeMapper.getOrdConByOrdId(sfOrder.getId());
            List<SfOrderItem> sfOrderItems = sfOrderItemMallMapper.selectBySfOrderId(sfOrder.getId());
            for (SfOrderItem sfOrderItem : sfOrderItems) {
                sfOrderItem.setSkuUrl(skuValue + skuService.findComSkuImage(sfOrderItem.getSkuId()).getImgUrl());
                sfOrderItem.setSkuMoney(sfOrderItem.getUnitPrice().toString());
                sfOrder.setTotalQuantity(sfOrder.getTotalQuantity() + sfOrderItem.getQuantity());//订单商品总量
                sfOrder.setOrderMoney(sfOrder.getOrderAmount().toString());
            }
            sfOrder.setSfOrderItems(sfOrderItems);
            sfOrder.setSfOrderConsignee(orderConsignee);
        }
        return sfOrders;
    }
    /**
     * 发货
     * @author muchaofeng
     * @date 2016/4/13 18:19
     */
    @Transactional
    public void deliver(String shipManName, Long orderId, String freight, String shipManId, ComUser user) throws Exception {
        SfOrder sfOrder = sfOrderMapper.selectByPrimaryKey(orderId);
        ComUser comUser = comUserMapper.selectByPrimaryKey(sfOrder.getUserId());
        if(sfOrder.getSendType() == 0){
            throw new BusinessException("请选择发货方式");
        }
        if (freight == null || freight == "") {
            throw new BusinessException("请重新输入快递单号");
        }
        if (sfOrder.getSendType() == 1) {//平台代发
            sfOrder.setShipStatus(5);
            sfOrder.setShipTime(new Date());
            sfOrder.setOrderStatus(8);
            SfOrderFreight sforderFreight = new SfOrderFreight();
            sforderFreight.setCreateTime(new Date());
            sforderFreight.setShipManId(Integer.parseInt(shipManId));
            sforderFreight.setSfOrderId(orderId);
            sforderFreight.setFreight(freight);
            sforderFreight.setShipManName(shipManName);
            borderSkuStockService.updateOrderStock(sfOrder, user);
            sfOrderMapper.updateByPrimaryKey(sfOrder);
            sfOrderFreightMapper.insert(sforderFreight);
            //添加订单日志
            SfOrderOperationLog sfOrderOperationLog = new SfOrderOperationLog();
            sfOrderOperationLog.setCreateMan(sfOrder.getUserId());
            sfOrderOperationLog.setCreateTime(new Date());
            sfOrderOperationLog.setSfOrderStatus(8);
            sfOrderOperationLog.setSfOrderId(sfOrder.getId());
            sfOrderOperationLog.setRemark("订单完成");
            sfOrderOperationLogMapper.insert(sfOrderOperationLog);

            String url = PropertiesUtils.getStringValue("mall.domain.name.address")+"/sfOrderController/sfOrderDetal.html?id="+sfOrder.getId().toString();
            String[] params=new String[5];
            params[0]="";
            params[1]="";
            params[2]=sfOrder.getOrderCode();
            params[3]=shipManName;
            params[4]=freight;
            Boolean aBoolean = WxPFNoticeUtils.getInstance().orderShippedNotice(comUser, params, url);
            if(aBoolean==false){
                throw new Exception("消费者订单发货微信提示失败");
            }
            MobileMessageUtil.getInitialization("C").consumerShipRemind(comUser.getMobile(),sfOrder.getOrderCode(),shipManName,freight);
        } else if (sfOrder.getSendType() == 2) {//自己发货
            sfOrder.setShipStatus(5);
            sfOrder.setOrderStatus(8);
            SfOrderFreight sforderFreight = new SfOrderFreight();
            sforderFreight.setCreateTime(new Date());
            sforderFreight.setShipManId(Integer.parseInt(shipManId));
            sforderFreight.setSfOrderId(orderId);
            sforderFreight.setFreight(freight);
            sforderFreight.setShipManName(shipManName);
            sfOrderMapper.updateByPrimaryKey(sfOrder);
            sfOrderFreightMapper.insert(sforderFreight);
            //添加订单日志
            SfOrderOperationLog sfOrderOperationLog = new SfOrderOperationLog();
            sfOrderOperationLog.setCreateMan(sfOrder.getUserId());
            sfOrderOperationLog.setCreateTime(new Date());
            sfOrderOperationLog.setSfOrderStatus(8);
            sfOrderOperationLog.setSfOrderId(sfOrder.getId());
            sfOrderOperationLog.setRemark("订单完成");
            sfOrderOperationLogMapper.insert(sfOrderOperationLog);

            String url = PropertiesUtils.getStringValue("mall.domain.name.address")+"/sfOrderController/sfOrderDetal.html?id="+sfOrder.getId().toString();
            String[] params=new String[5];
            params[0]="";
            params[1]="";
            params[2]=sfOrder.getOrderCode();
            params[3]=shipManName;
            params[4]=freight;
            Boolean aBoolean = WxPFNoticeUtils.getInstance().orderShippedNotice(comUser, params, url);
            if(aBoolean==false){
                throw new Exception("消费者订单发货微信提示失败");
            }
            MobileMessageUtil.getInitialization("C").consumerShipRemind(comUser.getMobile(),sfOrder.getOrderCode(),shipManName,freight);
        }
    }
}
