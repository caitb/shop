package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.service.product.SkuService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.mall.service.user.UserAddressService;
import com.masiis.shop.web.mall.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by hzz on 2016/4/8.
 */
@Service
public class SfOrderPurchaseService {


    private Logger log = Logger.getLogger(this.getClass());


    @Resource
    private UserAddressService userAddressService;
    @Resource
    private SfShopCartService sfShopCartService;
    @Resource
    private SkuService skuService;
    @Resource
    private SfShopService sfShopService;
    @Resource
    private SfSkuDistributionService sfSkuDistributionService;
    @Resource
    private SfUserRelationService sfUserRelationService;
    @Resource
    private SfOrderService sfOrderService;
    @Resource
    private SfOrderOperationLogService ordOperLogService;
    @Resource
    private SfOrderItemService sfOrderItemService;
    @Resource
    private SfOrderItemDistributionService orderItemDisService;
    @Resource
    private SfOrderConsigneeService ordConService;
    @Resource
    private UserService userService;

    private static BigDecimal orderSumDisAmount;//一条订单的总的分润
    private static Map<Integer, BigDecimal> skuDisMap = null; //一条订单中每款产品的分润信息
    private static Map<Integer, List<SfOrderItemDistribution>> ordItemDisMap = null; //一款产品的购买上级的分润信息

    private static BigDecimal skuTotalPrice = null;
    private static Integer totalQuantity = null;

    /**
     * 获得确认订单界面，地址信息和商品信息
     * <p/>
     * 一个小铺对应一个购物车
     *
     * @author hanzengzhi
     * @date 2016/4/8 20:55
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Map<String, Object> getConfirmOrderInfo(Long userId, Long selectedAddressId, Long sfShopId) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            //获得用户地址地址信息
            ComUserAddress comUserAddress = getUserAddressByUserId(userId, selectedAddressId);
            map.put("comUserAddress", comUserAddress);
            //获得购物车中的商品信息
            List<SfShopCart> shopCarts = getShopCartInfoByUserIdAndShopId(userId, sfShopId, 1);
            map.put("shopCarts", shopCarts);
            //获得商品的详情信息
            List<SfShopCartSkuDetail> shopCartSkuDetails = getShopCartSkuBySkuId(shopCarts);
            map.put("shopCartSkuDetails", shopCartSkuDetails);
            //获得购物车中商品的总价格和数量
            getShopCartSkuTotalPrice(shopCartSkuDetails);
            map.put("skuTotalPrice", skuTotalPrice);
            map.put("totalQuantity", totalQuantity);
            //获得运费
            BigDecimal skuTotalShipAmount = getShopCartSkuTotalShipAmount(shopCarts);
            map.put("skuTotalShipAmount", skuTotalShipAmount);
            map.put("totalPrice", skuTotalPrice.add(skuTotalShipAmount));
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return map;
    }

    /**
     * 获得用户地址
     *
     * @author hanzengzhi
     * @date 2016/4/8 21:00
     */
    private ComUserAddress getUserAddressByUserId(Long userId, Long selectedAddressId) {
        return userAddressService.getOrderAddress(selectedAddressId, userId);
    }

    /**
     * 获得用户购物车中的信息
     *
     * @author hanzengzhi
     * @date 2016/4/9 10:52
     */
    private List<SfShopCart> getShopCartInfoByUserIdAndShopId(Long userId, Long sfShopId, Integer isCheck) {
        return sfShopCartService.getShopCartInfoByUserIdAndShopId(userId, sfShopId, isCheck);
    }

    /**
     * 获得购物车中的商品的详情信息
     *
     * @author hanzengzhi
     * @date 2016/4/9 12:55
     */
    private List<SfShopCartSkuDetail> getShopCartSkuBySkuId(List<SfShopCart> sfShopCarts) {
        List<SfShopCartSkuDetail> sfShopCartSkuDetails = new LinkedList<SfShopCartSkuDetail>();
        for (SfShopCart sfShopCart : sfShopCarts) {
            SfShopCartSkuDetail sfShopCartSkuDetail = new SfShopCartSkuDetail();
            sfShopCartSkuDetail.setShopCartId(sfShopCart.getId());
            ComSku comSku = skuService.getComSkuBySkuId(sfShopCart.getSkuId());
            sfShopCartSkuDetail.setSfShopId(sfShopCart.getSfShopId());
            sfShopCartSkuDetail.setSfShopUserId(sfShopCart.getSfShopUserId());
            sfShopCartSkuDetail.setComSku(comSku);
            sfShopCartSkuDetail.setQuantity(sfShopCart.getQuantity());
            sfShopCartSkuDetail.setSkuSumPrice(comSku.getPriceRetail().multiply(new BigDecimal(sfShopCart.getQuantity())));
            sfShopCartSkuDetails.add(sfShopCartSkuDetail);
        }
        return sfShopCartSkuDetails;
    }

