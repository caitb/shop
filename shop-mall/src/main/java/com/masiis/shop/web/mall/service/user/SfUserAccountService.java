package com.masiis.shop.web.mall.service.user;

import com.masiis.shop.common.enums.mall.SfOrderStatusEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.po.*;
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
    private SfUserAccountMapper sfUserAccountMapper;
    @Resource
    private SfOrderMapper orderMapper;
    @Resource
    private UserService userService;
    @Resource
    private ComUserAccountMapper comUserAccountMapper;

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
                countFee = order.getPayAmount()
                        .subtract(order.getDistributionAmount()).subtract(order.getShipAmount());
            } else if(order.getSendType() == 2){
                countFee = order.getPayAmount().subtract(order.getDistributionAmount());
            } else {
                throw new BusinessException("不合法的拿货方式");
            }
            // 店主account
            ComUserAccount comUserAccount = comUserAccountMapper.findByUserId(order.getShopUserId());
            // 创建店主结算中金额变动记录
            ComUserAccountRecord pfCountRecord = createComUserAccountRecordBySfOrder(order, countFee);
            pfCountRecord.setPrevFee(comUserAccount.getCountingFee());
            comUserAccount.setCountingFee(comUserAccount.getCountingFee().add(countFee));
            pfCountRecord.setNextFee(comUserAccount.getCountingFee());
            // 创建
            // 计算店主此次总利润
            // 计算分销订单的分润

        } catch (Exception e) {

        }
    }

    private ComUserAccountRecord createComUserAccountRecordBySfOrder(SfOrder order, BigDecimal countFee) {
        return null;
    }
}
