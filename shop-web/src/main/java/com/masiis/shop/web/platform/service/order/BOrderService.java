package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
public class BOrderService {
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfBorderConsigneeMapper pfBorderConsigneeMapper;
    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private PfBorderOperationLogMapper pfBorderOperationLogMapper;
    @Resource
    private PfUserSkussMapper pfUserSkussMapper;
    @Resource
    private ComAgentLevelsMapper comAgentLevelsMapper;
    @Resource
    private PfSkuStatisticMapper pfSkuStatisticMapper;
    @Resource
    private PfSkuStockMapper pfSkuStockMapper;

    /**
     * 添加订单
     *
     * @param pfBorder
     * @param pfBorderItems
     */
    @Transactional
    public Long AddBOrder(PfBorder pfBorder, List<PfBorderItem> pfBorderItems, PfUserSku pfUserSku, ComUser comUser) throws Exception {
        if (pfBorder == null) {
            throw new BusinessException("pfBorder为空");
        }
        if (pfUserSku == null) {
            throw new BusinessException("pfUserSku为空");
        }
        if (pfBorderItems == null || pfBorderItems.size() == 0) {
            throw new BusinessException("pfBorderItems为空");
        }
        if (comUser == null) {
            throw new BusinessException("comUser为空");
        }
        //添加订单
        pfBorderMapper.insert(pfBorder);
        //添加订单商品
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            pfBorderItem.setPfBorderId(pfBorder.getId());
            pfBorderItemMapper.insert(pfBorderItem);
        }
        pfUserSku.setPfBorderId(pfBorder.getId());
        //添加用户代理商品关系
        pfUserSkuMapper.insert(pfUserSku);
        //完善用户信息
        comUserMapper.updateByPrimaryKey(comUser);
        //添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderId(pfBorder.getId());
        pfBorderOperationLog.setCreateMan(comUser.getId());
        pfBorderOperationLog.setPfBorderStatus(0);
        pfBorderOperationLog.setRemark("新增订单");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        return pfBorder.getId();
    }

    /**
     * 获取订单
     *
     * @author ZhaoLiang
     * @date 2016/3/9 11:07
     */
    public PfBorder getPfBorderById(Long id) {
        return pfBorderMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据订单号获取订单商品
     *
     * @author ZhaoLiang
     * @date 2016/3/9 11:45
     */
    public List<PfBorderItem> getPfBorderItemByOrderId(Long pfBorderId) {
        return pfBorderItemMapper.selectAllByOrderId(pfBorderId);
    }

    /**
     * 合伙人订单支付
     *
     * @author ZhaoLiang
     * @date 2016/3/16 10:04
     * 操作详情 <1>修改订单支付信息<2>修改订单数据<3>添加订单日志<4>修改合伙人商品关系状态<5>修改用户sku代理关系支付状态<6>修改sku代理量<7>冻结sku库存
     */
    @Transactional
    public void payBOrder(PfBorderPayment pfBorderPayment, String outOrderId) throws Exception {
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);//设置为有效
        //<1>修改订单支付信息
        pfBorderPaymentMapper.updateById(pfBorderPayment);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        Long bOrderId = pfBorderPayment.getPfBorderId();
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        pfBorder.setReceivableAmount(pfBorder.getReceivableAmount().subtract(payAmount));
        pfBorder.setPayAmount(pfBorder.getPayAmount().add(payAmount));
        pfBorder.setPayTime(new Date());
        pfBorder.setPayStatus(1);//已付款
        pfBorder.setOrderStatus(1);//已付款
        //<2>修改订单数据
        pfBorderMapper.updateByPrimaryKey(pfBorder);
        //<3>添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(1);
        pfBorderOperationLog.setPfBorderId(bOrderId);
        pfBorderOperationLog.setRemark("订单已支付");
        pfBorderOperationLogMapper.updateByPrimaryKey(pfBorderOperationLog);
        //<4>修改合伙人商品关系状态
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        if (comUser.getIsAgent() == 0) {
            comUser.setIsAgent(1);
            comUserMapper.updateByPrimaryKey(comUser);
        }
        //<5>修改用户sku代理关系支付状态
        PfUserSku pfUserSku = pfUserSkuMapper.selectByOrderId(bOrderId);
        pfUserSku.setIsPay(1);
        pfUserSkuMapper.updateByPrimaryKey(pfUserSku);
        //**************维护商品信息******************
        PfSkuStatistic pfSkuStatistic = null;
        PfSkuStock pfSkuStock = null;
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(bOrderId)) {
            //<6>修改sku代理量
            pfSkuStatistic = pfSkuStatisticMapper.selectBySkuId(pfBorderItem.getSkuId());
            pfSkuStatistic.setAgentNum(pfSkuStatistic.getAgentNum() + 1);
            pfSkuStatisticMapper.updateById(pfSkuStatistic);
            //<7>冻结sku库存
            pfSkuStock = pfSkuStockMapper.selectBySkuId(pfBorderItem.getSkuId());
            pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
        }
    }

    /**
     * 查用户商品关系表
     *
     * @author muchaofeng
     * @date 2016/3/9 18:12
     */
    public PfUserSku findPfUserSkuById(Long id) {
        return pfUserSkussMapper.selectPfUserSkusById(id);
    }

    /**
     * 获取合伙人等级
     *
     * @author muchaofeng
     * @date 2016/3/9 18:52
     */
    public ComAgentLevel findComAgentLevel(Integer id) {
        return comAgentLevelsMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据订单号获取订单
     *
     * @author muchaofeng
     * @date 2016/3/14 13:23
     */

    public PfBorder findByOrderCode(String orderId) {
        return pfBorderMapper.selectByOrderCode(orderId);
    }

    /**
     * 根据用户id获取订单
     *
     * @author muchaofeng
     * @date 2016/3/14 13:22
     */

    public List<PfBorder> findByUserId(Long UserId) {
        return pfBorderMapper.selectByUserId(UserId);
    }

}
