package com.masiis.shop.web.platform.service.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.beans.order.BOrderAdd;
import com.masiis.shop.dao.beans.order.BOrderConfirm;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.platform.order.PfBorderConsigneeMapper;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderOperationLogMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BOrderAddService
 *
 * @author ZhaoLiang
 * @date 2016/4/14
 */
@Service
public class BOrderAddService {
    private final static Logger logger = Logger.getLogger(BOrderService.class);
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfBorderConsigneeMapper pfBorderConsigneeMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private PfBorderOperationLogMapper pfBorderOperationLogMapper;
    @Resource
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private SkuService skuService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;

    @Transactional
    public Long addBOrder(BOrderAdd bOrderAdd) throws Exception {
        if (bOrderAdd == null) {
            throw new BusinessException("bOrderAdd为空");
        }
        BigDecimal retailPrice = BigDecimal.ZERO;//微信零售价
        BigDecimal discount = BigDecimal.ZERO;//折扣
        BigDecimal bailPrice = BigDecimal.ZERO;//代理保证金
        Integer quantity = 0;//数量
        Integer agentLevelId = 0;//代理等级
        String weiXinId = "";
        ComSku comSku = skuService.getSkuById(bOrderAdd.getSkuId());
        retailPrice = comSku.getPriceRetail();
        PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(bOrderAdd.getUserId(), comSku.getId());
        if (pfUserSku == null) {
            agentLevelId = bOrderAdd.getAgentLevelId();
            weiXinId = bOrderAdd.getWeiXinId();
        } else {
            agentLevelId = pfUserSku.getAgentLevelId();
            weiXinId = pfUserCertificateMapper.selectByUserSkuId(pfUserSku.getId()).getWxId();
        }
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(comSku.getId(), agentLevelId);
        if (pfSkuAgent == null) {
            throw new BusinessException("找不到要代理的商品信息");
        }
        discount = pfSkuAgent.getDiscount();
        //合伙订单需要缴纳保证金
        if (bOrderAdd.getOrderType() == 0) {
            bailPrice = pfSkuAgent.getBail();
            quantity = pfSkuAgent.getQuantity();
        } else {
            bailPrice = BigDecimal.ZERO;
            quantity = bOrderAdd.getQuantity();
        }
        //处理订单数据
        PfBorder pfBorder = new PfBorder();
        pfBorder.setCreateTime(new Date());
        pfBorder.setCreateMan(bOrderAdd.getUserId());
        String orderCode = OrderMakeUtils.makeOrder("B");
        pfBorder.setOrderCode(orderCode);
        pfBorder.setUserMessage(bOrderAdd.getUserMessage());
        pfBorder.setUserId(bOrderAdd.getUserId());
        pfBorder.setUserPid(bOrderAdd.getpUserId());
        pfBorder.setSupplierId(0);
        //商品折扣后价格
        BigDecimal unitPrice = retailPrice.multiply(discount).setScale(2, BigDecimal.ROUND_DOWN);
        //商品总金额=商品微信销售价*折扣*数量
        BigDecimal productAmount = unitPrice.multiply(BigDecimal.valueOf(quantity));
        //订单总金额=商品总金额+保证金+运费
        BigDecimal orderAmount = productAmount.add(bailPrice).add(bOrderAdd.getShipAmount());
        pfBorder.setReceivableAmount(orderAmount);
        pfBorder.setOrderAmount(orderAmount);
        pfBorder.setBailAmount(bailPrice);
        pfBorder.setProductAmount(productAmount);
        pfBorder.setShipAmount(bOrderAdd.getShipAmount());
        pfBorder.setPayAmount(BigDecimal.ZERO);
        pfBorder.setShipManId(0);
        pfBorder.setShipManName("");
        pfBorder.setShipType(0);
        pfBorder.setShipRemark("");
        //确定订单的拿货方式
        pfBorder.setSendType(bOrderAdd.getSendType());
        pfBorder.setOrderType(bOrderAdd.getOrderType());
        pfBorder.setOrderStatus(0);
        pfBorder.setShipStatus(0);
        pfBorder.setPayStatus(0);
        pfBorder.setIsCounting(0);
        pfBorder.setIsShip(0);
        pfBorder.setIsReplace(0);
        pfBorder.setIsReceipt(0);
        pfBorder.setReplaceOrderId(0l);
        pfBorder.setRemark("");
        //添加订单
        pfBorderMapper.insert(pfBorder);
        //处理订单商品数据
        PfBorderItem pfBorderItem = new PfBorderItem();
        pfBorderItem.setPfBorderId(pfBorder.getId());
        pfBorderItem.setCreateTime(new Date());
        pfBorderItem.setSpuId(comSku.getSpuId());
        pfBorderItem.setSkuId(comSku.getId());
        pfBorderItem.setSkuName(comSku.getName());
        pfBorderItem.setAgentLevelId(agentLevelId);
        pfBorderItem.setWxId(weiXinId);
        pfBorderItem.setQuantity(quantity);
        pfBorderItem.setOriginalPrice(retailPrice);
        pfBorderItem.setDiscount(discount);
        pfBorderItem.setUnitPrice(unitPrice);
        pfBorderItem.setTotalPrice(productAmount);
        pfBorderItem.setBailAmount(bailPrice);
        pfBorderItem.setIsComment(0);
        pfBorderItem.setIsReturn(0);
        pfBorderItemMapper.insert(pfBorderItem);
        //添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderId(pfBorder.getId());
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setPfBorderStatus(0);
        pfBorderOperationLog.setRemark("新增订单");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getOrderType() == 2 || pfBorder.getSendType() == 2) {
            //获得地址
            ComUserAddress comUserAddress = userAddressService.getOrderAddress(bOrderAdd.getUserAddressId(), bOrderAdd.getUserId());
            if (comUserAddress == null) {
                throw new BusinessException("请填写收货地址");
            }
            PfBorderConsignee pfBorderConsignee = new PfBorderConsignee();
            pfBorderConsignee.setPfBorderId(pfBorder.getId());
            pfBorderConsignee.setCreateTime(new Date());
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
        }
        return pfBorder.getId();
    }


    /**
     * 添加补货订单
     *
     * @return 订单id
     * @author ZhaoLiang
     * @date 2016/3/22 15:44
     */
    public Long addReplenishmentOrders(Long userId, Integer skuId, int quantity) throws Exception {
        //获取用户代理sku关系数据
        PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userId, skuId);
        if (pfUserSku == null) {
            throw new BusinessException("您还没有代理过此商品，不能补货。");
        }
        //获取商品代理等级数据
        PfSkuAgent pfSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(pfUserSku.getSkuId(), pfUserSku.getAgentLevelId());
        //获取sku信息
        ComSku comSku = skuService.getSkuById(pfUserSku.getSkuId());
        //获取产品单价
        BigDecimal unitPrice = comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount());
        //处理订单数据
        PfBorder order = new PfBorder();
        order.setCreateTime(new Date());
        order.setCreateMan(userId);
        order.setOrderCode(OrderMakeUtils.makeOrder("B"));
        order.setUserMessage("系统生成：用户补货");
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
        //获取拿货方式
        ComUser comUser = comUserMapper.selectByPrimaryKey(userId);
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
        //处理订单商品数据
        List<PfBorderItem> pfBorderItems = new ArrayList<>();
        PfBorderItem pfBorderItem = new PfBorderItem();
        pfBorderItem.setCreateTime(new Date());
        pfBorderItem.setSpuId(comSku.getSpuId());
        pfBorderItem.setSkuId(comSku.getId());
        pfBorderItem.setSkuName(comSku.getName());
        pfBorderItem.setAgentLevelId(pfUserSku.getAgentLevelId());
        pfBorderItem.setWxId("");
        pfBorderItem.setQuantity(quantity);
        pfBorderItem.setOriginalPrice(comSku.getPriceRetail());
        pfBorderItem.setDiscount(pfSkuAgent.getDiscount());
        pfBorderItem.setUnitPrice(unitPrice);
        pfBorderItem.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        pfBorderItem.setBailAmount(BigDecimal.ZERO);
        pfBorderItem.setIsComment(0);
        pfBorderItem.setIsReturn(0);
        pfBorderItems.add(pfBorderItem);
        return null;
    }

    /**
     * 添加拿货订单
     *
     * @param userId   用户id
     * @param skuId    商品id
     * @param quantity 拿货数量
     * @param message  用户留言
     *                 <1>处理订单数据
     *                 <2>添加订单日志
     *                 <3>冻结sku库存 如果用户id是0 则为平台直接代理商扣减平台商品库存
     *                 <4>添加订单地址信息
     * @return
     * @throws Exception
     * @auth:wbj
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

}
