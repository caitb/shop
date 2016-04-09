package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.dao.po.SfShopCart;
import com.masiis.shop.web.mall.service.product.SkuService;
import com.masiis.shop.web.mall.service.user.UserAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    private SkuService skuService;


    /**
     * 获得确认订单界面，地址信息和商品信息
     * @author hanzengzhi
     * @date 2016/4/8 20:55
     */
    public Map<String,Object> getConfirmOrderInfo(Long userId,Long selectedAddressId){
        //获得用户地址地址信息
        ComUserAddress comUserAddress = getUserAddressByUserId(userId,selectedAddressId);
        //获得购物车中的商品信息
        List<SfShopCart> sfShopCarts = getShopCartInfoByUserId(userId);
        //获得购物车中商品的总价格
        BigDecimal skuTotalPrice = getShopCartSkuTotalPrice(sfShopCarts);
        //获得运费
        return null;
    }

    /**
     * 获得
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
    private List<SfShopCart> getShopCartInfoByUserId(Long userId){
        return sfShopCartService.getShopCartInfoByUserId(userId);
    }

    /**
     * 获得购物车中商品的总价格
     * @author hanzengzhi
     * @date 2016/4/9 10:57
     */
    private BigDecimal getShopCartSkuTotalPrice(List<SfShopCart> sfShopCarts){
        BigDecimal skuTotalPrice = new BigDecimal(0);
        for(SfShopCart sfShopCart : sfShopCarts){
            ComSku comSku = skuService.getComSkuBySkuId(sfShopCart.getSkuId());
            if (comSku!= null){
                //商品的价格乘以数量
                skuTotalPrice.add(comSku.getPriceRetail().multiply(new BigDecimal(sfShopCart.getQuantity())));
            }
        }
        return skuTotalPrice;
    }
    /**
     * 获得购车商品的运费
     * @author hanzengzhi
     * @date 2016/4/9 12:00
     */
    private BigDecimal getShopCartSkuTotalShipAmount(List<SfShopCart> sfShopCarts){
        BigDecimal skuTotalPrice = new BigDecimal(0);
        for(SfShopCart sfShopCart : sfShopCarts){
            ComSku comSku = skuService.getComSkuBySkuId(sfShopCart.getSkuId());
            if (comSku!= null){
                //商品的价格乘以数量
                skuTotalPrice.add(comSku.getPriceRetail().multiply(new BigDecimal(sfShopCart.getQuantity())));
            }
        }
        return skuTotalPrice;
    }

}
