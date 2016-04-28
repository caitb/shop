package com.masiis.shop.scheduler.mall.service.user;

import com.masiis.shop.common.enums.UserAccountRecordFeeType;
import com.masiis.shop.common.enums.mall.SfOrderStatusEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.mall.order.SfOrderItemDistributionMapper;
import com.masiis.shop.dao.mall.order.SfOrderItemMapper;
import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.mall.user.SfUserAccountRecordMapper;
import com.masiis.shop.dao.mall.user.SfUserBillItemMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountRecordMapper;
import com.masiis.shop.dao.platform.user.PfUserBillItemMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.scheduler.platform.service.user.ComUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangbingjian on 2016/4/8.
 */
@Service
public class SfUserAccountService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfUserAccountMapper sfUserAccountMapper;
    @Resource
    private SfOrderMapper orderMapper;
    @Resource
    private ComUserService userService;
    @Resource
    private ComUserAccountMapper comUserAccountMapper;
    @Resource
    private ComUserAccountRecordMapper comUserAccountRecordMapper;
    @Resource
    private SfOrderItemMapper sfOrderItemMapper;
    @Resource
    private ComSkuMapper skuMapper;
    @Resource
    private PfSkuAgentMapper skuAgentMapper;
    @Resource
    private PfUserSkuMapper userSkuMapper;
    @Resource
    private SfOrderItemDistributionMapper distributionMapper;
    @Resource
    private SfUserAccountRecordMapper sfRecordMapper;
    @Resource
    private PfUserBillItemMapper billItemMapper;
    @Resource
    private SfUserBillItemMapper sfBillItemMapper;
    @Resource
    private SfShopMapper shopMapper;

    /**
     * 根据用户id查询分销用户账户表
     * @param userId
     * @return
     */
    public SfUserAccount findAccountByUserId(Long userId){
        return sfUserAccountMapper.selectByUserId(userId);
    }

    /**
     * 根据ComUser创建小铺端用户账户对象
     *
     * @param user
     */
    public void createSfAccountByUser(ComUser user) {
        SfUserAccount account = new SfUserAccount();

        account.setCreateTime(new Date());
        account.setExtractableFee(new BigDecimal(0));
        account.setCountingFee(new BigDecimal(0));
        account.setUserId(user.getId());
        account.setVersion(0L);

        sfUserAccountMapper.insert(account);
    }

    /**
     * 结算小铺订单
     *
     * @param order
     */
    @Transactional
    public void countingSfOrder(SfOrder order){
        try{
            if(order == null || order.getId() == null){
                log.error("传入订单对象为null");
                throw new BusinessException();
            }
            order = orderMapper.selectByPrimaryKey(order.getId());
            if(order.getOrderType() != 0){
                log.error("订单类型不匹配");
                throw new BusinessException();
            }
            if(order.getOrderStatus().intValue() != SfOrderStatusEnum.ORDER_SHIPED.getCode().intValue()
                    || order.getPayStatus() != 1){
                log.error("订单状态不匹配,订单不是" + SfOrderStatusEnum.ORDER_SHIPED.getDesc() + "状态");
                throw new BusinessException("订单状态不匹配,订单不是"
                        + SfOrderStatusEnum.ORDER_SHIPED.getDesc() + "状态");
            }

            // 计算店主的各种钱
            //  获取店主用户
            ComUser shopKeeper = userService.getUserById(order.getShopUserId());
            // 计算店主待结算中金额(减去分润,减去运费)
            BigDecimal countFee = null;
            if(order.getSendType() == 1){
                countFee = order.getPayAmount().subtract(order.getDistributionAmount())
                        .subtract(order.getShipAmount());
            } else if(order.getSendType() == 2){
                countFee = order.getPayAmount().subtract(order.getDistributionAmount());
            } else {
                throw new BusinessException("不合法的拿货方式");
            }

            log.info("计算店铺的总销售额");

            SfShop sfShop = shopMapper.selectByPrimaryKey(order.getShopId());
            sfShop.setSaleAmount(sfShop.getSaleAmount().add(countFee));
            sfShop.setShipAmount(sfShop.getShipAmount().add(order.getShipAmount()));
            shopMapper.updateWithVersion(sfShop);

            log.info("计算店主的结算中金额增加,总销售额增加,总利润增加");
            // 店主account
            ComUserAccount comUserAccount = comUserAccountMapper.findByUserId(order.getShopUserId());

            // 插入店主pf_user_bill_item
            PfUserBillItem billItem = createPfUserBillItemBySfOrder(order, shopKeeper, countFee);
            billItemMapper.insert(billItem);

            // 计算订单结算中金额计入到account中
            ComUserAccountRecord countRecord = createComUserAccountRecordBySfOrder(order, countFee,
                    UserAccountRecordFeeType.SF_AddCountingFee.getCode(), comUserAccount);
            countRecord.setPrevFee(comUserAccount.getCountingFee());
            comUserAccount.setCountingFee(comUserAccount.getCountingFee().add(countFee));
            countRecord.setNextFee(comUserAccount.getCountingFee());

            // 计算店主此次总销售额(利润和运费也算销售额)
            ComUserAccountRecord pfIncomeRecord = createComUserAccountRecordBySfOrder(order, order.getPayAmount(),
                    UserAccountRecordFeeType.SF_AddTotalIncomeFee.getCode(), comUserAccount);
            pfIncomeRecord.setPrevFee(comUserAccount.getTotalIncomeFee());
            comUserAccount.setTotalIncomeFee(comUserAccount.getTotalIncomeFee().add(order.getPayAmount()));
            pfIncomeRecord.setNextFee(comUserAccount.getTotalIncomeFee());

            log.info("小铺店主的结算中和总销售额增加金额:" + countFee);

            // 小铺店主利润
            BigDecimal profit = new BigDecimal(0);
            // 每个分润用户的分润金额map,key:userId;value:fee
            Map<Long, BigDecimal> fenRunUserFeeMap = new HashMap<Long, BigDecimal>();
            List<SfOrderItem> sfOrderItems = sfOrderItemMapper.getOrderItemByOrderId(order.getId());
            for(SfOrderItem item:sfOrderItems) {
                // 计算单个item的小铺店主利润
                profit = profit.add(calculateShopkeeperProfitBySfOrder(item, shopKeeper));

                // 计算单个item的分销分润
                List<SfOrderItemDistribution> distributions = distributionMapper.selectBySfOrderItemId(item.getId());
                for(SfOrderItemDistribution dis:distributions){
                    Long userId = dis.getUserId();
                    BigDecimal curFee = new BigDecimal(0);
                    if(fenRunUserFeeMap.containsKey(userId)){
                        curFee = fenRunUserFeeMap.get(userId);
                    }
                    fenRunUserFeeMap.put(userId, curFee.add(dis.getDistributionAmount()));
                    dis.setIsCounting(1);
                    distributionMapper.updateByPrimaryKey(dis);
                }
            }
            profit = profit.subtract(order.getDistributionAmount());
            if(profit.compareTo(BigDecimal.ZERO) < 0){
                profit = BigDecimal.ZERO;
            }
            // 计算店主此次总利润
            ComUserAccountRecord pfprofitRecord = createComUserAccountRecordBySfOrder(order, profit,
                    UserAccountRecordFeeType.SF_AddProfitFee.getCode(), comUserAccount);
            // 设置店主总利润
            pfprofitRecord.setPrevFee(comUserAccount.getProfitFee());
            comUserAccount.setProfitFee(comUserAccount.getProfitFee().add(profit));
            pfprofitRecord.setNextFee(comUserAccount.getProfitFee());

            log.info("小铺店主总利润增加:" + profit);

            int res = comUserAccountMapper.updateByIdWithVersion(comUserAccount);
            if(res != 1){
                throw new BusinessException("小铺店主account修改失败!");
            }
            // 插入变动记录
            comUserAccountRecordMapper.insert(countRecord);
            comUserAccountRecordMapper.insert(pfIncomeRecord);
            comUserAccountRecordMapper.insert(pfprofitRecord);

            log.info("计算小铺订单分润");

            // 计算分销订单的分润
            for(Long sfUserId:fenRunUserFeeMap.keySet()){
                BigDecimal fee = fenRunUserFeeMap.get(sfUserId);
                SfUserAccount sfUserAccount = sfUserAccountMapper.selectByUserId(sfUserId);
                SfUserBillItem sfBillItem = createSfUserBillItem(order, sfUserAccount, fee);
                sfBillItemMapper.insert(sfBillItem);
                // 计算分润用户结算中变动
                SfUserAccountRecord sfRecord = createSfUserAccountRecordByAccount(sfUserAccount,
                        fee, shopKeeper, order);
                sfRecord.setPrevFee(sfUserAccount.getCountingFee());
                sfUserAccount.setCountingFee(sfUserAccount.getCountingFee().add(fee));
                sfRecord.setNextFee(sfUserAccount.getCountingFee());
                sfRecordMapper.insert(sfRecord);
                sfUserAccountMapper.updateByIdAndVersion(sfUserAccount);

                log.info("用户id:{" + sfUserId + "}" + ",分润金额:{" + fee + "}");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e);
        }
    }

    private SfUserBillItem createSfUserBillItem(SfOrder order, SfUserAccount sfUserAccount, BigDecimal fee) {
        SfUserBillItem sfItem = new SfUserBillItem();

        sfItem.setAmount(fee);
        sfItem.setComUserId(sfUserAccount.getUserId());
        sfItem.setCreateMan(order.getShopUserId());
        sfItem.setCreateTime(new Date());
        sfItem.setSourceId(order.getId());
        sfItem.setSourceCreateTime(order.getCreateTime());
        sfItem.setRemark("用户订单分润");
        sfItem.setItemType(1);
        sfItem.setItemSubType(2);

        return sfItem;
    }

    /**
     * 根据小铺订单插入代理账单子项
     *
     * @param order
     * @param shopKeeper
     * @return
     */
    private PfUserBillItem createPfUserBillItemBySfOrder(SfOrder order, ComUser shopKeeper, BigDecimal countFee) {
        PfUserBillItem item = new PfUserBillItem();

        item.setPfBorderId(order.getId());
        item.setUserId(shopKeeper.getId());
        item.setCreateDate(new Date());
        item.setOrderCreateDate(order.getCreateTime());
        item.setOrderPayAmount(countFee);
        item.setOrderType(1);
        item.setOrderSubType(0);
        item.setIsCount(0);

        return item;
    }

    private SfUserAccountRecord createSfUserAccountRecordByAccount(SfUserAccount sfUserAccount,
                                          BigDecimal fee, ComUser shopkeeper, SfOrder order) {
        SfUserAccountRecord record = new SfUserAccountRecord();

        record.setHandler(String.valueOf(shopkeeper.getId()));
        record.setSourceId(order.getId());
        record.setSfUserAccountId(sfUserAccount.getId());
        record.setHandleTime(new Date());
        record.setComUserId(sfUserAccount.getUserId());
        record.setFeeType(0);
        record.setHandleFee(fee);
        record.setHandleSerialNum(SysBeanUtils.createSfAccountRecordSerialNum());
        record.setHandleType(0);

        return record;
    }

    /**
     * 根据小铺订单计算此订单给店主带来的利润
     *
     * @param item
     * @param shopKeeper
     * @return
     */
    private BigDecimal calculateShopkeeperProfitBySfOrder(SfOrderItem item, ComUser shopKeeper) {
        // 查询小铺店主的代理信息
        ComSku sku = skuMapper.findBySkuId(item.getSkuId());
        PfUserSku userSku = userSkuMapper.selectByUserIdAndSkuId(shopKeeper.getId(), sku.getId());
        PfSkuAgent skuAgent = skuAgentMapper.selectBySkuIdAndLevelId(sku.getId(), userSku.getAgentLevelId());
        BigDecimal itemProfit = sku.getPriceRetail().multiply(BigDecimal.ONE.subtract(skuAgent.getDiscount()));
        if(itemProfit.compareTo(BigDecimal.ZERO) < 0){
            itemProfit = BigDecimal.ZERO;
        }
        return itemProfit;
    }

    private ComUserAccountRecord createComUserAccountRecordBySfOrder(SfOrder order, BigDecimal countFee,
                                                 Integer fee_type, ComUserAccount comAccount) {
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

    public static void main(String[] args){
        BigDecimal aa = new BigDecimal(1);
        System.out.println(aa);
        aa = aa.add(new BigDecimal(2));
        System.out.println(aa);
    }
}
