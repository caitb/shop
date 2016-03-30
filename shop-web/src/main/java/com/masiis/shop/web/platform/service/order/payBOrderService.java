package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.product.SkuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * payBOrderService
 *
 * @author ZhaoLiang
 * @date 2016/3/30
 */
@Service
public class payBOrderService {
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
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Resource
    private PfBorderFreightMapper pfBorderFreightMapper;
    @Resource
    private SkuService skuService;
    @Resource
    private ComUserAccountMapper comUserAccountMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;


    /**
     * 平台代理订单支付成功回调(平台代发货)
     *
     * @author ZhaoLiang
     * @date 2016/3/30 14:33
     * 操作详情：
     * <1>修改订单支付信息
     * <2>修改订单数据
     * <3>添加订单日志
     * <4>修改合伙人商品关系状态
     * <5>修改用户sku代理关系支付状态
     * <6>修改sku代理量
     * <7>冻结sku库存
     * <8>初始化个人库存信息
     * <9>更新上级用户资产 (暂不更新)
     */
    @Transactional
    private void payBOrderI(PfBorderPayment pfBorderPayment, String outOrderId) throws Exception {
        //<1>修改订单支付信息
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);//设置为有效
        pfBorderPaymentMapper.updateById(pfBorderPayment);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        Long bOrderId = pfBorderPayment.getPfBorderId();
        //<2>修改订单数据
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        if (pfBorder.getSendType() != 1) {
            throw new BusinessException("订单拿货类型错误：为" + pfBorder.getSendType() + ",应为1.");
        }
        pfBorder.setReceivableAmount(pfBorder.getReceivableAmount().subtract(payAmount));
        pfBorder.setPayAmount(pfBorder.getPayAmount().add(payAmount));
        pfBorder.setPayTime(new Date());
        pfBorder.setPayStatus(1);//已付款
        pfBorder.setOrderStatus(1);//已付款
        pfBorderMapper.updateById(pfBorder);
        //<3>添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(1);
        pfBorderOperationLog.setPfBorderId(bOrderId);
        pfBorderOperationLog.setRemark("订单已支付");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        //<4>修改合伙人商品关系状态
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        if (comUser.getIsAgent() == 0) {
            comUser.setIsAgent(1);
            comUserMapper.updateByPrimaryKey(comUser);
        }
        //<5>修改用户sku代理关系支付状态
        PfUserSku pfUserSku = pfUserSkuMapper.selectByOrderId(bOrderId);
        if (pfUserSku != null) {
            pfUserSku.setIsPay(1);
            pfUserSkuMapper.updateByPrimaryKey(pfUserSku);
        }
        //**************维护商品信息******************
        PfSkuStatistic pfSkuStatistic = null;
        PfSkuStock pfSkuStock = null;
        PfUserSkuStock pfUserSkuStock = null;
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(bOrderId)) {
            //<6>修改代理人数(如果是代理类型的订单增加修改sku代理人数)
            if (pfBorder.getOrderType() == 0) {
                pfSkuStatisticMapper.updateAgentNumBySkuId(pfBorderItem.getSkuId());
            }
            //<7>冻结sku库存 如果用户id是0操作平台库存
            if (pfBorder.getUserPid() == 0) {
                pfSkuStock = pfSkuStockMapper.selectBySkuId(pfBorderItem.getSkuId());
                if (pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    pfBorder.setOrderStatus(6);//排队订单
                    pfBorderMapper.updateById(pfBorder);
                } else {
                    pfSkuStock.setStock(pfSkuStock.getStock() - pfBorderItem.getQuantity());

                }

                pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) == 0) {
                    throw new BusinessException("并发修改库存失败");
                }
            } else {
                pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                if (pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    pfBorder.setOrderStatus(6);//排队订单
                    pfBorderMapper.updateById(pfBorder);
                }
                pfUserSkuStock.setFrozenStock(pfUserSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                if (pfUserSkuStockMapper.updateByIdAndVersion(pfUserSkuStock) == 0) {
                    throw new BusinessException("并发修改库存失败");
                }
            }
            //<8>初始化个人库存信息
            PfUserSkuStock defaultUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            if (defaultUserSkuStock == null) {
                defaultUserSkuStock = new PfUserSkuStock();
                defaultUserSkuStock.setCreateTime(new Date());
                defaultUserSkuStock.setUserId(pfBorder.getUserId());
                defaultUserSkuStock.setSpuId(pfBorderItem.getSpuId());
                defaultUserSkuStock.setSkuId(pfBorderItem.getSkuId());
                defaultUserSkuStock.setStock(0);
                defaultUserSkuStock.setFrozenStock(0);
                defaultUserSkuStock.setVersion(0);
                pfUserSkuStockMapper.insert(defaultUserSkuStock);
            }
            //<9>更新上级用户资产 (暂不更新)
//            if (pfBorder.getUserPid() != 0) {
//                int i = comUserAccountMapper.payBOrderToUpdateUserAccount(pfBorder.getUserPid(), pfBorder.getPayAmount());
//                if (i == 0) {
//                    throw new BusinessException("更新用户资产失败");
//                }
//            }
        }

    }
}
