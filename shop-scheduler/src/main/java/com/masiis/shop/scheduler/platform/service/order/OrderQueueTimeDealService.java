package com.masiis.shop.scheduler.platform.service.order;

import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.scheduler.platform.service.user.ComUserService;
import com.masiis.shop.scheduler.utils.wx.WxPFNoticeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wangbingjian on 2016/4/21.
 */
@Service
@Transactional
public class OrderQueueTimeDealService {

    private static final Logger logger = Logger.getLogger(OrderQueueTimeDealService.class);

    @Autowired
    private PfBorderMapper pfBorderMapper;
    @Autowired
    private PfBorderItemMapper pfBorderItemMapper;
    @Autowired
    private PfSkuStockMapper pfSkuStockMapper;
    @Autowired
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Autowired
    private ComUserService comUserService;
    @Autowired
    private BOrderPayService bOrderPayService;

    /**
     * 定时处理排单状态订单
     */
    public void commonQueuingOrder(){
        logger.info("1:查询出所有的排单状态的订单，按照userpid 与 createtime排序查询");
        List<PfBorder> pfBorders = pfBorderMapper.selectAllQueuingOrder(BOrderStatus.MPS.getCode());
        logger.info("查询排单订单数量：" + pfBorders.size());
        for (PfBorder pfBorder : pfBorders){
            try{
                logger.info(pfBorder.getSendType() == 1?"平台代发货":"自己发货");
                if (pfBorder.getSendType() == 1){//平台代发货
                    pfQueuingOrder(pfBorder.getId());
                }else if (pfBorder.getSendType() == 2){//自己发货
                    userQueuingOrder(pfBorder.getId());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * 平台代发处理排单
     * @param orderId 订单id
     * @throws Exception
     */
    private void pfQueuingOrder(Long orderId) throws Exception{
        logger.info("进入平台代发排单处理Service");
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(orderId);
        if (pfBorder == null) {
            return;
        }
        if (pfBorder.getSendType() != 1) {
            logger.info("平台代发:该订单不是平台代发货，orderId=" + orderId);
            return;
        }
        if (pfBorder.getOrderStatus() != 6) {
            logger.info("平台代发:该订单不是排单状态，orderId=" + orderId);
            return;
        }
        //判断库存是否充足,库存不足直接返回
        if (!checkBOrderStock(pfBorder)){
            logger.info("库存不足");
            return;
        }
        logger.info("查询订单子表信息");
        List<PfBorderItem> orderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        int skuId;
        int quantity = 0;
        boolean dealOrder = true;
//        PfUserSkuStock userSkuStock;
        for (PfBorderItem orderItem : orderItems) {
            skuId = orderItem.getSkuId();
            quantity = orderItem.getQuantity();
            //根据sku_id查询商品sku库存数据
            PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
            if (quantity >= pfSkuStock.getStock() - pfSkuStock.getFrozenStock()){
                dealOrder = false;
            }
        }
        if (dealOrder){
            bOrderPayService.saveBOrderSendType(pfBorder);
            this.sendMessage(pfBorder,orderItems);
        }
    }

    /**
     * 用户自发货订单排单处理
     * @param orderId       订单id
     * @throws Exception
     */
    private void userQueuingOrder(Long orderId) throws Exception {
//        logger.info("用户自发货订单排单处理Service");
//        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(orderId);
//        if (pfBorder == null) {
//            logger.info("自发货:订单编号不存在，orderCode=" + orderId);
//            return;
//        }
//        if (pfBorder.getSendType() != 2) {
//            logger.info("自发货:该订单不是自发货，orderCode=" + orderId);
//            return;
//        }
//        if (pfBorder.getOrderStatus() != 6) {
//            logger.info("自发货:该订单不是排单状态，orderCode=" + orderId);
//            return;
//        }
//        //判断库存是否充足,库存不足直接返回
//        if (!checkBOrderStock(pfBorder)){
//            logger.info("自发货:库存不足，orderCode=" + orderId);
//            return;
//        }
//        List<PfBorderItem> orderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
//        int skuId;
//        int quantity = 0;
//        for (PfBorderItem borderItem : orderItems) {
//            quantity = borderItem.getQuantity();
//            skuId = borderItem.getSkuId();
//            //根据sku_id查询商品sku库存数据
//            PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
//            //当取货数量不大于平台当前库存则直接进行平台减库存加冻结库存
//            //取货数量大于平台库存量则向上抛出异常
//            if (quantity > pfSkuStock.getStock() - pfSkuStock.getFrozenStock()) {
//                logger.info("自发货:平台商品库存不足，拿货数量为" + quantity + "，平台库存量" + pfSkuStock.getStock());
//                throw new BusinessException("自发货:平台商品库存不足，拿货数量为" + quantity + "，平台库存量" + pfSkuStock.getStock());
//            }
//        }
//        //修改订单状态，订单待发货
//        pfBorder.setOrderStatus(BOrderStatus.WaitShip.getCode());
//        if (pfBorderMapper.updateById(pfBorder) == 0) {
//            throw new BusinessException("自发货:修改订单状态失败");
//        }
//        //添加订单日志
//        bOrderOperationLogService.insertBOrderOperationLog(pfBorder,"订单待发货，自发货处理排单订单");
    }

    /**
     * 判断订单库存是否充足
     */
    private boolean checkBOrderStock(PfBorder pfBorder) {
        List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            int n = checkSkuStock(pfBorderItem.getSkuId(), pfBorderItem.getQuantity(), pfBorder.getUserPid());
            if (n < 0) {
                return false;
            }
        }
        return true;
    }
    /**
     * 判断库存是否足够
     */
    private int checkSkuStock(Integer skuId, int quantity, Long pUserId) {
        int a;
        if (pUserId == 0) {
            PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
            a = pfSkuStock.getStock() - pfSkuStock.getFrozenStock();
        } else {
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pUserId, skuId);
            a = pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock();
        }
        return a;
    }

    /**
     * 向用户发送信息
     * @param pfBorder
     */
    private void sendMessage(PfBorder pfBorder,List<PfBorderItem> pfBorderItems){

        ComUser comUser = comUserService.getUserById(pfBorder.getUserId());
        try{
            //平台代发货
            if (pfBorder.getSendType().equals(1)){
                MobileMessageUtil.getInitialization("").dealQueueOrderRemind(comUser.getMobile(), pfBorder.getOrderCode() ,pfBorder.getSendType());
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
