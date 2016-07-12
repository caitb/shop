package com.masiis.shop.api.service.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.druid.support.logging.SLF4JImpl;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.api.service.product.PfUserSkuStockService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.common.constant.platform.SysConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JingHao on 2016/4/13 0013.
 */
@Service
@Transactional
public class ManageShopProductService {
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    private SfShopSkuMapper sfShopSkuMapper;

    @Resource
    private ComSkuMapper comSkuMapper;

    @Resource
    private ComSkuImageMapper comSkuImageMapper;

    @Resource
    private PfUserSkuStockService pfUserSkuStockService;

    @Resource
    private SfShopMapper sfShopMapper;

    /**
     * @Author JJH
     * @Date 2016/4/13 0013 上午 10:29
     * 小铺中商品列表
     */
    public List<SkuInfo> getShopProductsList(Long shopId, Integer isSale, Long userId, Integer deliverType, int currentPage, int pageSize) throws Exception {
        PageHelper.startPage(currentPage, pageSize,false);
        List<SfShopSku> sfShopSkuList = sfShopSkuMapper.selectByShopIdAndSaleType(shopId, isSale, deliverType);
        SfShop sfShop = sfShopMapper.selectByPrimaryKey(shopId);
        List<SkuInfo> skuInfoList = new ArrayList<>();
        if (sfShopSkuList != null) {
            String Value = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
            for (SfShopSku sfShopSku : sfShopSkuList) {
                SkuInfo skuInfo = new SkuInfo();
                skuInfo.setWxqrCode(sfShop.getWxQrCode());
                ComSku comsku = comSkuMapper.selectByPrimaryKey(sfShopSku.getSkuId());
                ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(sfShopSku.getSkuId());
                comSkuImage.setFullImgUrl(Value + comSkuImage.getImgUrl());
                skuInfo.setComSku(comsku);
                skuInfo.setSkuName(comsku.getName());
                skuInfo.setPriceRetail(comsku.getPriceRetail());
                skuInfo.setSkuId(comsku.getId());
                skuInfo.setComSkuImageUrl(comSkuImage.getFullImgUrl());
                skuInfo.setShopSkuId(sfShopSku.getId());
                skuInfo.setSaleNum(sfShopSku.getSaleNum());
                PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(userId, sfShopSku.getSkuId());
                skuInfo.setIsOwnShip(sfShopSku.getIsOwnShip());
                skuInfo.setFlagSelf(sfShopSku.getRemark());
                if (pfUserSkuStock != null) {
                    if (sfShopSku.getIsOwnShip() == 0) {//平台代发
                        int useStock = pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock();
                        if (useStock >= 0) {
                            skuInfo.setStock(pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock());
                        } else {
                            skuInfo.setStock(0);
                        }
                    } else {
                        skuInfo.setStock(pfUserSkuStock.getCustomStock());
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

    /**
     * JJH
     * @param shopSkuId
     * @throws Exception
     */
    public void addSelfDeliverySku(Long shopSkuId)throws Exception{
        SfShopSku sfShopSku = sfShopSkuMapper.selectByPrimaryKey(shopSkuId);
        if (sfShopSku == null) {
            throw new BusinessException("店铺商品异常");
        }
        log.info("设置标注为生成自己发货---");
        sfShopSku.setRemark("已经生成店主发货");
        sfShopSkuMapper.updateByPrimaryKey(sfShopSku);
        sfShopSku.setRemark("");
        sfShopSku.setIsOwnShip(1);
        sfShopSku.setCreateTime(new Date());
        sfShopSku.setId(null);
        sfShopSkuMapper.insert(sfShopSku);
        log.info("--添加自己发货的商品ok");
    }
}
