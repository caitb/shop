package com.masiis.shop.scheduler.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.user.PfUserBillItemMapper;
import com.masiis.shop.dao.platform.user.PfUserBillMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfUserBill;
import com.masiis.shop.dao.po.PfUserBillItem;
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
    private PfBorderMapper orderMapper;
    @Resource
    private PfUserBillMapper billMapper;
    @Resource
    private PfUserBillItemMapper itemMapper;

    @Transactional
    public void createBillByUserAndDate(ComUser user, Date start, Date end, Date balanceDate) {
        try{
            // 组织账单对象
            PfUserBill bill = createBillBean(user, start, end, balanceDate);
            billMapper.insert(bill);
            log.info("");

            // 根据用户来查询订单(已完成且未结算状态订单)
            List<PfBorder> orders = orderMapper.selectByUserAndDate(user.getId(), start, end);
            for(PfBorder order:orders){
                // 创建日账单子项
                PfUserBillItem item = createBillItemByOrder(order);
                item.setPfUserBillId(bill.getId());
                itemMapper.insert(item);

                // 修改订单状态
                order.setIsCounting(1);
                orderMapper.updateByPrimaryKey(order);

                // 统计销售总额,结算总额,佣金总额
                if(item.getOrderSubType() == 0){
                    // 销售订单
                    bill.setTotalAmount(bill.getTotalAmount().add(item.getOrderPayAmount()));
                    bill.setBillAmount(bill.getBillAmount().add(item.getOrderPayAmount()));
                } else if (item.getOrderSubType() == 1) {
                    // 退货订单
                    bill.setBillAmount(bill.getBillAmount().subtract(item.getOrderPayAmount()));
                    bill.setReturnAmount(bill.getReturnAmount().add(item.getOrderPayAmount()));
                }
            }
            // 修改account账户总收入和可提现额
            // 添加账户操作记录
        } catch (Exception e) {
            log.error("创建日账单失败," + e.getMessage());
            throw new BusinessException(e);
        }

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
