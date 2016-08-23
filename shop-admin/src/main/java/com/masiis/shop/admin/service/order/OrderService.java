package com.masiis.shop.admin.service.order;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.admin.service.product.PfUserSkuStockService;
import com.masiis.shop.admin.service.shop.SfShopStatisticsService;
import com.masiis.shop.admin.utils.WxSFNoticeUtils;
import com.masiis.shop.common.enums.platform.OperationType;
import com.masiis.shop.common.enums.platform.UserAccountRecordFeeType;
import com.masiis.shop.common.enums.mall.SfOrderStatusEnum;
import com.masiis.shop.common.enums.platform.UserSkuStockLogType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.mall.order.*;
import com.masiis.shop.dao.mall.shop.SfShopBillItemMapper;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.user.*;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.system.PbOperationLogMapper;
import com.masiis.shop.dao.platform.user.*;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.*;

/**
 * 店铺订单业务
 * Created by cai_tb on 16/4/14.
 */
@Service
public class OrderService {
    private Logger log = Logger.getLogger(this.getClass());

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
    private ComUserAccountMapper comUserAccountMapper;
    @Resource
    private PfUserBillItemMapper pfUserBillItemMapper;
    @Resource
    private SfShopBillItemMapper shopBillItemMapper;
    @Resource
    private SfShopMapper shopMapper;
    @Resource
    private SfOrderItemDistributionMapper distributionMapper;
    @Resource
    private ComUserAccountRecordMapper comUserAccountRecordMapper;
    @Resource
    private SfUserBillItemMapper sfBillItemMapper;
    @Resource
    private SfUserAccountMapper sfUserAccountMapper;
    @Resource
    private SfUserAccountRecordMapper sfRecordMapper;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private PbOperationLogMapper pbOperationLogMapper;
    @Resource
    private SfShopStatisticsService shopStatisticsService;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private SfUserStatisticsMapper sfUserStatisticsMapper;

