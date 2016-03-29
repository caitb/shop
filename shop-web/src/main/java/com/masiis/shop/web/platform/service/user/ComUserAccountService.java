package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.PfUserBillItemMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfUserBillItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lzh on 2016/3/19.
 */
@Service
public class ComUserAccountService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    ComUserAccountMapper accountMapper;
    @Resource
    PfUserBillItemMapper itemMapper;

    /**
     * 根据用户id查询用户资产账户
     *
     * @param id
     * @return
     */
    public ComUserAccount findAccountByUserid(Long id) {
        return accountMapper.findByUserId(id);
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
        accountMapper.insert(account);
    }

    /**
     * 订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项
     *
     * @param order
     */
    @Transactional
    public void countingByOrder(PfBorder order){
        try{
            // 验证订单的状态
            Integer orderType = order.getOrderType();
            Integer orderStatus = order.getOrderStatus();
            if(orderType != 0 && orderType != 1){
                throw new BusinessException("订单类型不正确,当前订单状态为:" + orderType);
            }
            if(orderStatus != 3){
                throw new BusinessException("订单状态不正确,当前订单状态为:" + orderStatus);
            }

            log.info("订单类型和状态校验通过,进行创建账单子项工作!");
            // 创建对应的bill_item
            PfUserBillItem item = createBillItemByBOrder(order);
            itemMapper.insert(item);

            // 获取对应的account记录
            ComUserAccount account = accountMapper.findByUserId(order.getUserId());
            //account.setCostFee(account.getCostFee().add(order.));
        } catch (Exception e) {
            log.error("订单完成进行账户总销售额和结算金额操作错误," + e.getMessage());
            throw new BusinessException(e);
        }

    }

    private PfUserBillItem createBillItemByBOrder(PfBorder order) {
        PfUserBillItem item = new PfUserBillItem();

        item.setCreateDate(new Date());
        item.setOrderCreateDate(order.getCreateTime());
        item.setOrderPayAmount(order.getPayAmount());
        item.setOrderSubType(0);
        item.setOrderType(0);
        item.setPfBorderId(order.getId());

        return item;
    }
}
