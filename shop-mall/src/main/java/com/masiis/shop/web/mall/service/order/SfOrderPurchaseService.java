package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.service.product.SkuService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.mall.service.user.UserAddressService;
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
    private SfOrderItemService sfOrderItemService;
    @Resource
    private SfOrderItemDistributionService orderItemDisService;
    @Resource
    private SfOrderConsigneeService ordConService;

    private static BigDecimal orderSumDisAmount;//一条订单的总的分润
    private static Map<Integer,BigDecimal> skuDisMap = null; //一条订单中每款产品的分润信息
    private static Map<Integer, List<SfOrderItemDistribution>> ordItemDisMap = null; //一款产品的店铺上级的分润信息

    private static BigDecimal skuTotalPrice = null;
    private static Integer totalQuantity = null;

    /**
     * 获得确认订单界面，地址信息和商品信息
     *
     * 一个小铺对应一个购物车
     *
     * @author hanzengzhi
     * @date 2016/4/8 20:55
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public Map<String,Object> getConfirmOrderInfo(Long userId,Long selectedAddressId,Long sfShopId){
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        try{
            //获得用户地址地址信息
            ComUserAddress comUserAddress = getUserAddressByUserId(userId,selectedAddressId);
            map.put("comUserAddress",comUserAddress);
            //获得购物车中的商品信息
            List<SfShopCart> shopCarts = getShopCartInfoByUserIdAndShopId(userId,sfShopId,1);
            map.put("shopCarts",shopCarts);
            //获得商品的详情信息
            List<SfShopCartSkuDetail> shopCartSkuDetails = getShopCartSkuBySkuId(shopCarts);
            map.put("shopCartSkuDetails",shopCartSkuDetails);
            //获得购物车中商品的总价格和数量
            getShopCartSkuTotalPrice(shopCartSkuDetails);
            map.put("skuTotalPrice",skuTotalPrice);
            map.put("totalQuantity",totalQuantity);
            //获得运费
            BigDecimal skuTotalShipAmount = getShopCartSkuTotalShipAmount(shopCarts);
            map.put("skuTotalShipAmount",skuTotalShipAmount);
            map.put("totalPrice",skuTotalPrice.add(skuTotalShipAmount));
        }catch (Exception e){
            throw new BusinessException(e);
        }
        return map;
    }

    /**
     * 获得用户地址
     * @author hanzengzhi
     * @date 2016/4/8 21:00
     */
    private ComUserAddress getUserAddressByUserId(Long userId,Long selectedAddressId){
        return userAddressService.getOrderAddress(selectedAddressId, userId);
    }
    /**
     * 获得用户购物车中的信息
     * @author hanzengzhi
     * @date 2016/4/9 10:52
     */
    private List<SfShopCart> getShopCartInfoByUserIdAndShopId(Long userId,Long sfShopId,Integer isCheck){
        return sfShopCartService.getShopCartInfoByUserIdAndShopId(userId,sfShopId,isCheck);
    }

    /**
     * 获得购物车中的商品的详情信息
     * @author hanzengzhi
     * @date 2016/4/9 12:55
     */
    private List<SfShopCartSkuDetail> getShopCartSkuBySkuId(List<SfShopCart> sfShopCarts){
        List<SfShopCartSkuDetail> sfShopCartSkuDetails = new LinkedList<SfShopCartSkuDetail>();
        for (SfShopCart sfShopCart: sfShopCarts){
            SfShopCartSkuDetail sfShopCartSkuDetail = new SfShopCartSkuDetail();
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
     * @author hanzengzhi
     * @date 2016/4/9 10:57
     */
    private void getShopCartSkuTotalPrice(List<SfShopCartSkuDetail> sfShopCartSkuDetails ){
        totalQuantity = new Integer(0);
        skuTotalPrice = new BigDecimal(0);
        for(SfShopCartSkuDetail sfShopCartSkuDetail : sfShopCartSkuDetails){
                totalQuantity += sfShopCartSkuDetail.getQuantity();
                skuTotalPrice = skuTotalPrice.add(sfShopCartSkuDetail.getSkuSumPrice());
        }
    }
    /**
     * 获得购车商品的总运费
     * @author hanzengzhi
     * @date 2016/4/9 12:00
     */
    private BigDecimal getShopCartSkuTotalShipAmount(List<SfShopCart> sfShopCarts){
        BigDecimal skuTotalShipAmount = new BigDecimal(0);
        List<Long> sfShopIdList = new LinkedList<Long>();
        for(SfShopCart sfShopCart : sfShopCarts){
            SfShop sfShop = sfShopService.getSfShopById(sfShopCart.getSfShopId());
            if (sfShop!= null){
                if (!sfShopIdList.contains(sfShopCart.getSfShopId())){
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
     * @author hanzengzhi
     * @date 2016/4/9 12:23
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public Map<String,Object> submitOrder(Long userId,Long selectedAddressId,Long shopId,String message){
        log.info("service生成订单---start");
        Map<String,Object> map = null;
        try{
            map = getConfirmOrderInfo(userId,selectedAddressId,shopId);
            ComUserAddress comUserAddress = (ComUserAddress)map.get("comUserAddress");
            List<SfShopCartSkuDetail> sfShopCartSkuDetails  = (List<SfShopCartSkuDetail> )map.get("shopCartSkuDetails");
            BigDecimal skuTotalPrice  = (BigDecimal )map.get("skuTotalPrice");
            BigDecimal skuTotalShipAmount = (BigDecimal )map.get("skuTotalShipAmount");
            //获得每款商品的分润信息
            log.info("获得分润信息---start");
            for (SfShopCartSkuDetail sfShopCartSkuDetail: sfShopCartSkuDetails){
                getDisDetail(sfShopCartSkuDetail.getSfShopUserId(),sfShopCartSkuDetail.getComSku().getId(),sfShopCartSkuDetail.getSkuSumPrice());
            }
            log.info("获得分润信息---end");
            //插入订单表
            SfOrder sfOrder = generateSfOrder(userId,sfShopCartSkuDetails,message,skuTotalPrice,skuTotalShipAmount);
            int i = sfOrderService.insert(sfOrder);
            if (i == 1){
                map.put("orderCode",sfOrder.getOrderCode());
                map.put("orderId",sfOrder.getId());
                StringBuffer sb = new StringBuffer("(");
                for (SfShopCartSkuDetail sfShopCartSkuDetail: sfShopCartSkuDetails){
                    //插入订单子表
                    //sb.append();
                    SfOrderItem sfOrderItem = generateSfOrderItem(sfOrder.getId(),sfShopCartSkuDetail);
                    int ii = sfOrderItemService.insert(sfOrderItem);
                    if (ii ==1){
                        //插入子订单分润表
                        List<SfOrderItemDistribution> itemDisList = ordItemDisMap.get(sfShopCartSkuDetail.getComSku().getId());
                        if (itemDisList != null){
                            for (SfOrderItemDistribution  orderItemDis :itemDisList){
                                orderItemDis = generateSfOrderItemDistribution(sfOrder.getId(),sfOrderItem.getId(),orderItemDis);
                                int iii = orderItemDisService.insert(orderItemDis);
                                if (iii != 1){
                                    throw new BusinessException("插入子订单分润表失败");
                                }
                            }
                        }else{
                            throw new BusinessException("根据sku获得订单子表分润信息为null");
                        }
                    }else{
                        throw new BusinessException("插入子订单失败");
                    }
                }
                //批量删除购物车中相应的商品信息
            }else{
                throw new BusinessException("插入订单失败");
            }
            //插入地址
            SfOrderConsignee sfOrderConsignee = generateSfOrderConsigness(sfOrder.getId(),comUserAddress);
            int iiii = ordConService.insert(sfOrderConsignee);
            if (iiii != 1){
                throw new BusinessException("插入订单地址失败");
            }
        }catch (Exception e){
            throw new BusinessException(e);
        }
        log.info("service生成订单---end");
        return map;
    }
    /**
     * 获得订单中一款商品的分润总额
     * @author hanzengzhi
     * @date 2016/4/9 17:44
     */
    private void getDisDetail(Long userId,Integer skuId,BigDecimal skuTotalPrice){
        if (skuDisMap == null){
            skuDisMap = new LinkedHashMap<Integer, BigDecimal>();
        }
        if (orderSumDisAmount == null){
            orderSumDisAmount = new BigDecimal(0);
        }
        if (ordItemDisMap == null||ordItemDisMap.size()==0){
            ordItemDisMap = new LinkedHashMap<Integer, List<SfOrderItemDistribution>>();
        }
        List<SfSkuDistribution> sfSkuDistribution =  sfSkuDistributionService.getSfSkuDistributionBySkuIdAndSortAsc(skuId);
        List<SfUserRelation> sfUserRelations = getSfUserRelation(userId,null);
        if (sfUserRelations!=null){
            log.info("获得店铺--id为"+userId+"---的上级关系共有---"+sfUserRelations.size());
            for (int i = 0; i < sfUserRelations.size(); i++){
                /*一条订单的总的分润*/
                orderSumDisAmount.add(skuTotalPrice.multiply(sfSkuDistribution.get(i).getDiscount()));
                /*获得一款商品的店铺上级总的分润*/
                BigDecimal skuDis = skuDisMap.get(skuId);
                if (skuDis==null){
                    skuDis = skuTotalPrice.multiply(sfSkuDistribution.get(i).getDiscount());
                }else{
                    skuDis = skuDis.add(skuTotalPrice.multiply(sfSkuDistribution.get(i).getDiscount()));
                }
                skuDisMap.put(skuId,skuDis);
                /*获得一款商品对应店铺上级分润信息*/
                List<SfOrderItemDistribution> orderItemDisList = (List<SfOrderItemDistribution>)ordItemDisMap.get(skuId);
                if (orderItemDisList == null|| orderItemDisList.size() == 0){
                    orderItemDisList = new LinkedList<SfOrderItemDistribution>();
                }
                orderItemDisList.add(generateSfOrderItemDistribution(sfUserRelations.get(i).getUserId(), sfSkuDistribution.get(i).getId(),skuTotalPrice.multiply(sfSkuDistribution.get(i).getDiscount())));
                ordItemDisMap.put(skuId,orderItemDisList);
            }
        }else{
            log.info("获得店铺--id为"+userId+"---的上级关系出错为---"+null);
            throw new BusinessException("获得店铺--id为"+userId+"---的上级关系出错为---"+null);
        }
    }
    /**
     * 生成订单数据
     * @author hanzengzhi
     * @date 2016/4/9 14:03
     */
    private SfOrder generateSfOrder(Long userId,
                                    List<SfShopCartSkuDetail> sfShopCartSkuDetails, String message,
                                    BigDecimal skuTotalPrice, BigDecimal skuTotalShipAmount){
        SfShopCartSkuDetail sfShopCartSkuDetail = null;
        SfOrder  sfOrder = new SfOrder();
        sfOrder.setUserId(userId);
        if (sfShopCartSkuDetails!=null&&sfShopCartSkuDetails.size()>0){
            sfShopCartSkuDetail = sfShopCartSkuDetails.get(0);
            sfOrder.setShopId(sfShopCartSkuDetail.getSfShopId());
            sfOrder.setShopUserId(sfShopCartSkuDetail.getSfShopUserId());
            sfOrder.setShopId(sfShopCartSkuDetail.getSfShopId());
            sfOrder.setShopUserId(sfShopCartSkuDetail.getSfShopUserId());
        }
        sfOrder.setCreateTime(new Date());
        sfOrder.setCreateMan(userId);
        sfOrder.setOrderCode(OrderMakeUtils.makeOrder("S"));
        sfOrder.setOrderAmount(skuTotalPrice.add(skuTotalShipAmount));//订单费用
        sfOrder.setUserMessage(message);
        sfOrder.setCreateTime(new Date());
        sfOrder.setModifyTime(new Date());
        sfOrder.setProductAmount(skuTotalPrice);//商品总费用
        sfOrder.setShipAmount(skuTotalShipAmount);//运费
        sfOrder.setShipType(0);
        sfOrder.setShipStatus(0);
        sfOrder.setPayAmount(skuTotalPrice.add(skuTotalShipAmount));//支付金额
        sfOrder.setPayStatus(0);
        sfOrder.setDistributionAmount(orderSumDisAmount);
        sfOrder.setReceivableAmount(skuTotalShipAmount.add(skuTotalShipAmount));
        sfOrder.setSendType(0);
        sfOrder.setOrderType(0);
        sfOrder.setOrderStatus(0);
        sfOrder.setIsCounting(0);
        sfOrder.setIsShip(0);
        sfOrder.setIsReplace(0);
        sfOrder.setIsReceipt(0);
        return sfOrder;
    }
    /**
     * 生成订单日志数据
     * @author hanzengzhi
     * @date 2016/4/9 14:39
     */
    private SfOrderOperationLog generateSfOrderOperationLog(SfOrderOperationLog sfOrderOperationLog,Long sfOrderId,int sfOrderStatus){
        if (sfOrderOperationLog == null){
            sfOrderOperationLog = new SfOrderOperationLog();
            sfOrderOperationLog.setSfOrderId(sfOrderId);
            sfOrderOperationLog.setRemark("创建订单");
        }else{
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
     * @author hanzengzhi
     * @date 2016/4/9 14:24
     */
    private SfOrderItem generateSfOrderItem(Long sfOrderId,SfShopCartSkuDetail sfShopCartSkuDetail){
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
     * 生成订单地址数据
     * @author hanzengzhi
     * @date 2016/4/9 14:31
     */
    private SfOrderConsignee generateSfOrderConsigness(Long sfOrderId,ComUserAddress comUserAddress ){
        SfOrderConsignee  sfOrderConsignee = new SfOrderConsignee();
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
     * @author hanzengzhi
     * @date 2016/4/9 18:10
     */
    private SfOrderItemDistribution generateSfOrderItemDistribution(Long userId,int skuDisId,BigDecimal disAmount){
        SfOrderItemDistribution orderItemDis = new SfOrderItemDistribution();
        orderItemDis.setUserId(userId);
        orderItemDis.setSfSkuDistributionId(skuDisId);
        orderItemDis.setDistributionAmount(disAmount);
        return orderItemDis;
    }

    /**
     * 生成订单订单商品分润表数据
     * @author hanzengzhi
     * @date 2016/4/9 14:43
     */
    private SfOrderItemDistribution generateSfOrderItemDistribution(Long sfOrderId,Long sfOrderItemId,SfOrderItemDistribution  orderItemDis ){
        orderItemDis.setSfOrderId(sfOrderId);
        orderItemDis.setSfOrderItemId(sfOrderItemId);
        orderItemDis.setIsCounting(0);
        orderItemDis.setCreateTime(new Date());
        return orderItemDis;
    }



    /**
     * 获得当前用户的分销关系
     * 只找当前用户的上面的三个人。
     * @author hanzengzhi
     * @date 2016/4/9 15:56
     */
    private List<SfUserRelation> getSfUserRelation(Long userId,List<SfUserRelation> sfUserRelationList ){
        if (sfUserRelationList==null||sfUserRelationList.size()==0){
            sfUserRelationList = new LinkedList<SfUserRelation>();
        }
        SfUserRelation sfUserRelation =   sfUserRelationService.getSfUserRelationByUserId(userId);
        if (sfUserRelation!=null && sfUserRelation.getUserPid()!=null&&sfUserRelationList.size()<3){
            sfUserRelationList.add(sfUserRelation);
            getSfUserRelation(sfUserRelation.getUserId(),sfUserRelationList);
        }
        return sfUserRelationList;
    }

}
