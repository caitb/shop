package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.order.*;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.utils.wx.WxPFNoticeUtils;
import com.masiis.shop.web.common.utils.wx.WxSFNoticeUtils;
import com.masiis.shop.web.platform.service.order.BOrderSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by hzz on 2016/4/10.
 */
@Service
public class SfOrderService {

    @Resource
    private SfOrderMapper sfOrderMapper;
    @Resource
    private SfOrderManageMapper sfOrderManageMapper;
    @Resource
    private SfOrderConsigneeMapper sfOrderConsigneeMapper;
    @Resource
    private SfOrderOperationLogMapper sfOrderOperationLogMapper;
    @Resource
    private BOrderSkuStockService borderSkuStockService;
    @Resource
    private SfOrderFreightMapper sfOrderFreightMapper;
    @Resource
    private SfOrderItemMallMapper sfOrderItemMallMapper;
    @Resource
    private SkuService skuService;
    @Resource
    private ComUserMapper comUserMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int insert(SfOrder sfOrder) {
        return sfOrderMapper.insert(sfOrder);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public SfOrder getOrderById(Long ordId) {
        return sfOrderMapper.selectByPrimaryKey(ordId);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int update(SfOrder sfOrder) {
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

    public List<SfOrder> findByShopUserIds(List<Long> userIds) {
        return sfOrderMapper.selectByShopUserIds(userIds);
    }

    public List<SfOrder> findByUserIds(Long userId, List<Long> userIds) {
        return sfOrderMapper.selectByUserIds(userId, userIds);
    }

    public List<SfOrder> findByUserId(Long userId) {
        return sfOrderMapper.selectByUserId(userId);
    }

    /**
     * 通过订单Id获取订单
     *
     * @author muchaofeng
     * @date 2016/4/13 19:59
     */
    public SfOrder findSforderByorderId(Long id) {
        return sfOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 小铺订单
     *
     * @author muchaofeng
     * @date 2016/4/13 10:40
     */
    public List<SfOrder> findOrdersByShopUserId(Long shopUserId, Integer orderStatus, Long shopId, Integer sendType) {
        List<SfOrder> sfOrders = sfOrderManageMapper.selectByShopUserId(shopUserId, orderStatus, shopId, sendType);
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
     *
     * @author muchaofeng
     * @date 2016/4/13 18:19
     */
    @Transactional
    public void deliver(String shipManName, Long orderId, String freight, String shipManId, ComUser user) throws Exception {
        SfOrder sfOrder = sfOrderMapper.selectByPrimaryKey(orderId);
        ComUser comUser = comUserMapper.selectByPrimaryKey(sfOrder.getUserId());
        if (sfOrder.getSendType() == 0) {
            throw new BusinessException("请选择发货方式");
        }
        if (freight == null || freight == "") {
            throw new BusinessException("请重新输入快递单号");
        }
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


        String url = PropertiesUtils.getStringValue("mall.domain.name.address") + "/sfOrderManagerController/borderDetils.html?id=" + sfOrder.getId();
        String[] params = new String[3];
        params[0] = sfOrder.getOrderCode();
        params[1] = shipManName;
        params[2] = freight;
        WxSFNoticeUtils.getInstance().orderShipNotice(comUser, params, url);
        MobileMessageUtil.getInitialization("C").consumerShipRemind(comUser.getMobile(), sfOrder.getOrderCode(), shipManName, freight);
    }
}
