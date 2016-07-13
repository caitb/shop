package com.masiis.shop.web.common.service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuExtendMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.platform.product.ComSkuExtensionMapper;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.web.platform.service.product.PfSkuStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * sku业务类
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
    private SfShopSkuExtendMapper sfShopSkuExtendMapper;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private PfSkuStockService pfSkuStockService;
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
        PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(sfShop.getUserId(), skuId);
        if(shopUser!=null && shopUser.getSendType()==1){//平台
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
        String productImgValue = PropertiesUtils.getStringValue("index_product_prototype_url");
        ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(skuId);
        if (comSkuImage != null) {
            comSkuImage.setFullImgUrl(productImgValue + comSkuImage.getImgUrl());
        }
        return comSkuImage;
    }

    /**
     * 获取代理等级图标
     * @author muchaofeng
     * @date 2016/4/27 12:02
     */
    public SfShopSku findSfSkuLevelImage(Long shopId,Integer skuId) throws Exception {
        String ImgValue = PropertiesUtils.getStringValue("agent_level_product_icon_url");
        SfShopSku sfShopSku = sfShopSkuExtendMapper.selectShopviewByShopIdAndSkuId(shopId, skuId);
        if (sfShopSku != null) {
            sfShopSku.setIcon(ImgValue + sfShopSku.getIcon());
        }
        return sfShopSku;
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
        String productImgValue = PropertiesUtils.getStringValue("index_product_prototype_url");
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
    public SkuInfo getSkuInfoBySkuId(Long shopId, Integer skuId,Integer isOwnShip) throws Exception {
        SkuInfo skuInfo = new SkuInfo();
        ComSku comSku = comSkuMapper.selectByPrimaryKey(skuId);
        if (comSku != null) {
            skuInfo.setComSku(comSku);
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            skuInfo.setSlogan(comSpu.getSlogan());
            skuInfo.setContent(comSpu.getContent());
        }
        SfShopSku sfShopSku = sfShopSkuMapper.selectByShopIdAndSkuId(shopId, skuId,isOwnShip);
        if (sfShopSku != null) {
//            skuInfo.setSaleNum(sfShopSku.getSaleNum());
            skuInfo.setShareNum(sfShopSku.getShareNum());
            skuInfo.setIsSale(sfShopSku.getIsSale());
        }
        List<SfShopSku> sfShopSkuList = sfShopSkuMapper.selectBySkuId(skuId);
        if (sfShopSkuList != null) {
            Long saleNum = 0L;
            for (SfShopSku sfShopSku1 : sfShopSkuList) {
                saleNum = saleNum + sfShopSku1.getSaleNum();
            }
            skuInfo.setSaleNum(saleNum);
        }
        SfShop sfShop = sfShopMapper.selectByPrimaryKey(shopId);
        ComUser shopUser = comUserMapper.selectByPrimaryKey(sfShop.getUserId());
        PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(sfShop.getUserId(), skuId);
        if (pfUserSkuStock != null && sfShop != null) {
            if (isOwnShip == 0) {//平台代发
                skuInfo.setShipAmount(sfShop.getShipAmount());
                int currentStock = pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock();
                if(currentStock>=0){
                    skuInfo.setStock(currentStock);
                }else {
                    skuInfo.setStock(0);
                }
            }
            if (isOwnShip == 1) {//自己发货
                int currentCustomerStock = pfUserSkuStock.getCustomStock()-pfUserSkuStock.getFrozenCustomStock();
                skuInfo.setStock(currentCustomerStock >= 0 ? currentCustomerStock : 0);
            }
        }
        return skuInfo;
    }

	/**
     * 检查库存状态
     *
     * @param skuId
     * @param quantity
     * @param pUserId
     * @return 0库存充足1库存不足进入排单2库存不足不可下单
     * @throws Exception
     */
    public int getSkuStockStatus(Integer skuId, int quantity, Long pUserId) throws Exception {
        if (pUserId == 0) {
            PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(skuId);
            //如果进入排单直接返回-quantity
            if (pfSkuStock.getIsQueue() == 1) {
                return 1;
            }
            if (pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < quantity) {
                return 1;
            } else {
                return 0;
            }
        } else {
            ComUser comUser = comUserMapper.selectByPrimaryKey(pUserId);
            if (comUser == null) {
                throw new BusinessException("找不到该用户");
            } else {
                PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pUserId, skuId);
                //拿货方式: 0,未选择; 1,平台代发; 2,自己发货
                if (comUser.getSendType() == 1) {
                    if (pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock() < quantity) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    if (pfUserSkuStock.getCustomStock() < quantity) {
                        return 2;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    /**
     * jjh
     * 查看排单flag
     */
    public PfSkuStock getPfSkuStockInfoBySkuId(Integer skuId) {
        return pfSkuStockService.selectBySkuId(skuId);
    }


    public ComSku getSkuName(Integer skuId) {
        return comSkuMapper.selectByPrimaryKey(skuId);
    }

    public List<ComSku> getAllSku() {
        return comSkuMapper.selectAll();
    }



}
