package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderOperationLogMapper;
import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.*;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import org.apache.log4j.Logger;
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

    private Logger log = Logger.getLogger(this.getClass());
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private PfBorderOperationLogMapper pfBorderOperationLogMapper;
    @Resource
    private PfSkuStatisticMapper pfSkuStatisticMapper;
    @Resource
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Resource
    private ComUserAccountService comUserAccountService;
    @Resource
    ComUserAccountMapper accountMapper;
    @Resource
    PfUserBillItemMapper itemMapper;
    @Resource
    ComUserAccountRecordMapper recordMapper;

    @Transactional
    public void mainPayBOrder(PfBorderPayment pfBorderPayment, String outOrderId) throws Exception {
        if (pfBorderPayment == null) {
            throw new BusinessException("pfBorderPayment为空");
        }

    }

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
     * <6>修改代理人数(如果是代理类型的订单增加修改sku代理人数)
     * <7>减少发货方库存 如果用户id是0操作平台库存
     * <8>增加收货方库存
     * <9>增加保证金
     * <10>订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项
     */
    private void payBOrderI(PfBorderPayment pfBorderPayment, String outOrderId) throws Exception {
        //<1>修改订单支付信息
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);//设置为有效
        pfBorderPaymentMapper.updateById(pfBorderPayment);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        Long bOrderId = pfBorderPayment.getPfBorderId();
        log.info("<1>修改订单支付信息");
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
        log.info("<2>修改订单数据");
        //<3>添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(1);
        pfBorderOperationLog.setPfBorderId(bOrderId);
        pfBorderOperationLog.setRemark("订单已支付");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        log.info("<3>添加订单日志");
        //<4>修改合伙人商品关系状态
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        if (comUser.getIsAgent() == 0) {
            comUser.setIsAgent(1);
            comUserMapper.updateByPrimaryKey(comUser);
        }
        log.info("<4>修改合伙人商品关系状态");
        //<5>修改用户sku代理关系支付状态
        PfUserSku pfUserSku = pfUserSkuMapper.selectByOrderId(bOrderId);
        if (pfUserSku != null) {
            pfUserSku.setIsPay(1);
            pfUserSku.setBail(pfBorder.getBailAmount());
            pfUserSkuMapper.updateByPrimaryKey(pfUserSku);
        }
        log.info("<5>修改用户sku代理关系支付状态");
        //**************维护商品信息******************
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(bOrderId)) {
            //<6>修改代理人数(如果是代理类型的订单增加修改sku代理人数)
            if (pfBorder.getOrderType() == 0) {
                pfSkuStatisticMapper.updateAgentNumBySkuId(pfBorderItem.getSkuId());
            }
            log.info("<6>修改代理人数(如果是代理类型的订单增加修改sku代理人数)");
            //<7>减少发货方库存 如果用户id是0操作平台库存
            if (pfBorder.getUserPid() == 0) {
                PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(pfBorderItem.getSkuId());
                if (pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    //平台库存不足，排单处理
                    pfBorder.setOrderStatus(6);//排队订单
                    pfBorderMapper.updateById(pfBorder);
                } else {
                    //减少平台库存
                    pfSkuStock.setStock(pfSkuStock.getStock() - pfBorderItem.getQuantity());
                    if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) == 0) {
                        throw new BusinessException("减少平台库存失败");
                    }
                }
            } else {
                PfUserSkuStock parentSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                //上级合伙人库存不足，排单处理
                if (parentSkuStock.getStock() - parentSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    pfBorder.setOrderStatus(6);//排队订单
                    pfBorderMapper.updateById(pfBorder);
                } else {
                    //减少上级合伙人平台库存
                    parentSkuStock.setStock(parentSkuStock.getStock() - pfBorderItem.getQuantity());
                    if (pfUserSkuStockMapper.updateByIdAndVersion(parentSkuStock) == 0) {
                        throw new BusinessException("减少上级合伙人平台库存失败");
                    }
                }
            }
            log.info("<7>减少发货方库存 如果用户id是0操作平台库存");
            //<8>增加收货方库存
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            //如果还没有库存信息直接初始化库存
            if (pfUserSkuStock == null) {
                pfUserSkuStock = new PfUserSkuStock();
                pfUserSkuStock.setCreateTime(new Date());
                pfUserSkuStock.setUserId(pfBorder.getUserId());
                pfUserSkuStock.setSpuId(pfBorderItem.getSpuId());
                pfUserSkuStock.setSkuId(pfBorderItem.getSkuId());
                pfUserSkuStock.setStock(pfBorderItem.getQuantity());
                pfUserSkuStock.setFrozenStock(0);
                pfUserSkuStock.setVersion(0);
                pfUserSkuStockMapper.insert(pfUserSkuStock);
            } else {
                pfUserSkuStock.setStock(pfUserSkuStock.getStock() + pfBorderItem.getQuantity());
                if (pfUserSkuStockMapper.updateByIdAndVersion(pfUserSkuStock) == 0) {
                    throw new BusinessException("增加用户平台库存失败");
                }
            }
            log.info("<8>增加收货方库存");
        }
        //<9>增加保证金
        ComUserAccount accountS = accountMapper.findByUserId(pfBorder.getUserId());
        ComUserAccountRecord recordS = comUserAccountService.createAccountRecordByBail(pfBorder.getBailAmount(), accountS, pfBorder.getId());
        // 保存修改前的金额
        recordS.setPrevFee(accountS.getBailFee());
        accountS.setBailFee(accountS.getBailFee().add(pfBorder.getBailAmount()));
        // 保存修改后的金额
        recordS.setNextFee(accountS.getBailFee());
        recordMapper.insert(recordS);
        int typeS = accountMapper.updateByIdWithVersion(accountS);
        if (typeS == 0) {
            throw new BusinessException("修改进货方成本账户失败!");
        }
        log.info("<9>增加保证金");
        //<10>订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项
        comUserAccountService.countingByOrder(pfBorder);
        log.info("<10>订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项");

    }
}
