package com.masiis.shop.scheduler.mall.service.shop;

import com.google.gson.annotations.SerializedName;
import com.masiis.shop.common.enums.UserAccountRecordFeeType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.mall.shop.SfShopBillItemMapper;
import com.masiis.shop.dao.mall.shop.SfShopBillMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountRecordMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Date 2016/5/26
 * @Auther lzh
 */
@Service
public class SfShopBillService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfShopBillItemMapper shopBillItemMapper;
    @Resource
    private SfShopBillMapper shopBillMapper;
    @Resource
    private ComUserAccountMapper accountMapper;
    @Resource
    private ComUserAccountRecordMapper recordMapper;


    public Long queryShopBillNumsByDateAndShopId(Date countStartDay, Date countEndDay, Long shopId) {
        return shopBillMapper.selectShopBillNumsByDateAndShopId(countStartDay, countEndDay, shopId);
    }

    @Transactional
    public void createShopBillByShopAndDate(SfShop pa, Date countStartDay, Date countEndDay, Date balanceDate) {
        try {
            // 创建店铺账单
            SfShopBill bill = createBillBean(pa, countStartDay, countEndDay, balanceDate);
            shopBillMapper.insert(bill);

            log.info("店铺日账单记录创建成功,日账单id:" + bill.getId());

            // 根据店铺来查询账单子项
            List<SfShopBillItem> items = shopBillItemMapper.selectByShopIdAndDate(pa.getId(), countStartDay, countEndDay);
            for(SfShopBillItem item:items){
                item.setSfShopBillId(bill.getId());
                log.info("计算店铺账单子项,子项id:" + item.getId());
                if(item.getItemType().intValue() == 1){
                    log.info("此账单子项是店铺分销订单销售");
                    bill.setBillAmount(bill.getBillAmount().add(item.getAmount()));
                    bill.setCountAmount(bill.getCountAmount().add(item.getAmount()));
                } else if (item.getItemType().intValue() == 2) {
                    log.info("此账单子项是店铺分销订单退货");
                    bill.setCountAmount(bill.getCountAmount().subtract(item.getAmount()));
                    bill.setReturnAmount(bill.getReturnAmount().add(item.getAmount()));
                }
                item.setIsCount(1);
                shopBillItemMapper.updateByPrimaryKey(item);
            }
            // 修改账单状态
            bill.setStatus(1);
            // 修改account账户结算额和可提现额
            ComUserAccount account = accountMapper.findByUserId(pa.getUserId());
            ComUserAccountRecord record = createAccountRecord(account, bill, UserAccountRecordFeeType.SFSHOP_COUNTING_SUB.getCode());
            record.setUserAccountId(account.getId());
            // 修改结算
            log.info("修改账户的结算金额,之前结算金额是:" + account.getCountingFee());
            record.setPrevFee(account.getCountingFee());
            account.setCountingFee(account.getCountingFee().subtract(bill.getCountAmount()));
            log.info("修改账户的结算金额,之后结算金额是:" + account.getCountingFee());
            record.setNextFee(account.getCountingFee());
            recordMapper.insert(record);
            // 修改可提现
            ComUserAccountRecord recordEx = createAccountRecord(account, bill, UserAccountRecordFeeType.SFSHOP_EXTRACTABLE_ADD.getCode());
            log.info("修改账户的可提现金额,之前可提现金额是:" + account.getExtractableFee());
            recordEx.setPrevFee(account.getExtractableFee());
            account.setExtractableFee(account.getExtractableFee().add(bill.getCountAmount()));
            log.info("修改账户的可提现金额,之后可提现金额是:" + account.getExtractableFee());
            recordEx.setNextFee(account.getExtractableFee());
            recordMapper.insert(recordEx);
            log.info("添加资产账户操作记录成功!");

            int changeSize = accountMapper.updateByIdWithVersion(account);
            if(changeSize != 1){
                throw new BusinessException("");
            }
            shopBillMapper.updateByPrimaryKey(bill);
            log.info("修改用户资产账户结算中和可提现额成功!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    private ComUserAccountRecord createAccountRecord(ComUserAccount account, SfShopBill bill, int type) {
        ComUserAccountRecord record = new ComUserAccountRecord();

        record.setBillId(bill.getId());
        record.setComUserId(bill.getComUserId());
        record.setFeeType(type);
        record.setHandleFee(bill.getBillAmount());
        record.setHandleTime(new Date());
        record.setHandleSerialNum(SysBeanUtils.createAccountRecordSerialNum(0));
        record.setHandleType(0);
        record.setUserAccountId(account.getId());
        record.setHandler("0");

        return record;
    }

    private SfShopBill createBillBean(SfShop pa, Date countStartDay, Date countEndDay, Date balanceDate) {
        SfShopBill bill = new SfShopBill();

        bill.setBillAmount(new BigDecimal(0));
        bill.setCreateTime(new Date());
        bill.setCountingDate(balanceDate);
        bill.setCreateMan(0L);
        bill.setCountAmount(new BigDecimal(0));
        bill.setComUserId(pa.getUserId());
        bill.setShopId(pa.getId());
        bill.setReturnAmount(new BigDecimal(0));
        bill.setSourceStartTime(countStartDay);
        bill.setSourceEndTime(countEndDay);
        bill.setStatus(0);

        return bill;
    }
}
