package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.enums.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.user.PfUserSkuCustom;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import org.apache.log4j.Logger;
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
    private PfBorderOperationLogMapper pfBorderOperationLogMapper;
    @Resource
    private PfUserSkussMapper pfUserSkussMapper;
    @Resource
    private ComAgentLevelsMapper comAgentLevelsMapper;
    @Resource
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Resource
    private PfBorderFreightMapper pfBorderFreightMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private SkuService skuService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private BorderSkuStockService borderSkuStockService;
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private ComUserAccountService comUserAccountService;

    /**
     * 添加合伙订单
     *
     * @param pfBorder
     * @param pfBorderItems
     */
    @Transactional
    public Long AddBOrder(PfBorder pfBorder, List<PfBorderItem> pfBorderItems) throws Exception {
        if (pfBorder == null) {
            throw new BusinessException("pfBorder为空");
        }
        if (pfBorderItems == null || pfBorderItems.size() == 0) {
            throw new BusinessException("pfBorderItems为空");
        }
        //添加订单
        pfBorderMapper.insert(pfBorder);
        //添加订单商品
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            pfBorderItem.setPfBorderId(pfBorder.getId());
            pfBorderItemMapper.insert(pfBorderItem);
        }
        //添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderId(pfBorder.getId());
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setPfBorderStatus(0);
        pfBorderOperationLog.setRemark("新增代理订单");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        return pfBorder.getId();
    }


    /**
     * 添加补货订单
     *
     * @return 订单id
     * @author ZhaoLiang
     * @date 2016/3/22 15:44
     */
    @Transactional
    public Long addReplenishmentOrders(Long userId, Integer skuId, int quantity) throws Exception {
        ComUser comUser = comUserMapper.selectByPrimaryKey(userId);
        PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userId, skuId);
        PfSkuAgent pfSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(pfUserSku.getSkuId(), pfUserSku.getAgentLevelId());
        ComSku comSku = skuService.getSkuById(pfUserSku.getSkuId());
        //获取产品单价
        BigDecimal unitPrice = comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount());
        if (pfUserSku == null) {
            throw new BusinessException("您还没有代理过此商品，不能补货。");
        }
        //处理订单数据
        PfBorder order = new PfBorder();
        order.setCreateTime(new Date());
        order.setCreateMan(userId);
        String orderCode = OrderMakeUtils.makeOrder("B");
        order.setOrderCode(orderCode);
        order.setUserMessage("");
        order.setUserId(userId);
        if (pfUserSku.getUserPid() != null && pfUserSku.getUserPid() > 0) {
            order.setUserPid(pfUserSku.getUserPid());
        } else {
            order.setUserPid(0l);
        }
        order.setSupplierId(0);
        order.setReceivableAmount(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        order.setOrderAmount(unitPrice.multiply(BigDecimal.valueOf(quantity)));//运费到付，商品总价即订单总金额
        order.setBailAmount(BigDecimal.ZERO);
        order.setProductAmount(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        order.setShipAmount(BigDecimal.ZERO);
        order.setPayAmount(BigDecimal.ZERO);
        order.setShipManId(0);
        order.setShipManName("");
        order.setShipType(0);
        order.setShipRemark("");
        order.setSendType(comUser.getSendType());
        order.setOrderType(1);//订单类型(0代理1补货2拿货)
        order.setOrderStatus(0);
        order.setShipStatus(0);
        order.setPayStatus(0);
        order.setIsCounting(0);
        order.setIsShip(0);
        order.setIsReplace(0);
        order.setIsReceipt(0);
        order.setReplaceOrderId(0l);
        order.setRemark("补货订单");
        pfBorderMapper.insert(order);
        //处理订单商品
        PfBorderItem pfBorderItem = new PfBorderItem();
        pfBorderItem.setCreateTime(new Date());
        pfBorderItem.setPfBorderId(order.getId());
        pfBorderItem.setSpuId(comSku.getSpuId());
        pfBorderItem.setSkuId(comSku.getId());
        pfBorderItem.setSkuName(comSku.getName());
        pfBorderItem.setQuantity(quantity);
        pfBorderItem.setOriginalPrice(comSku.getPriceRetail());
        pfBorderItem.setUnitPrice(unitPrice);
        pfBorderItem.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        pfBorderItem.setBailAmount(BigDecimal.ZERO);
        pfBorderItem.setIsComment(0);
        pfBorderItem.setIsReturn(0);
        pfBorderItem.setAgentLevelId(pfUserSku.getAgentLevelId());
        pfBorderItem.setDiscount(pfSkuAgent.getDiscount());
        pfBorderItem.setBailAmount(BigDecimal.ZERO);
        pfBorderItemMapper.insert(pfBorderItem);
        //添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderId(order.getId());
        pfBorderOperationLog.setCreateMan(order.getUserId());
        pfBorderOperationLog.setPfBorderStatus(0);
        pfBorderOperationLog.setRemark("新增补货订单");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        return order.getId();
    }

    /**
     * 添加拿货订单
     * @param userId   用户id
     * @param skuId    商品id
     * @param quantity 拿货数量
     * @param message  用户留言
     * <1>处理订单数据
     * <2>添加订单日志
     * <3>冻结sku库存 如果用户id是0 则为平台直接代理商扣减平台商品库存
     * <4>添加订单地址信息
     * @auth:wbj
     * @return
     * @throws Exception
     */
    @Transactional
    public Long addProductTake(Long userId, Integer skuId, int quantity, String message, long userAddressId) throws Exception {
        logger.info("进入拿货订单处理Service");
        logger.info("<1>处理订单数据");
        PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userId, skuId);
        if (pfUserSku == null) {
            throw new BusinessException("您还没有代理过此商品，不能拿货。");
        }
        ComUser comUser = comUserMapper.selectByPrimaryKey(userId);
        if (comUser.getSendType() != 1) {
            throw new BusinessException("发货方式不是平台代发，不能拿货");
        }
        Integer levelId = pfUserSku.getAgentLevelId();//代理等级
        Long pUserId = 0l;//上级代理用户id
        BigDecimal amount = BigDecimal.ZERO;//订单总金额
        Long rBOrderId = 0l;//返回生成的订单id
        //获取上级代理
        PfUserSku paremtUserSku = pfUserSkuMapper.selectByPrimaryKey(pfUserSku.getPid());
        if (paremtUserSku != null) {
            pUserId = paremtUserSku.getUserId();
        }
        PfSkuAgent pfSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(skuId, levelId);
        ComSku comSku = skuService.getSkuById(skuId);
        amount = comSku.getPriceRetail().multiply(BigDecimal.valueOf(quantity)).multiply(pfSkuAgent.getDiscount());
        //处理订单数据
        PfBorder order = new PfBorder();
        order.setCreateTime(new Date());
        order.setCreateMan(userId);
        String orderCode = OrderMakeUtils.makeOrder("B");
        order.setOrderCode(orderCode);
        order.setUserMessage(message);
        order.setUserId(userId);
        order.setUserPid(pUserId);
        order.setBailAmount(new BigDecimal(0));
        order.setSupplierId(0);
        order.setReceivableAmount(amount);
        order.setOrderAmount(amount);//运费到付，商品总价即订单总金额
        order.setProductAmount(amount);
        order.setShipAmount(BigDecimal.ZERO);
        order.setPayAmount(BigDecimal.ZERO);
        order.setShipType(0);
        order.setSendType(comUser.getSendType());
        order.setOrderStatus(BOrderStatus.WaitShip.getCode());    //待发货
        order.setShipStatus(0);
        order.setPayStatus(1);      //已支付
        order.setIsShip(0);
        order.setIsReplace(0);
        order.setIsReceipt(0);
        order.setIsCounting(0);
        order.setRemark("拿货订单");
        order.setOrderType(2);
        pfBorderMapper.insert(order);
        rBOrderId = order.getId();
        PfBorderItem pfBorderItem = new PfBorderItem();
        pfBorderItem.setCreateTime(new Date());
        pfBorderItem.setPfBorderId(rBOrderId);
        pfBorderItem.setSpuId(comSku.getSpuId());
        pfBorderItem.setSkuId(comSku.getId());
        pfBorderItem.setSkuName(comSku.getName());
        pfBorderItem.setBailAmount(new BigDecimal(0));
        pfBorderItem.setQuantity(quantity);
        pfBorderItem.setOriginalPrice(comSku.getPriceRetail());
        pfBorderItem.setUnitPrice(comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount()));
        pfBorderItem.setTotalPrice(comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount()).multiply(BigDecimal.valueOf(quantity)));
        pfBorderItem.setIsComment(0);
        pfBorderItem.setIsReturn(0);
        pfBorderItemMapper.insert(pfBorderItem);
        logger.info("<2>添加订单日志");
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(order.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(order.getOrderStatus());
        pfBorderOperationLog.setPfBorderId(order.getId());
        pfBorderOperationLog.setRemark("订单已支付,拿货订单");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);

        logger.info("<3>冻结sku库存 如果用户id是0 则为平台直接代理商扣减平台商品库存");
        PfSkuStock pfSkuStock = null;
        PfUserSkuStock pfUserSkuStock = null;
        //冻结sku库存 如果用户id是0 则为平台直接代理商扣减平台商品库存
        if (order.getUserPid() == 0) {
            pfSkuStock = pfSkuStockMapper.selectBySkuId(pfBorderItem.getSkuId());
            if (pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < quantity) {
                throw new BusinessException("拿货失败：拿货数量超过库存数量");
            }
            pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + quantity);
            if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) == 0) {
                throw new BusinessException("并发修改库存失败");
            }
        } else {
            pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(order.getUserPid(), pfBorderItem.getSkuId());
            if (pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock() < quantity) {
                throw new BusinessException("拿货失败：拿货数量超过库存数量");
            }
            pfUserSkuStock.setFrozenStock(pfUserSkuStock.getFrozenStock() + quantity);
            if (pfUserSkuStockMapper.updateByIdAndVersion(pfUserSkuStock) == 0) {
                throw new BusinessException("并发修改库存失败");
            }
        }
        logger.info("<4>添加订单地址信息");
        ComUserAddress comUserAddress = userAddressService.getUserAddressById(userAddressId);
        PfBorderConsignee pfBorderConsignee = new PfBorderConsignee();
        pfBorderConsignee.setCreateTime(new Date());
        pfBorderConsignee.setPfBorderId(order.getId());
        pfBorderConsignee.setUserId(comUserAddress.getUserId());
        pfBorderConsignee.setConsignee(comUserAddress.getName());
        pfBorderConsignee.setMobile(comUserAddress.getMobile());
        pfBorderConsignee.setProvinceId(comUserAddress.getProvinceId());
        pfBorderConsignee.setProvinceName(comUserAddress.getProvinceName());
        pfBorderConsignee.setCityId(comUserAddress.getCityId());
        pfBorderConsignee.setCityName(comUserAddress.getCityName());
        pfBorderConsignee.setRegionId(comUserAddress.getRegionId());
        pfBorderConsignee.setRegionName(comUserAddress.getRegionName());
        pfBorderConsignee.setAddress(comUserAddress.getAddress());
        pfBorderConsignee.setZip(comUserAddress.getZip());
        pfBorderConsigneeMapper.insert(pfBorderConsignee);
        return rBOrderId;
    }

    /**
     * 订单支付
     *
     * @author ZhaoLiang
     * @date 2016/4/9 11:13
     */
    @Transactional
    public void toPayBOrder(PfBorder pfBorder, PfBorderConsignee pfBorderConsignee) throws Exception {
        pfBorderMapper.updateById(pfBorder);
        if (pfBorderConsignee != null) {
            PfBorderConsignee pbc = pfBorderConsigneeMapper.selectByBorderId(pfBorderConsignee.getPfBorderId());
            if (pbc != null) {
                pfBorderConsigneeMapper.deleteByOrderId(pfBorderConsignee.getPfBorderId());
            }
            pfBorderConsigneeMapper.insert(pfBorderConsignee);
        }
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
            }
            if (pfBorder.getUserPid() == 0) {
                pfBorder.setPidUserName("平台代理");
            } else {
                ComUser user = comUserMapper.selectByPrimaryKey(pfBorder.getUserPid());
                pfBorder.setPidUserName(user.getRealName());
            }
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
            }
            if (pfBorder.getUserPid() == 0) {
                pfBorder.setPidUserName("平台代理");
            } else {
                ComUser user = comUserMapper.selectByPrimaryKey(pfBorder.getUserPid());
                pfBorder.setPidUserName(user.getRealName());
            }
            pfBorder.setPfBorderItems(pfBorderItems);
        }
        return pfBorders;
    }

    /**
     * 判断订单库存是否充足
     *
     * @author ZhaoLiang
     * @date 2016/3/18 14:25
     */
    public boolean checkBOrderStock(PfBorder pfBorder) {
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
     * 发货
     *
     * @author muchaofeng
     * @date 2016/4/1 18:12
     */
    @Transactional
    public void deliver(String shipManName, Long orderId, String freight, String shipManId, ComUser user) throws Exception {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(orderId);
        if(pfBorder.getSendType() == 0){
            throw new BusinessException("请选择发货方式");
        }
        if (pfBorder.getSendType() == 1) {//平台代发
            if (pfBorder.getOrderType() == 2) {//拿货
                if (freight == null || freight == "") {
                    throw new BusinessException("请重新输入快递单号");
                } else {
                    pfBorder.setShipStatus(5);
                    pfBorder.setOrderStatus(8);
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
                    PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
                    pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
                    pfBorderOperationLog.setCreateTime(new Date());
                    pfBorderOperationLog.setPfBorderStatus(8);
                    pfBorderOperationLog.setPfBorderId(pfBorder.getId());
                    pfBorderOperationLog.setRemark("订单完成");
                    pfBorderOperationLogMapper.insert(pfBorderOperationLog);
                }
            }
        } else if (pfBorder.getSendType() == 2) {//自己发货
            pfBorder.setShipStatus(5);
            pfBorder.setOrderStatus(8);
            PfBorderFreight pfBorderFreight = new PfBorderFreight();
            pfBorderFreight.setCreateTime(new Date());
            pfBorderFreight.setPfBorderId(orderId);
            pfBorderFreight.setFreight(freight);
            pfBorderFreight.setShipManName(shipManName);
            pfBorderMapper.updateById(pfBorder);
            pfBorderFreightMapper.insert(pfBorderFreight);
            //添加订单日志
            PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
            pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
            pfBorderOperationLog.setCreateTime(new Date());
            pfBorderOperationLog.setPfBorderStatus(8);
            pfBorderOperationLog.setPfBorderId(pfBorder.getId());
            pfBorderOperationLog.setRemark("订单完成");
            pfBorderOperationLogMapper.insert(pfBorderOperationLog);
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
     * 订单完成处理统一入口
     *
     * @author ZhaoLiang
     * @date 2016/4/9 11:22
     */
    @Transactional
    public void completeBOrder(PfBorder pfBorder) throws Exception {
        if (pfBorder == null) {
            throw new BusinessException("订单为空对象");
        }
        if (pfBorder.getPayStatus() != 1) {
            throw new BusinessException("订单还未支付怎么能完成呢？");
        }
        pfBorder.setOrderStatus(BOrderStatus.Complete.getCode());
        pfBorderMapper.updateById(pfBorder);
        //添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(BOrderStatus.Complete.getCode());
        pfBorderOperationLog.setPfBorderId(pfBorder.getId());
        pfBorderOperationLog.setRemark("订单完成");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        //订单类型(0代理1补货2拿货)
        if (pfBorder.getOrderType() == 0 || pfBorder.getOrderType() == 1) {
            comUserAccountService.countingByOrder(pfBorder);
        }
    }
}
