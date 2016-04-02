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

import java.util.*;

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
     * 排单处理入口
     *
     * @param orderMap map类型参数 key：订单编号 value：发货类型（同数据库）
     * @return
     * @throws Exception
     */
    public Map<String, String> commonQueuingOrder(Map<String, String> orderMap) throws Exception {
        if (orderMap == null) {
            return orderMap;
        }
        Map<String, String> map = new HashMap<>();
//        Iterator it = orderMap.entrySet().iterator();
//        String key;
//        String value;
//        String receiveMsg = "";
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            key = (String) entry.getKey();
//            value = (String) entry.getValue();
//            try {
//                if (value.equals("1")) {
//                    receiveMsg = pfQueuingOrder(key);
//                }
//                if (value.equals("2")) {
//                    receiveMsg = userQueuingOrder(key);
//                }
//                map.put(key, receiveMsg);
//            } catch (Exception e) {
//                e.printStackTrace();
//                map.put(key, "系统异常");
//            }
//        }
        return map;
    }


    @Transactional
    private void abvas(Long orderId) throws Exception {
        pfQueuingOrder(orderId);
    }

    /**
     * 平台代发处理排单
     *
     * @param orderId 订单id
     * @throws Exception
     */
    private void pfQueuingOrder(Long orderId) throws Exception {
        log.info("进入平台代发排单处理Service");
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(orderId);
        if (pfBorder == null) {
            log.info("平台代发:订单编号不存在，orderId=" + orderId);
            throw new BusinessException("平台代发:订单编号不存在，orderCode=" + orderId);
        }
        if (pfBorder.getSendType() != 1) {
            log.info("平台代发:该订单不是平台代发货，orderCode=" + orderId);
            throw new BusinessException("平台代发:该订单不是平台代发货，orderCode=" + orderId);
        }
        if (pfBorder.getOrderStatus() != 6) {
            log.info("平台代发:该订单不是排单状态，orderCode=" + orderId);
            throw new BusinessException("平台代发:该订单不是排单状态，orderCode=" + orderId);
        }
        List<PfBorderItem> orderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        int skuId;
        int quantity = 0;
        PfUserSkuStock userSkuStock;
        for (PfBorderItem orderItem : orderItems) {
            skuId = orderItem.getSkuId();
            quantity = orderItem.getQuantity();
            //根据sku_id查询商品sku库存数据
            PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
            //当取货数量不大于平台当前库存则直接进行平台减库存用户加库存操作
            //取货数量大于平台库存量则向上抛出异常
            if (quantity <= pfSkuStock.getStock()) {
                //平台减库存
                pfSkuStock.setStock(pfSkuStock.getStock().intValue() - quantity);
                if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) == 0) {
                    log.info("平台代发:更新平台商品库存表失败");
                    throw new BusinessException("平台代发:更新平台商品库存表失败");
                }
                //代理加库存
                userSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserId(), skuId);
                if (userSkuStock == null) {
                    userSkuStock = new PfUserSkuStock();
                    userSkuStock.setCreateTime(new Date());
                    userSkuStock.setUserId(pfBorder.getUserId());
                    userSkuStock.setSpuId(orderItem.getSpuId());
                    userSkuStock.setSkuId(orderItem.getSkuId());
                    userSkuStock.setStock(quantity);
                    userSkuStock.setFrozenStock(0);
                    userSkuStock.setRemark("处理排单时插入的数据");
                    userSkuStock.setVersion(0);
                } else {
                    userSkuStock.setStock(userSkuStock.getStock().intValue() + quantity);
                    if (pfUserSkuStockMapper.updateByIdAndVersion(userSkuStock) == 0) {
                        log.info("平台代发:更新代理商商品库存表失败");
                        throw new BusinessException("平台代发:更新代理商商品库存表失败");
                    }
                }
            } else {
                log.info("平台代发:平台商品库存不足，拿货数量为" + quantity + "，平台库存量" + pfSkuStock.getStock());
                throw new BusinessException("平台代发:平台商品库存不足，拿货数量为" + quantity + "，平台库存量" + pfSkuStock.getStock());
            }
        }
        //修改订单状态，订单完成
        pfBorder.setOrderStatus(3);
        if (pfBorderMapper.updateById(pfBorder) == 0) {
            log.info("平台代发:修改订单状态失败");
            throw new BusinessException("平台代发:修改订单状态失败");
        }
        //添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(pfBorder.getOrderStatus());
        pfBorderOperationLog.setPfBorderId(pfBorder.getId());
        pfBorderOperationLog.setRemark("订单完成，平台代发排单处理订单");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        //递归处理下级
        List<PfBorder> pfBorders = pfBorderMapper.selectByUserPid(pfBorder.getUserId(), 6, 0);
        for (PfBorder order : pfBorders) {
            pfQueuingOrder(order.getId());
        }
    }

    /**
     * 用户自发货订单排单处理
     *
     * @param orderCode
     * @throws Exception
     */
    @Transactional
    public String userQueuingOrder(String orderCode) throws Exception {
        log.info("用户自发货订单排单处理Service");
        PfBorder pfBorder = pfBorderMapper.selectByOrderCode(orderCode);
        if (pfBorder == null) {
            log.info("自发货:订单编号不存在，orderCode=" + orderCode);
            return "自发货:订单编号不存在，orderCode=" + orderCode;
        }
        if (pfBorder.getSendType() != 2) {
            log.info("自发货:该订单不是自发货，orderCode=" + orderCode);
            return "自发货:该订单不是自发货，orderCode=" + orderCode;
        }
        if (pfBorder.getOrderStatus() != 6) {
            log.info("自发货:该订单不是排单状态，orderCode=" + orderCode);
            return "自发货:该订单不是排单状态，orderCode=" + orderCode;
        }
        List<PfBorderItem> orderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        int skuId;
        int quantity = 0;
        for (PfBorderItem borderItem : orderItems) {
            skuId = borderItem.getSkuId();
            quantity = borderItem.getQuantity();
            //根据sku_id查询商品sku库存数据
            PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
            //当取货数量不大于平台当前库存则直接进行平台减库存加冻结库存
            //取货数量大于平台库存量则向上抛出异常
            if (quantity <= pfSkuStock.getStock()) {
                pfSkuStock.setStock(pfSkuStock.getStock().intValue() - quantity);
                pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock().intValue() + quantity);
                pfSkuStock.setRemark("自发货排单处理");
                if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) == 0) {
                    log.info("自发货:自发货排单处理，平台库存更新失败");
                    return "自发货:自发货排单处理，平台库存更新失败";
                }
            } else {
                log.info("自发货:平台商品库存不足，拿货数量为" + quantity + "，平台库存量" + pfSkuStock.getStock());
                return "自发货:平台商品库存不足，拿货数量为" + quantity + "，平台库存量" + pfSkuStock.getStock();
            }
        }
        //修改订单状态，订单完成
        pfBorder.setOrderStatus(7);
        if (pfBorderMapper.updateById(pfBorder) == 0) {
            log.info("自发货:修改订单状态失败");
            return "自发货:修改订单状态失败";
        }
        //添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(pfBorder.getOrderStatus());
        pfBorderOperationLog.setPfBorderId(pfBorder.getId());
        pfBorderOperationLog.setRemark("订单待发货，自发货排单处理订单");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        return "success";
    }
}