    /**
     * 店铺订单列表
     *
     * @param pageNumber
     * @param pageSize
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap,Integer payTypeId) {
        String sort = "create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<SfOrder> sfOrders = sfOrderMapper.selectByMap(conditionMap);
        PageInfo<SfOrder> pageInfo = new PageInfo<>(sfOrders);

        List<Order> orders = new ArrayList<>();
        for (SfOrder sfOrder : sfOrders) {
            ComUser comUser = comUserMapper.selectByPrimaryKey(sfOrder.getUserId());
            SfOrderConsignee sfOrderConsignee = sfOrderConsigneeMapper.getOrdConByOrdId(sfOrder.getId());
            List<SfOrderFreight> sfOrderFreights = sfOrderFreightMapper.selectByOrderId(sfOrder.getId());
            List<SfOrderPayment> sfOrderPayments = sfOrderPaymentMapper.selectBySfOrderId(sfOrder.getId());
            List<SfOrderItem> sfOrderItems = sfOrderItemMapper.getOrderItemByOrderId(sfOrder.getId());
            ComUser shopUser = comUserMapper.selectByPrimaryKey(sfOrder.getShopUserId());//店铺主人

            Order order = new Order();
            order.setComUser(comUser);
            order.setSfOrder(sfOrder);
            order.setSfOrderConsignee(sfOrderConsignee);
            order.setSfOrderFreights(sfOrderFreights);
            order.setSfOrderPayments(sfOrderPayments);
            order.setSfOrderItems(sfOrderItems);
            order.setShopUser(shopUser);

            orders.add(order);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", orders);

        return pageMap;
    }

    /**
     * 店铺待发货订单列表
     *
     * @param pageNumber
     * @param pageSize
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listDeliveryByCondition(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap) {
        String sort = "o.create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> orderMaps = sfOrderMapper.selectDeliveryByCondition(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(orderMaps);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", orderMaps);

        return pageMap;
    }

    /**
     * 获取订单明细
     *
     * @param id
     * @return
     */
    public Order find(Long id) {
        SfOrder sfOrder = sfOrderMapper.selectByPrimaryKey(id);
        ComUser comUser = comUserMapper.selectByPrimaryKey(sfOrder.getUserId());
        ComUser shopUser = comUserMapper.selectByPrimaryKey(sfOrder.getShopUserId());
        SfOrderConsignee sfOrderConsignee = sfOrderConsigneeMapper.getOrdConByOrdId(sfOrder.getId());
        List<SfOrderFreight> sfOrderFreights = sfOrderFreightMapper.selectByOrderId(sfOrder.getId());
        List<SfOrderItem> sfOrderItems = sfOrderItemMapper.getOrderItemByOrderId(sfOrder.getId());

        List<ProductInfo> productInfos = new ArrayList<>();
        for (SfOrderItem sfOrderItem : sfOrderItems) {
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
        order.setShopUser(shopUser);

        if (sfOrder.getPayStatus().intValue() == 1) {
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
     *
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

        SfOrderConsignee sfOrderConsignee = sfOrderConsigneeMapper.getOrdConByOrdId(sfOrder.getId());
        ComUser comUser = comUserMapper.selectByPrimaryKey(sfOrder.getUserId());
        //短信和微信通知
        MobileMessageUtil.getInitialization("C").consumerShipRemind(sfOrderConsignee.getMobile(), sfOrder.getOrderCode(), sfOrderFreight.getShipManName(), sfOrderFreight.getFreight());
        WxSFNoticeUtils.getInstance().orderShipNotice(comUser, new String[]{sfOrder.getOrderCode(), sfOrderFreight.getShipManName(), sfOrderFreight.getFreight()}, PropertiesUtils.getStringValue("mall.domain.name.address") + "/sfOrderManagerController/borderDetils.html?id=" + sfOrder.getId());

        PbOperationLog pbOperationLog = new PbOperationLog();
        pbOperationLog.setOperateIp(InetAddress.getLocalHost().getHostAddress());
        pbOperationLog.setCreateTime(new Date());
        pbOperationLog.setPbUserId(operationUser.getId());
        pbOperationLog.setPbUserName(operationUser.getUserName());
        pbOperationLog.setOperateType(OperationType.Update.getCode());
        pbOperationLog.setRemark("发货");
        pbOperationLog.setOperateContent(sfOrderFreight.toString());
        int updateByPrimaryKey = pbOperationLogMapper.insert(pbOperationLog);
        if(updateByPrimaryKey==0){
            throw new Exception("日志新建店铺发货失败!");
        }
    }

    public void updateOrderStock(SfOrder sfOrder) throws Exception {
        List<SfOrderItem> sfOrderItems = sfOrderItemMapper.getOrderItemByOrderId(sfOrder.getId());
        for (SfOrderItem sfOrderItem : sfOrderItems) {
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(sfOrder.getShopUserId(), sfOrderItem.getSkuId());
            if (pfUserSkuStock.getStock() - sfOrderItem.getQuantity() >= 0 && pfUserSkuStock.getFrozenStock() - sfOrderItem.getQuantity() >= 0) {
                pfUserSkuStockService.updateUserSkuStockWithLog(sfOrderItem.getQuantity(), pfUserSkuStock, sfOrder.getId(), UserSkuStockLogType.shopOrder);
            } else {
                throw new Exception("库存异常!");
            }
        }
    }

    /**
     * 小铺订单退货逻辑
     *
     * @param oid
     * @param res
     */
    @Transactional
    public void sfOrderRefund(Long oid, JSONObject res) {
        try {
            if (oid == null || oid.longValue() <= 0) {
                res.put("resCode", 2);
                res.put("resMsg", "参数格式不正确");
                throw new BusinessException("参数格式不正确");
            }
            SfOrder order = sfOrderMapper.selectByPrimaryKey(oid);
            if (order == null) {
                res.put("resCode", 3);
                res.put("resMsg", "该orderId订单不存在");
                throw new BusinessException("该orderId订单不存在");
            }
            if (order.getOrderStatus().intValue() != SfOrderStatusEnum.ORDER_COMPLETE.getCode().intValue()) {
                res.put("resCode", 4);
                res.put("resMsg", "该订单订单状态不正确,不是" + SfOrderStatusEnum.ORDER_COMPLETE.getDesc());
                throw new BusinessException("该订单订单状态不正确,不是" + SfOrderStatusEnum.ORDER_COMPLETE.getDesc());
            }
            Date receiveTime = order.getReceiptTime();
            if (DateUtil.getDateNextdays(receiveTime, 7).compareTo(new Date()) <= 0) {
                res.put("resCode", 5);
                res.put("resMsg", "该订单收货已超过7天,不予退货");
                throw new BusinessException("该订单收货已超过7天,不予退货");
            }

            // 开始退货逻辑
            log.info("开始退货逻辑......");

            //  获取店主用户
            ComUser shopKeeper = comUserMapper.selectByPrimaryKey(order.getShopUserId());
            // 计算店主待结算中金额(减去分润,减去运费)
            BigDecimal countFee = null;
            if (order.getSendType() == 1) {
                countFee = order.getPayAmount().subtract(order.getDistributionAmount())
                        .subtract(order.getShipAmount());
            } else if (order.getSendType() == 2) {
                countFee = order.getPayAmount().subtract(order.getDistributionAmount());
            } else {
                throw new BusinessException("不合法的拿货方式");
            }

            // 计算回退销售额
            SfShop sfShop = shopMapper.selectByPrimaryKey(order.getShopId());
            sfShop.setSaleAmount(sfShop.getSaleAmount().subtract(order.getPayAmount()));
            int shopRes = shopMapper.updateWithVersion(sfShop);
            if (shopRes != 1) {
                res.put("resCode", 6);
                res.put("resMsg", "该订单退货失败,请重试");
                throw new BusinessException("退货修改店铺总销售额失败");
            }
            log.info("店铺修改店铺的总销售额");

            // 店主account
            ComUserAccount comUserAccount = comUserAccountMapper.findByUserId(order.getShopUserId());

            // 插入退货店主pf_user_bill_item
            SfShopBillItem billItem = createSfShopBillItemBySfOrderRefund(order, shopKeeper, countFee, receiveTime);
            shopBillItemMapper.insert(billItem);
            log.info("店主结算中减少");

            // 退货订单扣除结算中金额计
            ComUserAccountRecord countRecord = createComUserAccountRecordBySfOrder(order, countFee,
                    UserAccountRecordFeeType.SF_Refund_SubCountingFee.getCode(), comUserAccount);
            countRecord.setPrevFee(comUserAccount.getCountingFee());
            comUserAccount.setCountingFee(comUserAccount.getCountingFee().subtract(countFee));
            countRecord.setNextFee(comUserAccount.getCountingFee());

            // 店主此次退货总销售额减少(利润和运费也算销售额)
            ComUserAccountRecord pfIncomeRecord = createComUserAccountRecordBySfOrder(order, order.getPayAmount(),
                    UserAccountRecordFeeType.SF_Refund_SubTotalIncomeFee.getCode(), comUserAccount);
            pfIncomeRecord.setPrevFee(comUserAccount.getTotalIncomeFee());
            comUserAccount.setTotalIncomeFee(comUserAccount.getTotalIncomeFee().subtract(order.getPayAmount()));
            pfIncomeRecord.setNextFee(comUserAccount.getTotalIncomeFee());
            log.info("小铺店主的结算中和总销售额减少金额:" + countFee);

            Set<Long> fenRunUserSet = new HashSet<Long>();
            List<SfOrderItem> sfOrderItems = sfOrderItemMapper.getOrderItemByOrderId(order.getId());
            for (SfOrderItem item : sfOrderItems) {
                // 计算单个item的分销分润
                List<SfOrderItemDistribution> distributions = distributionMapper.selectBySfOrderItemId(item.getId());
                for (SfOrderItemDistribution dis : distributions) {
                    Long userId = dis.getUserId();
                    if (!fenRunUserSet.contains(userId)) {
                        fenRunUserSet.add(userId);
                    }
                }

                // 按照订单子项回退库存
                PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(shopKeeper.getId(), item.getSkuId());
                try {
                    pfUserSkuStockService.updateUserSkuStockWithLog(item.getQuantity(),
                            pfUserSkuStock, order.getId(), UserSkuStockLogType.shopReturn);
                } catch (Exception e) {
                    res.put("resCode", 6);
                    res.put("resMsg", "该订单退货失败,请重试");
                    throw new BusinessException("退货增加用户平台库存失败");
                }
            }
            // 计算店主此次总利润回退
            ComUserAccountRecord profitBefore = comUserAccountRecordMapper.selectByUserAndTypeAndBillId(shopKeeper.getId(),
                    UserAccountRecordFeeType.SF_AddProfitFee.getCode(), order.getId());
            if (profitBefore == null) {
                throw new BusinessException("");
            }
            ComUserAccountRecord pfprofitRecord = cloneComUserAccountRecordByTypeAndHandleType(
                    UserAccountRecordFeeType.SF_Refund_SubProfitFee.getCode(), 0, profitBefore);
            // 设置店主总利润回退
            pfprofitRecord.setPrevFee(comUserAccount.getProfitFee());
            comUserAccount.setProfitFee(comUserAccount.getProfitFee().subtract(profitBefore.getHandleFee()));
            pfprofitRecord.setNextFee(comUserAccount.getProfitFee());
            log.info("小铺店主总利润回退:" + profitBefore.getHandleFee());

            int result = comUserAccountMapper.updateByIdWithVersion(comUserAccount);
            if (result != 1) {
                res.put("resCode", 6);
                res.put("resMsg", "该订单退货失败,请重试");
                throw new BusinessException("小铺店主account修改失败!");
            }
            // 插入变动记录
            comUserAccountRecordMapper.insert(countRecord);
            comUserAccountRecordMapper.insert(pfIncomeRecord);
            comUserAccountRecordMapper.insert(pfprofitRecord);

            log.info("计算小铺订单分润");

            // 计算分销订单的分润
            for (Long sfUserId : fenRunUserSet) {
                SfUserAccount sfUserAccount = sfUserAccountMapper.selectByUserId(sfUserId);
                // 查询之前的分润记录
                SfUserAccountRecord before = sfBillItemMapper.selectByUserIdAndSourceIdAndSubType(sfUserId, order.getId(), 0);
                if (before == null) {
                    throw new BusinessException();
                }
                // 创建退货对应的分润退回记录
                SfUserAccountRecord sfRecord = cloneSfUserAccountRecordByBefore(before, 5);
                BigDecimal fee = before.getHandleFee();
                // 创建退货分润退回账单子项
                SfUserBillItem sfBillItem = createSfUserBillItemByType(order, sfUserAccount, fee, before.getHandleTime());
                sfBillItemMapper.insert(sfBillItem);
                // 计算分润用户结算中变动
                sfRecord.setPrevFee(sfUserAccount.getCountingFee());
                sfUserAccount.setCountingFee(sfUserAccount.getCountingFee().subtract(fee));
                sfRecord.setNextFee(sfUserAccount.getCountingFee());
                sfRecordMapper.insert(sfRecord);
                int resNum = sfUserAccountMapper.updateByIdAndVersion(sfUserAccount);
                if (resNum != 1) {
                    res.put("resCode", 6);
                    res.put("resMsg", "该订单退货失败,请重试");
                    throw new BusinessException("用户id:" + sfUserId + ",分润退回失败");
                }
                SfUserStatistics sfUserStatistics = sfUserStatisticsMapper.selectByUserId(sfUserId);
                sfUserStatistics.setDistributionFee(fee);

                log.info("分润金额回退成功,用户id:{" + sfUserId + "}" + ",分润金额:{" + fee + "}");
            }

            // 修改店铺统计信息
            updateShopUserStatisticsByReturn(order, sfOrderItems);
            // 修改新店主结算中金额
            updateDisBillAmount(order, sfOrderItems);
            // 修改订单购买人统计信息
            SfUserStatistics statistics = sfUserStatisticsMapper.selectByUserId(order.getUserId());
            //总订单数
            statistics.setOrderCount(statistics.getOrderCount()+1);
            //总购买金额(总购买金额 = 订单金额 - 订单代理商运费)
            statistics.setBuyFee(statistics.getBuyFee().add(order.getOrderAmount()).subtract(order.getAgentShipAmount()));
            int i = sfUserStatisticsMapper.updateByIdAndVersion(statistics);
            if (i != 1){
                throw new BusinessException("更新购买人统计信息失败------购买人id---"+order.getUserId());
            }

            // 修改订单状态
            order.setOrderStatus(SfOrderStatusEnum.ORDER_REFUNDED.getCode());
            sfOrderMapper.updateByPrimaryKey(order);
            // 添加订单操作日志
            SfOrderOperationLog sfOrderOperationLog = new SfOrderOperationLog();
            sfOrderOperationLog.setCreateMan(0l);
            sfOrderOperationLog.setCreateTime(new Date());
            sfOrderOperationLog.setSfOrderStatus(SfOrderStatusEnum.ORDER_REFUNDED.getCode());
            sfOrderOperationLog.setSfOrderId(order.getId());
            sfOrderOperationLog.setRemark("小铺订单退货");
            sfOrderOperationLogMapper.insert(sfOrderOperationLog);

            res.put("resCode", 0);
            res.put("resMsg", "该订单退货完成");
            log.info("小铺订单退货完成");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    /**
     * 退货操作,小铺用户人结算中信息,回退结算中
     * 结算中(结算中 = 之前结算中 + 利润 )
     * @param order
     * @param orderItems
     */
    private void updateDisBillAmount(SfOrder order,List<SfOrderItem> orderItems){
        ComUserAccount comUserAccount = comUserAccountMapper.findByUserId(order.getShopUserId());
        if (comUserAccount != null){
            BigDecimal sumProfitFee = getShopProfitfee(order,orderItems);
            comUserAccount.setDistributionBillAmount(comUserAccount.getDistributionBillAmount().subtract(sumProfitFee));
            comUserAccountMapper.updateByIdWithVersion(comUserAccount);
        }
    }

    /**
     * 此订单小铺获得利润
     * 小铺商品的利润 = 商品购买价格 - 商品的代理价格 - 商品的分润
     * @param order
     * @param orderItems
     * @return
     */
    private BigDecimal getShopProfitfee(SfOrder order,List<SfOrderItem> orderItems){
        BigDecimal sumProfitFee = BigDecimal.ZERO;
        PfUserSku pUserSku = null;
        PfSkuAgent pSkuAgent = null;
        for (SfOrderItem orderItem:orderItems){
            pUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(order.getShopUserId(), orderItem.getSkuId());
            pSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(orderItem.getSkuId(), pUserSku.getAgentLevelId());
            BigDecimal unit_profit = orderItem.getUnitPrice().subtract(pSkuAgent.getUnitPrice());
            sumProfitFee = sumProfitFee.add(unit_profit.multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }
        sumProfitFee = sumProfitFee.subtract(order.getDistributionAmount());
        return sumProfitFee;
    }

    /**
     * 退货操作,小铺统计信息减少
     *
     * @param order
     * @param orderItems
     */
    private void updateShopUserStatisticsByReturn(SfOrder order,List<SfOrderItem> orderItems){
        //获得小铺统计信息
        SfShopStatistics shopStatistics = shopStatisticsService.selectByShopUserId(order.getShopUserId());
        if (shopStatistics != null){
            //总销售额(总销售额 = 订单金额 - 订单的代理运费)
            shopStatistics.setIncomeFee(shopStatistics.getIncomeFee().subtract((order.getOrderAmount()).subtract(order.getAgentShipAmount())));
            //总利润
            BigDecimal sumProfitFee = getShopProfitfee(order,orderItems);
            shopStatistics.setProfitFee(shopStatistics.getProfitFee().subtract(sumProfitFee));
            //店铺总销量
            Integer toatalQuantity = new Integer(0);
            for (SfOrderItem orderItem : orderItems){
                toatalQuantity = toatalQuantity + orderItem.getQuantity();
            }
            shopStatistics.setProductCount(shopStatistics.getProductCount() - toatalQuantity);
            shopStatisticsService.updateByIdAndVersion(shopStatistics);
        }

    }

    private SfShopBillItem createSfShopBillItemBySfOrderRefund(SfOrder order, ComUser shopKeeper, BigDecimal countFee, Date receiveTime) {
        SfShopBillItem item = new SfShopBillItem();

        item.setSourceId(order.getId());
        item.setShopId(order.getShopId());
        item.setComUserId(shopKeeper.getId());
        item.setCreateTime(new Date());
        item.setSourceCreateTime(order.getCreateTime());
        item.setAmount(countFee);
        item.setItemType(2);
        item.setIsCount(0);

        return item;
    }

    private SfUserBillItem createSfUserBillItemByType(SfOrder order, SfUserAccount sfUserAccount, BigDecimal fee, Date opTime) {
        SfUserBillItem sfItem = new SfUserBillItem();

        sfItem.setAmount(fee);
        sfItem.setComUserId(sfUserAccount.getUserId());
        sfItem.setCreateMan(order.getShopUserId());
        sfItem.setCreateTime(opTime);
        sfItem.setSourceId(order.getId());
        sfItem.setSourceCreateTime(order.getCreateTime());
        sfItem.setRemark("小铺订单退货,分润退回");
        sfItem.setItemType(1);
        sfItem.setItemSubType(3);
        sfItem.setIsCount(0);

        return sfItem;
    }

    private SfUserAccountRecord cloneSfUserAccountRecordByBefore(SfUserAccountRecord before, int feeType) {
        SfUserAccountRecord record = new SfUserAccountRecord();

        record.setHandler(before.getHandler());
        record.setSourceId(before.getSourceId());
        record.setSfUserAccountId(before.getSfUserAccountId());
        record.setHandleTime(new Date());
        record.setComUserId(before.getComUserId());
        record.setFeeType(feeType);
        record.setHandleFee(before.getHandleFee());
        record.setHandleSerialNum(SysBeanUtils.createSfAccountRecordSerialNum());
        record.setHandleType(0);

        return record;
    }

    private ComUserAccountRecord cloneComUserAccountRecordByTypeAndHandleType(Integer feeType, int handleType, ComUserAccountRecord before) {
        ComUserAccountRecord record = new ComUserAccountRecord();

        record.setHandleSerialNum(SysBeanUtils.createAccountRecordSerialNum(0));
        record.setBillId(before.getBillId());
        record.setHandleFee(before.getHandleFee());
        record.setHandleType(handleType);
        record.setFeeType(feeType);
        record.setComUserId(before.getComUserId());
        record.setHandler(before.getHandler());
        record.setUserAccountId(before.getUserAccountId());
        record.setHandleTime(new Date());

        return record;
    }

    private ComUserAccountRecord createComUserAccountRecordBySfOrder(SfOrder order, BigDecimal countFee, Integer fee_type, ComUserAccount comAccount) {
        ComUserAccountRecord comRecord = new ComUserAccountRecord();

        comRecord.setHandleSerialNum(SysBeanUtils.createAccountRecordSerialNum(0));
        comRecord.setBillId(order.getId());
        comRecord.setHandleFee(countFee);
        comRecord.setHandleType(0);
        comRecord.setFeeType(fee_type);
        comRecord.setComUserId(order.getShopUserId());
        comRecord.setHandler(String.valueOf(order.getShopUserId()));
        comRecord.setUserAccountId(comAccount.getId());
        comRecord.setHandleTime(new Date());

        return comRecord;
    }


    /**
     * 根据小铺订单插入代理账单子项
     *
     * @param order
     * @param shopKeeper
     * @param opTime     收货时间
     * @return
     */
    private PfUserBillItem createPfUserBillItemBySfOrderRefund(SfOrder order, ComUser shopKeeper,
                                                               BigDecimal countFee, Date opTime) {
        PfUserBillItem item = new PfUserBillItem();

        item.setPfBorderId(order.getId());
        item.setUserId(shopKeeper.getId());
        item.setCreateDate(opTime);
        item.setOrderCreateDate(order.getCreateTime());
        item.setOrderPayAmount(countFee);
        item.setOrderType(1);
        item.setOrderSubType(1);
        item.setIsCount(0);

        return item;
    }

    /**
     * 查找店铺订单
     * @param sfOrderId
     * @return
     */
    public SfOrder findById(Long sfOrderId){
        return sfOrderMapper.selectByPrimaryKey(sfOrderId);
    }
}
