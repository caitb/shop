package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.enums.UserAccountRecordFeeType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderRecommenRewardMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountRecordMapper;
import com.masiis.shop.dao.platform.user.PfUserBillItemMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lzh on 2016/3/19.
 */
@Service
public class ComUserAccountService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ComUserAccountMapper accountMapper;
    @Resource
    private PfUserBillItemMapper itemMapper;
    @Resource
    private ComUserAccountRecordMapper recordMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private PfBorderRecommenRewardMapper recommenRewardMapper;

    /**
     * 根据用户id查询用户资产账户
     *
     * @param id
     * @return
     */
    public ComUserAccount findAccountByUserid(Long id) {
        return accountMapper.findByUserId(id);
    }
    public int updateByIdWithVersion(ComUserAccount comUserAccount){
        return accountMapper.updateByIdWithVersion(comUserAccount);
    }


    /**
     * 创建用户之初,创建用户的资产对象
     *
     * @param user
     */
    public void createAccountByUser(ComUser user) {
        ComUserAccount account = new ComUserAccount();
        account.setComUserId(user.getId());
        account.setCostFee(new BigDecimal(0));
        account.setCountingFee(new BigDecimal(0));
        account.setExtractableFee(new BigDecimal(0));
        account.setBailFee(new BigDecimal(0));
        account.setCreatedTime(new Date());
        account.setTotalIncomeFee(new BigDecimal(0));
        account.setProfitFee(new BigDecimal(0));
        account.setAgentBillAmount(new BigDecimal(0));
        account.setDistributionBillAmount(new BigDecimal(0));
        account.setRecommenBillAmount(new BigDecimal(0));
        account.setVersion(0L);
        accountMapper.insert(account);
    }

    /**
     * 订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项
     *
     * @param order
     */
    @Transactional
    public void countingByOrder(PfBorder order) {
        try {
            // 验证订单的状态
            Integer orderType = order.getOrderType();
            Integer orderStatus = order.getOrderStatus();
            if (orderType.intValue() != 0 && orderType.intValue() != 1&& orderType.intValue() != 3) {
                throw new BusinessException("订单类型不正确,当前订单类型为:" + orderType);
            }
            if (orderStatus != 3) {
                throw new BusinessException("订单状态不正确,当前订单状态为:" + orderStatus);
            }

            BigDecimal orderPayment = order.getPayAmount().subtract(order.getBailAmount());
            BigDecimal pUserCountAmount = order.getPayAmount().subtract(order.getBailAmount()).subtract(order.getRecommenAmount());

            log.info("订单计入计算的金额是:" + orderPayment.doubleValue());
            Long userPId = order.getUserPid();
            Long userId = order.getUserId();
            // 获取对应的account记录
            if (userPId != 0) {
                log.info("订单类型和状态校验通过,进行创建账单子项工作!");
                // 创建对应的bill_item
                PfUserBillItem item = createBillItemByBOrder(order);
                itemMapper.insert(item);

                log.info("账单子项创建成功,账单子项金额为:" + item.getOrderPayAmount());

                ComUserAccount account = accountMapper.findByUserId(userPId);
                log.info("增加上级结算中金额");
                ComUserAccountRecord recordC = createAccountRecord(pUserCountAmount, account, order.getId(), UserAccountRecordFeeType.AddCountingFee);
                recordC.setPrevFee(account.getCountingFee());
                account.setCountingFee(account.getCountingFee().add(pUserCountAmount));
                recordC.setNextFee(account.getCountingFee());
                recordMapper.insert(recordC);
                log.info("增加上级总销售额");
                ComUserAccountRecord recordT = createAccountRecord(orderPayment, account, order.getId(), UserAccountRecordFeeType.AddTotalIncomeFee);
                recordT.setPrevFee(account.getTotalIncomeFee());
                account.setTotalIncomeFee(account.getTotalIncomeFee().add(orderPayment));
                recordT.setNextFee(account.getTotalIncomeFee());
                recordMapper.insert(recordT);
                log.info("增加上级总利润");
                PfUserSku pUserSku = null;
                PfSkuAgent pSkuAgent = null;
                BigDecimal sumProfitFee = BigDecimal.ZERO;
                for (PfBorderItem pfBorderItem : pfBorderItemMapper.getPfBorderItemDetail(order.getId())) {
                    pUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userPId, pfBorderItem.getSkuId());
                    pSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(pfBorderItem.getSkuId(), pUserSku.getAgentLevelId());
                    BigDecimal unit_profit = pfBorderItem.getUnitPrice().subtract(pSkuAgent.getUnitPrice());
                    sumProfitFee = sumProfitFee.add(unit_profit.multiply(BigDecimal.valueOf(pfBorderItem.getQuantity())));
                }
                // 减去推荐奖励
                sumProfitFee = sumProfitFee.subtract(order.getRecommenAmount());
                log.info("开始修改利润");
                ComUserAccountRecord recordP = createAccountRecord(sumProfitFee, account, order.getId(), UserAccountRecordFeeType.AddProfitFee);
                recordP.setPrevFee(account.getProfitFee());
                account.setProfitFee(account.getProfitFee().add(sumProfitFee));
                recordP.setNextFee(account.getProfitFee());
                recordMapper.insert(recordP);
                log.info("插入总销售额的变动流水!");
                log.info("个人账户数据:" + account.toString());
                int type = accountMapper.updateByIdWithVersion(account);
                if (type != 1) {
                    throw new BusinessException("修改出货方结算金额和总销售额失败!");
                }
                log.info("更新出货人账户结算额和总销售额成功!");
            }

            BigDecimal recommon = order.getRecommenAmount();
            if(recommon != null && recommon.compareTo(BigDecimal.ZERO) > 0){
                log.info("计算推荐奖励");
                // 查询要计算的推荐奖励明细
                List<PfBorderRecommenReward> rewards = recommenRewardMapper.selectByPfBorderId(order.getId());
                if(rewards != null && rewards.size() > 0){
                    // 循环处理推荐奖励
                    for(PfBorderRecommenReward reward:rewards){
                        log.info("创建推荐奖励账单子项,奖励用户:" + reward.getRecommenUserId()
                                + ",推荐奖励额度:" + reward.getRewardTotalPrice());
                        PfUserBillItem rewardItem = createBillItemByRecommenReward(reward);
                        itemMapper.insert(rewardItem);
                    }
                }
            }

            log.info("开始给进货人增加成本");

            ComUserAccount accountS = accountMapper.findByUserId(userId);
            log.info("增加本级总成本");
            ComUserAccountRecord recordCostFee = createAccountRecord(orderPayment, accountS, order.getId(), UserAccountRecordFeeType.AddCostFee);
            recordCostFee.setPrevFee(accountS.getCostFee());
            accountS.setCostFee(accountS.getCostFee().add(orderPayment));
            recordCostFee.setNextFee(accountS.getCostFee());
            recordMapper.insert(recordCostFee);
            log.info("增加本级保证金");
            ComUserAccountRecord recordBailFee = createAccountRecord(order.getBailAmount(), accountS, order.getId(), UserAccountRecordFeeType.AddBailFee);
            recordBailFee.setPrevFee(accountS.getBailFee());
            accountS.setBailFee(accountS.getBailFee().add(order.getBailAmount()));
            recordBailFee.setNextFee(accountS.getBailFee());
            recordMapper.insert(recordBailFee);
            int typeS = accountMapper.updateByIdWithVersion(accountS);
            if (typeS != 1) {
                throw new BusinessException("修改进货方成本账户失败!");
            }
            log.info("更新进货方成本账户成功!");
        } catch (Exception e) {
            log.error("订单完成进行账户总销售额和结算金额操作错误," + e.getMessage(), e);
            throw new BusinessException("订单完成进行账户总销售额和结算金额操作错误");
        }
    }

    /**
     * 创建推荐奖励结算账单子项
     *
     * @param reward
     * @return
     */
    private PfUserBillItem createBillItemByRecommenReward(PfBorderRecommenReward reward) {
        PfUserBillItem item = new PfUserBillItem();

        item.setCreateDate(new Date());
        item.setOrderCreateDate(reward.getCreateTime());
        item.setOrderPayAmount(reward.getRewardTotalPrice());
        item.setOrderSubType(2);
        item.setOrderType(0);
        item.setPfBorderId(reward.getPfBorderId());
        item.setUserId(reward.getRecommenUserId());
        item.setIsCount(0);

        return item;
    }

    /**
     * 创建结算账单子项
     *
     * @param order
     * @return
     */
    private PfUserBillItem createBillItemByBOrder(PfBorder order) {
        PfUserBillItem item = new PfUserBillItem();

        item.setCreateDate(new Date());
        item.setOrderCreateDate(order.getCreateTime());
        item.setOrderPayAmount(order.getPayAmount().subtract(order.getBailAmount()));
        item.setOrderSubType(0);
        item.setOrderType(0);
        item.setPfBorderId(order.getId());
        item.setUserId(order.getUserPid());
        item.setIsCount(0);
        return item;
    }

    /**
     * 创建结算记录对象
     *
     * @param payment
     * @param account
     * @param billId
     * @param userAccountRecordFeeType 枚举类型
     * @return
     */
    public ComUserAccountRecord createAccountRecord(BigDecimal payment,
                                                     ComUserAccount account,
                                                     Long billId,
                                                     UserAccountRecordFeeType userAccountRecordFeeType) {
        ComUserAccountRecord comUserAccountRecord = new ComUserAccountRecord();
        comUserAccountRecord.setUserAccountId(account.getId());
        comUserAccountRecord.setFeeType(userAccountRecordFeeType.getCode());
        comUserAccountRecord.setHandleFee(payment);
        comUserAccountRecord.setBillId(billId);
        comUserAccountRecord.setComUserId(account.getComUserId());
        comUserAccountRecord.setHandleType(0);
        comUserAccountRecord.setHandleSerialNum(SysBeanUtils.createAccountRecordSerialNum(userAccountRecordFeeType.getCode()));
        comUserAccountRecord.setHandleTime(new Date());
        return comUserAccountRecord;
    }

    public List<Map<String, Object>> getPfCountindFee(Long userId){
        return itemMapper.selectPfCountindFee(userId);
    }
}

