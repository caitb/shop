package com.masiis.shop.api.service.product;

import com.masiis.shop.api.constants.SysConstants;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JingHao on 2016/4/13 0013.
 */
@Service
@Transactional
public class ManageShopProductService {

    @Resource
    private SfShopSkuMapper sfShopSkuMapper;

    @Resource
    private ComSkuMapper comSkuMapper;

    @Resource
    private ComSkuImageMapper comSkuImageMapper;

    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;

    @Resource
    private ComUserMapper ComUserMapper;


    /**
      * @Author JJH
      * @Date 2016/4/13 0013 上午 10:29
      * 小铺中商品列表
      */
     public List<SkuInfo> getShopProductsList(Long shopId,Integer isSale,Long userId) throws Exception{

         List<SfShopSku> sfShopSkuList = sfShopSkuMapper.selectByShopIdAndSaleType(shopId,isSale);
         List<SkuInfo> skuInfoList = new ArrayList<>();
         ComUser comUser = ComUserMapper.selectByPrimaryKey(userId);
         if(sfShopSkuList!=null){
             String Value = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
             for(SfShopSku sfShopSku :sfShopSkuList){
                 SkuInfo skuInfo = new SkuInfo();
                 ComSku comsku = comSkuMapper.selectByPrimaryKey(sfShopSku.getSkuId());
                 ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(sfShopSku.getSkuId());
                 PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(userId,sfShopSku.getSkuId());
                 comSkuImage.setFullImgUrl(Value+comSkuImage.getImgUrl());
                 skuInfo.setComSku(comsku);
                 skuInfo.setComSkuImage(comSkuImage);
                 skuInfo.setShopSkuId(sfShopSku.getId());
                 skuInfo.setSaleNum(sfShopSku.getSaleNum());
                 if(pfUserSkuStock!=null){
                     if(comUser.getSendType()==1){//平台代发
                         skuInfo.setStock(pfUserSkuStock.getStock()-pfUserSkuStock.getFrozenStock());
                     }else if(comUser.getSendType()==2){//自己
                         skuInfo.setStock(pfUserSkuStock.getCustomStock());
                     }else{
                         throw new BusinessException("发货方式未选择！");
                     }
                 }
                 skuInfoList.add(skuInfo);
             }
         }
         return skuInfoList;
     }

    /**
      * @Author JJH
      * @Date 2016/4/13 0013 下午 4:15
      * 商品上下架
      */
    public void updateSale(Long shopSkuId,Integer isSale) throws Exception{
        SfShopSku sfShopSku = sfShopSkuMapper.selectByPrimaryKey(shopSkuId);
        if(sfShopSku!=null){
            sfShopSku.setIsSale(isSale);
            sfShopSkuMapper.updateByPrimaryKey(sfShopSku);
        }
    }
}