    /**
     * 获得购物车中所有商品的总价格和数量
     *
     * @author hanzengzhi
     * @date 2016/4/9 10:57
     */
    private void getShopCartSkuTotalPrice(List<SfShopCartSkuDetail> sfShopCartSkuDetails) {
        totalQuantity = new Integer(0);
        skuTotalPrice = new BigDecimal(0);
        for (SfShopCartSkuDetail sfShopCartSkuDetail : sfShopCartSkuDetails) {
            totalQuantity += sfShopCartSkuDetail.getQuantity();
            skuTotalPrice = skuTotalPrice.add(sfShopCartSkuDetail.getSkuSumPrice());
        }
    }

    /**
     * 获得购车商品的总运费
     *
     * @author hanzengzhi
     * @date 2016/4/9 12:00
     */
    private BigDecimal getShopCartSkuTotalShipAmount(List<SfShopCart> sfShopCarts) {
        BigDecimal skuTotalShipAmount = new BigDecimal(0);
        List<Long> sfShopIdList = new LinkedList<Long>();
        for (SfShopCart sfShopCart : sfShopCarts) {
            SfShop sfShop = sfShopService.getSfShopById(sfShopCart.getSfShopId());
            if (sfShop != null) {
                if (!sfShopIdList.contains(sfShopCart.getSfShopId())) {
                    sfShopIdList.add(sfShopCart.getSfShopId());
                    //商品的运费
                    skuTotalShipAmount = skuTotalShipAmount.add(sfShop.getShipAmount());
                }
            }
        }
        return skuTotalShipAmount;
    }

