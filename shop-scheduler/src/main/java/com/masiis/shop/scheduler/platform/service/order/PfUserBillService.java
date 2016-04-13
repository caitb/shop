package com.masiis.shop.scheduler.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountRecordMapper;
import com.masiis.shop.dao.platform.user.PfUserBillItemMapper;
import com.masiis.shop.dao.platform.user.PfUserBillMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lzh on 2016/3/23.
 */
@Service
public class PfUserBillService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfBorderMapper borderMapper;
    @Resource
    private PfUserBillMapper billMapper;
    @Resource
    private PfUserBillItemMapper itemMapper;
    @Resource
    private ComUserAccountMapper accountMapper;
    @Resource
    private ComUserAccountRecordMapper recordMapper;

    @Transactional
    public void createBillByUserAndDate(ComUser user, Date start, Date end, Date balanceDate) {
        try{
            // 组织账单对象
            PfUserBill bill = createBillBean(user, start, end, balanceDate);
            billMapper.insert(bill);

            log.info("日账单记录创建成功,日账单id:" + bill.getId());

            // 根据用户来查询账单子项(已完成且未结算状态订单)
            List<PfUserBillItem> items = itemMapper.selectByUserAndDate(user.getId(), start, end);
            for(PfUserBillItem item:items){
                item.setPfUserBillId(bill.getId());
                log.info("计算账单子项,子项id:" + item.getId());
                if(item.getOrderSubType() == 0){
                    // 销售订单
                    log.info("此账单子项是销售订单,销售额是:" + item.getOrderPayAmount());
                    bill.setTotalAmount(bill.getTotalAmount().add(item.getOrderPayAmount()));
                    bill.setBillAmount(bill.getBillAmount().add(item.getOrderPayAmount()));
                } else if(item.getOrderSubType() == 1){
                    // 退货订单
                    log.info("此账单子项是退货订单,销售额是:" + item.getOrderPayAmount());
                    bill.setBillAmount(bill.getBillAmount().subtract(item.getOrderPayAmount()));
                    bill.setReturnAmount(bill.getReturnAmount().add(item.getOrderPayAmount()));
                }
                if(item.getOrderType().intValue() == 0){
                    // 代理订单
                    PfBorder order = borderMapper.selectByPrimaryKey(item.getPfBorderId());
                    order.setIsCounting(1);
                    borderMapper.updateByPrimaryKey(order);
                } else if(item.getOrderType().intValue() == 1){
                    // 分销订单
                }
            }
            // 修改账单状态
            bill.setStatus(1);
            // 修改account账户结算额和可提现额
            ComUserAccount account = accountMapper.findByUserId(user.getId());
            ComUserAccountRecord record = createAccountRecord(bill, 1);
            record.setUserAccountId(account.getId());
            // 修改结算
            log.info("修改账户的结算金额,之前结算金额是:" + account.getCountingFee());
            record.setPrevFee(account.getCountingFee());
            account.setCountingFee(account.getCountingFee().subtract(bill.getBillAmount()));
            log.info("修改账户的结算金额,之后结算金额是:" + account.getCountingFee());
            record.setNextFee(account.getCountingFee());
            recordMapper.insert(record);
            // 修改可提现
            ComUserAccountRecord recordEx = createAccountRecord(bill, 4);
            log.info("修改账户的可提现金额,之前可提现金额是:" + account.getExtractableFee());
            recordEx.setPrevFee(account.getExtractableFee());
            account.setExtractableFee(account.getExtractableFee().add(bill.getBillAmount()));
            log.info("修改账户的可提现金额,之后可提现金额是:" + account.getExtractableFee());
            recordEx.setNextFee(account.getExtractableFee());
            recordMapper.insert(recordEx);
            log.info("添加资产账户操作记录成功!");

            int changeSize = accountMapper.updateByIdWithVersion(account);
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

    private ComUserAccountRecord createAccountRecord(PfUserBill bill, int type) {
        ComUserAccountRecord record = new ComUserAccountRecord();

        record.setBillId(bill.getId());
        record.setComUserId(bill.getUserId());
        record.setFeeType(type);
        record.setHandleFee(bill.getBillAmount());
        record.setHandleTime(new Date());

        return record;
    }

    private PfUserBillItem createBillItemByOrder(PfBorder order) {
        PfUserBillItem item = new PfUserBillItem();

        item.setCreateDate(new Date());
        item.setOrderCreateDate(order.getCreateTime());
        item.setOrderPayAmount(order.getPayAmount());
        item.setOrderSubType(0);
        item.setOrderType(0);
        item.setPfBorderId(order.getId());

        return item;
    }

    /**
     * 创建日账单对象
     *
     * @param user
     * @param start
     * @param end
     * @param balanceDate
     * @return
     */
    private PfUserBill createBillBean(ComUser user, Date start, Date end, Date balanceDate) {
        PfUserBill bill = new PfUserBill();

        bill.setBalanceDate(DateUtil.getMinTimeofDay(balanceDate));
        bill.setBillAmount(new BigDecimal(0));
        bill.setCountStartTime(start);
        bill.setCountEndTime(end);
        bill.setCreateDate(new Date());
        bill.setCreateMan(0L);
        bill.setPfBrokerage(new BigDecimal(0));
        bill.setReturnAmount(new BigDecimal(0));
        bill.setTotalAmount(new BigDecimal(0));
        bill.setSupplierId(0L);
        bill.setUserId(user.getId());
        bill.setStatus(0);

        return bill;
    }
}
