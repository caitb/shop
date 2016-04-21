package com.masiis.shop.scheduler.mall.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.mall.user.SfUserAccountRecordMapper;
import com.masiis.shop.dao.mall.user.SfUserBillItemMapper;
import com.masiis.shop.dao.mall.user.SfUserBillMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Date:2016/4/14
 * @auth:lzh
 */
@Service
public class SfUserBillService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfUserBillMapper billMapper;
    @Resource
    private SfUserBillItemMapper itemMapper;
    @Resource
    private SfOrderMapper orderMapper;
    @Resource
    private SfUserAccountMapper accountMapper;
    @Resource
    private SfUserAccountRecordMapper sfRecordMapper;

    public void createBillByUserAndDate(ComUser user, Date countStartDay, Date countEndDay, Date balanceDate) {
        try{
            // 组织账单对象
            SfUserBill bill = createBillBean(user, countStartDay, countEndDay, balanceDate);
            billMapper.insert(bill);

            log.info("日账单记录创建成功,日账单id:" + bill.getId());

            // 根据用户来查询小铺账单子项
            List<SfUserBillItem> items = itemMapper.selectByUserAndDate(user.getId(), countStartDay, countEndDay);
            for(SfUserBillItem item : items){
                item.setSfUserBillId(bill.getId());
                log.info("计算账单子项,子项id:" + item.getId());
                if(item.getItemType() == 1){
                    // 代理订单
                    SfOrder order = orderMapper.selectByPrimaryKey(item.getSourceId());
                    order.setIsCounting(1);
                    orderMapper.updateByPrimaryKey(order);
                }
                if (item.getItemSubType() == 1) {
                    // 退货

                } else if(item.getItemSubType() == 2) {
                    // 分润类型
                    log.info("此账单子项是分润订单,分润额是:" + item.getAmount());
                    bill.setBillAmount(bill.getBillAmount().add(item.getAmount()));
                    bill.setCountAmount(bill.getCountAmount().add(item.getAmount()));
                } else if(item.getItemSubType() == 3) {
                    // 退货分润退款
                    log.info("此账单子项是分润退款,分润退款额是:" + item.getAmount());
                    bill.setCountAmount(bill.getCountAmount().subtract(item.getAmount()));
                    bill.setReturnAmount(bill.getReturnAmount().add(item.getAmount()));
                }
                item.setIsCount(1);
                itemMapper.updateByPrimaryKey(item);
            }

            // 修改账单状态
            bill.setStatus(1);
            // 修改account账户结算额和可提现额
            SfUserAccount account = accountMapper.selectByUserId(user.getId());
            SfUserAccountRecord record = createAccountRecord(account, bill, 1);
            // 修改结算
            log.info("修改账户的结算金额,之前结算金额是:" + account.getCountingFee());
            record.setPrevFee(account.getCountingFee());
            account.setCountingFee(account.getCountingFee().subtract(bill.getCountAmount()));
            log.info("修改账户的结算金额,之后结算金额是:" + account.getCountingFee());
            record.setNextFee(account.getCountingFee());
            sfRecordMapper.insert(record);
            // 修改可提现
            SfUserAccountRecord recordEx = createAccountRecord(account, bill, 3);
            log.info("修改账户的可提现金额,之前可提现金额是:" + account.getExtractableFee());
            recordEx.setPrevFee(account.getExtractableFee());
            account.setExtractableFee(account.getExtractableFee().add(bill.getCountAmount()));
            log.info("修改账户的可提现金额,之后可提现金额是:" + account.getExtractableFee());
            recordEx.setNextFee(account.getExtractableFee());
            sfRecordMapper.insert(recordEx);
            log.info("添加资产账户操作记录成功!");

            int changeSize = accountMapper.updateByIdAndVersion(account);
            if(changeSize != 1){
                throw new BusinessException("");
            }
            billMapper.updateByPrimaryKey(bill);
            log.info("修改用户资产账户结算中和可提现额成功!");
        } catch (Exception e) {
            log.error("创建日账单失败," + e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    private SfUserAccountRecord createAccountRecord(SfUserAccount account, SfUserBill bill, int type) {
        SfUserAccountRecord record = new SfUserAccountRecord();

        record.setHandler("0");
        record.setSourceId(bill.getId());
        record.setSfUserAccountId(account.getId());
        record.setHandleTime(new Date());
        record.setComUserId(account.getUserId());
        record.setFeeType(0);
        record.setHandleFee(bill.getCountAmount());
        record.setHandleSerialNum(SysBeanUtils.createSfAccountRecordSerialNum());
        record.setHandleType(0);

        return record;
    }

    /**
     * 创建日账单对象
     *
     * @param user
     * @param countStartDay
     * @param countEndDay
     * @param balanceDate
     * @return
     */
    private SfUserBill createBillBean(ComUser user, Date countStartDay, Date countEndDay, Date balanceDate) {
        SfUserBill bill = new SfUserBill();

        bill.setBillAmount(new BigDecimal(0));
        bill.setCreateTime(new Date());
        bill.setCountingDate(balanceDate);
        bill.setCreateMan(0L);
        bill.setCountAmount(new BigDecimal(0));
        bill.setComUserId(user.getId());
        bill.setReturnAmount(new BigDecimal(0));
        bill.setSourceStartTime(countStartDay);
        bill.setSourceEndTime(countEndDay);
        bill.setStatus(0);

        return bill;
    }

    /**
     * 根据日期区间查询账单数量
     *
     * @param countStartDay
     * @param countEndDay
     * @return
     */
    public Long queryBillNumsByDate(Date countStartDay, Date countEndDay) {
        return billMapper.selectBillNumsByDate(countStartDay, countEndDay);
    }
}
