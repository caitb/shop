package com.masiis.shop.web.mall.service.product;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopCartMapper;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.platform.product.*;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * SkuService
 *
 * @author ZhaoLiang
 * @date 2016/3/7
 */
@Service
@Transactional
public class SkuService {

    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSkuImageMapper comSkuImageMapper;
    @Resource
    private ComSkuExtensionMapper comSkuExtensionMapper;
    @Resource
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;

    @Resource
    private ComSpuMapper comSpuMapper;

    @Resource
    private SfShopSkuMapper sfShopSkuMapper;

    @Resource
    private SfShopCartMapper sfShopCartMapper;

    @Resource
    private SfShopMapper sfShopMapper;

    public ComSku getSkuById(Integer skuId) {
        return comSkuMapper.selectByPrimaryKey(skuId);
    }

    public ComSkuImage findComSkuImage(Integer skuId) {
        return comSkuImageMapper.selectDefaultImgBySkuId(skuId);
    }

    /**
     * 判断库存是否足够
     *
     * @author ZhaoLiang
     * @date 2016/4/1 16:19
     */
    public int checkSkuStock(Integer skuId, int quantity, Long pUserId) {
        int n;
        if (pUserId == 0) {
            PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
            n = pfSkuStock.getStock() - pfSkuStock.getFrozenStock();
        } else {
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pUserId, skuId);
            n = pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock();
        }
        return n - quantity;
    }

    /**
     * 根据skuId查找skuExtension
     *
     * @param skuId
     * @return
     */
    public ComSkuExtension findSkuExteBySkuId(Integer skuId) {
        return comSkuExtensionMapper.selectBySkuId(skuId);
    }

    /**
     * 根据skuId查找商品
     *
     * @author hanzengzhi
     * @date 2016/4/9 11:41
     */
    public ComSku getComSkuBySkuId(Integer skuId) {
        return comSkuMapper.findBySkuId(skuId);
    }


    /**
     * SkuImage Default 信息
     *
     * @param skuId
     * @return
     */
    public ComSkuImage findDefaultComSkuImage(Integer skuId) throws Exception {
        String productImgValue = PropertiesUtils.getStringValue("index_product_220_220_url");
        ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(skuId);
        if (comSkuImage != null) {
            comSkuImage.setFullImgUrl(productImgValue + comSkuImage.getImgUrl());
        }
        return comSkuImage;
    }

    /**
     * Spu 信息
     *
     * @param spuId
     * @return
     */
    public ComSpu getSpuById(Integer spuId) throws Exception {
        return comSpuMapper.selectById(spuId);
    }

    /**
     * SkuImage List 信息
     *
     * @param skuId
     * @return
     */
    public List<ComSkuImage> findComSkuImages(Integer skuId) throws Exception {
        String productImgValue = PropertiesUtils.getStringValue("index_product_308_308_url");
        List<ComSkuImage> comSkuImageList = comSkuImageMapper.selectBySkuId(skuId);
        if (comSkuImageList != null) {
            for (ComSkuImage comSkuImage : comSkuImageList) {
                comSkuImage.setImgUrl(productImgValue + comSkuImage.getImgUrl());
            }
        }
        return comSkuImageList;
    }

    /**
     * sku 详细信息
     * jjh
     */
    public SkuInfo getSkuInfoBySkuId(Long shopId, Integer skuId) throws Exception {
        SkuInfo skuInfo = new SkuInfo();
        ComSku comSku = comSkuMapper.selectByPrimaryKey(skuId);
        if (comSku != null) {
            skuInfo.setComSku(comSku);
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            skuInfo.setSlogan(comSpu.getSlogan());
            skuInfo.setContent(comSpu.getContent());
        }
        SfShopSku sfShopSku = sfShopSkuMapper.selectByShopIdAndSkuId(shopId, skuId);
        if (sfShopSku != null) {
            skuInfo.setSaleNum(sfShopSku.getSaleNum());
            skuInfo.setShareNum(sfShopSku.getShareNum());
        }
        PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
        if (pfSkuStock != null) {
            skuInfo.setStock(pfSkuStock.getStock());
        }
        SfShop sfShop = sfShopMapper.selectByPrimaryKey(shopId);
        if(sfShop!=null){
            skuInfo.setShipAmount(sfShop.getShipAmount());
        }
        return skuInfo;
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
    }
}
