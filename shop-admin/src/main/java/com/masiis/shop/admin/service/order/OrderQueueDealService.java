package com.masiis.shop.admin.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderOperationLogMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 排单处理Service
 * Created by wangbingjian on 2016/4/1.
 */
@Service
public class OrderQueueDealService {

    private static final Logger log = Logger.getLogger(OrderQueueDealService.class);

    @Autowired
    private PfBorderMapper pfBorderMapper;
    @Autowired
    private PfBorderItemMapper pfBorderItemMapper;
    @Autowired
    private PfSkuStockMapper pfSkuStockMapper;
    @Autowired
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Autowired
    private PfBorderOperationLogMapper pfBorderOperationLogMapper;

    /**
     * 平台代发处理排单
     * @param orderCode     订单编号
     * @throws Exception
     */
    @Transactional
    public void pfQueuingOrder(String orderCode) throws Exception{
        log.info("进入平台代发排单处理Service");
        PfBorder pfBorder = pfBorderMapper.selectByOrderCode(orderCode);
        if (pfBorder == null){
            log.info("订单编号不存在：orderCode="+orderCode);
            throw new BusinessException("订单编号不存在：orderCode="+orderCode);
        }
        if (pfBorder.getSendType() != 1){
            log.info("该订单不是平台代发货：orderCode="+orderCode);
            throw new BusinessException("该订单不是平台代发货：orderCode="+orderCode);
        }
        if (pfBorder.getOrderStatus() != 6){
            log.info("该订单不是排单状态：orderCode="+orderCode);
            throw new BusinessException("该订单不是排单状态：orderCode="+orderCode);
        }
        List<PfBorderItem> orderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        int skuId;
        int quantity = 0;
        PfUserSkuStock userSkuStock;
        for (PfBorderItem orderItem:orderItems){
            skuId = orderItem.getSkuId();
            quantity = orderItem.getQuantity();
            //根据sku_id查询商品sku库存数据
            PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
            //当取货数量不大于平台当前库存则直接进行平台减库存用户加库存操作
            //取货数量大于平台库存量则向上抛出异常
            if (quantity <= pfSkuStock.getStock()){
                //平台减库存
                pfSkuStock.setStock(pfSkuStock.getStock()-quantity);
                pfSkuStockMapper.updateById(pfSkuStock);
                //代理加库存
                userSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserId(),skuId);
                if (userSkuStock == null){
                    userSkuStock = new PfUserSkuStock();
                    userSkuStock.setCreateTime(new Date());
                    userSkuStock.setUserId(pfBorder.getUserId());
                    userSkuStock.setSpuId(orderItem.getSpuId());
                    userSkuStock.setSkuId(orderItem.getSkuId());
                    userSkuStock.setStock(quantity);
                    userSkuStock.setFrozenStock(0);
                    userSkuStock.setRemark("处理排单时插入的数据");
                    userSkuStock.setVersion(0);
                }else {
                    userSkuStock.setStock(userSkuStock.getStock()+quantity);
                    if (pfUserSkuStockMapper.updateByIdAndVersion(userSkuStock) == 0){
                        log.info("更新代理商商品库存表失败");
                        throw new BusinessException("更新代理商商品库存表失败,平台代发排单处理");
                    }
                }
            }else {
                log.info("平台商品库存不足，拿货数量为："+quantity+"，平台库存量："+pfSkuStock.getStock());
                throw new BusinessException("平台商品库存不足，拿货数量为："+quantity+"，平台库存量："+pfSkuStock.getStock());
            }
        }
        //修改订单状态，订单完成
        pfBorder.setOrderStatus(3);
        if (pfBorderMapper.updateById(pfBorder) == 0){
            log.info("修改订单状态失败");
            throw new BusinessException("修改订单状态失败");
        }
        //添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(pfBorder.getOrderStatus());
        pfBorderOperationLog.setPfBorderId(pfBorder.getId());
        pfBorderOperationLog.setRemark("订单完成，平台代发排单处理订单");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
    }

    /**
     * 用户自发货订单排单处理
     * @param orderCode
     * @throws Exception
     */
    @Transactional
    public void userQueuingOrder(String orderCode) throws Exception{
        log.info("用户自发货订单排单处理Service");
        PfBorder pfBorder = pfBorderMapper.selectByOrderCode(orderCode);
        if (pfBorder == null){
            log.info("订单编号不存在：orderCode="+orderCode);
            throw new BusinessException("订单编号不存在：orderCode="+orderCode);
        }
        if (pfBorder.getSendType() != 2){
            log.info("该订单不是自发货：orderCode="+orderCode);
            throw new BusinessException("该订单不是自发货：orderCode="+orderCode);
        }
        if (pfBorder.getOrderStatus() != 6){
            log.info("该订单不是排单状态：orderCode="+orderCode);
            throw new BusinessException("该订单不是排单状态：orderCode="+orderCode);
        }
        List<PfBorderItem> orderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        int skuId;
        int quantity = 0;
        for (PfBorderItem borderItem : orderItems){
            skuId = borderItem.getSkuId();
            quantity = borderItem.getQuantity();
            //根据sku_id查询商品sku库存数据
            PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
            //当取货数量不大于平台当前库存则直接进行平台减库存加冻结库存
            //取货数量大于平台库存量则向上抛出异常
            if (quantity <= pfSkuStock.getStock()){
                pfSkuStock.setStock(pfSkuStock.getStock() - quantity);
                pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + quantity);
                pfSkuStock.setRemark("自发货排单处理");
                if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) == 0){
                    log.info("自发货排单处理，平台库存更新失败");
                    throw new BusinessException("自发货排单处理，平台库存更新失败");
                }
            }else {
                log.info("平台商品库存不足，拿货数量为："+quantity+"，平台库存量："+pfSkuStock.getStock());
                throw new BusinessException("平台商品库存不足，拿货数量为："+quantity+"，平台库存量："+pfSkuStock.getStock());
            }
        }
        //修改订单状态，订单完成
        pfBorder.setOrderStatus(7);
        if (pfBorderMapper.updateById(pfBorder) == 0){
            log.info("修改订单状态失败");
            throw new BusinessException("修改订单状态失败");
        }
        //添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(pfBorder.getOrderStatus());
        pfBorderOperationLog.setPfBorderId(pfBorder.getId());
        pfBorderOperationLog.setRemark("订单待发货，自发货排单处理订单");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
    }
}
