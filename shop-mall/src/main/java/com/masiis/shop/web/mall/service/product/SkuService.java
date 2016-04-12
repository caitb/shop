package com.masiis.shop.web.mall.service.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.platform.product.*;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private Log log = LogFactory.getLog(this.getClass());
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
    private SfShopMapper sfShopMapper;

    @Resource
    private ComUserMapper comUserMapper;

    public ComSku getSkuById(Integer skuId) {
        return comSkuMapper.selectByPrimaryKey(skuId);
    }

    public ComSkuImage findComSkuImage(Integer skuId) {
        return comSkuImageMapper.selectDefaultImgBySkuId(skuId);
    }

    /**
     * 判断库存是否足够
     *
     * @author JJH
     * @date 2016/4/1 16:19
     */
    public int checkSkuStock(Integer skuId, int quantity, Long shopId) {
        int n;
        SfShop sfShop = sfShopMapper.selectByPrimaryKey(shopId);
        ComUser shopUser = comUserMapper.selectByPrimaryKey(sfShop.getUserId());
        PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(sfShop.getUserId(), skuId);
        if(shopUser!=null && shopUser.getSendType().equals("1")){//平台
            n = pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock();
        }else{
            n = pfUserSkuStock.getCustomStock();
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
     * 获取小铺商品
     * @author muchaofeng
     * @date 2016/4/10 14:37
     */
    public List<SfShopSku> getSfShopSkuByShopId(Long shopId) throws Exception {
        return sfShopSkuMapper.selectByShopId(shopId);
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
        SfShop sfShop = sfShopMapper.selectByPrimaryKey(shopId);
        if (sfShop != null) {
            skuInfo.setShipAmount(sfShop.getShipAmount());
        }
        ComUser shopUser = comUserMapper.selectByPrimaryKey(sfShop.getUserId());
        PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(sfShop.getUserId(), skuId);
        if (pfUserSkuStock != null) {
            if (shopUser.getSendType() == 1) {//平台代发
                skuInfo.setStock(pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock());
            }
            if (shopUser.getSendType() == 2) {//自己发货
                skuInfo.setStock(pfUserSkuStock.getCustomStock());
            }
        }
        return skuInfo;
    }

}
