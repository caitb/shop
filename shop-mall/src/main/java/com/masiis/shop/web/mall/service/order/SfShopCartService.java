package com.masiis.shop.web.mall.service.order;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.dao.mall.shop.SfShopCartMapper;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.dao.po.SfShopCart;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by hzz on 2016/4/9.
 */
@Service
public class SfShopCartService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private SfShopCartMapper sfShopCartMapper;

    @Resource
    private SfShopSkuMapper sfShopSkuMapper;

    @Resource
    private SfShopMapper sfShopMapper;

    @Resource
    private ComSkuMapper comSkuMapper;

    /**
     * 获得用户购物车中的信息
     * @author hanzengzhi
     * @date 2016/4/9 10:38
     */
    public List<SfShopCart> getShopCartInfoByUserIdAndShopId(Long userId,Long shopId,Integer isCheck){
        return sfShopCartMapper.getShopCartInfoByUserIdAndShopId(userId,shopId,isCheck);
    }
    /**
     * 删除购物车中的信息
     * @author hanzengzhi
     * @date 2016/4/11 17:05
     */
    public int deleteShopCartById(Long id){
        return sfShopCartMapper.deleteByPrimaryKey(id);
    }

    /**
     * jjh
     * 添加商品到购物车
     */
    public void addProductToCart(Long shopId,Long userId,Integer skuId,Integer quantity)throws Exception{
        SfShopCart ShopCart = new SfShopCart();
        SfShopCart sfShopCart = sfShopCartMapper.getProductInfoByUserIdAndShipIdAndSkuId(userId,shopId,skuId);
        if(sfShopCart!=null){
            sfShopCartMapper.deleteByPrimaryKey(sfShopCart.getId());
            log.info("----删除当前商品----");
        }
        SfShop sfShop = sfShopMapper.selectByPrimaryKey(shopId);
        ComSku comSku = comSkuMapper.selectByPrimaryKey(skuId);
        ShopCart.setCreateTime(new Date());
        ShopCart.setSfShopId(shopId);
        ShopCart.setUserId(userId);
        ShopCart.setSkuId(skuId);
        ShopCart.setSpuId(comSku.getSpuId());
        ShopCart.setSfShopUserId(sfShop.getUserId());
        ShopCart.setQuantity(quantity);
        ShopCart.setIsCheck(1);
        ShopCart.setSort(0);
        sfShopCartMapper.insert(ShopCart);
        log.info("----加入cart 成功----");
    }

}
