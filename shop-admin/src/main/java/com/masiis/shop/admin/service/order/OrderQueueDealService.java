package com.masiis.shop.admin.service.order;

import com.masiis.shop.admin.service.product.SkuService;
import com.masiis.shop.admin.service.user.ComUserService;
import com.masiis.shop.admin.utils.WxPFNoticeUtils;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
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
    private SkuService skuService;
    @Autowired
    private PfBorderMapper pfBorderMapper;
    @Autowired
    private PfBorderItemMapper pfBorderItemMapper;
    @Autowired
    private PfSkuStockMapper pfSkuStockMapper;
    @Autowired
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Autowired
    private BOrderOperationLogService bOrderOperationLogService;
    @Autowired
    private ComUserService comUserService;

    /**
     * 排单处理入口
     *
     * @param orderMap map类型参数 key：订单id value：发货类型（同数据库）
     * @return
     * @throws Exception
     */
    @Transactional
    public Map<Long,String> commonQueuingOrder(Map<Long, String> orderMap) throws Exception {
        if (orderMap == null) {
            throw new BusinessException("参数为空");
        }
        Map<Long, String> map = new HashMap<>();
        Iterator it = orderMap.entrySet().iterator();
        Long key;
        String value;
        String receiveMsg = "";
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            key = (Long) entry.getKey();
            value = (String) entry.getValue();
            if (value.equals("1")) {
                receiveMsg = pfQueuingOrder(key);
            }
            if (value.equals("2")) {
                receiveMsg = userQueuingOrder(key);
            }
            map.put(key,receiveMsg);
        }
        return map;
    }

    /**
     * 平台代发处理排单
     * @param orderId 订单id
     * @throws Exception
     */
    private String pfQueuingOrder(Long orderId) throws Exception {
        log.info("进入平台代发排单处理Service");
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(orderId);
        if (pfBorder == null) {
            throw new BusinessException("平台代发:订单编号不存在，orderId=" + orderId);
        }
        if (pfBorder.getSendType() != 1) {
            throw new BusinessException("平台代发:该订单不是平台代发货，orderId=" + orderId);
        }
        if (pfBorder.getOrderStatus() != 6) {
            throw new BusinessException("平台代发:该订单不是排单状态，orderId=" + orderId);
        }
        //判断库存是否充足,库存不足直接返回
        if (!checkBOrderStock(pfBorder)){
            return "false";
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
            if (quantity <= pfSkuStock.getStock() - pfSkuStock.getFrozenStock()) {
                //平台减库存
                pfSkuStock.setStock(pfSkuStock.getStock() - quantity);
                pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() - quantity);
                if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) == 0) {
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
                    if (pfUserSkuStockMapper.insert(userSkuStock) == 0) {
                        throw new BusinessException("平台代发:更新代理商商品库存表失败");
                    }
                } else {
                    userSkuStock.setStock(userSkuStock.getStock() + quantity);
                    if (pfUserSkuStockMapper.updateByIdAndVersion(userSkuStock) == 0) {
                        throw new BusinessException("平台代发:更新代理商商品库存表失败");
                    }
                }
            } else {
                log.info("平台代发:平台商品库存不足，拿货数量为" + quantity + "，平台库存量" + pfSkuStock.getStock());
                throw new BusinessException("平台代发:平台商品库存不足，拿货数量为" + quantity + "，平台库存量" + pfSkuStock.getStock());
            }
        }
        //修改订单状态，订单完成
        pfBorder.setOrderStatus(BOrderStatus.Complete.getCode());
        if (pfBorderMapper.updateById(pfBorder) == 0) {
            throw new BusinessException("平台代发:修改订单状态失败");
        }
        //添加订单日志
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder,"订单完成，平台代发处理排单订单");
        this.sendMessage(pfBorder);
        return "success";
    }

    /**
     * 用户自发货订单排单处理
     * @param orderId       订单id
     * @throws Exception
     */
    private String userQueuingOrder(Long orderId) throws Exception {
        log.info("用户自发货订单排单处理Service");
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(orderId);
        if (pfBorder == null) {
            throw new BusinessException("自发货:订单编号不存在，orderCode=" + orderId);
        }
        if (pfBorder.getSendType() != 2) {
            throw new BusinessException("自发货:该订单不是自发货，orderCode=" + orderId);
        }
        if (pfBorder.getOrderStatus() != 6) {
            throw new BusinessException("自发货:该订单不是排单状态，orderCode=" + orderId);
        }
        //判断库存是否充足,库存不足直接返回
        if (!checkBOrderStock(pfBorder)){
            return "false";
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
            if (quantity > pfSkuStock.getStock() - pfSkuStock.getFrozenStock()) {
                log.info("自发货:平台商品库存不足，拿货数量为" + quantity + "，平台库存量" + pfSkuStock.getStock());
                throw new BusinessException("自发货:平台商品库存不足，拿货数量为" + quantity + "，平台库存量" + pfSkuStock.getStock());
            }
        }
        //修改订单状态，订单待发货
        pfBorder.setOrderStatus(BOrderStatus.WaitShip.getCode());
        if (pfBorderMapper.updateById(pfBorder) == 0) {
            throw new BusinessException("自发货:修改订单状态失败");
        }
        //添加订单日志
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder,"订单待发货，自发货处理排单订单");
        this.sendMessage(pfBorder);
        return "success";
    }

    /**
     * 判断订单库存是否充足
     *
     * @author ZhaoLiang
     * @date 2016/3/18 14:25
     */
    private boolean checkBOrderStock(PfBorder pfBorder) {
        List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            int n = skuService.checkSkuStock(pfBorderItem.getSkuId(), pfBorderItem.getQuantity(), pfBorder.getUserPid());
            if (n < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 想用户发送信息
     * @param pfBorder
     */
    private void sendMessage(PfBorder pfBorder){

        ComUser comUser = comUserService.findById(pfBorder.getUserId());
        List<PfBorderItem> pfBorderItems = pfBorderItemMapper.getPfBorderItemDetail(pfBorder.getId());
        //平台代发货
        if ("1".equals(pfBorder.getSendType())){
            MobileMessageUtil.dealQueueOrderPlatform(comUser.getMobile(),pfBorder.getOrderCode());
            String[] params;
            for (PfBorderItem pfBorderItem : pfBorderItems){
                params = new String[5];
                params[0] = pfBorderItem.getSkuName();
                params[1] = String.valueOf(pfBorder.getOrderAmount());
                params[2] = String.valueOf(pfBorderItem.getQuantity());
                params[3] = pfBorder.getOrderType() == 0?"代理":pfBorder.getOrderType() == 1?"补货":"拿货";
                params[4] = BOrderStatus.Complete.getDesc();
                WxPFNoticeUtils.getInstance().dealWithOrderInQueueByPlatForm(comUser,params);
            }
        }else {//自己发货
            String[] params;
            for (PfBorderItem pfBorderItem : pfBorderItems){
                params = new String[5];
                params[0] = pfBorderItem.getSkuName();
                params[1] = String.valueOf(pfBorder.getOrderAmount());
                params[2] = String.valueOf(pfBorderItem.getQuantity());
                params[3] = pfBorder.getOrderType() == 0?"代理":pfBorder.getOrderType() == 1?"补货":"拿货";
                params[4] = BOrderStatus.WaitShip.getDesc();
                WxPFNoticeUtils.getInstance().dealWithOrderInQueueBySelf(comUser,params);
            }
        }
    }
}