    /**
     * 提交订单
     *
     * @author hanzengzhi
     * @date 2016/4/9 12:23
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Long submitOrder(Long purchaseUserId, Long selectedAddressId, Long shopId, String message) {
        log.info("service生成订单---start");
        Map<String, Object> map = null;
        SfOrder sfOrder = null;
        try {
            map = getConfirmOrderInfo(purchaseUserId, selectedAddressId, shopId);
            ComUserAddress comUserAddress = (ComUserAddress) map.get("comUserAddress");
            List<SfShopCartSkuDetail> sfShopCartSkuDetails = (List<SfShopCartSkuDetail>) map.get("shopCartSkuDetails");
            BigDecimal skuTotalPrice = (BigDecimal) map.get("skuTotalPrice");
            BigDecimal skuTotalShipAmount = (BigDecimal) map.get("skuTotalShipAmount");
            if (sfShopCartSkuDetails == null || sfShopCartSkuDetails.size() == 0){
                return -2L; //购物车清空了，微信上返回获取不到购物车数据
            }
            //判断商品是否有足够的库存
            if (isEnoughStock(sfShopCartSkuDetails)) {
                //获得每款商品的分润信息
                log.info("获得分润信息---start");
                for (SfShopCartSkuDetail sfShopCartSkuDetail : sfShopCartSkuDetails) {
                    getDisDetail(purchaseUserId, sfShopCartSkuDetail.getSfShopUserId(), sfShopCartSkuDetail.getComSku().getId(), sfShopCartSkuDetail.getSkuSumPrice());
                }
                log.info("获得分润信息---end");
                //插入订单表
                log.info("插入订单---start");
                sfOrder = generateSfOrder(purchaseUserId, sfShopCartSkuDetails, message, skuTotalPrice, skuTotalShipAmount);
                int i = sfOrderService.insert(sfOrder);
                if (i == 1) {
                    log.info("插入订单成功---end");
                    //插入订单操作操作日志
                    log.info("插入订单操作日志---start");
                    SfOrderOperationLog ordOperLog = generateSfOrderOperationLog(null, sfOrder.getId(), 0);
                    int _i = ordOperLogService.insert(ordOperLog);
                    if (_i == 1) {
                        log.info("插入订单操作日志成功---end");
                    } else {
                        log.info("插入订单操作日志失败");
                        throw new BusinessException("插入订单操作日志失败");
                    }
                    StringBuffer shopCartIdSB = new StringBuffer();//所有商品的字符串
                    if (sfShopCartSkuDetails != null && sfShopCartSkuDetails.size() > 0) {
                        for (SfShopCartSkuDetail sfShopCartSkuDetail : sfShopCartSkuDetails) {
                            //插入订单子表
                            shopCartIdSB.append(sfShopCartSkuDetail.getShopCartId()).append(",");
                            log.info("插入订单子表---start");
                            SfOrderItem sfOrderItem = generateSfOrderItem(sfOrder.getId(), sfShopCartSkuDetail);
                            int ii = sfOrderItemService.insert(sfOrderItem);
                            if (ii == 1) {
                                log.info("插入订单子表成功---end");
                                //插入子订单分润表
                                List<SfOrderItemDistribution> itemDisList = ordItemDisMap.get(sfShopCartSkuDetail.getComSku().getId());
                                if (itemDisList != null && itemDisList.size() != 0) {
                                    for (SfOrderItemDistribution orderItemDis : itemDisList) {
                                        log.info("插入子订单分润表--start");
                                        orderItemDis = generateSfOrderItemDistribution(sfOrder.getId(), sfOrderItem.getId(), orderItemDis);
                                        log.info("插入的数据----"+orderItemDis.toString());
                                        int iii = orderItemDisService.insert(orderItemDis);
                                        if (iii != 1) {
                                            log.info("插入子订单分润表失败");
                                            throw new BusinessException("插入子订单分润表失败");
                                        } else {
                                            log.info("插入子订单分润表成功--end");
                                        }
                                    }
                                }
                            } else {
                                log.info("插入子订单失败---end");
                                throw new BusinessException("插入子订单失败");
                            }
                        }
                        //批量删除购物车中相应的商品信息
                        String shopCartIds = shopCartIdSB.toString();
                        shopCartIds = shopCartIds.substring(0, shopCartIds.lastIndexOf(","));
                        deleteShopCartById(shopCartIds);
                    } else {
                        log.info("获得购物车中的商品详情信息为null");
                        throw new BusinessException("获得购物车中的商品详情信息为null");
                    }
                } else {
                    log.info("插入订单失败---end");
                    throw new BusinessException("插入订单失败");
                }
                //插入地址
                SfOrderConsignee sfOrderConsignee = generateSfOrderConsigness(sfOrder.getId(), comUserAddress);
                int iiii = ordConService.insert(sfOrderConsignee);
                if (iiii != 1) {
                    log.info("插入订单失败---end");
                    throw new BusinessException("插入订单地址失败");
                } else {
                    log.info("插入订单成功---end");
                }
            } else {
                return -1L;//库存不足
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        log.info("service生成订单---end");
        if (sfOrder != null) {
            return sfOrder.getId();
        } else {
            return null;
        }
    }
    /**
     * 判断商品是否有足够的库存
     * @author hanzengzhi
     * @date 2016/4/14 15:18
     */
    private Boolean isEnoughStock(List<SfShopCartSkuDetail> sfShopCartSkuDetails) {
        log.info("判断商品是否有足够的库存----start");
        Boolean bl = true;
        if (sfShopCartSkuDetails == null || sfShopCartSkuDetails.size() == 0) {
           return false;
        }
        for (SfShopCartSkuDetail cartSkuDetail : sfShopCartSkuDetails) {
            int n = skuService.checkSkuStock(cartSkuDetail.getComSku().getId(), cartSkuDetail.getQuantity(), cartSkuDetail.getSfShopId());
            if (n < 0) {
                log.info("商品id为---" + cartSkuDetail.getComSku().getId() + "购买数量为---" + cartSkuDetail.getQuantity() + "小铺id为" + cartSkuDetail.getSfShopId() + "库存不足");
                bl = false;
                break;
            }
        }
        log.info("判断商品是否有足够的库存----end");
        return bl;
    }

