package com.masiis.shop.web.mall.service.order;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.common.enums.mall.SfOrderStatusEnum;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.order.*;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.utils.wx.WxSFNoticeUtils;
import com.masiis.shop.web.mall.service.user.SfUserAccountService;
import com.masiis.shop.web.platform.service.order.BOrderSkuStockService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hzz on 2016/4/10.
 */
@Service
public class SfOrderService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfOrderOperationLogMapper logMapper;
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
    @Resource
    private SfUserAccountService sfUserAccountService;
    @Resource
    private ComSkuImageMapper comSkuImageMapper;

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

    /**
     * 查询指定过期时间的待取消订单
     *
     * @param expiraTime
     * @param orderStatus
     * @param payStatus
     * @return
     */
    public List<SfOrder> findListByStatusAndDate(Date expiraTime, int orderStatus, int payStatus) {
        log.info("查询创建时间小于:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss")
                + ",订单状态为:" + orderStatus + ",支付状态为:" + payStatus + "的订单");
        // 查询
        List<SfOrder> resList = sfOrderMapper.selectByStatusAndDate(expiraTime, orderStatus, payStatus);
        if(resList == null || resList.size() == 0)
            return null;
        return resList;
    }

    /**
     * 取消未支付订单
     *
     * @param sfOrder
     */
    @Transactional
    public void cancelUnPaySfOrder(SfOrder sfOrder) {
        try{
            // 重新根据id查询该订单
            sfOrder = sfOrderMapper.selectByPrimaryKey(sfOrder.getId());
            // 检查订单状态的有效性
            if(sfOrder.getOrderStatus() != 0){
                throw new BusinessException("订单状态不正确,订单号:" + sfOrder.getOrderCode()
                        + ",当前订单状态为:" + sfOrder.getOrderStatus());
            }
            if(sfOrder.getPayStatus() != 0){
                throw new BusinessException("订单支付状态不正确,订单号:" + sfOrder.getOrderCode()
                        + ",当前订单支付状态为:" + sfOrder.getPayStatus());
            }
            log.info("订单状态和支付状态校验通过!");
            // 修改订单的状态为已取消状态
            int result = sfOrderMapper.updateOrderCancelById(sfOrder.getId(), SfOrderStatusEnum.ORDER_CANCEL.getCode());
            if(result != 1){
                sfOrder = sfOrderMapper.selectByPrimaryKey(sfOrder.getId());
                throw new BusinessException("订单取消失败,订单此时状态为:" + sfOrder.getOrderStatus()
                        + ",支付状态为:" + sfOrder.getPayStatus());
            }
            // 插入订单操作记录
            SfOrderOperationLog oLog = new SfOrderOperationLog();
            oLog.setCreateMan(0L);
            oLog.setCreateTime(new Date());
            oLog.setSfOrderId(sfOrder.getId());
            // 取消状态
            oLog.setSfOrderStatus(SfOrderStatusEnum.ORDER_CANCEL.getCode());
            oLog.setRemark("超过72小时未支付,系统自动取消");
            logMapper.insert(oLog);
        } catch (Exception e) {
            log.error("订单超72小时未支付订单取消失败," + e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 超过7天自动收货
     *
     * @param sfOrder
     */
    @Transactional
    public void confirmOrderReceive(SfOrder sfOrder) {
        //SfOrder sfOrder = sfOrderMapper.selectByPrimaryKey(orderId);
        // 进行订单分润和代理商销售额、收入计算
        sfUserAccountService.countingSfOrder(sfOrder);
        // 进行订单状态修改
        sfOrder.setOrderStatus(3);
        sfOrder.setShipStatus(9);//已收货
        sfOrder.setIsReceipt(1);
        sfOrder.setReceiptTime(new Date());
        sfOrderMapper.updateByPrimaryKey(sfOrder);
        SfOrderOperationLog sfOrderOperationLog = new SfOrderOperationLog();
        sfOrderOperationLog.setCreateMan(sfOrder.getUserId());
        sfOrderOperationLog.setCreateTime(new Date());
        sfOrderOperationLog.setSfOrderStatus(BOrderStatus.Complete.getCode());
        sfOrderOperationLog.setSfOrderId(sfOrder.getId());
        sfOrderOperationLog.setRemark("订单完成");
        logMapper.insert(sfOrderOperationLog);
    }

    /**
     * 查询状态为“待发货”的订单总数
     * @param shopId
     * @param userId
     */
    public Integer getUnshippedOrderCount(Long shopId, Long userId) {
        return sfOrderMapper.selectUnshippedOrderCount(shopId, userId, 7);
    }

    /**
     * 店铺订单列表
     * @param shopUserId 小铺归属人id
     * @param orderStatus 订单状态 0待付款，7待发货，3已完成，8已发货
     * @param sendType 发货方式 1平台发货，2店主发货
     * @return
     */
    public List<Map<String,Object>> getShopOrderList(Long shopUserId, Integer orderStatus, Integer sendType, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize, false);
        return sfOrderMapper.getShopOrderList(shopUserId, orderStatus, sendType);
    }

    public List<SfOrderFreight> getFreightByOrderId(Long orderId) {
        return sfOrderFreightMapper.selectByOrderId(orderId);
    }

    /**
     * 查询指定过期发货时间的待收货订单
     *
     * @param expiraTime
     * @param orderStatus
     * @param payStatus
     * @return
     */
    public List<SfOrder> findListByStatusAndShipTime(Date expiraTime, Integer orderStatus, int payStatus) {
        log.info("查询发货时间小于:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss")
                + ",订单状态为:" + orderStatus + ",支付状态为:" + payStatus + "的订单");
        // 查询
        List<SfOrder> resList = sfOrderMapper.selectByStatusAndShipTime(expiraTime, orderStatus, payStatus);
        if(resList == null || resList.size() == 0)
            return null;
        return resList;
    }

    /**
     * 获取sku默认图片url
     * @param skuId
     * @return
     */
    public String getSkuDefaultImgUrlBySkuId(Integer skuId) {
        return comSkuImageMapper.selectDefaultImgBySkuId(skuId).getImgUrl();
    }
}
