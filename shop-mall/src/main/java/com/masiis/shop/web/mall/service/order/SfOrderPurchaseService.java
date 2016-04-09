package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.service.product.SkuService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.mall.service.user.UserAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by hzz on 2016/4/8.
 */
@Service
public class SfOrderPurchaseService {

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


    /**
     * 获得确认订单界面，地址信息和商品信息
     * @author hanzengzhi
     * @date 2016/4/8 20:55
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public Map<String,Object> getConfirmOrderInfo(Long userId,Long selectedAddressId,Long sfShopId){
        try{
            //获得用户地址地址信息
            ComUserAddress comUserAddress = getUserAddressByUserId(userId,selectedAddressId);
            //获得购物车中的商品信息
            List<SfShopCart> sfShopCarts = getShopCartInfoByUserIdAndShopId(userId,sfShopId);
            //获得商品的详情信息
            List<SfShopCartSkuDetail> sfShopCartSkuDetails = getShopCartSkuBySkuId(sfShopCarts);
            //获得购物车中商品的总价格
            BigDecimal skuTotalPrice = getShopCartSkuTotalPrice(sfShopCartSkuDetails);
            //获得运费
            BigDecimal skuTotalShipAmount = getShopCartSkuTotalShipAmount(sfShopCarts);
        }catch (Exception e){

        }
        return null;
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
    private List<SfShopCart> getShopCartInfoByUserIdAndShopId(Long userId,Long sfShopId){
        return sfShopCartService.getShopCartInfoByUserIdAndShopId(userId,sfShopId);
    }

    /**
     * 获得购物车中的商品的详情信息
     * @author hanzengzhi
     * @date 2016/4/9 12:55
     */
    private List<SfShopCartSkuDetail> getShopCartSkuBySkuId(List<SfShopCart> sfShopCarts){
        List<SfShopCartSkuDetail> sfShopCartSkuDetails = new LinkedList<SfShopCartSkuDetail>();
        List<ComSku> comSkuList = new LinkedList<ComSku>();
        for (SfShopCart sfShopCart: sfShopCarts){
            ComSku comSku = null;
            SfShopCartSkuDetail sfShopCartSkuDetail = new SfShopCartSkuDetail();
            comSku = skuService.getComSkuBySkuId(sfShopCart.getSkuId());
            sfShopCartSkuDetail.setComSku(comSku);
            sfShopCartSkuDetail.setQuantity(sfShopCart.getQuantity());
            sfShopCartSkuDetail.setSkuSumPrice(comSku.getPriceRetail().multiply(new BigDecimal(sfShopCart.getQuantity())));
            sfShopCartSkuDetails.add(sfShopCartSkuDetail);
        }
        return sfShopCartSkuDetails;
    }

    /**
     * 获得购物车中所有商品的总价格
     * @author hanzengzhi
     * @date 2016/4/9 10:57
     */
    private BigDecimal getShopCartSkuTotalPrice(List<SfShopCartSkuDetail> sfShopCartSkuDetails ){
        BigDecimal skuTotalPrice = new BigDecimal(0);
        for(SfShopCartSkuDetail sfShopCartSkuDetail : sfShopCartSkuDetails){
                skuTotalPrice.add(sfShopCartSkuDetail.getSkuSumPrice());
        }
        return skuTotalPrice;
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
                    //商品的价格乘以数量
                    skuTotalShipAmount.add(sfShop.getShipAmount());
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
    public void submitOrder(Long userId,Long selectedAddressId,Long sfShopId,String message){
        Map<String,Object> map  = getConfirmOrderInfo(userId,selectedAddressId,sfShopId);

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
        }
        sfOrder.setUserMessage(message);
        sfOrder.setCreateTime(new Date());
        sfOrder.setModifyTime(new Date());
        sfOrder.setProductAmount(skuTotalPrice);
        sfOrder.setShipAmount(skuTotalShipAmount);
        sfOrder.setReceivableAmount(skuTotalShipAmount.add(skuTotalShipAmount));
        sfOrder.setSendType(0);
        sfOrder.setOrderType(0);
        sfOrder.setOrderStatus(0);
        return null;
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
    private void generateSfOrderItem(Long sfOrderId,SfShopCartSkuDetail sfShopCartSkuDetail){
        SfOrderItem sfOrderItem = new SfOrderItem();
        sfOrderItem.setSfOrderId(sfOrderId);
        sfOrderItem.setSpuId(sfShopCartSkuDetail.getComSku().getSpuId());
        sfOrderItem.setSkuId(sfShopCartSkuDetail.getComSku().getId());
        sfOrderItem.setSkuName(sfShopCartSkuDetail.getComSku().getName());
        sfOrderItem.setQuantity(sfShopCartSkuDetail.getQuantity());
        sfOrderItem.setOriginalPrice(sfShopCartSkuDetail.getComSku().getPriceRetail());
        sfOrderItem.setUnitPrice(sfShopCartSkuDetail.getComSku().getPriceRetail());
        sfOrderItem.setTotalPrice(new BigDecimal(sfOrderItem.getQuantity()).multiply(sfOrderItem.getOriginalPrice()));
        sfOrderItem.setIsComment(0);
        sfOrderItem.setIsReturn(0);
    }
    /**
     * 生成订单地址数据
     * @author hanzengzhi
     * @date 2016/4/9 14:31
     */
    private void generateSfOrderConsigness(Long sfOrderId,ComUserAddress comUserAddress ){
        SfOrderConsignee  sfOrderConsignee = new SfOrderConsignee();
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
    }
    /**
     * 生成订单订单商品分润表数据
     * @author hanzengzhi
     * @date 2016/4/9 14:43
     */
    private SfOrderItemDistribution generateSfOrderItemDistribution(Long sfOrderId,Long sfOrderItemId){
        SfOrderItemDistribution sfOrderItemDistribution = new SfOrderItemDistribution();
        sfOrderItemDistribution.setSfOrderId(sfOrderId);
        sfOrderItemDistribution.setSfOrderItemId(sfOrderItemId);
        sfOrderItemDistribution.setIsCounting(0);
        return sfOrderItemDistribution;
    }
    private void getDistributionInfo(Integer skuId){
        List<SfSkuDistribution> sfSkuDistribution =  sfSkuDistributionService.getSfSkuDistributionBySkuId(skuId);
    }
    /**
     * 获得当前用户的分销关系
     * @author hanzengzhi
     * @date 2016/4/9 15:56
     */
    private List<SfUserRelation> getSfUserRelation(Long userId,List<SfUserRelation> sfUserRelationList ){
        if (sfUserRelationList==null||sfUserRelationList.size()==0){
            sfUserRelationList = new LinkedList<SfUserRelation>();
        }
        SfUserRelation sfUserRelation =   sfUserRelationService.getSfUserRelationByUserId(userId);
        if (sfUserRelation!=null && sfUserRelation.getUserPid()!=null){
            sfUserRelationList.add(sfUserRelation);
            getSfUserRelation(sfUserRelation.getUserId(),sfUserRelationList);
        }
        return sfUserRelationList;
    }

}