    /**
     * 获得订单中一款商品的分润总额
     *
     * @author hanzengzhi
     * @date 2016/4/9 17:44
     */
    private void getDisDetail(Long purchaseUserId, Long shopUserId, Integer skuId, BigDecimal skuTotalPrice) {
        if (skuDisMap == null) {
            skuDisMap = new LinkedHashMap<Integer, BigDecimal>();
        }
        if (orderSumDisAmount == null) {
            orderSumDisAmount = new BigDecimal(0);
        }
        if (ordItemDisMap == null || ordItemDisMap.size() == 0) {
            ordItemDisMap = new LinkedHashMap<Integer, List<SfOrderItemDistribution>>();
        }
        List<SfSkuDistribution> sfSkuDistribution = sfSkuDistributionService.getSfSkuDistributionBySkuIdAndSortAsc(skuId);
        /* 获得当前用户的分销关系 */
        /* 获得当前用户的分销关系规则：父级在list第一位，父父级级在第二位 以此类推(这种排序和skuDis相对应起来) */
        List<SfUserRelation> sfUserRelations = getSfUserRelation(purchaseUserId, null, null);
        if (sfUserRelations != null && sfUserRelations.size() != 0 && sfSkuDistribution != null && sfSkuDistribution.size() != 0) {
            log.info("获得购买人--id为" + purchaseUserId + "---的上级关系共有---" + sfUserRelations.size());
            /* 此处以realtions为维度，而不是skuDistrion为维度。因为只有关系存在才会商品的分润 但是商品的分润不能为0 */
            for (int i = 0; i < sfUserRelations.size(); i++) {
                /*一条订单的总的分润*/
                orderSumDisAmount = orderSumDisAmount.add(skuTotalPrice.multiply(sfSkuDistribution.get(i).getDiscount()));
                /*获得一款商品的购买人上级总的分润*/
                BigDecimal skuDis = skuDisMap.get(skuId);
                if (skuDis == null) {
                    skuDis = skuTotalPrice.multiply(sfSkuDistribution.get(i).getDiscount());
                } else {
                    skuDis = skuDis.add(skuTotalPrice.multiply(sfSkuDistribution.get(i).getDiscount()));
                }
                skuDisMap.put(skuId, skuDis);
                /*获得一款商品对应购买上级分润信息*/
                List<SfOrderItemDistribution> orderItemDisList = (List<SfOrderItemDistribution>) ordItemDisMap.get(skuId);
                if (orderItemDisList == null || orderItemDisList.size() == 0) {
                    orderItemDisList = new LinkedList<SfOrderItemDistribution>();
                }
                //
                log.info("向订单orderItem分润map放数据-----start");
                if (!shopUserId.equals(sfUserRelations.get(0).getUserId())) {
                    log.info("购买不是自己店铺的进行分润");
                    SfOrderItemDistribution orderItemDistribution = generateSfOrderItemDistribution(sfUserRelations.get(i).getUserId(), sfSkuDistribution.get(i).getId(), skuTotalPrice.multiply(sfSkuDistribution.get(i).getDiscount()));
                    log.info("向map放的key为----"+skuId+"-----value值为----"+orderItemDistribution.toString());
                    orderItemDisList.add(orderItemDistribution);
                }
                ordItemDisMap.put(skuId, orderItemDisList);


                log.info("向map放完后，遍历map查看map有的数据----start");
                for (Map.Entry<Integer, List<SfOrderItemDistribution>> entry : ordItemDisMap.entrySet()) {
                    log.info("map---key值为----"+entry.getKey());
                    for(SfOrderItemDistribution oid :entry.getValue()){
                        log.info("map---value值为---"+oid.toString());
                    }
                    log.info("-------------------------");
                }
                log.info("向map放完后，遍历map查看map有的数据----end");

                log.info("向订单orderItem分润map放数据-----end");
            }
        }
    }

