package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.order.*;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.constants.SysConstants;
import com.masiis.shop.web.mall.service.product.SkuService;
import com.masiis.shop.web.mall.service.user.SfUserAccountService;
import com.masiis.shop.web.mall.utils.wx.WxSFNoticeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 小铺订单
 * @author muchaofeng
 * @date 2016/4/8 17:52
 */

@Service
@Transactional
public class SfOrderManageService {

    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private SfOrderManageMapper sfOrderManageMapper;
    @Autowired
    private SfOrderMapper sfOrderMapper;
    @Resource
    private SfOrderItemMapper sfOrderItemMapper;
    @Autowired
    private ComSkuImageMapper comSkuImageMapper;
    @Autowired
    private SfOrderMallFreightMapper sfOrderMallFreightMapper;
    @Autowired
    private SfOrderItemMallMapper sfOrderItemMallMapper;
    @Autowired
    private SkuService skuService;
    @Autowired
    private SfOrderMallConsigneeMapper sfOrderMallConsigneeMapper;
    @Autowired
    private SfUserRelationMapper sfUserRelationMapper;
    @Autowired
    private SfOrderOperationLogMapper sfOrderOperationLogMapper;
    @Autowired
    private SfUserAccountService sfUserAccountService;

    /**
     * 订单
     * @author muchaofeng
     * @date 2016/4/9 17:12
     */
    public List<SfOrder> findOrdersByUserId(Long userId,Integer orderStatus, Long shopId){
        List<SfOrder> sfOrders = sfOrderManageMapper.selectByUserId(userId, orderStatus, shopId);
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        for (SfOrder sfOrder : sfOrders) {
            List<SfOrderItem> sfOrderItems = sfOrderItemMallMapper.selectBySfOrderId(sfOrder.getId());
            for (SfOrderItem sfOrderItem : sfOrderItems) {
                ComSkuImage comSkuImage = skuService.findComSkuImage(sfOrderItem.getSkuId());
                sfOrderItem.setSkuUrl(skuValue + comSkuImage.getImgUrl());
                sfOrder.setTotalQuantity(sfOrder.getTotalQuantity() + sfOrderItem.getQuantity());//订单商品总量
            }
            sfOrder.setSfOrderItems(sfOrderItems);
        }
        return sfOrders;
    }


    /**
     * 获取上级
     * @author muchaofeng
     * @date 2016/4/9 17:11
     */
    public SfUserRelation findSfUserRelationByUserId(Long userId){
        return sfUserRelationMapper.getSfUserRelationByUserId(userId);
    }

    /**
     * 根据订单Id获取订单
     * @author muchaofeng
     * @date 2016/4/9 17:12
     */
    public SfOrder findOrderByOrderId(Long orderId){
        return sfOrderMapper.selectByPrimaryKey(orderId);
    }
    /**
     * 根据订单id找订单商品关系
     * @author muchaofeng
     * @date 2016/4/9 17:13
     */
    public List<SfOrderItem> findSfOrderItemBySfOrderId(Long sfOrderId){
        return sfOrderItemMallMapper.selectBySfOrderId(sfOrderId);
    }
    /**
     * 查询图片地址
     * @author muchaofeng
     * @date 2016/4/9 17:14
     */
    public ComSkuImage findComSkuImage(Integer skuId) {
        return comSkuImageMapper.selectDefaultImgBySkuId(skuId);
    }
    /**
     * 获取快递公司
     * @author muchaofeng
     * @date 2016/4/9 17:15
     */
    public List<SfOrderFreight> findSfOrderFreight(Long orderId) {
        return sfOrderMallFreightMapper.selectByOrderId(orderId);
    }
    /**
     * 收货人信息
     * @author muchaofeng
     * @date 2016/4/9 17:15
     */
    public SfOrderConsignee findSfOrderConsignee(Long orderId) {
        return sfOrderMallConsigneeMapper.selectBySfOrderId(orderId);
    }
    /**
     * 收货
     * @author muchaofeng
     * @date 2016/4/1 18:12
     */
    @Transactional
    public void deliver(Long orderId ,ComUser user) throws Exception {
        SfOrder sfOrder = sfOrderMapper.selectByPrimaryKey(orderId);
        List<SfOrderItem> orderItemByOrderId = sfOrderItemMapper.getOrderItemByOrderId(sfOrder.getId());
        // 进行订单分润和代理商销售额、收入计算
        sfUserAccountService.countingSfOrder(sfOrder);
        // 进行订单状态修改
        sfOrder.setOrderStatus(3);
        sfOrder.setReceiptTime(new Date());
        sfOrderMapper.updateByPrimaryKey(sfOrder);
        SfOrderOperationLog sfOrderOperationLog = new SfOrderOperationLog();
        sfOrderOperationLog.setCreateMan(sfOrder.getUserId());
        sfOrderOperationLog.setCreateTime(new Date());
        sfOrderOperationLog.setSfOrderStatus(BOrderStatus.Complete.getCode());
        sfOrderOperationLog.setSfOrderId(sfOrder.getId());
        sfOrderOperationLog.setRemark("订单完成");
        sfOrderOperationLogMapper.insert(sfOrderOperationLog);
        String[] params = new String[5];
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        params[0] = sfOrder.getOrderCode();
        if(orderItemByOrderId.size()==1){
            params[1] = orderItemByOrderId.get(0).getSkuName();
        }else if(orderItemByOrderId.size()>1){
            params[1] = orderItemByOrderId.get(0).getSkuName()+"等";
        }
        params[2] =sdf.format(sfOrder.getCreateTime());//下单时间
        params[3] = sdf.format(sfOrder.getShipTime());//发货时间
        params[4] = sdf.format(new Date());//收货时间

        for (int i = 0;i<params.length;i++){
            log.info("第"+i+"个参数-----"+params[i]);
        }

        Boolean aBoolean = WxSFNoticeUtils.getInstance().orderConfirmNotice(user, params);
        log.info("发送短信失败或成功----"+aBoolean);
        if (aBoolean == false) {
            throw new BusinessException("订单完成微信提示失败");
        }
        MobileMessageUtil.getInitialization("C").consumerConsumeSuccessRemind(user.getMobile(),sfOrder.getOrderCode());
    }
}
