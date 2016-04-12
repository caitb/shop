package com.masiis.shop.web.mall.service.user;

import com.masiis.shop.common.enums.mall.SfOrderStatusEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfOrder;
import com.masiis.shop.dao.po.SfUserAccount;
import com.masiis.shop.web.mall.service.order.SfOrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangbingjian on 2016/4/8.
 */
@Service
public class SfUserAccountService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfUserAccountMapper userAccountMapper;
    @Resource
    private SfOrderMapper orderMapper;

    /**
     * 根据用户id查询分销用户账户表
     * @param userId
     * @return
     */
    public SfUserAccount findAccountByUserId(Long userId){
        return userAccountMapper.selectByUserId(userId);
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

        userAccountMapper.insert(account);
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
            if(order.getOrderStatus().intValue() != SfOrderStatusEnum.ORDER_SHIPED.getCode().intValue()){
                log.error("订单状态不匹配,订单不是" + SfOrderStatusEnum.ORDER_SHIPED.getDesc() + "状态");
                throw new BusinessException("订单状态不匹配,订单不是"
                        + SfOrderStatusEnum.ORDER_SHIPED.getDesc() + "状态");
            }


        } catch (Exception e) {

        }
    }
}