    /**
     * 生成订单数据
     *
     * @author hanzengzhi
     * @date 2016/4/9 14:03
     */
    private SfOrder generateSfOrder(Long purchaseUserId,
                                    List<SfShopCartSkuDetail> sfShopCartSkuDetails, String message,
                                    BigDecimal skuTotalPrice, BigDecimal skuTotalShipAmount) {
        SfShopCartSkuDetail sfShopCartSkuDetail = null;
        SfOrder sfOrder = new SfOrder();
        sfOrder.setUserId(purchaseUserId);
        if (sfShopCartSkuDetails != null && sfShopCartSkuDetails.size() > 0) {
            sfShopCartSkuDetail = sfShopCartSkuDetails.get(0);
            sfOrder.setShopId(sfShopCartSkuDetail.getSfShopId());
            sfOrder.setShopUserId(sfShopCartSkuDetail.getSfShopUserId());
            sfOrder.setShopId(sfShopCartSkuDetail.getSfShopId());
            sfOrder.setShopUserId(sfShopCartSkuDetail.getSfShopUserId());
            sfOrder.setSendType(getSendType(sfShopCartSkuDetail.getSfShopUserId()));
        }
        sfOrder.setCreateTime(new Date());
        sfOrder.setCreateMan(purchaseUserId);
        sfOrder.setOrderCode(OrderMakeUtils.makeOrder("S"));
        sfOrder.setOrderAmount(skuTotalPrice.add(skuTotalShipAmount));//订单费用
        sfOrder.setUserMessage(message);
        sfOrder.setCreateTime(new Date());
        sfOrder.setModifyTime(new Date());
        sfOrder.setProductAmount(skuTotalPrice);//商品总费用
        sfOrder.setShipAmount(skuTotalShipAmount);//运费
        sfOrder.setShipType(0);
        sfOrder.setShipStatus(0);
        sfOrder.setPayAmount(new BigDecimal(0));//支付金额
        sfOrder.setPayStatus(0);
        sfOrder.setDistributionAmount(orderSumDisAmount);
        sfOrder.setReceivableAmount(skuTotalPrice.add(skuTotalShipAmount));//应收费用(微信调用支付时需要)
        sfOrder.setOrderType(0);
        sfOrder.setOrderStatus(0);
        sfOrder.setIsCounting(0);
        sfOrder.setIsShip(0);
        sfOrder.setIsReplace(0);
        sfOrder.setIsReceipt(0);
        return sfOrder;
    }

    /**
     * 获得发货方式
     * @author hanzengzhi
     * @date 2016/4/14 16:12
     */
    private Integer getSendType(Long shopUserId){
        ComUser comUser = userService.getUserById(shopUserId);
        if (comUser == null){
            log.info("支付成功获得发货方式comUser为null,shopUserId为"+shopUserId);
            throw new BusinessException("支付成功获得发货方式comUser为null");
        }
        return comUser.getSendType();
    }


    /**
     * 生成订单日志数据
     *
     * @author hanzengzhi
     * @date 2016/4/9 14:39
     */
    private SfOrderOperationLog generateSfOrderOperationLog(SfOrderOperationLog sfOrderOperationLog, Long sfOrderId, int sfOrderStatus) {
        if (sfOrderOperationLog == null) {
            sfOrderOperationLog = new SfOrderOperationLog();
            sfOrderOperationLog.setSfOrderId(sfOrderId);
            sfOrderOperationLog.setRemark("创建订单");
        } else {
            StringBuffer sb = new StringBuffer("将订单的状态由");
            sb.append(sfOrderOperationLog.getSfOrderStatus());
            sb.append("改变为");
            sb.append(sfOrderStatus);
            sfOrderOperationLog.setRemark(sb.toString());
        }
        sfOrderOperationLog.setSfOrderStatus(sfOrderStatus);
        return sfOrderOperationLog;
    }

    /**
     * 生成订单商品子表数据
     *
     * @author hanzengzhi
     * @date 2016/4/9 14:24
     */
    private SfOrderItem generateSfOrderItem(Long sfOrderId, SfShopCartSkuDetail sfShopCartSkuDetail) {
        SfOrderItem sfOrderItem = new SfOrderItem();
        sfOrderItem.setCreateTime(new Date());
        sfOrderItem.setSfOrderId(sfOrderId);
        sfOrderItem.setSpuId(sfShopCartSkuDetail.getComSku().getSpuId());
        sfOrderItem.setSkuId(sfShopCartSkuDetail.getComSku().getId());
        sfOrderItem.setSkuName(sfShopCartSkuDetail.getComSku().getName());
        sfOrderItem.setQuantity(sfShopCartSkuDetail.getQuantity());
        sfOrderItem.setOriginalPrice(sfShopCartSkuDetail.getComSku().getPriceRetail());
        sfOrderItem.setUnitPrice(sfShopCartSkuDetail.getComSku().getPriceRetail());
        sfOrderItem.setDistributionAmount(skuDisMap.get(sfShopCartSkuDetail.getComSku().getId()));
        sfOrderItem.setTotalPrice(new BigDecimal(sfOrderItem.getQuantity()).multiply(sfOrderItem.getOriginalPrice()));
        sfOrderItem.setIsComment(0);
        sfOrderItem.setIsReturn(0);
        return sfOrderItem;
    }

