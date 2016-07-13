package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.enums.platform.BOrderShipStatus;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrder;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.UpgradeNoticeService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.common.utils.wx.WxPFNoticeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
public class BOrderService {

    private final static Logger logger = Logger.getLogger(BOrderService.class);

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
    private BOrderOperationLogService bOrderOperationLogService;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private PfBorderFreightMapper pfBorderFreightMapper;
    @Resource
    private SkuService skuService;
    @Resource
    private BOrderSkuStockService borderSkuStockService;
    @Resource
    private ComUserAccountService comUserAccountService;
    @Resource
    private UpgradeNoticeService upgradeNoticeService;
    @Resource
    private UserService comUserService;

    public int insertPfBorder(PfBorder pfBorder){
        return pfBorderMapper.insert(pfBorder);
    }
    public int insertPfBorderItem(PfBorderItem pfBorderItem){
        return pfBorderItemMapper.insert(pfBorderItem);
    }

    public int updatePfBorder(PfBorder pfBorder){
        return pfBorderMapper.updateById(pfBorder);
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
     * 根据pfBorderId查询
     *
     * @param pfBorderId
     * @return
     */
    public List<PfBorderItem> getPfBorderItemDetail(Long pfBorderId) {
        return pfBorderItemMapper.getPfBorderItemDetail(pfBorderId);
    }

    /**
     * 获取合伙人等级
     *
     * @author muchaofeng
     * @date 2016/3/9 18:52
     */
    public ComAgentLevel findComAgentLevel(Integer id) {
        return comAgentLevelMapper.selectByPrimaryKey(id);
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

    public List<PfBorder> findByUserId(Long UserId, Integer orderStatus, Integer sendType) {
        return pfBorderMapper.selectByUserId(UserId, orderStatus, sendType);
    }

    /**
     * 根据用户id获取出货订单
     *
     * @author muchaofeng
     * @date 2016/3/23 14:36
     */
    public List<PfBorder> findByUserPid(Long UserId, Integer orderStatus, Integer sendType) {
        return pfBorderMapper.selectByUserPid(UserId, orderStatus, sendType);
    }

    /**
     * 添加订单支付记录
     *
     * @author ZhaoLiang
     * @date 2016/3/16 12:42
     */
    @Transactional
    public void addBOrderPayment(PfBorderPayment pfBorderPayment) throws Exception {
        pfBorderPaymentMapper.insert(pfBorderPayment);
    }

    /**
     * 根据支付流水号查询支付记录
     *
     * @param paySerialNum
     * @return
     */
    public PfBorderPayment findOrderPaymentBySerialNum(String paySerialNum) {
        return pfBorderPaymentMapper.selectBySerialNum(paySerialNum);
    }

    /**
     * 根据订单号获取快递信息
     *
     * @author muchaofeng
     * @date 2016/3/16 15:21
     */
    public List<PfBorderFreight> findByPfBorderFreightOrderId(Long id) {
        return pfBorderFreightMapper.selectByBorderId(id);
    }

    /**
     * 根据订单号获取收货人信息
     *
     * @author muchaofeng
     * @date 2016/3/16 15:36
     */
    public PfBorderConsignee findpfBorderConsignee(Long id) {
        return pfBorderConsigneeMapper.selectByBorderId(id);
    }

    /**
     * 根据userId获取关系
     *
     * @author muchaofeng
     * @date 2016/3/21 17:37
     */
    public PfUserSku findPfUserSku(long userId, Integer skuId) {
        return pfUserSkuMapper.selectByUserIdAndSkuId(userId, skuId);
    }

    /**
     * 异步查询进货订单
     *
     * @author muchaofeng
     * @date 2016/4/6 14:36
     */
    public List<PfBorder> findPfBorder(long userId, Integer orderStatus, Integer sendType) {
        List<PfBorder> pfBorders = pfBorderMapper.selectByUserId(userId, orderStatus, sendType);
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        for (PfBorder pfBorder : pfBorders) {
            List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
            for (PfBorderItem pfBorderItem : pfBorderItems) {
                pfBorderItem.setSkuUrl(skuValue + skuService.findComSkuImage(pfBorderItem.getSkuId()).getImgUrl());
                pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
                pfBorderItem.setSkuMoney(pfBorderItem.getUnitPrice().toString());
            }
            pfBorder.setOrderMoney(pfBorder.getOrderAmount().toString());
            pfBorder.setBailMoney(pfBorder.getBailAmount().toString());
//            if (pfBorder.getUserPid() == 0) {
//                pfBorder.setPidUserName("平台代理");
//            } else {
//                ComUser user = comUserMapper.selectByPrimaryKey(pfBorder.getUserPid());
//                pfBorder.setPidUserName(user.getRealName());
//            }
            pfBorder.setPidUserName("平台");
            String insertDay = DateUtil.insertDay(pfBorder.getCreateTime());
            pfBorder.setPayTimes(insertDay);
            pfBorder.setPfBorderItems(pfBorderItems);
        }
        return pfBorders;
    }

    /**
     * 异步查询出货订单
     *
     * @author muchaofeng
     * @date 2016/4/7 15:54
     */
    public List<PfBorder> findPfpBorder(long userId, Integer orderStatus, Integer sendType) {
        List<PfBorder> pfBorders = pfBorderMapper.selectByUserPid(userId, orderStatus, sendType);
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        for (PfBorder pfBorder : pfBorders) {
            List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
            for (PfBorderItem pfBorderItem : pfBorderItems) {
                ComSkuImage comSkuImage = skuService.findComSkuImage(pfBorderItem.getSkuId());
                pfBorderItem.setSkuUrl(skuValue + comSkuImage.getImgUrl());
                pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
                pfBorderItem.setSkuMoney(pfBorderItem.getUnitPrice().toString());
            }
            pfBorder.setOrderMoney(pfBorder.getOrderAmount().toString());
            pfBorder.setBailMoney(pfBorder.getBailAmount().toString());
            if (pfBorder.getSendType() == 1) {
                pfBorder.setPidUserName("平台");
            } else if (pfBorder.getSendType() == 0 || pfBorder.getSendType() == null) {
                pfBorder.setPidUserName("未选择");
            } else if (pfBorder.getSendType() == 2) {
                pfBorder.setPidUserName("自己发货");
            }
//            if (pfBorder.getUserPid() == 0) {
//                pfBorder.setPidUserName("平台");
//            } else {
//                ComUser user = comUserMapper.selectByPrimaryKey(pfBorder.getUserPid());
//                pfBorder.setPidUserName(user.getRealName());
//            }
            pfBorder.setPfBorderItems(pfBorderItems);
        }
        return pfBorders;
    }

    /**
     * 发货
     *
     * @author muchaofeng
     * @date 2016/4/1 18:12
     */
    @Transactional
    public void deliver(String shipManName, Long orderId, String freight, String shipManId, ComUser user) throws Exception {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(orderId);
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        if (pfBorder.getSendType() == 0) {
            throw new BusinessException("请选择发货方式");
        }
        if (freight == null || freight == "") {
            throw new BusinessException("请重新输入快递单号");
        }
        PfBorderItem pfBorderItems = pfBorderItemMapper.selectByOrderId(pfBorder.getId());
        ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pfBorderItems.getAgentLevelId());
        if (pfBorder.getSendType() == 1) {//平台代发
            if (pfBorder.getOrderType() == 2) {//拿货
                pfBorder.setShipStatus(5);
                pfBorder.setOrderStatus(8);
                pfBorder.setIsShip(1);
                PfBorderFreight pfBorderFreight = new PfBorderFreight();
                pfBorderFreight.setCreateTime(new Date());
                pfBorderFreight.setShipManId(Integer.parseInt(shipManId));
                pfBorderFreight.setPfBorderId(orderId);
                pfBorderFreight.setFreight(freight);
                pfBorderFreight.setShipManName(shipManName);
                borderSkuStockService.updateStock(pfBorder, user);
                pfBorderMapper.updateById(pfBorder);
                pfBorderFreightMapper.insert(pfBorderFreight);
                //添加订单日志
                bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单完成");

                String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/deliveryBorderDetils.html?id=" + pfBorder.getId().toString();
                String[] params = new String[5];
                params[0] = pfBorderItems.getSkuName();
                params[1] = comAgentLevel.getName();
                params[2] = pfBorder.getOrderCode();
                params[3] = shipManName;
                params[4] = freight;
                Boolean aBoolean = WxPFNoticeUtils.getInstance().orderShippedNotice(comUser, params, url);
                if (aBoolean == false) {
                    throw new Exception("订单发货微信提示失败");
                }
                MobileMessageUtil.getInitialization("B").goodsOrderShipRemind(comUser.getMobile(), pfBorder.getOrderCode(), shipManName, freight);
            }
        } else if (pfBorder.getSendType() == 2) {//自己发货
            pfBorder.setShipStatus(5);
            pfBorder.setOrderStatus(8);
            pfBorder.setIsShip(1);
            PfBorderFreight pfBorderFreight = new PfBorderFreight();
            pfBorderFreight.setCreateTime(new Date());
            pfBorderFreight.setPfBorderId(orderId);
            pfBorderFreight.setFreight(freight);
            pfBorderFreight.setShipManName(shipManName);
            pfBorderMapper.updateById(pfBorder);
            pfBorderFreightMapper.insert(pfBorderFreight);
            //添加订单日志
            bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单完成");
            String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/deliveryBorderDetils.html?id=" + pfBorder.getId().toString();
            String[] params = new String[5];
            params[0] = pfBorderItems.getSkuName();
            params[1] = comAgentLevel.getName();
            params[2] = pfBorder.getOrderCode();
            params[3] = shipManName;
            params[4] = freight;
            Boolean aBoolean = WxPFNoticeUtils.getInstance().orderShippedNotice(comUser, params, url);
            if (aBoolean == false) {
                throw new Exception("订单发货微信提示失败");
            }
            MobileMessageUtil.getInitialization("B").goodsOrderShipRemind(comUser.getMobile(), pfBorder.getOrderCode(), shipManName, freight);
        }
    }

    /**
     * 收货
     *
     * @author muchaofeng
     * @date 2016/4/1 18:42
     */

    @Transactional
    public void closeDeal(Long orderId, ComUser user) throws Exception {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(orderId);
        if (pfBorder.getSendType() == 1) {//平台代发
            if (pfBorder.getOrderType() == 2) {//拿货
                pfBorder.setOrderStatus(3);
                pfBorder.setShipStatus(9);
//                borderSkuStockService.updateGetStock(pfBorder, user);
                pfBorderMapper.updateById(pfBorder);
                //comUserAccountService.countingByOrder(pfBorder);
            }
        } else if (pfBorder.getSendType() == 2) {//自己发货
            pfBorder.setOrderStatus(3);
            pfBorder.setShipStatus(9);
            pfBorderMapper.updateById(pfBorder);
            //comUserAccountService.countingByOrder(pfBorder);
        }
    }


    /**
     * 获取排队订单数量
     *
     * @param skuId
     * @return
     */
    public Integer selectQueuingOrderCount(Integer skuId) {
        return pfBorderMapper.selectQueuingOrderCount(skuId);
    }

    /**
     * 根据userId 和SkuId获取订单信息
     */
    public PfBorder getPfBorderBySkuAndUserId(Integer skuId, Long userId) {
        return pfBorderMapper.selectPfBOrderBySkuIdAndUserId(skuId, userId);
    }

    /**
     * 获得奖励订单
     * @author muchaofeng
     * @date 2016/6/16 14:32
     */

    public List<PfBorder> getRecommendPfBorder(Long userId, Integer skuId) {
        return pfBorderMapper.selectRecommend(userId, skuId);
    }

    /**
     * 发出奖励订单
     * @author muchaofeng
     * @date 2016/6/16 16:46
     */
    public List<PfBorder> SendRecommendPfBorder(Long userId, Integer skuId) {
        return pfBorderMapper.selectSendRecommend(userId, skuId);
    }


    /**
     * 支付成功后查询订单信息
     * @param orderId
     */
    public BOrderUpgradeDetail getUpgradeOrderInfo(Long orderId){
        logger.info("支付成功后查询订单信息----start");
        //根据订单查询通知单号
        logger.info("订单号-------"+orderId);
        PfUserUpgradeNotice upgradeNotice = upgradeNoticeService.selectByPfBorderId(orderId);
        Long newPUserId = null;
        BOrderUpgradeDetail upgradeDetail = null;
        if (upgradeNotice!=null){
            logger.info("通知单号-------"+upgradeNotice.getId());
            upgradeDetail = upgradeNoticeService.getUpgradeNoticeInfo(upgradeNotice.getId());
            if (upgradeDetail!=null){
                PfBorder pfBorder = getPfBorderById(orderId);
                if (pfBorder!=null){
                    logger.info("新上级id--------"+pfBorder.getUserPid());
                    ComUser comUser = comUserService.getUserById(pfBorder.getUserPid());
                    if (comUser!=null){
                        upgradeDetail.setNewPUserId(comUser.getId());
                        upgradeDetail.setNewPUserName(comUser.getRealName());
                    }else{
                        logger.info("新上级不存在-----");
                    }
                }
            }else {
                logger.info("升级通知不存在------订单号----"+orderId);
                throw new BusinessException("升级通知不存在------订单号----"+orderId);
            }
        }
        logger.info("支付成功后查询订单信息----end");
        return upgradeDetail;
    }

    /**
     * 查询进货订单或出货订单
     * @param userId       自己ID
     * @param userPid      上级ID
     * @param orderStatus  订单状态
     * @return
     */
    public List<BOrder> orderList(Long userId, Long userPid, Integer orderStatus) {
        return pfBorderMapper.selectByUserIdOrUserPidAndOrderStatus(userId, userPid, orderStatus);
    }

}