    /**
     * 提交完订单后删除购物车中相应的信息
     *
     * @author hanzengzhi
     * @date 2016/4/11 17:03
     */
    private void deleteShopCartById(String ids) {
        sfShopCartService.deleteByIds(ids);
    }

    /**
     * 生成订单地址数据
     *
     * @author hanzengzhi
     * @date 2016/4/9 14:31
     */
    private SfOrderConsignee generateSfOrderConsigness(Long sfOrderId, ComUserAddress comUserAddress) {
        SfOrderConsignee sfOrderConsignee = new SfOrderConsignee();
        sfOrderConsignee.setCreateTime(new Date());
        sfOrderConsignee.setSfOrderId(sfOrderId);
        sfOrderConsignee.setUserId(comUserAddress.getUserId());
        sfOrderConsignee.setConsignee(comUserAddress.getName());
        sfOrderConsignee.setMobile(comUserAddress.getMobile());
        sfOrderConsignee.setProvinceId(comUserAddress.getProvinceId());
        sfOrderConsignee.setProvinceName(comUserAddress.getProvinceName());
        sfOrderConsignee.setCityName(comUserAddress.getCityName());
        sfOrderConsignee.setCityId(comUserAddress.getCityId());
        sfOrderConsignee.setRegionId(comUserAddress.getRegionId());
        sfOrderConsignee.setRegionName(comUserAddress.getRegionName());
        sfOrderConsignee.setAddress(comUserAddress.getAddress());
        sfOrderConsignee.setZip(comUserAddress.getZip());
        return sfOrderConsignee;
    }

    /**
     * 生成订单订单商品分润表数据
     *
     * @author hanzengzhi
     * @date 2016/4/9 18:10
     */
    private SfOrderItemDistribution generateSfOrderItemDistribution(Long userId, int skuDisId, BigDecimal disAmount) {
        SfOrderItemDistribution orderItemDis = new SfOrderItemDistribution();
        orderItemDis.setUserId(userId);
        orderItemDis.setSfSkuDistributionId(skuDisId);
        orderItemDis.setDistributionAmount(disAmount);
        return orderItemDis;
    }

    /**
     * 生成订单订单商品分润表数据
     *
     * @author hanzengzhi
     * @date 2016/4/9 14:43
     */
    private SfOrderItemDistribution generateSfOrderItemDistribution(Long sfOrderId, Long sfOrderItemId, SfOrderItemDistribution orderItemDis) {
        orderItemDis.setSfOrderId(sfOrderId);
        orderItemDis.setSfOrderItemId(sfOrderItemId);
        orderItemDis.setIsCounting(0);
        orderItemDis.setCreateTime(new Date());
        return orderItemDis;
    }


    /**
     * 获得当前用户的分销关系
     * 只找当前用户的上面的三个人。
     *
     * @author hanzengzhi
     * @date 2016/4/9 15:56
     */
    private List<SfUserRelation> getSfUserRelation(Long userId, Long userPid, List<SfUserRelation> sfUserRelationList) {
        if (sfUserRelationList == null || sfUserRelationList.size() == 0) {
            sfUserRelationList = new LinkedList<SfUserRelation>();
        }
        if (userPid == null) {
            SfUserRelation _userRelation = sfUserRelationService.getSfUserRelationByUserId(userId);
            userPid = _userRelation.getUserPid();
        }
        SfUserRelation sfUserRelation = sfUserRelationService.getSfUserRelationByUserId(userPid);
        if (sfUserRelation != null) {
            sfUserRelationList.add(sfUserRelation);
        }
        if (sfUserRelation != null && sfUserRelation.getUserPid() != null && sfUserRelationList.size() < 3) {
            getSfUserRelation(sfUserRelation.getUserId(), sfUserRelation.getUserPid(), sfUserRelationList);
        }
        log.info("关系----start");
        for (SfUserRelation sf :sfUserRelationList ){
            log.info("-------------start--------");
            log.info("用户id-----"+sf.getUserId());
            log.info("上一级用户id-----"+sf.getUserPid());
            log.info("-------------end--------");
        }
        log.info("关系----end");
        return sfUserRelationList;
    }

}
